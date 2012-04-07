package com.superproyecto.game.engine;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;

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
