package com.quest.helpers;

import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;

import com.quest.game.Game;
import com.quest.helpers.interfaces.IAsyncCallback;
import com.quest.triggers.MapChangeTrigger;
import com.quest.triggers.Trigger;
import com.quest.util.constants.IMeasureConstants;

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
	private List<MapChangeTrigger> mTriggerTiles;
	private boolean isChangingMap;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public MapHelper() {

		this.mCollideTiles = new ArrayList<TMXTile>();
		this.mTriggerTiles = new ArrayList<MapChangeTrigger>();
		
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

	/**
	 * @return the mTriggerTiles
	 */
	public List<MapChangeTrigger> getTriggerTiles() {
		return mTriggerTiles;
	}

	/**
	 * @param mTriggerTiles the mTriggerTiles to set
	 */
	public void setTriggerTiles(List<MapChangeTrigger> mTriggerTiles) {
		this.mTriggerTiles = mTriggerTiles;
	}

	/**
	 * @return the isChangingMap
	 */
	public boolean isChangingMap() {
		return isChangingMap;
	}

	/**
	 * @param isChangingMap the isChangingMap to set
	 */
	public void setChangingMap(boolean isChangingMap) {
		this.isChangingMap = isChangingMap;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	public void loadMap(String pName) {
		// Detach old Map Layers
		if(this.mCurrentMap != null) {
			for(final TMXLayer tLayer : this.mCurrentMap.getTMXLayers()) {
				Game.getSceneManager().getGameScene().getMapLayer().detachChild(tLayer);
			}
			this.mCollideTiles.clear();
			this.mTriggerTiles.clear();
		}
		
		// Load the map
		try {
			this.mCurrentMap = this.mTmxLoader.loadFromAsset("tmx/" + pName + ".tmx");
		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}

		// Attach new Map Layers
		for(final TMXLayer tLayer : this.mCurrentMap.getTMXLayers()) {
			Game.getSceneManager().getGameScene().getMapLayer().attachChild(tLayer);
		}
		
		// Set Map Bounds 
		Game.getSceneManager().getDisplay().setMapBounds(this.mCurrentMap.getTMXLayers().get(0).getWidth(), this.mCurrentMap.getTMXLayers().get(0).getHeight());
		
		// The for loop cycles through each object on the map
		for (final TMXObjectGroup group : this.mCurrentMap.getTMXObjectGroups()) {

			// Gets the object layer with these properties. Use if
			// you have many object layers, Otherwise this can be
			// removed
			if (group.getTMXObjectGroupProperties().containsTMXProperty("Collide", "true")) {

				for (final TMXObject object : group.getTMXObjects()) {

					int objectX = object.getX() + TILE_SIZE / 2;
					int objectY = object.getY() + TILE_SIZE / 2;
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
			
			if(group.getTMXObjectGroupProperties().containsTMXProperty("MapChangeTrigger", "true")) {

				for (final TMXObject object : group.getTMXObjects()) {

					int objectX = object.getX() + TILE_SIZE / 2;
					int objectY = object.getY() + TILE_SIZE / 2;
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
							
							MapChangeTrigger tmpTrigger = new MapChangeTrigger(tempTile) {
								
								@Override
								public void onHandleTriggerAction() {
									// TODO Auto-generated method stub
									super.onHandleTriggerAction();
									
									// Check if it's already in a map change
									if(MapHelper.this.isChangingMap) return;
									MapHelper.this.isChangingMap = true;
									
									// Set our constants
									final int nextMapNumber = this.mNextMapNumber;
									final int nextMapX = this.mNextMapX;
									final int nextMapY = this.mNextMapY;
									
									// Load the map in the background
									Game.getSceneManager().saveCurrentSceneState(true);
									Game.getSceneManager().setLoadingScene();
									Game.getInstance().runOnUiThread(new Runnable() {
								        @Override
								        public void run() {
											 
									        new AsyncTaskLoader().execute(new IAsyncCallback() {

												@Override
												public void workToDo() {
													// TODO Auto-generated method stub
													
													// Load it and set new Player's position
													MapHelper.this.loadMap(String.valueOf(nextMapNumber));
													Game.getPlayerHelper().getPlayer("Player").setTileAt((nextMapX == 0) ? Game.getPlayerHelper().getPlayer("Player").getTMXTileAt().getTileColumn() : nextMapX, 
															(nextMapY == 0) ? Game.getPlayerHelper().getPlayer("Player").getTMXTileAt().getTileRow() : nextMapY);
													
												}

												@Override
												public void onComplete() {
													// TODO Auto-generated method stub

											        // Map loaded!
													MapHelper.this.isChangingMap = false;
													Game.getSceneManager().restoreSavedScene();
													Game.getSceneManager().getDisplay().setZoom(1.6f);
												}
									        });
								        }
									});
								}
							};
							
							// Set our Trigger info
							tmpTrigger.setNextMapNumber(Integer.parseInt(object.getTMXObjectProperties().get(0).getValue()));
							tmpTrigger.setNextMapX(Integer.parseInt(object.getTMXObjectProperties().get(1).getValue()));
							tmpTrigger.setNextMapY(Integer.parseInt(object.getTMXObjectProperties().get(2).getValue()));
							
							// Add it to the list
							this.mTriggerTiles.add(tmpTrigger);
						}
					}
				}
			}
		}
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

	public Trigger checkTrigger(TMXTile tmxTileAt) {
		for(Trigger tmpTrigger : this.mTriggerTiles) {
			if(tmpTrigger.getTile().equals(tmxTileAt)) {
				return tmpTrigger;
			}
		}
		return null;
	}
	
	public boolean checkCollision(TMXTile tmxTileAt) {
		if(this.mCollideTiles.contains(tmxTileAt))
			return true;
		else
			return false;
	}
	
	public boolean isLegalPosition(int pPositionX, int pPositionY) {
		
		// Check for Map Bounds
		if(pPositionX <= 0 || pPositionY <= 0 || pPositionX >= (MAP_WIDTH * TILE_SIZE) ||  pPositionY >= (MAP_HEIGHT * TILE_SIZE)) return false;

		// Get the Tile
		final TMXTile tmxTileTo = Game.getMapManager().getTMXTileAt(pPositionX, pPositionY);
		if(this.checkCollision(tmxTileTo)) return false;
		
		return true; // Everything ok!
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
