package com.quest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    static final String dbName = "UserDB";
    static final String tProfile = "Profile";
    	static final String fProfileKey = "Key";//key
    	static final String fUserID = "UserID";//la mac
    	static final String fJoinedMatchesT = "JoinedMatchesT";
    	static final String fCreatedMatchesT = "CreatedMatchesT";
    static final String tJoinedMatches = "JoinedMatches";
    	static final String fJoinedKey = "Key";
    	static final String fJoinedName = "Name";
    	static final String fJoinedID = "UserID";
    static final String tCreatedMatches = "CreatedMatches";
    	static final String fCreatedKey = "Key";
    	static final String fCreatedName = "Name";
    	static final String fCreatedOptionsT = "OptionsT";//conectado con fOptionsT
    static final String tMatchOptions = "MatchOptions";
    	static final String fOptionsT = "OptionsT";//conectado con fCreatedOptionsT o con CreatedKey?
    	static final String fOptionsPassword = "Password";
    	static final String fOptionsMap = "Map";
    static final String tMatch = "Match";
    	static final String fMatchKey = "Key";//conectado con createdKey
    	static final String fMatchPlayersT = "PlayersT";
    	static final String fMatchCurrentQuest = "CurrentQuest";
    static final String tPlayers = "Players";
    	static final String fPlayersKey = "Key";//Conectado con playersT
    	static final String fPlayersPlayerID = "PlayerID";//es el del profile
    static final String tPlayer = "Player";
    	static final String fPlayerID = "PlayerID";//es el del profile
    	static final String fPlayerName = "Name";//El del profile
    	static final String fPlayerPosition = "Position";
    	static final String fPlayerClass = "Class";
    	static final String fPlayerInventoryID = "InventoryID";
    	static final String fPlayerAttributesID = "AttributesID";
    	static final String fPlayerModifiersID = "ModifiersID";    	
    	static final String fPlayerSpellbookID = "SpellBookID"; //conectado con fSpellBookPlayerID
    static final String tInventory = "Inventory";	
	    static final String fInventoryPlayerID = "PlayerID";//conectado con fPlayerID
	    static final String fInventoryItemID = "ItemID"; //conectada con la estatica
	    static final String fInventoryItemAmount = "Amount";
	    static final String fInventoryIsItemEquipped = "IsEquipped";
    static final String tAttributes = "Attributes";
    	static final String fAttributesPlayerID = "PlayerID"; //conectado con fPlayerAttributesID
	    static final String fAttributesEndurance = "Endurance";
	    static final String fAttributesIntelligence = "Intelligence";
	    static final String fAttributesPower = "Power";
	    static final String fAttributesDefense = "Defense";
	static final String tModifiers = "Modifiers";
		static final String fModifiersPlayerID = "PlayerID"; //conectado con fPlayerModifiersID
		static final String fModifiersHitPoints = "HitPoints";
		static final String fModifiersManaPoints = "ManaPoints";
	    static final String fModifiersEndurance = "Endurance";
	    static final String fModifiersIntelligence = "Intelligence";
	    static final String fModifiersPower = "Power";
	    static final String fModifiersDefense = "Defense";
    static final String tSpellBook = "SpellBook";
	    static final String fSpellBookPlayerID = "PlayerID";//conectado con fPlayerSpellbookID
	    static final String fSpellBookSpellID = "SpellID"; //conectado con la estatica
	    static final String fSpellBookSpellLevel = "Level";
    
    
    public UserDatabase(Context context) {
//THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
//IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
//DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
            super(context, dbName, null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tProfile+" ("+
                fProfileKey+" INTEGER PRIMARY KEY , "+
                fUserID +" TEXT , "+
                fJoinedMatchesT +" INTEGER , "+
                fCreatedMatchesT +" INTEGER)" 
                );
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tJoinedMatches+" ("+
    			fJoinedKey+" INTEGER PRIMARY KEY , "+
    			fJoinedName +" TEXT , "+
    			fJoinedID +" TEXT)" 
                );  	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tCreatedMatches+" ("+
    			fCreatedKey+" INTEGER PRIMARY KEY , "+
    			fCreatedName +" TEXT , "+
    			fCreatedOptionsT +" INTEGER)" 
                );  	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatchOptions+" ("+
    			fOptionsT+" INTEGER PRIMARY KEY , "+
    			fOptionsPassword +" TEXT , "+
    			fOptionsMap +" INTEGER)" 
                );  

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatch+" ("+
    			fMatchKey+" INTEGER PRIMARY KEY , "+
    			fMatchPlayersT +" INTEGER , "+
    			fMatchCurrentQuest +" TEXT)" 
                );  

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayers+" ("+
    			fPlayersKey+" INTEGER PRIMARY KEY , "+
    			fPlayersPlayerID +" INTEGER)" 
                );  
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
          		fPlayerID+" INTEGER PRIMARY KEY , "+
          		fPlayerName+" TEXT ,"+
          		fPlayerPosition+" STRING, "+
          		fPlayerClass+" INTEGER, "+
          		fPlayerInventoryID+" INTEGER, "+
          		fPlayerAttributesID+" INTEGER, "+
          		fPlayerModifiersID+" INTEGER, "+
          		fPlayerSpellbookID+" INTEGER, "+
          		fPlayerPosition+" INTEGER)"
                );
          
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
        		fInventoryItemID+" INTEGER PRIMARY KEY , "+
        		fInventoryPlayerID+" INTEGER, "+
                fInventoryItemAmount+" INTEGER , "+
                fInventoryIsItemEquipped+" INTEGER)"
                );     	
	     
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttributes+" ("+
	     		fPlayerID+" INTEGER PRIMARY KEY , "+
	     		fAttributesDefense+" INTEGER ,"+
	     		fAttributesEndurance+" INTEGER ,"+
	     		fAttributesPower+" INTEGER ,"+
	     		fAttributesIntelligence+" INTEGER)"
	             );	     

		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tModifiers+" ("+
		    	fModifiersPlayerID+" INTEGER PRIMARY KEY , "+
		   		fModifiersHitPoints +" INTEGER ,"+
		   		fModifiersManaPoints +" INTEGER ,"+
		   		fModifiersEndurance+" INTEGER ,"+
		   		fModifiersDefense+" INTEGER ,"+
		   		fModifiersPower+" INTEGER ,"+
		   		fModifiersIntelligence+" INTEGER)"
		        );
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBook+" ("+
				fSpellBookPlayerID+" INTEGER PRIMARY KEY , "+
				fSpellBookSpellID+" INTEGER , "+
				fSpellBookSpellLevel+" INTEGER)"
                 );
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS *");
            onCreate(db);
    }
    
         

}