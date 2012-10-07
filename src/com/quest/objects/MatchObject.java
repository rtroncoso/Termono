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
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.quest.game.Game;
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
	private Entity mEntity;
	private String mIP;
	private MatchScene mMatchScene;
	private String mMatchName;
	private String mUserID;
	private String IPAdress;
	private Boolean mJoining;
	private Boolean mHasPassword;
	private Text mText;
	private String mPassword = "**nopass**";
	private boolean mReady = false;
	private TimerHandler tempTimer;
	
	
	//slide
	private float lastY;
	private float initialY;
	private float currY;
	private boolean scroll;
	private float speed;
	private boolean running = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public MatchObject(ITextureRegion pTextureRegion,int pX,final int pY,MatchScene pScene,String pIPAdress,final Entity pEntity,Boolean pJoining,String pMatchName,String pUserID,boolean pHasPassword, String pKey) {
		this.mMatchEntity = new Entity(pX,pY);
		this.mIP = pIPAdress;
		this.mMatchName = pMatchName;
		this.mMatchScene = pScene;
		this.mJoining = pJoining;
		this.mUserID = pUserID;
		this.mHasPassword = pHasPassword;
		this.mEntity = pEntity;
		
		this.mMatchSprite = new Sprite(0, 0, pTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				mGrabbed = true;
				if(running==true)endTimer();
				scroll = false;
				currY = initialY = lastY = pTouchAreaLocalY;
				startTimer(false);
				break;
			case TouchEvent.ACTION_MOVE:
				if(mGrabbed){
				currY = pTouchAreaLocalY;
					if((Math.sqrt(Math.pow(currY-initialY,2)))>5 || scroll == true){
						scroll=true;
						mEntity.setY(mEntity.getY()+(pTouchAreaLocalY-initialY));
					}
				}
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;
					mReady = false;
					endTimer();
					if(!scroll){//checkeo si lo scrolleo
						if(mJoining){//si me estoy uniendo o creando
							if(MatchObject.this.hasPassword()){
								showPasswordInput();
								WaitForPassword();
							}else{
								mMatchScene.EnterMatch(mIP,mPassword,mMatchName);
							}
						}else{
							mMatchScene.setSelectedMatch(mMatchName);
						}
					}else{
						if((Math.sqrt(Math.pow(currY-lastY,2)))>3){//Me fijo si estaba escrolleando o frenado
							speed = (currY - lastY);
							startTimer(true);
						}
						
					}
				}				
				break;
			}
			return true;
			}					
		};
		this.mMatchEntity.attachChild(this.mMatchSprite);
		
		if(hasPassword()){
			this.mMatchEntity.attachChild(new Sprite(500, 50, this.mMatchScene.getLockTexture(), Game.getInstance().getVertexBufferObjectManager()) {});
			
		}
		if(mJoining){
			this.mText = Game.getTextHelper().NewText(100, 20, "Match name: "+this.mMatchName+"  Creator: "+Game.getDataHandler().getUsername(this.mUserID), pKey);
		}else{
			this.mText = Game.getTextHelper().NewText(100, 20, "Match name: "+this.mMatchName, pKey);
		}
		this.mMatchEntity.attachChild(this.mText);
		
		pScene.registerTouchArea(this.mMatchSprite);
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

	public String getMatchName(){
		return this.mMatchName;
	}
	
	public String getUserID(){
		return this.mUserID;
	}
	
	public boolean hasPassword(){
		return this.mHasPassword;
	}
	
	public Entity getMatchSprite(){
		return this.mMatchEntity;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	public void showPasswordInput() {
		Game.getInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(Game.getInstance());

				alert.setTitle("Enter password");
				alert.setMessage("This match is password protected, please enter the password to join.");
				final EditText editText = new EditText(Game.getInstance());
				editText.setTextSize(15f);
				editText.setText("");
				editText.setGravity(Gravity.CENTER_HORIZONTAL);
				editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				alert.setView(editText);
				alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						mPassword = editText.getText().toString();
						mReady = true;
					}
				});
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						mReady = true;
					}
				});
				final AlertDialog dialog = alert.create();
				dialog.setOnShowListener(new OnShowListener() {//puede tirar error de api, necesita el 8
					@Override
					public void onShow(DialogInterface dialog) {
						editText.requestFocus();
						final InputMethodManager imm = (InputMethodManager) Game.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				dialog.show();
			}
		});
	}

	
	public void WaitForPassword(){
		this.mMatchScene.registerUpdateHandler(tempTimer = new TimerHandler(0.5f, true, new ITimerCallback() {
			@Override
	        public void onTimePassed(final TimerHandler pTimerHandler) {
				if(mReady==true){
	        		if(mPassword.equals(null)||mPassword.equals("")){
	        			mMatchScene.unregisterUpdateHandler(tempTimer);
	        		}else{
	        			mMatchScene.unregisterUpdateHandler(tempTimer);
	        			mMatchScene.EnterMatch(mIP,mPassword,mMatchName);
	        		}
	        	}
	        }
	    }));
	}


	public void changeAlpha(float pAlpha) {
		for(int i = 0;i<this.mMatchEntity.getChildCount();i++){
			this.mMatchEntity.getChildByIndex(i).setAlpha(pAlpha);
		}
	}


	public void startTimer(final boolean pScroll){
		this.mMatchScene.registerUpdateHandler(tempTimer = new TimerHandler(0.2f, true, new ITimerCallback() {
			@Override
	        public void onTimePassed(final TimerHandler pTimerHandler) {
				if(!pScroll){
					lastY = currY;
				}else{
					running = true;
					if(speed>0){
						if(mEntity.getY()>61){
							endTimer();
						}else{
							speed-=1;
						}
					}else{
						if(mEntity.getY()<(((mEntity.getChildCount()-1)*mMatchSprite.getHeight())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getHeight()+50+mMatchSprite.getHeight())*-1){
							endTimer();
						}else{
							speed+=1;
						}
					}
					mEntity.setY(mEntity.getY()+speed);
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
		if(mEntity.getY()>61){
			mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), mEntity.getX(), mEntity.getY(), 61, EaseBackOut.getInstance()));
		}else if(mEntity.getY()<(((mEntity.getChildCount()-1)*mMatchSprite.getHeight())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getHeight()+50+mMatchSprite.getHeight())*-1){
			if(mEntity.getChildCount()>2 && mEntity.getY()<61){
				mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), mEntity.getX(), mEntity.getY(), (((mEntity.getChildCount()-1)*mMatchSprite.getHeight())+((mEntity.getChildCount()-1)*29)- Game.getSceneManager().getDisplay().getCamera().getHeight()+50+mMatchSprite.getHeight())*-1, EaseBackOut.getInstance()));
			}else{
				mEntity.registerEntityModifier(new MoveModifier(0.7f, mEntity.getX(), mEntity.getX(), mEntity.getY(), 61, EaseBackOut.getInstance()));
			}
		}
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
