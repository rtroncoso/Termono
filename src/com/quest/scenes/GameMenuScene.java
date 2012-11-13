package com.quest.scenes;

import java.security.acl.Owner;
import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseBackOut;

import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.database.DataHandler;
import com.quest.entities.Player;
import com.quest.entities.objects.Item;
import com.quest.entities.objects.ItemIcon;
import com.quest.entities.objects.SpellIcon;
import com.quest.game.Game;
import com.quest.helpers.EquipmentHelper;
import com.quest.helpers.InventoryItemHelper;
import com.quest.methods.Point;


public class GameMenuScene extends Scene implements GameFlags{// implements IOnSceneTouchListener{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mCurrentTab = 0;//0 default, 1 Inv, 2 Equipment, 3 Skills, 4 Atts, 5 Other, 6 Settings
	
	private Entity mGameMenuEntity;
	private BitmapTextureAtlas mSceneTextureAtlas;
	private ITextureRegion mBackgroundTextureRegion;
	private ITextureRegion mPlankTextureRegion;
	private ITextureRegion mInventoryTabTextureRegion;
	private ITextureRegion mEquipmentTabTextureRegion;
	private ITextureRegion mSkillsTabTextureRegion;
	private ITextureRegion mAttributesTabTextureRegion;
	private ITextureRegion mOtherTabTextureRegion;
	private ITextureRegion mSettingsTextureRegion;
	private Sprite mBackgroundSprite;
	private Sprite mPlankSprite;
	private Sprite mInventoryTabSprite;
	private Sprite mEquipmentTabSprite;
	private Sprite mSkillsTabSprite;
	private Sprite mAttributesTabSprite;
	private Sprite mOtherTabSprite;
	private Sprite mSettingsSprite;
	
	private Entity mInventoryEntity;
	private BitmapTextureAtlas mInventoryTextureAtlas;//para iconos
	private ITextureRegion mInventoryBoxTextureRegion;
	private ITextureRegion mInventoryMoneyTextureRegion;
	private ITextureRegion mInventoryDescriptionTextureRegion;
	private Sprite mInventoryUseSprite;
	private Sprite mInventoryTossSprite;
	private Sprite mInventoryMoneySprite;
	private Sprite mInventoryDescriptionSprite;
	private Text mInventoryMoneyText;
	
	private Entity mAttributesEntity;
	private ITextureRegion mAttributesBoxTextureRegion;
	private ITextureRegion mAttributesPlusTextureRegion;
	private ITextureRegion mAttributesMinusTextureRegion;
	private ITextureRegion mAttributesConfirmTextureRegion;
	private Sprite[] mAttributesBoxSprite = new Sprite[4];
	private Sprite[] mAttributesPlusSprite = new Sprite[4];
	private Sprite[] mAttributesMinusSprite = new Sprite[4];
	private Sprite mAttributesDescriptionSprite;
	private Sprite mAttributesConfirmSprite;
	private Text mAttributesUnassignedPointsText;
	private Text[] mAttributesTexts = new Text[4];
	private Text mAttributesDescriptionText;
	private int[] pAttributes;
	private int pUnassigned;
	//HACER UN BOOLEAN PARA CURRENT TAB
	
	
	private Entity mInventoryItemsEntity;
	
	
	//Entidades
	
	
	private Entity mEquipmentEntity;
	private Entity mEquipmentUnEquippedItemsEntity;
	private Entity mSkillsEntity;
	private Entity mSkillTreeEntity;
	
	private Entity mOtherEntity;
	private Entity mSettingsEntity;
	private Entity mCurrentEntity;
	

	//Textures Atlas
	
	
	private BitmapTextureAtlas mInventoryItemsTextureAtlas;
	private BitmapTextureAtlas mEquipmentTextureAtlas;
		private BitmapTextureAtlas mEquipmentEquippedItemsTextureAtlas;
		private BitmapTextureAtlas mEquipmentUnEquippedItemsTextureAtlas;
	private BitmapTextureAtlas mSkillsTextureAtlas;
		private BitmapTextureAtlas mSkillTreeAtlas;
	private BitmapTextureAtlas mAttributesTextureAtlas;
	private BitmapTextureAtlas mOtherTextureAtlas;
		
	//Texture regions
	
	
		private ITextureRegion mEquipmentBoxTextureRegion;
		private ITextureRegion mEquipmentAttributesTextureRegion;
		private ITextureRegion mEquipmentItemsTextureRegion;
		private ITextureRegion mEquipmentHeadTextureRegion;
		private ITextureRegion mEquipmentBodyTextureRegion;
		private ITextureRegion mEquipmentLegsTextureRegion;
		private ITextureRegion mEquipmentWeaponTextureRegion;
		private ITextureRegion mEquipmentOffhandTextureRegion;
		private ITextureRegion mEquipmentExtraTextureRegion;
	
