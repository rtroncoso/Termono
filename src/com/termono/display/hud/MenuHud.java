package com.termono.display.hud;



import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseBackOut;

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
		private HUD mHud;
		private boolean updater = false;
		private ITextureRegion mMenuTextureRegion;
		private ITextureRegion mCancelTextureRegion;
		private ITextureRegion mInventoryTextureRegion;
		private Sprite mMenuSprite;
		private Sprite mCancelSprite;
		private Sprite mInventorySprite;
		private Entity mMenuEntity;
		
		

		// ===========================================================
		// Constructor
		// ===========================================================

		public MenuHud(Game pGame, HUD pHud){

			//Init local Variables
			this.mGame = pGame;
			this.mHud = pHud;

			this.mMenuEntity = new Entity(this.mGame.getDisplay().getCameraWidth()-96, -236);
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/");

			// Create Texture objects
			this.mMenuTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);		
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "menu ingame.png", 0, 0);
			this.mCancelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "cruz.png", 0, 322);
			this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "Inventory.png", 602, 0);
			
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
 
			this.mMenuSprite = new Sprite(0, 0, this.mMenuTextureRegion, this.mGame.getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == false) {
								updater = true;
								MenuHud.this.mMenuEntity.registerEntityModifier(new MoveModifier(0.7f, MenuHud.this.mGame.getDisplay().getCameraWidth() - 96, 100, -236, 50, EaseBackOut.getInstance()));
							} 
						break;
					}
					return true;
					
				}
			};
			
			//fin del menu
			
			this.mCancelSprite = new Sprite(MenuHud.this.mMenuSprite.getWidth() - 120, 40, this.mCancelTextureRegion, this.mGame.getVertexBufferObjectManager()){
			
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == true && MenuHud.this.mMenuEntity.getX() < 100) { updater = false; MenuHud.this.mMenuEntity.setPosition(MenuHud.this.mMenuEntity.getInitialX(), MenuHud.this.mMenuEntity.getInitialY());}
							break;
						case TouchEvent.ACTION_MOVE:
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							MenuHud.this.mCancelSprite.setScale(2.0f);
							break;
					}
					return true;
				}
			};
			this.mCancelSprite.setScale(2.0f);
			//fin del cancel
			
			
			this.mInventorySprite = new Sprite(100, 100, this.mInventoryTextureRegion, this.mGame.getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							MenuHud.this.mInventorySprite.setScale(5.0f);
							break;
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							MenuHud.this.mInventorySprite.setScale(3.0f);
							break;
						}
					return true;
				}		
			};
			this.mInventorySprite.setScale(3.0f);
			//fin del Inventory
			

			//this.mHud.registerTouchArea(this.mInventorySprite);
			this.mHud.registerTouchArea(this.mCancelSprite);
			this.mHud.registerTouchArea(this.mMenuSprite);
			
			this.mMenuEntity.attachChild(this.mMenuSprite);
			this.mMenuEntity.attachChild(this.mCancelSprite);
			//this.mMenuEntity.attachChild(this.mInventorySprite);
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

		public Entity getMenuEntity() {
			return this.mMenuEntity;
		}
		

}




/*					MenuHud.this.mGame.getScene().registerUpdateHandler(pUpdatehandler = new IUpdateHandler() {

	@Override
	public void onUpdate(float pSecondsElapsed) {
		updater = true;
		if(MenuHud.this.mMenuEntity.getX() < 230){MenuHud.this.mGame.getScene().unregisterUpdateHandler(pUpdatehandler);}
		MenuHud.this.mMenuEntity.setPositi	on(MenuHud.this.mMenuEntity.getX() - 20, MenuHud.this.mMenuEntity.getY() + 12);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub			
	}
});		
*/