package com.superproyecto.player;

import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.util.constants.Constants;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.EaseLinear;

import android.content.Context;

import com.superproyecto.game.Control;
import com.superproyecto.game.Game;
import com.superproyecto.objects.Entity;


public class Player extends Entity implements IOnScreenControlListener {

	/*
	 * PRIVATE FIELDS
	 */
	private final float mSpeedModifier = 300.0f;
	private Control mControl;
	private TMXLayer mTMXLayer;
	private Path mPath;
	private PathModifier mPathModifier;
	private int mWaypointIndex;
	
	/*
	 * CONSTRUCTORS
	 */
	public Player(Game pEngine, TMXLayer pTMXLayer) {
		super(pEngine);
		this.mTMXLayer = pTMXLayer;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
			float pValueX, float pValueY) {
		// TODO Auto-generated method stub

		float moveToXTile = this.mPosition.getX() * 32 * pValueX;
		float moveToYTile = this.mPosition.getY() * 32 * pValueY;

		// If the user is touching the screen Puts the touch events into an array
		final float[] pToTiles = this.mGame.getScene().convertLocalToSceneCoordinates(moveToXTile, moveToYTile);

		// Gets where to go
		TMXTile tmxTilePlayerTo = this.mTMXLayer.getTMXTileAt(pToTiles[Constants.VERTEX_INDEX_X],
				pToTiles[Constants.VERTEX_INDEX_Y]);
		
		// Creates a path to that tile
		this.mPath = new Path(2).to(this.mPosition.getX(), this.mPosition.getY())
								.to(225, 144);
		//.to(tmxTilePlayerTo.getTileX(), tmxTilePlayerTo.getTileY());

		this.mPathModifier = new PathModifier(100, this.mPath, new IEntityModifierListener() {

			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

			}

			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

			}
		}, new PathModifier.IPathModifierListener() {

			public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity,
					int pWaypointIndex) {
				
				// Keep the waypointIndex in a Global Var
				Player.this.mWaypointIndex = pWaypointIndex;

			}

			public void onPathWaypointFinished(PathModifier pPathModifier, IEntity pEntity, int pWaypointIndex) {
			}

			public void onPathStarted(PathModifier pPathModifier, IEntity pEntity) {
				// Set a global var
				Player.this.isWalking = true;
			}

			public void onPathFinished(PathModifier pPathModifier, IEntity pEntity) {
				// Stop walking and set A_path to null
				Player.this.isWalking = false;
				Player.this.mPath = null;
				Player.this.mAnimatedSprite.stopAnimation();
			}

		}, EaseLinear.getInstance());

		this.mAnimatedSprite.registerEntityModifier(this.mPathModifier);
	}
	
}
