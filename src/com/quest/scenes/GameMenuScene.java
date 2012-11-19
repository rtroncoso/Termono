package com.quest.scenes;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
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
import com.quest.entities.objects.Item;
import com.quest.entities.objects.SpellIcon;
import com.quest.game.Game;
import com.quest.helpers.InventoryItemHelper;
import com.quest.methods.Point;


public class GameMenuScene extends Scene implements GameFlags,IOnSceneTouchListener{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mCurrentTab = 0;//0 default, 1 Inv, 2 Equipment, 3 Skills, 4 Atts, 5 Other, 6 Settings
	private Item mCurrentItem = null;
	private boolean mUsed = false;
	private Sprite[] mCollisionSprites;
	
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
	private Text mInventoryDescriptionText;
	private Text mInventoryMoneyText;
	private ArrayList<Item> mInventoryItemsList; 
	private Entity mInventoryItemsEntity;
	
	private Entity mAttributesEntity;
	private ITextureRegion[] mAttributesBoxTextureRegion = new ITextureRegion[4];
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
	
	private Entity mEquipmentEntity;
	private Entity mEquipmentUnEquippedItemsEntity;
	private Entity mEquipmentEquippedItemsEntity;
	private ArrayList<Item> mEquippedItemsList;
	private ArrayList<Item> mUnEquippedItemsList;
	private BitmapTextureAtlas mEquipmentTextureAtlas;
	private ITextureRegion mEquipmentBoxTextureRegion;
	private ITextureRegion mEquipmentBox2TextureRegion;
	private ITextureRegion mEquipmentAttributesTextureRegion;
	private ITextureRegion mEquipmentItemsTextureRegion;
	private ITextureRegion mEquipmentHeadTextureRegion;
	private ITextureRegion mEquipmentBodyTextureRegion;
	private ITextureRegion mEquipmentWeaponTextureRegion;
	private ITextureRegion mEquipmentOffhandTextureRegion;
	private ITextureRegion mEquipmentExtraTextureRegion;
	private Sprite mEquipmentBoxSprite;
	private Sprite mEquipmentBox2Sprite;
	private Sprite mEquipmentAttributesSprite;
	private Sprite mEquipmentItemsSprite;
	private Sprite mEquipmentHeadSprite;
	private Sprite mEquipmentBodySprite;
	private Sprite mEquipmentWeaponSprite;
	private Sprite mEquipmentOffhandSprite;
	private Sprite mEquipmentExtraSprite;
	
	
	
	//Entidades
	private Entity mSkillsEntity;
	private Entity mSkillTreeEntity;
	
	private Entity mOtherEntity;
	private Entity mSettingsEntity;
	private Entity mCurrentEntity;
	

	//Textures Atlas
	
	
	private BitmapTextureAtlas mInventoryItemsTextureAtlas;
	private BitmapTextureAtlas mSkillsTextureAtlas;
		private BitmapTextureAtlas mSkillTreeAtlas;
	private BitmapTextureAtlas mOtherTextureAtlas;
		
