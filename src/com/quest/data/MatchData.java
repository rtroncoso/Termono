package com.quest.data;

public class MatchData {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String mUserID;
	private String mMatchName;
	private int mMatchID;//La id que tiene el server del match
	private String mPassword;
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

	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String mUserID) {
		this.mUserID = mUserID;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
