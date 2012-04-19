package com.superproyecto.display.hud;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.graphics.Color;
import android.graphics.Typeface;

import com.superproyecto.game.Game;

public class StatsHud extends HUD {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Game mGame;
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	private Text mTermono;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public StatsHud(Game pGame) {
		
		this.mGame = pGame;
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mFontTexture = new BitmapTextureAtlas(this.mGame.getTextureManager(), 256, 256);
		
		this.mFont = new Font(this.mGame.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, true, Color.WHITE);
		
		this.mGame.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		this.mGame.getEngine().getFontManager().loadFont(this.mFont);
		
		this.mTermono = new Text(20, 20, this.mFont, "Termono", "Tuvieja".length(), this.mGame.getVertexBufferObjectManager());
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
