package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryRegisterPlayerMoney extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private int mMoney;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryRegisterPlayerMoney() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_REGISTER_PLAYER_MONEY;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerMoney(mPlayerID, mMoney);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,int pMoney){
		this.setPlayerID(pPlayerID);
		this.setMoney(pMoney);
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
	 * @return the mMoney
	 */
	public int getMoney() {
		return mMoney;
	}

	/**
	 * @param mMoney the mMoney to set
	 */
	public void setMoney(int pMoney) {
		this.mMoney = pMoney;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}