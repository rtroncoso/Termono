package com.quest.scenes;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.algorithm.collision.RectangularShapeCollisionChecker;
import org.andengine.util.algorithm.collision.ShapeCollisionChecker;

import com.quest.display.hud.StatsHud;
import com.quest.game.Game;


public class GameMenuScene extends Scene{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	//HACER UN BOOLEAN PARA CURRENT TAB
	
	//Entidades
	private Entity mGameMenuEntity;
	private Entity mInventoryEntity;
	private Entity mEquipmentEntity;
	private Entity mSkillsEntity;
	private Entity mAttributesEntity;
	private Entity mInfoEntity;
	private Entity mCurrentEntity;

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
		private ITextureRegion mItemTextureRegion;
	private ITextureRegion mEquipmentTabTextureRegion;
		private ITextureRegion mEquipmentBoxTextureRegion;
		private ITextureRegion mEquipmentAttributesTextureRegion;
		private ITextureRegion mEquipmentItemsTextureRegion;
		private ITextureRegion mEquipmentHelmTextureRegion;
		private ITextureRegion mEquipmentPlateTextureRegion;
		private ITextureRegion mEquipmentLegsTextureRegion;
		private ITextureRegion mEquipmentWeaponTextureRegion;
		private ITextureRegion mEquipmentOffhandTextureRegion;
		private ITextureRegion mEquipmentExtraTextureRegion;
		private ITextureRegion mEquipmentSwordItemTextureRegion;
		private ITextureRegion mEquipmentShieldItemTextureRegion;
		private ITextureRegion mEquipmentPlateItemTextureRegion;
	private ITextureRegion mSkillsTabTextureRegion;
	private ITextureRegion mAttributesTabTextureRegion;
	private ITextureRegion mInfoTabTextureRegion;
	private ITextureRegion mCloseTextureRegion;
	private ITextureRegion mBackgroundTextureRegion;
	
	
	//Sprites
	private Sprite mInventoryTabSprite;
		private Sprite mInventoryUseSprite;
		private Sprite mInventoryTossSprite;
		private Sprite mInventoryMoneySprite;
		private Sprite mItemSprite;
	private Sprite mEquipmentTabSprite;
		private Sprite mEquipmentBoxSprite;
		private Sprite mEquipmentBox2Sprite;
		private Sprite mEquipmentAttributesSprite;
		private Sprite mEquipmentItemsSprite;
		private Sprite mEquipmentHelmSprite;
		private Sprite mEquipmentPlateSprite;
		private Sprite mEquipmentLegsSprite;
		private Sprite mEquipmentWeaponSprite;
		private Sprite mEquipmentOffhandSprite;
		private Sprite mEquipmentExtraSprite;
		private Sprite mEquipmentSwordItemSprite;
		private Sprite mEquipmentShieldItemSprite;
		private Sprite mEquipmentPlateItemSprite;
	private Sprite mSkillsTabSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mInfoTabSprite;
	private Sprite mCloseSprite;
	private Sprite mBackgroundSprite;
	
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
		this.mCloseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 42, 10,this.mCloseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_CANCEL:
					case TouchEvent.ACTION_OUTSIDE:
						break;
					case TouchEvent.ACTION_DOWN:
							Game.getSceneManager().setGameScene();
						break;
					}
					return true;
					}					
				};
				this.mCloseSprite.setScale(1.3f);
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
				UnloadEntity(mCurrentEntity);
				mCurrentEntity = LoadInventoryEntity();
				GameMenuScene.this.attachChild(mCurrentEntity);
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
				UnloadEntity(mCurrentEntity);
				mCurrentEntity = LoadEquipmentEntity();
				GameMenuScene.this.attachChild(mCurrentEntity);
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
			UnloadEntity(mCurrentEntity);
			mCurrentEntity = LoadSkillsEntity();
			GameMenuScene.this.attachChild(mCurrentEntity);
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
				UnloadEntity(mCurrentEntity);
				mCurrentEntity = LoadAttributesEntity();
				GameMenuScene.this.attachChild(mCurrentEntity);
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
				UnloadEntity(mCurrentEntity);
				mCurrentEntity = LoadInfoEntity();
				GameMenuScene.this.attachChild(mCurrentEntity);
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
		
					
		this.mInventoryTabSprite.setAlpha(0.5f);
		mCurrentEntity = LoadInventoryEntity();
		GameMenuScene.this.attachChild(mCurrentEntity);
		
		//##############FIN DE LA ENTIDAD PRINCIPAL########################
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}
			
	
	
	
	
	//#################INVENTORY ENTITY######################
	public Entity LoadInventoryEntity(){
				this.mInventoryEntity.detachChildren();//La limpio, necesario?
		
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Inventory/");
				this.mInventoryTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
				this.mInventoryUseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "UseEquip.png", 0, 0);
				this.mInventoryTossTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Toss.png", 100, 0);
				this.mInventoryMoneyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Money.png", 0, 145);
				this.mInventoryTextureAtlas.load();
				
				//Use Sprite
				this.mInventoryUseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 184, 150,this.mInventoryUseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
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
				this.mInventoryUseSprite.setScale(2.0f);
				this.mInventoryEntity.attachChild(mInventoryUseSprite);
				
				this.registerTouchArea(mInventoryUseSprite);		
			
				
				//Toss Sprite
				this.mInventoryTossSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 400, 295,this.mInventoryTossTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
					boolean mGrabbed = false;
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_OUTSIDE:
					case TouchEvent.ACTION_CANCEL:
						break;
					case TouchEvent.ACTION_UP:
					case TouchEvent.ACTION_DOWN:
						GameMenuScene.this.mInventoryUseSprite.setAlpha(0.5f);
						GameMenuScene.this.checkInside(GameMenuScene.this.mInventoryUseSprite, GameMenuScene.this.mItemSprite);
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
					case TouchEvent.ACTION_UP:
					case TouchEvent.ACTION_DOWN:
						GameMenuScene.this.mInventoryUseSprite.setAlpha(0.5f);
						break;
					}
					return true;
					}					
				};
				this.mInventoryEntity.attachChild(mInventoryMoneySprite);
					
				
				//load a los objetos(funcion?)####### Texture atlas distinto para items??###############################################################
					this.mInventoryTextureAtlas.unload();
					this.mItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Item.png", 100, 145);
					this.mInventoryTextureAtlas.load();
					
					this.mItemSprite = new Sprite(50, 100,this.mItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
						boolean mGrabbed = false;
						@Override
						public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						switch(pSceneTouchEvent.getAction()) {
						case TouchEvent.ACTION_DOWN:
							GameMenuScene.this.mItemSprite.setScale(3f);
							this.mGrabbed = true;
							break;
						case TouchEvent.ACTION_MOVE:
							if(this.mGrabbed) {
								GameMenuScene.this.mItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mInventoryTossSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mInventoryTossSprite.getHeight() / 2);
							}
							break;
						case TouchEvent.ACTION_UP:
							if(this.mGrabbed) {
								this.mGrabbed = false;
								GameMenuScene.this.mItemSprite.setScale(2.0f);
							}
							break;
						}
						return true;
						}					
					};
					GameMenuScene.this.mItemSprite.setScale(2.0f);
					this.mInventoryEntity.attachChild(mItemSprite);
					this.registerTouchArea(mItemSprite);
					
					return this.mInventoryEntity;
	}	
	
	
	
	
	
	//#################EQUIPMENT ENTITY######################
	public Entity LoadEquipmentEntity(){
		this.mEquipmentEntity.detachChildren();//La limpio, necesario?
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Equipment/");
		this.mEquipmentTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mEquipmentBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Box.png", 0, 0);
		this.mEquipmentAttributesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 390, 0);
		this.mEquipmentItemsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Items.png", 390, 35);
		this.mEquipmentHelmTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Helm.png", 390, 69);
		this.mEquipmentPlateTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Plate.png", 390, 150);
		this.mEquipmentLegsTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Legs.png", 390, 250);
		this.mEquipmentWeaponTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Weapon.png", 390, 350);
		this.mEquipmentOffhandTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Offhand.png", 0, 406);
		this.mEquipmentExtraTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Extra.png", 80, 406);
		this.mEquipmentTextureAtlas.load();
		
		
		//Cajas de contenidos
		this.mEquipmentBoxSprite = new Sprite(20, 70,this.mEquipmentBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
		this.mEquipmentEntity.attachChild(mEquipmentBoxSprite);
		
		this.mEquipmentBox2Sprite = new Sprite(415, 70,this.mEquipmentBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
		this.mEquipmentEntity.attachChild(mEquipmentBox2Sprite);	
		
		//Items Tab
		this.mEquipmentItemsSprite = new Sprite(415, 75,this.mEquipmentItemsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentItemsSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentItemsSprite);
		
		//Attributes Tab
		this.mEquipmentAttributesSprite = new Sprite(695, 75,this.mEquipmentAttributesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentAttributesSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentAttributesSprite);
		
		//##########################BODY###################################
		
		//Helm Sprite
		this.mEquipmentHelmSprite = new Sprite(165, 85,this.mEquipmentHelmTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentHelmSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentHelmSprite);
		
		//Plate Sprite
		this.mEquipmentPlateSprite = new Sprite(144, 186,this.mEquipmentPlateTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentPlateSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentPlateSprite);
		
		//Legs Sprite
		this.mEquipmentLegsSprite = new Sprite(169, 306,this.mEquipmentLegsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentLegsSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentLegsSprite);
		
		
		//Weapon Sprite
		this.mEquipmentWeaponSprite = new Sprite(46, 180,this.mEquipmentWeaponTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentWeaponSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentWeaponSprite);
		
		
		//Offhand Sprite
		this.mEquipmentOffhandSprite = new Sprite(284, 180,this.mEquipmentOffhandTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentOffhandSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentOffhandSprite);
		
		
		//Extra Sprite
		this.mEquipmentExtraSprite = new Sprite(272, 311,this.mEquipmentExtraTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
				GameMenuScene.this.mEquipmentExtraSprite.setAlpha(0.5f);
				break;
			}
			return true;
			}					
		};
		this.mEquipmentEntity.attachChild(mEquipmentExtraSprite);
		
				//Funcion para registrar touch areas??
		this.registerTouchArea(this.mEquipmentAttributesSprite);
		this.registerTouchArea(this.mEquipmentItemsSprite);
	/*	this.registerTouchArea(this.mEquipmentHelmSprite);
		this.registerTouchArea(this.mEquipmentPlateSprite);
		this.registerTouchArea(this.mEquipmentLegsSprite);
		this.registerTouchArea(this.mEquipmentWeaponSprite);
		this.registerTouchArea(this.mEquipmentOffhandSprite);		
		this.registerTouchArea(this.mEquipmentExtraSprite);
		*/
		//########################FIN DEL BODY#############################
		
		
		//items de prueba(seria en una funcion)
		this.mEquipmentTextureAtlas.unload();//se pierde lo que estaba antes?? / Necesito unloadear?
		this.mEquipmentSwordItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "SwordItem.png", 160, 406);
		this.mEquipmentShieldItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "ShieldItem.png", 185, 406);
		this.mEquipmentPlateItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "PlateItem.png", 210, 406);
		this.mEquipmentTextureAtlas.load();
		//sword
		this.mEquipmentSwordItemSprite = new Sprite(GameMenuScene.this.mEquipmentWeaponSprite.getX()+ GameMenuScene.this.mEquipmentWeaponSprite.getWidth()/2, GameMenuScene.this.mEquipmentWeaponSprite.getY()+ GameMenuScene.this.mEquipmentWeaponSprite.getHeight()/2,this.mEquipmentSwordItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
					if(GameMenuScene.this.mEquipmentSwordItemSprite.getX() == GameMenuScene.this.mEquipmentWeaponSprite.getX()+ GameMenuScene.this.mEquipmentWeaponSprite.getWidth()/2 && GameMenuScene.this.mEquipmentSwordItemSprite.getY() == GameMenuScene.this.mEquipmentWeaponSprite.getY()+ GameMenuScene.this.mEquipmentWeaponSprite.getHeight()/2){
						GameMenuScene.this.mEquipmentSwordItemSprite.setX(GameMenuScene.this.mEquipmentWeaponSprite.getX()+ GameMenuScene.this.mEquipmentWeaponSprite.getWidth()/2 + 100);
						GameMenuScene.this.mEquipmentSwordItemSprite.setY(GameMenuScene.this.mEquipmentWeaponSprite.getY()+ GameMenuScene.this.mEquipmentWeaponSprite.getHeight()/2 + 100);
					}else if(GameMenuScene.this.mEquipmentSwordItemSprite.getX() == GameMenuScene.this.mEquipmentWeaponSprite.getX()+ GameMenuScene.this.mEquipmentWeaponSprite.getWidth()/2 + 100 && GameMenuScene.this.mEquipmentSwordItemSprite.getY() == GameMenuScene.this.mEquipmentWeaponSprite.getY()+ GameMenuScene.this.mEquipmentWeaponSprite.getHeight()/2 + 100){
						GameMenuScene.this.mEquipmentSwordItemSprite.setX(GameMenuScene.this.mEquipmentWeaponSprite.getX()+ GameMenuScene.this.mEquipmentWeaponSprite.getWidth()/2);
						GameMenuScene.this.mEquipmentSwordItemSprite.setY(GameMenuScene.this.mEquipmentWeaponSprite.getY()+ GameMenuScene.this.mEquipmentWeaponSprite.getHeight()/2);												
					}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentSwordItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentSwordItemSprite);
		this.registerTouchArea(mEquipmentSwordItemSprite);
		
		//shield
		this.mEquipmentShieldItemSprite = new Sprite(GameMenuScene.this.mEquipmentOffhandSprite.getX()+ GameMenuScene.this.mEquipmentOffhandSprite.getWidth()/2, GameMenuScene.this.mEquipmentOffhandSprite.getY()+ GameMenuScene.this.mEquipmentOffhandSprite.getHeight()/2,this.mEquipmentShieldItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
					if(GameMenuScene.this.mEquipmentShieldItemSprite.getX() == GameMenuScene.this.mEquipmentOffhandSprite.getX()+ GameMenuScene.this.mEquipmentOffhandSprite.getWidth()/2 && GameMenuScene.this.mEquipmentShieldItemSprite.getY() == GameMenuScene.this.mEquipmentOffhandSprite.getY()+ GameMenuScene.this.mEquipmentOffhandSprite.getHeight()/2){
						GameMenuScene.this.mEquipmentShieldItemSprite.setX(GameMenuScene.this.mEquipmentOffhandSprite.getX()+ GameMenuScene.this.mEquipmentOffhandSprite.getWidth()/2 + 20);
						GameMenuScene.this.mEquipmentShieldItemSprite.setY(GameMenuScene.this.mEquipmentOffhandSprite.getY()+ GameMenuScene.this.mEquipmentOffhandSprite.getHeight()/2 + 20);
					}else if(GameMenuScene.this.mEquipmentShieldItemSprite.getX() == GameMenuScene.this.mEquipmentOffhandSprite.getX()+ GameMenuScene.this.mEquipmentOffhandSprite.getWidth()/2 + 20 && GameMenuScene.this.mEquipmentShieldItemSprite.getY() == GameMenuScene.this.mEquipmentOffhandSprite.getY()+ GameMenuScene.this.mEquipmentOffhandSprite.getHeight()/2 + 20){
						GameMenuScene.this.mEquipmentShieldItemSprite.setX(GameMenuScene.this.mEquipmentOffhandSprite.getX()+ GameMenuScene.this.mEquipmentOffhandSprite.getWidth()/2);
						GameMenuScene.this.mEquipmentShieldItemSprite.setY(GameMenuScene.this.mEquipmentOffhandSprite.getY()+ GameMenuScene.this.mEquipmentOffhandSprite.getHeight()/2);												
					}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentShieldItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentShieldItemSprite);
		this.registerTouchArea(mEquipmentShieldItemSprite);
		
		//plate
		this.mEquipmentPlateItemSprite = new Sprite(GameMenuScene.this.mEquipmentPlateSprite.getX()+ GameMenuScene.this.mEquipmentPlateSprite.getWidth()/2, GameMenuScene.this.mEquipmentPlateSprite.getY()+ GameMenuScene.this.mEquipmentPlateSprite.getHeight()/2,this.mEquipmentPlateItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
				break;
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_UP:
					if(GameMenuScene.this.mEquipmentPlateItemSprite.getX() == GameMenuScene.this.mEquipmentPlateSprite.getX()+ GameMenuScene.this.mEquipmentPlateSprite.getWidth()/2 && GameMenuScene.this.mEquipmentPlateItemSprite.getY() == GameMenuScene.this.mEquipmentPlateSprite.getY()+ GameMenuScene.this.mEquipmentPlateSprite.getHeight()/2){
						GameMenuScene.this.mEquipmentPlateItemSprite.setX(GameMenuScene.this.mEquipmentPlateSprite.getX()+ GameMenuScene.this.mEquipmentPlateSprite.getWidth()/2 + 20);
						GameMenuScene.this.mEquipmentPlateItemSprite.setY(GameMenuScene.this.mEquipmentPlateSprite.getY()+ GameMenuScene.this.mEquipmentPlateSprite.getHeight()/2 + 20);
					}else if(GameMenuScene.this.mEquipmentPlateItemSprite.getX() == GameMenuScene.this.mEquipmentPlateSprite.getX()+ GameMenuScene.this.mEquipmentPlateSprite.getWidth()/2 + 20 && GameMenuScene.this.mEquipmentPlateItemSprite.getY() == GameMenuScene.this.mEquipmentPlateSprite.getY()+ GameMenuScene.this.mEquipmentPlateSprite.getHeight()/2 + 20){
						GameMenuScene.this.mEquipmentPlateItemSprite.setX(GameMenuScene.this.mEquipmentPlateSprite.getX()+ GameMenuScene.this.mEquipmentPlateSprite.getWidth()/2);
						GameMenuScene.this.mEquipmentPlateItemSprite.setY(GameMenuScene.this.mEquipmentPlateSprite.getY()+ GameMenuScene.this.mEquipmentPlateSprite.getHeight()/2);												
					}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentPlateItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentPlateItemSprite);
		this.registerTouchArea(mEquipmentPlateItemSprite);

		//fin de carga de items
		
		
		return this.mEquipmentEntity;
	}
	
	
	
	
	//#################SKILLS ENTITY######################	
	public Entity LoadSkillsEntity(){
		this.mSkillsEntity.detachChildren();//La limpio, necesario?
		
		
		return this.mSkillsEntity;
	}
	
	
	
	
	//#################ATTRIBUTES ENTITY######################
	public Entity LoadAttributesEntity(){
		this.mAttributesEntity.detachChildren();//La limpio, necesario?
		
		
		return this.mAttributesEntity;
	}
	
	
	
	//#################INFO ENTITY######################
	public Entity LoadInfoEntity(){
		this.mInfoEntity.detachChildren();//La limpio, necesario?
		
		
		return this.mInfoEntity;
	}
	
	
	//#################UNLOAD ENTITY######################
	public void UnloadEntity(Entity pEntity){
		this.detachChild(pEntity);
		}
	
	
	//###################CHECK TOUCH AREA#################
	  public boolean checkInside(Sprite pSprite1, Sprite pSprite2){
	        float[] VerticesA= new float[8];
	        float[] VerticesB= new float[8];
	        //por ahora lo hago a lo negro, despues me fijo si tengo funcionde cargar vertices
	        	VerticesA[0] = pSprite1.getX();
	        	VerticesA[1] = pSprite1.getY();	        	
	        	VerticesA[2] = pSprite1.getX() + pSprite1.getWidth();
	        	VerticesA[3] = pSprite1.getY();	        	
	        	VerticesA[4] = pSprite1.getX();
	        	VerticesA[5] = pSprite1.getY() + pSprite1.getHeight();       	
	        	VerticesA[6] = pSprite1.getX() + pSprite1.getWidth();
	        	VerticesA[7] = pSprite1.getY() + pSprite1.getHeight();
	        	
	        	VerticesB[0] = pSprite2.getX();
	        	VerticesB[1] = pSprite2.getY();	        	
	        	VerticesB[2] = pSprite2.getX() + pSprite2.getWidth();
	        	VerticesB[3] = pSprite2.getY();	        	
	        	VerticesB[4] = pSprite2.getX();
	        	VerticesB[5] = pSprite2.getY() + pSprite2.getHeight();       	
	        	VerticesB[6] = pSprite2.getX() + pSprite2.getWidth();
	        	VerticesB[7] = pSprite2.getY() + pSprite2.getHeight();
	        	
	        	
	    		if (ShapeCollisionChecker.checkCollision(VerticesA, 4, VerticesB, 4)){
	    			this.mInfoTabSprite.setScale(2.0f);
	    			return true;
	    		} else {
	        		this.mInfoTabSprite.setScale(0.5f);
	        		return false;
	        }
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















/*
 * 
 * -FUNCION PARA CARGAR Y DESCARGAR TOUCH AREAS
 * -CENTRAR LA POSICION A LOS ITEMS (- THIS.WIDTH / 2, FIJARME SI HAY SET CENTER O HACER UNA FUNCION)
 * 
 * 
 * 
 * */








