package com.quest.objects;

import org.andengine.extension.tmx.TMXTile;

import com.quest.util.constants.ITriggerAction;

public class Trigger implements ITriggerAction {
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private TMXTile tmxTile;
	private int triggerType;
	private int nextMapNumber;
	private int nextMapX;
	private int nextMapY;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	public Trigger(TMXTile tmxTileAt, int triggerType) {
		this.tmxTile = tmxTileAt;
		this.triggerType = triggerType;
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
		return tmxTile;
	}


	/**
	 * @param tmxTile the tmxTile to set
	 */
	public void setTile(TMXTile tmxTile) {
		this.tmxTile = tmxTile;
	}


	/**
	 * @return the triggerType
	 */
	public int getTriggerType() {
		return triggerType;
	}


	/**
	 * @param triggerType the triggerType to set
	 */
	public void setTriggerType(int triggerType) {
		this.triggerType = triggerType;
	}


	/**
	 * @return the nextMapNumber
	 */
	public int getNextMapNumber() {
		return nextMapNumber;
	}


	/**
	 * @param nextMapNumber the nextMapNumber to set
	 */
	public void setNextMapNumber(int nextMapNumber) {
		this.nextMapNumber = nextMapNumber;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
}
