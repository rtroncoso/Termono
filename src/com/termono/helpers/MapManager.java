package com.termono.helpers;

import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import com.termono.game.Game;

public class MapManager {

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
	public MapManager(Game pGame, String pPath) {
		this.mGame = pGame;
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
