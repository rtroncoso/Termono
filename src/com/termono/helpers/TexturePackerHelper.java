package com.termono.helpers;

import java.util.HashMap;

import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;

import com.termono.game.Game;

public class TexturePackerHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private HashMap<String, TexturePack> mSpritesheetMap;
	private Game mGame;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public TexturePackerHelper(Game pGame) {
		this.mGame = pGame;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public HashMap<String, TexturePack> getSpritesheetMap() {
		return mSpritesheetMap;
	}


	public void setSpritesheetMap(HashMap<String, TexturePack> pSpritesheetMap) {
		this.mSpritesheetMap = pSpritesheetMap;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	/**
	 * @param pFile - Nombre del Archivo XML a cargar (sin el ".xml")
	 * @return TexturePackTextureRegionLibrary
	 */
	public TexturePack loadSpritesheet(String pFile) {
		try {
			this.mSpritesheetMap.put(pFile, new TexturePackLoader(this.mGame.getTextureManager(), "spritesheets/").loadFromAsset(this.mGame.getAssets(), pFile + ".xml"));
		} catch (TexturePackParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSpritesheetMap.get(pFile);
		
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
