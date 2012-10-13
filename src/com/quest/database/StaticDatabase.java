package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class StaticDatabase extends SQLiteOpenHelper {
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
        
	    
        //spells 
	    static final String tSpells = "Spells";
        static final String fSpellID = "SpellID";
        static final String fSpellName = "Name";
        static final String fSpellIconTexture = "IconTexture";
        static final String fSpellAnimationTexture = "AnimationTexture";
        static final String fSpellType = "Type";
        static final String fSpellDescription = "Description";
        static final String fSpellLevels = "Levels";
        static final String fSpellClass = "Class";
        static final String fSpellEffectID = "EffectID";
        
        //SpellEffect table
        static final String tSpellEffect = "SpellEffect";
        static final String fEffectSpellID = "SpellID";
        static final String fEffectSpellLevel1 = "Effect1";
        static final String fEffectSpellLevel2 = "Effect2";
        static final String fEffectSpellLevel3 = "Effect3";
        static final String fEffectSpellLevel4 = "Effect4";
        static final String fEffectSpellLevel5 = "Effect5";
        
        
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
                
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpells+" ("+
                        fSpellID+" INTEGER PRIMARY KEY , "+
                        fSpellName+" TEXT , "+
                        fSpellIconTexture+" TEXT , "+
                        fSpellAnimationTexture+" TEXT , "+
                        fSpellType+" INTEGER , "+
                        fSpellDescription+" TEXT , "+
                        fSpellLevels+" INTEGER, "+
                        fSpellClass+" INTEGER, "+
                        fSpellEffectID+" INTEGER)"
                        );
                

                db.execSQL("CREATE TABLE IF NOT EXISTS "+tSpellEffect+" ("+
                		fEffectSpellID+" INTEGER PRIMARY KEY , "+
                		fEffectSpellLevel1+" TEXT ,"+//Como definir los datos aca adentro? los encapsulo en un string por ahora
                		fEffectSpellLevel2+" TEXT ,"+
                		fEffectSpellLevel3+" TEXT ,"+
                		fEffectSpellLevel4+" TEXT ,"+
                		fEffectSpellLevel5+" TEXT)"
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
		                		                
                 cv.put(fItemID, 0);//-------Cypress Stick-----------
          		 cv.put(fItemName, "Cypress Stick");
          		 cv.put(fItemIconTexture, "Paladin/Swords/CypressStick.png");
          		 cv.put(fItemAnimationTexture, "Paladin/Swords/CypressStick.png");
          		 cv.put(fItemType, 5);
          		 cv.put(fItemDescription, "A reliable wooden stick");
          		 cv.put(fItemBuyPrice, 15);
          		 cv.put(fItemSellPrice, 8);
          		 cv.put(fItemClass,1);
          		 cv.put(fItemModifierID,0);
          		 db.insert(tItem, null, cv);
          		 
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
          		cv.put(fClassAnimationTexture,"Players/Animations/Mage.png");
          		cv.put(fClassAnimationCols,4);
          		cv.put(fClassAnimationRows,4);
          		cv.put(fClassFrameHeight,256);
          		cv.put(fClassFrameWidth,256);
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
          		
          		cv.put(fHeadID, 4);
          		cv.put(fHeadIconTexture,"Players/Heads/Icons/Paladin.png");
          		cv.put(fHeadAnimationTexture,"Players/Heads/Icons/Paladin.png");
          		cv.put(fHeadAnimationCols,5);
          		cv.put(fHeadAnimationRows,4);
          		cv.put(fHeadFrameHeight,256);
          		cv.put(fHeadFrameWidth,256);
          		cv.put(fClassID,1);
          		db.insert(tHead, null, cv); 
          		

	            
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
        	return myAnswer;
        }
        
        public String getClassAnimationTexture(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationTexture +" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationTexture);
        	String myAnswer = myCursor.getString(index);
        	myCursor.close();
        	return myAnswer;
        }
        
        public int getClassAnimationRows(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationRows+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationRows);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	return myAnswer;
        }
        
        public int getClassAnimationCols(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassAnimationCols+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassAnimationCols);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	return myAnswer;
        }
        
        public int getClassFrameWidth(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassFrameWidth+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassFrameWidth);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
        	return myAnswer;
        }
        
        public int getClassFrameHeight(int pID){
       	 	SQLiteDatabase myDB = this.getReadableDatabase();
       	 	Cursor myCursor = myDB.rawQuery("SELECT "+ fClassFrameHeight+" FROM "+ tClass+" WHERE "+ fClassID +"=?",new String[]{String.valueOf(pID)});
        	myCursor.moveToFirst();
        	int index = myCursor.getColumnIndex(fClassFrameHeight);
        	int myAnswer = myCursor.getInt(index);
        	myCursor.close();
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
             return myAnswer;
         }         
         
         public String getItemIconTexture(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemIconTexture +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemIconTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public String getItemAnimationTexture(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemAnimationTexture +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemAnimationTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }
         
         public int getItemType(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemType +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemType);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
         
         public int getItemBuyPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemBuyPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",new String[]{String.valueOf(pID)});
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemBuyPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
        	 return myAnswer;
         }
         
         public int getItemSellPrice(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemSellPrice+" FROM "+tItem+" WHERE "+fItemID+"=?",new String[]{String.valueOf(pID)});
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemSellPrice);
        	 int myAnswer = myCursor.getInt(index);
        	 myCursor.close();
        	 return myAnswer;
         }
        
         public String getItemDescription(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemDescription +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemDescription);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }      
         
         public int getItemClass(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemClass +" FROM "+ tItem +" WHERE "+ fItemID +"=?",new String[]{String.valueOf(pID)});
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemClass);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
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
             return myAnswer;
         }
 
         
}