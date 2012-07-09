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
    
	public int getItemType(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemType(pID);
        myDB.close();
        return myReturn;
    }
	
	
	public String getItemImagePath(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemImagePath(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemBuyPrice(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemBuyPrice(pID);
        myDB.close();
        return myReturn;
    }

	public int getItemSellPrice(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemSellPrice(pID);
        myDB.close();
        return myReturn;
    }
	
	public String getItemDescription(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemDescription(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemClass(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemClass(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getInventoryCount(){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getInventoryCount();
        myDB.close();
        return myReturn;
    }
	
	public int getInventoryItemID(int pIndex){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getInventoryItemID(pIndex);
        myDB.close();
        return myReturn;
    }
	
	public String getItemName(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemName(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemAmount(int pID){
		 myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
	        int myReturn = myDB.getItemAmount(pID);
	        myDB.close();
	        return myReturn;
	}
	
	
	public int[] getEquippedIDs(int pEstado){
		 myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
	        int[] myReturn = myDB.getEquippedIDs(pEstado);
	        myDB.close();
	        return myReturn;
	}
	
	public boolean isItemEquipped(int pID){
		myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
		int temp = myDB.isItemEquipped(pID);
		myDB.close();
		if(temp == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void EquipItem(int pID, int pEquipped){
		myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
		myDB.EquipItem(pID, pEquipped);
		myDB.close();
	}
	
	public int getPlayerClass(int pID){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getPlayerClass(pID);
        myDB.close();
        return myReturn;
    }
	
}
