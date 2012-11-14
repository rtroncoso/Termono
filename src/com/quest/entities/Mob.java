/**
 * 
 */
package com.quest.entities;

import java.util.ArrayList;

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
		this.mDropAmounts = Game.getDataHandler().getMobDropAmounts(mMobFlag);
		this.mBodyExtraCols = Game.getDataHandler().getMobExtraCols(mMobFlag);
		this.mEntityType = "Mob";
	}
	
	//si hago que los mobs se ataquen entre si acordarme de mandar la exp en el existing mob message

	// ===========================================================
	// Methods
	// ===========================================================
	public void startMoveTimer(){
		if(Game.isServer()){	
			Game.getTimerHelper().addTimer(new Timer(3, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					Mob.this.doRandomPath();
				}
			}), String.valueOf(this.getUserData()));
		}
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
		if(Game.isServer())Game.getTimerHelper().deleteTimer(String.valueOf(this.getUserData()));
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
	};
	
	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int ATTACK_FLAG) {
		if(!Game.isServer()){
			//muestro el mob atacando
		}else{
			//muestro el mob atacando
			//Mando mensaje de que el mob ataco (el mensaje llama este metodo del lado cliente)
		}
	};
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getMobFlag(){
		return this.mMobFlag;
	}
	
	public void setDying(boolean status){
		this.dying = status;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}
