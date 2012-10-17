package com.quest.entities;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;

import android.util.Log;

import com.quest.entities.objects.InventoryItem;
import com.quest.entities.objects.Spell;
import com.quest.game.Game;
import com.quest.helpers.BattleHelper;
import com.quest.helpers.InventoryItemHelper;
import com.quest.triggers.Trigger;


public class Player extends BaseEntity implements IOnScreenControlListener, ITouchArea {


	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mClass;
	private int mPlayerID;
	private int mHeadID;
	private int mPositionID;
	private String mUserID;
	private InventoryItemHelper mInventory;
	private int mMoney,mExperience;
	
	
	private int spellattackid;
	// ===========================================================
	// Constructors
	// ===========================================================
	public Player(int pInitialPosX, int pInitialPosY, String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		// TODO Auto-generated constructor stub
		super(pTextureName, pFrameWidth, pFrameHeight, pFramePosX, pFramePosY, pCols, pRows);

		this.mEntityType = "Player";
	}
	
	public Player(int pPlayerID,int pClass,String pUserID) {
		super(Game.getDataHandler().getClassAnimationTexture(pClass), Game.getDataHandler().getClassFrameWidth(pClass), Game.getDataHandler().getClassFrameHeight(pClass), 0, 0, Game.getDataHandler().getClassAnimationCols(pClass), Game.getDataHandler().getClassAnimationRows(pClass));
		this.mPlayerID = pPlayerID;
		this.mClass = pClass;
		this.setLevel(Game.getDataHandler().getPlayerLevel(this.mPlayerID));
		this.setAttributes(Game.getDataHandler().getPlayerAttributes(this.mPlayerID));
		this.setModifiers(this.getAttributes());
		this.updateHPMana(Game.getDataHandler().getPlayerCurrentHPMP(this.mPlayerID));
		this.setHeadID(Game.getDataHandler().getPlayerHeadID(this.mPlayerID));
		this.mUserID = pUserID;//Game.getDataHandler().getUserID(Game.getDataHandler().getPlayerProfileID(this.mPlayerID));
		this.setInventory(LoadInventory(Game.getDataHandler().getInventoryItems(this.mPlayerID),Game.getDataHandler().getInventoryAmounts(this.mPlayerID),Game.getDataHandler().getInventoryEquipStatus(this.mPlayerID)));
		setSpellattackid(1);
		this.mEntityType = "Player";
	}
	
	
	public Player(String pUserID,int pPlayerID,int pClass,int pLevel,int[] pAttributes,int[] currHPMP,int pHeadID,int[] pItemIDs,int[] pAmounts,int[] isEquipped){//Creacion de lado cliente, el inventory se lodea por separado(y solo al player propio) cuando llega el mensaje con los valores.
		super(Game.getDataHandler().getClassAnimationTexture(pClass), Game.getDataHandler().getClassFrameWidth(pClass), Game.getDataHandler().getClassFrameHeight(pClass), 0, 0, Game.getDataHandler().getClassAnimationCols(pClass), Game.getDataHandler().getClassAnimationRows(pClass));
		this.mPlayerID = pPlayerID;
		this.mClass = pClass;
		this.mLevel = pLevel;
		this.mHeadID = pHeadID;
		this.mUserID = pUserID;
		this.setAttributes(pAttributes);
		this.setModifiers(pAttributes);
		this.updateHPMana(currHPMP);
		this.setInventory(LoadInventory(pItemIDs, pAmounts, isEquipped));
		setSpellattackid(1);
		this.mEntityType = "Player";
	}

	// ===========================================================
	// Methods
	// ===========================================================
	private InventoryItemHelper LoadInventory(int[] pItemIDs,int[] pAmounts,int[] isEquipped){
		InventoryItemHelper pInventory = new InventoryItemHelper(mUserID);
		for(int i = 0;i<pItemIDs.length;i++){
			pInventory.addItem(new InventoryItem(pItemIDs[i], pAmounts[i], isEquipped[i]));
		}
		return pInventory;
	}
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {

		if(pValueX != 0.0f || pValueY != 0.0f) {
			if(!this.isWalking && !Game.getMapManager().isChangingMap()) {

				// Stores the player Position
				byte pDirection = DIRECTION_DEFAULT;
				
				// Check where it is heading
				if(pValueY == 1.0f) pDirection = DIRECTION_NORTH;
				if(pValueY == -1.0f) pDirection = DIRECTION_SOUTH;
				if(pValueX == 1.0f) pDirection = DIRECTION_EAST;
				if(pValueX == -1.0f) pDirection = DIRECTION_WEST;
				
				// Performs the move
				this.moveInDirection(pDirection);
				if(!Game.isServer()){
					Game.getClient().sendMovePlayerMessage(this.mUserID, pDirection);
				}else{
					Game.getServer().sendUpdateEntityPositionMessage(this.mUserID, pDirection);
				}
			}
		}
	}
	
	
	@Override
	public void onDeathAction(BaseEntity pKillerEntity) {
		// TODO Auto-generated method stub
		super.onDeathAction(pKillerEntity);
		if(!Game.isServer()){
			//Mostrar que murio el player
		}else{
			//Mostrar que murio el player
		    //mandar mensaje de que murio y en donde aparece (el mensaje llama este metodo del lado cliente)
		}
	}

	@Override
	public void onAttackedAction(BaseEntity pAttackingEntity, int pDamage,int pAttackID){
		if(decreaseHP(pDamage)){
			if(Game.isServer()){
				onDeathAction(pAttackingEntity);	
			}
		}
		Log.d("Quest!", "Player: "+this.getUserData()+" hp: "+this.currHP);
		this.mSpellsLayer.add(new Spell(getSpellattackid()));
	};
	
	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int pAttackID) {
		Game.getBattleHelper().startAttack(this, pAttackID, pAttackedEntity);
		Log.d("Quest!", "Player: "+this.getUserData()+" exp: "+this.mExperience);
	};
	
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getPlayerID() {
		return mPlayerID;
	}
	
	public int getPlayerClass() {
		return mClass;
	}

	public void setPlayerClass(int mClass) {
		this.mClass = mClass;
	}
	
	public int getPositionID() {
		return mPositionID;
	}

	public void setPositionID(int mPositionID) {
		this.mPositionID = mPositionID;
	}

	public int getHeadID() {
		return mHeadID;
	}

	public void setHeadID(int mHeadID) {
		this.mHeadID = mHeadID;
	}
	
	public String getUserID() {
		return this.mUserID;
	}

	public void setUserID(String pUserID) {
		this.mUserID = pUserID;
	}

	public InventoryItemHelper getInventory() {
		return mInventory;
	}

	public void setInventory(InventoryItemHelper mInventory) {
		this.mInventory = mInventory;
	}

	public int getMoney() {
		return mMoney;
	}

	public void setMoney(int mMoney) {
		this.mMoney = mMoney;
	}

	public void addMoney(int mMoney) {
		this.mMoney += mMoney;
	}

	
	public int getExperience() {
		return mExperience;
	}

	public void setExperience(int mExperience) {
		this.mExperience = mExperience;
	} 

	public void addExperience(int mExperience) {
		this.mExperience += mExperience;
	} 
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public int getSpellattackid() {
		return spellattackid;
	}

	public void setSpellattackid(int spellattackid) {
		this.spellattackid = spellattackid;
	}
	

}
