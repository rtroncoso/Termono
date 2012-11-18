/**
 * 
 */
package com.quest.entities;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.objects.Attack;
import com.quest.game.Game;
import com.quest.timers.Timer;



/**
 * @author raccoon
 *
 */
public class Mob extends BaseEntity implements ITouchArea, GameFlags{
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int mMobFlag;
	private int[] mDroppedItems,mDropRates,mDropAmounts;
	private boolean dying = false;
	private boolean mGrabbed = false;
	private boolean following,pursuit,cooling = false;
	private int mViewRange,mAttackRange;
	private int mATTACK_FLAG;
	private int mMobType;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Mob(int pMobFlag){
		super(Game.getDataHandler().getMobAnimationTexture(pMobFlag), Game.getDataHandler().getMobFrameWidth(pMobFlag), Game.getDataHandler().getMobFrameHeight(pMobFlag), 0, 0, Game.getDataHandler().getMobAnimationCols(pMobFlag), Game.getDataHandler().getMobAnimationRows(pMobFlag));
		this.mMobFlag = pMobFlag;
		this.mName = Game.getDataHandler().getMobName(mMobFlag);
		this.mLevel = Game.getDataHandler().getMobLevel(mMobFlag);
		this.setModifiers(Game.getDataHandler().getMobAttributes(mMobFlag));
		this.mMoney = Game.getDataHandler().getMobMoney(pMobFlag);
		this.mExperience= Game.getDataHandler().getMobExperience(pMobFlag);
		this.mDroppedItems = Game.getDataHandler().getMobDroppedItems(mMobFlag);
		this.mDropRates = Game.getDataHandler().getMobDropRates(mMobFlag);
		
		setMobType(Game.getDataHandler().getMobType(mMobFlag));
		
		this.mDropAmounts = Game.getDataHandler().getMobDropAmounts(mMobFlag);
		this.mBodyExtraCols = Game.getDataHandler().getMobExtraCols(mMobFlag);
		this.mViewRange = Game.getDataHandler().getMobViewRange(mMobFlag);
		this.mAttackRange = Game.getDataHandler().getMobAttackRange(mMobFlag);
		this.mATTACK_FLAG = Game.getDataHandler().getMobAttack(mMobFlag);
		startRecoveryTimer();
		this.mEntityType = "Mob";
	}
	
	//si hago que los mobs se ataquen entre si acordarme de mandar la exp en el existing mob message

