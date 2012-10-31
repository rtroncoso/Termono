package com.quest.database;

import com.quest.constants.GameFlags;
import com.quest.game.Game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class StaticDatabase extends SQLiteOpenHelper implements GameFlags{
        static final String dbName = "Static";
        
        //Class
        static final String tClass = "Class"; 
        static final String fClassID = "ClassID";
        static final String fClassIconTexture = "IconTexture";
        static final String fClassAnimationTexture = "AnimationTexture";
        static final String fClassAnimationRows = "AnimationRows";
        static final String fClassAnimationCols = "AnimationCols";
        static final String fClassFrameWidth = "FrameWidth";
        static final String fClassFrameHeight = "FrameHeight";
        
        //Head
        static final String tHead = "Head"; 
        static final String fHeadID = "HeadID";
        static final String fHeadIconTexture = "IconTexture";
        static final String fHeadAnimationTexture = "AnimationTexture";
        static final String fHeadAnimationRows = "AnimationRows";
        static final String fHeadAnimationCols = "AnimationCols";
        static final String fHeadFrameWidth = "FrameWidth";
        static final String fHeadFrameHeight = "FrameHeight";
        //ClassID - diferencia de que class es
        
        //Item
        static final String tItem = "Item"; 
        static final String fItemID = "ItemID";
        static final String fItemName = "Name";
        static final String fItemIconTexture = "IconTexture";
        static final String fItemAnimationTexture = "AnimationTexture";
        static final String fItemType = "Type";
        static final String fItemStackable = "Stackable";
        static final String fItemDescription = "Description";
        static final String fItemBuyPrice = "BuyPrice";
        static final String fItemSellPrice = "SellPrice";
        static final String fItemClass = "ItemClassID";
        //fItemModifierID
        //droprate etc
        
        //Item Modifiers
        static final String tItemModifiers = "ItemModifiers";
        static final String fItemModifierID = "ModifierID";
	    static final String fItemModifierEndurance = "Endurance";
	    static final String fItemModifierIntelligence = "Intelligence";
	    static final String fItemModifierPower = "Power";
	    static final String fItemModifierDefense = "Defense";
        
	    
        //Attacks 
	    static final String tAttacks = "Attacks";
        static final String fAttackID = "AttackID";
        static final String fAttackName = "Name";
        static final String fAttackManaCost = "ManaCost";
        static final String fAttackEffect = "AttackEffect";
        static final String fAttackIconTexture = "IconTexture";
        static final String fAttackAnimationTexture = "AnimationTexture";
        static final String fAttackAnimationRows = "AnimationRows";
        static final String fAttackAnimationCols = "AnimationCols";
        static final String fAttackFrameWidth = "FrameWidth";
        static final String fAttackFrameHeight = "FrameHeight";
        static final String fAttackType = "Type";
        static final String fAttackDescription = "Description";
        static final String fAttackLevels = "Levels";
        static final String fAttackClass = "Class";
        
        
        //Mobs   ***attack texture y eso
        static final String tMob = "Mobs";
        static final String fMobID = "MobID";
        static final String fMobIconTexture = "IconTexture";
        static final String fMobAnimationTexture = "AnimationTexture";
        static final String fMobAnimationRows = "AnimationRows";
        static final String fMobAnimationCols = "AnimationCols";
        static final String fMobFrameWidth = "FrameWidth";
        static final String fMobFrameHeight = "FrameHeight";
        static final String fMobType = "Type";//Aggressive - etc, no se
        static final String fMobDescription = "Description";
        //attributes
        //droptable
        //spawns
        
        //Mob Attributes
        static final String tMobAttributes = "MobAttributes";
        static final String fMobAttributesID = "MobAttributesID";
        //mobID
	    static final String fMobPower = "Power";
	    static final String fMobIntelligence = "Intelligence";
	    static final String fMobDefense = "Defense";
	    static final String fMobEndurance = "Endurance";
	    static final String fMobLevel = "Level";
	    
	    //Mob Droptable
	    static final String tMobDroptable = "MobDroptable";
	    static final String fMobDroptableID = "MobDroptableID";
	    static final String fMobDropExperience = "Experience";
	    static final String fMobDropMoney = "Money";
	    static final String fMobDropItemIDs = "ItemIDs";
	    static final String fMobDropRates = "DropRates";
	    static final String fMobDropAmounts = "DropAmounts";
	    //mobID
	    
	    //Mob Spawn
	    static final String tMobSpawns = "MobSpawns";
	    //mobID
	    //NOSE
        
        public StaticDatabase(Context context) {
// THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
// IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
// DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
                super(context, dbName, null, 1);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
// ESTABLISH NEW DATABASE TABLES IF THEY DON'T ALREADY EXIST IN THE DATABASE
	            db.execSQL("CREATE TABLE IF NOT EXISTS "+tClass+" ("+
	            		fClassID+" INTEGER PRIMARY KEY , "+
	            		fClassIconTexture+" TEXT ,"+
	            		fClassAnimationTexture+" TEXT ,"+
	            		fClassAnimationRows+" INTEGER ,"+
	            		fClassAnimationCols+" INTEGER ,"+
	            		fClassFrameWidth+" INTEGER ,"+
	            		fClassFrameHeight+" INTEGER)"
	                    );
	            
	            db.execSQL("CREATE TABLE IF NOT EXISTS "+tHead+" ("+
	            		fHeadID+" INTEGER PRIMARY KEY , "+
	            		fHeadIconTexture+" TEXT ,"+
	            		fHeadAnimationTexture+" TEXT ,"+
	            		fHeadAnimationRows+" INTEGER ,"+
	            		fHeadAnimationCols+" INTEGER ,"+
	            		fHeadFrameWidth+" INTEGER ,"+
	            		fHeadFrameHeight+" INTEGER ,"+
	            		fClassID+" INTEGER)"
	                    );
	            
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tItem+" ("+
                        fItemID+" INTEGER PRIMARY KEY , "+
                        fItemName+" TEXT , "+
                        fItemIconTexture+" TEXT , "+
                        fItemAnimationTexture+" TEXT , "+
                        fItemType+" INTEGER , "+
                        fItemStackable+" INTEGER , "+
                        fItemDescription+" TEXT , "+
                        fItemBuyPrice+" INTEGER , "+
                        fItemSellPrice+" INTEGER , "+
                        fItemClass+" INTEGER)"
                        );

                db.execSQL("CREATE TABLE IF NOT EXISTS "+tItemModifiers+" ("+
                		fItemModifierID+" INTEGER PRIMARY KEY , "+
                		fItemModifierPower+" INTEGER, "+
                        fItemModifierIntelligence+" INTEGER , "+
                        fItemModifierDefense+" INTEGER , "+
                        fItemModifierEndurance+" INTEGER , "+
                        fItemID+" INTEGER)"
                        );
                
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tAttacks+" ("+
                        fAttackID+" INTEGER PRIMARY KEY , "+
                        fAttackName+" TEXT , "+
                        fAttackManaCost+" INTEGER ,"+
                        fAttackEffect+" TEXT ,"+
                        fAttackIconTexture+" TEXT , "+
                        fAttackAnimationTexture+" TEXT , "+
                        fAttackAnimationRows+" INTEGER ,"+
                        fAttackAnimationCols+" INTEGER ,"+
                        fAttackFrameWidth+" INTEGER ,"+
                        fAttackFrameHeight+" INTEGER ,"+
                        fAttackType+" INTEGER , "+
                        fAttackDescription+" TEXT , "+
                        fAttackLevels+" INTEGER, "+
                        fAttackClass+" INTEGER)"
                        );

                
                
	            db.execSQL("CREATE TABLE IF NOT EXISTS "+tMob+" ("+
	            		fMobID+" INTEGER PRIMARY KEY , "+
	            		fMobIconTexture+" TEXT ,"+
	            		fMobAnimationTexture+" TEXT ,"+
	            		fMobAnimationRows+" INTEGER ,"+
	            		fMobAnimationCols+" INTEGER ,"+
	            		fMobFrameWidth+" INTEGER ,"+
	            		fMobFrameHeight+" INTEGER ,"+
	            		fMobType+" INTEGER ,"+
	            		fMobDescription+" TEXT)"
	                    );
	            
	            
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tMobAttributes+" ("+
                		fMobAttributesID+" INTEGER PRIMARY KEY , "+
                		fMobID+" INTEGER, "+
                		fMobPower+" INTEGER , "+
                		fMobIntelligence+" INTEGER , "+
                		fMobDefense+" INTEGER , "+
                		fMobEndurance+" INTEGER , "+
                		fMobLevel+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tMobDroptable+" ("+
                		fMobDroptableID+" INTEGER PRIMARY KEY , "+
                		fMobDropExperience+" INTEGER, "+
                		fMobDropMoney+" INTEGER , "+
                		fMobDropItemIDs+" TEXT , "+
                		fMobDropRates+" TEXT , "+
                		fMobDropAmounts+" TEXT , "+
                        fMobID+" INTEGER)"
                        );
                
        	    
