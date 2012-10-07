package com.quest.objects;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseBackOut;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.quest.game.Game;
import com.quest.scenes.MatchScene;

public class CharacterObject extends Entity{


	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Sprite mCharacterSprite;
	private Entity mCharacterEntity;
	private Entity mEntity;
	private int mCharacterID;
	private MatchScene mMatchScene;
	private Text mText;
	private TimerHandler tempTimer;
	private int mClass;
	
	//slide
	private float lastX;
	private float initialX;
	private float currX;
	private boolean scroll;
	private float speed;
	private boolean running = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public CharacterObject(ITextureRegion pTextureRegion,final int pX,int pY,MatchScene pScene,final Entity pEntity,int pCharacterID,int pLevel,int pClass,String pKey) {
		int Y = pY;
		if(mClass==3)Y = pY-10;//Ajusto diferencia de tamaño con el orco
		this.mCharacterEntity = new Entity(pX,pY);
		this.mMatchScene = pScene;
		this.mCharacterID = pCharacterID;
		this.mEntity = pEntity;
		this.mClass = pClass;

		
		this.mCharacterSprite = new Sprite(0, 0, pTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					mGrabbed = true;
					if(running==true)endTimer();
					scroll = false;
					currX = initialX = lastX = pTouchAreaLocalX;
					startTimer(false);
					break;
				case TouchEvent.ACTION_MOVE:
					if(mGrabbed){
					currX = pTouchAreaLocalX;
						if((Math.sqrt(Math.pow(currX-initialX,2)))>5 || scroll == true){
							scroll=true;
							mEntity.setX(mEntity.getX()+(pTouchAreaLocalX-initialX));
						}
					}
					break;
				case TouchEvent.ACTION_UP:
					if(mGrabbed) {
						mGrabbed = false;
						endTimer();
						if(!scroll){//checkeo si lo scrolleo
							mMatchScene.setSelectedCharacter(mCharacterID);
						}else{
							if((Math.sqrt(Math.pow(currX-lastX,2)))>3){//Me fijo si estaba escrolleando o frenado
								speed = (currX - lastX);
								startTimer(true);
							}
							
						}
					}			
					break;
				}
				return true;
			}					
		};
		this.mCharacterEntity.attachChild(this.mCharacterSprite);
		


		this.mText = Game.getTextHelper().NewText(this.mCharacterSprite.getX()-15, this.mCharacterSprite.getHeight()+10, "L: "+String.valueOf(pLevel), pKey);
		this.mCharacterEntity.attachChild(this.mText);
		
		pScene.registerTouchArea(this.mCharacterSprite);
		pEntity.attachChild(this.mCharacterEntity);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getCharacterID(){
		return this.mCharacterID;
	}


	public Entity getCharacterSprite(){
		return this.mCharacterSprite;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public void changeAlpha(float pAlpha) {
		for(int i = 0;i<this.mCharacterEntity.getChildCount();i++){
			this.mCharacterEntity.getChildByIndex(i).setAlpha(pAlpha);
		}
	}


	public void startTimer(final boolean pScroll){
		this.mMatchScene.registerUpdateHandler(tempTimer = new TimerHandler(0.2f, true, new ITimerCallback() {
			@Override
	        public void onTimePassed(final TimerHandler pTimerHandler) {
				if(!pScroll){
					lastX = currX;
				}else{
					running = true;
					if(speed>0){
						if(mEntity.getX()>(61)){
							endTimer();
						}else{
							speed-=1;
						}
					}else{
						if(mEntity.getX()<(((mEntity.getChildCount()-1)*mCharacterSprite.getWidth())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getWidth()+61+mCharacterSprite.getWidth())*-1){
							endTimer();
						}else{
							speed+=1;
						}
					}
					mEntity.setX(mEntity.getX()+speed);
					if(speed == 0)endTimer();
				}
	        }
	    }));
	}
	
	public void endTimer(){
		running = false;
		mMatchScene.unregisterUpdateHandler(tempTimer);
		slideBack();
	}
	
	public void slideBack(){
		if(mEntity.getX()>61){
			mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), 61, mEntity.getY(), mEntity.getY(), EaseBackOut.getInstance()));
		}else if(mEntity.getX()<(((mEntity.getChildCount()-1)*mCharacterSprite.getWidth())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getWidth()+61+mCharacterSprite.getWidth())*-1){
			if(mEntity.getChildCount()>15 && mEntity.getX()<61){
				mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), (((mEntity.getChildCount()-1)*mCharacterSprite.getWidth())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getWidth()+61+mCharacterSprite.getWidth())*-1, mEntity.getY(), mEntity.getY(), EaseBackOut.getInstance()));
			}else{
				mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), 61, mEntity.getY(), mEntity.getY(), EaseBackOut.getInstance()));
			}
		}
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
