package com.superproyecto.player;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.util.modifier.ease.EaseLinear;

import com.superproyecto.game.Game;
import com.superproyecto.objects.Entity;


public class Player extends Entity implements IOnScreenControlListener {


	// ===========================================================
	// Constants
	// ===========================================================
	private final float SPEED_MODIFIER = 3.0f;
	private final float TILE_WIDTH = 32;
	private final float TILE_HEIGHT = 32;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private MoveModifier mMoveModifier;
	
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
	public void moveToTile(final float pToTileX, final float pToTileY, float pSpeed) {
		
		final Path pathToNextTile = new Path(2).to(this.mAnimatedSprite.getX(), this.mAnimatedSprite.getY()).to(pToTileX, pToTileY);
		
		final PathModifier pm = new PathModifier(0.15f, pathToNextTile, new IPathModifierListener() {

			@Override
			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				Player.this.isWalking = true;

				// RIGHT
				if(Player.this.mAnimatedSprite.getX() - pToTileX < 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 6, 8, false);
				// LEFT
				if(Player.this.mAnimatedSprite.getX() - pToTileX > 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 3, 5, false);
				// DOWN
				if(Player.this.mAnimatedSprite.getY() - pToTileY < 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 0, 2, false);
				// UP
				if(Player.this.mAnimatedSprite.getY() - pToTileY > 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 9, 11, false);
				
			}

			@Override
			public void onPathWaypointStarted(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPathWaypointFinished(PathModifier pPathModifier,
					IEntity pEntity, int pWaypointIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPathFinished(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				
				Player.this.isWalking = false;
				//Player.this.mAnimatedSprite.stopAnimation();
				
			}	
		}, EaseLinear.getInstance());
		
		this.mAnimatedSprite.registerEntityModifier(pm);
	}

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
