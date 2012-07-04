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
	private Entity mSettingsEntity;
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
	private ITextureRegion mSettingsTextureRegion;
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
		
	private Sprite mSkillsTabSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mInfoTabSprite;
	private Sprite mSettingsSprite;
	private Sprite mBackgroundSprite;
	
	
	private EquipmentHelper mEquipmentManager;
	
	//FALTA HACER BIEN LA CARGA DE ENTIDADES
	//FALTA HACER LA CARGA DE TEXTURAS DINAMICAS
	
	
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
		this.mSettingsEntity = new Entity(0,0);
		
		this.mEquipmentManager = new EquipmentHelper();
		
		//###################COMIENZO DE ENTIDAD PRINCIPAL############################
		
		this.attachChild(mGameMenuEntity);					
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
		this.mSettingsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Settings.png", 800, 0);
		this.mInventoryTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 800, 64);
		this.mEquipmentTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Equipment.png", 800, 114);
		this.mSkillsTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Skills.png", 800, 164);
		this.mAttributesTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 800, 214);
		this.mInfoTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Info.png", 800, 264);
		this.mSceneTextureAtlas.load();
		//Cargar los otros atlas ahora?
	
		//Backgroud
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.mGameMenuEntity.attachChild(mBackgroundSprite);		
		
		//Settings
		this.mSettingsSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 64, 20,this.mSettingsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
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
						mCurrentEntity = LoadSettingsEntity();
						GameMenuScene.this.attachChild(mCurrentEntity);
						break;
					}
					return true;
					}					
				};
				this.mSettingsSprite.setScale(0.8f);
				this.mGameMenuEntity.attachChild(mSettingsSprite);
				
		
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
		
			
		//this.registerTouchArea(this.mSettingsSprite);
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
							GameMenuScene.this.mItemSprite.setScale(3.0f);
							this.mGrabbed = true;
							break;
						case TouchEvent.ACTION_MOVE:
							if(this.mGrabbed) {
								GameMenuScene.this.mItemSprite.setPosition(pSceneTouchEvent.getX() - GameMenuScene.this.mItemSprite.getWidth() / 2, pSceneTouchEvent.getY() - GameMenuScene.this.mItemSprite.getHeight() / 2);
							}
							break;
						case TouchEvent.ACTION_UP:
							if(this.mGrabbed) {
								this.mGrabbed = false;
								if(GameMenuScene.this.mItemSprite.collidesWith(mInventoryUseSprite)){
								//	if(GameMenuScene.this.mEquipmentManager.EquipmentFunction(mEquipmentSwordItemSprite)== true){
									//borrarlo, se usa
								//	}else {
									GameMenuScene.this.mItemSprite.setPosition(GameMenuScene.this.mItemSprite.getInitialX(),GameMenuScene.this.mItemSprite.getInitialY());
								//	}
								}else{
								//	if(GameMenuScene.this.mEquipmentManager.IsEquiped(this,GameMenuScene.this.mEquipmentManager.SortEquip(this)) == true){
								//		GameMenuScene.this.mEquipmentManager.UnequipItem(this,GameMenuScene.this.mEquipmentManager.SortEquip(this));
								//	}
								//	GameMenuScene.this.mItemSprite.setPosition(GameMenuScene.this.mItemSprite.getInitialX(),GameMenuScene.this.mItemSprite.getInitialY());
									}
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
		//HACER FUNCION PARA CARGAR EL EQUIPMENT VIEJO, NECESITO EL SQL
		
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
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;					
					GameMenuScene.this.mEquipmentItemsSprite.setAlpha(0.5f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 50,GameMenuScene.this.mEquipmentSwordItemTextureRegion, 1.0f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 100,GameMenuScene.this.mEquipmentShieldItemTextureRegion,0.99f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 150,GameMenuScene.this.mEquipmentPlate1ItemTextureRegion,0.96f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 150,GameMenuScene.this.mEquipmentPlate2ItemTextureRegion,0.96f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 50,GameMenuScene.this.mEquipmentAxeItemTextureRegion,1.0f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 200,GameMenuScene.this.mEquipmentLegsItemTextureRegion,0.97f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 250,GameMenuScene.this.mEquipmentHelm1ItemTextureRegion,0.95f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 250,GameMenuScene.this.mEquipmentHelm2ItemTextureRegion,0.95f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 50,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 300,GameMenuScene.this.mEquipmentRingItemTextureRegion,0.98f);
					GameMenuScene.this.addItem(GameMenuScene.this.mEquipmentBox2Sprite.getX() + 100,GameMenuScene.this.mEquipmentBox2Sprite.getY() + 300,GameMenuScene.this.mEquipmentNecklaceItemTextureRegion,0.98f);
				}
				break;
			}
			return true;
			}							
		};
		this.mEquipmentEntity.attachChild(mEquipmentItemsSprite);
		
		//Attributes Tab
		this.mEquipmentAttributesSprite = new Sprite(665, 75,this.mEquipmentAttributesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		this.mEquipmentHelmSprite = new Sprite(165, 85,this.mEquipmentHelmTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentHelmSprite);
		
		//Plate Sprite
		this.mEquipmentPlateSprite = new Sprite(144, 186,this.mEquipmentPlateTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentPlateSprite);
		
		//Legs Sprite
		this.mEquipmentLegsSprite = new Sprite(169, 306,this.mEquipmentLegsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
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
		this.mEquipmentOffhandSprite = new Sprite(284, 180,this.mEquipmentOffhandTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentOffhandSprite);
		
		
		//Extra Sprite
		this.mEquipmentExtraSprite = new Sprite(272, 311,this.mEquipmentExtraTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentExtraSprite);
		
				//Funcion para registrar touch areas??
		this.registerTouchArea(this.mEquipmentAttributesSprite);
		this.registerTouchArea(this.mEquipmentItemsSprite);

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
			
		return this.mEquipmentEntity;
	}
	
	
	
	//################################################################################################
	//								FUNCIONES DE EQUIPMENT ENTITY
	//################################################################################################
	public void EquipItem(Sprite pSprite, Boolean pColorChange,Boolean pCollides){
		final Sprite tempSprite = pSprite;//es final, me cambia algo?
		switch(this.mEquipmentManager.SortEquip(pSprite)){//iguala pSprite al container que corresponde
		case 0:
			pSprite = this.mEquipmentHelmSprite;		
			break;
		case 1:
			pSprite = this.mEquipmentPlateSprite;
			break;
		case 2:
			pSprite = this.mEquipmentLegsSprite;
			break;
		case 3:
			pSprite = this.mEquipmentExtraSprite;
			break;
		case 4:
			pSprite = this.mEquipmentOffhandSprite;
			break;
		case 5:
			pSprite = this.mEquipmentWeaponSprite;
			break;
		}
		this.ColorSprite(pSprite, pColorChange);//cambia el color
		
		if(pColorChange == false){//si ColorChange = false (osea que es en el Action_UP)
			this.MoveItem(pSprite, tempSprite,pCollides);//Ejecuta el equipamiento
		}
		
	}
	
	public void ColorSprite(Sprite pSprite, Boolean pChangeColor){//funcion para cambiar colores
			if(pChangeColor==true){//si es en el Action_DOWN cambia el color a verde
				pSprite.setGreen(1f);
				pSprite.setRed(0.2f);
				pSprite.setBlue(0.2f);
			}else {//Si es en el Action_UP resetea el color ***ESTA ANDANDO MAL***
				pSprite.setGreen(0f);
				pSprite.setRed(0f);
				pSprite.setBlue(0f);			
			}
			
		}
	
	
	public void MoveItem(Sprite pSprite, Sprite pItemSprite,Boolean pCollides){//Funcion para iniciar equipamiento grafico + helper
		if(pCollides == true){//Si colisiona
			if(this.mEquipmentManager.EquipmentFunction(pItemSprite) == true){//Si se equipo				
				pItemSprite.setPosition(pSprite.getX() + pSprite.getWidth() / 2 - pItemSprite.getWidth() / 2,pSprite.getY() + pSprite.getHeight() / 2 - pItemSprite.getHeight() / 2);//lo pone donde corresponde
				pSprite.setAlpha(0.5f);
			} else {//Si se desequipo(porque ya estaba equipado) lo devuelve al final				
				pSprite.setAlpha(1.0f);
			}
		} else{//Si no colisiona
			if(this.mEquipmentManager.IsEquiped(pItemSprite,this.mEquipmentManager.SortEquip(pItemSprite)) == true){//se fija si estaba equipado
			this.mEquipmentManager.UnequipItem(pItemSprite,this.mEquipmentManager.SortEquip(pItemSprite));//si lo estaba lo desequipa
			pItemSprite.setPosition(pItemSprite.getInitialX(),pItemSprite.getInitialY());//despues hacer la funcion que se fije donde quedaria(lo devuelve al final)
			} else{//Si no estaba equipado
				pItemSprite.setPosition(pItemSprite.getInitialX(),pItemSprite.getInitialY());//despues hacer la funcion que se fije donde quedaria(tiene que volver a donde estaba, no al final)
			}
		}
	}
	
	
	private void loadInventory(){
		//cargar el inventory de la base de datos
		//necesito saber cuantos items hay para saber como ordenarlo? (o usar directamente la row en que esta como pos)
	//	this.addItem(400,500/*checkitem*/);
	}
	
	private void checkItem(){
		//checkear si el item es equipamiento, consumible o de quest (esta funcion sirve para todo, no solo equipment)
	
		
		//return el tipo de item
	}
	
	//#####FALTA HACER LA CARGA DE TEXTURES DINAMICA
	private void addItem(float pX, float pY,ITextureRegion pTextureRegion,float pAlpha/*TIPO de ITEM*/){
		//si es equipment
			//new sprite
		final Sprite tempSprite = new Sprite(pX,pY, pTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setScale(3.0f);
				GameMenuScene.this.EquipItem(this,true,false);
				mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;					
					GameMenuScene.this.EquipItem(this, false,this.collidesWith(mEquipmentBoxSprite));//inicia la funcion de equipamiento, si devuelve true hace el equipamiento grafico, si devuelve false no la hace(la devuelve al lugar de antes), tambien se fija si hay colision 
					this.setScale(2.0f);
				}
				break;
			}
			return true;
			}					
		};
		tempSprite.setAlpha(pAlpha);
		tempSprite.setScale(2.0f);
		this.mEquipmentEntity.attachChild(tempSprite);
		this.registerTouchArea(tempSprite);
	}
	//################################################################################################
	//################################################################################################	
	
	
	
	
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
	
	
	//##################SETTINGS ENTITY###################
	public Entity LoadSettingsEntity(){
		this.mSettingsEntity.detachChildren();//La limpio, necesario?


		return this.mSettingsEntity;
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







