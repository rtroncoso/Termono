package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QuerySetPlayerAttributes extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private int[] mAttributes;
	private int mUnassigned;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QuerySetPlayerAttributes() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_SET_PLAYER_ATTRIBUTES;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerAttributes(mAttributes[0], mAttributes[1], mAttributes[2], mAttributes[3], mUnassigned, mPlayerID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,int[] pAttributes,int pUnassigned){
		this.setPlayerID(pPlayerID);
		this.setUnassigned(pUnassigned);
		this.setAttributes(pAttributes);
	}
	
	/**
	 * @return the mPlayerID
	 */
	public int getPlayerID() {
		return mPlayerID;
	}

	/**
	 * @param mPlayerID the mPlayerID to set
	 */
	public void setPlayerID(int pPlayerID) {
		this.mPlayerID = pPlayerID;
	}


	public int getUnassigned() {
		return mUnassigned;
	}

	public void setUnassigned(int mUnassigned) {
		this.mUnassigned = mUnassigned;
	}

	/**
	 * @return the mAttributes
	 */
	public int[] getAttributes() {
		return mAttributes;
	}

	/**
	 * @param mAttributes the mAttributes to set
	 */
	public void setAttributes(int[] pAttributes) {
		this.mAttributes = pAttributes;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}