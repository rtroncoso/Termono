package com.quest.database;

import com.quest.game.Game;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class myDatabase extends SQLiteOpenHelper {
        static final String dbName = "myDB";
       //fItemAmount+" INTEGER , "+
        static final String tItem = "Item"; 
        static final String fItemID = "ItemID";
        static final String fItemName = "Name";
        static final String fItemTexture = "Texture";
        static final String fItemType = "Type";
        static final String fItemDescription = "Description";
        static final String fItemBuyPrice = "BuyPrice";
        static final String fItemSellPrice = "SellPrice";
        static final String fItemClass = "ItemClassID";
        
        static final String fModifierID = "Modifier";
        
        static final String tInventory = "Inventory";
        static final String fInventoryPlayerID = "PlayerID";
        static final String fInventoryItemID = "ItemID";
        static final String fInventoryItemAmount = "Amount";
        static final String fInventoryIsItemEquipped = "IsEquipped";
        
        
        static final String tPlayer = "Player";
        static final String fPlayerID = "PlayerID";
        static final String fPlayerName = "PlayerName";
        static final String fPlayerClass = "PlayerClass";
        
        
        static final String tModifiers = "Modifiers";
        static final String fMHitPoint = "HP";
        static final String fMEndurance = "Endurance";
        static final String fMManaPoints = "ManaPoints";
        static final String fMIntelligence = "Intelligence";
        static final String fMPower = "Power";
        static final String fMDefense = "Defense";
        
        //atributos
        static final String tAttributes = "Attributes";
        static final String fPEndurance = "Endurance";
        static final String fPIntelligence = "Intelligence";
        static final String fPPower = "Power";
        static final String fPDefense = "Defense";
        
        //spells -estaticos, hacer tabla spellbook
        static final String tSpells = "Spells";
        static final String fSpellID = "SpellID";
        static final String fSpellName = "Name";
        static final String fSpellTexture = "Texture";
        static final String fSpellType = "Type";
        static final String fSpellDescription = "Description";
    //    static final String fSpellLevels = "Levels";		por ahora todos tienen la misma cantidad
        static final String fSpellClass = "Class";
        static final String fSpellEffectID = "EffectID";
        
        //spellbook - los que tiene el player
        static final String tSpellBook = "SpellBook";
        static final String fSpellBookPlayerID = "PlayerID";
        static final String fSpellBookSpellID = "SpellID";
        static final String fSpellBookSpellLevel = "Level";
        
        
        //SpellEffect table
        static final String tSpellEffect = "SpellEffect";
        static final String fEffectSpellID = "SpellID";
        static final String fEffectSpellLevel0 = "0";
        static final String fEffectSpellLevel1 = "1";
        static final String fEffectSpellLevel2 = "2";
        static final String fEffectSpellLevel3 = "3";
        
        
        public myDatabase(Context context) {
// THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
// IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
// DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
                super(context, dbName, null, 18);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
// ESTABLISH NEW DATABASE TABLES IF THEY DON'T ALREADY EXIST IN THE DATABASE
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tItem+" ("+
                        fItemID+" INTEGER PRIMARY KEY , "+
                        fItemName+" TEXT , "+
                        fItemTexture+" TEXT , "+
                        fItemType+" INTEGER , "+
                        fItemDescription+" TEXT , "+
                        fItemBuyPrice+" INTEGER , "+
                        fItemSellPrice+" INTEGER , "+
                        fItemClass+" INTEGER, "+
                        fModifierID+" INTEGER)"
                        );
                //hacer que no sea auto increasing?
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
                		fInventoryPlayerID+" INTEGER PRIMARY KEY , "+
                		fInventoryItemID+" INTEGER, "+
                        fInventoryItemAmount+" INTEGER , "+
                        fInventoryIsItemEquipped+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
                		fPlayerID+" INTEGER PRIMARY KEY , "+
                		fPlayerName+" TEXT ,"+
                		fPlayerClass+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tModifiers+" ("+
                		fModifierID+" INTEGER PRIMARY KEY , "+
                		fMHitPoint+" INTEGER ,"+
                		fMEndurance+" INTEGER ,"+
                		fMManaPoints+" INTEGER ,"+
                		fMIntelligence+" INTEGER ,"+
                		fMPower+" INTEGER ,"+
                		fMDefense+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttributes+" ("+
                		fPlayerID+" INTEGER PRIMARY KEY , "+//es necesario?
                		fPEndurance+" INTEGER ,"+
                		fPIntelligence+" INTEGER ,"+
                		fPPower+" INTEGER ,"+
                		fPDefense+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpells+" ("+
                        fSpellID+" INTEGER PRIMARY KEY , "+
                        fSpellName+" TEXT , "+
                        fSpellTexture+" TEXT , "+
                        fSpellType+" INTEGER , "+//no se ni para que por ahora
                        fSpellDescription+" TEXT , "+
                        fSpellClass+" INTEGER, "+
                        fSpellEffectID+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellBook+" ("+
                		fSpellBookPlayerID+" INTEGER PRIMARY KEY , "+
                		fSpellBookSpellID+" INTEGER , "+//Hacer un "Unlocked" o pasarle solo los que tiene?
                		fSpellBookSpellLevel+" INTEGER)"
                        );

                db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellEffect+" ("+
                		fEffectSpellID+" INTEGER PRIMARY KEY , "+
                		fEffectSpellLevel0+" TEXT ,"+//Como defino los datos aca adentro? los encapsulo en un string por ahora
                		fEffectSpellLevel1+" TEXT ,"+
                		fEffectSpellLevel2+" TEXT ,"+
                		fEffectSpellLevel3+" TEXT)"
                        );                
                
                
                
       
