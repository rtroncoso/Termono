package com.quest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {
    static final String dbName = "UserDB";
    static final String tUser = "User";
    static final String tJoinedMatches = "JoinedMatches";
    static final String tOwnMatches = "OwnMatches";
    static final String tMatchOptions = "MatchOptions";
    static final String tMatch = "Match";
    static final String tPlayers = "Players";
    static final String tPlayer = "Player";
    static final String tInventory = "Inventory";
    static final String tAttributes = "Attributes";
    static final String tSpellBook = "SpellBook";
    
    
    public UserDatabase(Context context) {
//THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
//IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
//DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
            super(context, dbName, null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS *");
            onCreate(db);
    }
    
         

}