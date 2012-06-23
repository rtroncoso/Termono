package com.quest.scenes;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.display.hud.StatsHud;
import com.quest.game.Game;


public class GameMenuScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	//Entidades
	private Entity mGameMenuEntity;
	private Entity mInventoryEntity;
	private Entity mEquipmentEntity;
	private Entity mSkillsEntity;
	private Entity mAttributesEntity;
	private Entity mInfoEntity;


	//Textures Atlas
	private BitmapTextureAtlas mSceneTextureAtlas;
	private BitmapTextureAtlas mInventoryTextureAtlas;
	private BitmapTextureAtlas mEquipmentTextureAtlas;
	private BitmapTextureAtlas mSkillsTextureAtlas;
	private BitmapTextureAtlas mAttributesTextureAtlas;
	private BitmapTextureAtlas mInfoTextureAtlas;
	
	//Texture regions
	private ITextureRegion mInventoryTabTextureRegion;
		private ITextureRegion mInventoryUseTextureRegion;
		private ITextureRegion mInventoryTossTextureRegion;
		private ITextureRegion mInventoryMoneyTextureRegion;
	private ITextureRegion mEquipmentTabTextureRegion;
	private ITextureRegion mSkillsTabTextureRegion;
	private ITextureRegion mAttributesTabTextureRegion;
	private ITextureRegion mInfoTabTextureRegion;
	private ITextureRegion mCloseTextureRegion;
	
	
	
	//Sprites
	private Sprite mInventoryTabSprite;
		private Sprite mInventoryUseSprite;
		private Sprite mInventoryTossSprite;
		private Sprite mInventoryMoneySprite;
	private Sprite mEquipmentTabSprite;
	private Sprite mSkillsTabSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mInfoTabSprite;
	private Sprite mCloseSprite;
	private Sprite mBackgroundSprite;
	private ITextureRegion mBackgroundTextureRegion;
	
	private StatsHud mStatsHud;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public GameMenuScene(){
		this.mGameMenuEntity = new Entity(0,0);
		this.mInventoryEntity = new Entity(0,0);
		this.mEquipmentEntity = new Entity(0,0);
		this.mSkillsEntity = new Entity(0,0);
		this.mAttributesEntity = new Entity(0,0);
		this.mInfoEntity = new Entity(0,0);

		mStatsHud = new StatsHud();
		
		//###################COMIENZO DE ENTIDAD PRINCIPAL############################
		
		this.attachChild(mGameMenuEntity);
					
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
		this.mCloseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Close.png", 800, 0);
		this.mInventoryTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 800, 50);
		this.mEquipmentTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Equipment.png", 800, 100);
		this.mSkillsTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Skills.png", 800, 150);
		this.mAttributesTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 800, 200);
		this.mInfoTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Info.png", 800, 250);
		this.mSceneTextureAtlas.load();
		//Cargar los otros atlas ahora?
	
		//Backgroud
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.mGameMenuEntity.attachChild(mBackgroundSprite);		
		
		//Close
		this.mCloseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 42, 80,this.mCloseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						break;
					case TouchEvent.ACTION_DOWN:
					case TouchEvent.ACTION_UP:
							Game.getSceneManager().setGameScene();
						break;
					}
					return true;
					}					
				};
				this.mGameMenuEntity.attachChild(mCloseSprite);
				
		
		//Inventory Tab
		this.mInventoryTabSprite = new Sprite(0, 0,this.mInventoryTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				//cargar datos?
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mInventoryTabSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mInventoryTabSprite);
		
		
		//Equipment Tab
		this.mEquipmentTabSprite = new Sprite(147, 0,this.mEquipmentTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentTabSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mEquipmentTabSprite);
		
		
		//Skills Tab
		this.mSkillsTabSprite = new Sprite(311, 0,this.mSkillsTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mSkillsTabSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mSkillsTabSprite);
		
		//Attributes Tab
		this.mAttributesTabSprite = new Sprite(482, 0,this.mAttributesTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mAttributesTabSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mAttributesTabSprite);
		

		//Info Tab
		this.mInfoTabSprite = new Sprite(623, 0,this.mInfoTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mInfoTabSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mInfoTabSprite);
		
			
		this.registerTouchArea(this.mCloseSprite);
		this.registerTouchArea(this.mInventoryTabSprite);
		this.registerTouchArea(this.mEquipmentTabSprite);
		this.registerTouchArea(this.mSkillsTabSprite);
		this.registerTouchArea(this.mAttributesTabSprite);
		this.registerTouchArea(this.mInfoTabSprite);
			
		
		//##############FIN DE LA ENTIDAD PRINCIPAL########################
		
		
		
		//##############COMIENZO DE ENTIDAD INVENTORY######################
		//atachearlas cuando se llame a la tab
		this.attachChild(mInventoryEntity);
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Inventory/");
		this.mInventoryTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512,512, TextureOptions.BILINEAR);
		this.mInventoryUseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "UseEquip.png", 0, 0);
		this.mInventoryTossTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Toss.png", 100, 0);
		this.mInventoryMoneyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Money.png", 0, 145);
		this.mInventoryTextureAtlas.load();
		
		//Use Sprite
		this.mInventoryUseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 150,this.mInventoryUseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_UP:
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mInventoryUseSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mInventoryEntity.attachChild(mInventoryUseSprite);
		
		this.registerTouchArea(mInventoryUseSprite);		
	
		
		//Toss Sprite
		this.mInventoryTossSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 295,this.mInventoryTossTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_UP:
			case TouchEvent.ACTION_DOWN:	
				GameMenuScene.this.mInventoryTossSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mInventoryEntity.attachChild(mInventoryTossSprite);
		this.registerTouchArea(mInventoryTossSprite);
		
		
		//Money Sprite
		this.mInventoryMoneySprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 110, 125,this.mInventoryMoneyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				break;
			}
			return true;
			}					
		};
		this.mInventoryEntity.attachChild(mInventoryMoneySprite);
		
		this.attachChild(this.mStatsHud.getTermono());
		
		//################FIN DE ENTIDAD INVENTORY#########################
	//	this.attachChild(mEquipmentEntity);
		
	//	this.attachChild(mSkillsEntity);
		
	//	this.attachChild(mAttributesEntity);
		
//		this.attachChild(mInfoEntity);
	
		
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
