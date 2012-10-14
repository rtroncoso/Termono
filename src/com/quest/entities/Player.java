package com.quest.entities;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;

import android.util.Log;

import com.quest.entities.objects.InventoryItem;
import com.quest.game.Game;
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
	// ===========================================================
	// Constructors
	// ===========================================================
	public Player(int pInitialPosX, int pInitialPosY, String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		// TODO Auto-generated constructor stub
		super(pTextureName, pFrameWidth, pFrameHeight, pFramePosX, pFramePosY, pCols, pRows);

		this.mEntityType = "Player";
	}
	
	public Player(int pPlayerID,int pClass) {
		// TODO Auto-generated constructor stub
		//aca lo ideal seria pasarle solo playerID que se ejecute lo de abajo y pasarle mClass al constructor, pero me cago en java que no me deja...
		//this.mClass = Game.getDataHandler().getPlayerClass(pPlayerID);
		super(Game.getDataHandler().getClassAnimationTexture(pClass), Game.getDataHandler().getClassFrameWidth(pClass), Game.getDataHandler().getClassFrameHeight(pClass), 0, 0, Game.getDataHandler().getClassAnimationCols(pClass), Game.getDataHandler().getClassAnimationRows(pClass));
		this.mPlayerID = pPlayerID;
		this.mClass = pClass;
		this.setLevel(Game.getDataHandler().getPlayerLevel(this.mPlayerID));
		this.setAttributes(Game.getDataHandler().getPlayerAttributes(this.mPlayerID));
		this.setModifiers(this.getAttributes());
		this.updateHPMana(Game.getDataHandler().getPlayerCurrentHPMP(this.mPlayerID));
		this.setHeadID(Game.getDataHandler().getPlayerHeadID(this.mPlayerID));
		this.mUserID = Game.getDataHandler().getUserID(Game.getDataHandler().getPlayerProfileID(this.mPlayerID));
		this.setInventory(LoadInventory(Game.getDataHandler().getInventoryItems(this.mPlayerID),Game.getDataHandler().getInventoryAmounts(this.mPlayerID),Game.getDataHandler().getInventoryEquipStatus(this.mPlayerID),Game.getDataHandler().getInventoryKeys(this.mPlayerID)));
		this.mEntityType = "Player";
	}
	
	
	public Player(String pUserID,int pPlayerID,int pClass,int pLevel,int[] pAttributes,int[] currHPMP,int pHeadID,int[] pItemIDs,int[] pAmounts,int[] isEquipped,int[] pItemKeys){//Creacion de lado cliente, el inventory se lodea por separado(y solo al player propio) cuando llega el mensaje con los valores.
		super(Game.getDataHandler().getClassAnimationTexture(pClass), Game.getDataHandler().getClassFrameWidth(pClass), Game.getDataHandler().getClassFrameHeight(pClass), 0, 0, Game.getDataHandler().getClassAnimationCols(pClass), Game.getDataHandler().getClassAnimationRows(pClass));
		this.mPlayerID = pPlayerID;
		this.mClass = pClass;
		this.mLevel = pLevel;
		this.mHeadID = pHeadID;
		this.mUserID = pUserID;
		this.setAttributes(pAttributes);
		this.setModifiers(pAttributes);
		this.updateHPMana(currHPMP);
		this.setInventory(LoadInventory(pItemIDs, pAmounts, isEquipped, pItemKeys));
		this.mEntityType = "Player";
	}

	// ===========================================================
	// Methods
	// ===========================================================
	private InventoryItemHelper LoadInventory(int[] pItemIDs,int[] pAmounts,int[] isEquipped,int[] pItemKeys){
		InventoryItemHelper pInventory = new InventoryItemHelper(mUserID);
		if(pItemIDs.length!=pItemKeys.length)Log.e("Quest!","InventoryHelper - Array leghts don't match");
		for(int i = 0;i<pItemKeys.length;i++){
			pInventory.addItem(new InventoryItem(pItemIDs[i], pAmounts[i], isEquipped[i]), pItemKeys[i]);
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
				// Gets the new Tile
				float moveToXTile = this.getX() + (TILE_SIZE * pValueX);
				float moveToYTile = this.getY() + (TILE_SIZE * pValueY);
				
				// Is it a legal position?
				if(!Game.getMapManager().isLegalPosition((int) moveToXTile, (int) moveToYTile)) return;

				// Get the new Tile
				final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
				
				// Animate the Character
				long frameDuration = (long) ((SPEED_MODIFIER / this.mSpeedFactor) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };
				this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), frameDurations, false);
				
				// Check Tiles
				Trigger tmpTrigger = Game.getMapManager().checkTrigger(tmxTileTo);
				if(tmpTrigger != null) { tmpTrigger.onHandleTriggerAction(); return; } // Hacer cambio de mapa
				
				// Perform Move
				this.moveToTile(tmxTileTo);
				/*try {
					Game.getClient().sendClientMessage(new ClientMessageMovePlayer(Game.getDataHandler().getUsername(1), this.getFacingDirectionToTile(tmxTileTo)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}
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

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	

}