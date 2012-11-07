package com.quest.database.queries;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

public class QueryPlayerLevelUP extends Query implements GameFlags{
	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mPlayerID;
	private int mLevel;
	private int mUnassignedPoints;
	private float mExperience;
	// ===========================================================
	// Constructors
	// ===========================================================
	@Deprecated
	public QueryPlayerLevelUP() {

	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	@Override
	public short getFlag() {
		return FLAG_QUERY_PLAYER_LEVEL_UP;
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		Game.getDataHandler().setPlayerLevel(mLevel,mPlayerID);
		Game.getDataHandler().setPlayerExperience(mPlayerID, mExperience);
		Game.getDataHandler().setPlayerUnassignedPoints(this.mUnassignedPoints, mPlayerID);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public void set(int pPlayerID,int pLevel,int pUnassignedPoints,float pExperience){
		this.setPlayerID(pPlayerID);
		this.setLevel(pLevel);
		this.setUnassignedPoints(pUnassignedPoints);
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
	 * @return the mLevel
	 */
	public int getLevel() {
		return mLevel;
	}

	/**
	 * @param mLevel the mLevel to set
	 */
	public void setLevel(int pLevel) {
		this.mLevel = pLevel;
	}

	public int getUnassignedPoints() {
		return mUnassignedPoints;
	}

	public void setUnassignedPoints(int mUnassignedPoints) {
		this.mUnassignedPoints = mUnassignedPoints;
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