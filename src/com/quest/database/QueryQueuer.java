package com.quest.database;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.quest.constants.GameFlags;
import com.quest.database.queries.IQuery;
import com.quest.database.queries.Query;
import com.quest.database.queries.QueryPlayerLevelUP;
import com.quest.database.queries.QueryRegisterPlayerExperience;
import com.quest.database.queries.QueryRegisterPlayerHPMP;
import com.quest.database.queries.QueryRegisterPlayerMoney;
import com.quest.database.queries.QueryRegisterPlayerPosition;
import com.quest.database.queries.QuerySetPlayerAttributes;
import com.quest.database.queries.QueryWritePlayerInventory;
import com.quest.entities.Player;
import com.quest.game.Game;
import com.quest.pools.QueryPool;
import com.quest.timers.Timer;

public class QueryQueuer implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private final QueryPool<IQuery> mQueryPool = new QueryPool<IQuery>();
	private ArrayList<Query> mQueue;//***usar queue
	private float step = 0;
	private boolean working = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	public QueryQueuer(){
		this.initQueryPool();
		this.mQueue = new ArrayList<Query>();
		Game.getTimerHelper().addTimer(new Timer(1, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(step % 10 == 0 && !working){
						for(int i = Game.getPlayerHelper().getEntities().size() - 1;i>=0;i--){
							Player player = Game.getPlayerHelper().getPlayerbyIndex(i);
							
							QueryRegisterPlayerExperience query = (QueryRegisterPlayerExperience) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE);
							query.set(player.getPlayerID(), player.getExperience());
							mQueue.add(query);
							
							QueryRegisterPlayerMoney money = (QueryRegisterPlayerMoney) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_MONEY);
							money.set(player.getPlayerID(), player.getMoney());
							mQueue.add(money);
							
						}	
					}else if(step % 5 == 0 && !working){
						for(int i = Game.getPlayerHelper().getEntities().size() - 1;i>=0;i--){
							QueryRegisterPlayerPosition query = (QueryRegisterPlayerPosition) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_POSITION);
							Player player = Game.getPlayerHelper().getPlayerbyIndex(i);
							query.set(player.getPlayerID(), player.getCurrentMap(), player.getTMXTileAt().getTileColumn(), player.getTMXTileAt().getTileRow());
							mQueue.add(query);
						}	
					}else{
						if(mQueue.size()>0 && !working){
							mQueue.get(0).executeQuery();
							mQueryPool.recycleQuery(mQueue.get(0));
							mQueue.remove(0);
						}
					}
					step++;
				}
			}), "QueryQueuer");
	}
	
	private void initQueryPool(){
		this.mQueryPool.registerQuery(FLAG_QUERY_REGISTER_PLAYER_POSITION, QueryRegisterPlayerPosition.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE, QueryRegisterPlayerExperience.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_REGISTER_PLAYER_MONEY, QueryRegisterPlayerMoney.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_REGISTER_PLAYER_HPMP, QueryRegisterPlayerHPMP.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_PLAYER_LEVEL_UP, QueryPlayerLevelUP.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_SET_PLAYER_ATTRIBUTES, QuerySetPlayerAttributes.class);
		this.mQueryPool.registerQuery(FLAG_QUERY_WRITE_PLAYER_INVENTORY, QueryWritePlayerInventory.class);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================
	public void allocateQueryinPool(int QUERY_FLAG,int pAmountToAllocate){
		this.mQueryPool.getPool(QUERY_FLAG).batchAllocatePoolItems(pAmountToAllocate);
	}
	
	public void executeQueries(){
		working = true;
		for(int i = Game.getPlayerHelper().getEntities().size() - 1;i>=0;i--){
			Player player = Game.getPlayerHelper().getPlayerbyIndex(i);
			
			addWritePlayerInventoryQuery(player.getPlayerID());

			QueryRegisterPlayerMoney money = (QueryRegisterPlayerMoney) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_MONEY);
			money.set(player.getPlayerID(), player.getMoney());
			mQueue.add(money);
			
			QueryRegisterPlayerExperience experience = (QueryRegisterPlayerExperience) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE);
			experience.set(player.getPlayerID(), player.getExperience());
			mQueue.add(experience);

			QueryRegisterPlayerPosition position = (QueryRegisterPlayerPosition) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_POSITION);
			position.set(player.getPlayerID(), player.getCurrentMap(), player.getTMXTileAt().getTileColumn(), player.getTMXTileAt().getTileRow());
			mQueue.add(position);
			
			QueryRegisterPlayerHPMP HPMP = (QueryRegisterPlayerHPMP) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_HPMP);
			HPMP.set(player.getPlayerID(), (int)(player.getCurrHPMP()[0]),(int)(player.getCurrHPMP()[1]));
			mQueue.add(HPMP);
		}
		for(int i = 0;i < mQueue.size();i++){
			mQueue.get(0).executeQuery();
			mQueryPool.recycleQuery(mQueue.get(0));
			mQueue.remove(0);
		}
		working = false;
	}
	
	
	public void addPlayerLevelUPQuery(int pPlayerID,int pLevel,int pUnassignedPoints,float pExperience){
		QueryPlayerLevelUP query = (QueryPlayerLevelUP) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_PLAYER_LEVEL_UP);
		query.set(pPlayerID, pLevel,pUnassignedPoints, pExperience);
		mQueue.add(query);
	}
	
	public void addSetPlayerAttributesQuery(int pPlayerID,int[] pAttributes,int pUnassigned){
		QuerySetPlayerAttributes query = (QuerySetPlayerAttributes) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_SET_PLAYER_ATTRIBUTES);
		query.set(pPlayerID, pAttributes,pUnassigned);
		mQueue.add(query);
	}
	
	public void addWritePlayerInventoryQuery(int pPlayerID){
		working = true;
		QueryWritePlayerInventory query = (QueryWritePlayerInventory) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_WRITE_PLAYER_INVENTORY);
		query.setPlayerID(pPlayerID);
		if(mQueue.size()>1){
		int i = 1;
		boolean found = false;
		while(found == false && i<mQueue.size()){
			if(mQueue.get(i).getFlag()==FLAG_QUERY_WRITE_PLAYER_INVENTORY){
				if(((QueryWritePlayerInventory)(mQueue.get(i))).getPlayerID()==pPlayerID)
					found = true;
			}
		}
		
		if(found){
			mQueue.set(i, query);
		}else{
			mQueue.add(query);
		}
		
		}else{
			mQueue.add(query);
		}
		working = false;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
