package com.termono.display.hud;



import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
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
		private IUpdateHandler pUpdatehandler;
		private boolean updater = false;
		private ITextureRegion mMenuTextureRegion;
		private ITextureRegion mCancelTextureRegion;
		private Sprite mMenuSprite;
		private Sprite mCancelSprite;
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
			this.mMenuTextureAtlas = new BitmapTextureAtlas(this.mGame.getTextureManager(), 512, 512, TextureOptions.BILINEAR);		
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "menu.png", 0, 0);
			this.mCancelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "cruz.png", 0, 320);
			
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
 
			this.mMenuSprite = new Sprite(0,0, this.mMenuTextureRegion, this.mGame.getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == false) {
								updater = true;
								MenuHud.this.mMenuEntity.registerEntityModifier(new MoveModifier(0.7f, MenuHud.this.mGame.getDisplay().getCameraWidth()-96, 200, -236, 100, EaseBackOut.getInstance()));

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
							} 
						break;

													
						case TouchEvent.ACTION_MOVE:
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							break;
					}
					return true;
					
				}
			};
			
			
			this.mCancelSprite = new Sprite(MenuHud.this.mMenuSprite.getWidth() - 120, 40, this.mCancelTextureRegion, this.mGame.getVertexBufferObjectManager()){
			
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == true && MenuHud.this.mMenuEntity.getX() < 230) { updater = false; MenuHud.this.mMenuEntity.setPosition(MenuHud.this.mMenuEntity.getInitialX(), MenuHud.this.mMenuEntity.getInitialY());}
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
			this.mHud.registerTouchArea(this.mCancelSprite);
			this.mHud.registerTouchArea(this.mMenuSprite);//416 - 27
			
			this.mMenuEntity.attachChild(this.mMenuSprite);
			this.mMenuEntity.attachChild(this.mCancelSprite);
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