package com.quest.scenes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseBackOut;

import android.util.Log;
import android.view.KeyEvent;

import com.quest.database.DataHandler;
import com.quest.entities.objects.ItemIcon;
import com.quest.entities.objects.SpellIcon;
import com.quest.game.Game;
import com.quest.helpers.EquipmentHelper;
import com.quest.methods.Point;


public class GameMenuScene extends Scene{// implements IOnSceneTouchListener{
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
		private Entity mEquipmentUnEquippedItemsEntity;
	private Entity mSkillsEntity;
		private Entity mSkillTreeEntity;
	private Entity mAttributesEntity;
	private Entity mOtherEntity;
	private Entity mSettingsEntity;
	private Entity mCurrentEntity;
	

	//Textures Atlas
	private BitmapTextureAtlas mSceneTextureAtlas;
	private BitmapTextureAtlas mInventoryTextureAtlas;
		private BitmapTextureAtlas mInventoryItemsTextureAtlas;
	private BitmapTextureAtlas mEquipmentTextureAtlas;
		private BitmapTextureAtlas mEquipmentEquippedItemsTextureAtlas;
		private BitmapTextureAtlas mEquipmentUnEquippedItemsTextureAtlas;
	private BitmapTextureAtlas mSkillsTextureAtlas;
		private BitmapTextureAtlas mSkillTreeAtlas;
	private BitmapTextureAtlas mAttributesTextureAtlas;
	private BitmapTextureAtlas mOtherTextureAtlas;
		
	//Texture regions
	private ITextureRegion mInventoryTabTextureRegion;
		private ITextureRegion mInventoryUseTextureRegion;
		private ITextureRegion mInventoryTossTextureRegion;
		private ITextureRegion mInventoryMoneyTextureRegion;
		private ITextureRegion mInventoryItemsBackgroundTextureRegion;
		private ITextureRegion mInventoryDescriptionTextureRegion;
	private ITextureRegion mEquipmentTabTextureRegion;
		private ITextureRegion mEquipmentBoxTextureRegion;
		private ITextureRegion mEquipmentAttributesTextureRegion;
		private ITextureRegion mEquipmentItemsTextureRegion;
		private ITextureRegion mEquipmentHeadTextureRegion;
		private ITextureRegion mEquipmentBodyTextureRegion;
		private ITextureRegion mEquipmentLegsTextureRegion;
		private ITextureRegion mEquipmentWeaponTextureRegion;
		private ITextureRegion mEquipmentOffhandTextureRegion;
		private ITextureRegion mEquipmentExtraTextureRegion;
	private ITextureRegion mSkillsTabTextureRegion;
		private ITextureRegion mSkillTreeBackgroundTextureRegion;
		private ITextureRegion mSkillBarTextureRegion;
	private ITextureRegion mAttributesTabTextureRegion;
	private ITextureRegion mOtherTabTextureRegion;
	private ITextureRegion mSettingsTextureRegion;
	private ITextureRegion mBackgroundTextureRegion;
		
	
	
	//Sprites
	private Sprite mInventoryTabSprite;
		private Sprite mInventoryUseSprite;
		private Sprite mInventoryTossSprite;
		private Sprite mInventoryMoneySprite;
		private Sprite mInventoryItemsBackgroundSprite;
		private Sprite mInventoryDescriptionSprite;
		private Sprite mItemSprite;
	private Sprite mEquipmentTabSprite;
		private Sprite mEquipmentBoxSprite;
		private Sprite mEquipmentBox2Sprite;
		private Sprite mEquipmentAttributesSprite;
		private Sprite mEquipmentItemsSprite;
		private Sprite mEquipmentHeadSprite;
		private Sprite mEquipmentBodySprite;
		private Sprite mEquipmentLegsSprite;
		private Sprite mEquipmentWeaponSprite;
		private Sprite mEquipmentOffhandSprite;
		private Sprite mEquipmentExtraSprite;
	private Sprite mSkillsTabSprite;
		private Sprite mSkillTreeBackgroundSprite; 
		private Sprite mSkillBarSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mOtherTabSprite;
	private Sprite mSettingsSprite;
	private Sprite mBackgroundSprite;
	
	
	//Handlers, Helpers y Managers
	private EquipmentHelper mEquipmentManager;
	private DataHandler mDataHandler;
	
	
	//Lists y arrays
	private ArrayList<ItemIcon> mItemsList;
	private ArrayList<Point> mSpellIconPosition;
	
