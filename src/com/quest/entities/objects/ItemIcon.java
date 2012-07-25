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


public class ItemIcon extends Entity{
	
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
	private String mItemDescription;
	private Entity mEntity;//como hacer para no tener que usar esto y pasarle el pEntity al icon?
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public ItemIcon(DataHandler pDataHandler,BitmapTextureAtlas pTextureAtlas,int pAtlasX,int AtlasY,int pIconX,int pIconY,Entity pEntity,Scene pScene,int pID, int pFunction) {
		this.mID = pID;
		this.mName = pDataHandler.getItemName(pID);
		this.mImagePath = pDataHandler.getItemImagePath(mID);		
		this.mFunction = pFunction;
		this.mBuyPrice = pDataHandler.getItemBuyPrice(mID);
		this.mSellPrice = pDataHandler.getItemSellPrice(mID);
		this.mType = pDataHandler.getItemType(mID);
		this.mItemDescription = pDataHandler.getItemDescription(mID);
		this.mItemClass = pDataHandler.getItemClass(mID);
		this.mEntity = pEntity;
		
		this.mITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, Game.getInstance().getApplicationContext(), mImagePath, pAtlasX, AtlasY);
		this.mItemIcon = new Sprite(pIconX, pIconY, mITextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				if(Game.getSceneManager().getGameMenuScene().getUsed() == false){
					Game.getSceneManager().getGameMenuScene().setUsed(true);
					this.setScale(3.5f);
					mGrabbed = true;
					if(ItemIcon.this.mFunction == 0){
						Game.getSceneManager().getGameMenuScene().EquipItem(ItemIcon.this, true, false);
					}
				}
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					ItemIcon.this.mItemIcon.setPosition(pSceneTouchEvent.getX() - ItemIcon.this.mEntity.getX() - ItemIcon.this.mItemIcon.getWidth() / 2, pSceneTouchEvent.getY() - ItemIcon.this.mEntity.getY() - ItemIcon.this.mItemIcon.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;	 
					this.setScale(2.5f);
					if(ItemIcon.this.mFunction == 0){
						Game.getSceneManager().getGameMenuScene().EquipItem(ItemIcon.this, false,this.collidesWith(Game.getSceneManager().getGameMenuScene().getEquipmentBoxSprite()));
						}
				}
				break;
			}
			return true;
			}					
		};
		this.mItemIcon.setScale(2.5f);
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

	public String getItemDescription(){
		return mItemDescription;
	}
	
	public void setEntity(Entity pEntity){
		this.mEntity = pEntity;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
