package com.quest.helpers;

import java.util.HashMap;

import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.quest.game.Game;

public class TextureHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private HashMap<String, TexturePack> mSpritesheetMap;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public TextureHelper() {
		this.mSpritesheetMap = new HashMap<String, TexturePack>();
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
	
	public TiledTextureRegion getTiledTexture(String pKey, int pID) {
		TexturePack tmpTexturePack = this.mSpritesheetMap.get(pKey);
		TexturePackerTextureRegion tmpTexturePackerRegion = this.mSpritesheetMap.get(pKey).getTexturePackTextureRegionLibrary().get(pID);
		return new TiledTextureRegion(tmpTexturePack.getTexture(), tmpTexturePackerRegion);
	}
	
	public TextureRegion getTexture(String pKey, int pID) {
		return this.mSpritesheetMap.get(pKey).getTexturePackTextureRegionLibrary().get(pID);
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
	public TexturePack loadSpritesheet(String pKey) {
		try {
			this.mSpritesheetMap.put(pKey, new TexturePackLoader(Game.getInstance().getTextureManager(), "spritesheets/").loadFromAsset(Game.getInstance().getAssets(), pKey + ".xml"));
		} catch (TexturePackParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.mSpritesheetMap.get(pKey);
		
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
