package com.quest.data;

public class PlayerData {

	// ===========================================================
	// Constants
	// ===========================================================
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private int mProfileID;
	private String mUsername;
	private int mPlayerID;
	private int mPositionID;
	private int mClass;
	private int mLevel;
	private int mEndurance,mIntelligence,mPower,mDefense;
	private int mModEndurance,mModIntelligence,mModPower,mModDefense,mModHP,mModMana;
	private int currHP,currMana;
	// ===========================================================
	// Constructors
	// ===========================================================
	public PlayerData(){
		
	}
	
	public PlayerData(String pUsername, int pClass){
		this.setUsername(pUsername);
		this.setPlayerClass(pClass);
	}

	public PlayerData(int pPlayerID,int pClass,int pLevel){
		this.setPlayerID(pPlayerID);
		this.setPlayerClass(pClass);
		this.setPositionID(pLevel);
	}
	
	public void addAttributes(int pEndurance,int pIntelligence,int pPower,int pDefense){
		this.setEndurance(pEndurance);
		this.setIntelligence(pIntelligence);
		this.setPower(pPower);
		this.setDefense(pDefense);
	}
	
	public void updateModifiers(int pEndurance,int pIntelligence,int pPower,int pDefense,int pTotalHP,int pTotalMana,int pModHP,int pModMana){
		this.setModEndurance(pEndurance);
		this.setModIntelligence(pIntelligence);
		this.setModPower(pPower);
		this.setModDefense(pDefense);
		this.setModHP(pModHP);
		this.setModMana(pModMana);
	}
	
	public void updateHPMana(int currHP,int currMana){
		this.setCurrHP(currHP);
		this.setCurrMana(currMana);
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================


	// ===========================================================
	// Getter & Setter
	// ===========================================================
	public int getPlayerID() {
		return mPlayerID;
	}

	public void setPlayerID(int mPlayerID) {
		this.mPlayerID = mPlayerID;
	}

	public int getPositionID() {
		return mPositionID;
	}

	public void setPositionID(int mPositionID) {
		this.mPositionID = mPositionID;
	}

	public int getPlayerClass() {
		return mClass;
	}

	public void setPlayerClass(int mClass) {
		this.mClass = mClass;
	}

	public int getLevel() {
		return mLevel;
	}

	public void setLevel(int mLevel) {
		this.mLevel = mLevel;
	}

	public int getEndurance() {
		return mEndurance;
	}

	public void setEndurance(int mEndurance) {
		this.mEndurance = mEndurance;
	}

	public int getIntelligence() {
		return mIntelligence;
	}

	public void setIntelligence(int mIntelligence) {
		this.mIntelligence = mIntelligence;
	}

	public int getPower() {
		return mPower;
	}

	public void setPower(int mPower) {
		this.mPower = mPower;
	}

	public int getDefense() {
		return mDefense;
	}

	public void setDefense(int mDefense) {
		this.mDefense = mDefense;
	}

	public int getModEndurance() {
		return mModEndurance;
	}

	public void setModEndurance(int mModEndurance) {
		this.mModEndurance = mModEndurance;
	}

	public int getModIntelligence() {
		return mModIntelligence;
	}

	public void setModIntelligence(int mModIntelligence) {
		this.mModIntelligence = mModIntelligence;
	}

	public int getModPower() {
		return mModPower;
	}

	public void setModPower(int mModPower) {
		this.mModPower = mModPower;
	}

	public int getModDefense() {
		return mModDefense;
	}

	public void setModDefense(int mModDefense) {
		this.mModDefense = mModDefense;
	}

	public int getModHP() {
		return mModHP;
	}

	public void setModHP(int mModHP) {
		this.mModHP = mModHP;
	}

	public int getModMana() {
		return mModMana;
	}

	public void setModMana(int mModMana) {
		this.mModMana = mModMana;
	}

	public int getCurrHP() {
		return currHP;
	}

	public void setCurrHP(int currHP) {
		this.currHP = currHP;
	}

	public int getCurrMana() {
		return currMana;
	}

	public void setCurrMana(int currMana) {
		this.currMana = currMana;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	public int getProfileID() {
		return mProfileID;
	}

	public void setProfileID(int mProfileID) {
		this.mProfileID = mProfileID;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