	// ===========================================================
	// Methods
	// ===========================================================
	public void startRecoveryTimer(){
		if(Game.isServer() && this.getModMana()>0){
			Game.getTimerHelper().addTimer(new Timer(0.2f, new ITimerCallback() 
			{	
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					if(Mob.this.getCurrMana()<Mob.this.getModMana())Mob.this.recoverMP();
				}
			}), Mob.this.getUserData()+";Recovery");		
		}
}
	
	public void startMoveTimer(){
		if(Game.isServer()){	
			Game.getTimerHelper().addTimer(new Timer(2.5f, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					if(!following && !pursuit){
						Mob.this.doRandomPath();
						for(int i = Game.getPlayerHelper().getEntities().size()-1;i>=0;i--)
							if(Game.getPlayerHelper().getPlayerbyIndex(i).getTMXTileAt()!=null)
								if(Game.getPlayerHelper().getPlayerbyIndex(i).getTMXTileAt().getTileColumn()>(Mob.this.getTMXTileAt().getTileColumn()-4) && Game.getPlayerHelper().getPlayerbyIndex(i).getTMXTileAt().getTileColumn()<(Mob.this.getTMXTileAt().getTileColumn()+4))
									if(Game.getPlayerHelper().getPlayerbyIndex(i).getTMXTileAt().getTileRow()>(Mob.this.getTMXTileAt().getTileRow()-4) && Game.getPlayerHelper().getPlayerbyIndex(i).getTMXTileAt().getTileRow()<(Mob.this.getTMXTileAt().getTileRow()+4)){
										Log.d("Quest!","Mob; player "+Game.getPlayerHelper().getPlayerbyIndex(i).getUserID()+" is near me");
										startFollowTimer(Game.getPlayerHelper().getPlayerbyIndex(i),false);
									}
					}
				}
			}), String.valueOf(this.getUserData()));
		}
	}
	
	
	
	public void startFollowTimer(final Player player,boolean inpursuit){
		if(Game.isServer()){
			following = true;
			pursuit = inpursuit;
			
			Game.getTimerHelper().addTimer(new Timer(0.2f, new ITimerCallback() {			
				float countdown = 25;
				boolean blocked = false;
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					boolean attack = false;
					byte movingDirection = DIRECTION_DEFAULT;
					
					if(player.getTMXTileAt().getTileColumn()>(Mob.this.getTMXTileAt().getTileColumn()-mViewRange) && player.getTMXTileAt().getTileColumn()<(Mob.this.getTMXTileAt().getTileColumn()+mViewRange) && player.getTMXTileAt().getTileRow()>(Mob.this.getTMXTileAt().getTileRow()-mViewRange) && player.getTMXTileAt().getTileRow()<(Mob.this.getTMXTileAt().getTileRow()+mViewRange) || pursuit){
						countdown-=0.2f;
						if(countdown<0.1f && pursuit)pursuit=false;
						
						if(player.getTMXTileAt().getTileColumn()<Mob.this.getTMXTileAt().getTileColumn() && !blocked){
							if(player.getTMXTileAt().getTileRow()==Mob.this.getTMXTileAt().getTileRow() && player.getTMXTileAt().getTileColumn()>=(Mob.this.getTMXTileAt().getTileColumn()-mAttackRange)){
								attack = true;
							}else{
								movingDirection = DIRECTION_WEST;
							}
						}else if(player.getTMXTileAt().getTileColumn()>Mob.this.getTMXTileAt().getTileColumn() && !blocked){
							if(player.getTMXTileAt().getTileRow()==Mob.this.getTMXTileAt().getTileRow() && player.getTMXTileAt().getTileColumn()<=(Mob.this.getTMXTileAt().getTileColumn()+mAttackRange)){
								attack = true;
							}else{
								movingDirection = DIRECTION_EAST;
							}
						}else if(player.getTMXTileAt().getTileColumn()==Mob.this.getTMXTileAt().getTileColumn() || blocked){
							if(player.getTMXTileAt().getTileRow()<Mob.this.getTMXTileAt().getTileRow()){
								if(player.getTMXTileAt().getTileRow()>=(Mob.this.getTMXTileAt().getTileRow()-mAttackRange) && !blocked){
									attack = true;
								}else{
									movingDirection = DIRECTION_SOUTH;
								}
							}else if(player.getTMXTileAt().getTileRow()>Mob.this.getTMXTileAt().getTileRow()){
								if(player.getTMXTileAt().getTileRow()<=(Mob.this.getTMXTileAt().getTileRow()+mAttackRange) && !blocked){
									attack = true;
								}else{
									movingDirection = DIRECTION_NORTH;
								}	
							}
						}
						blocked = false;
							
						if(!attack){
							TMXTile tmpNewTile = Mob.this.moveInDirection(movingDirection);
							if(tmpNewTile!=null){
								Log.d("Quest!","usrdt: "+((Integer)Mob.this.getUserData())+" tmp: "+tmpNewTile+" \n x:"+tmpNewTile.getTileColumn()+" y: "+tmpNewTile.getTileRow()+" \n px: "+player.getTMXTileAt().getTileColumn()+" py: "+player.getTMXTileAt().getTileRow());
								Game.getServer().sendMessageMoveMob((Integer)(Mob.this.getUserData()), tmpNewTile.getTileColumn(), tmpNewTile.getTileRow());
							}else{
							//	Log.e("Quest!","Blocked: "+blocked);
								blocked = true;
							}
							Mob.this.moveToTile(tmpNewTile);
						}else{
							if(!cooling){
								Mob.this.onAttackAction(player, mATTACK_FLAG);
								startCooldown(1.5f);
							}
						}
					
					}else{
						following = false;
						Game.getTimerHelper().deleteTimer(String.valueOf(Mob.this.getUserData())+";Follow");
					}
				}
			}), String.valueOf(this.getUserData()+";Follow"));
		}
	}
	
	public void startCooldown(float time){
		cooling = true;
			Game.getTimerHelper().addTimer(new Timer(time, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					cooling = false;
					Game.getTimerHelper().deleteTimer(String.valueOf(Mob.this.getUserData())+";Cooldown");
				}
			}), String.valueOf(this.getUserData())+";Cooldown");
	}
	
	public void doRandomPath() 
	{		
		if(!this.isWalking) 
		{	
			boolean move = false;
			byte movingDirection = DIRECTION_DEFAULT;
			switch(Game.getRandomInt(1, 5))
			{
			case 1://Arriba
				movingDirection = DIRECTION_NORTH;
				move = true;
				break;
			case 2://Abajo
				movingDirection = DIRECTION_SOUTH;
				move = true;
				break;
			case 3://Derecha
				movingDirection = DIRECTION_EAST;
				move = true;
				break;
			case 4://Izquierda
				movingDirection = DIRECTION_WEST;
				move = true;
				break;
			case 5:
				movingDirection = DIRECTION_DEFAULT;
				move = false;
				break;
			}		
			if(move == true && movingDirection != DIRECTION_DEFAULT)
			{

				// Store the new Tile
				final TMXTile tmpNewTile = this.moveInDirection(movingDirection);
				if(tmpNewTile == null) return;
				
				// Sends Move
				Game.getServer().sendMessageMoveMob((Integer)(this.getUserData()), tmpNewTile.getTileColumn(), tmpNewTile.getTileRow());
				this.moveToTile(tmpNewTile);
			}
		}		
	}

	public int[] getMobDrop(){
		//HACER EL CALCULO DE QUE ITEM TIENE QUE DROPPEAR
		if(this.getMobFlag()==3){
			return new int[]{FLAG_ITEM_HEALTH_POTION,2};
		}else{
			return new int[]{FLAG_ITEM_BIG_FLAMED_SWORD,1};
		}
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		switch(pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			mGrabbed = true;					
			break;
		case TouchEvent.ACTION_UP:
			if(mGrabbed) {
				mGrabbed = false;
				if(!dying){
					Player tmpPlayer = Game.getPlayerHelper().getOwnPlayer();
					Attack tmpAttack = Game.getAttacksHelper().getAttack(tmpPlayer.getAttack_Flag());
					tmpPlayer.setCurrentTarget((Integer)Mob.this.getUserData());
					Log.d("Quest!", "At eff: "+tmpAttack.getEffect()[1]);
					if(tmpAttack.getEffect()[1]!=3){//si no es un area attack
						Game.getSceneManager().getGameScene().changeMobHUD(Mob.this);
						Game.getPlayerHelper().getOwnPlayer().onAttackAction(this, Game.getPlayerHelper().getOwnPlayer().getAttack_Flag());
						if(this.getAlpha()==1f){
							Game.getMobHelper().clearMobsAlpha();
							this.getBodySprite().setAlpha(0.70f);
						}
					}
					Game.getAttacksHelper().recycleAttack(tmpAttack);
				}
			}
			break;
		}
		return true;
		/*return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);*/
	}
	
	@Override
	public void setModifiers(int[] Modifiers) {
		this.addModifiers(Modifiers);
		this.updateHPMana(Modifiers[3]*10, Modifiers[1]*10);
		this.setLevel(Modifiers[4]);
	};

	@Override
	public void onDeathAction(BaseEntity pKillerEntity) {
		// TODO Auto-generated method stub
		super.onDeathAction(pKillerEntity);
		dying = true;
		Mob.this.following=false;
		pursuit = false;
		cooling = true;
		if(Game.isServer())Game.getTimerHelper().deleteTimer(String.valueOf(this.getUserData()));
		if(Game.isServer())Game.getTimerHelper().deleteTimer(String.valueOf(Mob.this.getUserData()+";Follow"));
		if(Game.getSceneManager().getGameScene().getOtherStatsHud() != null)Game.getSceneManager().getGameScene().getOtherStatsHud().dettachHUD((Integer)this.getUserData());
		Game.getTimerHelper().addTimer(new Timer(1, new ITimerCallback() {			
			int i = 1;
			@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					if(i==1){
						Mob.this.mAttackLayer.add(Game.getAttacksHelper().addNewAttack(FLAG_ATTACK_MOB_DEATH));
					}else if (i==0){
						Game.getTimerHelper().deleteTimer(String.valueOf(Mob.this.getUserData()));
						Game.getMobHelper().deleteMob(Mob.this);
					}
					i--;
				}
			}), String.valueOf(this.getUserData()));
		
	}

	
	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int ATTACK_FLAG){
		Attack tmpAtt = Game.getAttacksHelper().addNewAttack(ATTACK_FLAG);
		if(tmpAtt.getAttackType()!=2){
			this.mAttackLayer.add(tmpAtt);	//Mostrar la animacion de ataque
		}
		popOverHead(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_DAMAGE, this.getBodySprite().getX(), this.getBodySprite().getY(), String.valueOf(pDamage), "Damage;"+this.getUserData()+" "+System.currentTimeMillis()),1+(float)((float)(pDamage)/(float)(mModHP)));
		if(!following && !pursuit){
			this.startFollowTimer((Player)pAttackingEntity, true);
			Log.d("Quest!","Attacked, following");
		}
			
	};
	
	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int ATTACK_FLAG) {
		if(Game.getAttacksHelper().canAttack(Mob.this, ATTACK_FLAG)){
			this.onDisplayAttackingAction();
			Game.getBattleHelper().startAttack(Mob.this, ATTACK_FLAG, pAttackedEntity);
		}
	};
	
	public void recoverMP(){
		this.currMana+=(float)(this.mModIntelligence)/10;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	/**
	 * @return the following
	 */
	public boolean isFollowing() {
		return following;
	}

	/**
	 * @param following the following to set
	 */
	public void setFollowing(boolean following) {
		this.following = following;
	}

	/**
	 * @return the pursuit
	 */
	public boolean isPursuit() {
		return pursuit;
	}

	/**
	 * @param pursuit the pursuit to set
	 */
	public void setPursuit(boolean pursuit) {
		this.pursuit = pursuit;
	}

	/**
	 * @return the cooling
	 */
	public boolean isCooling() {
		return cooling;
	}

	/**
	 * @param cooling the cooling to set
	 */
	public void setCooling(boolean cooling) {
		this.cooling = cooling;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getMobFlag(){
		return this.mMobFlag;
	}
	
	public void setDying(boolean status){
		this.dying = status;
	}

	public int getMobType() {
		return mMobType;
	}

	public void setMobType(int mMobType) {
		this.mMobType = mMobType;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}
