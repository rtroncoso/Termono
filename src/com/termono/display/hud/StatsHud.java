package com.termono.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.graphics.Color;
import android.graphics.Typeface;

import com.termono.game.Game;
import com.termono.player.Enemy;

public class StatsHud extends HUD {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private Enemy mEnemy;
	private BitmapTextureAtlas mFontTexture;
	private StrokeFont mFont;
	private Text mTermono;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public StatsHud(Game pGame, Enemy pEnemy) {
		
		this.mGame = pGame;
		this.mEnemy = pEnemy;
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mFontTexture = new BitmapTextureAtlas(this.mGame.getTextureManager(), 256, 256);
		
		this.mFont = new StrokeFont(this.mGame.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, true, Color.BLACK, 2, Color.WHITE);
		
		this.mGame.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		this.mGame.getEngine().getFontManager().loadFont(this.mFont);
		
	//	this.mTermono = new Text(20, 20, this.mFont, "Termono", "Tuvieja".length(), this.mGame.getVertexBufferObjectManager());
		this.mTermono = new Text(20, 20, this.mFont, String.valueOf(this.mEnemy.GetRandom(1, 4)), "T".length(), this.mGame.getVertexBufferObjectManager());
	}
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public Text getTermono() {
		return mTermono;
	}

	public void setTermono(Text pTermono) {
		this.mTermono = pTermono;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
