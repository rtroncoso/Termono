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
        static final String fItemModifiersID = "ModifiersID";
        static final String fItemClassID = "ItemClassID";
        
        static final String tInventory = "Inventory";
        static final String fInventoryItemID = "ItemID";
        static final String fInventoryItemAmount = "Amount";
        static final String fInventoryIsItemEquiped = "IsEquiped";
        static final String fInventoryPlayerID = "PlayerID";
        
        static final String tPlayer = "Player";
        static final String fPlayerID = "PlayerID";
        static final String fPlayerName = "PlayerName";
        static final String fPlayerInventoryID = "PlayerInventoryID";
        
        
        public myDatabase(Context context) {
// THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
// IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
// DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
                super(context, dbName, null, 12);
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
                        fItemClassID+" INTEGER, "+
                        fItemModifiersID+" INTEGER)"
                        );
                //hacer que no sea auto increasing?
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
                		fInventoryItemID+" INTEGER PRIMARY KEY , "+
                        fInventoryItemAmount+" INTEGER , "+
                        fInventoryIsItemEquiped+" INTEGER , "+//SQLite no tiene booleans, uso 1 y 0
                        fInventoryPlayerID+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
                		fPlayerID+" INTEGER PRIMARY KEY , "+
                        fPlayerName+" TEXT)"
                        );
                
       
