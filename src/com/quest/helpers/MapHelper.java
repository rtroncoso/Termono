package com.quest.helpers;

import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import com.quest.game.Game;
import com.quest.interfaces.IMeasureConstants;

public class MapHelper implements IMeasureConstants {

	// ===========================================================
	// Constants
	// ===========================================================
	
	// ===========================================================
	// Fields
	// ===========================================================
	private TMXLoader mTmxLoader;
	private TMXTiledMap mCurrentMap;
	private List<TMXTile> mCollideTiles;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public MapHelper() {

		this.mTmxLoader = new TMXLoader(Game.getInstance().getAssets(), Game.getInstance().getEngine().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, Game.getInstance().getVertexBufferObjectManager());
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public TMXTiledMap getCurrentMap() {
		return mCurrentMap;
	}

	public void setCurrentMap(TMXTiledMap pCurrentMap) {
		this.mCurrentMap = pCurrentMap;
	}

	/**
	 * @return the mCollideTiles
	 */
	public List<TMXTile> getCollideTiles() {
		return mCollideTiles;
	}

	/**
	 * @param mCollideTiles the mCollideTiles to set
	 */
	public void setCollideTiles(List<TMXTile> mCollideTiles) {
		this.mCollideTiles = mCollideTiles;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void loadMap(String pName) {
		// Load the map
		try {
			this.mCurrentMap = this.mTmxLoader.loadFromAsset("tmx/" + pName + ".tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
		
		Game.getSceneManager().getDisplay().setMapBounds(this.mCurrentMap.getTMXLayers().get(0).getWidth(), this.mCurrentMap.getTMXLayers().get(0).getHeight());
		
		// Load Properties
		this.mCollideTiles = new ArrayList<TMXTile>();
		// The for loop cycles through each object on the map
		for (final TMXObjectGroup group : this.mCurrentMap.getTMXObjectGroups()) {

			// Gets the object layer with these properties. Use if
			// you have many object layers, Otherwise this can be
			// removed
			if (group.getTMXObjectGroupProperties().containsTMXProperty("Collide", "true")) {

				for (final TMXObject object : group.getTMXObjects()) {

					int objectX = object.getX() + TILE_SIZE / 2;
					int objectY = object.getY() - TILE_SIZE / 2;
					// Gets the number of rows and columns in the
					// object
					int objectHeight = object.getHeight() / TILE_SIZE;
					int objectWidth = object.getWidth() / TILE_SIZE;

					// Gets the tiles the object covers and puts it
					// into the Arraylist CollideTiles
					for (int TileRow = 0; TileRow < objectHeight; TileRow++) {
						for (int TileColumn = 0; TileColumn < objectWidth; TileColumn++) {
							TMXTile tempTile = this.getTMXTileAt(objectX + TileColumn * TILE_SIZE, objectY
									+ TileRow * TILE_SIZE);
							this.mCollideTiles.add(tempTile);
						}
					}

				}
			}

		}
	}
	
	public boolean collisionCheck(TMXTile tmxTileAt) {
		if(this.mCollideTiles.contains(tmxTileAt))
			return true;
		else
			return false;
	}
	
	public void registerCollisionTile(TMXTile tmxTileAt) {
		this.mCollideTiles.add(tmxTileAt);
	}
	
	public void unregisterCollisionTile(TMXTile tmxTileAt) {
		this.mCollideTiles.remove(tmxTileAt);
	}
	
	public TMXTile getTMXTileAt(float pScreenX, float pScreenY) {
		return this.mCurrentMap.getTMXLayers().get(0).getTMXTileAt(pScreenX, pScreenY);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