// OPTIONALLY PREPOPULATE THE TABLE WITH SOME VALUES   
                 ContentValues cv = new ContentValues();
                 		/*
                 		//@=====//::::::::::::::> - Swords 
                 		cv.put(fItemID, 0);//-------Cypress Stick-----------
                 		cv.put(fItemName, "Cypress Stick");cv.put(fItemTexture, "Paladin/Swords/CypressStick.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A reliable wooden stick");cv.put(fItemBuyPrice, 15);cv.put(fItemSellPrice, 8);cv.put(fItemClass,1);cv.put(fModifierID,0);db.insert(tItem, null, cv);
		                cv.put(fItemID, 1);//-------Bronze Sword-----------
				        cv.put(fItemName, "Bronze Sword");cv.put(fItemTexture, "Paladin/Swords/BronzeSword.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A plain bronze sword");cv.put(fItemBuyPrice, 60);cv.put(fItemSellPrice, 32);cv.put(fItemClass,1);cv.put(fModifierID,1);db.insert(tItem, null, cv);
				        cv.put(fItemID, 2);//--------Iron Sword----------
				        cv.put(fItemName, "Iron Sword");cv.put(fItemTexture, "Paladin/Swords/IronSword.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A good iron sword");cv.put(fItemBuyPrice, 180);cv.put(fItemSellPrice, 105);cv.put(fItemClass,1);cv.put(fModifierID,2);db.insert(tItem, null, cv);
						cv.put(fItemID, 3);//-------Steel Sword-----------
				        cv.put(fItemName, "Steel Sword");cv.put(fItemTexture, "Paladin/Swords/SteelSword.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A strong steel sword");cv.put(fItemBuyPrice, 430);cv.put(fItemSellPrice, 255);cv.put(fItemClass,1);cv.put(fModifierID,3);db.insert(tItem, null, cv);
						cv.put(fItemID, 4);//-------Knights Sword1-----------
				        cv.put(fItemName, "Knights Sword");cv.put(fItemTexture, "Paladin/Swords/KnightsSword1.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A sword given to the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 800);cv.put(fItemClass,1);cv.put(fModifierID,4);db.insert(tItem, null, cv);
						cv.put(fItemID, 5);//-------Knights Sword2-----------
				        cv.put(fItemName, "Knights Sword");cv.put(fItemTexture, "Paladin/Swords/KnightsSword2.png");cv.put(fItemType, 5);cv.put(fItemDescription, "A sword given to the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 800);cv.put(fItemClass,1);cv.put(fModifierID,5);db.insert(tItem, null, cv);
						cv.put(fItemID, 6);//-------Mythril Sword-----------
				        cv.put(fItemName, "Mythril Sword");cv.put(fItemTexture, "Paladin/Swords/MythrilSword.png");cv.put(fItemType, 5);cv.put(fItemDescription, "The sword of a champion");cv.put(fItemBuyPrice, 4600);cv.put(fItemSellPrice, 2700);cv.put(fItemClass,1);cv.put(fModifierID,6);db.insert(tItem, null, cv);
						
						//@=====//##############> - PlateBodies 
                 		cv.put(fItemID, 7);//-------Bronze Platebody-----------
		                cv.put(fItemName, "Bronze Platebody");cv.put(fItemTexture, "Paladin/PlateBodies/BronzePlate.png");cv.put(fItemType, 1);cv.put(fItemDescription, "A plain bronze platebody");cv.put(fItemBuyPrice, 110);cv.put(fItemSellPrice, 70);cv.put(fItemClass,1);cv.put(fModifierID,7);db.insert(tItem, null, cv);
		                cv.put(fItemID, 8);//-------Iron Platebody-----------
		                cv.put(fItemName, "Iron Platebody");cv.put(fItemTexture, "Paladin/PlateBodies/IronPlate.png");cv.put(fItemType, 1);cv.put(fItemDescription, "A good iron platebody");cv.put(fItemBuyPrice, 420);cv.put(fItemSellPrice, 360);cv.put(fItemClass,1);cv.put(fModifierID,8);db.insert(tItem, null, cv);
		                cv.put(fItemID, 9);//-------Steel Platebody-----------
		                cv.put(fItemName, "Steel Platebody");cv.put(fItemTexture, "Paladin/PlateBodies/SteelPlate.png");cv.put(fItemType, 1);cv.put(fItemDescription, "A solid steel platebody");cv.put(fItemBuyPrice, 860);cv.put(fItemSellPrice, 620);cv.put(fItemClass,1);cv.put(fModifierID,9);db.insert(tItem, null, cv);
		                cv.put(fItemID, 10);//-------Knights Platebody1-----------
		                cv.put(fItemName, "Knights Platebody");cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate1.png");cv.put(fItemType, 1);cv.put(fItemDescription, "An armour worn by the city knights");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 1420);cv.put(fItemClass,1);cv.put(fModifierID,10);db.insert(tItem, null, cv);
		                cv.put(fItemID, 11);//-------Knights Platebody2-----------
		                cv.put(fItemName, "Knights Platebody");cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate2.png");cv.put(fItemType, 1);cv.put(fItemDescription, "An armor worn by the city knights");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 1420);cv.put(fItemClass,1);cv.put(fModifierID,11);db.insert(tItem, null, cv);
		                cv.put(fItemID, 12);//-------Mythril Chain-----------
		                cv.put(fItemName, "Mythril Chainmail");cv.put(fItemTexture, "Paladin/PlateBodies/MythrilChain.png");cv.put(fItemType, 1);cv.put(fItemDescription, "The armor of a champion");cv.put(fItemBuyPrice, 7200);cv.put(fItemSellPrice, 3500);cv.put(fItemClass,1);cv.put(fModifierID,12);db.insert(tItem, null, cv);
						
		              //@=====//##############> - Shields 
                 		cv.put(fItemID, 13);//-------Wooden Shield-----------
		                cv.put(fItemName, "Wooden Shield");cv.put(fItemTexture, "Paladin/Shields/WoodenShield.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A wooden shield");cv.put(fItemBuyPrice, 14);cv.put(fItemSellPrice, 7);cv.put(fItemClass,1);cv.put(fModifierID,13);db.insert(tItem, null, cv);
                 		cv.put(fItemID, 14);//-------Bronze Shield-----------
		                cv.put(fItemName, "Bronze Shield");cv.put(fItemTexture, "Paladin/Shields/BronzeShield.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A simple bronze shield");cv.put(fItemBuyPrice, 55);cv.put(fItemSellPrice, 27);cv.put(fItemClass,1);cv.put(fModifierID,14);db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 15);//-------Iron Shield-----------
		                cv.put(fItemName, "Iron Shield");cv.put(fItemTexture, "Paladin/Shields/IronShield.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A good iron shield");cv.put(fItemBuyPrice, 160);cv.put(fItemSellPrice, 95);cv.put(fItemClass,1);cv.put(fModifierID,15);db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 16);//-------Steel Shield-----------
		                cv.put(fItemName, "Steel Shield");cv.put(fItemTexture, "Paladin/Shields/SteelShield.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A solid steel shield");cv.put(fItemBuyPrice, 390);cv.put(fItemSellPrice, 260);cv.put(fItemClass,1);cv.put(fModifierID,16);db.insert(tItem, null, cv);
                 		cv.put(fItemID, 17);//-------Knights Shield1-----------
		                cv.put(fItemName, "Knights Shield");cv.put(fItemTexture, "Paladin/Shields/KnightsShield1.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A shield worn by the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 700);cv.put(fItemClass,1);cv.put(fModifierID,17);db.insert(tItem, null, cv);
                 		cv.put(fItemID, 18);//-------Knights Shield2-----------
		                cv.put(fItemName, "Knights Shield");cv.put(fItemTexture, "Paladin/Shields/KnightsShield2.png");cv.put(fItemType, 4);cv.put(fItemDescription, "A shield worn by the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 700);cv.put(fItemClass,1);cv.put(fModifierID,18);db.insert(tItem, null, cv);
		                
		                //@=====//##############> - Helm 
                 		cv.put(fItemID, 19);//-------Bronze Helm-----------
		                cv.put(fItemName, "Bronze Helm");cv.put(fItemTexture, "Paladin/Helms/BronzeHelm.png");cv.put(fItemType, 0);cv.put(fItemDescription, "A common bronze helm");cv.put(fItemBuyPrice, 40);cv.put(fItemSellPrice, 30);cv.put(fItemClass,1);cv.put(fModifierID,19);db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 20);//-------Iron Helm-----------
		                cv.put(fItemName, "Iron Helm");cv.put(fItemTexture, "Paladin/Helms/IronHelm.png");cv.put(fItemType, 0);cv.put(fItemDescription, "A good iron helm");cv.put(fItemBuyPrice, 120);cv.put(fItemSellPrice, 80);cv.put(fItemClass,1);cv.put(fModifierID,20);db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 21);//-------Steel Helm-----------
		                cv.put(fItemName, "Steel Helm");cv.put(fItemTexture, "Paladin/Helms/SteelHelm.png");cv.put(fItemType, 0);cv.put(fItemDescription, "A fine steel helm");cv.put(fItemBuyPrice, 300);cv.put(fItemSellPrice, 200);cv.put(fItemClass,1);cv.put(fModifierID,21);db.insert(tItem, null, cv);
                 		cv.put(fItemID, 22);//-------Knights Helm1-----------
		                cv.put(fItemName, "Knights Helm");cv.put(fItemTexture, "Paladin/Helms/KnightsHelm1.png");cv.put(fItemType, 0);cv.put(fItemDescription, "A helm worn by the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 500);cv.put(fItemClass,1);cv.put(fModifierID,22);db.insert(tItem, null, cv); 
		                cv.put(fItemID, 23);//-------Knights Helm2-----------
		                cv.put(fItemName, "Knights Helm");cv.put(fItemTexture, "Paladin/Helms/KnightsHelm2.png");cv.put(fItemType, 0);cv.put(fItemDescription, "A helm worn by the knights of the city");cv.put(fItemBuyPrice, 0);cv.put(fItemSellPrice, 500);cv.put(fItemClass,1);cv.put(fModifierID,23);db.insert(tItem, null, cv); 
		                
		                cv.clear();
		                
		                //MODIFIERS
		                //----------Cypress stick-------
		                cv.put(fModifierID,0);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,2);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //--------bronze sword------
		                cv.put(fModifierID,1);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,7);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //--------iron sword--------
		                cv.put(fModifierID,2);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,15);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //----------steel sword-----
		                cv.put(fModifierID,3);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,25);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //---------knights sword1--
		                cv.put(fModifierID,4);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,35);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //--------knights sword2----
		                cv.put(fModifierID,5);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,35);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);
		                //--------mythril sword-----
		                cv.put(fModifierID,6);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,5);cv.put(fMPower,45);cv.put(fMDefense,0);db.insert(tModifiers, null, cv);		                

		                //@=====//##############> - PlateBodies 
		                //--------bronze palte------
		                cv.put(fModifierID,7);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,5);db.insert(tModifiers, null, cv);
		                //--------iron plate--------
		                cv.put(fModifierID,8);cv.put(fMHitPoint,0);cv.put(fMEndurance,0);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,10);db.insert(tModifiers, null, cv);
			            //--------steel plate-------
		                cv.put(fModifierID,9);cv.put(fMHitPoint,0);cv.put(fMEndurance,5);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,15);db.insert(tModifiers, null, cv);
		                //-------knights plate1---
		                cv.put(fModifierID,10);cv.put(fMHitPoint,0);cv.put(fMEndurance,10);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,23);db.insert(tModifiers, null, cv);
		        	      //--------knights plate2----
		                cv.put(fModifierID,11);cv.put(fMHitPoint,0);cv.put(fMEndurance,10);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,23);db.insert(tModifiers, null, cv);
		                //--------mythril chain-----
		                cv.put(fModifierID,12);cv.put(fMHitPoint,0);cv.put(fMEndurance,15);cv.put(fMManaPoints,0);cv.put(fMIntelligence,0);cv.put(fMPower,0);cv.put(fMDefense,35);db.insert(tModifiers, null, cv);      
		                cv.clear();

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
		                		                
                 cv.put(fItemID, 1);//-------Cypress Stick-----------
          		 cv.put(fItemName, "Cypress Stick");
          		 cv.put(fItemIconTexture, "Paladin/Swords/CypressStick.png");
          		 cv.put(fItemAnimationTexture, "Paladin/Swords/CypressStick.png");
          		 cv.put(fItemType, 5);
          		 cv.put(fItemStackable, 0);
          		 cv.put(fItemDescription, "A reliable wooden stick");
          		 cv.put(fItemBuyPrice, 15);
          		 cv.put(fItemSellPrice, 8);
          		 cv.put(fItemClass,1);
          		 db.insert(tItem, null, cv);
          		 
          		 cv.clear();
          		 
                 cv.put(fItemModifierID, 1);//-------Cypress Stick-----------
          		 cv.put(fItemModifierEndurance, 1);
          		 cv.put(fItemModifierIntelligence, 1);
          		 cv.put(fItemModifierPower, 4);
          		 cv.put(fItemModifierDefense, 1);
          		 cv.put(fItemID, 1);
          		 db.insert(tItemModifiers, null, cv);
          		 
          		 cv.clear();                 
          		 
          		cv.put(fClassID, 1);
          		cv.put(fClassIconTexture,"Players/Icons/Paladin.png");
          		cv.put(fClassAnimationTexture,"Players/Animations/Paladin.png");
          		cv.put(fClassAnimationCols,5);
          		cv.put(fClassAnimationRows,4);
          		cv.put(fClassFrameHeight,256);
          		cv.put(fClassFrameWidth,256);
          		db.insert(tClass, null, cv);
          		
          		cv.put(fClassID, 2);
          		cv.put(fClassIconTexture,"Players/Icons/Mage.png");
          		if(Game.isAVD_DEBUGGING()){
              		cv.put(fClassAnimationTexture,"Mobs/Animations/Fairy.png");
              		cv.put(fClassAnimationCols,4);//Low res img for avd
              		cv.put(fClassAnimationRows,4);
              		cv.put(fClassFrameHeight,128);
              		cv.put(fClassFrameWidth,128);
          		}else{
              		cv.put(fClassAnimationTexture,"Players/Animations/Mage.png");
              		cv.put(fClassAnimationCols,5);
              		cv.put(fClassAnimationRows,4);
              		cv.put(fClassFrameHeight,256);
              		cv.put(fClassFrameWidth,256);	
          		}
          		db.insert(tClass, null, cv);
          		
          		cv.put(fClassID, 3);
          		cv.put(fClassIconTexture,"Players/Icons/Orc.png");
          		cv.put(fClassAnimationTexture,"Players/Animations/Orc.png");
          		cv.put(fClassAnimationCols,5);
          		cv.put(fClassAnimationRows,4);
          		cv.put(fClassFrameHeight,256);
          		cv.put(fClassFrameWidth,256);
          		db.insert(tClass, null, cv);
          		
          		cv.put(fClassID, 4);
          		cv.put(fClassIconTexture,"Players/Icons/Archer.png");
          		cv.put(fClassAnimationTexture,"Players/Animations/Archer.png");
          		cv.put(fClassAnimationCols,5);
          		cv.put(fClassAnimationRows,4);
          		cv.put(fClassFrameHeight,256);
          		cv.put(fClassFrameWidth,256);
          		db.insert(tClass, null, cv); 
          		
          		cv.clear();
          		
          		cv.put(fHeadID, 1);
          		cv.put(fHeadIconTexture,"Players/Heads/Icons/Paladin.png");
          		cv.put(fHeadAnimationTexture,"Players/Heads/Icons/Paladin.png");
          		cv.put(fHeadAnimationCols,5);
          		cv.put(fHeadAnimationRows,4);
          		cv.put(fHeadFrameHeight,256);
          		cv.put(fHeadFrameWidth,256);
          		cv.put(fClassID,1);
          		db.insert(tHead, null, cv); 
          		
          		cv.clear();
          		
                
          		cv.put(fMobID, FLAG_MOB_BAT);
          		cv.put(fMobIconTexture,"Mobs/Icons/Bat1.png");
          		cv.put(fMobAnimationTexture,"Mobs/Animations/Bat1.png");
          		cv.put(fMobAnimationCols,4);
          		cv.put(fMobAnimationRows,4);
          		cv.put(fMobFrameWidth,256);
          		cv.put(fMobFrameHeight,256);
          		cv.put(fMobType,1);
          		cv.put(fMobDescription,"A common bat.");
          		db.insert(tMob, null, cv); 
          		
          		cv.clear();
          		cv.put(fMobID, FLAG_MOB_BAT);
          		cv.put(fMobPower,1);
          		cv.put(fMobIntelligence,0);
          		cv.put(fMobDefense,2);
          		cv.put(fMobEndurance,10);
          		cv.put(fMobLevel,1);
          		db.insert(tMobAttributes, null, cv); 
          		
          		cv.clear();
          		cv.put(fMobDropExperience,5);
          		cv.put(fMobDropMoney,1);
          		cv.put(fMobDropItemIDs,"1,2,3,4,5");
          		cv.put(fMobDropRates,"10,8,6,4,2");
          		cv.put(fMobDropAmounts,"1,1,1,1,1");
          		cv.put(fMobID, FLAG_MOB_BAT);
          		db.insert(tMobDroptable, null, cv); 
          		
          		cv.clear();
	            
          		
          		//Bee
         		cv.put(fMobID, FLAG_MOB_BEE);
          		cv.put(fMobIconTexture,"Mobs/Icons/Bee1.png");
          		cv.put(fMobAnimationTexture,"Mobs/Animations/Bee1.png");
          		cv.put(fMobAnimationCols,4);
          		cv.put(fMobAnimationRows,4);
          		cv.put(fMobFrameWidth,256);
          		cv.put(fMobFrameHeight,256);
          		cv.put(fMobType,1);
          		cv.put(fMobDescription,"A common bee.");
          		db.insert(tMob, null, cv); 
          		
          		cv.clear();
          		cv.put(fMobID, FLAG_MOB_BEE);
          		cv.put(fMobPower,4);
          		cv.put(fMobIntelligence,0);
          		cv.put(fMobDefense,6);
          		cv.put(fMobEndurance,20);
          		cv.put(fMobLevel,5);
          		db.insert(tMobAttributes, null, cv); 
          		
          		cv.clear();
          		cv.put(fMobDropExperience,20);
          		cv.put(fMobDropMoney,6);
          		cv.put(fMobDropItemIDs,"1,2,3,4,5");
          		cv.put(fMobDropRates,"12,10,8,6,4");
          		cv.put(fMobDropAmounts,"1,1,1,1,1");
          		cv.put(fMobID, FLAG_MOB_BEE);
          		db.insert(tMobDroptable, null, cv); 
          		
          		cv.clear();
          		
                cv.put(fAttackID,FLAG_ATTACK_SPELL_FIREBALL);
                cv.put(fAttackIconTexture, "Attacks/Spells/Icons/Spell1.png");
                cv.put(fAttackAnimationTexture,"Attacks/Spells/Animations/Spell1.png");
                cv.put(fAttackEffect, "1;2");
                cv.put(fAttackManaCost, 5);
                cv.put(fAttackType, 1);
                cv.put(fAttackAnimationRows, 1);
                cv.put(fAttackAnimationCols, 5);
                cv.put(fAttackFrameWidth, 960);
                cv.put(fAttackFrameHeight, 192);
                db.insert(tAttacks, null, cv); 
                cv.clear();
                
                cv.put(fAttackID,FLAG_ATTACK_SPELL_BLAST);
                cv.put(fAttackIconTexture, "Attacks/Spells/Icons/Spell2.png");
                cv.put(fAttackAnimationTexture,"Attacks/Spells/Animations/Spell2.png");
                cv.put(fAttackType, 1);
                cv.put(fAttackEffect, "1.5;2");
                cv.put(fAttackManaCost, 10);
                cv.put(fAttackAnimationRows, 1);
                cv.put(fAttackAnimationCols, 5);
                cv.put(fAttackFrameWidth, 960);
                cv.put(fAttackFrameHeight, 192);
                db.insert(tAttacks, null, cv); 
                cv.clear();
                
                cv.put(fAttackID,FLAG_ATTACK_SPELL_THUNDER);
                cv.put(fAttackIconTexture, "Attacks/Spells/Icons/Spell3.png");
                cv.put(fAttackAnimationTexture,"Attacks/Spells/Animations/Spell3.png");
                cv.put(fAttackType, 1);
                cv.put(fAttackEffect, "2;2");
                cv.put(fAttackManaCost, 15);
                cv.put(fAttackAnimationRows, 3);
                cv.put(fAttackAnimationCols, 5);
                cv.put(fAttackFrameWidth, 960);
                cv.put(fAttackFrameHeight, 576);
                db.insert(tAttacks, null, cv); 
                cv.clear();
                
                cv.put(fAttackID,FLAG_ATTACK_SPELL_ICE_RING);
                cv.put(fAttackIconTexture, "Attacks/Spells/Icons/Spell4.png");
                cv.put(fAttackAnimationTexture,"Attacks/Spells/Animations/Spell4.png");
                cv.put(fAttackType, 2);
                cv.put(fAttackEffect, "1.2;3");
                cv.put(fAttackManaCost, 25);
                cv.put(fAttackAnimationRows, 2);
                cv.put(fAttackAnimationCols, 5);
                cv.put(fAttackFrameWidth, 960);
                cv.put(fAttackFrameHeight, 384);
                db.insert(tAttacks, null, cv); 
                cv.clear();
                
                cv.put(fAttackID,FLAG_ATTACK_MOB_DEATH);
                cv.put(fAttackIconTexture, "null");
                cv.put(fAttackAnimationTexture,"Attacks/Mob/Other/MobDeath.png");
                cv.put(fAttackType, 0);
                cv.put(fAttackEffect, "0;0");
                cv.put(fAttackManaCost, 0);
                cv.put(fAttackAnimationRows, 1);
                cv.put(fAttackAnimationCols, 5);
                cv.put(fAttackFrameWidth, 960);
                cv.put(fAttackFrameHeight, 192);
                db.insert(tAttacks, null, cv); 
                cv.clear();
                
        }
        
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// THIS METHOD DELETES THE EXISTING TABLE AND THEN CALLS THE METHOD onCreate() AGAIN TO RECREATE A NEW TABLE
// THIS SERVES TO ESSENTIALLY RESET THE DATABASE
// INSTEAD YOU COULD MODIFY THE EXISTING TABLES BY ADDING/REMOVING COLUMNS/ROWS/VALUES THEN NO EXISTING DATA WOULD BE LOST
                db.execSQL("DROP TABLE IF EXISTS *");
                onCreate(db);
        }
        
         //Class table
        public String getClassIconTexture(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassIconTexture +" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassIconTexture);
        	String myAnswer = myCursor.getString(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        public String getClassAnimationTexture(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationTexture +" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationTexture);
        	String myAnswer = myCursor.getString(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        public int getClassAnimationRows(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationRows+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationRows);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        public int getClassAnimationCols(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationCols+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationCols);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        public int getClassFrameWidth(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassFrameWidth+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassFrameWidth);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        public int getClassFrameHeight(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassFrameHeight+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassFrameHeight);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	myDB.close();
        	return myAnswer;
        }
        
        
         //Item table
         public String getItemName(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemName +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemName);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             myDB.close();
             return myAnswer;
         }         
         
         public String getItemIconTexture(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemIconTexture +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemIconTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
         		myDB.close();
             return myAnswer;
         }         
         
         public String getItemAnimationTexture(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemAnimationTexture +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemAnimationTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
         		myDB.close();
             return myAnswer;
         }
         
         public int getItemType(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemType +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemType);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
         	myDB.close();
             return myAnswer;
         }
         
         public int getItemBuyPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemBuyPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",new String[]{String.valueOf(pID)});
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemBuyPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
         	myDB.close();
        	 return myAnswer;
         }
         
         public int getItemSellPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemSellPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",new String[]{String.valueOf(pID)});
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemSellPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
         	myDB.close();
        	 return myAnswer;
         }
        
         public String getItemDescription(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemDescription +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemDescription);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
         	myDB.close();
             return myAnswer;
         }      
         
         public int getItemClass(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemClass +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
         	myDB.close();
             return myAnswer;
         }
         
         public int[] getItemModifiers(int pItemID){
        	 int[] myAnswer = new int[4];
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT * FROM "+ tItemModifiers +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pItemID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemModifierPower);
          	 myAnswer[0]=myCursor.getInt(index);
          	 index = myCursor.getColumnIndex(fItemModifierIntelligence);
          	 myAnswer[1]=myCursor.getInt(index);
          	 index = myCursor.getColumnIndex(fItemModifierDefense);
          	 myAnswer[2]=myCursor.getInt(index);
          	 index = myCursor.getColumnIndex(fItemModifierEndurance);
          	 myAnswer[3]=myCursor.getInt(index);
          	 myCursor.close();
         	myDB.close();
             return myAnswer;
         }
 
         public boolean isItemStackable(int pItemID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemStackable +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pItemID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemStackable);
             boolean stackable = false;
             if(myCursor.getInt(index)==1)stackable=true;
             myCursor.close();
         	myDB.close();
             return stackable;
         }
         
         
         //Mob table
         public String getMobIconTexture(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobIconTexture +" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobIconTexture);
         	String myAnswer = myCursor.getString(index);
         	myCursor.close();
        	myDB.close();
         	return myAnswer;
         }
         
         public String getMobAnimationTexture(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobAnimationTexture +" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobAnimationTexture);
         	String myAnswer = myCursor.getString(index);
         	myCursor.close();
         	return myAnswer;
         }
         
         public int getMobAnimationRows(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobAnimationRows+" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobAnimationRows);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
        	myDB.close();
         	return myAnswer;
         }
         
         public int getMobAnimationCols(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobAnimationCols+" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobAnimationCols);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
        	myDB.close();
         	return myAnswer;
         }
         
         public int getMobFrameWidth(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobFrameWidth+" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobFrameWidth);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
        	myDB.close();
         	return myAnswer;
         }
         
         public int getMobFrameHeight(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fMobFrameHeight+" FROM "+ tMob+" WHERE "+ fMobID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fMobFrameHeight);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
        	myDB.close();
         	return myAnswer;
         }
         
         
         //Mob Attributes!
         public int[] getMobAttributes(int pID){
        	int[] myAnswer = new int[5];
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select * from "+tMobAttributes+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobPower);
	 		myAnswer[0]=myCursor.getInt(index);
			index = myCursor.getColumnIndex(fMobIntelligence);
			myAnswer[1]=myCursor.getInt(index);
			index = myCursor.getColumnIndex(fMobDefense);
			myAnswer[2]=myCursor.getInt(index);
			index = myCursor.getColumnIndex(fMobEndurance);
			myAnswer[3]=myCursor.getInt(index);
			index = myCursor.getColumnIndex(fMobLevel);
			myAnswer[4]=myCursor.getInt(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         //Mob Droptable
         public int getMobExperience(int pID){
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMobDropExperience+" from "+tMobDroptable+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobDropExperience);
			int myAnswer = myCursor.getInt(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         public int getMobMoney(int pID){
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMobDropMoney+" from "+tMobDroptable+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobDropMoney);
			int myAnswer = myCursor.getInt(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         public String getMobDroppedItems(int pID){
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMobDropItemIDs+" from "+tMobDroptable+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobDropItemIDs);
			String myAnswer = myCursor.getString(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         public String getMobDropRates(int pID){
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMobDropRates+" from "+tMobDroptable+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobDropRates);
			String myAnswer = myCursor.getString(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         public String getMobDropAmounts(int pID){
        	Cursor myCursor = this.getReadableDatabase().rawQuery("Select "+fMobDropAmounts+" from "+tMobDroptable+" where "+fMobID+" =?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fMobDropAmounts);
			String myAnswer = myCursor.getString(index);
			myCursor.close();
			this.close();
			return myAnswer;
          }
         
         
         
         //Attacks
         public String getAttackIconTexture(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackIconTexture +" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackIconTexture);
         	String myAnswer = myCursor.getString(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public String getAttackAnimationTexture(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackAnimationTexture +" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackAnimationTexture);
         	String myAnswer = myCursor.getString(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackAnimationRows(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackAnimationRows+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackAnimationRows);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackAnimationCols(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackAnimationCols+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackAnimationCols);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackFrameWidth(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackFrameWidth+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackFrameWidth);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackFrameHeight(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackFrameHeight+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackFrameHeight);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackType(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackType+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackType);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public String getAttackEffect(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackEffect+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackEffect);
         	String myAnswer = myCursor.getString(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
         
         public int getAttackCost(int pID){
    	 	SQLiteDatabase myDB = this.getReadableDatabase();
    	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fAttackManaCost+" FROM "+ tAttacks+" WHERE "+ fAttackID +"=?",new String[]{String.valueOf(pID)});
         	myCursor.moveToFirst();
         	int index = myCursor.getColumnIndex(fAttackManaCost);
         	int myAnswer = myCursor.getInt(index);
         	myCursor.close();
         	myDB.close();
         	return myAnswer;
         }
}