// OPTIONALLY PREPOPULATE THE TABLE WITH SOME VALUES   
                 ContentValues cv = new ContentValues();
                 		//@=====//::::::::::::::> - Swords 
                 		cv.put(fItemID, 0);//-------Cypress Stick-----------
		                cv.put(fItemName, "Cypress Stick");
		                cv.put(fItemTexture, "Paladin/Swords/CypressStick.png");
		                cv.put(fItemType, 5);
		                cv.put(fItemDescription, "A reliable wooden stick");
		                cv.put(fItemBuyPrice, 15);
		                cv.put(fItemSellPrice, 8);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 1);//-------Bronze Sword-----------
				        cv.put(fItemName, "Bronze Sword");
				        cv.put(fItemTexture, "Paladin/Swords/BronzeSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A plain bronze sword");
		                cv.put(fItemBuyPrice, 60);
		                cv.put(fItemSellPrice, 32);
				        cv.put(fItemClass,1);
				        cv.put(fModifierID,1);
				        db.insert(tItem, null, cv);
				        cv.put(fItemID, 2);//--------Iron Sword----------
				        cv.put(fItemName, "Iron Sword");
				        cv.put(fItemTexture, "Paladin/Swords/IronSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A good iron sword");
		                cv.put(fItemBuyPrice, 180);
		                cv.put(fItemSellPrice, 105);
				        cv.put(fItemClass,1);
						cv.put(fModifierID,2);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 3);//-------Steel Sword-----------
				        cv.put(fItemName, "Steel Sword");
				        cv.put(fItemTexture, "Paladin/Swords/SteelSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A strong steel sword");
		                cv.put(fItemBuyPrice, 430);
		                cv.put(fItemSellPrice, 255);
				        cv.put(fItemClass,1);
						cv.put(fModifierID,3);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 4);//-------Knights Sword1-----------
				        cv.put(fItemName, "Knights Sword");
				        cv.put(fItemTexture, "Paladin/Swords/KnightsSword1.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A sword given to the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 800);
				        cv.put(fItemClass,1);
						cv.put(fModifierID,4);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 5);//-------Knights Sword2-----------
				        cv.put(fItemName, "Knights Sword");
				        cv.put(fItemTexture, "Paladin/Swords/KnightsSword2.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A sword given to the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 800);
				        cv.put(fItemClass,1);
						cv.put(fModifierID,5);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 6);//-------Mythril Sword-----------
				        cv.put(fItemName, "Mythril Sword");
				        cv.put(fItemTexture, "Paladin/Swords/MythrilSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "The sword of a champion");
		                cv.put(fItemBuyPrice, 4600);
		                cv.put(fItemSellPrice, 2700);
				        cv.put(fItemClass,1);
						cv.put(fModifierID,6);
						db.insert(tItem, null, cv);
						
						//@=====//##############> - PlateBodies 
                 		cv.put(fItemID, 7);//-------Bronze Platebody-----------
		                cv.put(fItemName, "Bronze Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/BronzePlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A plain bronze platebody");
		                cv.put(fItemBuyPrice, 110);
		                cv.put(fItemSellPrice, 70);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,7);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 8);//-------Iron Platebody-----------
		                cv.put(fItemName, "Iron Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/IronPlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A good iron platebody");
		                cv.put(fItemBuyPrice, 420);
		                cv.put(fItemSellPrice, 360);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,8);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 9);//-------Steel Platebody-----------
		                cv.put(fItemName, "Steel Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/SteelPlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A solid steel platebody");
		                cv.put(fItemBuyPrice, 860);
		                cv.put(fItemSellPrice, 620);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,9);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 10);//-------Knights Platebody1-----------
		                cv.put(fItemName, "Knights Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate1.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "An armour worn by the city knights");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 1420);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,10);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 11);//-------Knights Platebody2-----------
		                cv.put(fItemName, "Knights Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate2.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "An armor worn by the city knights");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 1420);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,11);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 12);//-------Mythril Chain-----------
		                cv.put(fItemName, "Mythril Chainmail");
		                cv.put(fItemTexture, "Paladin/PlateBodies/MythrilChain.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "The armor of a champion");
		                cv.put(fItemBuyPrice, 7200);
		                cv.put(fItemSellPrice, 3500);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,12);
		                db.insert(tItem, null, cv);
						
		              //@=====//##############> - Shields 
                 		cv.put(fItemID, 13);//-------Wooden Shield-----------
		                cv.put(fItemName, "Wooden Shield");
		                cv.put(fItemTexture, "Paladin/Shields/WoodenShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A wooden shield");
		                cv.put(fItemBuyPrice, 14);
		                cv.put(fItemSellPrice, 7);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,13);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 14);//-------Bronze Shield-----------
		                cv.put(fItemName, "Bronze Shield");
		                cv.put(fItemTexture, "Paladin/Shields/BronzeShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A simple bronze shield");
		                cv.put(fItemBuyPrice, 55);
		                cv.put(fItemSellPrice, 27);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,14);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 15);//-------Iron Shield-----------
		                cv.put(fItemName, "Iron Shield");
		                cv.put(fItemTexture, "Paladin/Shields/IronShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A good iron shield");
		                cv.put(fItemBuyPrice, 160);
		                cv.put(fItemSellPrice, 95);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,15);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 16);//-------Steel Shield-----------
		                cv.put(fItemName, "Steel Shield");
		                cv.put(fItemTexture, "Paladin/Shields/SteelShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A solid steel shield");
		                cv.put(fItemBuyPrice, 390);
		                cv.put(fItemSellPrice, 260);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,16);
		                db.insert(tItem, null, cv);
                 		cv.put(fItemID, 17);//-------Knights Shield1-----------
		                cv.put(fItemName, "Knights Shield");
		                cv.put(fItemTexture, "Paladin/Shields/KnightsShield1.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A shield worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 700);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,17);
		                db.insert(tItem, null, cv);
                 		cv.put(fItemID, 18);//-------Knights Shield2-----------
		                cv.put(fItemName, "Knights Shield");
		                cv.put(fItemTexture, "Paladin/Shields/KnightsShield2.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A shield worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 700);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,18);
		                db.insert(tItem, null, cv);
		                
		                //@=====//##############> - Helm 
                 		cv.put(fItemID, 19);//-------Bronze Helm-----------
		                cv.put(fItemName, "Bronze Helm");
		                cv.put(fItemTexture, "Paladin/Helms/BronzeHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A common bronze helm");
		                cv.put(fItemBuyPrice, 40);
		                cv.put(fItemSellPrice, 30);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,19);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 20);//-------Iron Helm-----------
		                cv.put(fItemName, "Iron Helm");
		                cv.put(fItemTexture, "Paladin/Helms/IronHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A good iron helm");
		                cv.put(fItemBuyPrice, 120);
		                cv.put(fItemSellPrice, 80);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,20);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 21);//-------Steel Helm-----------
		                cv.put(fItemName, "Steel Helm");
		                cv.put(fItemTexture, "Paladin/Helms/SteelHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A fine steel helm");
		                cv.put(fItemBuyPrice, 300);
		                cv.put(fItemSellPrice, 200);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,21);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 22);//-------Knights Helm1-----------
		                cv.put(fItemName, "Knights Helm");
		                cv.put(fItemTexture, "Paladin/Helms/KnightsHelm1.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A helm worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,22);
		                db.insert(tItem, null, cv); 
		                cv.put(fItemID, 23);//-------Knights Helm2-----------
		                cv.put(fItemName, "Knights Helm");
		                cv.put(fItemTexture, "Paladin/Helms/KnightsHelm2.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A helm worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClass,1);
		                cv.put(fModifierID,23);
		                db.insert(tItem, null, cv); 
		                
		                
		                
		                //MODIFIERS
		                //----------Cypress stick-------
		                cv.put(fModifierID,0);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,2);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		                //--------bronze sword------
		                cv.put(fModifierID,1);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,7);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		              //--------iron sword--------
		                cv.put(fModifierID,2);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,15);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		              //----------steel sword-----
		                cv.put(fModifierID,3);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,25);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		              //---------kngihts sword1--
		                cv.put(fModifierID,4);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,35);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		              //--------knights sword2----
		                cv.put(fModifierID,5);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,35);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		              //--------mythril sword-----
		                cv.put(fModifierID,6);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,5);
		                cv.put(fMPower,45);
		                cv.put(fMDefense,0);
		                db.insert(tModifiers, null, cv);
		                
		              //@=====//##############> - PlateBodies 
		              //--------bronze palte------
		                cv.put(fModifierID,7);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,5);
		                db.insert(tModifiers, null, cv);
		              //--------iron plate--------
		                cv.put(fModifierID,8);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,0);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,10);
		                db.insert(tModifiers, null, cv);
		              //--------steel plate-------
		                cv.put(fModifierID,9);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,5);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,15);
		                db.insert(tModifiers, null, cv);
		              //-------knights plate1---
		                cv.put(fModifierID,10);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,10);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,23);
		                db.insert(tModifiers, null, cv);
		              //--------knights plate2----
		                cv.put(fModifierID,11);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,10);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,23);
		                db.insert(tModifiers, null, cv);
		              //--------mythril chain-----
		                cv.put(fModifierID,12);
		                cv.put(fMHitPoint,0);
		                cv.put(fMEndurance,15);
		                cv.put(fMManaPoints,0);
		                cv.put(fMIntelligence,0);
		                cv.put(fMPower,0);
		                cv.put(fMDefense,35);
		                db.insert(tModifiers, null, cv);
		                /*
		              //@=====//##############> - Shields 
		              //--------wooden shield-----
		                cv.put(fModifierID,13);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		              //--------bronze shield-----
		                cv.put(fModifierID,14);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		              //------iron shield---------
		                cv.put(fModifierID,15);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		              //--------steel shield-------
		                cv.put(fModifierID,16);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		              //--------knights shield1---
		                cv.put(fModifierID,17);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		                //------knights shield2-----
		                cv.put(fModifierID,18);
		                cv.put(fMHitPoint, );
		                cv.put(fMEndurance, );
		                cv.put(fMManaPoints, );
		                cv.put(fMIntelligence, );
		                cv.put(fMPower, );
		                cv.put(fMDefense, );
		                db.insert(tModifiers, null, cv);
		                
		                */
		                		                
		                
		                
		                
		                ///TEST ITEMS
		                cv.put(fItemID, 40);
		                cv.put(fItemName, "TESTITEM");
		                cv.put(fItemTexture, "Orc/Weapons/Warhammer.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A test item");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClass,2);
		                cv.put(fModifierID,40);
		                db.insert(tItem, null, cv); 
		                cv.put(fItemID, 41);
		                cv.put(fItemName, "TESTITEM");
		                cv.put(fItemTexture, "Orc/Weapons/Warhammer.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A test item");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClass,0);
		                cv.put(fModifierID,41);
		                db.insert(tItem, null, cv); 
		                cv.put(fItemID, 42);
		                cv.put(fItemName, "TESTITEM");
		                cv.put(fItemTexture, "Orc/Weapons/Warhammer.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A test item");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClass,3);
		                cv.put(fModifierID,42);
		                db.insert(tItem, null, cv); 
		                
						cv.clear(); //Hace falta hacerle clear?
		                
						
		                //INVENTORY	
		                cv.put(fInventoryItemID,0);//tiene que ser igual a fItemID
		                cv.put(fInventoryItemAmount, 1);//tiene que ser igual a fItemName
		                cv.put(fInventoryIsItemEquipped, 0);
		                cv.put(fPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		                db.insert(tInventory, null, cv);
		                cv.put(fInventoryItemID,2);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		                cv.put(fInventoryItemID,5);//tiene que ser igual a fItemID
		 		        cv.put(fInventoryItemAmount, 2);//tiene que ser igual a fItemName
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		 		        db.insert(tInventory, null, cv);
		 			    cv.put(fInventoryItemID,7);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,11);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,14);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,18);
		 		        cv.put(fInventoryItemAmount, 2);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,20);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		    	cv.put(fInventoryItemID,23);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);	
		 		        //item de prueba
		 		        cv.put(fInventoryItemID,40);
		 		   		cv.put(fInventoryItemAmount, 1);
	 		        	cv.put(fInventoryIsItemEquipped, 0);
	 		        	cv.put(fPlayerID, 0);
	 		        	db.insert(tInventory, null, cv);	  
		                //mas items
		                cv.put(fInventoryItemID,9);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		    	cv.put(fInventoryItemID,16);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		       //items de prueba
		 		        cv.put(fInventoryItemID,41);
		 		   		cv.put(fInventoryItemAmount, 1);
	 		        	cv.put(fInventoryIsItemEquipped, 0);
	 		        	cv.put(fPlayerID, 0);
	 		        	db.insert(tInventory, null, cv);
		 		    	cv.put(fInventoryItemID,21);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);	
		 		        cv.put(fInventoryItemID,42);
		 		   		cv.put(fInventoryItemAmount, 1);
	 		        	cv.put(fInventoryIsItemEquipped, 0);
	 		        	cv.put(fPlayerID, 0);
	 		        	db.insert(tInventory, null, cv);
	 		        	cv.put(fInventoryItemID,10);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);	
		 		        cv.put(fInventoryItemID,19);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquipped, 0);
		 		        cv.put(fPlayerID, 0);
		 		        db.insert(tInventory, null, cv);	
		 		       
		 		       
		 		        
		 		    //  cv.clear();
		                		//clases 0 multi; 1 pala; 2 arquero; 3 mago; 4 orco;
		                cv.put(fPlayerID, 0);
		                cv.put(fPlayerName, "Joaquin");
		                cv.put(fPlayerClass, 1);
	                	db.insert(tPlayer, null, cv);
		                
	                	
	                	//atributos
	                    cv.put(fPlayerID, 0);
	                    cv.put(fPEndurance,1);
	                    cv.put(fPIntelligence,1);
	            		cv.put(fPPower,1);
	    				cv.put(fPDefense,1);
	                    db.insert(tAttributes, null, cv);
	                    
	                    
	                    
	                    //spells
	                    cv.put(fSpellID, 0);
	                    cv.put(fSpellName, "Energy blast");
	                    cv.put(fSpellTexture, "Spells/Icons/EnergyBlast.png");
	                    cv.put(fSpellType, 0);
	                    cv.put(fSpellDescription, "A basic missile of energy.");
	                    cv.put(fSpellClass, 0);
	                    cv.put(fSpellEffectID, 0);
	                    db.insert(tSpells, null, cv);
	                    cv.put(fSpellID, 1);
	                    cv.put(fSpellName, "Fire Ball");
	                    cv.put(fSpellTexture, "Spells/Icons/FireBall.png");
	                    cv.put(fSpellType, 1);
	                    cv.put(fSpellDescription, "A small ball of fire.");
	                    cv.put(fSpellClass, 0);
	                    cv.put(fSpellEffectID, 1);
	                    db.insert(tSpells, null, cv);
	                    
	                    //spell effect
	                    cv.put(fEffectSpellID, 0);
	                    cv.put(fEffectSpellLevel0, ";5;0;");
	                    cv.put(fEffectSpellLevel1, ";10;0;");
	                    cv.put(fEffectSpellLevel2, ";20;1;");//1 = dmg to mana
	                    cv.put(fEffectSpellLevel3, ";35;1;");
	                    db.insert(tSpellEffect, null, cv);
	                    cv.put(fEffectSpellID, 1);
	                    cv.put(fEffectSpellLevel0, ";15;0;"); //2 = burn
	                    cv.put(fEffectSpellLevel1, ";20;0;");
	                    cv.put(fEffectSpellLevel2, ";40;2;");
	                    cv.put(fEffectSpellLevel3, ";65;2;");
	                    db.insert(tSpellEffect, null, cv);
	                    
	                    //spellbook
	                    cv.put(fSpellBookPlayerID, 0); //hacer el inner join
	                    cv.put(fSpellBookSpellID, 0);
	                    cv.put(fSpellBookSpellLevel, 0);
	                    db.insert(tSpellBook, null, cv);
	                    cv.put(fSpellBookPlayerID, 0); //hacer el inner join
	                    cv.put(fSpellBookSpellID, 1);
	                    cv.put(fSpellBookSpellLevel, 2);
	                    db.insert(tSpellBook, null, cv);
        
	                    
		                
