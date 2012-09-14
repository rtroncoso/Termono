package com.quest.objects;

import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.database.DataHandler;
import com.quest.game.Game;

public class MatchObject extends Entity{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Sprite mMatchSprite;
	private String mIP;
	
	private int mFunction;
	private String mName;
	private int mItemClass;
	private String IPAdress;
	private Entity mEntity;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public MatchObject(DataHandler pDataHandler,ITextureRegion pTextureRegion,int pIconX,int pIconY,Scene pScene,String pIPAdress) {
		this.mIP = pIPAdress;
				
		
		this.mMatchSprite = new Sprite(pIconX, pIconY, pTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
					mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				//slide
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;	 
				}
				break;
			}
			return true;
			}					
		};
		this.attachChild(this.mMatchSprite);
		pScene.registerTouchArea(this.mMatchSprite);
	
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getIP(){
		return this.mIP;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
