package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.quest.game.Game;

public class UserDatabase extends SQLiteOpenHelper {
    static final String dbName = "UserDB";
    static final String tProfile = "Profile";
    	static final String fProfileID = "ProfileID";//key
    	static final String fUserID = "UserID";//la mac
    	static final String fUsername = "Username";
	
	static final String tMatchProfile = "MatchProfile";
		static final String fMatchProfileID = "MatchProfileID";
		static final String fMatchProfileJoined = "Joined";
		//ProfileID
		//MatchID
		
    static final String tMatch = "Match";
    	static final String fMatchID = "MatchID";
    	static final String fMatchName = "Name";
    	static final String fMatchCurrentQuest = "CurrentQuest";
    	static final String fMatchPassword = "Password";
    	static final String fMatchMap = "Map";
    	
    static final String tMatchPlayers = "MatchPlayers";
    	static final String fMatchPlayersID = "MatchPlayersID";
    	//MatchID
    	//PlayerID
    	
    static final String tPlayer = "Player";
    	static final String fPlayerID = "PlayerID";
    	static final String fPlayerLevel = "Level";
    	static final String fPlayerExperience = "Experience";
	    static final String fPlayerCurrentHP = "HP";
	    static final String fPlayerCurrentMana = "Mana";
    	static final String fPlayerPosition = "Position";
    	static final String fPlayerClass = "Class";
    	static final String fPlayerHeadID = "Head";
    	//ProfileID 
    	
    static final String tInventory = "Inventory";	
	    static final String fInventoryID = "InventoryID";
	    //PlayerID
	    
    static final String tInventoryItem = "InventoryItem";
    	static final String fInventoryItemKey = "InventoryItemKey";
    	//inventoryID
	    static final String fInventoryItemAmount = "Amount";
	    static final String fInventoryIsItemEquipped = "IsEquipped";
    	static final String fItemID = "ItemID"; //conectado a la estatica
    	
    static final String tAttributes = "Attributes";
    	static final String fAttributesID = "AttributesID";
	    static final String fAttributesEndurance = "Endurance";
	    static final String fAttributesIntelligence = "Intelligence";
	    static final String fAttributesPower = "Power";
	    static final String fAttributesDefense = "Defense";
	    //PlayerID
	    
	    
    static final String tSpellBook = "SpellBook";
	    static final String fSpellBookID = "SpellBookID";
	    //PlayerID
	    
    static final String tSpellBookSpell = "SpellBookSpell";
    	static final String fSpellBookSpellID = "SpellBookSpellID";
    	//SpellbookID
	    static final String fSpellBookSpellLevel = "Level";
	    static final String fSpellID = "SpellID"; //conectado a la estatica
    
    
	    
	    
	    
