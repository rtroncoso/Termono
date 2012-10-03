package com.quest.entities;

import java.io.IOException;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.extension.tmx.TMXTile;

import com.quest.game.Game;
import com.quest.network.messages.client.ClientMessageMovePlayer;
import com.quest.triggers.Trigger;


public class Player extends BaseEntity implements IOnScreenControlListener, ITouchArea {


	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	

	// ===========================================================
	// Constructors
	// ===========================================================
	public Player(int pInitialPosX, int pInitialPosY, String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		// TODO Auto-generated constructor stub
		super(pInitialPosX, pInitialPosY, pTextureName, pFrameWidth, pFrameHeight, pFramePosX, pFramePosY, pCols, pRows);
		
		this.mEntityType = "Player";
	}

	// ===========================================================
	// Methods
	// ===========================================================
	
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {

		if(pValueX != 0.0f || pValueY != 0.0f) {
			if(!this.isWalking) {
				// Gets the new Tile
				float moveToXTile = this.getX() + (TILE_SIZE * pValueX);
				float moveToYTile = this.getY() + (TILE_SIZE * pValueY);

				final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
				
				long frameDuration = (long) ((1.0f / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };

				this.setAnimationDirection(this.getFacingDirectionToTile(tmxTileTo), frameDurations, false);
				
				// Check Tiles
				Trigger tmpTrigger = Game.getMapManager().checkTrigger(tmxTileTo);
				if(!tmpTrigger.equals(null)) tmpTrigger.onHandleTriggerAction(); // Hacer cambio de mapa
				if(Game.getMapManager().checkCollision(tmxTileTo)) return;
				
				// Perform Move
				this.moveToTile(tmxTileTo);
				try {
					Game.getClient().sendClientMessage(new ClientMessageMovePlayer(Game.getDataHandler().getUsername(1), this.getFacingDirectionToTile(tmxTileTo)));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	

}