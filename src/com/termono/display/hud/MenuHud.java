package com.termono.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
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
		private BitmapTextureAtlas mMenuTextureAtlas;
		private ITextureRegion mMenuTextureRegion;
		private Sprite mMenuSprite;
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
			this.mMenuTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 512, 512, TextureOptions.BILINEAR);		
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "menu.png", 0, 0);
			
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
			
			float posX = this.mGame.getDisplay().getCamera().getWidth()-96;
			float posY = -236;
			this.mMenuSprite = new Sprite(posX, posY, this.mMenuTextureRegion, this.mGame.getVertexBufferObjectManager()) {
			
		
					
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							MenuHud.this.mMenuSprite.setPosition(MenuHud.this.mMenuSprite.getInitialX(), MenuHud.this.mMenuSprite.getInitialY());
							MenuHud.this.mMenuSprite.setAlpha(1.0f);
							/*
							
							MenuHud.this.mGame.getScene().registerUpdateHandler(new IUpdateHandler() 
							{
								@Override
								public void onUpdate(float pSecondsElapsed)
								 {
									for(float i=MenuHud.this.mMenuSprite.getInitialX(); i<500; i++)
										{
									
									
										}
									}
								@Override
								public void reset() {
									// TODO Auto-generated method stub			
													}
							});			
								*/				
									break;
						case TouchEvent.ACTION_MOVE:
							this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
							MenuHud.this.mMenuSprite.setAlpha(1.0f);
							MenuHud.this.mGame.getStatsHud().getTermono().setText(String.valueOf("X: " + String.valueOf(pSceneTouchEvent.getX()) + "\n Y: " + String.valueOf(pSceneTouchEvent.getY() + "\n Alpha: " + String.valueOf(MenuHud.this.mMenuSprite.getAlpha()))));
							break;
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							//MenuHud.this.mMenuSprite.setAlpha(1.0f);
							break;
					}
				//	MenuHud.this.mMenuSprite.setAlpha(0.3f);
					return true;
				}
			};
			this.mHud.registerTouchArea(this.mMenuSprite);
			this.mMenuSprite.setAlpha(0.3f);
		//	this.mMenuSprite.setScale(1.0f); 
			
			
			
		}


		
		// ===========================================================
		// Methods
		// ===========================================================
		
		// ===========================================================
		// Getter & Setter
		// ===========================================================
		public Sprite getMenuSprite() {
			return this.mMenuSprite;
		}
	
		
}