/*             
 * MORE ADVANCED EXAMPLES OF USAGE
 *
                db.execSQL("CREATE TRIGGER fk_empdept_deptid " +
                                " BEFORE INSERT "+
                                " ON "+employeeTable+                          
                                " FOR EACH ROW BEGIN"+
                                " SELECT CASE WHEN ((SELECT "+colDeptID+" FROM "+deptTable+" WHERE "+colDeptID+"=new."+colDept+" ) IS NULL)"+
                                " THEN RAISE (ABORT,'Foreign Key Violation') END;"+
                                "  END;");
               
                db.execSQL("CREATE VIEW "+viewEmps+
                                " AS SELECT "+employeeTable+"."+colID+" AS _id,"+
                                " "+employeeTable+"."+colName+","+
                                " "+employeeTable+"."+colAge+","+
                                " "+deptTable+"."+colDeptName+""+
                                " FROM "+employeeTable+" JOIN "+deptTable+
                                " ON "+employeeTable+"."+colDept+" ="+deptTable+"."+colDeptID
                                );
*/                             
        }
        
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// THIS METHOD DELETES THE EXISTING TABLE AND THEN CALLS THE METHOD onCreate() AGAIN TO RECREATE A NEW TABLE
// THIS SERVES TO ESSENTIALLY RESET THE DATABASE
// INSTEAD YOU COULD MODIFY THE EXISTING TABLES BY ADDING/REMOVING COLUMNS/ROWS/VALUES THEN NO EXISTING DATA WOULD BE LOST
                db.execSQL("DROP TABLE IF EXISTS "+tItem);
                db.execSQL("DROP TABLE IF EXISTS Levels");
                db.execSQL("DROP TABLE IF EXISTS "+tInventory);
                db.execSQL("DROP TABLE IF EXISTS "+tPlayer);
                db.execSQL("DROP TABLE IF EXISTS "+tAttributes);
                db.execSQL("DROP TABLE IF EXISTS "+tSpellBook);
                db.execSQL("DROP TABLE IF EXISTS "+tSpellEffect);
                db.execSQL("DROP TABLE IF EXISTS "+tSpells);
                db.execSQL("DROP TABLE IF EXISTS "+tModifiers);
                onCreate(db);
        }
        
        
       /*
         public String isLevelUnLocked(int ID) {
// THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO READ A VALUE FROM THE DATABASE                 
                 SQLiteDatabase myDB = this.getReadableDatabase();
                 String[] mySearch = new String[]{String.valueOf(ID)};
                 Cursor myCursor = myDB.rawQuery("SELECT "+ fLevelUnLocked +" FROM "+ tLevels +" WHERE "+ fLevelID +"=?",mySearch);
                 myCursor.moveToFirst();
                 int index = myCursor.getColumnIndex(fLevelUnLocked);
                 String myAnswer = myCursor.getString(index);
                 myCursor.close();
                 return myAnswer;
         }
         
         public int unLockLevel(int ID, String isUnLocked)
         {
// THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO WRITE A VALUE TO THE DATABASE          
                 SQLiteDatabase myDB = this.getWritableDatabase();
                 ContentValues cv = new ContentValues();
                 cv.put(fLevelUnLocked, isUnLocked);
                 int numRowsAffected = myDB.update(tLevels, cv, fLevelID+"=?", new String []{String.valueOf(ID)});
                 return numRowsAffected;
         }
               
         
         public int BeatLevel(int ID, String isBeat)
         {
// THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO WRITE A VALUE TO THE DATABASE          
                 SQLiteDatabase myDB = this.getWritableDatabase();
                 ContentValues cv = new ContentValues();
                 cv.put(fLevelBeat,isBeat);   
                 int numRowsAffected = myDB.update(tLevels, cv, fLevelID+"=?", new String []{String.valueOf(ID)});
                 return numRowsAffected;
         }
         
         public String isLevelBeat(int ID) {
        	// THIS METHOD IS CALLED BY YOUR MAIN ACTIVITY TO READ A VALUE FROM THE DATABASE                 
        	                 SQLiteDatabase myDB = this.getReadableDatabase();
        	                 String[] mySearch = new String[]{String.valueOf(ID)};
        	                 Cursor myCursor = myDB.rawQuery("SELECT "+ fLevelBeat +" FROM "+ tLevels +" WHERE "+ fLevelID +"=?",mySearch);
        	                 myCursor.moveToFirst();
        	                 int index = myCursor.getColumnIndex(fLevelBeat);
        	                 String myAnswer = myCursor.getString(index);
        	                 myCursor.close();
        	                 return myAnswer;
        	         }
         */
        
         //Item table
        /*
         public int getInventoryItemID(int pIndex){
       	  SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemID +" FROM "+ tInventory,null);
             myCursor.moveToPosition(pIndex);
             int index = myCursor.getColumnIndex(fInventoryItemID);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
        */
         public String getItemName(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//no se que pasarle para que quede bien, por ahora convierto a string
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemName +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemName);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public String getItemImagePath(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemTexture +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public int getItemType(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemType +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemType);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         
         public int getItemBuyPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 String[] mySearch = new String[]{String.valueOf(pID)};
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemBuyPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",mySearch);
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemBuyPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
        	 return myAnswer;
         }
         
         public int getItemSellPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 String[] mySearch = new String[]{String.valueOf(pID)};
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemSellPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",mySearch);
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemSellPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
        	 return myAnswer;
         }
        
         public String getItemDescription(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemDescription +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemDescription);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }      
         
         public int getItemClass(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemClass +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         
         
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
         
         
         //Player table
         public int getPlayerClass(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
           //  Cursor myCursor = myDB.rawQuery("SELECT "+ fPlayerClass +" FROM "+ tPlayer +" WHERE "+ fPlayerID +"=?",mySearch);
             Cursor myCursor = myDB.rawQuery("SELECT "+ fPlayerClass +" FROM "+ tPlayer,null);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fPlayerClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         /*
         public int getPlayerAttributes(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fModifierID +" FROM "+ tPlayer +" WHERE "+ fPlayerID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fPlayerClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         */
         
         
         //Spell table
         public String getSpellName(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//no se que pasarle para que quede bien, por ahora convierto a string
             Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellName +" FROM "+ tSpells +" WHERE "+ fSpellID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fSpellName);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public String getSpellImagePath(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellTexture +" FROM "+ tSpells +" WHERE "+ fSpellID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fSpellTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public int getSpellType(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellType +" FROM "+ tSpells +" WHERE "+ fSpellID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fSpellType);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         
        
         public String getSpellDescription(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellDescription +" FROM "+ tSpells +" WHERE "+ fSpellID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fSpellDescription);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }      
         
         public int getSpellClass(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellClass +" FROM "+ tSpells +" WHERE "+ fSpellID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fSpellClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         
         //Spell Effect
         public String getSpellEffect(int pID,int pLevel){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 String[] mySearch = new String[]{String.valueOf(pID)};
        	 Cursor myCursor = myDB.rawQuery("SELECT "+String.valueOf(pLevel)+" FROM "+tSpellEffect+" WHERE "+fEffectSpellID+"=?",mySearch);
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(String.valueOf(pLevel));
        	 String myAnswer = myCursor.getString(index);
        	 myCursor.close();
        	 return myAnswer;
         }
         
         //SpellBook
         public int getSpellBookCount(){
             SQLiteDatabase db=this.getWritableDatabase();
             Cursor cur= db.rawQuery("SELECT * FROM "+tSpellBook, null);
             int x= cur.getCount();
             cur.close();
             return x;
         }
      
	      public int getSpellLevel(int pID){
	     	 SQLiteDatabase myDB = this.getReadableDatabase();
	          String[] mySearch = new String[]{String.valueOf(pID)};//{String.valueOf(Name)};
	          Cursor myCursor = myDB.rawQuery("SELECT "+ fSpellBookSpellLevel +" FROM "+ tSpellBook +" WHERE "+ fSpellBookSpellID +"=?",mySearch);
	          myCursor.moveToFirst();
	          int index = myCursor.getColumnIndex(fSpellBookSpellLevel);
	          int myAnswer = myCursor.getInt(index);
	          myCursor.close();
	          return myAnswer;
	      }
	               
	      public void SpellLevelUp(int ID, int pLevel){//hacer los inner join para que de alguna manera solo agarre lo del playerID
	              SQLiteDatabase myDB = this.getWritableDatabase();
	              ContentValues cv = new ContentValues();
	              cv.put(fSpellBookSpellLevel,pLevel+1);   
	              myDB.update(tSpellBook, cv, fSpellBookSpellID+"=?", new String []{String.valueOf(ID)});
	      }
	               
                 
/*       
 * MORE ADVANCED EXAMPLES OF USAGE
 *
         void AddEmployee(String _name, int _age, int _dept) {
                SQLiteDatabase db= this.getWritableDatabase();
                ContentValues cv=new ContentValues();
                        cv.put(colName, _name);
                        cv.put(colAge, _age);
                        cv.put(colDept, _dept);
                        //cv.put(colDept,2);
                db.insert(employeeTable, colName, cv);
                db.close();
        }
       
         int getEmployeeCount()
         {
                SQLiteDatabase db=this.getWritableDatabase();
                Cursor cur= db.rawQuery("Select * from "+employeeTable, null);
                int x= cur.getCount();
                cur.close();
                return x;
         }
         
         Cursor getAllEmployees()
         {
                 SQLiteDatabase db=this.getWritableDatabase();
                 //Cursor cur= db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable, new String [] {});
                 Cursor cur= db.rawQuery("SELECT * FROM "+viewEmps,null);
                 return cur;
         }
         
         public int GetDeptID(String Dept)
         {
                 SQLiteDatabase db=this.getReadableDatabase();
                 Cursor c=db.query(deptTable, new String[]{colDeptID+" as _id",colDeptName},colDeptName+"=?", new String[]{Dept}, null, null, null);
                 //Cursor c=db.rawQuery("SELECT "+colDeptID+" as _id FROM "+deptTable+" WHERE "+colDeptName+"=?", new String []{Dept});
                 c.moveToFirst();
                 return c.getInt(c.getColumnIndex("_id"));
         }
         
         public String GetDept(int ID)
         {
                 SQLiteDatabase db=this.getReadableDatabase();
                 String[] params=new String[]{String.valueOf(ID)};
                 Cursor c=db.rawQuery("SELECT "+colDeptName+" FROM"+ deptTable+" WHERE "+colDeptID+"=?",params);
                 c.moveToFirst();
                 int index= c.getColumnIndex(colDeptName);
                 return c.getString(index);
         }
         
         public Cursor getEmpByDept(String Dept)
         {
                 SQLiteDatabase db=this.getReadableDatabase();
                 String [] columns=new String[]{"_id",colName,colAge,colDeptName};
                 Cursor c=db.query(viewEmps, columns, colDeptName+"=?", new String[]{Dept}, null, null, null);
                 return c;
         }
         
         public int UpdateEmp(String _name, int _age, int _dept, int _eid)
         {
                 SQLiteDatabase db=this.getWritableDatabase();
                 ContentValues cv=new ContentValues();
                 cv.put(colName, _name);
                 cv.put(colAge, _age);
                 cv.put(colDept, _dept);
                 return db.update(employeeTable, cv, colID+"=?", new String []{String.valueOf(_eid)});
         }
         
         public void DeleteEmp(String _name, int _age, int _dept, int _eid)
         {
                 SQLiteDatabase db=this.getWritableDatabase();
                 db.delete(employeeTable,colID+"=?", new String [] {String.valueOf(_eid)});
                 db.close();           
         }
*/       
 
}