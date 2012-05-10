package com.termono.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.termono.display.hud.MenuHud;
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
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public InventoryScene(Game pGame){
		this.mGame = pGame;
		this.mHud = new HUD();	
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/");
		this.mInventoryTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 1024,512, TextureOptions.BILINEAR);		
		this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, this.mGame.getApplicationContext(), "menu ingame.png", 0, 0);
		
	}
	
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
