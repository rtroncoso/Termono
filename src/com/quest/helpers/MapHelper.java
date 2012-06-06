package com.quest.helpers;

import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import com.quest.game.Game;

public class MapHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private TMXTiledMap mTMXTiledMap;
	private Game mGame;
	private String mPath;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Solo instancia el game
	 * @param pGame
	 */
	public MapHelper(Game pGame) {
		this.mGame = pGame;
		
	}
	
	public void loadMap(String pPath) {
		this.mPath = pPath;
		
		try {
			TMXLoader tmxLoader = new TMXLoader(this.mGame.getAssets(), this.mGame.getEngine().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.mGame.getVertexBufferObjectManager());
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/" + this.mPath + ".tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public TMXTiledMap getTMXTiledMap() {
		return mTMXTiledMap;
	}

	public void setTMXTiledMap(TMXTiledMap pTMXTiledMap) {
		this.mTMXTiledMap = pTMXTiledMap;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
