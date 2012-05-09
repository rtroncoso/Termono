package com.termono.map;

import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import com.termono.game.Game;

public class Map {

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
	public Map(Game pGame, String pPath) {
		this.mGame = pGame;
		this.mPath = pPath;
		
		try {
			final TMXLoader tmxLoader = new TMXLoader(this.mGame.getAssets(), this.mGame.getEngine().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.mGame.getVertexBufferObjectManager());
			this.mTMXTiledMap = tmxLoader.loadFromAsset("tmx/" + pPath + ".tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}

		final TMXLayer tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// mapa(x)(y)
	
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
