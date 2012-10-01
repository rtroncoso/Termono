package com.quest.objects;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.quest.database.DataHandler;
import com.quest.game.Game;
import com.quest.helpers.TextHelper;
import com.quest.scenes.MatchScene;

public class MatchObject extends Entity{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Sprite mMatchSprite;
	private Entity mMatchEntity;
	private String mIP;
	private MatchScene mMatchScene;
	private int mFunction;
	private String mName;
	private String IPAdress;
	private Entity mEntity;
	private Boolean mJoining;
	private float oldPos = 0;
	private int LastAction = 0;
	private Text mText;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public MatchObject(DataHandler pDataHandler,ITextureRegion pTextureRegion,int pIconX,int pIconY,MatchScene pScene,String pIPAdress,Entity pEntity,Boolean pJoining,String pName, TextHelper pTextHelper,float pTextX,float pTextY, String pText,String pKey) {
		this.mMatchEntity = new Entity(0,0);
		this.mIP = pIPAdress;
		this.mName = pName;
		this.mEntity = pEntity;
		this.mMatchScene = pScene;
		this.mJoining = pJoining;
		
		this.mMatchSprite = new Sprite(pIconX, pIconY, pTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
					oldPos = pSceneTouchEvent.getX();
					LastAction = 0;
					mGrabbed = true;
				break;
			case TouchEvent.ACTION_MOVE:
				/*if(this.mGrabbed) {
					float newPos = pSceneTouchEvent.getX();					
					float difference = oldPos - newPos;
					
					if(difference == (-newPos)) difference = 0;
					
					float entityPos = MatchObject.this.mEntity.getX();//fijarse si se actualiza con la otra
										
					if(difference > 0){
						LastAction = 2;						
						if(entityPos > Game.getSceneManager().getDisplay().getDisplayWidth() - 143 - (Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100))){
							MatchObject.this.mEntity.setX(entityPos - difference);
						}
					}else{
						LastAction = 1;
						if(entityPos < Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100)){
							MatchObject.this.mEntity.setX(entityPos - difference);
						}
					}
					oldPos = newPos;
				}*/
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;
					if(mJoining){//si me estoy uniendo o creando
						mMatchScene.EnterMatch(mIP, mName);
					}else{
						//empiezo el server no el cliente
					}
				}
					/*
					float entityX = MatchObject.this.mEntity.getX();
					float entityY = MatchObject.this.mEntity.getY();
					
					float mLimiteIzquierda = (float)(Game.getSceneManager().getDisplay().getDisplayWidth() - 143 - (Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100)));
					float mLimiteDerecha = (float)(Game.getSceneManager().getDisplay().getDisplayWidth() * (2.625/100));
					
					if(entityX > mLimiteDerecha + 5 && LastAction == 1){
						MatchObject.this.mEntity.registerEntityModifier(new MoveModifier(0.4f, entityX, mLimiteDerecha - 1, entityY, entityY, EaseBackOut.getInstance()));
					
					}else if(entityX < mLimiteIzquierda - 5 && LastAction == 2){
						MatchObject.this.mEntity.registerEntityModifier(new MoveModifier(0.4f, entityX, mLimiteIzquierda + 1, entityY, entityY, EaseBackOut.getInstance()));
					}*/
				
				break;
			}
			return true;
			}					
		};
		this.mMatchEntity.attachChild(this.mMatchSprite);
		pScene.registerTouchArea(this.mMatchSprite);
		
		this.mText = pTextHelper.NewText(pTextX, pTextY, pText, pKey);
		this.mMatchEntity.attachChild(this.mText);
		pEntity.attachChild(this.mMatchEntity);
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

	public String getName(){
		return this.mName;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
