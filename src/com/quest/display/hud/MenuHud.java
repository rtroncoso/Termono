package com.quest.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.game.Game;

public class MenuHud extends HUD{

		// ===========================================================

		// Constants

		// ===========================================================


		// ===========================================================
		// Fields
		// ===========================================================		
		private HUD mHud;
		private BitmapTextureAtlas mMenuTextureAtlas;
		private ITextureRegion mMenuTextureRegion;
		private Sprite mMenuSprite;

		// ===========================================================
		// Constructor
		// ===========================================================

		public MenuHud(HUD pHud){

			//Init local Variables
			this.mHud = pHud;
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/HUD/");

			// Create Texture objects
			this.mMenuTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 64, 64, TextureOptions.BILINEAR);		
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "menu.png", 0, 0);
								
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
 				//checkear pos
			this.mMenuSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 64, 0, this.mMenuTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_CANCEL://como mierda detecto el cancel!?
							break;
						case TouchEvent.ACTION_DOWN:
							Game.getSceneManager().getGameScene().unloadHUD();														
							Game.getSceneManager().getDisplay().getCamera().setChaseEntity(null);
							Game.getSceneManager().setGameMenuScene();							
						break;
					}
					return true;
					
				}
			};
			
			//fin del menu
			this.mHud.registerTouchArea(this.mMenuSprite);			
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
