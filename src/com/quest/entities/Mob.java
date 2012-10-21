/**
 * 
 */
package com.quest.entities;

import java.util.Random;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.game.Game;
import com.quest.timers.Timer;



/**
 * @author raccoon
 *
 */
//public class Enemy extends Entity implements IOnScreenControlListener{
public class Mob extends BaseEntity implements ITouchArea, GameFlags{
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Random rand;
	private int mMobFlag;
	private int[] mDroppedItems,mDropRates,mDropAmounts;
	private boolean mGrabbed = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Mob(int pMobFlag){
		super(Game.getDataHandler().getMobAnimationTexture(pMobFlag), Game.getDataHandler().getMobFrameWidth(pMobFlag), Game.getDataHandler().getMobFrameHeight(pMobFlag), 0, 0, Game.getDataHandler().getMobAnimationCols(pMobFlag), Game.getDataHandler().getMobAnimationRows(pMobFlag));
		this.mMobFlag = pMobFlag;
		this.setModifiers(Game.getDataHandler().getMobAttributes(mMobFlag));
		this.mMoney = Game.getDataHandler().getMobMoney(pMobFlag);
		this.mExperience= Game.getDataHandler().getMobExperience(pMobFlag);
		this.mDroppedItems = Game.getDataHandler().getMobDroppedItems(mMobFlag);
		this.mDropRates = Game.getDataHandler().getMobDropRates(mMobFlag);
		this.mDropAmounts = Game.getDataHandler().getMobDropAmounts(mMobFlag);
		this.mEntityType = "Mob";
	
		if(Game.isServer()){	
			Game.getTimerHelper().addTimer(new Timer(3, new ITimerCallback() {			
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					// TODO Auto-generated method stub
					Mob.this.doRandomPath();
				}
			}), (String) this.getUserData());
		}
	}


	// ===========================================================
	// Methods
	// ===========================================================
	public void doRandomPath() 
	{		
		if(!this.isWalking) 
		{	
			boolean move = false;
			byte movingDirection = DIRECTION_DEFAULT;
			switch(this.getRandom(1, 5))
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
				
				// Animate the Character
				this.setAnimationDirection(movingDirection, false);
				
				// Sends Move
				if(Game.isServer()){
					Game.getServer().sendMessageMoveMob((Integer)(this.getUserData()), movingDirection);
					this.moveInDirection(movingDirection);
				}
			}
		}		
	}
	
	public int getRandom(int min, int max)
	{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
	}
	
	public int[] getMobDrop(){
		//HACER EL CALCULO DE QUE ITEM TIENE QUE DROPPEAR
		return new int[]{0,0};
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
				Game.getPlayerHelper().getOwnPlayer().onAttackAction(this, Game.getPlayerHelper().getOwnPlayer().getAttack_Flag());
				if(this.getAlpha()==1f){
					Game.getMobHelper().clearMobsAlpha();
					this.getBodySprite().setAlpha(0.70f);
				}

				Game.getSceneManager().getGameScene().setHPbar((this.getCurrHP()*100)/this.getModHP());

				
			}
			break;
		}
	
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
				pTouchAreaLocalY);
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
		this.mAttackLayer.add(Game.getAttacksHelper().addNewAttack(FLAG_ATTACK_MOB_DEATH));
		//Hacer un wait?
		try {
			wait(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game.getMobHelper().deleteMob((Integer)(this.getUserData()));
	}

	
	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int ATTACK_FLAG){
		this.mAttackLayer.add(Game.getAttacksHelper().addNewAttack(ATTACK_FLAG));	//Mostrar la animacion de ataque

		Log.d("Quest!", "Mob: "+this.getUserData()+" hp: "+this.currHP);//mostrar la barrita de hp 
		if(decreaseHP(pDamage)){
			if(Game.isServer()){
				int[] drop = this.getMobDrop();
				Game.getBattleHelper().killMob(this,drop[0],drop[1], this.getExperience(), this.getMoney(),(Player) (pAttackingEntity));
			}
		}
		Game.getSceneManager().getGameScene().setHPbar((this.getCurrHP()*100)/this.getModHP());

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
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}
