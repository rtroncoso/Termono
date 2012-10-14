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

public class SpellIcon {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mID;
	private Sprite mSpellIcon;	
	private String mIconImagePath;
	private int mLevel;
	private int mType;
	private ITextureRegion mITextureRegion;
	private String mEffect;
	private String mDescription;
	private String mName;
	private int mSpellClass;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public SpellIcon(DataHandler pDataHandler,BitmapTextureAtlas pTextureAtlas,int pAtlasX,int AtlasY,int pIconX,int pIconY,Entity pEntity,Scene pScene,int pID){
	/*	this.mID = pID;
		this.mName = pDataHandler.getSpellName(pID);
		this.mIconImagePath = pDataHandler.getSpellImagePath(mID);
		this.mLevel = pDataHandler.getSpellLevel(pID);
		this.mType = pDataHandler.getSpellType(pID);
		this.mEffect = pDataHandler.getSpellEffect(pID, this.mLevel);
		this.mDescription = pDataHandler.getSpellDescription(pID);
		this.mSpellClass = pDataHandler.getSpellClass(pID);
		
		
		this.mITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pTextureAtlas, Game.getInstance().getApplicationContext(), mIconImagePath, pAtlasX, AtlasY);
		this.mSpellIcon = new Sprite(pIconX, pIconY, mITextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				//if(Game.getSceneManager().getGameMenuScene().getUsed() == false){
					Game.getSceneManager().getGameMenuScene().setUsed(true);
					this.setScale(3.5f);
					mGrabbed = true;
				//}
				break;
			case TouchEvent.ACTION_MOVE:
				if(this.mGrabbed) {
					//SpellIcon.this.mSpellIcon.setPosition(pSceneTouchEvent.getX()- SpellIcon.this.mSpellIcon.getWidth() / 2, pSceneTouchEvent.getY() - SpellIcon.this.mSpellIcon.getHeight() / 2);
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;	 
					this.setScale(2.5f);
					
				}
				break;
			}
			return true;
			}					
		};
		this.mSpellIcon.setScale(2.5f);
		pEntity.attachChild(this.mSpellIcon);
		pScene.registerTouchArea(this.mSpellIcon);
		
		*/
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
		return mSpellIcon;
	}
		
	public void getEffect(String pEffect){
		
	}
	
	public int getSpellClass(){
		return this.mSpellClass;
	}
	
	public String getName(){
		return this.mName;
	}
	
	public int getID(){
		return mID;
	}

	public String getSpellDescription(){
		return mDescription;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

