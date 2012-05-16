package com.quest.entities;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;

import com.quest.game.Game;


public class Player extends Entity implements IOnScreenControlListener {


	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	

	// ===========================================================
	// Constructors
	// ===========================================================
	public Player(Game pEngine) {
		super(pEngine);
		
		// TODO Auto-generated constructor stub
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
				float moveToXTile = this.mAnimatedSprite.getX() + (TILE_WIDTH * pValueX);
				float moveToYTile = this.mAnimatedSprite.getY() + (TILE_HEIGHT * pValueY);
				
				// Moves to it
				this.moveToTile(moveToXTile, moveToYTile, 1.0f);
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
