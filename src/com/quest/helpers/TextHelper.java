package com.quest.helpers;

import java.util.ArrayList;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.quest.constants.GameFlags;
import com.quest.entities.Mob;
import com.quest.game.Game;
import com.quest.objects.InputText;
import com.quest.pools.TextPool;

public class TextHelper implements GameFlags{

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private ArrayList<Text> mTextList;
	private final TextPool mTextPool = new TextPool();
	private StrokeFont mNormalFont;
	private StrokeFont mDamageFont;
	private StrokeFont mHealingFont;
	private StrokeFont mBlueFont;
	private Font mFancyFont;
	private Font mDarkFancyFont;
	private Font mBlackKnightFont;
	// ===========================================================
	// Constructors
	// ===========================================================
	public TextHelper(){
			final ITexture NormalFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture DamageFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture HealingFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture FancyFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture DarkFancyFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture BlueFontTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			final ITexture BlackKnightTexture = new BitmapTextureAtlas(Game.getInstance().getTextureManager(), 256, 256, TextureOptions.BILINEAR);
			
			this.mNormalFont = new StrokeFont(Game.getInstance().getFontManager(), NormalFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, true, Color.WHITE, 1, Color.BLACK);
			this.mDamageFont = new StrokeFont(Game.getInstance().getFontManager(), DamageFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 12, true, Color.rgb(157, 22, 22), 1, Color.rgb(196, 28, 28));
			this.mHealingFont = new StrokeFont(Game.getInstance().getFontManager(), HealingFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 12, true, Color.rgb(22, 157, 22), 1, Color.rgb(28, 196, 28));
			this.mBlueFont = new StrokeFont(Game.getInstance().getFontManager(), BlueFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 12, true, Color.rgb(43, 101, 236), 1, Color.rgb(53, 111, 246));
			this.mDarkFancyFont = new Font(Game.getInstance().getFontManager(), DarkFancyFontTexture, Typeface.createFromAsset(Game.getInstance().getAssets(), "fonts/StoneCross.ttf"),24,true,Color.rgb(59, 39, 39));
			this.mFancyFont = new Font(Game.getInstance().getFontManager(), FancyFontTexture, Typeface.createFromAsset(Game.getInstance().getAssets(), "fonts/StoneCross.ttf"),24,true,Color.rgb(214, 192, 167));
			this.mBlackKnightFont = new Font(Game.getInstance().getFontManager(), BlackKnightTexture, Typeface.createFromAsset(Game.getInstance().getAssets(), "fonts/BlackKnight.ttf"),24,true,Color.rgb(59, 39, 39));
			
			this.mNormalFont.load();
			this.mDamageFont.load();
			this.mHealingFont.load();
			this.mFancyFont.load();
			this.mBlueFont.load();
			this.mDarkFancyFont.load();
			this.mBlackKnightFont.load();
			
		this.mTextList = new ArrayList<Text>();
		this.initTextPool();
	}
	
	private void initTextPool() {
		this.mTextPool.registerText(FLAG_TEXT_TYPE_NORMAL, this.mNormalFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_DAMAGE, this.mDamageFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_HEALING, this.mHealingFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_BLUE, this.mBlueFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_FANCY, this.mFancyFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_FANCY_DARK, this.mDarkFancyFont);
		this.mTextPool.registerText(FLAG_TEXT_TYPE_BLACK_KNIGHT, this.mBlackKnightFont);
	}

	
	public Text addNewText(int TEXT_TYPE_FLAG,float X,float Y,String pText,String pKey){
		final Text tempText = (Text) (TextHelper.this.mTextPool.obtainText(TEXT_TYPE_FLAG));
		this.mTextList.add(tempText);
		tempText.setUserData(pKey);
		tempText.setText(pText);
		tempText.setX(X);
		tempText.setY(Y);
		return tempText;
	}
	
	public void deleteText(String pTextKey){
		int index = this.mTextList.indexOf(this.getText(pTextKey));
		this.mTextPool.recycleText(mTextList.get(index));
		this.mTextList.remove(index);
	}
	
	public void deleteText(Text pText){
		this.mTextList.remove(pText);
		this.mTextPool.recycleText(pText);
	}	
	
	public void ChangeText(String pText,String pTextKey,float X,float Y){
		Text tmpText = this.getText(pTextKey);
		tmpText.setText(pText);
		tmpText.setX(X);
		tmpText.setY(Y);
	}

	public void ChangeText(String pText,String pTextKey){
		Text tmpText = this.getText(pTextKey);
		tmpText.setText(pText);
	}
	
	public void ClearText(String pTextKey){
		Text tmpText = this.getText(pTextKey);
		tmpText.setText("");
		tmpText.setX(-10);
		tmpText.setY(-10);
	}
	
	public void FlushTexts(String pString){
		int a = 0;
		ArrayList<Text> deleteableTextList = new ArrayList<Text>();
		for(int i = 0;i<mTextList.size();i++){
			String[] temp = mTextList.get(i).getUserData().toString().split(";");
			if(temp[0].equals(pString)){
				a++;
				deleteableTextList.add(this.mTextList.get(i));
			}
		}
		this.mTextList.removeAll(deleteableTextList);
		this.mTextPool.recycleTexts(deleteableTextList);
		Log.d("Quest!","TextHelper - FlushText: Flushed "+a+" items.");
	}
	
	public InputText NewInputText(float pX,float pY,final String title, final String message, TiledTextureRegion texture,int textOffsetX, int textOffsetY, boolean isPassword){
		return new InputText(pX, pY, title, message, texture, this.mNormalFont, textOffsetX, textOffsetY, isPassword, Game.getInstance().getVertexBufferObjectManager(), Game.getInstance());
	}
	
	
	public void allocateDefaultTexts(){
		this.mTextPool.getPool(FLAG_TEXT_TYPE_FANCY).batchAllocatePoolItems(4);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_NORMAL).batchAllocatePoolItems(10);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_DAMAGE).batchAllocatePoolItems(10);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_BLUE).batchAllocatePoolItems(5);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_HEALING).batchAllocatePoolItems(3);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_FANCY_DARK).batchAllocatePoolItems(1);
		this.mTextPool.getPool(FLAG_TEXT_TYPE_BLACK_KNIGHT).batchAllocatePoolItems(5);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Text getText(String pKey){
		boolean found = false;
		int a=0;
		for(int i = 0;i<mTextList.size();i++){
			if(mTextList.get(i).getUserData() == pKey){
				a = i;
				found = true;
			}else{
				if(i==mTextList.size()-1 & found == false){
					Log.e("Quest!","TextHelper: GetText - No text matches key '"+pKey+"'");
				}
			}
		}
		return mTextList.get(a);
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
