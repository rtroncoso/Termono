package com.termono.scenes;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.termono.display.Display;
import com.termono.game.Game;

public class InventoryScene extends Scene {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private HUD mHud;
	private BitmapTextureAtlas mInventoryTextureAtlas;
	private ITextureRegion mInventoryTextureRegion;
	private Sprite mInventorySprite;
	private Entity mInventoryEntity;
	private Display mDisplay;
		
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryScene(Game pGame){
		this.mGame = pGame;
		
		//this.mDisplay = new Display(800, 480, 800, 480);
		//this.mGame.setDisplay(mDisplay);
		
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/Inventory/");
		this.mInventoryTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 1024,512, TextureOptions.BILINEAR);		
		this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, this.mGame.getApplicationContext(), "inventory.png", 0, 0);
		this.mInventoryTextureAtlas.load();
		
		
		this.mInventorySprite = new Sprite(0, 0, this.mInventoryTextureRegion, this.mGame.getVertexBufferObjectManager()) {		};
    	//this.mGame.getDisplay().getCamera().setCenter(this.mInventoryTextureRegion.getWidth() / 2, this.mInventoryTextureRegion.getHeight() / 2);
		this.attachChild(mInventorySprite);
	}

	
	
	
	
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the mInventoryEntity
	 */
	public Sprite getInventorySprite() {
		return mInventorySprite;
	}

	/**
	 * @param mInventoryEntity the mInventoryEntity to set
	 */
	public void setInventorySprite(Sprite mInventorySprite) {
		this.mInventorySprite = mInventorySprite;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
