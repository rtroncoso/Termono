package com.superproyecto.objects;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.superproyecto.game.Game;
import com.superproyecto.methods.Point;

public class Entity {
	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledTextureRegion;
	
	protected boolean isWalking;
	protected Game mGame;
	protected AnimatedSprite mAnimatedSprite;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Entity(Game pGame) {

		this.mGame = pGame;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public Entity loadTexture(String pPath, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY, int pCols, int pRows) {

		// Set base path for Textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		// Craete Texture Objects
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), pFrameWidth, pFrameHeight);
		this.mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this.mGame.getApplicationContext(), pPath, pFramePosX, pFramePosY, pCols, pRows);

		// Load Texture into memory and on the screen
		this.mGame.getTextureManager().loadTexture(this.mBitmapTextureAtlas);

		// Create the sprite and add it to the scene.
		this.mAnimatedSprite = new AnimatedSprite(0, 0, this.mTiledTextureRegion, this.mGame.getVertexBufferObjectManager());

		return this;
	}

	public Entity loadTexture(String pPath, int pFrameWidth, int pFrameHeight, int pFramePosX, int pFramePosY) {

		return this.loadTexture(pPath, pFrameWidth, pFrameHeight, pFramePosX,
				pFramePosY, 1, 1);
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
	
	public Point getPosition() {
		return new Point(this.mAnimatedSprite.getX(), this.mAnimatedSprite.getY());
	}
	
	public Entity setPosition(Point pNewPos) {
		this.mAnimatedSprite.setPosition(pNewPos.getX(), pNewPos.getY());
		return this;
	}

	public AnimatedSprite getAnimatedSprite() {
		return this.mAnimatedSprite;
	}

	public void setAnimatedSprite(AnimatedSprite pAnimatedSprite) {
		this.mAnimatedSprite = pAnimatedSprite;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
