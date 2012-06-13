package com.quest.entities;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;

import com.quest.game.Game;
import com.quest.interfaces.IMeasureConstants;

public class BaseEntity extends org.andengine.entity.Entity implements IMeasureConstants {
	// ===========================================================
	// Constants
	// ===========================================================
	protected final float SPEED_MODIFIER = 5.0f;
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledTextureRegion;
	private PathModifier mPathModifier;
	private Path mPath;
	
	protected boolean isWalkingputo;
	protected Game mGame;
	protected AnimatedSprite mBodySprite;

	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseEntity(Game pGame) {

		this.mGame = pGame;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public BaseEntity load(String pTexturePath, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {

		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this.mGame.getApplicationContext(), pTexturePath, pFramePosX, pFramePosY, pCols, pRows);

		// Load Texture into memory and on the screen
		this.mBitmapTextureAtlas.load();

		// Create the sprite and add it to the scene.
		this.mBodySprite = new AnimatedSprite(0, 0, this.mTiledTextureRegion, this.mGame.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				return BaseEntity.this.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		
		this.attachChild(this.mBodySprite);
		
		return this;
	}
	
	public BaseEntity load(String pPath, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY) {

		return this.load(pPath, pFrameWidth, pFrameHeight, pFramePosX,
				pFramePosY, 1, 1);
	}

	public BaseEntity moveToTile(final float pToTileX, final float pToTileY, final float pSpeed) {
		
		this.mPath = new Path(2).to(this.getX(), this.getY()).to(pToTileX, pToTileY);
		
		this.mPathModifier = new PathModifier(pSpeed / SPEED_MODIFIER, this.mPath, new IPathModifierListener() {

			@Override
			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				BaseEntity.this.isWalking = true;
				
				long frameDuration = (long) ((pSpeed / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };
				
				// RIGHT
				if(BaseEntity.this.getX() - pToTileX < 0)
					BaseEntity.this.mBodySprite.animate(frameDurations, 8, 11, false);
				// LEFT
				if(BaseEntity.this.getX() - pToTileX > 0)
					BaseEntity.this.mBodySprite.animate(frameDurations, 4, 7, false);
				// DOWN
				if(BaseEntity.this.getY() - pToTileY < 0)
					BaseEntity.this.mBodySprite.animate(frameDurations, 0, 3, false);
				// UP
				if(BaseEntity.this.getY() - pToTileY > 0)
					BaseEntity.this.mBodySprite.animate(frameDurations, 12, 15, false);
				
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
				
				BaseEntity.this.isWalking = false;
				//Player.this.mAnimatedSprite.stopAnimation();
				
			}	
		}, EaseLinear.getInstance());
		
		this.registerEntityModifier(this.mPathModifier);
		
		return this;
	}
	
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		return true;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public BitmapTextureAtlas getBitmapTextureAtlas() {
		return this.mBitmapTextureAtlas;
	}

	public TiledTextureRegion getTiledTextureRegion() {
		return this.mTiledTextureRegion;
	}

	public AnimatedSprite getAnimatedSprite() {
		return this.mBodySprite;
	}

	public void setAnimatedSprite(AnimatedSprite pAnimatedSprite) {
		this.mBodySprite = pAnimatedSprite;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
