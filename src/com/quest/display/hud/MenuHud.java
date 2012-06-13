package com.quest.display.hud;



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

import com.quest.game.Game;
import com.quest.helpers.SceneHelper;
import com.quest.scenes.GameScene;



public class MenuHud extends HUD{



		// ===========================================================

		// Constants

		// ===========================================================

	

		// ===========================================================
		// Fields
		// ===========================================================
		private BitmapTextureAtlas mMenuTextureAtlas;
		private HUD mHud;
		private boolean updater = false;
		private ITextureRegion mMenuTextureRegion;
		private ITextureRegion mCloseTextureRegion;
		private ITextureRegion mInventoryTextureRegion;
		private ITextureRegion mSkillsTextureRegion;
		private ITextureRegion mEquipmentTextureRegion;
		private ITextureRegion mAttributesTextureRegion;
		private Sprite mMenuSprite;
		private Sprite mCloseSprite;
		private Sprite mInventorySprite;
		private Sprite mAttributesSprite;
		private Sprite mSkillsSprite;
		private Sprite mEquipmentSprite;
		private Entity mMenuEntity;

		// ===========================================================
		// Constructor
		// ===========================================================

		public MenuHud(HUD pHud){

			//Init local Variables
			this.mHud = pHud;
			this.mMenuEntity = new Entity(Game.getInstance().getSceneManager().getDisplay().getCameraWidth()-96, -236);
			
			// Set base path for Textures
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/");

			// Create Texture objects
			this.mMenuTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);		
			this.mMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "IngameMenu.png", 0, 0);
			this.mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Close.png", 0, 322);
			this.mInventoryTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 600, 0);
			this.mSkillsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Skills.png", 600, 110);
			this.mAttributesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 600, 220);
			this.mEquipmentTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTextureAtlas, Game.getInstance().getApplicationContext(), "Equipment.png", 600, 330);
			
			
			
			// Load Texture into memory and on the screen
			this.mMenuTextureAtlas.load();
 
			this.mMenuSprite = new Sprite(0, 0, this.mMenuTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {

				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					// TODO Auto-generated method stub
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							if(updater == false) {
								updater = true;
								MenuHud.this.mMenuEntity.registerEntityModifier(new MoveModifier(0.7f, Game.getInstance().getSceneManager().getDisplay().getCameraWidth() - 96, 100, -236, 50, EaseBackOut.getInstance()));
							} 
						break;
					}
					return true;
					
				}
			};
			
			//fin del menu
			
			this.mCloseSprite = new Sprite(MenuHud.this.mMenuSprite.getWidth() - 50, 10, this.mCloseTextureRegion, Game.getInstance().getVertexBufferObjectManager()){
			
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_UP:
					    	if(updater == true) { updater = false; MenuHud.this.mMenuEntity.setPosition(MenuHud.this.mMenuEntity.getInitialX(), MenuHud.this.mMenuEntity.getInitialY());}
							break;
					}
					return true;
				}
			};
			//fin del cancel
			
			
			this.mInventorySprite = new Sprite(55,30, this.mInventoryTextureRegion,Game.getInstance().getVertexBufferObjectManager()){
				
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_UP:
					Game.getSceneManager().setInventoryScene();
					break;
					}
				return true;
					
				}
				
			};
			
			this.mEquipmentSprite = new Sprite(55,155, this.mEquipmentTextureRegion,Game.getInstance().getVertexBufferObjectManager()){
				
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_UP:
						//MenuHud.this.mSceneManager.setEquipmentScene();
						break;
					}
				return true;			
				}				
			};
			
			this.mSkillsSprite = new Sprite(290,155, this.mSkillsTextureRegion,Game.getInstance().getVertexBufferObjectManager()){
						
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_UP:
						//MenuHud.this.mSceneManager.setSkillsScene();
						break;
					}
				return true;
							
				}
						
			};
						
			this.mAttributesSprite = new Sprite(290,30, this.mAttributesTextureRegion,Game.getInstance().getVertexBufferObjectManager()){
									
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_UP:
						//MenuHud.this.mSceneManager.setAttributesScene();
						break;
					}
				return true;
				}
			};
								
			
			this.mHud.registerTouchArea(this.mEquipmentSprite);
			this.mHud.registerTouchArea(this.mSkillsSprite);
			this.mHud.registerTouchArea(this.mAttributesSprite);
			this.mHud.registerTouchArea(this.mInventorySprite);
			this.mHud.registerTouchArea(this.mCloseSprite);
			this.mHud.registerTouchArea(this.mMenuSprite);
			
			
			this.mMenuEntity.attachChild(this.mMenuSprite);
			this.mMenuEntity.attachChild(this.mCloseSprite);
			this.mMenuEntity.attachChild(this.mInventorySprite);
			this.mMenuEntity.attachChild(this.mAttributesSprite);
			this.mMenuEntity.attachChild(this.mSkillsSprite);
			this.mMenuEntity.attachChild(this.mEquipmentSprite);
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




/*					MenuHud.Game.getInstance().getScene().registerUpdateHandler(pUpdatehandler = new IUpdateHandler() {

	@Override
	public void onUpdate(float pSecondsElapsed) {
		updater = true;
		if(MenuHud.this.mMenuEntity.getX() < 230){MenuHud.Game.getInstance().getScene().unregisterUpdateHandler(pUpdatehandler);}
		MenuHud.this.mMenuEntity.setPositi	on(MenuHud.this.mMenuEntity.getX() - 20, MenuHud.this.mMenuEntity.getY() + 12);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub			
	}
});		
*/