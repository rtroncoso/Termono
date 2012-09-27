package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.quest.game.Game;

public class UserDatabase extends SQLiteOpenHelper {
    static final String dbName = "UserDB";
    static final String tProfile = "Profile";
    	static final String fProfileID = "ProfileID";//key
    	static final String fUserID = "UserID";//la mac
    	static final String fUsername = "Username";
	
	static final String tMatchProfile = "MatchProfile";
		static final String fMatchProfileID = "MatchProfileID";
		static final String fCreator = "Creator";
		//ProfileID
		//MatchID
		
    static final String tMatch = "Match";
    	static final String fMatchID = "MatchID";
    	static final String fMatchName = "Name";
    	static final String fMatchCurrentQuest = "CurrentQuest";
    	static final String fMatchPassword = "Password";
    	static final String fMatchMap = "Map";
    	static final String fMatchPlayers = "MatchPlayers";
    	
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
            super(context, dbName, null, 0);
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
    			fCreator +" INTEGER , "+//boolean
    			fMatchID +" INTEGER)" 
                );  	
    	

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatch+" ("+
    			fMatchID+" INTEGER PRIMARY KEY , "+
    			fMatchName +" TEXT , "+
    			fMatchCurrentQuest +" TEXT , "+
    			fMatchPassword +" TEXT , "+
    			fMatchMap +" INTEGER , "+
    			fMatchPlayers +"INTEGER)" 
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
         db.insert(tProfile, null, cv);	                    
         cv.clear();
         
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //  db.execSQL("DROP TABLE IF EXISTS *");
            onCreate(db);
    }
    
         
    
    
    //QUERIES

    

}