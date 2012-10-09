package com.quest.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.quest.game.Game;

public class LoadingScene extends Scene {


	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mLoadingCircleTextureAtlas;
	private ITiledTextureRegion mLoadingCircleTextureRegion;
	private AnimatedSprite mLoadingCircle;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public LoadingScene() {
		// Load the loading ring
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/LoadingScene/");
		this.mLoadingCircleTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024, 128, TextureOptions.BILINEAR);
		this.mLoadingCircleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mLoadingCircleTextureAtlas, 
				Game.getInstance().getApplicationContext(), "LoadingCircle.png", 0, 0, 8, 1);
		
		// Load Texture into memory and on the screen
		this.mLoadingCircleTextureAtlas.load();
		
		// Create the Sprite
		this.mLoadingCircle = new AnimatedSprite((Game.getInstance().getWindowManager().getDefaultDisplay().getWidth() / 2) - (this.mLoadingCircleTextureRegion.getWidth() / 2), 
				(Game.getInstance().getWindowManager().getDefaultDisplay().getHeight() / 2) - (this.mLoadingCircleTextureRegion.getHeight() / 2), 
				this.mLoadingCircleTextureRegion, Game.getInstance().getEngine().getVertexBufferObjectManager());
		
		// Attach the ring and animate!
		this.attachChild(this.mLoadingCircle);
		this.mLoadingCircle.animate(50);
		
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the mLoadingCircleTextureAtlas
	 */
	public BitmapTextureAtlas getLoadingCircleTextureAtlas() {
		return mLoadingCircleTextureAtlas;
	}


	/**
	 * @param mLoadingCircleTextureAtlas the mLoadingCircleTextureAtlas to set
	 */
	public void setLoadingCircleTextureAtlas(BitmapTextureAtlas mLoadingCircleTextureAtlas) {
		this.mLoadingCircleTextureAtlas = mLoadingCircleTextureAtlas;
	}


	/**
	 * @return the mLoadingCircleTextureRegion
	 */
	public ITiledTextureRegion getLoadingCircleTextureRegion() {
		return mLoadingCircleTextureRegion;
	}


	/**
	 * @param mLoadingCircleTextureRegion the mLoadingCircleTextureRegion to set
	 */
	public void setLoadingCircleTextureRegion(
			ITiledTextureRegion mLoadingCircleTextureRegion) {
		this.mLoadingCircleTextureRegion = mLoadingCircleTextureRegion;
	}


	/**
	 * @return the mLoadingCircle
	 */
	public AnimatedSprite getLoadingCircle() {
		return mLoadingCircle;
	}


	/**
	 * @param mLoadingCircle the mLoadingCircle to set
	 */
	public void setLoadingCircle(AnimatedSprite mLoadingCircle) {
		this.mLoadingCircle = mLoadingCircle;
	}
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
