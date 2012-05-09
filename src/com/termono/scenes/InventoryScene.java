package com.termono.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
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
	private BitmapTextureAtlas mInvetoryTextureAtlas;
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
