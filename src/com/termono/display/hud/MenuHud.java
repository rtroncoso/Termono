package com.termono.display.hud;



import org.andengine.engine.camera.hud.HUD;

import org.andengine.engine.handler.IUpdateHandler;

import org.andengine.entity.sprite.Sprite;

import org.andengine.entity.sprite.batch.DynamicSpriteBatch;

import org.andengine.entity.sprite.batch.SpriteBatch;

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
		private HUD mHud;
		private IUpdateHandler pUpdatehandler;
		private boolean updater = false;
		private ITextureRegion mMenuTextureRegion;
		private ITextureRegion mCancelTextureRegion;
		private Sprite mMenuSprite;
		private Sprite mCancelSprite;
		
		

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
			this.mCancelTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, this.mGame.getApplicationContext(), "cruz.png", 0, 320);
			
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();



			
 
			this.mMenuSprite = new Sprite(this.mGame.getDisplay().getCameraWidth()-96, -236, this.mMenuTextureRegion, this.mGame.getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == false) {	
								MenuHud.this.mGame.getScene().registerUpdateHandler(pUpdatehandler = new IUpdateHandler() {
								
									@Override
									public void onUpdate(float pSecondsElapsed) {
										updater = true;
										if(MenuHud.this.mMenuSprite.getX() < 230){MenuHud.this.mGame.getScene().unregisterUpdateHandler(pUpdatehandler);}//updater=false;}// MenuHud.this.mMenuSprite.setPosition(MenuHud.this.mMenuSprite.getInitialX(), MenuHud.this.mMenuSprite.getInitialY());}
										MenuHud.this.mMenuSprite.setPosition(MenuHud.this.mMenuSprite.getX() - 20, MenuHud.this.mMenuSprite.getY() + 12);
									}
								
									@Override
									public void reset() {
										// TODO Auto-generated method stub			
									}
								});		

							} else if(updater == true && MenuHud.this.mMenuSprite.getX() < 230) { updater = false; MenuHud.this.mMenuSprite.setPosition(MenuHud.this.mMenuSprite.getInitialX(), MenuHud.this.mMenuSprite.getInitialY()); }
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

			this.mHud.registerTouchArea(this.mMenuSprite);

			this.mCancelSprite = new Sprite(416, 27, this.mCancelTextureRegion, this.mGame.getVertexBufferObjectManager());/* {
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							MenuHud.this.mCancelSprite.setScale(2.0f);
							break;
						case TouchEvent.ACTION_MOVE:
						case TouchEvent.ACTION_CANCEL:
						case TouchEvent.ACTION_OUTSIDE:
						case TouchEvent.ACTION_UP:
							MenuHud.this.mCancelSprite.setScale(1.0f);
							break;
					}
					return true;
				}
			};

			this.mHud.registerTouchArea(this.mCancelSprite);*/
		
		
		
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