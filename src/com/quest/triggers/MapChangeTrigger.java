package com.quest.triggers;

import org.andengine.extension.tmx.TMXTile;

import com.quest.util.constants.ITriggerAction;

public class MapChangeTrigger extends Trigger implements ITriggerAction {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	protected int mNextMapNumber;
	protected int mNextMapX;
	protected int mNextMapY;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	public MapChangeTrigger(TMXTile tmxTileAt) {
		super(tmxTileAt);
		// TODO Auto-generated constructor stub
	}


	// ===========================================================
	// Getter & Setter
	// ===========================================================
	/**
	 * @return the nextMapNumber
	 */
	public int getNextMapNumber() {
		return mNextMapNumber;
	}


	/**
	 * @param nextMapNumber the nextMapNumber to set
	 */
	public void setNextMapNumber(int nextMapNumber) {
		this.mNextMapNumber = nextMapNumber;
	}


	/**
	 * @return the nextMapX
	 */
	public int getNextMapX() {
		return mNextMapX;
	}


	/**
	 * @param nextMapX the nextMapX to set
	 */
	public void setNextMapX(int nextMapX) {
		this.mNextMapX = nextMapX;
	}


	/**
	 * @return the nextMapY
	 */
	public int getNextMapY() {
		return mNextMapY;
	}


	/**
	 * @param nextMapY the nextMapY to set
	 */
	public void setNextMapY(int nextMapY) {
		this.mNextMapY = nextMapY;
	}

	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