// OPTIONALLY PREPOPULATE THE TABLE WITH SOME VALUES   
                 ContentValues cv = new ContentValues();
                 		//@=====//##############> - Swords 
                 		cv.put(fItemID, 0);//-------Cypress Stick-----------
		                cv.put(fItemName, "Cypress Stick");
		                cv.put(fItemTexture, "Paladin/Swords/CypressStick.png");
		                cv.put(fItemType, 5);
		                cv.put(fItemDescription, "A reliable wooden stick");
		                cv.put(fItemBuyPrice, 15);
		                cv.put(fItemSellPrice, 8);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 1);//-------Bronze Sword-----------
				        cv.put(fItemName, "Bronze Sword");
				        cv.put(fItemTexture, "Paladin/Swords/BronzeSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A plain bronze sword");
		                cv.put(fItemBuyPrice, 60);
		                cv.put(fItemSellPrice, 32);
				        cv.put(fItemClassID,1);
				        cv.put(fItemModifiersID,0);
				        db.insert(tItem, null, cv);
				        cv.put(fItemID, 2);//--------Iron Sword----------
				        cv.put(fItemName, "Iron Sword");
				        cv.put(fItemTexture, "Paladin/Swords/IronSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A good iron sword");
		                cv.put(fItemBuyPrice, 180);
		                cv.put(fItemSellPrice, 105);
				        cv.put(fItemClassID,1);
						cv.put(fItemModifiersID,0);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 3);//-------Steel Sword-----------
				        cv.put(fItemName, "Steel Sword");
				        cv.put(fItemTexture, "Paladin/Swords/SteelSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A strong steel sword");
		                cv.put(fItemBuyPrice, 430);
		                cv.put(fItemSellPrice, 255);
				        cv.put(fItemClassID,1);
						cv.put(fItemModifiersID,0);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 4);//-------Knights Sword1-----------
				        cv.put(fItemName, "Knights Sword");
				        cv.put(fItemTexture, "Paladin/Swords/KnightsSword1.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A sword given to the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 800);
				        cv.put(fItemClassID,1);
						cv.put(fItemModifiersID,0);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 5);//-------Knights Sword2-----------
				        cv.put(fItemName, "Knights Sword");
				        cv.put(fItemTexture, "Paladin/Swords/KnightsSword2.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "A sword given to the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 800);
				        cv.put(fItemClassID,1);
						cv.put(fItemModifiersID,0);
						db.insert(tItem, null, cv);
						cv.put(fItemID, 6);//-------Mythril Sword-----------
				        cv.put(fItemName, "Mythril Sword");
				        cv.put(fItemTexture, "Paladin/Swords/MythrilSword.png");
				        cv.put(fItemType, 5);
				        cv.put(fItemDescription, "The sword of a champion");
		                cv.put(fItemBuyPrice, 4600);
		                cv.put(fItemSellPrice, 2700);
				        cv.put(fItemClassID,1);
						cv.put(fItemModifiersID,0);
						db.insert(tItem, null, cv);
						
						//@=====//##############> - PlateBodies 
                 		cv.put(fItemID, 7);//-------Bronze Platebody-----------
		                cv.put(fItemName, "Bronze Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/BronzePlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A plain bronze platebody");
		                cv.put(fItemBuyPrice, 110);
		                cv.put(fItemSellPrice, 70);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 8);//-------Iron Platebody-----------
		                cv.put(fItemName, "Iron Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/IronPlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A good iron platebody");
		                cv.put(fItemBuyPrice, 420);
		                cv.put(fItemSellPrice, 360);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 9);//-------Steel Platebody-----------
		                cv.put(fItemName, "Steel Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/SteelPlate.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "A solid steel platebody");
		                cv.put(fItemBuyPrice, 860);
		                cv.put(fItemSellPrice, 620);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 10);//-------Knights Platebody1-----------
		                cv.put(fItemName, "Knights Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate1.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "An armour worn by the city knights");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 1420);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 11);//-------Knights Platebody2-----------
		                cv.put(fItemName, "Knights Platebody");
		                cv.put(fItemTexture, "Paladin/PlateBodies/KnightsPlate2.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "An armor worn by the city knights");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 1420);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                cv.put(fItemID, 12);//-------Mythril Chain-----------
		                cv.put(fItemName, "Mythril Chainmail");
		                cv.put(fItemTexture, "Paladin/PlateBodies/MythrilChain.png");
		                cv.put(fItemType, 1);
		                cv.put(fItemDescription, "The armor of a champion");
		                cv.put(fItemBuyPrice, 7200);
		                cv.put(fItemSellPrice, 3500);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
						
		              //@=====//##############> - Shields 
                 		cv.put(fItemID, 13);//-------Wooden Shield-----------
		                cv.put(fItemName, "Wooden Shield");
		                cv.put(fItemTexture, "Paladin/Shields/WoodenShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A wooden shield");
		                cv.put(fItemBuyPrice, 14);
		                cv.put(fItemSellPrice, 7);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 14);//-------Bronze Shield-----------
		                cv.put(fItemName, "Bronze Shield");
		                cv.put(fItemTexture, "Paladin/Shields/BronzeShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A simple bronze shield");
		                cv.put(fItemBuyPrice, 55);
		                cv.put(fItemSellPrice, 27);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 15);//-------Iron Shield-----------
		                cv.put(fItemName, "Iron Shield");
		                cv.put(fItemTexture, "Paladin/Shields/IronShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A good iron shield");
		                cv.put(fItemBuyPrice, 160);
		                cv.put(fItemSellPrice, 95);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 16);//-------Steel Shield-----------
		                cv.put(fItemName, "Steel Shield");
		                cv.put(fItemTexture, "Paladin/Shields/SteelShield.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A solid steel shield");
		                cv.put(fItemBuyPrice, 390);
		                cv.put(fItemSellPrice, 260);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
                 		cv.put(fItemID, 17);//-------Knights Shield1-----------
		                cv.put(fItemName, "Knights Shield");
		                cv.put(fItemTexture, "Paladin/Shields/KnightsShield1.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A shield worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 700);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
                 		cv.put(fItemID, 18);//-------Knights Shield2-----------
		                cv.put(fItemName, "Knights Shield");
		                cv.put(fItemTexture, "Paladin/Shields/KnightsShield2.png");
		                cv.put(fItemType, 4);
		                cv.put(fItemDescription, "A shield worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 700);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv);
		                
		                //@=====//##############> - Helm 
                 		cv.put(fItemID, 19);//-------Bronze Helm-----------
		                cv.put(fItemName, "Bronze Helm");
		                cv.put(fItemTexture, "Paladin/Helms/BronzeHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A common bronze helm");
		                cv.put(fItemBuyPrice, 40);
		                cv.put(fItemSellPrice, 30);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 20);//-------Iron Helm-----------
		                cv.put(fItemName, "Iron Helm");
		                cv.put(fItemTexture, "Paladin/Helms/IronHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A good iron helm");
		                cv.put(fItemBuyPrice, 120);
		                cv.put(fItemSellPrice, 80);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 21);//-------Steel Helm-----------
		                cv.put(fItemName, "Steel Helm");
		                cv.put(fItemTexture, "Paladin/Helms/SteelHelm.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A fine steel helm");
		                cv.put(fItemBuyPrice, 300);
		                cv.put(fItemSellPrice, 200);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
                 		cv.put(fItemID, 22);//-------Knights Helm1-----------
		                cv.put(fItemName, "Knights Helm");
		                cv.put(fItemTexture, "Paladin/Helms/KnightsHelm1.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A helm worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
		                cv.put(fItemID, 23);//-------Knights Helm2-----------
		                cv.put(fItemName, "Knights Helm");
		                cv.put(fItemTexture, "Paladin/Helms/KnightsHelm2.png");
		                cv.put(fItemType, 0);
		                cv.put(fItemDescription, "A helm worn by the knights of the city");
		                cv.put(fItemBuyPrice, 0);
		                cv.put(fItemSellPrice, 500);
		                cv.put(fItemClassID,1);
		                cv.put(fItemModifiersID,0);
		                db.insert(tItem, null, cv); 
		                
						cv.clear(); //Hace falta hacerle clear?
		                
						
		                		
		                cv.put(fInventoryItemID,0);//tiene que ser igual a fItemID
		                cv.put(fInventoryItemAmount, 1);//tiene que ser igual a fItemName
		                cv.put(fInventoryIsItemEquiped, 0);
		                cv.put(fInventoryPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		                db.insert(tInventory, null, cv);
		                cv.put(fInventoryItemID,5);//tiene que ser igual a fItemID
		 		        cv.put(fInventoryItemAmount, 2);//tiene que ser igual a fItemName
		 		        cv.put(fInventoryIsItemEquiped, 0);
		 		        cv.put(fInventoryPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,11);
		 		        cv.put(fInventoryItemAmount, 1);
		 		        cv.put(fInventoryIsItemEquiped, 0);
		 		        cv.put(fInventoryPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		        cv.put(fInventoryItemID,18);
		 		        cv.put(fInventoryItemAmount, 2);
		 		        cv.put(fInventoryIsItemEquiped, 0);
		 		        cv.put(fInventoryPlayerID, 0);
		 		        db.insert(tInventory, null, cv);
		 		    	cv.put(fInventoryItemID,23);
		 		        cv.put(fInventoryItemAmount, 5);
		 		        cv.put(fInventoryIsItemEquiped, 0);
		 		        cv.put(fInventoryPlayerID, 0);
		 		        db.insert(tInventory, null, cv);	
		 		        cv.put(fInventoryItemID,17);
		 		   		cv.put(fInventoryItemAmount, 4);
	 		        	cv.put(fInventoryIsItemEquiped, 0);
	 		        	cv.put(fInventoryPlayerID, 0);
	 		        	db.insert(tInventory, null, cv);
		                cv.clear();
		                		
		                		
		                cv.put(fPlayerID, 0);
		                cv.put(fPlayerName, "Joaquin");
		                	db.insert(tPlayer, null, cv);
		                

		                
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
        
         public int getInventoryItemID(int pIndex){
       	  SQLiteDatabase myDB = this.getReadableDatabase();
             Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemID +" FROM "+ tInventory,null);
             myCursor.moveToPosition(pIndex);
             int index = myCursor.getColumnIndex(fInventoryItemID);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
         }
        
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
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemClassID +" FROM "+ tItem +" WHERE "+ fItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemClassID);
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
                  
         public int[] getEquipedIDs(int pEstado){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 String[] mySearch = new String[]{String.valueOf(pEstado)};
        	 Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryItemID +" FROM "+ tInventory +" WHERE "+ fInventoryIsItemEquiped +"=?",mySearch);
        	 int index = myCursor.getColumnIndex(fInventoryItemID);
        	 int myAnswer[] = new int[myCursor.getCount()];
        	 for(int i = 0; i < myCursor.getCount(); i++){
        		myCursor.moveToPosition(i);
        		myAnswer[i] = myCursor.getInt(index);
        	 }
             myCursor.close();
             return myAnswer;
         }

         public void EquipItem(int ID, int pEquiped){
                 SQLiteDatabase myDB = this.getWritableDatabase();
                 ContentValues cv = new ContentValues();
                 cv.put(fInventoryIsItemEquiped,String.valueOf(pEquiped));   
                 myDB.update(tInventory, cv, fInventoryItemID+"=?", new String []{String.valueOf(ID)});
         }
         
         public int isItemEquiped(int pID){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{String.valueOf(pID)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fInventoryIsItemEquiped+" FROM "+ tInventory +" WHERE "+ fInventoryItemID +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fInventoryIsItemEquiped);
             int myAnswer = myCursor.getInt(index);
             myCursor.close();
             return myAnswer;
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