    public UserDatabase(Context context) {
            super(context, dbName, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tProfile+" ("+
    			fProfileID+" INTEGER PRIMARY KEY , "+
                fUserID +" TEXT , "+
                fUsername +" TEXT)" 
                );
    	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatchProfile+" ("+
    			fMatchProfileID+" INTEGER PRIMARY KEY , "+
    			fMatchProfileJoined +" INTEGER , "+
    			fProfileID +" INTEGER , "+
    			fMatchID +" INTEGER)" 
                );  	
    	

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatch+" ("+
    			fMatchID+" INTEGER PRIMARY KEY , "+
    			fMatchName +" TEXT , "+
    			fMatchCurrentQuest +" TEXT , "+
    			fMatchPassword +" TEXT , "+
    			fMatchMap +" INTEGER)" 
                );  
    	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatchPlayers+" ("+
    			fMatchPlayersID+" INTEGER PRIMARY KEY , "+
    			fMatchID +" INTEGER , "+
    			fPlayerID +" INTEGER)" 
                );  
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
    			fPlayerID+" INTEGER PRIMARY KEY , "+
    			fProfileID +" INTEGER , "+
    			fPlayerLevel+" INTEGER , "+
    			fPlayerCurrentHP+" INTEGER ,"+
    			fPlayerCurrentMana+" INTEGER ,"+
    			fPlayerExperience+" INTEGER , "+
    			fPlayerPosition +" INTEGER , "+
    			fPlayerHeadID +" INTEGER , "+
    			fPlayerClass +" INTEGER)" 
                );  
    	  
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
    			fInventoryID+" INTEGER PRIMARY KEY , "+
    			fPlayerID+" INTEGER)"
                );     	
    	
	    db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventoryItem+" ("+
	    		fInventoryItemKey+" INTEGER PRIMARY KEY , "+
        		fInventoryID+" INTEGER , "+
        		fInventoryItemAmount+" INTEGER, "+
        		fInventoryIsItemEquipped+" INTEGER , "+//boolean
        		fItemID+" INTEGER)" //conectado a la estatica
                );   
	     
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttributes+" ("+
    			fAttributesID+" INTEGER PRIMARY KEY , "+
    			fAttributesEndurance+" INTEGER , "+
    			fAttributesIntelligence+" INTEGER ,"+
    			fAttributesPower+" INTEGER ,"+
    			fAttributesDefense+" INTEGER ,"+
	     		fPlayerID+" INTEGER)"
	             );	        
        
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBook+" ("+
				fSpellBookID+" INTEGER PRIMARY KEY , "+
				fPlayerID+" INTEGER)"
                 );			    

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBookSpell+" ("+
    			fSpellBookSpellID+" INTEGER PRIMARY KEY , "+
    			fSpellBookID +" INTEGER , "+
    			fSpellBookSpellLevel +" INTEGER , "+
    			fSpellID +" INTEGER)" //conectado a la estatica
                );  	    	
				 
		 
		 ContentValues cv = new ContentValues();
		 cv.put(fUserID,Game.getUserID());
		// cv.put(fUserID,"00:00:00:00:00:00");
		 cv.put(fUsername, "***Player***");
		 db.insert(tProfile, null, cv);	                    
         cv.clear();
         
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+tProfile);//*** sacar
    		
            onCreate(db);
    }
    
         
    
    
    //QUERIES
    //Profile
    public String getUserID(int pProfileID) {
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fUserID +" FROM "+ tProfile +" WHERE "+ fProfileID +"=?",new String[]{String.valueOf(pProfileID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fUserID);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
   	}
    
    public String getUsername(int pProfileID) {
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fUsername +" FROM "+ tProfile +" WHERE "+ fProfileID +"=?",new String[]{String.valueOf(pProfileID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fUsername);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
   	}
    
    public String getUsername(String pUserID) {
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fUsername +" FROM "+ tProfile +" WHERE "+ fUserID +"=?",new String[]{String.valueOf(pUserID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fUsername);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
   	}
    
    public void setUsername(int pProfileID,String pUsername) {
        ContentValues cv = new ContentValues();
        cv.put(fUsername,pUsername);
        this.getWritableDatabase().update(tProfile, cv, fProfileID+"=?", new String []{String.valueOf(pProfileID)});
        cv.clear();
        this.close();
   }
    
   public int getProfileID(String pUserID){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fProfileID +" FROM "+ tProfile +" WHERE "+ fUserID +"=?",new String[]{String.valueOf(pUserID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fProfileID);
	    int myAnswer = myCursor.getInt(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
   }
    
   public boolean checkifProfileExists(String pUserID){
   	Cursor myCursor = this.getReadableDatabase().rawQuery("Select * from "+tProfile+" where "+fUserID+"=?", new String[]{String.valueOf(pUserID)});
   	int x= myCursor.getCount();
       myCursor.close();
       this.close();
       if(x>0){
       	return true;
       }else{
       	return false;
       }
   }
    
   public void addNewProfile(String pUserID,String pUsername){
 	  ContentValues cv = new ContentValues();
      cv.put(fUserID,pUserID);
      cv.put(fUsername,pUsername);
      this.getWritableDatabase().insert(tProfile, null, cv);
      cv.clear();
      this.close();     
   }
    
    //ProfileMatch
    public int addNewMatchProfile(int pProfileID,int pMatchID, boolean pJoined){
    	  Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+ tProfile+","+tMatchProfile +" ON " + tMatchProfile+"."+fProfileID+" = "+tProfile+"."+fProfileID+" and "+tMatchProfile+"."+fMatchID+" =? and "+tProfile+"."+fProfileID+" =?",new String[]{String.valueOf(pMatchID),String.valueOf(pProfileID)});
	  	  int count = myCursor.getCount();
	  	  myCursor.close();
	  	  this.close();
	  	  if(count==0){//No existe el MatchProfile, lo agrego
  	    	ContentValues cv = new ContentValues();
            cv.put(fProfileID,pProfileID);
            cv.put(fMatchID,pMatchID);
            cv.put(fMatchProfileJoined,pJoined);
            this.getWritableDatabase().insert(tMatchProfile, null, cv);
            cv.clear();
            this.close(); 
	  	  }//Si ya existe no hace falta agregarlo
	  	return pMatchID;
    }
    
    public boolean checkifJoined(String pUserID,String pMatchName){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+ tProfile+","+tMatch+","+tMatchProfile +" ON " + tMatchProfile+"."+fProfileID+" = "+tProfile+"."+fProfileID+" and "+tMatch+"."+fMatchID+" = "+tMatchProfile+"."+fMatchID+" and "+tMatch+"."+fMatchName+" =? and "+tProfile+"."+fUserID+" =? and "+tMatchProfile+"."+fMatchProfileJoined+" = 1",new String[]{pMatchName,pUserID});
	    int count = myCursor.getCount();
	    myCursor.close();
	    this.close();
	    if(count>0){
	    	return true;//Existe la partida
	    }else{
	    	return false;//No existe la partida
	    }
    }
    
    //Match
    public int CreateNewMatch(String pName,String pPassword){
	  	  ContentValues cv = new ContentValues();
	      cv.put(fMatchName,pName);
	      cv.put(fMatchPassword,pPassword);
	      this.getWritableDatabase().insert(tMatch, fMatchID, cv);
	      Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMatchID+" from "+tMatch+" order by "+fMatchID+" desc limit 1", null);
		  myCursor.moveToFirst();
		  int index = myCursor.getColumnIndex(fMatchID);
		  int myAnswer = myCursor.getInt(index);
		  myCursor.close();
	      this.close();
	      return myAnswer;//devuelve el ID del match recien creado
    }
    
    public boolean hasMatches(){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("Select * from "+tMatch, null);
    	int x= myCursor.getCount();
        myCursor.close();
        this.close();
        if(x>0){
        	return true;
        }else{
        	return false;
        }
    }

    public int getMatchID(String pName, int pOwnerID){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ tMatch+"."+fMatchID +" FROM "+ tMatch+","+tMatchProfile+","+tProfile +" ON " + tMatchProfile+"."+fProfileID+" = "+tProfile+"."+fProfileID+" and "+tMatch+"."+fMatchID+" = "+tMatchProfile+"."+fMatchID+" and "+tProfile+"."+fProfileID+" = "+String.valueOf(pOwnerID)+" and "+tMatch+"."+fMatchName+" =?",new String[]{pName});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fMatchID);
	    int myAnswer = myCursor.getInt(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
    }
    
    
    public int getMatchID(int pRow){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+ tMatch+"."+fMatchID +" from "+tMatch+","+tMatchProfile+" on "+tMatchProfile+"."+fMatchID+" = "+tMatch+"."+fMatchID+" and "+tMatchProfile+"."+fProfileID+" = 1 order by "+ tMatch+"."+fMatchID+" asc limit 1 offset ?", new String[]{String.valueOf(pRow)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fMatchID);
	    int myAnswer = myCursor.getInt(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
    }
    
    public String getMatchName(int pMatchID){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fMatchName +" FROM "+ tMatch +" WHERE "+ fMatchID +"=?",new String[]{String.valueOf(pMatchID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fMatchName);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
    }
    
    public boolean MatchExists(String pName, int pProfileID){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+ tMatch+","+tMatchProfile +" ON " + tMatchProfile+"."+fProfileID+" = "+String.valueOf(pProfileID)+" and "+tMatch+"."+fMatchID+" = "+tMatchProfile+"."+fMatchID+" and "+tMatch+"."+fMatchName+" =?",new String[]{pName});
	    int count = myCursor.getCount();
	    myCursor.close();
	    this.close();
	    if(count>0){
	    	return true;//Existe la partida
	    }else{
	    	return false;//No existe la partida
	    }
    }
    
    public String getMatchPassword(int pMatchID){
	    Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ fMatchPassword +" FROM "+ tMatch +" WHERE "+ fMatchID +"=?",new String[]{String.valueOf(pMatchID)});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fMatchPassword);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
    }
    
    public String getMatchPassword(String pMatchName,int pProfileID,int pJoined){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+ tMatch+"."+fMatchPassword +" FROM "+ tMatch+","+tMatchProfile +" ON " + tMatchProfile+"."+fProfileID+" = "+String.valueOf(pProfileID)+" and "+tMatch+"."+fMatchID+" = "+tMatchProfile+"."+fMatchID+" and "+tMatch+"."+fMatchName+" =? and "+tMatchProfile+"."+fMatchProfileJoined+" = "+String.valueOf(pJoined),new String[]{pMatchName});
	    myCursor.moveToFirst();
	    int index = myCursor.getColumnIndex(fMatchPassword);
	    String myAnswer = myCursor.getString(index);
	    myCursor.close();
	    this.close();
	    return myAnswer;
    }
    
    public int getMatchesAmount(){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("Select * from "+tMatch+","+tMatchProfile+" on "+tMatchProfile+"."+fMatchID+" = "+tMatch+"."+fMatchID+" and "+tMatchProfile+"."+fProfileID+" = 1", null);
    	int x= myCursor.getCount();
        myCursor.close();
        this.close();
        return x;
    }
    
    public void DeleteMatch(int pMatchID){//Updetear con el valor de todo
    	String[] args = new String[]{String.valueOf(pMatchID)};
    	this.getWritableDatabase().delete(tMatch, fMatchID+" =?", args);
    	this.getWritableDatabase().delete(tMatchProfile, fMatchID+" =?", args);
    	this.close();
    }
    
    
    //ProfileMatch
    public int addNewMatchPlayers(int pMatchID,int pPlayerID){
    	  ContentValues cv = new ContentValues();
          cv.put(fMatchID,pMatchID);
          cv.put(fPlayerID,pPlayerID);
          this.getWritableDatabase().insert(tMatchPlayers, fMatchPlayersID, cv);
          cv.clear();
          this.close();
          return pPlayerID;
    }
    
    //Player
    public int CreateNewPlayer(int pProfileID,int pClass,int pHeadID){
	  	  ContentValues cv = new ContentValues();
	  	  cv.put(fProfileID,pProfileID);
	  	  cv.put(fPlayerClass,pClass);
	  	  cv.put(fPlayerHeadID,pHeadID);
	      this.getWritableDatabase().insert(tPlayer, fPlayerID, cv);
	      Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fPlayerID+" from "+tPlayer+" order by "+fPlayerID+" desc limit 1", null);
		  myCursor.moveToFirst();
		  int index = myCursor.getColumnIndex(fPlayerID);
		  int myAnswer = myCursor.getInt(index);
		  myCursor.close();
		  cv.clear();
		  cv.put(fPlayerID, myAnswer);
		  this.getWritableDatabase().insert(tAttributes, fAttributesID, cv);
		  this.getWritableDatabase().insert(tInventory, fInventoryID, cv);
		  this.getWritableDatabase().insert(tSpellBook, fSpellBookID, cv);
	      this.close();
	      return myAnswer;//devuelve el ID del player recien creado
    }
    
    public int[] getPlayerIDifExists(int pProfileID, int pMatchID){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT "+tPlayer+"."+fPlayerID+" FROM "+tMatchPlayers+","+tPlayer +" ON " + tPlayer+"."+fProfileID+" =? and "+tMatchPlayers+"."+fMatchID+" =? and "+tMatchPlayers+"."+fPlayerID+" = "+tPlayer+"."+fPlayerID,new String[]{String.valueOf(pProfileID),String.valueOf(pMatchID)});
    	int count = myCursor.getCount();
	    if(count>0){
	       	 int myAnswer[] = new int[myCursor.getCount()];
	       	 int index = myCursor.getColumnIndex(fPlayerID);
	       	 for(int i = 0; i < myCursor.getCount(); i++){
	       		myCursor.moveToPosition(i);
	       		myAnswer[i] = myCursor.getInt(index);
	       	 }
			 myCursor.close();
			 this.close();
			 return myAnswer;
	    }else{
	    	this.close();
	    	return new int[]{};
	    }
    }
    
    public int getPlayerLevel(int pPlayerID){
      Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fPlayerLevel+" from "+tPlayer+" where "+fPlayerID+" =?", new String[]{String.valueOf(pPlayerID)});
	  myCursor.moveToFirst();
	  int index = myCursor.getColumnIndex(fPlayerLevel);
	  int myAnswer = myCursor.getInt(index);
	  myCursor.close();
      this.close();
      return myAnswer;
    }
    
    public int getPlayerClass(int pPlayerID){
   	  Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fPlayerClass+" from "+tPlayer+" where "+fPlayerID+" =?", new String[]{String.valueOf(pPlayerID)});
   	  myCursor.moveToFirst();
	  int index = myCursor.getColumnIndex(fPlayerClass);
	  int myAnswer = myCursor.getInt(index);
	  myCursor.close();
      this.close();
      return myAnswer;
   }
    
    public int getPlayerProfileID(int pPlayerID){
   	  Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fProfileID+" from "+tPlayer+" where "+fPlayerID+" =?", new String[]{String.valueOf(pPlayerID)});
   	  myCursor.moveToFirst();
	  int index = myCursor.getColumnIndex(fProfileID);
	  int myAnswer = myCursor.getInt(index);
	  myCursor.close();
      this.close();
      return myAnswer;
   }
    
    public void setPlayerLevel(int pLevel,int pPlayerID){
    	ContentValues cv = new ContentValues();
        cv.put(fPlayerLevel,pLevel);
        this.getWritableDatabase().update(tPlayer, cv, fPlayerID+" =?",new String[]{String.valueOf(pPlayerID)});
        cv.clear();
        this.close();
      }

    public int getPlayerHeadID(int pPlayerID){
     	  Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fPlayerHeadID+" from "+tPlayer+" where "+fPlayerID+" =?", new String[]{String.valueOf(pPlayerID)});
       	  myCursor.moveToFirst();
    	  int index = myCursor.getColumnIndex(fPlayerHeadID);
    	  int myAnswer = myCursor.getInt(index);
    	  myCursor.close();
          this.close();
          return myAnswer;    	
    }

    public void setPlayerHead(int pHeadID,int pPlayerID){
    	ContentValues cv = new ContentValues();
        cv.put(fPlayerHeadID,pHeadID);
        this.getWritableDatabase().update(tPlayer, cv, fPlayerID+" =?",new String[]{String.valueOf(pPlayerID)});
        cv.clear();
        this.close();
      }
    
    public void setPlayerMPHP(int pPlayerID, int currHP, int currMP){
    	ContentValues cv = new ContentValues();
    	cv.put(fPlayerCurrentHP,currHP);
    	cv.put(fPlayerCurrentMana,currMP);
        this.getWritableDatabase().update(tPlayer, cv, fPlayerID+" =?",new String[]{String.valueOf(pPlayerID)});
        cv.clear();
        this.close();
    }
    
    public int[] getPlayerHPMP(int pPlayerID){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("SELECT * FROM "+tPlayer+" where " +fPlayerID+" =?",new String[]{String.valueOf(pPlayerID)});
    	int[] myAnswer = new int[2];
    	myCursor.moveToFirst();
    	int index = myCursor.getColumnIndex(fPlayerCurrentHP);
   		myAnswer[0] = myCursor.getInt(index);
   		index = myCursor.getColumnIndex(fPlayerCurrentMana);
   		myAnswer[1] = myCursor.getInt(index);
   		myCursor.close();
   		this.close();
   		return myAnswer;
    }

    //Atributtes
    public void setAttributes(int pPower,int pIntelligence,int pDefense,int pEndurance,int pPlayerID){
  	  ContentValues cv = new ContentValues();
      cv.put(fAttributesPower,pPower);
      cv.put(fAttributesIntelligence, pIntelligence);
      cv.put(fAttributesDefense, pDefense);
      cv.put(fAttributesEndurance, pEndurance);
      this.getWritableDatabase().update(tAttributes, cv, fPlayerID+" =?",new String[]{String.valueOf(pPlayerID)});
      cv.clear();
      this.close();
    }
    
    public int[] getAttributes(int pPlayerID){
      int[] myAnswer = new int[4];
	  Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+tAttributes+".* from "+tPlayer+","+tAttributes+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tAttributes+"."+fPlayerID, new String[]{String.valueOf(pPlayerID)});
 	  myCursor.moveToFirst();
  	  int index = myCursor.getColumnIndex(fAttributesPower);
  	  myAnswer[0]=myCursor.getInt(index);
  	  index = myCursor.getColumnIndex(fAttributesIntelligence);
  	  myAnswer[1]=myCursor.getInt(index);
  	  index = myCursor.getColumnIndex(fAttributesDefense);
  	  myAnswer[2]=myCursor.getInt(index);
  	  index = myCursor.getColumnIndex(fAttributesEndurance);
  	  myAnswer[3]=myCursor.getInt(index);
  	  myCursor.close();
  	  this.close();
  	  return myAnswer;
     }
    
    //Inventory
    public int[] getInventoryItems(int pPlayerID){//All inventory Item IDs
    	Cursor myCursor = this.getReadableDatabase().rawQuery("select "+tInventoryItem+"."+fInventoryItemKey+" from "+tPlayer+","+tInventory+","+tInventoryItem+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fPlayerID+" and "+tInventory+"."+fInventoryID+" = "+tInventoryItem+"."+fInventoryID, new String[]{String.valueOf(pPlayerID)});
    	int index = myCursor.getColumnIndex(fItemID);
        int myAnswer[] = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); i++){
        	myCursor.moveToPosition(i);
        	myAnswer[i] = myCursor.getInt(index);
        }
    	 myCursor.close();
    	 return myAnswer;
    }
    
    public int[] getInventoryEquipStatus(int pPlayerID){//All inventory item equipped status
    	Cursor myCursor = this.getReadableDatabase().rawQuery("select "+tInventoryItem+"."+fInventoryIsItemEquipped+" from "+tPlayer+","+tInventory+","+tInventoryItem+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fPlayerID+" and "+tInventory+"."+fInventoryID+" = "+tInventoryItem+"."+fInventoryID, new String[]{String.valueOf(pPlayerID)});
    	int index = myCursor.getColumnIndex(fInventoryIsItemEquipped);
        int myAnswer[] = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); i++){
        	myCursor.moveToPosition(i);
        	myAnswer[i] = myCursor.getInt(index);
        }
    	 myCursor.close();
    	 return myAnswer;
    }
    
    public int[] getInventoryAmounts(int pPlayerID){//All inventory amounts
    	Cursor myCursor = this.getReadableDatabase().rawQuery("select "+tInventoryItem+"."+fInventoryItemAmount+" from "+tPlayer+","+tInventory+","+tInventoryItem+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fPlayerID+" and "+tInventory+"."+fInventoryID+" = "+tInventoryItem+"."+fInventoryID, new String[]{String.valueOf(pPlayerID)});
    	int index = myCursor.getColumnIndex(fInventoryItemAmount);
        int myAnswer[] = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); i++){
        	myCursor.moveToPosition(i);
        	myAnswer[i] = myCursor.getInt(index);
        }
    	 myCursor.close();
    	 return myAnswer;
    }
    
    public int[] getInventoryItemsbyEquipStatus(int pPlayerID,boolean pEquipped){//Equipped/Unequipped only
    	String mEquipped = "0";
    	if(pEquipped)mEquipped = "1";
    	Cursor myCursor = this.getReadableDatabase().rawQuery("select "+tInventoryItem+"."+fInventoryItemKey+" from "+tPlayer+","+tInventory+","+tInventoryItem+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fPlayerID+" and "+tInventory+"."+fInventoryID+" = "+tInventoryItem+"."+fInventoryID+" and "+tInventoryItem+"."+fInventoryIsItemEquipped+" =?", new String[]{String.valueOf(pPlayerID),mEquipped});
    	int index = myCursor.getColumnIndex(fInventoryItemKey);
        int myAnswer[] = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); i++){
        	myCursor.moveToPosition(i);
        	myAnswer[i] = myCursor.getInt(index);
        }
    	 myCursor.close();
    	 return myAnswer;
    }
    
    public int[] getInventoryItemKeys(int pPlayerID){//All inventory Item Keys
    	Cursor myCursor = this.getReadableDatabase().rawQuery("select "+tInventoryItem+"."+fInventoryItemKey+" from "+tPlayer+","+tInventory+","+tInventoryItem+" on "+tPlayer+"."+fPlayerID+" =? and "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fPlayerID+" and "+tInventory+"."+fInventoryID+" = "+tInventoryItem+"."+fInventoryID, new String[]{String.valueOf(pPlayerID)});
    	int index = myCursor.getColumnIndex(fInventoryItemKey);
        int myAnswer[] = new int[myCursor.getCount()];
        for(int i = 0; i < myCursor.getCount(); i++){
        	myCursor.moveToPosition(i);
        	myAnswer[i] = myCursor.getInt(index);
        }
    	 myCursor.close();
    	 return myAnswer;
    }
    
    public void deleteInventory(int[] inventoryItemsKeys){
    	SQLiteDatabase myDB = this.getWritableDatabase();
    	for(int i = 0;i<inventoryItemsKeys.length;i++){
    		myDB.delete(tInventoryItem, fInventoryItemKey+" =?", new String[]{String.valueOf(inventoryItemsKeys[i])});
    	}
    }
    
    public void addInventoryItems(int pPlayerID,int[] pItemID,int[] pAmount, int[] pEqquiped){
      Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+tInventory+"."+fInventoryID+" from "+tInventory+","+tPlayer+" on "+tPlayer+"."+fPlayerID+" = "+tInventory+"."+fInventoryID+" and "+tPlayer+"."+fPlayerID+" =?", new String[]{String.valueOf(pPlayerID)});
      myCursor.moveToFirst();
      int index = myCursor.getColumnIndex(fInventoryID);
      int invID = myCursor.getInt(index);
      myCursor.close();
      ContentValues cv = new ContentValues();
      for(int i = 0;i<pItemID.length;i++){
    	cv.put(fInventoryID, invID);
   	  	cv.put(fItemID,pItemID[i]);
   	  	cv.put(fInventoryItemAmount,pAmount[i]);
   	  	cv.put(fInventoryIsItemEquipped, pEqquiped[i]);
   	  	this.getWritableDatabase().insert(tInventoryItem, fInventoryItemKey, cv);
   	  	cv.clear();
      }
      this.close();
    }
    
        

}