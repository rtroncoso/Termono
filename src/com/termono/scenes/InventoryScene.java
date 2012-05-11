package com.termono.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.termono.display.Display;
import com.termono.game.Game;
import com.termono.helpers.SceneManager;

public class InventoryScene extends Scene {
	// ===========================================================
	// Constants
	// ===========================================================
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private HUD mHud;
	private SceneManager mSceneManager;
	private Entity mInventoryEntity;
	private Display mDisplay;
	
	//Textures y Sprites
	private BitmapTextureAtlas mInventoryTextureAtlas;
	
	private ITextureRegion mInventoryTextureRegion;
	private ITextureRegion mCloseTextureRegion;
	
	private Sprite mInventorySprite;
	private Sprite mCloseSprite;
		
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryScene(Game pGame){
		this.mGame = pGame;
		this.mSceneManager = new SceneManager(mGame);
		//this.mDisplay = new Display(CAMERA_WIDTH, CAMERA_HEIGHT, this.mGame.getWindowManager().getDefaultDisplay().getWidth(),
	//			this.mGame.getWindowManager().getDefaultDisplay().getHeight());
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/Inventory/");
		this.mInventoryTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 1024,1024, TextureOptions.BILINEAR);		
		this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, this.mGame.getApplicationContext(), "inventory.png", 0, 0);
		this.mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, this.mGame.getApplicationContext(), "cruz.png", 0, 480);
		
		this.mInventoryTextureAtlas.load();
		
		
		
		//Fondo principal
		this.mInventorySprite = new Sprite(0, 0, this.mInventoryTextureRegion, this.mGame.getVertexBufferObjectManager()) {		};
		this.attachChild(mInventorySprite);
		
		this.mCloseSprite = new Sprite(InventoryScene.this.mInventorySprite.getWidth() - 120, 40,this.mCloseTextureRegion,this.mGame.getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				break;
			case TouchEvent.ACTION_MOVE:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_UP:
				InventoryScene.this.mSceneManager.setGameScene();
				break;
			}
			return true;
			}
			
		};
		
		
		
		
		
		
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