	//Texture regions
		private ITextureRegion mSkillTreeBackgroundTextureRegion;
		private ITextureRegion mSkillBarTextureRegion;
	
	
	//Sprites
		private Sprite mSkillTreeBackgroundSprite; 
		private Sprite mSkillBarSprite;
	
	
	//Lists y arrays
	private ArrayList<Point> mSpellIconPosition;
	
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public GameMenuScene(){
		this.mCurrentEntity = new Entity(0,0);
		this.mGameMenuEntity = new Entity(0,0);
		this.mSkillsEntity = new Entity(0,0);
		this.mOtherEntity = new Entity(0,0);
		this.mSettingsEntity = new Entity(0,0);
		this.mCollisionSprites = new Sprite[]{};
		
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
		this.mAttributesBoxTextureRegion = new ITextureRegion[]{
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesPowerBackground.png", 0, 638),
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesIntelligenceBackground.png", 138, 638),
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesDefenseBackground.png", 276, 638),
				BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSceneTextureAtlas, Game.getInstance().getApplicationContext(), "Attributes/AttributesEnduranceBackground.png", 414, 638),
		};
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
		this.setOnSceneTouchListener(GameMenuScene.this);
	}
	
	
	
	//#################INVENTORY ENTITY######################
	public Entity LoadInventoryEntity(){
		this.mCurrentTab = 1;
			if(this.mInventoryEntity == null){
				this.mInventoryItemsList = new ArrayList<Item>();
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
				this.mInventoryMoneyText.setScale(2f);
				this.mInventoryEntity.attachChild(mInventoryMoneyText);
				
				this.mInventoryDescriptionText = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_NORMAL, mInventoryDescriptionSprite.getX()+10, mInventoryDescriptionSprite.getY()+5, "", "GameMenuScene;Inventory;DescriptionText");
				this.mInventoryEntity.attachChild(mInventoryDescriptionText);
			}
		this.mCollisionSprites= new Sprite[]{this.mInventoryUseSprite,this.mInventoryTossSprite};
		
		this.mInventoryMoneyText.setText(String.valueOf(Game.getPlayerHelper().getOwnPlayer().getMoney()));
		
		if(this.mInventoryItemsEntity!=null)this.mInventoryEntity.detachChild(this.mInventoryItemsEntity);
		
		this.mInventoryEntity.attachChild(this.loadInventoryItems());
			
		return this.mInventoryEntity;
	}		
	
	
	public Entity loadInventoryItems(){
		if(this.mInventoryItemsEntity == null)mInventoryItemsEntity = new Entity(20,this.mPlankSprite.getHeight()+20);
		this.mInventoryItemsEntity.detachChildren();
		InventoryItemHelper inventory = Game.getPlayerHelper().getOwnPlayer().getInventory();
		mInventoryItemsList.clear();
		
		for(int i = inventory.getEntities().size()-1;i>=0;i--){
			Item item = inventory.getItembyIndex(i);
			if(!item.isEqquiped()){
				item.getItemIcon().detachSelf();
				item.getItemIcon().setPosition((((int)(mInventoryItemsList.size()%((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getWidthScaled()+5)), (((int)(mInventoryItemsList.size()/((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getHeightScaled()+5)));
				item.setEntity(mInventoryItemsEntity);
				mInventoryItemsEntity.attachChild(item.getItemIcon());			
				mInventoryItemsList.add(item);
				this.registerTouchArea(item.getItemIcon());
			}
		}
		return mInventoryItemsEntity;
	}
	
	public void ActionOnCollide(Item pItem, Sprite pSprite, Entity pEntity){
		if(pSprite != null){
			if(pEntity == mInventoryItemsEntity){
				if(pSprite == mInventoryUseSprite){
					Log.d("Quest!", "Use sprite");
					//FIJARME SI ES EQUIPABLE O CONSUMIBLE AAAAAAAAAAAAAAAAAAAAA
					Game.getPlayerHelper().getOwnPlayer().getInventory().EquipItem(pItem);
					loadInventoryItems();
				}else if(pSprite == mInventoryTossSprite){
					Game.getPlayerHelper().getOwnPlayer().getInventory().removeItem(pItem);
					mInventoryItemsEntity.detachChild(pItem.getItemIcon());
					mInventoryItemsList.remove(pItem);
					Game.getPlayerHelper().getOwnPlayer().getInventory().removeItem(pItem);
					loadInventoryItems();
				}
				
			}else if(mEquipmentEntity!=null && pEntity == mEquipmentEquippedItemsEntity || pEntity == mEquipmentUnEquippedItemsEntity){
				if(pItem.isEqquiped()){//Estaba equipado y colisiono
					Sprite tmpSprite = null;
					switch (pItem.getItemType()) {//2 = arma, 3 = armadura, 4 = Escudo, 5 = casco, 6 = extra
					case 2:
						tmpSprite = mEquipmentWeaponSprite;
						break;
					case 3:
						tmpSprite = mEquipmentBodySprite;
						break;
					case 4:
						tmpSprite = mEquipmentOffhandSprite;
						break;
					case 5:
						tmpSprite = mEquipmentHeadSprite;
						break;
					case 6:
						tmpSprite = mEquipmentExtraSprite;
						break;					
					}
					pItem.getItemIcon().setPosition(tmpSprite.getX()+(tmpSprite.getWidthScaled()/2)-(pItem.getItemIcon().getWidthScaled()/2),tmpSprite.getY()+(tmpSprite.getHeightScaled()/2)-(pItem.getItemIcon().getHeightScaled()/2));
				}else{//No estaba equipado y colisiono
					Game.getPlayerHelper().getOwnPlayer().getInventory().EquipItem(pItem);
					loadEquippedItems();
					loadUnEquippedItems();
				}
			}
		}else{
			placeIcon(pItem, pEntity);
		}
	}
	
	public void placeIcon(Item pItem,Entity pEntity){
		if(pEntity == mInventoryItemsEntity){
			if(mInventoryItemsList.indexOf(pItem)!=-1){
				pItem.getItemIcon().setPosition((((int)(mInventoryItemsList.indexOf(pItem)%((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getWidthScaled()+5)), (((int)(mInventoryItemsList.indexOf(pItem)/((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getHeightScaled()+5)));
			}else{
				pItem.getItemIcon().setPosition((((int)(mInventoryItemsList.size()%((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getWidthScaled()+5)), (((int)(mInventoryItemsList.size()/((int)((Game.getSceneManager().getDisplay().getCameraWidth()-this.mInventoryUseSprite.getWidthScaled()-20)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getHeightScaled()+5)));
			}
		}else if(mEquipmentEntity!=null && pEntity == mEquipmentEquippedItemsEntity || pEntity == mEquipmentUnEquippedItemsEntity){ 
			if(!pItem.isEqquiped()){//Estaba desequipado y no colisiono
				pItem.getItemIcon().setPosition((((int)(mUnEquippedItemsList.indexOf(pItem)%((int)((this.mEquipmentBox2Sprite.getWidth()-10)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getWidthScaled()+5)), (((int)(mUnEquippedItemsList.indexOf(pItem)/((int)((this.mEquipmentBox2Sprite.getWidth()-10)/(pItem.getItemIcon().getWidthScaled()+5)))))*(pItem.getItemIcon().getHeightScaled()+5)));
			}else{//Estaba equipado y no colisiono
				Game.getPlayerHelper().getOwnPlayer().getInventory().UnequipItem(pItem);
				loadEquippedItems();
				loadUnEquippedItems();
			}
		}
	}
	//#################EQUIPMENT ENTITY######################
	public Entity LoadEquipmentEntity(){
		if(this.mEquipmentEntity == null){
			this.mEquipmentEntity = new Entity(0,0);
			this.mEquippedItemsList = new ArrayList<Item>();
			this.mUnEquippedItemsList = new ArrayList<Item>();
			
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Interfaces/"); 			//para los items es a partir de 633, me da para 340 items, ni me quema hacer otro
			this.mEquipmentTextureAtlas = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 512,1024, TextureOptions.BILINEAR);
			this.mEquipmentBox2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Box2.png", 0, 0);
			this.mEquipmentBodyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Body.png", 370, 0);
			this.mEquipmentOffhandTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Offhand.png", 370, 191);
			this.mEquipmentWeaponTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Weapon.png", 439, 191);
			this.mEquipmentExtraTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Extra.png", 370, 335);
			this.mEquipmentBoxTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Box.png", 0, 292);
			this.mEquipmentHeadTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Head.png", 286, 292);
			this.mEquipmentAttributesTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Attributes.png", 286, 402);
			this.mEquipmentItemsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mEquipmentTextureAtlas, Game.getInstance().getApplicationContext(), "InGameMenu/Equipment/Items.png", 286, 452);
			this.mEquipmentTextureAtlas.load();
			
			
			//Cajas de contenidos
			this.mEquipmentBoxSprite = new Sprite(55, 115,this.mEquipmentBoxTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
			this.mEquipmentEntity.attachChild(mEquipmentBoxSprite);
		
			this.mEquipmentBox2Sprite = new Sprite(400, 155,this.mEquipmentBox2TextureRegion,Game.getInstance().getVertexBufferObjectManager()) {	};
			this.mEquipmentEntity.attachChild(mEquipmentBox2Sprite);
					
			//Items Tab
			this.mEquipmentItemsSprite = new Sprite(mEquipmentBox2Sprite.getX()+15, mEquipmentBox2Sprite.getY()-50,this.mEquipmentItemsTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentItemsSprite);
			this.registerTouchArea(mEquipmentItemsSprite);
			
			//Attributes Tab
			this.mEquipmentAttributesSprite = new Sprite(mEquipmentItemsSprite.getX()+mEquipmentItemsSprite.getWidth()+1, mEquipmentBox2Sprite.getY()-50,this.mEquipmentAttributesTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentAttributesSprite);
			this.registerTouchArea(this.mEquipmentAttributesSprite);
			
			//Helm Sprite
			this.mEquipmentHeadSprite = new Sprite(155, 130,this.mEquipmentHeadTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentHeadSprite);
			
			//Plate Sprite
			this.mEquipmentBodySprite = new Sprite(140, 225,this.mEquipmentBodyTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentBodySprite);
			
			//Weapon Sprite
			this.mEquipmentWeaponSprite = new Sprite(65, 225,this.mEquipmentWeaponTextureRegion,Game.getInstance().getVertexBufferObjectManager()) { };
			this.mEquipmentEntity.attachChild(mEquipmentWeaponSprite);
			
			
			//Offhand Sprite
			this.mEquipmentOffhandSprite = new Sprite(262, 225,this.mEquipmentOffhandTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentOffhandSprite);
			
			//Extra Sprite
			this.mEquipmentExtraSprite = new Sprite(262, 370,this.mEquipmentExtraTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {};
			this.mEquipmentEntity.attachChild(mEquipmentExtraSprite);
			
		}
		this.mCollisionSprites= new Sprite[]{this.mEquipmentBoxSprite};
		
		if(this.mEquipmentEquippedItemsEntity!=null)this.mEquipmentEntity.detachChild(this.mEquipmentEquippedItemsEntity);
		if(this.mEquipmentUnEquippedItemsEntity!=null)this.mEquipmentEntity.detachChild(this.mEquipmentUnEquippedItemsEntity);
		
		this.mEquipmentEntity.attachChild(this.loadEquippedItems());
		this.mEquipmentEntity.attachChild(this.loadUnEquippedItems());
		
		return this.mEquipmentEntity;
	}
	
	public Entity loadEquippedItems(){
		if(this.mEquipmentEquippedItemsEntity == null)mEquipmentEquippedItemsEntity = new Entity();
		this.mEquipmentEquippedItemsEntity.detachChildren();
		mEquippedItemsList = Game.getPlayerHelper().getOwnPlayer().getInventory().getEquippedItems();
		for(int i = mEquippedItemsList.size()-1;i>=0;i--){
			Item item = Game.getPlayerHelper().getOwnPlayer().getInventory().getItembyIndex((Integer)mEquippedItemsList.get(i).getUserData());
			item.getItemIcon().detachSelf();
			Sprite tmpSprite = null;
			switch (item.getItemType()) {//2 = arma, 3 = armadura, 4 = Escudo, 5 = casco, 6 = extra
			case 2:
				tmpSprite = mEquipmentWeaponSprite;
				break;
			case 3:
				tmpSprite = mEquipmentBodySprite;
				break;
			case 4:
				tmpSprite = mEquipmentOffhandSprite;
				break;
			case 5:
				tmpSprite = mEquipmentHeadSprite;
				break;
			case 6:
				tmpSprite = mEquipmentExtraSprite;
				break;
				
			default:
				Log.e("Quest!","no es equip: "+item.getItemName());					
			}
			
			item.getItemIcon().setPosition(tmpSprite.getX()+(tmpSprite.getWidthScaled()/2)-(item.getItemIcon().getWidthScaled()/2),tmpSprite.getY()+(tmpSprite.getHeightScaled()/2)-(item.getItemIcon().getHeightScaled()/2));
			mEquipmentEquippedItemsEntity.attachChild(item.getItemIcon());
			item.setEntity(mEquipmentEquippedItemsEntity);
			this.registerTouchArea(item.getItemIcon());
		}	
		return mEquipmentEquippedItemsEntity;
	}

	public Entity loadUnEquippedItems(){
		if(this.mEquipmentUnEquippedItemsEntity == null)mEquipmentUnEquippedItemsEntity = new Entity(this.mEquipmentBox2Sprite.getX()+10,this.mEquipmentBox2Sprite.getY()+15);
		this.mEquipmentUnEquippedItemsEntity.detachChildren();
		InventoryItemHelper inventory = Game.getPlayerHelper().getOwnPlayer().getInventory();
		mUnEquippedItemsList.clear();
		
		for(int i = inventory.getEntities().size()-1;i>=0;i--){
			Item item = inventory.getItembyIndex(i);
			if(!item.isEqquiped() && item.getItemType()>1){
				item.getItemIcon().detachSelf();
				item.getItemIcon().setPosition((((int)(mUnEquippedItemsList.size()%((int)((this.mEquipmentBox2Sprite.getWidth()-10)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getWidthScaled()+5)), (((int)(mUnEquippedItemsList.size()/((int)((this.mEquipmentBox2Sprite.getWidth()-10)/(item.getItemIcon().getWidthScaled()+5)))))*(item.getItemIcon().getHeightScaled()+5)));
				mEquipmentUnEquippedItemsEntity.attachChild(item.getItemIcon());
				item.setEntity(mEquipmentUnEquippedItemsEntity);							
				mUnEquippedItemsList.add(item);
				this.registerTouchArea(item.getItemIcon());
			}
		}
		return mEquipmentUnEquippedItemsEntity;
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
				mAttributesBoxSprite[i] = new Sprite(offset, mPlankSprite.getHeight()+60,this.mAttributesBoxTextureRegion[i],Game.getInstance().getVertexBufferObjectManager()) {
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
				mAttributesBoxSprite[i].setScale(1.5f);
				//Plus
				mAttributesPlusSprite[i] = new Sprite(offset+(mAttributesBoxSprite[i].getWidthScaled()/2)-32, mAttributesBoxSprite[i].getY()+45,this.mAttributesPlusTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
				mAttributesPlusSprite[i].setScale(1.5f);
				//Minus
				mAttributesMinusSprite[i] = new Sprite(offset+(mAttributesBoxSprite[i].getWidthScaled()/2)-32, mAttributesPlusSprite[i].getY()+mAttributesPlusSprite[i].getHeightScaled()+20,this.mAttributesMinusTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
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
				mAttributesMinusSprite[i].setScale(1.5f);
				this.mAttributesEntity.attachChild(mAttributesBoxSprite[i]);
				this.mAttributesEntity.attachChild(mAttributesPlusSprite[i]);
				this.mAttributesEntity.attachChild(mAttributesMinusSprite[i]);
				
				//cargar los otros textos
				this.mAttributesTexts[i] = Game.getTextHelper().addNewText(FLAG_TEXT_TYPE_BLACK_KNIGHT, offset+10, mAttributesBoxSprite[i].getY()+5, ATTRIBUTES[i]+"\n        "+Game.getPlayerHelper().getOwnPlayer().getAttributes()[i], "GameMenuScene;AttributesText"+i);
				this.mAttributesEntity.attachChild(mAttributesTexts[i]);
				
				offset+= (mAttributesBoxSprite[i].getWidthScaled() + 20);				
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

	boolean grabbed = false;
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					Log.d("Quest!", "Item es "+this.getCurrentItem());
					if(mCurrentItem!=null)
						mInventoryDescriptionText.setText(mCurrentItem.getItemDescription());
					grabbed = true;
					break;
				case TouchEvent.ACTION_MOVE:
					Log.d("Quest!", "move es "+this.getCurrentItem());
					if(mCurrentItem!=null && grabbed){
						mCurrentItem.getItemIcon().setPosition(pSceneTouchEvent.getX()-(mCurrentItem.getItemIcon().getWidthScaled()/2)-mCurrentItem.getEntity().getX(), pSceneTouchEvent.getY()-(mCurrentItem.getItemIcon().getHeightScaled()/2)-mCurrentItem.getEntity().getY());
						boolean collides =false;
						int i = 0;
						while(i<mCollisionSprites.length){
							if(mCurrentItem.getItemIcon().collidesWith(mCollisionSprites[i]) && !collides){
								mCollisionSprites[i].setAlpha(0.5f);
								collides = true;
							}else{
								mCollisionSprites[i].setAlpha(1f);
							}
							i++;
						}
					}
					break;
				case TouchEvent.ACTION_UP:	
					if(grabbed == true){
						if(mCurrentItem!=null){
							mCurrentItem.getItemIcon().setPosition(pSceneTouchEvent.getX()-(mCurrentItem.getItemIcon().getWidthScaled()/2)-mCurrentItem.getEntity().getX(), pSceneTouchEvent.getY()-(mCurrentItem.getItemIcon().getHeightScaled()/2)-mCurrentItem.getEntity().getY());
							boolean collideS =false;
							int a = 0;
							int b=-1;
							Log.d("Quest!","Collision lenght: "+mCollisionSprites.length);
							while(a<mCollisionSprites.length){
								if(!collideS && mCurrentItem.getItemIcon().collidesWith(mCollisionSprites[a])){
									b = a;
									collideS = true;
								}
								mCollisionSprites[a].setAlpha(1f);
								a++;
							}
							if(b!=-1)
								Game.getSceneManager().getGameMenuScene().ActionOnCollide(mCurrentItem, mCollisionSprites[b],mCurrentItem.getEntity());
							else
								Game.getSceneManager().getGameMenuScene().ActionOnCollide(mCurrentItem, null,mCurrentItem.getEntity());
							
							this.setCurrentItem(null);
						}
					}
					grabbed = false;
					break;
		}
		return false;
	}

	public Item getCurrentItem() {
		return mCurrentItem;
	}

	public void setCurrentItem(Item mCurrentItem) {
		this.mCurrentItem = mCurrentItem;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

