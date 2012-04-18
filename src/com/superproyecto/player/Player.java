package com.superproyecto.player;

import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.util.Constants;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseLinear;

import com.superproyecto.game.Game;
import com.superproyecto.objects.Entity;


public class Player extends Entity implements IOnScreenControlListener {


	// ===========================================================
	// Constants
	// ===========================================================
	private final float mSpeedModifier = 7.0f;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mWaypointIndex;
	private PathModifier mPathModifier;
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
	public void moveToTile(float pToTileX, float pToTileY) {

		this.mAnimatedSprite.unregisterEntityModifier(this.mPathModifier);
		
		// Creates a path to that tile
		this.mPath = new Path(2).to(this.mAnimatedSprite.getX(), this.mAnimatedSprite.getY()).to(pToTileX,pToTileY);

		this.mPathModifier = new PathModifier((1.0f / mSpeedModifier), this.mPath, null, new IPathModifierListener() {
			@Override
			public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
				Player.this.isWalking = true;
				Debug.d("onPathStarted");
			}
	
			@Override
			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointStarted: " + pWaypointIndex);
				Player.this.mWaypointIndex = pWaypointIndex;
				switch(pWaypointIndex) {
				case 0:
					Player.this.mAnimatedSprite.animate(new long[]{200, 200, 200}, 6, 8, true);
					break;
				case 1:
					Player.this.mAnimatedSprite.animate(new long[]{200, 200, 200}, 3, 5, true);
					break;
				case 2:
					Player.this.mAnimatedSprite.animate(new long[]{200, 200, 200}, 0, 2, true);
					break;
				case 3:
					Player.this.mAnimatedSprite.animate(new long[]{200, 200, 200}, 9, 11, true);
					break;
				}
			}
	
			@Override
			public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				Debug.d("onPathWaypointFinished: " + pWaypointIndex);
				Player.this.isWalking = false;
			}
	
			@Override
			public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
				Player.this.isWalking = false;
				//Player.this.mAnimatedSprite.stopAnimation();
				Player.this.mPath = null;
				Debug.d("onPathFinished");
			}
		}, EaseLinear.getInstance());

		this.mAnimatedSprite.registerEntityModifier(this.mPathModifier);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {
		if(pValueX == 0.0f && pValueY == 0.0f) return;
		if(this.mPath != null);
		
		float moveToXTile = this.mAnimatedSprite.getX() + (32 * pValueX);
		float moveToYTile = this.mAnimatedSprite.getY() + (32 * pValueY);
		
		final float[] pToTiles = this.mGame.getScene().convertLocalToSceneCoordinates(moveToXTile, moveToYTile);

		// Gets where to go
		TMXTile tmxTilePlayerTo = this.mGame.getTMXTiledMap().getTMXLayers().get(0).getTMXTileAt(pToTiles[Constants.VERTEX_INDEX_X], pToTiles[Constants.VERTEX_INDEX_Y]);
		
		this.moveToTile(pToTiles[Constants.VERTEX_INDEX_X], pToTiles[Constants.VERTEX_INDEX_Y]);

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
