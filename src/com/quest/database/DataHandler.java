package com.quest.database;

import com.quest.game.Game;

public class DataHandler {
	private StaticDatabase mStaticDB;
	private UserDatabase mUserDB;
	
	public DataHandler(){
		this.mStaticDB = new StaticDatabase(Game.getInstance().getApplicationContext());
		this.mUserDB = new UserDatabase(Game.getInstance().getApplicationContext());
	}
	
//UserDatabase
	
	//Profile
	public boolean CheckUsername(int pProfileID){
		if(this.mUserDB.getUsername(pProfileID).equals("Player")){
			return false;//no tiene user
		}else{
			return true;//tiene user
		}
	}
	
	public String getUsername(int pProfileID){
		return this.mUserDB.getUsername(pProfileID);
	}
	
	public void setUsername(int pProfileID,String pUsername){
		this.mUserDB.setUsername(pProfileID,pUsername);
	}	
	
	public int getProfileID(String pUserID){
		return this.mUserDB.getProfileID(pUserID);
	}
	
	//MatchProfile
	public void AddNewMatch(int pProfileID,boolean pCreator,String pName,String pPassword){
		if(pPassword == "")pPassword="**nopass**";
		this.mUserDB.addNewMatchProfile(pProfileID, this.mUserDB.CreateNewMatch(pName,pPassword), pCreator);
	}
	
	
	//Match
	public int getMatchID(String pName, String pUserID){
		return this.mUserDB.getMatchID(pName, this.mUserDB.getProfileID(pUserID));		
	}
	
	public boolean MatchExists(String pName){
		return this.mUserDB.MatchExists(pName);
	}
	
	public boolean hasPassword(int pMatchID){
		if(this.mUserDB.getMatchPassword(pMatchID)=="**nopass**"){
			return false;//no password
		}else{
			return true;//pass protected
		}
	}
	
	public boolean hasMatches(){
		return this.mUserDB.hasMatches();
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
	public String getItemName(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemName(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemType(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemType(pID);
        myDB.close();
        return myReturn;
    }
	
	
	public String getItemImagePath(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemImagePath(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemBuyPrice(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemBuyPrice(pID);
        myDB.close();
        return myReturn;
    }

	public int getItemSellPrice(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemSellPrice(pID);
        myDB.close();
        return myReturn;
    }
	
	public String getItemDescription(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getItemDescription(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getItemClass(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getItemClass(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getInventoryCount(){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getInventoryCount();
        myDB.close();
        return myReturn;
    }
	/*
	public int getInventoryItemID(int pIndex){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getInventoryItemID(pIndex);
        myDB.close();
        return myReturn;
    }
	*/
	
	public int getItemAmount(int pID){
		 StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
	        int myReturn = myDB.getItemAmount(pID);
	        myDB.close();
	        return myReturn;
	}
	
	
	public int[] getEquippedIDs(int pEstado){
		 StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
	        int[] myReturn = myDB.getEquippedIDs(pEstado);
	        myDB.close();
	        return myReturn;
	}
	
	public boolean isItemEquipped(int pID){
		StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
		int temp = myDB.isItemEquipped(pID);
		myDB.close();
		if(temp == 0){
			return false;
		}else{
			return true;
		}
	}
	
	public void EquipItem(int pID, int pEquipped){
		StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
		myDB.EquipItem(pID, pEquipped);
		myDB.close();
	}
	
	public int getPlayerClass(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getPlayerClass(pID);
        myDB.close();
        return myReturn;
    }
	
	//spell
	public String getSpellName(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getSpellName(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getSpellType(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getSpellType(pID);
        myDB.close();
        return myReturn;
    }
	
	
	public String getSpellImagePath(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getSpellImagePath(pID);
        myDB.close();
        return myReturn;
    }
	
	
	public String getSpellDescription(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getSpellDescription(pID);
        myDB.close();
        return myReturn;
    }
	
	public int getSpellClass(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getSpellClass(pID);
        myDB.close();
        return myReturn;
    }
	
	
	public int[] getClassSpells(int pClass){
		 StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
	        int[] myReturn = myDB.getClassSpells(pClass);
	        myDB.close();
	        return myReturn;
	}
	
	//spellbook
	public int getSpellBookCount(){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getSpellBookCount();
        myDB.close();
        return myReturn;
    }
	
	public int getSpellLevel(int pID){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        int myReturn = myDB.getSpellLevel(pID);
        myDB.close();
        return myReturn;
    }
	
	public void SpellLevelUp(int ID, int pLevel){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        myDB.SpellLevelUp(ID, pLevel);
        myDB.close();
    }
	
	//Spell Effect
	public String getSpellEffect(int pID,int pLevel){
        StaticDatabase myDB = new StaticDatabase(Game.getInstance().getApplicationContext());
        String myReturn = myDB.getSpellEffect(pID,pLevel);
        myDB.close();
        return myReturn;
    }
	
}
