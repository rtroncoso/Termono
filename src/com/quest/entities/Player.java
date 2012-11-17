package com.quest.entities;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.objects.Item;
import com.quest.game.Game;
import com.quest.helpers.InventoryItemHelper;
import com.quest.timers.Timer;


public class Player extends BaseEntity implements IOnScreenControlListener, ITouchArea, GameFlags {


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
	private int mUnassignedPoints;
	private int Attack_Flag;
	private Entity mEquippedWeaponsLayer;
	
	private boolean mGrabbed = false;
	private int mCurrentTarget;

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
		int[] tAttributes =Game.getDataHandler().getPlayerAttributes(this.mPlayerID); 
		this.setAttributes(tAttributes);
		this.setUnassignedPoints(tAttributes[4]);
		this.setModifiers(new int[]{0,0,0,0});
		this.updateHPMana(Game.getDataHandler().getPlayerCurrentHPMP(this.mPlayerID));
		this.setHeadID(Game.getDataHandler().getPlayerHeadID(this.mPlayerID));
		this.mUserID = pUserID;
		this.mExperience = Game.getDataHandler().getPlayerExperience(this.mPlayerID);
		this.mMoney = Game.getDataHandler().getPlayerMoney(this.mPlayerID);
		this.setInventory(LoadInventory(Game.getDataHandler().getInventoryItems(this.mPlayerID),Game.getDataHandler().getInventoryAmounts(this.mPlayerID),Game.getDataHandler().getInventoryEquipStatus(this.mPlayerID)));
		setAttack_Flag(FLAG_ATTACK_SPELL_FIREBALL);
		this.setCurrentMap(Game.getDataHandler().getPlayerCurrentMap(this.mPlayerID));
		this.setCoords(Game.getDataHandler().getPlayerPosition(this.mPlayerID));
		this.mEntityType = "Player";
		
