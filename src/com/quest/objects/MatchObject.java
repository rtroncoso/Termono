package com.quest.objects;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.R.bool;
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
	private String mIP;
	private MatchScene mMatchScene;
	private String mMatchName;
	private String mUserID;
	private String IPAdress;
	private Entity mEntity;
	private Boolean mJoining;
	private Boolean mHasPassword;
	private Text mText;
	private String mPassword = "";
	private boolean mReady = false;
	private TimerHandler tempTimer;
	// ===========================================================
	// Constructors
	// ===========================================================
	
	public MatchObject(ITextureRegion pTextureRegion,int pSpriteX,int pSpriteY,MatchScene pScene,String pIPAdress,Entity pEntity,Boolean pJoining,String pMatchName,String pUserID,boolean pHasPassword,float pTextX,float pTextY, String pKey) {
		this.mMatchEntity = new Entity(0,0);
		this.mIP = pIPAdress;
		this.mMatchName = pMatchName;
		this.mEntity = pEntity;
		this.mMatchScene = pScene;
		this.mJoining = pJoining;
		this.mUserID = pUserID;
		this.mHasPassword = pHasPassword;
		
		this.mMatchSprite = new Sprite(pSpriteX, pSpriteY, pTextureRegion,Game.getInstance().getVertexBufferObjectManager()) {
			boolean mGrabbed = false;
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
					mGrabbed = true;
				break;
			case TouchEvent.ACTION_UP:
				if(mGrabbed) {
					mGrabbed = false;
					mReady = false;
					if(mJoining){//si me estoy uniendo o creando
						if(MatchObject.this.hasPassword()){
							showPasswordInput();
							WaitForPassword();
						}else{
							mMatchScene.EnterMatch(mIP, mMatchName,mUserID,mPassword);
						}
					}else{
						mMatchScene.setSelectedMatch(mMatchName);
					}
				}				
				break;
			}
			return true;
			}					
		};
		this.mMatchEntity.attachChild(this.mMatchSprite);
		
		if(hasPassword()){
			this.mMatchEntity.attachChild(new Sprite(530, 50, this.mMatchScene.getLockTexture(), Game.getInstance().getVertexBufferObjectManager()) {});
		}
		
		this.mText = Game.getTextHelper().NewText(pTextX, pTextY, "Match name:"+this.mMatchName+"  Creator: "+Game.getDataHandler().getUsername(Game.getDataHandler().getProfileID(this.mUserID)), pKey);
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
				Log.d("Logd",MatchObject.this.mPassword+" "+String.valueOf(mReady));
				if(mReady==true){
	        		if(mPassword.equals(null)||mPassword.equals("")){
	        			mMatchScene.unregisterUpdateHandler(tempTimer);
	        		}else{
	        			
	        			mMatchScene.unregisterUpdateHandler(tempTimer);
	        			mMatchScene.EnterMatch(mIP, mMatchName,mUserID,mPassword);
	        		}
	        	}
	        }
	    }));
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
