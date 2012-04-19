package com.superproyecto.player;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;

import com.superproyecto.game.Game;
import com.superproyecto.objects.Entity;


public class Player extends Entity implements IOnScreenControlListener {


	// ===========================================================
	// Constants
	// ===========================================================
	private final float SPEED_MODIFIER = 6.0f;
	private final float TILE_WIDTH = 32.0f;
	private final float TILE_HEIGHT = 32.0f;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mWaypointIndex;
	private PathModifier mPathModifier;
	private MoveModifier mMoveModifier;
	private Path mPath;
	
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

		this.mAnimatedSprite.unregisterEntityModifier(this.mMoveModifier);
		
		
		this.mMoveModifier = new MoveModifier((pSpeed / SPEED_MODIFIER), this.mAnimatedSprite.getX(), pToTileX, this.mAnimatedSprite.getY(), pToTileY, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				Player.this.isWalking = true;

				// DERECHA
				if(Player.this.mAnimatedSprite.getX() - pToTileX < 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 6, 8, false);
				// IZQUIERDA
				if(Player.this.mAnimatedSprite.getX() - pToTileX > 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 3, 5, false);
				// ABAJO
				if(Player.this.mAnimatedSprite.getY() - pToTileY < 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 0, 2, false);
				// ARRIBA
				if(Player.this.mAnimatedSprite.getY() - pToTileY > 0) 
					Player.this.mAnimatedSprite.animate(new long[]{100, 100, 100}, 9, 11, false);
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				Player.this.isWalking = false;
				Player.this.mAnimatedSprite.stopAnimation();
				
			}
		});
		
		this.mAnimatedSprite.registerEntityModifier(this.mMoveModifier);
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
				
				final float[] pToTiles = this.mGame.getScene().convertLocalToSceneCoordinates(moveToXTile, moveToYTile);
				
				// Moves to it
				this.moveToTile(pToTiles[Constants.VERTEX_INDEX_X], pToTiles[Constants.VERTEX_INDEX_Y], 1.0f);
			}

		}
	}

	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public PathModifier getPathModifier() {
		return mPathModifier;
	}

	public void setPathModifier(PathModifier pPathModifier) {
		this.mPathModifier = pPathModifier;
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	

}