		private ITextureRegion mSkillTreeBackgroundTextureRegion;
		private ITextureRegion mSkillBarTextureRegion;
	
		
	
	
	//Sprites
	

		

		private Sprite mItemSprite;
		
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
		
		private Sprite mSkillTreeBackgroundSprite; 
		private Sprite mSkillBarSprite;
	
	
	
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
		this.mCurrentEntity = new Entity(0,0);
		this.mGameMenuEntity = new Entity(0,0);
		this.mEquipmentEntity = new Entity(0,0);
		this.mSkillsEntity = new Entity(0,0);
		this.mOtherEntity = new Entity(0,0);
		this.mSettingsEntity = new Entity(0,0);
		
		
		this.mEquipmentManager = new EquipmentHelper(this.mDataHandler,GameMenuScene.this);
		
		//###################COMIENZO DE ENTIDAD PRINCIPAL############################
		
		this.attachChild(mGameMenuEntity);					
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/");
		this.mSceneTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);//http://i.imgur.com/ryypV.png
		this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Background.png", 0, 0);
		this.mPlankTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Plank.png", 0, 480);
		this.mInventoryTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory.png", 800, 0);
		this.mEquipmentTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Equipment.png", 800, 87);
		this.mSkillsTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Skills.png", 800, 174);
		this.mAttributesTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes.png", 800, 261);
		this.mOtherTabTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Other.png", 800, 348);
		this.mSettingsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Settings.png", 950, 0);
		//inventory
		this.mInventoryDescriptionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory/Description.png", 0, 563);
		this.mInventoryBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory/UseBack.png", 800, 435);
		this.mInventoryMoneyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Inventory/Money.png", 950, 64);
		//Attributes
		this.mAttributesBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesBackground.png", 0, 638);
		this.mAttributesPlusTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesPlus.png", 950, 112);
		this.mAttributesMinusTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesMinus.png", 950, 176);
		this.mAttributesConfirmTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/Confirm.png", 800, 563);
		
		this.mSceneTextureAtlas.load();
	
		//Backgroud
		this.mBackgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {};
		this.mGameMenuEntity.attachChild(mBackgroundSprite);		
		
		//Plank
		this.mPlankSprite = new Sprite(0, 0, this.mPlankTextureRegion, Game.getInstance().getVertexBufferObjectManager()) {@Override public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {return true;}};
		this.mGameMenuEntity.attachChild(mPlankSprite);
		
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
		this.mEquipmentTabSprite = new Sprite(mInventoryTabSprite.getWidth(), 0,this.mEquipmentTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		this.mSkillsTabSprite = new Sprite(mEquipmentTabSprite.getX() + mEquipmentTabSprite.getWidth(), 0,this.mSkillsTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		this.mAttributesTabSprite = new Sprite(mSkillsTabSprite.getX() + mSkillsTabSprite.getWidth(), 0,this.mAttributesTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		this.mOtherTabSprite = new Sprite(mAttributesTabSprite.getX() + mAttributesTabSprite.getWidth(), 0,this.mOtherTabTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
		
			
		//Settings
		this.mSettingsSprite = new Sprite(mOtherTabSprite.getX() + mOtherTabSprite.getWidth(), 8,this.mSettingsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {					
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
							GameMenuScene.this.mSettingsSprite.setAlpha(0.5f);
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
		this.mSettingsSprite.setScale(0.7f);
		this.mGameMenuEntity.attachChild(mSettingsSprite);
						
		this.loadTabTouchAreas();
		
		this.mInventoryTabSprite.setAlpha(0.5f);
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
		this.registerTouchArea(this.mPlankSprite);
	}
	
	
	
	//#################INVENTORY ENTITY######################
	public Entity LoadInventoryEntity(){
		this.mCurrentTab = 1;
			if(this.mInventoryEntity == null){
				
				this.mInventoryEntity = new Entity(0,0);
				
				BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Inventory/");
				this.mInventoryTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 1024,1024, TextureOptions.BILINEAR);
				this.mInventoryTextureAtlas.load();
				
				float offset = this.mPlankSprite.getHeight() + 5;
				
				//Money Sprite
				this.mInventoryMoneySprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 128, offset,this.mInventoryMoneyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryMoneySprite);
				
				offset+= (5 + mInventoryMoneySprite.getHeight());
				
				//Use Sprite
				this.mInventoryUseSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 128, offset,this.mInventoryBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryUseSprite);
				
				offset+= (5 + mInventoryUseSprite.getHeight());
				
				//Toss Sprite
				this.mInventoryTossSprite = new Sprite(Game.getSceneManager().getDisplay().getDisplayWidth() - 128, offset,this.mInventoryBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
				this.mInventoryEntity.attachChild(mInventoryTossSprite);
				
				//Descripción
				this.mInventoryDescriptionSprite = new Sprite(0, Game.getSceneManager().getDisplay().getDisplayHeight() - 75, this.mInventoryDescriptionTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};				
				this.mInventoryEntity.attachChild(mInventoryDescriptionSprite);

				this.mInventoryMoneyText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_YELLOW, this.mInventoryMoneySprite.getX()+this.mInventoryMoneySprite.getWidthScaled()+5, this.mInventoryMoneySprite.getY()+5, "", "GameMenuScene;Inventory;MoneyText");
				this.mInventoryMoneyText.setScale(2.5f);
				this.mInventoryEntity.attachChild(mInventoryMoneyText);
			}
			
			this.mInventoryMoneyText.setText(String.valueOf(Game.getPlayerHelper().getOwnPlayer().getMoney()));
			
			if(this.mInventoryItemsEntity!=null)this.mInventoryEntity.detachChild(this.mInventoryItemsEntity);
			
			this.mInventoryEntity.attachChild(this.loadInventoryItems());
			
		return this.mInventoryEntity;
	}		
	
	public Entity loadInventoryItems(){
		if(this.mInventoryItemsEntity == null)mInventoryItemsEntity = new Entity(20,this.mPlankSprite.getHeight()+20);
		this.mInventoryItemsEntity.detachChildren();
		InventoryItemHelper inventory = Game.getPlayerHelper().getOwnPlayer().getInventory();
		for(int i = inventory.getEntities().size()-1;i>=0;i--){
			Item item = inventory.getItembyIndex(i);
			item.getItemIcon().setPosition((((int)(mInventoryItemsEntity.getChildCount()%((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getWidthScaled()+5)), (((int)(mInventoryItemsEntity.getChildCount()/((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getHeightScaled()+5)));
			item.setmEntity(mInventoryItemsEntity);			
			mInventoryItemsEntity.attachChild(item.getItemIcon());
			this.registerTouchArea(item.getItemIcon());
		}
		return mInventoryItemsEntity;
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
	//	int tempArray[] = mDataHandler.getEquippedIDs(0); ***
	/*	for(int i=0;i<tempArray.length;i++){	//checkear si esta bien restarle 1 al length
			if(this.mDataHandler.getItemClass(tempArray[i]) == this.mDataHandler.getPlayerClass(0)){//checkeo que los items sean de la clase del player
			//	this.mUnEquippedCount+=1;//le sumo uno al unEquipped count para saber cuantos items hay sin equipar  \\ no le sumo, ya lo hace la funcion
			final ItemIcon pItem = new ItemIcon(mDataHandler,mEquipmentUnEquippedItemsTextureAtlas,0+24*i,0,0,0,mEquipmentUnEquippedItemsEntity,GameMenuScene.this,tempArray[i],0);
			this.PlaceEquipmentItem(pItem);
			}//si no son no los cargo wepa
		}
		this.mEquipmentUnEquippedItemsTextureAtlas.load();*/
	}
	
	
	private void loadEquippedItems(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Items/");
		this.mEquipmentEquippedItemsTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 144,24, TextureOptions.BILINEAR);
		
	/*	int tempArray[] = mDataHandler.getEquippedIDs(1);//consegui las ID de todo lo que esta equipado ***
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
		*/
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
		this.mSkillTreeEntity = new Entity(0,120);
		this.mSpellIconPosition = new ArrayList<Point>(){};
		this.mSpellIconPosition.add(0,new Point(20, 75));
		this.mSpellIconPosition.add(1,new Point(120, 75));
		this.mSpellIconPosition.add(2,new Point(220, 75));
		
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/InGameMenu/Skills/");
		this.mSkillsTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 2048,2048, TextureOptions.BILINEAR);
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
	/*	int tempArray[] = this.mDataHandler.getClassSpells(this.mDataHandler.getPlayerClass(1));//Le paso la class del player, despues definirla en la entidad
		//recorrer el array creando sprites dandoles de X y de Y lo que esta en el arraylist en la posicion ID
		
		for(int i=0;i<tempArray.length;i++){
			
			Log.d("Logd", "i: "+String.valueOf(i));
			Log.d("Logd", "length: "+String.valueOf(tempArray.length));
			final SpellIcon pIcon = new SpellIcon(mDataHandler,this.mSkillTreeAtlas,i*34,0,0,0,this.mSkillTreeEntity,GameMenuScene.this,tempArray[i]);
			this.placeSpellIcon(pIcon);
		}
		this.mSkillTreeAtlas.load();*/
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
		this.mCurrentTab = 4;
		pAttributes = new int[]{0,0,0,0};
		pUnassigned = Game.getPlayerHelper().getOwnPlayer().getUnassignedPoints();
		if(this.mAttributesEntity == null){
			this.mAttributesEntity = new Entity(0,0);
			
			this.mAttributesDescriptionText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_NORMAL, 5, Game.getSceneManager().getDisplay().getCameraHeight()-50, "Power: Makes attacks hit harder, skills & spells included.", "GameMenuScene;AttributesDescriptionText");
			
			float offset = 20;
			for(int i = 0;i<4;i++){
				//Background
				mAttributesBoxSprite[i] = new Sprite(offset, mPlankSprite.getHeight()+60,this.mAttributesBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
								switch ((Integer)(this.getUserData())) {
								case 0:
									mAttributesDescriptionText.setText("Power: Makes attacks hit harder, skills & spells included.");
									break;
								case 1:
									mAttributesDescriptionText.setText("Intelligence: Related to the total amount of mana you\n                       have.");
									break;
								case 2:
									mAttributesDescriptionText.setText("Defense: Reduces the amount of damage dealt to you.");
									break;
								case 3:
									mAttributesDescriptionText.setText("Endurance: Related to the total amount of health points\n                      you have.");
									break;
								default:
									Log.e("Quest!","DEFAULT USER DATA");
									mAttributesDescriptionText.setText("DEFAULT USER DATA");
									break;
								}
							}
							break;
						}
					return false;
					}					
				};
				mAttributesBoxSprite[i].setUserData(i);
								
				//Plus
				mAttributesPlusSprite[i] = new Sprite(offset+(mAttributesBoxSprite[i].getWidth()/2)-32, mAttributesBoxSprite[i].getY()+90,this.mAttributesPlusTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
								if(pUnassigned>0){
									int a = (Integer)(this.getUserData());
									pAttributes[a]+=1;
									mAttributesTexts[a].setText(ATTRIBUTES[a]+"\n        "+(Game.getPlayerHelper().getOwnPlayer().getAttributes()[a]+pAttributes[a]));
									pUnassigned-=1;
									mAttributesMinusSprite[a].setAlpha(1f);
								}
								
								if(pUnassigned<1){
									for(int i = 0;i<4;i++)mAttributesPlusSprite[i].setAlpha(0.5f);
								}
								
								mAttributesUnassignedPointsText.setText("Unassigned points: "+pUnassigned);
							}
							break;
						}
					return false;
					}					
				};
				mAttributesPlusSprite[i].setUserData(i);
				
				//Minus
				mAttributesMinusSprite[i] = new Sprite(offset+(mAttributesBoxSprite[i].getWidth()/2)-32, mAttributesPlusSprite[i].getY()+mAttributesPlusSprite[i].getHeight()+20,this.mAttributesMinusTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
								int a = (Integer)(this.getUserData());
								
								if(pAttributes[a]>0){
									pAttributes[a]-=1;
									mAttributesTexts[a].setText(ATTRIBUTES[a]+"\n        "+(Game.getPlayerHelper().getOwnPlayer().getAttributes()[a]+pAttributes[a]));
									pUnassigned+=1;
									for(int i = 0;i<4;i++)mAttributesPlusSprite[i].setAlpha(1f);
								}
								if(pAttributes[a]<1){
									mAttributesMinusSprite[a].setAlpha(0.5f);
								}
								mAttributesUnassignedPointsText.setText("Unassigned points: "+pUnassigned);
							}
							break;
						}
					return false;
					}					
				};
				mAttributesMinusSprite[i].setUserData(i);
				
				this.mAttributesEntity.attachChild(mAttributesBoxSprite[i]);
				this.mAttributesEntity.attachChild(mAttributesPlusSprite[i]);
				this.mAttributesEntity.attachChild(mAttributesMinusSprite[i]);
				
				//cargar los otros textos
				this.mAttributesTexts[i] = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLACK_KNIGHT, offset+10, mAttributesBoxSprite[i].getY()+5, ATTRIBUTES[i]+"\n        "+Game.getPlayerHelper().getOwnPlayer().getAttributes()[i], "GameMenuScene;AttributesText"+i);
				this.mAttributesEntity.attachChild(mAttributesTexts[i]);
				
				offset+= (mAttributesBoxSprite[i].getWidth() + 20);				
			}
			
			//Descripción
			this.mAttributesDescriptionSprite = new Sprite(-120, Game.getSceneManager().getDisplay().getDisplayHeight() - 55, this.mInventoryDescriptionTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};				
			this.mAttributesEntity.attachChild(mAttributesDescriptionSprite);
			this.mAttributesEntity.attachChild(this.mAttributesDescriptionText);
			
			this.mAttributesConfirmSprite = new Sprite(mAttributesDescriptionSprite.getX()+mAttributesDescriptionSprite.getWidth(), Game.getSceneManager().getDisplay().getDisplayHeight() - 55, this.mAttributesConfirmTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
				boolean mGrabbed = false;
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN:
							this.mGrabbed = true;
							this.setAlpha(0.5f);
						break;
					case TouchEvent.ACTION_UP:	
						if(this.mGrabbed) {
							this.mGrabbed = false;			
							if(Game.isServer()){
								for(int i = 0;i<4;i++)pAttributes[i]+=Game.getPlayerHelper().getOwnPlayer().getAttributes()[i];
								Game.getPlayerHelper().getOwnPlayer().setAttributes(pAttributes);
								Game.getPlayerHelper().getOwnPlayer().setUnassignedPoints(pUnassigned);
								Game.getServer().sendMessageSetPlayerAttributes(Game.getPlayerHelper().getOwnPlayer().getPlayerID(), pAttributes, pUnassigned);
								Game.getQueryQueuer().addSetPlayerAttributesQuery(Game.getPlayerHelper().getOwnPlayer().getPlayerID(), pAttributes, pUnassigned);
								pAttributes = new int[]{0,0,0,0};
							}else{
								for(int i = 0;i<4;i++)pAttributes[i]+=Game.getPlayerHelper().getOwnPlayer().getAttributes()[i];
								Game.getClient().sendSetPlayerAttributesMessage(Game.getPlayerHelper().getOwnPlayer().getPlayerID(), pAttributes, pUnassigned);
								pAttributes = new int[]{0,0,0,0};
							}
							mAttributesUnassignedPointsText.setText("Unassigned points: "+pUnassigned);
							if(pUnassigned<1)for(int i = 0;i<4;i++)mAttributesPlusSprite[i].setAlpha(0.5f);
							for(int i = 0;i<4;i++)mAttributesMinusSprite[i].setAlpha(0.5f);
							this.setAlpha(1f);
						}
						break;
					}
				return true;
				}					
			};				
			this.mAttributesEntity.attachChild(mAttributesConfirmSprite);
			
			this.mAttributesUnassignedPointsText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLACK_KNIGHT, (Game.getSceneManager().getDisplay().getCameraWidth()/3), this.mPlankSprite.getHeight()+10, "Unassigned points: ", "GameMenuScene;AttributesUnassignedPoints");
			this.mAttributesEntity.attachChild(mAttributesUnassignedPointsText);
		}
		if(pUnassigned<1){
			for(int i = 0;i<4;i++)mAttributesPlusSprite[i].setAlpha(0.5f);
		}else{
			for(int i = 0;i<4;i++)mAttributesPlusSprite[i].setAlpha(1f);
		}
		
		for(int i = 0;i<4;i++){
			mAttributesMinusSprite[i].setAlpha(0.5f);
			//this.registerTouchArea(mAttributesBoxSprite[i]);
			this.registerTouchArea(mAttributesPlusSprite[i]);
			this.registerTouchArea(mAttributesMinusSprite[i]);
			this.registerTouchArea(mAttributesBoxSprite[i]);
		}

		this.registerTouchArea(this.mAttributesConfirmSprite);

		this.mAttributesUnassignedPointsText.setText("Unassigned points: "+pUnassigned);
				
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
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	
	
	// ===========================================================
	// Methods
	// ===========================================================
	public void reloadCurrentTab(){
		switch (mCurrentTab) {
		case 1:
			LoadInventoryEntity();
			break;
		case 2:
			LoadEquipmentEntity();
			break;
		case 3:
			LoadSkillsEntity();
			break;
		case 4:
			LoadAttributesEntity();
			break;
		case 5:
			LoadOtherEntity();
			break;
		case 6:
			LoadSettingsEntity();
			break;
		default:
			mCurrentEntity = LoadInventoryEntity();
			this.attachChild(mCurrentEntity);
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

