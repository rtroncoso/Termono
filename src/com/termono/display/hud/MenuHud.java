package com.termono.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
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
						//	this.setScale(2.0f);
							this.setPosition(this.getInitialX(), this.getInitialY());
												
									break;
						case TouchEvent.ACTION_MOVE:
							this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
							break;
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							this.setScale(1.0f);
							break;
					}
					return true;
				}
			};
			this.mHud.registerTouchArea(this.mMenuSprite);
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
			return this.mMenuSprite;
		}
	
		
}
