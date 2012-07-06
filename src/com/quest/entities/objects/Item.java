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
	private Sprite mItemSprite;	
	private String mImagePath;
	private int mPrice;
	private ITextureRegion mITextureRegion;
	private GameMenuScene mGameMenuScene;
	private int mFunction;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public Item(DataHandler pDataHandler,BitmapTextureAtlas pTextureAtlas,int pAtlasX,int AtlasY,int pSpriteX,int pSpriteY,Entity pEntity,Scene pScene,String pName, int pFunction) {
		
		mImagePath = pDataHandler.getImagePath(pName);		
		this.mFunction = pFunction;
		this.mPrice = pDataHandler.getItemPrice(pName);
		
		this.mITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, Game.getInstance().getApplicationContext(), mImagePath, pAtlasX, AtlasY);
		this.mItemSprite = new Sprite(pSpriteX, pSpriteY, mITextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				this.setScale(3.0f);
				mGrabbed = true;
				if(Item.this.mFunction == 0){
					Game.getSceneManager().getGameMenuScene().EquipItem(this, true, false);
					}
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;	 
					this.setScale(2.0f);
					if(Item.this.mFunction == 0){
						Game.getSceneManager().getGameMenuScene().EquipItem(this, false,this.collidesWith(Game.getSceneManager().getGameMenuScene().getEquipmentBoxSprite()));
						}
				}
				break;
			}
			return true;
			}					
		};
		this.mItemSprite.setAlpha(1.0f);
		this.mItemSprite.setScale(2.0f);
		pEntity.attachChild(this.mItemSprite);
		pScene.registerTouchArea(this.mItemSprite);
		
	
	
	}
	
	

	
	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public ITextureRegion getTextureRegion(){
		return this.mITextureRegion;
	}
	
	public Sprite getSprite() {
		return mItemSprite;
	}

	

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
