package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryWritePlayerInventory  extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryWritePlayerInventory() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_WRITE_PLAYER_INVENTORY;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getPlayerHelper().getPlayerbyPlayerID(mPlayerID).getInventory().writeInventorytoDB(mPlayerID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
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

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}