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
	private String mPassword;
	private boolean mStarted = false;
	// ===========================================================
	// Constructors
	// ===========================================================
	public MatchData(){
		
	}
	
	public MatchData(int pMatchID,String pMatchName){
		setMatchID(pMatchID);
		setMatchName(pMatchName);
	}
	
	public MatchData(String pMatchName,String pPassword){
		setPassword(pPassword);
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

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public boolean isStarted() {
		return mStarted;
	}

	public void setStarted(boolean pStarted) {
		this.mStarted = pStarted;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
