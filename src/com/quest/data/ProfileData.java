package com.quest.data;

public class ProfileData {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private String mUserID;
	private String mUsername;
	private int mProfileID;//La id que tiene el server del profile
	// ===========================================================
	// Constructors
	// ===========================================================
	public ProfileData(){
		
	}
	
	public ProfileData(String pUserID,String pUsername){//El propio
		setUserID(pUserID);
		setUsername(pUsername);
	}
	
	public ProfileData(String pUserID,String pUsername,int pProfileID){//El del server
		setUserID(pUserID);
		setUsername(pUsername);
		setProfileID(pProfileID);
	}
	
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String pUsername) {
		this.mUsername = pUsername;
	}

	public String getUserID() {
		return mUserID;
	}

	public void setUserID(String pUserID) {
		this.mUserID = pUserID;
	}

	public int getProfileID() {
		return mProfileID;
	}

	public void setProfileID(int pProfileID) {
		this.mProfileID = pProfileID;
	}
	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