		this.mEquippedWeaponsLayer = new Entity();
	}
	
	
	public Player(String pUserID,int pPlayerID,int pClass,int pLevel,float pExperience, int pMoney,int[] pAttributes,int[] currHPMP,int pHeadID,int[] pItemIDs,int[] pAmounts,int[] isEquipped,int pMapID,int pTIleX, int pTileY){//Creacion de lado cliente, el inventory se lodea por separado(y solo al player propio) cuando llega el mensaje con los valores.
		super(Game.getDataHandler().getClassAnimationTexture(pClass), Game.getDataHandler().getClassFrameWidth(pClass), Game.getDataHandler().getClassFrameHeight(pClass), 0, 0, Game.getDataHandler().getClassAnimationCols(pClass), Game.getDataHandler().getClassAnimationRows(pClass));
		this.mPlayerID = pPlayerID;
		this.mClass = pClass;
		this.mLevel = pLevel;
		this.mHeadID = pHeadID;
		this.mExperience = pExperience;
		this.mMoney = pMoney;
		this.mUserID = pUserID;
		this.setAttributes(pAttributes);
		this.setModifiers(pAttributes);
		this.updateHPMana(currHPMP);
		this.setInventory(LoadInventory(pItemIDs, pAmounts, isEquipped));
		setAttack_Flag(FLAG_ATTACK_SPELL_FIREBALL);
		this.setCurrentMap(pMapID);
		this.setCoords(pTIleX, pTileY);
		this.mEntityType = "Player";
	}

	// ===========================================================
	// Methods
	// ===========================================================
	private InventoryItemHelper LoadInventory(int[] pItemIDs,int[] pAmounts,int[] isEquipped){
		InventoryItemHelper pInventory = new InventoryItemHelper(Player.this);
		for(int i = 0;i<pItemIDs.length;i++){
			pInventory.addItem(Game.getItemHelper().addNewItem(pItemIDs[i], pAmounts[i]), isEquipped[i]);
		}
		return pInventory;
	}
	
	public void attachWeapon(int pItemID) {
		
	}
	
	public void startRecoveryTimer(){
			Game.getTimerHelper().addTimer(new Timer(0.2f, new ITimerCallback() 
			{	
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) 
				{
					if(Player.this.getCurrHP()<Player.this.getModHP())Player.this.recoverHP();
					if(Player.this.getCurrMana()<Player.this.getModMana())Player.this.recoverMP();
				}
			}), mUserID);		
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
				
				// Store the new Tile
				final TMXTile tmpNewTile = this.moveInDirection(pDirection);
				if(tmpNewTile == null) return;
				
				// Sends the move
				if(!Game.isServer()){
					Game.getClient().sendMovePlayerMessage(this.mUserID, tmpNewTile.getTileColumn(), tmpNewTile.getTileRow());
				}else{
					Game.getServer().sendUpdateEntityPositionMessage(this.mUserID, tmpNewTile.getTileColumn(), tmpNewTile.getTileRow());
					this.moveToTile(tmpNewTile);
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
		this.mAttackLayer.add(Game.getAttacksHelper().addNewAttack(pAttackID));
	};
	
	@Override
	public void onAttackAction(BaseEntity pAttackedEntity, int pAttackID) {
		if(Game.getAttacksHelper().canAttack(Player.this, pAttackID))
		Game.getBattleHelper().startAttack(this, pAttackID, pAttackedEntity);
	};
	
	
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		switch(pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			mGrabbed = true;					
			break;
		case TouchEvent.ACTION_UP:
			
				Log.d("Quest!", "player touched");
				mGrabbed = false;

			break;
		}
	
		return true;
	}
	

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

	public void addMoney(int pMoney) {
		popOverHead(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_YELLOW, this.getBodySprite().getX(), this.getBodySprite().getY(),String.valueOf(pMoney), "Money;"+this.getUserData()+" "+System.currentTimeMillis()), 1f);
		this.mMoney += pMoney;
	}

	
	public float getExperience() {
		return mExperience;
	}

	public void setExperience(int mExperience) {
		this.mExperience = mExperience;
	} 

	public void addExperience(float pExperience) {
		popOverHead(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_HEALING, this.getBodySprite().getX(), this.getBodySprite().getY(),String.valueOf(pExperience), "Exp;"+this.getUserData()+" "+System.currentTimeMillis()), 1f);
		this.mExperience += pExperience;
		int oldlvl = this.mLevel;
		this.mLevel = Game.getLevelHelper().getPlayerLevel(mExperience);
		if(mLevel != oldlvl && Game.isServer())levelUP_Server();
	} 
	
	public void recoverHP(){
		this.currHP+=(float)(this.mEndurance)/10;
	}
	
	public void recoverMP(){
		this.currMana+=(float)(this.mIntelligence)/10;
	}
	
	public void levelUP_Server(){
			this.setUnassignedPoints(getUnassignedPoints()+3);
			Game.getServer().sendMessagePlayerLevelUP(this.getUserID(), this.mLevel, this.getUnassignedPoints());
			this.popOverHead(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_HEALING, 0, 0, "LEVEL UP!", "lvlup"), 1.5f);
			Game.getQueryQueuer().addPlayerLevelUPQuery(this.mPlayerID, this.mLevel,this.getUnassignedPoints(), this.mExperience);
	}
		
	public void levelUP_Client(int pLevel, int pUnassignedPoints){
		this.popOverHead(Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_HEALING, 0, 0, "LEVEL UP!", "lvlup"), 1.5f);
		this.setLevel(pLevel);
		this.setUnassignedPoints(pUnassignedPoints);
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public int getUnassignedPoints() {
		return mUnassignedPoints;
	}

	public void setUnassignedPoints(int mUnassignedPoints) {
		this.mUnassignedPoints = mUnassignedPoints;
	}
	
	public int getAttack_Flag() {
		return Attack_Flag;
	}

	public void setAttack_Flag(int pAttack_Flag) {
		this.Attack_Flag = pAttack_Flag;
	}

	public int getCurrentTarget() {
		return mCurrentTarget;
	}

	public void setCurrentTarget(int mCurrentTarget) {
		this.mCurrentTarget = mCurrentTarget;
	}
	

}