	//Otros
	private int mUnEquippedCount = 0; 
	private boolean mUsed = false;
	
	  	
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public GameMenuScene(){
		this.mDataHandler = new DataHandler();
		this.mGameMenuEntity = new Entity(0,0);
		this.mInventoryEntity = new Entity(0,0);
		this.mEquipmentEntity = new Entity(0,0);
		this.mSkillsEntity = new Entity(0,0);
		this.mAttributesEntity = new Entity(0,0);
		this.mOtherEntity = new Entity(0,0);
		this.mSettingsEntity = new Entity(0,0);
		
		
		this.mEquipmentManager = new EquipmentHelper(this.mDataHandler,GameMenuScene.this);
		
		//###################COMIENZO DE ENTIDAD PRINCIPAL############################
		
		this.attachChild(mGameMenuEntity);					
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
		this.mSettingsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Settings.png", 800, 750);
		this.mInventoryTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 800, 0);
		this.mEquipmentTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Equipment.png", 800, 150);
		this.mSkillsTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Skills.png", 800, 300);
		this.mAttributesTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 800, 450);
		this.mOtherTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Other.png", 800, 600);
		this.mSceneTextureAtlas.load();
		//Cargar los otros atlas ahora?
	
		//Backgroud
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.mGameMenuEntity.attachChild(mBackgroundSprite);		
		
		//Settings
		this.mSettingsSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 64, 5,this.mSettingsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;					
						}
						break;
					case TouchEvent.ACTION_UP:	
						if(this.mGrabbed) {
							this.mGrabbed = false;	
							GameMenuScene.this.mInventoryTabSprite.setAlpha(0.5f);
							GameMenuScene.this.clearTouchAreas();
							UnloadEntity(mCurrentEntity);
							mCurrentEntity = LoadSettingsEntity();
							GameMenuScene.this.attachChild(mCurrentEntity);
							GameMenuScene.this.loadTabTouchAreas();
						}
						break;
					}
					return true;
					}					
				};
				this.mSettingsSprite.setScale(0.8f);
				this.mGameMenuEntity.attachChild(mSettingsSprite);
				
		
		//Inventory Tab
		this.mInventoryTabSprite = new Sprite(0, 0,this.mInventoryTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;					
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(this.mGrabbed) {
						this.mGrabbed = false;			
						GameMenuScene.this.mInventoryTabSprite.setAlpha(0.5f);
						GameMenuScene.this.clearTouchAreas();
						UnloadEntity(mCurrentEntity);
						mCurrentEntity = LoadInventoryEntity();
						GameMenuScene.this.attachChild(mCurrentEntity);
						GameMenuScene.this.loadTabTouchAreas();
					}
					break;
				}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mInventoryTabSprite);
		
		
		//Equipment Tab
		this.mEquipmentTabSprite = new Sprite(150, 0,this.mEquipmentTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;					
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(this.mGrabbed) {
						this.mGrabbed = false;	
						GameMenuScene.this.mEquipmentTabSprite.setAlpha(0.5f);
						GameMenuScene.this.clearTouchAreas();
						UnloadEntity(mCurrentEntity);
						mCurrentEntity = LoadEquipmentEntity();
						GameMenuScene.this.attachChild(mCurrentEntity);
						GameMenuScene.this.loadTabTouchAreas();
					}
					break;
				}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mEquipmentTabSprite);
		
		
		//Skills Tab
		this.mSkillsTabSprite = new Sprite(300, 0,this.mSkillsTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(this.mGrabbed) {
						this.mGrabbed = false;	
						GameMenuScene.this.mSkillsTabSprite.setAlpha(0.5f);
						GameMenuScene.this.clearTouchAreas();
						UnloadEntity(mCurrentEntity);
						mCurrentEntity = LoadSkillsEntity();
						GameMenuScene.this.attachChild(mCurrentEntity);
						GameMenuScene.this.loadTabTouchAreas();
					}
					break;
				}
				return true;
				}					
		};
		this.mGameMenuEntity.attachChild(mSkillsTabSprite);
		
		//Attributes Tab
		this.mAttributesTabSprite = new Sprite(450, 0,this.mAttributesTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;	
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(this.mGrabbed) {
						this.mGrabbed = false;	
						GameMenuScene.this.mAttributesTabSprite.setAlpha(0.5f);
						GameMenuScene.this.clearTouchAreas();
						UnloadEntity(mCurrentEntity);
						mCurrentEntity = LoadAttributesEntity();
						GameMenuScene.this.attachChild(mCurrentEntity);
						GameMenuScene.this.loadTabTouchAreas();
					}
				break;
				}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mAttributesTabSprite);
		

		//Other Tab
		this.mOtherTabSprite = new Sprite(600, 0,this.mOtherTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(GameMenuScene.this.mUsed == false){
						this.mGrabbed = true;
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(this.mGrabbed) {
						this.mGrabbed = false;	
						GameMenuScene.this.mOtherTabSprite.setAlpha(0.5f);
					UnloadEntity(mCurrentEntity);
					GameMenuScene.this.clearTouchAreas();
					mCurrentEntity = LoadOtherEntity();
					GameMenuScene.this.attachChild(mCurrentEntity);
					GameMenuScene.this.loadTabTouchAreas();
					}
				break;
			}
			return true;
			}					
		};
		this.mGameMenuEntity.attachChild(mOtherTabSprite);
		
			
		
		
					
		this.mInventoryTabSprite.setAlpha(0.5f);
		mCurrentEntity = LoadInventoryEntity();
		this.loadTabTouchAreas();
		GameMenuScene.this.attachChild(mCurrentEntity);
		
		//##############FIN DE LA ENTIDAD PRINCIPAL########################
		
		this.setTouchAreaBindingOnActionDownEnabled(true);
		
	}
			
	public void loadTabTouchAreas(){
		this.registerTouchArea(this.mSettingsSprite);
		this.registerTouchArea(this.mInventoryTabSprite);
		this.registerTouchArea(this.mEquipmentTabSprite);
		this.registerTouchArea(this.mSkillsTabSprite);
		this.registerTouchArea(this.mAttributesTabSprite);
		this.registerTouchArea(this.mOtherTabSprite);
	}
	
	
	
	//#################INVENTORY ENTITY######################
	//COSAS DE FACU A ORDENAR
	private float mInventoryItemsInitialX = 0;
	private float mInventoryItemsFinalX = 0;
	private float mInventoryItemsInitialY = 0; // hacer
	private float mInventoryItemsFinalY = 0; // hacer
	
	private boolean mInventoryItemsTimerActivo = false;
	private boolean mInventoryItemsEstado = true; // True = items, false = equipo,
	//private float mInventoryItemsDefaultItems = 0; // Posición por defecto de los items.
	//private float mInventoryItemsDefaultEquipment = 0; // Posición por defecto del equipment.
	//private float mInventoryItemsDefaultPosition = 0; // Posición actual del texture.
	private float mInventoryItemsVerticalScrollInitialX = 0;
	private float mInventoryItemsVerticalScrollInitialY = 0;
	private float mInventoryItemsVerticalScrollFinalX = 0;
	private float mInventoryItemsVerticalScrollFinalY = 0;
	
	
	public Entity LoadInventoryEntity(){
				this.mInventoryEntity.detachChildren();//La limpio, necesario?
			
				//COSAS DE FACU A ORDENAR
				float Ancho = Game.getSceneManager().getDisplay().getDisplayWidth();
				float Alto = Game.getSceneManager().getDisplay().getDisplayHeight();
		
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Inventory/");
				this.mInventoryTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
				this.mInventoryUseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "UseEquip.png", 0, 0);
				this.mInventoryTossTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Toss.png", 192, 0);
				this.mInventoryDescriptionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Description.png", 0, 290);
				this.mInventoryMoneyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryTextureAtlas, Game.getInstance().getApplicationContext(), "Money.png", 0, 365);
				this.mInventoryTextureAtlas.load();
				
				//Use Sprite
				this.mInventoryUseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 150,this.mInventoryUseTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryUseSprite);
				
				
				//Toss Sprite
				this.mInventoryTossSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 92, 295,this.mInventoryTossTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryTossSprite);
				
				
				//Money Sprite
				this.mInventoryMoneySprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 110, Game.getSceneManager().getDisplay().getDisplayHeight()-50,this.mInventoryMoneyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryMoneySprite);
				
				//Descripción
				this.mInventoryDescriptionSprite = new Sprite(0, Game.getSceneManager().getDisplay().getDisplayHeight() - 75, this.mInventoryDescriptionTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryDescriptionSprite);
			
				
				
				this.mInventoryItemsTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
				this.mInventoryItemsBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mInventoryItemsTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 0, 0);
				this.mInventoryItemsTextureAtlas.load();
				
				float ItemsX = (float)Ancho*((float)2f/(float)100f);
				float ItemsY = (float)Alto*((float)12f/(float)100f);
				
				this.mInventoryItemsBackgroundSprite = new Sprite(0, ItemsY,this.mInventoryItemsBackgroundTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
					
					@Override
					public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:	
						mInventoryItemsInitialX = pSceneTouchEvent.getX();
						break;
					case TouchEvent.ACTION_UP:
	            		mInventoryItemsFinalX = pSceneTouchEvent.getX();
						break;
					case TouchEvent.ACTION_MOVE:
						mInventoryItemsFinalX = pSceneTouchEvent.getX();
						mInventoryItemsFinalY = pSceneTouchEvent.getY();
						
						mInventoryItemsVerticalScrollFinalX = pSceneTouchEvent.getX();//
						mInventoryItemsVerticalScrollFinalY = pSceneTouchEvent.getY();//
						
						if(mInventoryItemsTimerActivo == false){
							mInventoryItemsTimerActivo = true;
							createSpriteSpawnTimeHandler();	
						}
						
						float ScrollX = mInventoryItemsVerticalScrollFinalX - mInventoryItemsVerticalScrollInitialX;
						float ScrollY = mInventoryItemsVerticalScrollFinalY - mInventoryItemsVerticalScrollInitialY;
						
						if(ScrollX < 4 && ScrollX > (-4)){
							mInventoryItemsBackgroundSprite.setPosition(mInventoryItemsBackgroundSprite.getX(), mInventoryItemsBackgroundSprite.getY() + ScrollY);
						}
						
						mInventoryItemsVerticalScrollInitialX = mInventoryItemsVerticalScrollFinalX;
						mInventoryItemsVerticalScrollInitialY = mInventoryItemsVerticalScrollFinalY;
						break;
					}
					return true;
					}							
				};
				
				
				this.mInventoryEntity.attachChild(mInventoryItemsBackgroundSprite);
				this.registerTouchArea(mInventoryItemsBackgroundSprite);
				
				
					
		return this.mInventoryEntity;
	}	
	
	
	public void createSpriteSpawnTimeHandler()
	{
			TimerHandler InventoryScrollTimerHandler;
	       
	        Game.getInstance().getEngine().registerUpdateHandler(InventoryScrollTimerHandler = new TimerHandler(0.1f, true,new ITimerCallback()
	        {                      
	            @Override
	            public void onTimePassed(final TimerHandler pTimerHandler)
	            {
	            	float Resultado = (float)mInventoryItemsFinalX - (float)mInventoryItemsInitialX;
	            		
	            	//Log.d("logd", "___________________________________");
	            	//Log.d("logd", "Inicial: " + mInventoryItemsInitialX);
	            	//Log.d("logd", "Final: " + mInventoryItemsFinalX);
	            		
	            	if(Resultado < -80 && mInventoryItemsEstado == true){ // izquierda
	            		GameMenuScene.this.mInventoryItemsBackgroundSprite.registerEntityModifier(new MoveModifier(0.5f, mInventoryItemsBackgroundSprite.getX(), -690, mInventoryItemsBackgroundSprite.getY(), mInventoryItemsBackgroundSprite.getY(), EaseBackOut.getInstance()));
	            		mInventoryItemsEstado = false;
	            		
	            		//Log.d("logd", "izquierda con " + Resultado);
	            			
	            	}else if(Resultado > 80 && mInventoryItemsEstado == false){ // derecha		
            			GameMenuScene.this.mInventoryItemsBackgroundSprite.registerEntityModifier(new MoveModifier(0.5f, mInventoryItemsBackgroundSprite.getX(), 0, mInventoryItemsBackgroundSprite.getY(), mInventoryItemsBackgroundSprite.getY(), EaseBackOut.getInstance()));
            			mInventoryItemsEstado = true;
            				
            			//Log.d("logd", "derecha con " + Resultado);
	            	}
	            	
	            	
	            	
	            	mInventoryItemsInitialX = 0f;
	            	mInventoryItemsFinalX = 0f;
	            	mInventoryItemsTimerActivo = false;
	            	
	            	Game.getInstance().getEngine().unregisterUpdateHandler(pTimerHandler);
	            }
	        }));
	}	
		
	
	
	
	
	
	//#################EQUIPMENT ENTITY######################
	public Entity LoadEquipmentEntity(){
		//HACER FUNCION PARA CARGAR EL EQUIPMENT VIEJO, NECESITO EL SQL
		
		this.mEquipmentEntity.detachChildren();//La limpio, necesario?
		this.mEquipmentUnEquippedItemsEntity = new Entity(0,0);
		this.mItemsList = new ArrayList<ItemIcon>();
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/");
		this.mEquipmentTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mEquipmentBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Box.png", 0, 0);
		this.mEquipmentAttributesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Attributes.png", 390, 0);
		this.mEquipmentItemsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Items.png", 390, 35);
		this.mEquipmentHeadTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Head.png", 390, 69);
		this.mEquipmentBodyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Body.png", 390, 150);
		this.mEquipmentLegsTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Legs.png", 390, 250);
		this.mEquipmentWeaponTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Weapon.png", 390, 350);
		this.mEquipmentOffhandTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Offhand.png", 0, 406);
		this.mEquipmentExtraTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Extra.png", 80, 406);
		this.mEquipmentTextureAtlas.load();
		
		
		//Cajas de contenidos
		this.mEquipmentBoxSprite = new Sprite(20, 70,this.mEquipmentBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
		this.mEquipmentEntity.attachChild(mEquipmentBoxSprite);
	
		this.mEquipmentBox2Sprite = new Sprite(415, 70,this.mEquipmentBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
		this.mEquipmentEntity.attachChild(mEquipmentBox2Sprite);
			
		this.mEquipmentUnEquippedItemsEntity.setPosition(this.mEquipmentBox2Sprite.getX()+10,this.mEquipmentBox2Sprite.getY()+45);
		
				
		//Items Tab
		this.mEquipmentItemsSprite = new Sprite(420, 75,this.mEquipmentItemsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
				//	GameMenuScene.this.loadEquippedItems();
				}
				break;
			}
			return true;
			}							
		};
		this.mEquipmentEntity.attachChild(mEquipmentItemsSprite);
		this.registerTouchArea(mEquipmentItemsSprite);
		
		//Attributes Tab
		this.mEquipmentAttributesSprite = new Sprite(612, 75,this.mEquipmentAttributesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		this.mEquipmentEntity.attachChild(mEquipmentAttributesSprite);
		//##########################BODY###################################
		
		//Helm Sprite
		this.mEquipmentHeadSprite = new Sprite(165, 85,this.mEquipmentHeadTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentHeadSprite);
		
		//Plate Sprite
		this.mEquipmentBodySprite = new Sprite(144, 186,this.mEquipmentBodyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
		this.mEquipmentEntity.attachChild(mEquipmentBodySprite);
		
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
		
		this.loadEquippedItems();
		this.mUnEquippedCount = 0;
		this.loadUnEquippedItems();
		this.mEquipmentEntity.attachChild(mEquipmentUnEquippedItemsEntity);
		return this.mEquipmentEntity;
	}
	
	
	
	//################################################################################################
	//								FUNCIONES DE EQUIPMENT ENTITY
	//################################################################################################
	public void EquipItem(ItemIcon pItem, Boolean pColorChange,Boolean pCollides){
		Sprite tempSprite = null;//le doy null porque sino rompe las bolas
		switch(pItem.getType()){//iguala pSprite al container que corresponde
		case 0:
			tempSprite = this.mEquipmentHeadSprite;		
			break;
		case 1:
			tempSprite = this.mEquipmentBodySprite;
			break;
		case 2:
			tempSprite = this.mEquipmentLegsSprite;
			break;
		case 3:
			tempSprite = this.mEquipmentExtraSprite;
			break;
		case 4:
			tempSprite = this.mEquipmentOffhandSprite;
			break;
		case 5:
			tempSprite = this.mEquipmentWeaponSprite;
			break;
		}
		this.ColorSprite(tempSprite, pColorChange);//cambia el color
		
		if(pColorChange == false){//si ColorChange = false (osea que es en el Action_UP)
			this.MoveItem(tempSprite, pItem,pCollides);//Ejecuta el equipamiento
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
	
	
	public void MoveItem(Sprite pSprite, ItemIcon pItem,Boolean pCollides){//Funcion para iniciar equipamiento grafico + helper
		if(pCollides == true){//Si colisiona
			if(this.mEquipmentManager.EquipmentFunction(pItem) == true){//Si se equipo			
				pItem.getIcon().setPosition(pSprite.getX() + pSprite.getWidth() / 2 - pItem.getIcon().getWidth() / 2,pSprite.getY() + pSprite.getHeight() / 2 - pItem.getIcon().getHeight() / 2);//lo pone donde corresponde			
				pSprite.setAlpha(0.5f);
			} else {//Si se desequipo(porque ya estaba equipado) lo devuelve el Unequip				
				pSprite.setAlpha(1.0f);
			}
		} else{//Si no colisiona
			if(this.mEquipmentManager.IsEquipped(pItem,pItem.getType()) == true){//se fija si estaba equipado
				this.mEquipmentManager.UnequipItem(pItem.getType(),pItem);//si lo estaba lo desequipa				
			} else{//Si no estaba equipado
				this.mUnEquippedCount-=1;
				this.PlaceEquipmentItem(pItem);
			}
		}
		this.mUsed = false;
	}
	
	
	private void loadUnEquippedItems(){
		//pedir los items que necesito
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Items/");
		this.mEquipmentUnEquippedItemsTextureAtlas= new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512,24, TextureOptions.BILINEAR);
		int tempArray[] = mDataHandler.getEquippedIDs(0);
		for(int i=0;i<tempArray.length;i++){	//checkear si esta bien restarle 1 al length
			if(this.mDataHandler.getItemClass(tempArray[i]) == this.mDataHandler.getPlayerClass(0)){//checkeo que los items sean de la clase del player
			//	this.mUnEquippedCount+=1;//le sumo uno al unEquipped count para saber cuantos items hay sin equipar  \\ no le sumo, ya lo hace la funcion
			final ItemIcon pItem = new ItemIcon(mDataHandler,mEquipmentUnEquippedItemsTextureAtlas,0+24*i,0,0,0,mEquipmentUnEquippedItemsEntity,GameMenuScene.this,tempArray[i],0);
			this.PlaceEquipmentItem(pItem);
			}//si no son no los cargo wepa
		}
		this.mEquipmentUnEquippedItemsTextureAtlas.load();
	}
	
	
	private void loadEquippedItems(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Items/");
		this.mEquipmentEquippedItemsTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 144,24, TextureOptions.BILINEAR);
		
		int tempArray[] = mDataHandler.getEquippedIDs(1);//consegui las ID de todo lo que esta equipado
		for(int i=0;i<tempArray.length;i++){					
			final ItemIcon pItem = new ItemIcon(mDataHandler,mEquipmentEquippedItemsTextureAtlas,0+24*i,0,0,0,mEquipmentEntity,GameMenuScene.this,tempArray[i],0);
			
			mEquipmentManager.LoadEquippedItem(pItem);
			
			Sprite tempSprite = null;
			switch(pItem.getType()){
			case 0:
				tempSprite = this.mEquipmentHeadSprite;		
				break;
			case 1:
				tempSprite = this.mEquipmentBodySprite;
				break;
			case 2:
				tempSprite = this.mEquipmentLegsSprite;
				break;
			case 3:
				tempSprite = this.mEquipmentExtraSprite;
				break;
			case 4:
				tempSprite = this.mEquipmentOffhandSprite;
				break;
			case 5:
				tempSprite = this.mEquipmentWeaponSprite;
				break;
			}
			pItem.getIcon().setPosition(tempSprite.getX() + tempSprite.getWidth() / 2 - pItem.getIcon().getWidth() / 2,tempSprite.getY() + tempSprite.getHeight() / 2 - pItem.getIcon().getHeight() / 2);
			tempSprite.setAlpha(0.5f);
		}
		this.mEquipmentEquippedItemsTextureAtlas.load();
	}
	
	
	public void PlaceEquipmentItem(ItemIcon pItem){
		if(this.mItemsList.contains(pItem)==false){//si el item no esta en la lista
			this.mItemsList.add(this.mUnEquippedCount, pItem);//agrego el item a la lista en el indice del ultimo item			
			}
		int row = (int)(this.mEquipmentBox2Sprite.getWidth())/ 60;//cuantos items hay por fila
		int tY = (this.mItemsList.indexOf(pItem)/ row) * 60;//divido los items por la cantidad de items por fila, me dice cuantas filas hay.multiplico por 36 (item =24, scale = 1.5 asi que tamaño = 36) y consigo el Y
		int tX = (this.mItemsList.indexOf(pItem) % row) * 60;//me devuelve el resto, se cuantos sobran en una fila
		pItem.getIcon().registerEntityModifier(new MoveModifier(0.25f, pItem.getIcon().getX(), tX, pItem.getIcon().getY(), tY));//, EaseBackOut.getInstance()));
		this.mUnEquippedCount += 1;
	}
	
	public void LowerItemCount(int pStart){
		
		
	}
	
	public int getUnEquippedCount(){
		return this.mUnEquippedCount;
	}
	
	public void setUnEquippedCount(int pUnEquippedCount){
		this.mUnEquippedCount = pUnEquippedCount;
	}
	
	public Sprite getEquipmentBoxSprite(){//para los collides
		return this.mEquipmentBoxSprite;
	}
	
	public Entity getEquipmentEntity(){
		return this.mEquipmentEntity;
	}
	
	public ArrayList getItemList(){
		return this.mItemsList;
	}
	
	public Entity getEquipmentUnEquippedItemsEntity(){
		return this.mEquipmentUnEquippedItemsEntity;
	}
	
	public void setUsed(Boolean pBool){
		this.mUsed = pBool;
	}
	
	public boolean getUsed(){
		return this.mUsed;
	}
	//################################################################################################
	//################################################################################################	
	
	
	//COSAS DE FACU A ORDENAR
	private float mSkillScrollLastX = 0; // La vieja posición del dedo haciendo scroll.
	private float mSkillScrollNewX = 0; // La nueva posición del dedo haciendo scroll.
	private float mSkillScrollDiferencia = 0; // La diferencia entre LastX y NewX.
	private int mSkillScrollImageWidth = 1024; // El ancho de la imagen que se va a scrol,lear. Obligatorio para que funcione bien.
	private short mSkillScrollLastAction = 0; // Si movió a la derecha (1), izquierda (2), o ningún lado (0).
	
	
	//#################SKILLS ENTITY######################	
	public Entity LoadSkillsEntity(){
		this.mSkillsEntity.detachChildren();//La limpio, necesario?
		this.mSkillTreeEntity = new Entity(0,90);
		this.mSpellIconPosition = new ArrayList<Point>(){};
		this.mSpellIconPosition.add(0,new Point(20, 75));
		this.mSpellIconPosition.add(1,new Point(120, 75));
		this.mSpellIconPosition.add(2,new Point(220, 75));
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Skills/");
		this.mSkillsTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
		this.mSkillTreeBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSkillsTextureAtlas, Game.getInstance().getApplicationContext(), "fondo.png", 0, 0);
		this.mSkillBarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSkillsTextureAtlas, Game.getInstance().getApplicationContext(), "SkillBar.png", 0, 350);
		this.mSkillsTextureAtlas.load();
		
		//COSAS DE FACU A ORDENAR
		this.mSkillTreeBackgroundSprite = new Sprite(0, 0,this.mSkillTreeBackgroundTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				mSkillScrollLastX = pSceneTouchEvent.getX();
				mSkillScrollLastAction = 0;
				this.mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				//################################ SCROLL HORIZONTAL DE LOS SKILLS ################################//
				if(this.mGrabbed) {
					mSkillScrollNewX = pSceneTouchEvent.getX();					
					mSkillScrollDiferencia = mSkillScrollLastX - mSkillScrollNewX;
					
					if(mSkillScrollDiferencia == (-mSkillScrollNewX)) mSkillScrollDiferencia = 0;
					
					float mTreeX = GameMenuScene.this.mSkillTreeEntity.getX();
					
					// Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100) ===> Cuenta que centra la imagen en la mitad de la
					// pantalla. En resolución width 800, hay 21 pixeles que separan la imagen de los bordes horizontales.
					
					if(mSkillScrollDiferencia > 0){
						// MUEVE EL DEDO HACIA LA IZQUIERDA (SKILL TREE HACIA LA IZQUIERDA)
						mSkillScrollLastAction = 2;
						
						if(mTreeX > Game.getSceneManager().getDisplay().getDisplayWidth() - mSkillScrollImageWidth - (Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100))){
							GameMenuScene.this.mSkillTreeEntity.setX(mTreeX - mSkillScrollDiferencia);
						}
					}else{
						// MUEVE EL DEDO HACIA LA DERECHA (SKILL TREE HACIA LA DERECHA)
						mSkillScrollLastAction = 1;
						
						if(mTreeX < Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100)){
							GameMenuScene.this.mSkillTreeEntity.setX(mTreeX - mSkillScrollDiferencia);
						}
					}
					
					mSkillScrollLastX = mSkillScrollNewX;
				}
				break;
			case TouchEvent.ACTION_UP:
				if(this.mGrabbed) {
					this.mGrabbed = false;
					
					//################################ REBOTE EN EL SCROLL HORIZONTAL DE LOS SKILLS ################################//
					float mTreeX = GameMenuScene.this.mSkillTreeEntity.getX();
					float mTreeY = GameMenuScene.this.mSkillTreeEntity.getY();
					
					float mLimiteIzquierda = (float)(Game.getSceneManager().getDisplay().getDisplayWidth() - mSkillScrollImageWidth - (Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100)));
					float mLimiteDerecha = (float)(Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100));
					
					if(mTreeX > mLimiteDerecha + 5 && mSkillScrollLastAction == 1){
						GameMenuScene.this.mSkillTreeEntity.registerEntityModifier(new MoveModifier(0.4f, mTreeX, mLimiteDerecha - 1, mTreeY, mTreeY, EaseBackOut.getInstance()));
					
					}else if(mTreeX < mLimiteIzquierda - 5 && mSkillScrollLastAction == 2){
						GameMenuScene.this.mSkillTreeEntity.registerEntityModifier(new MoveModifier(0.4f, mTreeX, mLimiteIzquierda + 1, mTreeY, mTreeY, EaseBackOut.getInstance()));
					}
				}
				break;
			}
			return true;
			}					
		};
		this.mSkillTreeBackgroundSprite.setAlpha(0.9f);
		this.registerTouchArea(mSkillTreeBackgroundSprite);


		// ########################## SHORTCUTS ############################
	
			this.mSkillBarSprite = new Sprite(284, 74,this.mSkillBarTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				Log.d("logd", "down");
				break;
			case TouchEvent.ACTION_MOVE:
				Log.d("logd", "move");
				break;
			case TouchEvent.ACTION_UP:
				Log.d("logd", "up");
				break;
			}
			return true;
			}					
		};
		this.mSkillBarSprite.setAlpha(1f);
		this.registerTouchArea(mSkillBarSprite);
		
		this.mSkillTreeEntity.attachChild(mSkillTreeBackgroundSprite);
		this.mSkillTreeEntity.attachChild(mSkillBarSprite);
		
		
		this.LoadSkillsTree();
		this.LoadUserSpells();
		this.mSkillsEntity.attachChild(mSkillTreeEntity);
		return this.mSkillsEntity;
	}

	//################################################################################################
	//								FUNCIONES DE SKILLS ENTITY
	//################################################################################################	
	public void LoadSkillsTree(){
		Log.d("Logd", "loadskilltree");
		//hacer query a la base de datos con la clase del player y que me devuelva un array con todos los hechizos de esa clase
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mSkillTreeAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512,512, TextureOptions.BILINEAR);
		int tempArray[] = this.mDataHandler.getClassSpells(this.mDataHandler.getPlayerClass(1));//Le paso la class del player, despues definirla en la entidad
		//recorrer el array creando sprites dandoles de X y de Y lo que esta en el arraylist en la posicion ID
		
		for(int i=0;i<tempArray.length;i++){
			
			Log.d("Logd", "i: "+String.valueOf(i));
			Log.d("Logd", "length: "+String.valueOf(tempArray.length));
			final SpellIcon pIcon = new SpellIcon(mDataHandler,this.mSkillTreeAtlas,i*34,0,0,0,this.mSkillTreeEntity,GameMenuScene.this,tempArray[i]);
			this.placeSpellIcon(pIcon);
		}
		this.mSkillTreeAtlas.load();
	}
	
	public void placeSpellIcon(SpellIcon pIcon){
		pIcon.getIcon().setPosition(this.mSpellIconPosition.get(pIcon.getID()).getX(),this.mSpellIconPosition.get(pIcon.getID()).getY());
		Log.d("Logd", "icono x: " + String.valueOf(this.mSpellIconPosition.get(pIcon.getID()).getX()));
		Log.d("Logd", "icono y: "+ String.valueOf(this.mSpellIconPosition.get(pIcon.getID()).getY()));
	}
	
	public void LoadUserSpells(){
		
	}
	//################################################################################################
	//################################################################################################	
	
	
	
	//#################ATTRIBUTES ENTITY######################
	public Entity LoadAttributesEntity(){
		this.mAttributesEntity.detachChildren();//La limpio, necesario?
		
		
		return this.mAttributesEntity;
	}
	
	
	
	//#################Other ENTITY######################
	public Entity LoadOtherEntity(){
		this.mOtherEntity.detachChildren();//La limpio, necesario?


		return this.mOtherEntity;
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
	
	/*
	//key back
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	        Log.d("Logd", "back button pressed");
	    }
	    return super.onKeyDown(keyCode, event);
	}
		
	*/
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







