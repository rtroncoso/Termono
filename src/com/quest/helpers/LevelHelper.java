package com.quest.helpers;

import java.util.ArrayList;

public class LevelHelper {

	// ===========================================================
	// Constants
	// ===========================================================
	private float LEVEL_AMOUNT;
	private float BASE_XP; 
	private float GROWTH;
	// ===========================================================
	// Fields
	// ===========================================================
	private ArrayList<Float> TotalExpForLevel;
	private ArrayList<Float> ExpForNextLevel;
	private float PreviousLevelExp;
	private float NextLevelExp;
	// ===========================================================
	// Constructors
	// ===========================================================
	public LevelHelper(float pLEVEL_AMOUNT,float pBase_XP,float pGROWTH){
		LEVEL_AMOUNT = pLEVEL_AMOUNT;
		BASE_XP = pBase_XP;
		GROWTH = pGROWTH;
		
		TotalExpForLevel = new ArrayList<Float>();
		ExpForNextLevel = new ArrayList<Float>();
		TotalExpForLevel.add(0, 0f);//lvl 0
		TotalExpForLevel.add(1, 0f);//lvl 1 - Starting lvl
		TotalExpForLevel.add(2, BASE_XP);//lvl 2
		ExpForNextLevel.add(0, 0f);
		ExpForNextLevel.add(1, BASE_XP);
		PreviousLevelExp = BASE_XP;
		for(int i = 3;i<LEVEL_AMOUNT + 1;i++){
			NextLevelExp = PreviousLevelExp + (GROWTH*PreviousLevelExp) + BASE_XP;
			ExpForNextLevel.add(i - 1, NextLevelExp-PreviousLevelExp);
			TotalExpForLevel.add(i, NextLevelExp);
			PreviousLevelExp = NextLevelExp;
		}
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================
	public float getExptoNextLevel(int currentLvl){
		return ExpForNextLevel.get(currentLvl);
	}

	public float getPlayerExptoNextLevel(float currentExp){
		int i = 2;
		if(currentExp>=BASE_XP){//Evito 0/0
			while((int)(currentExp/TotalExpForLevel.get(i+1))>=1)
				i++;
		}else{
			i = 1;
		}
		return (currentExp-TotalExpForLevel.get(i));
	}
	
	public int getPlayerLevel(float experience){
		int i = 2;
		if(experience>=BASE_XP){//Evito 0/0
			while((int)(experience/TotalExpForLevel.get(i+1))>=1)
				i++;
		}else{
			i = 1;
		}
		return i;
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
