package com.termono.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.termono.game.Game;

public class MenuHud extends HUD{

		// ===========================================================
		// Constants
		// ===========================================================
	
		// ===========================================================
		// Fields
		// ===========================================================
		private Game mGame;
		private BitmapTextureAtlas mMenuTextureAtlas;
		private ITextureRegion mMenuTextureRegion;
		private Sprite mMenuSprite;

	
		// ===========================================================
		// Constructor
		// ===========================================================
		public MenuHud(Game pGame){
			//Init local Variables
			this.mGame = pGame;
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			
			// Create Texture objects
			this.mMenuTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 128, 64, TextureOptions.BILINEAR);
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "menu1.png", 0, 0);

			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
			
			float posX = this.mGame.getDisplay().getCameraWidth() - this.mMenuTextureRegion.getWidth();
			
			this.mMenuSprite = new Sprite(posX, 0, this.mMenuTextureRegion, this.mGame.getVertexBufferObjectManager());
			
			//this.mMenuSprite.setAlpha(pAlpha);
		
		
		
		}


		
		// ===========================================================
		// Methods
		// ===========================================================
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public Sprite getMenuSprite() {
			return mMenuSprite;
		}
	
	
}
