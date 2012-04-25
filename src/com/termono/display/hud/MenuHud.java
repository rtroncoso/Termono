package com.termono.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
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
		private BitmapTextureAtlas mMenu1TextureAtlas;
		private ITextureRegion mMenu1TextureRegion;
		private Sprite mMenu1Sprite;
		private BitmapTextureAtlas mMenu2TextureAtlas;
		private ITextureRegion mMenu2TextureRegion;
		private Sprite mMenu2Sprite;
		private HUD mHud;
	
		
		
		// ===========================================================
		// Constructor
		// ===========================================================
		public MenuHud(Game pGame, HUD pHud){
			//Init local Variables
			this.mGame = pGame;
			this.mHud = pHud;
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/");
			
			// Create Texture objects
			this.mMenu1TextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 128, 64, TextureOptions.BILINEAR);
			this.mMenu1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenu1TextureAtlas, this.mGame.getApplicationContext(), "menu1.png", 0, 0);

			this.mMenu2TextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
			this.mMenu2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenu2TextureAtlas, this.mGame.getApplicationContext(), "menu2.png", 0, 0);			
			// Load Texture into memory and on the screen
			this.mMenu1TextureAtlas.load();
			
			float posX = this.mGame.getDisplay().getCameraWidth() - this.mMenu1TextureRegion.getWidth();
			
			this.mMenu1Sprite = new Sprite(posX, 0, this.mMenu1TextureRegion, this.mGame.getVertexBufferObjectManager()){
			
			//this.mMenuSprite.setAlpha(pAlpha);
			
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							this.setScale(2.0f);
							
							
							MenuHud.this.mMenu2TextureAtlas.load();
							MenuHud.this.mMenu2Sprite = new Sprite(0, 0, MenuHud.this.mMenu2TextureRegion, MenuHud.this.getMenuGame().getVertexBufferObjectManager());
							
							
									break;
						case TouchEvent.ACTION_MOVE:
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							this.setScale(1.0f);
							break;
					}
					return true;
				}
			};
			this.mHud.registerTouchArea(this.mMenu1Sprite);
		//	this.mMenuSprite.setAlpha(0.6f);
		//	this.mMenuSprite.setScale(1.0f); 
			
			
			
		}


		
		// ===========================================================
		// Methods
		// ===========================================================
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public Sprite getMenuSprite() {
			return mMenu1Sprite;
		}
		public Sprite getMenu2Sprite() {
			return mMenu2Sprite;
		}
	
		public Game getMenuGame() {
			return mGame;
		}
	
		
}
