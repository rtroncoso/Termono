/**
 * 
 */
package com.quest.entities;

import java.util.Random;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.quest.entities.objects.Spell;
import com.quest.game.Game;
import com.quest.network.messages.server.ServerMessageMobDied;
import com.quest.scenes.MatchScene;

/**
 * @author raccoon
 *
 */
//public class Enemy extends Entity implements IOnScreenControlListener{
public class Mob extends BaseEntity implements ITouchArea {
	
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private Random rand;
	private int mMobFlag;
	private int mMoney,mExperience;
	private int[] mDroppedItems,mDropRates,mDropAmounts;
	private boolean mGrabbed = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	public Mob(String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		// TODO Auto-generated constructor stub
		super(pTextureName, pFrameWidth, pFrameHeight, pFramePosX, pFramePosY, pCols, pRows);		
		this.mEntityType = "Mob";
	}
	
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
	}


	// ===========================================================
	// Methods
	// ===========================================================
	public void randomPath() 
	{		
		if(!this.isWalking) 
		{	boolean move = false;
			float moveToXTile = this.getX();
			float moveToYTile = this.getY();
			switch(this.getRandom(1, 10))
			{
			case 1://Arriba
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() - 32;
				 move = true;
				break;
			case 2://Abajo
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() + 32;
				 move = true;
				break;
			case 3://Derecha
				 moveToXTile = this.getX() + 32;
				 moveToYTile = this.getY();
				 move = true;
				break;
			case 4://Izquierda
				 moveToXTile = this.getX() - 32;
				 moveToYTile = this.getY();
				 move = true;
				break;			
			case 5://Arriba 2
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() - 64;
				 move = true;
				break;
			case 6://Abajo 2
				 moveToXTile = this.getX();
				 moveToYTile = this.getY() + 64;
				 move = true;
				 break;
			case 7://Derecha 2
				 moveToXTile = this.getX() + 64;
				 moveToYTile = this.getY();
				 move = true;
				 break;
			case 8://Izquierda 2
				 moveToXTile = this.getX() - 32;
				 moveToYTile = this.getY();
				 move = true;
				 break;
			case 9:
				//nada, se queda en el lugar
				 moveToXTile = this.getX();
				 moveToYTile = this.getY();
				 move = false;
				 break;
			case 10:
				//nada, se queda en el lugar
				 moveToXTile = this.getX();
				 moveToYTile = this.getY();
				 move = false;
				break;
			}		
			if(move == true)
			{

				// Is it a legal position?
				if(!Game.getMapManager().isLegalPosition((int) moveToXTile, (int) moveToYTile)) return;
				
				// Gets the Tile
				final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
				
				// Animate the Character
				long frameDuration = (long) ((1.0f / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };
				//this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), frameDurations, false);
				
				// Perform Move
				//this.moveToTile(tmxTileTo);
			}
		}		
	}
	
	private int getRandom(int min, int max)
	{
		rand = new Random();	
		int RandomNum = rand.nextInt(max - min + 1) + min;
		return RandomNum;
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
				Log.d("Quest!", "Mob: "+this.getUserData()+" hp: "+this.currHP);
				if(Game.getServer().equals(null)){
				Game.getClient().sendAttackMessage((Integer)(this.getUserData()), 0);
				}else{
					Log.d("Quest!","ServerMessage");
				}
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
			int dropItem = 0;
			if(getRandom(0, 3)==2)dropItem=1;//Calcular bien el amount y el item 
			Game.getBattleHelper().killMob(this, dropItem,1, this.getExperience(), this.getMoney(),(Player) (pKillerEntity));
	}

	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int pAttackID){
		if(decreaseHP(pDamage)){
			if(!Game.getServer().equals(null)){
				onDeathAction(pAttackingEntity);	
			}
		}
		Cambiar la barrita de hp
		Mostrar el ataque 
	};
	
	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int pAttackID) {
		this.mSpellsLayer.add(new Spell(0));	//Mostrar la animacion de ataque
		Llamar al battle helper si soy server
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
	
	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
	}

	public int getExperience() {
		return mExperience;
	}

	public void setExperience(int mExperience) {
		this.mExperience = mExperience;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	
}