package com.quest.database;

import java.util.ArrayList;
import java.util.Queue;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import com.quest.constants.GameFlags;
import com.quest.database.queries.IQuery;
import com.quest.database.queries.Query;
import com.quest.database.queries.QueryPlayerLevelUP;
import com.quest.database.queries.QueryRegisterPlayerExperience;
import com.quest.database.queries.QueryRegisterPlayerPosition;
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
	// ===========================================================
	// Constructors
	// ===========================================================
	public QueryQueuer(){
		this.initQueryPool();
		this.mQueue = new ArrayList<Query>();
		Game.getTimerHelper().addTimer(new Timer(1, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(step % 10 == 0){
						for(int i = Game.getPlayerHelper().getEntities().size() - 1;i>=0;i--){
							QueryRegisterPlayerExperience query = (QueryRegisterPlayerExperience) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE);
							Player player = Game.getPlayerHelper().getPlayerbyIndex(i);
							query.set(player.getPlayerID(), player.getExperience());
							mQueue.add(query);
						}	
					}else if(step % 5 == 0){
						for(int i = Game.getPlayerHelper().getEntities().size() - 1;i>=0;i--){
							QueryRegisterPlayerPosition query = (QueryRegisterPlayerPosition) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_REGISTER_PLAYER_POSITION);
							Player player = Game.getPlayerHelper().getPlayerbyIndex(i);
							query.set(player.getPlayerID(), player.getCurrentMap(), player.getTMXTileAt().getTileColumn(), player.getTMXTileAt().getTileRow());
							mQueue.add(query);
						}	
					}else{
						if(mQueue.size()>0){
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
		this.mQueryPool.registerQuery(FLAG_QUERY_PLAYER_LEVEL_UP, QueryPlayerLevelUP.class);
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


	public void addPlayerLevelUPQuery(int pPlayerID,int pLevel,float pExperience){
		QueryPlayerLevelUP query = (QueryPlayerLevelUP) QueryQueuer.this.mQueryPool.obtainQuery(FLAG_QUERY_PLAYER_LEVEL_UP);
		query.set(pPlayerID, pLevel, pExperience);
		mQueue.add(query);
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
