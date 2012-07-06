package com.quest.database;

import com.quest.game.Game;

public class DataHandler {

	
	public DataHandler(){
		
	}
	/*
	
	public String isLevelUnLocked(int levelNum){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.isLevelUnLocked(levelNum);
        myDB.close();
        return myReturn;
    }
       
    public int unLockLevel(int levelNum, String isUnLocked){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.unLockLevel(levelNum, isUnLocked);
        myDB.close();
        return myReturn;
    }
    
    public int BeatLevel(int levelNum, String isBeat){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.BeatLevel(levelNum, isBeat);
        myDB.close();
        return myReturn;
    }
    
    public String isLevelBeat(int levelNum){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.isLevelBeat(levelNum);
        myDB.close();
        return myReturn;
    }
    
    */
    
	public String getType(String Name){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getType(Name);
        myDB.close();
        return myReturn;
    }
	
	
	public String getImagePath(String pName){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getImagePath(pName);
        myDB.close();
        return myReturn;
    }
	
	public int getItemPrice(String pName){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getPrice(pName);
        myDB.close();
        return myReturn;
    }
	
	
	public int getInventoryCount(){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getInventoryCount();
        myDB.close();
        return myReturn;
    }
	
	//HACER LOG.D para checkear todo
}
