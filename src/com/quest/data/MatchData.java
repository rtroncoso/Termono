package com.quest.data;

public class MatchData {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String mMatchName;
	private int mMatchID;//La id que tiene el server del match
	// ===========================================================
	// Constructors
	// ===========================================================
	public MatchData(){
		
	}
	
	public MatchData(int pMatchID,String pMatchName){
		setMatchID(pMatchID);
		setMatchName(pMatchName);
	}
		
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getMatchName() {
		return mMatchName;
	}

	public void setMatchName(String mMatchName) {
		this.mMatchName = mMatchName;
	}

	public int getMatchID() {
		return mMatchID;
	}

	public void setMatchID(int mMatchID) {
		this.mMatchID = mMatchID;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
