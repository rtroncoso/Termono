package com.quest.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.display.hud.StatsHud;
import com.quest.game.Game;
import com.quest.helpers.SceneHelper;

public class InventoryScene extends Scene {
	// ===========================================================
	// Constants
	// ===========================================================
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	
	//Textures y Sprites
	private BitmapTextureAtlas mInventoryTextureAtlas;
	
	private ITextureRegion mInventoryTextureRegion;
	private ITextureRegion mCloseTextureRegion;
	private ITextureRegion mArmasTextureRegion;
	private ITextureRegion mConsumiblesTextureRegion;
	
	private Sprite mInventorySprite;
	private Sprite mCloseSprite;
	private Sprite mArmasSprite;
	private Sprite mConsumiblesSprite;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryScene(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/Inventory/");
		this.mInventoryTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);		
		this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "inventory.png", 0, 0);
		this.mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Close.png", 0, 480);
		this.mArmasTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "BtnArmas.png", 100, 480);
		this.mConsumiblesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "BtnConsumibles.png", 200, 480);
		
		this.mInventoryTextureAtlas.load();
		
		//Fondo principal
		this.mInventorySprite = new Sprite(0, 0, this.mInventoryTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {		};
		this.attachChild(mInventorySprite);	
		
		this.mCloseSprite = new Sprite(InventoryScene.this.mInventorySprite.getWidth() - 45, 10,this.mCloseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_UP:
				Game.getSceneManager().setGameScene();
				break;
			}
			return true;
			}
			
		};
		
		this.mArmasSprite = new Sprite(50, 75,this.mArmasTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				break;
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};
		
		
		this.mConsumiblesSprite = new Sprite(150, 75,this.mConsumiblesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_OUTSIDE:
				break;
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};
		
		
		
		
		this.attachChild(mCloseSprite);
		this.attachChild(mArmasSprite);
		this.attachChild(mConsumiblesSprite);
		
		
		this.registerTouchArea(this.mCloseSprite);
		this.registerTouchArea(this.mArmasSprite);
		this.registerTouchArea(this.mConsumiblesSprite);
		
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
