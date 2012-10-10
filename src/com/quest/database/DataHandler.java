package com.quest.database;

import android.util.Log;

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
	
	public String getUsername(String pUserID){
		return this.mUserDB.getUsername(pUserID);
	}	
	
	public void setUsername(int pProfileID,String pUsername){
		this.mUserDB.setUsername(pProfileID,pUsername);
	}	
	
	public int getProfileID(String pUserID){
		return this.mUserDB.getProfileID(pUserID);
	}
	
	public boolean CheckAndAddProfile(String pUserID, String pUsername) {
		if(this.mUserDB.checkifProfileExists(pUserID)){
			this.mUserDB.setUsername(this.mUserDB.getProfileID(pUserID), pUsername);//Lo updateo por si cambio el username
			return true;//Existe el profile
		}else{
			this.mUserDB.addNewProfile(pUserID,pUsername);//lo agrego
			return false;
		}
		
	}
	
	//MatchProfile
	public int AddNewMatch(int pProfileID,String pName,String pPassword, boolean pJoined){
		if(pPassword.equals(null)||pPassword.equals(""))pPassword="**nopass**";
		return this.mUserDB.addNewMatchProfile(pProfileID, this.mUserDB.CreateNewMatch(pName,pPassword), pJoined);
	}
	
	public void AddNewMatchProfile(int pProfileID,int pMatchID,boolean pJoined){
		this.mUserDB.addNewMatchProfile(pProfileID, pMatchID, pJoined);
	}
	
	public boolean checkifJoined(String pUserID,String pMatchName){
		return this.mUserDB.checkifJoined(pUserID, pMatchName);
	}
	
	//Match
	public int getMatchID(String pName, String pUserID){
		return this.mUserDB.getMatchID(pName, this.mUserDB.getProfileID(pUserID));		
	}

	public int getMatchID(String pName){
		return this.mUserDB.getMatchID(pName, 1);		
	}
	
	public int getMatchID(int pRow){
		return this.mUserDB.getMatchID(pRow);		
	}
	
	public String getMatchName(int pMatchID){
		return this.mUserDB.getMatchName(pMatchID);
	}
	
	public boolean MatchExists(String pName,int pProfileID){
		return this.mUserDB.MatchExists(pName, pProfileID);
	}
	
	public String getMatchPassword(int pMatchID){
		return this.mUserDB.getMatchPassword(pMatchID);
	}
	
	public String getMatchPassword(String pMatchName){
		return this.mUserDB.getMatchPassword(pMatchName,1,0);
	}

	public String getMatchPassword(String pMatchName,String pUserID){//Para matches a las que se unio
		return this.mUserDB.getMatchPassword(pMatchName,getProfileID(pUserID),1);
	}
		
	
	public boolean hasPassword(int pMatchID){
		if(this.mUserDB.getMatchPassword(pMatchID).equals("**nopass**")){
			return false;//no password
		}else{
			return true;//pass protected
		}
	}
	
	public boolean hasMatches(){
		return this.mUserDB.hasMatches();
	}
	
	public int getMatchesAmount(){
		return this.mUserDB.getMatchesAmount();
	}
	
	public void DeleteMatch(int pMatchID){
		this.mUserDB.DeleteMatch(pMatchID);
	}
	
	//MatchPlayers
	public int AddNewPlayer(int pMatchID,int pProfileID,int pClass){
		int id = this.mUserDB.addNewMatchPlayers(pMatchID, this.mUserDB.CreateNewPlayer(pProfileID,pClass));
		//this.mUserDB.setModifiers();
		//this.mUserDB.addInventoryItem();
		return id;
	}
	
	
	//Player
	public int[] getPlayerIDifExists(int pProfileID, String pMatchName){
		return this.mUserDB.getPlayerIDifExists(pProfileID, this.mUserDB.getMatchID(pMatchName, 1));
	}
	
	
	public int getPlayerLevel(int pPlayerID){
		return this.mUserDB.getPlayerLevel(pPlayerID);
	}
	
	
	public int getPlayerClass(int pPlayerID){
        return this.mUserDB.getPlayerClass(pPlayerID);
    }
	
	public void setPlayerAttributes(int pPower,int pIntelligence,int pDefense,int pEndurance,int pPlayerID){
		this.mUserDB.setAttributes(pPower, pIntelligence, pDefense, pEndurance, pPlayerID);
	}
	
	public void setPlayerAttributes(int[] pAttributes,int pPlayerID){
		this.mUserDB.setAttributes(pAttributes[0], pAttributes[1], pAttributes[2], pAttributes[3], pPlayerID);
	}
	
	public int[] getPlayerAttributes(int pPlayerID){
		return this.mUserDB.getAttributes(pPlayerID);
	}
	
	public void setPlayerLevel(int pLevel, int pPlayerID){
		this.mUserDB.setPlayerLevel(pLevel,pPlayerID);
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
