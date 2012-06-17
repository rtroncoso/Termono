package com.quest.entities;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.extension.tmx.TMXTile;

import com.quest.game.Game;


public class Player extends BaseEntity implements IOnScreenControlListener {


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

				final TMXTile tmxTileAt = Game.getMapManager().getTMXTileAt(moveToXTile, moveToYTile);
				
				// Moves to it if not blocked
				if(!Game.getMapManager().collisionCheck(tmxTileAt)) this.moveToTile(moveToXTile, moveToYTile, 1.0f);
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
