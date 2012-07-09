package com.quest.entities.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.database.DataHandler;
import com.quest.game.Game;
import com.quest.scenes.GameMenuScene;


public class Item extends Entity{
	
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Sprite mItemIcon;
	private Sprite mItemSprite;	
	private String mImagePath;
	private int mID;
	private int mBuyPrice;
	private int mSellPrice;
	private int mType;
	private ITextureRegion mITextureRegion;
	private int mFunction;
	private String mName;
	private int mItemClass;
	
	private int mCount = -1;//despues pasarlo a un list en el menuscene. \\ uso -1 como null, despues cuando lo pase al list lo desnegreo
	private Entity mEntity;//como hacer para no tener que usar esto y pasarle el pEntity al icon?
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Item(DataHandler pDataHandler,BitmapTextureAtlas pTextureAtlas,int pAtlasX,int AtlasY,int pIconX,int pIconY,Entity pEntity,Scene pScene,int pID, int pFunction) {
		this.mID = pID;
		//this.setUserData(pID);
		this.mName = pDataHandler.getItemName(pID);
		this.mImagePath = pDataHandler.getItemImagePath(mID);		
		this.mFunction = pFunction;
		this.mBuyPrice = pDataHandler.getItemBuyPrice(mID);
		this.mSellPrice = pDataHandler.getItemSellPrice(mID);
		this.mType = pDataHandler.getItemType(mID);
		this.mItemClass = pDataHandler.getItemClass(mID);
		this.mEntity = pEntity;
		
		this.mITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, Game.getInstance().getApplicationContext(), mImagePath, pAtlasX, AtlasY);
		this.mItemIcon = new Sprite(pIconX, pIconY, mITextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setScale(2.5f);
				mGrabbed = true;
				if(Item.this.mFunction == 0){
					Game.getSceneManager().getGameMenuScene().EquipItem(Item.this, true, false);
					}
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					Item.this.mItemIcon.setPosition(pSceneTouchEvent.getX() - Item.this.mEntity.getX() - Item.this.mItemIcon.getWidth() / 2, pSceneTouchEvent.getY() - Item.this.mEntity.getY() - Item.this.mItemIcon.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;	 
					this.setScale(1.5f);
					if(Item.this.mFunction == 0){
						Game.getSceneManager().getGameMenuScene().EquipItem(Item.this, false,this.collidesWith(Game.getSceneManager().getGameMenuScene().getEquipmentBoxSprite()));
						}
				}
				break;
			}
			return true;
			}					
		};
		this.mItemIcon.setScale(1.5f);
		pEntity.attachChild(this.mItemIcon);
		pScene.registerTouchArea(this.mItemIcon);
		
	
	
	}
	
	

	
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getType() {
		return mType;
	}
	
	public ITextureRegion getTextureRegion(){
		return this.mITextureRegion;
	}
	
	public Sprite getIcon() {
		return mItemIcon;
	}
	
	public Sprite getSprite() {
		return mItemSprite;
	}
	
	public int getBuyPrice(){
		return mBuyPrice;
	}
	
	public int getSellPrice(){
		return mSellPrice;
	}
	
	public int getItemClass(){
		return this.mItemClass;
	}
	
	public String getName(){
		return this.mName;
	}
	
	public int getID(){
		return mID;
	}

	public void setEntity(Entity pEntity){
		this.mEntity = pEntity;
	}
	
	public int getCount(){
		return this.mCount;
	}
	
	public void setCount(int pCount){
		this.mCount = pCount;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
