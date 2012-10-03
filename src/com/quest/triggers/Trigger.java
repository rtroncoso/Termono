package com.quest.triggers;

import org.andengine.extension.tmx.TMXTile;

import com.quest.util.constants.ITriggerAction;

public class Trigger implements ITriggerAction {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private TMXTile mTMXTile;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Trigger(TMXTile tmxTileAt) {
		this.mTMXTile = tmxTileAt;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public void onHandleTriggerAction() {
		// TODO Auto-generated method stub
		
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the tmxTile
	 */
	public TMXTile getTile() {
		return mTMXTile;
	}


	/**
	 * @param tmxTile the tmxTile to set
	 */
	public void setTile(TMXTile tmxTile) {
		this.mTMXTile = tmxTile;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
