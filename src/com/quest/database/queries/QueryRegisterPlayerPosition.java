package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryRegisterPlayerPosition extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private int mMapID;
	private int tileColumn,tileRow;
	
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryRegisterPlayerPosition() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_REGISTER_PLAYER_POSITION;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerCurrentMap(mMapID, mPlayerID);
		Game.getDataHandler().setPlayerPosition(tileColumn, tileRow, mPlayerID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,int pMapID,int tileColumn,int tileRow){
		this.setPlayerID(pPlayerID);
		this.setMapID(pMapID);
		this.setTileColumn(tileColumn);
		this.setTileRow(tileRow);
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

	/**
	 * @return the mMapID
	 */
	public int getMapID() {
		return mMapID;
	}

	/**
	 * @param mMapID the mMapID to set
	 */
	public void setMapID(int pMapID) {
		this.mMapID = pMapID;
	}

	/**
	 * @return the tileColumn
	 */
	public int getTileColumn() {
		return tileColumn;
	}

	/**
	 * @param tileColumn the tileColumn to set
	 */
	public void setTileColumn(int tileColumn) {
		this.tileColumn = tileColumn;
	}

	/**
	 * @return the tileRow
	 */
	public int getTileRow() {
		return tileRow;
	}

	/**
	 * @param tileRow the tileRow to set
	 */
	public void setTileRow(int tileRow) {
		this.tileRow = tileRow;
	}


	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
