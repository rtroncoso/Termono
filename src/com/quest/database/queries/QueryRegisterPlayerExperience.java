package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryRegisterPlayerExperience extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private float mExperience;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryRegisterPlayerExperience() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_REGISTER_PLAYER_EXPERIENCE;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerExperience(mPlayerID, mExperience);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,float pExperience){
		this.setPlayerID(pPlayerID);
		this.setExperience(pExperience);
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
	 * @return the mExperience
	 */
	public float getExperience() {
		return mExperience;
	}

	/**
	 * @param mExperience the mExperience to set
	 */
	public void setExperience(float pExperience) {
		this.mExperience = pExperience;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}