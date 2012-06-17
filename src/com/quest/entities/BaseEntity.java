package com.quest.entities;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.ease.EaseLinear;

import android.util.Log;

import com.quest.game.Game;
import com.quest.interfaces.IMeasureConstants;

public class BaseEntity extends Entity implements IMeasureConstants, IOnAreaTouchListener {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledTextureRegion;
	private PathModifier mPathModifier;
	private Path mPath;
	
	protected String mEntityType;
	protected AnimatedSprite mBodySprite;
	protected TMXTile mTMXTileAt;
	protected boolean isWalking;
	

	// ===========================================================
	// Constructors
	// ===========================================================
	public BaseEntity(int pInitialPosX, int pInitialPosY, String pTextureName, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {
		this.mEntityType = "BaseEntity";

		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, Game.getInstance().getApplicationContext(), pTextureName, pFramePosX, pFramePosY, pCols, pRows);

		// Load Texture into memory and on the screen
		this.mBitmapTextureAtlas.load();

		// Create the sprite and add it to the scene.
		this.mBodySprite = new AnimatedSprite(0, 0, this.mTiledTextureRegion, Game.getInstance().getVertexBufferObjectManager());
		
		this.attachChild(this.mBodySprite);
		
		this.setTileAt(pInitialPosX, pInitialPosY);
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public BaseEntity moveToTile(final float pToTileX, final float pToTileY, final float pSpeed) {
		
		// get which tile are we going to
		final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(pToTileX, pToTileY);
		final TMXTile tmxTileAt = Game.getMapManager().getTMXTileAt(this.getX(), this.getY());
		
		// Unblock our current Tile and block our new one
		Game.getMapManager().unregisterCollisionTile(tmxTileAt);
		Game.getMapManager().registerCollisionTile(tmxTileTo);

		this.mPath = new Path(2).to(this.getX(), this.getY()).to(pToTileX, pToTileY);

		this.mPathModifier = new PathModifier(pSpeed / SPEED_MODIFIER, this.mPath, new IPathModifierListener() {

			@Override
			public void onPathStarted(PathModifier pPathModifier,
					IEntity pEntity) {
				// TODO Auto-generated method stub
				
				long frameDuration = (long) ((1.5f / SPEED_MODIFIER) * 1000) / 4;
				long[] frameDurations = { frameDuration, frameDuration, frameDuration, frameDuration };

				// RIGHT
				if(BaseEntity.this.getX() - pToTileX < 0)
					if(!BaseEntity.this.mBodySprite.isAnimationRunning()) BaseEntity.this.mBodySprite.animate(frameDurations, 8, 11, true);
				// LEFT
				if(BaseEntity.this.getX() - pToTileX > 0)
					if(!BaseEntity.this.mBodySprite.isAnimationRunning()) BaseEntity.this.mBodySprite.animate(frameDurations, 4, 7, true);
				// DOWN
				if(BaseEntity.this.getY() - pToTileY < 0)
					if(!BaseEntity.this.mBodySprite.isAnimationRunning()) BaseEntity.this.mBodySprite.animate(frameDurations, 0, 3, true);
				// UP
				if(BaseEntity.this.getY() - pToTileY > 0)
					if(!BaseEntity.this.mBodySprite.isAnimationRunning()) BaseEntity.this.mBodySprite.animate(frameDurations, 12, 15, true);
				
				BaseEntity.this.isWalking = true;
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
				BaseEntity.this.mBodySprite.stopAnimation();

			}	
		}, EaseLinear.getInstance());

		this.registerEntityModifier(this.mPathModifier);

		return this;
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

	public AnimatedSprite getBodySprite() {
		return this.mBodySprite;
	}

	public void setBodySprite(AnimatedSprite pBodySprite) {
		this.mBodySprite = pBodySprite;
	}

	public String getEntityType() {
		// TODO Auto-generated method stub
		return this.mEntityType;
	}
	public void setEntityType(String pEntityType) {
		// TODO Auto-generated method stub
		this.mEntityType = pEntityType;
	}
	
	public void setTileAt(int tileX, int tileY) {
		
		final TMXTile tmpTMXTile = Game.getMapManager().getCurrentMap().getTMXLayers().get(0).getTMXTile(tileX, tileY);

		final float pNewX = (this.mBodySprite.getWidth() > 32) ? tmpTMXTile.getTileX() - (this.mBodySprite.getWidth() - 32) : tmpTMXTile.getTileX();
		final float pNewY = (this.mBodySprite.getHeight() > 32) ? tmpTMXTile.getTileY() - (this.mBodySprite.getHeight() - 32) : tmpTMXTile.getTileY();

		this.setPosition(pNewX, pNewY);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		Log.d("TOUCH", "En entidad");
		return false;
	}
	
	@Override
	public void setPosition(float pX, float pY) {
		// TODO Auto-generated method stub
		super.setPosition(pX, pY);
		
		//this.mTMXTileAt = Game.getSceneManager().getGameScene().getMapManager().getTMXTileAt((int) pX, (int) pY);
		
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
