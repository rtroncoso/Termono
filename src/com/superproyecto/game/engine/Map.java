package com.superproyecto.game.engine;

import org.andengine.extension.tmx.TMXTiledMap;

import android.content.Context;

public class Map {

	/*
	 * FIELDS
	 */
	private Context mContext;
	private TMXTiledMap mTMXTiledMap;

	Map(Context pContext) {
		this.mContext = pContext;

	}

	public Map loadMap(String pPath) {

		return null;
	}

}
