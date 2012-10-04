package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
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
    	static final String fPlayerProfileID = "ProfileID";
    	static final String fPlayerName = "Name";
    	static final String fPlayerPosition = "Position";
    	static final String fPlayerClass = "Class";
    	//inventoryID
    	//attributesID
    	//modifiersID
    	//spellbookID
    	
    static final String tInventory = "Inventory";	
	    static final String fInventoryID = "InventoryID";
	    //PlayerID
	    
    static final String tInventoryItem = "InventoryItem";
    	static final String fInventoryItemID = "InventoryItemID";
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
	    
	static final String tModifiers = "Modifiers";
		static final String fModifiersID = "ModifiersID";
		static final String fModifiersHitPoints = "HitPoints";
		static final String fModifiersManaPoints = "ManaPoints";
	    static final String fModifiersEndurance = "Endurance";
	    static final String fModifiersIntelligence = "Intelligence";
	    static final String fModifiersPower = "Power";
	    static final String fModifiersDefense = "Defense";
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
            super(context, dbName, null, 2);
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
    			fPlayerProfileID+"INTEGER , "+
    			fPlayerName +" TEXT , "+
    			fPlayerPosition +" INTEGER , "+
    			fPlayerClass +" INTEGER , "+
    			fInventoryID +" INTEGER , "+
    			fAttributesID +" INTEGER , "+
    			fModifiersID +" INTEGER , "+
    			fSpellBookID +" INTEGER)" 
                );  
    	  
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
    			fInventoryID+" INTEGER PRIMARY KEY , "+
    			fPlayerID+" INTEGER)"
                );     	
    	
	    db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventoryItem+" ("+
	    		fInventoryItemID+" INTEGER PRIMARY KEY , "+
        		fInventoryID+"INTEGER , "+
        		fInventoryItemAmount+" INTEGER, "+
        		fInventoryIsItemEquipped+" INTEGER , "+//boolean
        		fItemID+" INTEGER)" //conectado a la estatica
                );   
	     
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttributes+" ("+
    			fAttributesID+" INTEGER PRIMARY KEY , "+
    			fAttributesEndurance+"INTEGER , "+
    			fAttributesIntelligence+" INTEGER ,"+
    			fAttributesPower+" INTEGER ,"+
    			fAttributesDefense+" INTEGER ,"+
	     		fPlayerID+" INTEGER)"
	             );	     

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tModifiers+" ("+
    			fModifiersID+" INTEGER PRIMARY KEY , "+
    			fModifiersHitPoints+"INTEGER , "+
    			fModifiersManaPoints+" INTEGER ,"+
    			fModifiersEndurance+" INTEGER ,"+
    			fModifiersIntelligence+" INTEGER ,"+
    			fModifiersPower+" INTEGER ,"+
    			fModifiersDefense+" INTEGER ,"+
	     		fPlayerID+" INTEGER)"
	             );	     
	    
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBook+" ("+
				fSpellBookID+" INTEGER PRIMARY KEY , "+
				fPlayerID+"INTEGER)"
                 );			    

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBookSpell+" ("+
    			fSpellBookSpellID+" INTEGER PRIMARY KEY , "+
    			fSpellBookID +" INTEGER , "+
    			fSpellBookSpellLevel +" INTEGER , "+
    			fSpellID +"INTEGER)" //conectado a la estatica
                );  	    	
				 
		 
		 ContentValues cv = new ContentValues();
		 cv.put(fUserID,Game.getUserID());
		// cv.put(fUserID,"00:00:00:00:00:00");
		 cv.put(fUsername, "Player");
		 db.insert(tProfile, null, cv);	                    
         cv.clear();
         
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //  db.execSQL("DROP TABLE IF EXISTS *");
            onCreate(db);
    }
    
         
    
    
    //QUERIES
    //Profile
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
    public void addNewMatchProfile(int pProfileID,int pMatchId){
    	  ContentValues cv = new ContentValues();
          cv.put(fProfileID,pProfileID);
          cv.put(fMatchID,pMatchId);
          this.getWritableDatabase().insert(tMatchProfile, null, cv);
          cv.clear();
          this.close();         
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
    
    public int getMatchesAmount(){
    	Cursor myCursor = this.getReadableDatabase().rawQuery("Select * from "+tMatch+","+tMatchProfile+" on "+tMatchProfile+"."+fMatchID+" = "+tMatch+"."+fMatchID+" and "+tMatchProfile+"."+fProfileID+" = 1", null);
    	int x= myCursor.getCount();
        myCursor.close();
        this.close();
        return x;
    }
}