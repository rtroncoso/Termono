package com.quest.scenes;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.game.Game;
import com.quest.helpers.EquipmentHelper;


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
		private ITextureRegion mEquipmentAxeItemTextureRegion;
		private ITextureRegion mEquipmentShieldItemTextureRegion;
		private ITextureRegion mEquipmentPlate1ItemTextureRegion;
		private ITextureRegion mEquipmentPlate2ItemTextureRegion;
		private ITextureRegion mEquipmentLegsItemTextureRegion;
		private ITextureRegion mEquipmentHelm1ItemTextureRegion;
		private ITextureRegion mEquipmentHelm2ItemTextureRegion;
		private ITextureRegion mEquipmentNecklaceItemTextureRegion;
		private ITextureRegion mEquipmentRingItemTextureRegion;
		
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
		private Sprite mEquipmentPlate1ItemSprite;
		private Sprite mEquipmentAxeItemSprite;
		private Sprite mEquipmentPlate2ItemSprite;
		private Sprite mEquipmentLegsItemSprite;
		private Sprite mEquipmentHelm1ItemSprite;
		private Sprite mEquipmentHelm2ItemSprite;
		private Sprite mEquipmentNecklaceItemSprite;
		private Sprite mEquipmentRingItemSprite;
		
	private Sprite mSkillsTabSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mInfoTabSprite;
	private Sprite mCloseSprite;
	private Sprite mBackgroundSprite;
	
	
	private EquipmentHelper mEquipmentManager;
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
		
		this.mEquipmentManager = new EquipmentHelper();
		
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
				this.mInventoryTossTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Toss.png", 192, 0);
				this.mInventoryMoneyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Money.png", 0, 290);
				this.mInventoryTextureAtlas.load();
				
				//Use Sprite
				this.mInventoryUseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 150,this.mInventoryUseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryUseSprite);
				
				
				//Toss Sprite
				this.mInventoryTossSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 295,this.mInventoryTossTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryTossSprite);
				
				
				//Money Sprite
				this.mInventoryMoneySprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 110, 125,this.mInventoryMoneyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryMoneySprite);

					
				//ITEM
				//load a los objetos(funcion?)####### Texture atlas distinto para items??###############################################################
					this.mInventoryTextureAtlas.unload();
					this.mItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Item.png", 190, 290);
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
								if(GameMenuScene.this.mItemSprite.collidesWith(GameMenuScene.this.mInventoryUseSprite)){
									GameMenuScene.this.mInfoTabSprite.setScale(2.0f);
								} else {
					        		GameMenuScene.this.mInfoTabSprite.setScale(0.5f);
								}
								GameMenuScene.this.mItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mItemSprite.getHeight() / 2);
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
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:	
				this.mGrabbed = true;
				GameMenuScene.this.mEquipmentSwordItemSprite.setAlpha(0.5f);
				//checkear si ya tiene algo atacheado, si no tiene:
				//Leer que items en equipment son weapons y ponerlos en algun color
				//si tiene:
				//dragear el item afuera del sprite y desatachearlo y registrar el touch area
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					//GameMenuScene.this.ITEM ATACHEADO.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mITEM ATACHEADO.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mITEM ATACHEADO.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					GameMenuScene.this.mEquipmentSwordItemSprite.setAlpha(1f);
					//checkear si ya tiene algo atacheado, si no tiene:
					//Leer que items en equipment son weapons y ponerlos en algun color
					//si tiene:
					//dragear el item afuera del sprite y desatachearlo y registrar el touch area
				}
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
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Equipment/Items/");
		this.mEquipmentSwordItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Sword.png", 160, 406);
		this.mEquipmentShieldItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Shield.png", 185, 406);
		this.mEquipmentPlate1ItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Plate1.png", 210, 406);
		this.mEquipmentPlate2ItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Plate2.png", 210, 430);
		this.mEquipmentAxeItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Axe.png", 234, 406);
		this.mEquipmentLegsItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Legs.png", 234, 430);
		this.mEquipmentRingItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Ring.png", 258, 430);
		this.mEquipmentNecklaceItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Necklace.png", 282, 430);
		this.mEquipmentHelm1ItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Helm1.png", 306, 430);
		this.mEquipmentHelm2ItemTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "Helm2.png", 330, 430);
		this.mEquipmentTextureAtlas.load();
		
		
		
		//sword
		this.mEquipmentSwordItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 50,this.mEquipmentSwordItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentSwordItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentSwordItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentSwordItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentSwordItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentSwordItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentSwordItemSprite)== true){
						GameMenuScene.this.mEquipmentWeaponSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentSwordItemSprite.setPosition(GameMenuScene.this.mEquipmentWeaponSprite.getX() + GameMenuScene.this.mEquipmentWeaponSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentSwordItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentWeaponSprite.getY() + GameMenuScene.this.mEquipmentWeaponSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentSwordItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentWeaponSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentSwordItemSprite.setPosition(GameMenuScene.this.mEquipmentSwordItemSprite.getInitialX(),GameMenuScene.this.mEquipmentSwordItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentSwordItemSprite.setPosition(GameMenuScene.this.mEquipmentSwordItemSprite.getInitialX(),GameMenuScene.this.mEquipmentSwordItemSprite.getInitialY());
						}
					GameMenuScene.this.mEquipmentSwordItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentSwordItemSprite.setAlpha(1.0f);
		this.mEquipmentSwordItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentSwordItemSprite);
		this.registerTouchArea(mEquipmentSwordItemSprite);
		
		
		//shield
		this.mEquipmentShieldItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 100,this.mEquipmentShieldItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentShieldItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentShieldItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentShieldItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentShieldItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentShieldItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentShieldItemSprite)== true){
						GameMenuScene.this.mEquipmentOffhandSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentShieldItemSprite.setPosition(GameMenuScene.this.mEquipmentOffhandSprite.getX() + GameMenuScene.this.mEquipmentOffhandSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentShieldItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentOffhandSprite.getY() + GameMenuScene.this.mEquipmentOffhandSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentShieldItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentOffhandSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentShieldItemSprite.setPosition(GameMenuScene.this.mEquipmentShieldItemSprite.getInitialX(),GameMenuScene.this.mEquipmentShieldItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentShieldItemSprite.setPosition(GameMenuScene.this.mEquipmentShieldItemSprite.getInitialX(),GameMenuScene.this.mEquipmentShieldItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentShieldItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentShieldItemSprite.setAlpha(0.99f);
		this.mEquipmentShieldItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentShieldItemSprite);
		this.registerTouchArea(mEquipmentShieldItemSprite);
		
		
		//plate 1
		this.mEquipmentPlate1ItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 150,this.mEquipmentPlate1ItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentPlate1ItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentPlate1ItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentPlate1ItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentPlate1ItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentPlate1ItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentPlate1ItemSprite)== true){
						GameMenuScene.this.mEquipmentPlateSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentPlate1ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlateSprite.getX() + GameMenuScene.this.mEquipmentPlateSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentPlate1ItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentPlateSprite.getY() + GameMenuScene.this.mEquipmentPlateSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentPlate1ItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentPlateSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentPlate1ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlate1ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentPlate1ItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentPlate1ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlate1ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentPlate1ItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentPlate1ItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentPlate1ItemSprite.setAlpha(0.96f);
		this.mEquipmentPlate1ItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentPlate1ItemSprite);
		this.registerTouchArea(mEquipmentPlate1ItemSprite);
		
		
		
		//Plate 2
		this.mEquipmentPlate2ItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 150,this.mEquipmentPlate2ItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentPlate2ItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentPlate2ItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentPlate2ItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentPlate2ItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentPlate2ItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentPlate2ItemSprite)== true){
						GameMenuScene.this.mEquipmentPlateSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentPlate2ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlateSprite.getX() + GameMenuScene.this.mEquipmentPlateSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentPlate2ItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentPlateSprite.getY() + GameMenuScene.this.mEquipmentPlateSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentPlate2ItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentPlateSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentPlate2ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlate2ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentPlate2ItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentPlate2ItemSprite.setPosition(GameMenuScene.this.mEquipmentPlate2ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentPlate2ItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentPlate2ItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentPlate2ItemSprite.setAlpha(0.96f);
		this.mEquipmentPlate2ItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentPlate2ItemSprite);
		this.registerTouchArea(mEquipmentPlate2ItemSprite);

		
		
		
		
		//Axe
		this.mEquipmentAxeItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 50,this.mEquipmentAxeItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentAxeItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentAxeItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentAxeItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentAxeItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentAxeItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentAxeItemSprite)== true){
						GameMenuScene.this.mEquipmentWeaponSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentAxeItemSprite.setPosition(GameMenuScene.this.mEquipmentWeaponSprite.getX() + GameMenuScene.this.mEquipmentWeaponSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentAxeItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentWeaponSprite.getY() + GameMenuScene.this.mEquipmentWeaponSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentAxeItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentWeaponSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentAxeItemSprite.setPosition(GameMenuScene.this.mEquipmentAxeItemSprite.getInitialX(),GameMenuScene.this.mEquipmentAxeItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentAxeItemSprite.setPosition(GameMenuScene.this.mEquipmentAxeItemSprite.getInitialX(),GameMenuScene.this.mEquipmentAxeItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentAxeItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentAxeItemSprite.setAlpha(1.0f);
		this.mEquipmentAxeItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentAxeItemSprite);
		this.registerTouchArea(mEquipmentAxeItemSprite);
		
		
		//legs
		this.mEquipmentLegsItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 200,this.mEquipmentLegsItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentLegsItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentLegsItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentLegsItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentLegsItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentLegsItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentLegsItemSprite)== true){
						GameMenuScene.this.mEquipmentLegsSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentLegsItemSprite.setPosition(GameMenuScene.this.mEquipmentLegsSprite.getX() + GameMenuScene.this.mEquipmentLegsSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentLegsItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentLegsSprite.getY() + GameMenuScene.this.mEquipmentLegsSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentLegsItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentLegsSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentLegsItemSprite.setPosition(GameMenuScene.this.mEquipmentLegsItemSprite.getInitialX(),GameMenuScene.this.mEquipmentLegsItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentLegsItemSprite.setPosition(GameMenuScene.this.mEquipmentLegsItemSprite.getInitialX(),GameMenuScene.this.mEquipmentLegsItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentLegsItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentLegsItemSprite.setAlpha(0.97f);
		this.mEquipmentLegsItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentLegsItemSprite);
		this.registerTouchArea(mEquipmentLegsItemSprite);
		
		
		
		
		//helm 1
		this.mEquipmentHelm1ItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 250,this.mEquipmentHelm1ItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentHelm1ItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentHelm1ItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentHelm1ItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentHelm1ItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentHelm1ItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentHelm1ItemSprite)== true){
						GameMenuScene.this.mEquipmentHelmSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentHelm1ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelmSprite.getX() + GameMenuScene.this.mEquipmentHelmSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentHelm1ItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentHelmSprite.getY() + GameMenuScene.this.mEquipmentHelmSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentHelm1ItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentHelmSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentHelm1ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelm1ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentHelm1ItemSprite.getInitialY());
						}
					} else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentHelm1ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelm1ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentHelm1ItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentHelm1ItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentHelm1ItemSprite.setAlpha(0.95f);
		this.mEquipmentHelm1ItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentHelm1ItemSprite);
		this.registerTouchArea(mEquipmentHelm1ItemSprite);
		
		
		
		//helm 2
		this.mEquipmentHelm2ItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 250,this.mEquipmentHelm2ItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentHelm2ItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentHelm2ItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentHelm2ItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentHelm2ItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentHelm2ItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentHelm2ItemSprite)== true){
						GameMenuScene.this.mEquipmentHelmSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentHelm2ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelmSprite.getX() + GameMenuScene.this.mEquipmentHelmSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentHelm2ItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentHelmSprite.getY() + GameMenuScene.this.mEquipmentHelmSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentHelm2ItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentHelmSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentHelm2ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelm2ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentHelm2ItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentHelm2ItemSprite.setPosition(GameMenuScene.this.mEquipmentHelm2ItemSprite.getInitialX(),GameMenuScene.this.mEquipmentHelm2ItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentHelm2ItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentHelm2ItemSprite.setAlpha(0.95f);
		this.mEquipmentHelm2ItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentHelm2ItemSprite);
		this.registerTouchArea(mEquipmentHelm2ItemSprite);
		
		
		//Ring
		this.mEquipmentRingItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 300,this.mEquipmentRingItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentRingItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentRingItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentRingItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentRingItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentRingItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentRingItemSprite)== true){
						GameMenuScene.this.mEquipmentExtraSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentRingItemSprite.setPosition(GameMenuScene.this.mEquipmentExtraSprite.getX() + GameMenuScene.this.mEquipmentExtraSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentRingItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentExtraSprite.getY() + GameMenuScene.this.mEquipmentExtraSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentRingItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentExtraSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentRingItemSprite.setPosition(GameMenuScene.this.mEquipmentRingItemSprite.getInitialX(),GameMenuScene.this.mEquipmentRingItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentRingItemSprite.setPosition(GameMenuScene.this.mEquipmentRingItemSprite.getInitialX(),GameMenuScene.this.mEquipmentRingItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentRingItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentRingItemSprite.setAlpha(0.98f);
		this.mEquipmentRingItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentRingItemSprite);
		this.registerTouchArea(mEquipmentRingItemSprite);
				
		//Necklace
		this.mEquipmentNecklaceItemSprite = new Sprite(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 300,this.mEquipmentNecklaceItemTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameMenuScene.this.mEquipmentNecklaceItemSprite.setScale(3.0f);
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					GameMenuScene.this.mEquipmentNecklaceItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mEquipmentNecklaceItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mEquipmentNecklaceItemSprite.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					if(GameMenuScene.this.mEquipmentNecklaceItemSprite.collidesWith(mEquipmentBoxSprite)){
						if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentNecklaceItemSprite)== true){
						GameMenuScene.this.mEquipmentExtraSprite.setAlpha(0.5f);
						GameMenuScene.this.mEquipmentNecklaceItemSprite.setPosition(GameMenuScene.this.mEquipmentExtraSprite.getX() + GameMenuScene.this.mEquipmentExtraSprite.getWidth() / 2 - GameMenuScene.this.mEquipmentNecklaceItemSprite.getWidth() / 2,GameMenuScene.this.mEquipmentExtraSprite.getY() + GameMenuScene.this.mEquipmentExtraSprite.getHeight() / 2 - GameMenuScene.this.mEquipmentNecklaceItemSprite.getHeight() / 2);
						}else {
						GameMenuScene.this.mEquipmentExtraSprite.setAlpha(1.0f);
						GameMenuScene.this.mEquipmentNecklaceItemSprite.setPosition(GameMenuScene.this.mEquipmentNecklaceItemSprite.getInitialX(),GameMenuScene.this.mEquipmentNecklaceItemSprite.getInitialY());
						}
					}else{
						if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
							GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
						}
						GameMenuScene.this.mEquipmentNecklaceItemSprite.setPosition(GameMenuScene.this.mEquipmentNecklaceItemSprite.getInitialX(),GameMenuScene.this.mEquipmentNecklaceItemSprite.getInitialY());
					}
					GameMenuScene.this.mEquipmentNecklaceItemSprite.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		this.mEquipmentNecklaceItemSprite.setAlpha(0.98f);
		this.mEquipmentNecklaceItemSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(mEquipmentNecklaceItemSprite);
		this.registerTouchArea(mEquipmentNecklaceItemSprite);
		
		
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







