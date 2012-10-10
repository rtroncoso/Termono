package com.quest.helpers;

import java.util.ArrayList;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.quest.game.Game;
import com.quest.objects.BooleanMessage;
import com.quest.objects.InputText;

public class TextHelper{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private BitmapTextureAtlas mNormalFontTexture;
	private StrokeFont mNormalFont;
	private Text mText;
	private ArrayList<Text> mList;
	private Text mDeletableText;	
	private enum FontUsed {
		Normal,
		Supa,
		Custom
	}
	private boolean mReady = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	public TextHelper(/*FontUsed pFontUsed*/){
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			this.mNormalFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256);		
			this.mNormalFont = new StrokeFont(Game.getInstance().getFontManager(), this.mNormalFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, true, Color.WHITE, 2, Color.WHITE);
			Game.getInstance().getEngine().getTextureManager().loadTexture(this.mNormalFontTexture);
			Game.getInstance().getEngine().getFontManager().loadFont(this.mNormalFont);
			
		//hacer las otras fonts
		this.mList = new ArrayList<Text>();
		this.mDeletableText = new Text(0, 0, this.mNormalFont, "",Game.getInstance().getVertexBufferObjectManager());
	}
	
	//ver que hacer para los text que cambian
	public Text NewText(float X,float Y,String pText,String pKey/*,FontUsed pFontUsed*/){
		/*
		switch(pFontUsed){
		case Normal:
			switch para usar otras fonts
		}
		*/
		Text tempText =  new Text(X, Y, this.mNormalFont, pText,Game.getInstance().getVertexBufferObjectManager());
		tempText.setUserData(pKey);
		if(mList.contains(this.mDeletableText)){
			this.mList.set(mList.indexOf(mDeletableText),tempText);
		}else{
			this.mList.add(tempText);
		}
		return tempText;
	}
	
	
	
	public void EraseText(String pKey){
		boolean found = false;
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				this.mList.set(i,mDeletableText);
				found = true;
			}else{
				if(i==mList.size()-1 & found == false){
					Log.d("Quest!","TextHelper: Erase - No text matches key '"+pKey+"'");
				}
			}	
		}
	}
	
	
	public void ChangeText(String pText,String pKey,float X,float Y){
		boolean found = false;
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				Text tempText = this.mList.get(i);
				tempText.setText(pText);
				tempText.setX(X);
				tempText.setY(Y);
				found = true;
			}else{
				if(i==mList.size()-1 && found == false){
					Log.d("Quest!","TextHelper: Change - No text matches key '"+pKey+"'");
				}
			}	
		}
	}
	
	public void ClearText(String pKey){
		boolean found = false;
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				Text tempText = this.mList.get(i);
				tempText.setText("");
				tempText.setX(0);
				tempText.setY(0);
				found = true;
			}else{
				if(i==mList.size()-1 && found == false){
					Log.d("Quest!","TextHelper: Clear - No text matches key '"+pKey+"'");
				}
			}	
		}
	}
	
	public InputText NewInputText(float pX,float pY,final String title, final String message, TiledTextureRegion texture,int textOffsetX, int textOffsetY, boolean isPassword){
		return new InputText(pX, pY, title, message, texture, this.mNormalFont, textOffsetX, textOffsetY, isPassword, Game.getInstance().getVertexBufferObjectManager(), Game.getInstance());
	}
		
	public void FlushText(String pString){
		int a = 0;
		for(int i = 0;i<mList.size();i++){
			String[] temp = mList.get(i).getUserData().toString().split(";");
			if(temp[0].equals(pString)){
				a++;
				this.mList.set(i,mDeletableText);
			}
		}
		Log.d("Quest!","TextHelper - FlushText: Flushed "+String.valueOf(a)+" items.");
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Text getText(String pKey){
		int a=0;
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				a = i;
			}	
		}
		return mList.get(a);
	}

	public Font getFont(){
		return this.mNormalFont;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
