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
		String usr = this.mUserDB.getUsername(pProfileID);
		if(usr.equals("Player")||usr.equals("")){
			return false;//no tiene user
		}else{
			return true;//tiene user
		}
	}
	
	public String getUserID(int pProfileID){
		return this.mUserDB.getUserID(pProfileID);
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
	public int AddNewPlayer(int pMatchID,int pProfileID,int pClass,int pHeadID){
		return this.mUserDB.addNewMatchPlayers(pMatchID, this.mUserDB.CreateNewPlayer(pProfileID,pClass,pHeadID));
	}
	
	
	//Player
	public int[] getPlayerIDifExists(int pProfileID, String pMatchName){
		return this.mUserDB.getPlayerIDifExists(pProfileID, this.mUserDB.getMatchID(pMatchName, 1));
	}	
	
	public int[] getPlayerIDifExists(int pProfileID,int pMatchID){
		return this.mUserDB.getPlayerIDifExists(pProfileID,pMatchID);
	}	
	
	public int getPlayerLevel(int pPlayerID){
		return this.mUserDB.getPlayerLevel(pPlayerID);
	}
	
	public int getPlayerHeadID(int pPlayerID){
        return this.mUserDB.getPlayerHeadID(pPlayerID);
    }
	
	public int getPlayerClass(int pPlayerID){
        return this.mUserDB.getPlayerClass(pPlayerID);
    }
	
	public void setPlayerLevel(int pLevel, int pPlayerID){
		this.mUserDB.setPlayerLevel(pLevel,pPlayerID);
	}
	
	public int getPlayerProfileID(int pPlayerID){
        return this.mUserDB.getPlayerProfileID(pPlayerID);
    }
	
	public void setPlayerCurrentHPMP(int pPlayerID,int currHP, int currMP){
		this.mUserDB.setPlayerMPHP(pPlayerID, currHP, currMP);
	}

	public int[] getPlayerCurrentHPMP(int pPlayerID){
		return this.mUserDB.getPlayerHPMP(pPlayerID);
	}
	
	//Attributes
	public void setPlayerAttributes(int pPower,int pIntelligence,int pDefense,int pEndurance,int pPlayerID){
		this.mUserDB.setAttributes(pPower, pIntelligence, pDefense, pEndurance, pPlayerID);
	}
	
	public void setPlayerAttributes(int[] pAttributes,int pPlayerID){
		this.mUserDB.setAttributes(pAttributes[0], pAttributes[1], pAttributes[2], pAttributes[3], pPlayerID);
	}
	
	public int[] getPlayerAttributes(int pPlayerID){
		return this.mUserDB.getAttributes(pPlayerID);
	}
	
	
	//Inventory
	public int[] getInventoryKeys(int pPlayerID){
		return this.mUserDB.getInventoryKeys(pPlayerID);
	}
	
	public int[] getInventoryItems(int pPlayerID){
		return this.mUserDB.getInventoryItems(pPlayerID);
	}
	
	public int[] getInventoryEquipStatus(int pPlayerID){
		return this.mUserDB.getInventoryEquipStatus(pPlayerID);
	}

	public int[] getInventoryAmounts(int pPlayerID){
		return this.mUserDB.getInventoryAmounts(pPlayerID);
	}
	
	public int[] getEquippedItems(int pPlayerID){
		return this.mUserDB.getInventoryItemsbyEquipStatus(pPlayerID, true);
	}
	
	public int[] getUnequippedItems(int pPlayerID){
		return this.mUserDB.getInventoryItemsbyEquipStatus(pPlayerID, false);
	}
	
	public int addInventoryItem(int pPlayerID,int pItemID, int pAmount){
		int inventoryitemKey;
		if(this.mStaticDB.isItemStackable(pItemID)){
			inventoryitemKey = this.mUserDB.increaseItemAmount(pPlayerID, pItemID, pAmount);
		}else{
			inventoryitemKey = this.mUserDB.addInventoryItem(pPlayerID,pItemID,pAmount);
		}
		return inventoryitemKey;
	}
	
	
	public void InventoryItemDecreaseAmount(int pPlayerID,int pItemKey){
		this.mUserDB.addInventoryItem(pPlayerID, pItemKey, this.mUserDB.getInventoryItemAmount(pPlayerID, pItemKey)-1);
		if(this.mUserDB.getInventoryItemAmount(pPlayerID, pItemKey)<1)this.mUserDB.removeInventoryItem(pPlayerID,pItemKey);
	}

	public void InventoryItemDecreaseAmountby(int pPlayerID,int pItemKey,int pAmount){
		this.mUserDB.addInventoryItem(pPlayerID, pItemKey, this.mUserDB.getInventoryItemAmount(pPlayerID, pItemKey)-pAmount);
		if(this.mUserDB.getInventoryItemAmount(pPlayerID, pItemKey)<1)this.mUserDB.removeInventoryItem(pPlayerID,pItemKey);
	}
	
	
	
	
/*
	
	public String isLevelUnLocked(int levelNum){
        myDatabase myDB = new myDatabase(Game.getInstance().getApplicationContext());  <---- Fijarme si afecta memoria/input
        String myReturn = myDB.isLevelUnLocked(levelNum);
        myDB.close();
        return myReturn;
    }
 */
	
	
	
	//**********
	//ESTATICA
	//**********
	//Class
	public String getClassIconTexture(int pClass){
        return this.mStaticDB.getClassIconTexture(pClass);
    }
	
	public String getClassAnimationTexture(int pClass){
        return this.mStaticDB.getClassAnimationTexture(pClass);
    }
	
	public int getClassAnimationRows(int pClass){
        return this.mStaticDB.getClassAnimationRows(pClass);
    }
	
	public int getClassAnimationCols(int pClass){
        return this.mStaticDB.getClassAnimationCols(pClass);
    }
	
	public int getClassFrameWidth(int pClass){
        return this.mStaticDB.getClassFrameWidth(pClass);
    }
	
	public int getClassFrameHeight(int pClass){
        return this.mStaticDB.getClassFrameHeight(pClass);
    }
	
	
	//Item
	public String getItemName(int pItemID){
        return this.mStaticDB.getItemName(pItemID);
    }
	
	public int getItemType(int pItemID){
        return this.mStaticDB.getItemType(pItemID);
    }
	
	public String getItemIconTexture(int pItemID){
		return this.mStaticDB.getItemIconTexture(pItemID);
    }

	public String getItemAnimationTexture(int pItemID){
		return this.mStaticDB.getItemAnimationTexture(pItemID);
    }
	
	public boolean isItemStackable(int pItemID){
		return this.mStaticDB.isItemStackable(pItemID);
	}
	
	public int getItemBuyPrice(int pItemID){
		return this.mStaticDB.getItemBuyPrice(pItemID);
    }

	public int getItemSellPrice(int pItemID){
		return this.mStaticDB.getItemSellPrice(pItemID);
    }
	
	public String getItemDescription(int pItemID){
		return this.mStaticDB.getItemDescription(pItemID);
    }
	
	public int getItemClass(int pItemID){
		return this.mStaticDB.getItemClass(pItemID);
    }
	
	public int[] getItemModifiers(int pItemID){
		return this.mStaticDB.getItemModifiers(pItemID);
	}
	
	public int getItemModPower(int pItemID){
		return this.mStaticDB.getItemModifiers(pItemID)[0];
	}
	
	public int getItemModIntelligence(int pItemID){
		return this.mStaticDB.getItemModifiers(pItemID)[1];
	}
	
	public int getItemModDefense(int pItemID){
		return this.mStaticDB.getItemModifiers(pItemID)[2];
	}
	
	public int getItemModEndurance(int pItemID){
		return this.mStaticDB.getItemModifiers(pItemID)[3];
	}
	
}
