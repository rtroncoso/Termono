package com.quest.database;

import com.quest.game.Game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    static final String dbName = "UserDB";
    static final String tProfile = "Profile";
    	static final String fProfileKey = "Key";//key
    	static final String fUserID = "UserID";//la mac
    	static final String fUsername = "Username";
    	static final String fJoinedMatchesT = "JoinedMatchesT";
    	static final String fCreatedMatchesT = "CreatedMatchesT";
    static final String tJoinedMatches = "JoinedMatches";
    	static final String fJoinedKey = "Key";
    	static final String fJoinedName = "Name";
    	static final String fJoinedID = "UserID";
    static final String tCreatedMatches = "CreatedMatches";
    	static final String fCreatedID = "MatchID"; //vendria a ser el matchID
    	static final String fCreatedName = "Name";
    	static final String fCreatedOptionsT = "OptionsT";//conectado con fOptionsT
    static final String tMatchOptions = "MatchOptions";
    	static final String fOptionsT = "OptionsT";//conectado con fCreatedOptionsT o con CreatedKey?
    	static final String fOptionsPassword = "Password";
    	static final String fOptionsMap = "Map";
    static final String tMatch = "Match";
    	static final String fMatchID = "ID";//conectado con createdKey
    	static final String fMatchPlayersT = "PlayersT";
    	static final String fMatchCurrentQuest = "CurrentQuest";
    static final String tPlayers = "Players";
    	static final String fPlayersKey = "Key";//Conectado con playersT
    	static final String fPlayersPlayerID = "PlayerID";//es el del profile
    	static final String fPlayersPlayerNumber = "PlayerNumber";//Cuando consigo el ID le doy esto asi es mas facil
    	static final String fPlayersMatchID = "MatchID";//A que match pertenecen
    static final String tPlayer = "Player";
    	static final String fPlayerNumber = "PlayerNumber";
    	static final String fPlayerID = "PlayerID";//es el del profile
    	static final String fPlayerName = "Name";//El del profile
    	static final String fPlayerPosition = "Position";
    	static final String fPlayerClass = "Class";
    	static final String fPlayerInventoryID = "InventoryID";
    	static final String fPlayerAttributesID = "AttributesID";
    	static final String fPlayerModifiersID = "ModifiersID";    	
    	static final String fPlayerSpellbookID = "SpellBookID"; //conectado con fSpellBookPlayerID
    	static final String fPlayerMatchID = "MatchID";
    static final String tInventory = "Inventory";	
	    static final String fInventoryPlayerNumber = "PlayerNumber";//conectado con fPlayerNumber
	    static final String fInventoryItemID = "ItemID"; //conectada con la estatica
	    static final String fInventoryItemAmount = "Amount";
	    static final String fInventoryIsItemEquipped = "IsEquipped";
    	static final String fInventoryMatchID = "MatchID";
    static final String tAttributes = "Attributes";
    	static final String fAttributesPlayerNumber = "PlayerNumber"; //conectado con fPlayerAttributesNumber
	    static final String fAttributesEndurance = "Endurance";
	    static final String fAttributesIntelligence = "Intelligence";
	    static final String fAttributesPower = "Power";
	    static final String fAttributesDefense = "Defense";
	    static final String fAttributesMatchID = "MatchID";
	static final String tModifiers = "Modifiers";
		static final String fModifiersPlayerNumber = "PlayerNumber"; //conectado con fPlayerModifiersNumber
		static final String fModifiersHitPoints = "HitPoints";
		static final String fModifiersManaPoints = "ManaPoints";
	    static final String fModifiersEndurance = "Endurance";
	    static final String fModifiersIntelligence = "Intelligence";
	    static final String fModifiersPower = "Power";
	    static final String fModifiersDefense = "Defense";
	    static final String fModifiersMatchID = "MatchID";
    static final String tSpellBook = "SpellBook";
	    static final String fSpellBookPlayerNumber = "PlayerNumber";//conectado con fPlayerSpellbookNumber
	    static final String fSpellBookSpellID = "SpellID"; //conectado con la estatica
	    static final String fSpellBookSpellLevel = "Level";
	    static final String fSpellBookMatchID = "MatchID";
    
    
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
                fUsername +" TEXT , "+
                fJoinedMatchesT +" INTEGER , "+
                fCreatedMatchesT +" INTEGER)" 
                );
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tJoinedMatches+" ("+
    			fJoinedKey+" INTEGER PRIMARY KEY , "+
    			fJoinedName +" TEXT , "+
    			fJoinedID +" TEXT)" 
                );  	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tCreatedMatches+" ("+
    			fCreatedID+" INTEGER PRIMARY KEY , "+
    			fCreatedName +" TEXT , "+
    			fCreatedOptionsT +" INTEGER)" 
                );  	
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatchOptions+" ("+
    			fOptionsT+" INTEGER PRIMARY KEY , "+
    			fOptionsPassword +" TEXT , "+
    			fOptionsMap +" INTEGER)" 
                );  

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tMatch+" ("+
    			fMatchID+" INTEGER PRIMARY KEY , "+
    			fMatchPlayersT +" INTEGER , "+
    			fMatchCurrentQuest +" TEXT)" 
                );  

    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayers+" ("+
    			fPlayersKey+" INTEGER PRIMARY KEY , "+
    			fPlayersMatchID+"INTEGER , "+
    			fPlayersPlayerID +" INTEGER , "+
    			fPlayersPlayerNumber +" INTEGER)" 
                );  
    	
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
          		fPlayerNumber+" INTEGER PRIMARY KEY , "+
          		fPlayerMatchID+"INTEGER , "+
          		fPlayerID+" TEXT ,"+
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
        		fInventoryMatchID+"INTEGER , "+
        		fInventoryPlayerNumber+" INTEGER, "+
                fInventoryItemAmount+" INTEGER , "+
                fInventoryIsItemEquipped+" INTEGER)"
                );     	
	     
    	db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttributes+" ("+
	     		fPlayerID+" INTEGER PRIMARY KEY , "+
	     		fAttributesMatchID+"INTEGER , "+
	     		fAttributesDefense+" INTEGER ,"+
	     		fAttributesEndurance+" INTEGER ,"+
	     		fAttributesPower+" INTEGER ,"+
	     		fAttributesIntelligence+" INTEGER)"
	             );	     

		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tModifiers+" ("+
		    	fModifiersPlayerNumber+" INTEGER PRIMARY KEY , "+
		    	fModifiersMatchID+"INTEGER , "+
		   		fModifiersHitPoints +" INTEGER ,"+
		   		fModifiersManaPoints +" INTEGER ,"+
		   		fModifiersEndurance+" INTEGER ,"+
		   		fModifiersDefense+" INTEGER ,"+
		   		fModifiersPower+" INTEGER ,"+
		   		fModifiersIntelligence+" INTEGER)"
		        );
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBook+" ("+
				fSpellBookPlayerNumber+" INTEGER PRIMARY KEY , "+
				fSpellBookMatchID+"INTEGER , "+
				fSpellBookSpellID+" INTEGER , "+
				fSpellBookSpellLevel+" INTEGER)"
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
    
    //Profile
    
    //JoinedMatches
    
    //CreatedMatches
    
    //Match
    
    //MatchOptions
    
    //Players
    
    //Player
    
    //Inventory
	  /* public int getInventoryCount(){
	           SQLiteDatabase db=this.getWritableDatabase();
	           Cursor cur= db.rawQuery("SELECT * FROM "+tInventory, null);
	           int x= cur.getCount();
	           cur.close();
	           return x;
	    }
	    
	    public int getItemAmount(int pID){
	   	 SQLiteDatabase myDB = this.getReadableDatabase();
	        String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
	        Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemAmount +" FROM "+ tInventory +" WHERE "+ fInventoryItemID +"=?",mySearch);
	        myCursor.moveToFirst();
	        int index = myCursor.getColumnIndex(fInventoryItemAmount);
	        int myAnswer = myCursor.getInt(index);
	        myCursor.close();
	        return myAnswer;
	    }
	             
	    public int[] getEquippedIDs(int pEstado){
	   	 SQLiteDatabase myDB = this.getReadableDatabase();
	   	 String[] mySearch = new String[]{String.valueOf(pEstado)};
	   	 Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemID +" FROM "+ tInventory +" WHERE "+ fInventoryIsItemEquipped +"=?",mySearch);
	   	 int index = myCursor.getColumnIndex(fInventoryItemID);
	   	 int myAnswer[] = new int[myCursor.getCount()];
	   	 for(int i = 0; i < myCursor.getCount(); i++){
	   		myCursor.moveToPosition(i);
	   		myAnswer[i] = myCursor.getInt(index);
	   	 }
	        myCursor.close();
	        return myAnswer;
	    }
	
	    public void EquipItem(int ID, int pEquipped){
	            SQLiteDatabase myDB = this.getWritableDatabase();
	            ContentValues cv = new ContentValues();
	            cv.put(fInventoryIsItemEquipped,String.valueOf(pEquipped));   
	            myDB.update(tInventory, cv, fInventoryItemID+"=?", new String []{String.valueOf(ID)});
	    }
	    
	    public int isItemEquipped(int pID){
	   	 SQLiteDatabase myDB = this.getReadableDatabase();
	        String[] mySearch = new String[]{String.valueOf(pID)};
	        Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryIsItemEquipped+" FROM "+ tInventory +" WHERE "+ fInventoryItemID +"=?",mySearch);
	        myCursor.moveToFirst();
	        int index = myCursor.getColumnIndex(fInventoryIsItemEquipped);
	        int myAnswer = myCursor.getInt(index);
	        myCursor.close();
	        return myAnswer;
	    }
    */
    //Attributes
    
    //Modifiers
    
    //Spellbook
    
    
    
  //Inventory table
    public int getInventoryCount(){
           SQLiteDatabase db=this.getWritableDatabase();
           Cursor cur= db.rawQuery("SELECT * FROM "+tInventory, null);
           int x= cur.getCount();
           cur.close();
           return x;
    }
    
    public int getItemAmount(int pID){
   	 SQLiteDatabase myDB = this.getReadableDatabase();
        String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
        Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemAmount +" FROM "+ tInventory +" WHERE "+ fInventoryItemID +"=?",mySearch);
        myCursor.moveToFirst();
        int index = myCursor.getColumnIndex(fInventoryItemAmount);
        int myAnswer = myCursor.getInt(index);
        myCursor.close();
        return myAnswer;
    }
             
    public int[] getEquippedIDs(int pEstado){
   	 SQLiteDatabase myDB = this.getReadableDatabase();
   	 String[] mySearch = new String[]{String.valueOf(pEstado)};
   	 Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemID +" FROM "+ tInventory +" WHERE "+ fInventoryIsItemEquipped +"=?",mySearch);
   	 int index = myCursor.getColumnIndex(fInventoryItemID);
   	 int myAnswer[] = new int[myCursor.getCount()];
   	 for(int i = 0; i < myCursor.getCount(); i++){
   		myCursor.moveToPosition(i);
   		myAnswer[i] = myCursor.getInt(index);
   	 }
        myCursor.close();
        return myAnswer;
    }

    public void EquipItem(int ID, int pEquipped){
            SQLiteDatabase myDB = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(fInventoryIsItemEquipped,String.valueOf(pEquipped));   
            myDB.update(tInventory, cv, fInventoryItemID+"=?", new String []{String.valueOf(ID)});
    }
    
    public int isItemEquipped(int pID){
   	 SQLiteDatabase myDB = this.getReadableDatabase();
        String[] mySearch = new String[]{String.valueOf(pID)};
        Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryIsItemEquipped+" FROM "+ tInventory +" WHERE "+ fInventoryItemID +"=?",mySearch);
        myCursor.moveToFirst();
        int index = myCursor.getColumnIndex(fInventoryIsItemEquipped);
        int myAnswer = myCursor.getInt(index);
        myCursor.close();
        return myAnswer;
    }
    

}