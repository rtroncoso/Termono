package com.quest.helpers;

import java.util.ArrayList;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.quest.game.Game;

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
	// ===========================================================
	// Constructors
	// ===========================================================
	public TextHelper(/*FontUsed pFontUsed*/){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
			this.mNormalFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256);		
			this.mNormalFont = new StrokeFont(Game.getInstance().getFontManager(), this.mNormalFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, true, Color.BLACK, 2, Color.WHITE);
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
			this.mList.add(mList.indexOf(mDeletableText),tempText);
		}else{
			this.mList.add(tempText);
		}
		return tempText;
	}
	
	
	public void EraseText(String pKey){
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				this.mList.add(i,mDeletableText);
			}else{
				Log.d("Logd","TextHelper: Erase - No text matches key");
			}	
		}
	}
	
	
	public void ChangeText(String pText,String pKey,float X,float Y){
		for(int i = 0;i<mList.size();i++){
			if(mList.get(i).getUserData() == pKey){
				Text tempText = this.mList.get(i);
				tempText.setText(pText);
				tempText.setX(X);
				tempText.setY(Y);
				//hacer que puedas cambiar el key?
			}else{
				if(i==mList.size()-1){
					Log.d("Logd","TextHelper: Change - No text matches key");
				}
			}	
		}
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

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
