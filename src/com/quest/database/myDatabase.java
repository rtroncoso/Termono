package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class myDatabase extends SQLiteOpenHelper {
 
        static final String dbName = "myDB";
       
        static final String tItem = "Item"; 
        static final String fItemID = "ItemID";
        static final String fItemName = "Name";
        static final String fItemTexture = "Texture";
        static final String fItemType = "Type";
        static final String fItemDescription = "Description";
        static final String fItemPrice = "Price";
        static final String fItemModifiersID = "ModifiersID";
        
        static final String tInventory = "Inventory";
        static final String fInventoryItemID = "ItemID";
        static final String fInventoryItemName = "ItemName";
        static final String fInventoryPlayerID = "PlayerID";
        
        static final String tPlayer = "Player";
        static final String fPlayerID = "PlayerID";
        static final String fPlayerName = "PlayerName";
        static final String fPlayerInventoryID = "PlayerInventoryID";
        
        
        public myDatabase(Context context) {
// THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
// IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
// DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
                super(context, dbName, null, 4);
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
                        fItemPrice+" INTEGER , "+
                        fItemModifiersID+" INTEGER)"
                        );
                //hacer que no sea auto increasing?
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tInventory+" ("+
                		fInventoryItemID+" INTEGER PRIMARY KEY , "+
                        fInventoryItemName+" TEXT , "+
                        fInventoryPlayerID+" INTEGER)"
                        );
                
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tPlayer+" ("+
                		fPlayerInventoryID+" INTEGER PRIMARY KEY , "+
                        fPlayerName+" TEXT , "+
                        fPlayerInventoryID+" INTEGER)"
                        );
                
       
// OPTIONALLY PREPOPULATE THE TABLE WITH SOME VALUES   
                 ContentValues cv = new ContentValues();
                 		cv.put(fItemID, 0);
		                cv.put(fItemName, "Sword");
		                cv.put(fItemTexture, "Sword.png");
		                cv.put(fItemType, 5);
		                cv.put(fItemDescription, "Espada, para azotar a los monstruos pervertidos");
		                cv.put(fItemPrice, 200);
		                cv.put(fItemModifiersID,0);
		                		db.insert(tItem, null, cv);
		                //cv.put(fItemID, 1);
				        cv.put(fItemName, "Shield");
				        cv.put(fItemTexture, "Shield.png");
				        cv.put(fItemType, 4);
				        cv.put(fItemDescription, "Escudo, para que no te azoten");
				        cv.put(fItemPrice, 300);
				        cv.put(fItemModifiersID,1);
				        		db.insert(tItem, null, cv);                                       
		                		cv.clear(); //Hace falta hacerle clear?
		                		
		                		
		                cv.put(fInventoryItemID,0);//tiene que ser igual a fItemID
		                cv.put(fInventoryItemName, "Sword");//tiene que ser igual a fItemName
		                cv.put(fInventoryPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		                		db.insert(tInventory, null, cv);
		                cv.put(fInventoryItemID,1);//tiene que ser igual a fItemID
		 		        cv.put(fInventoryItemName, "Shield");//tiene que ser igual a fItemName
		 		        cv.put(fInventoryPlayerID, 0);//tiene que ser igual a PlayerID o fPlayerInventoryID?
		 		                db.insert(tInventory, null, cv);
		                		cv.clear();
		                		
		                		
		                cv.put(fPlayerID, 0);
		                cv.put(fPlayerName, "Joaquin");
		                cv.put(fPlayerInventoryID, 0);//Uso esto o relaciono con fPlayerID directamente?
		                

		                
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
         
         public String getType(String pName){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{pName};//{String.valueOf(Name)};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemType +" FROM "+ tItem +" WHERE "+ fItemName +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemType);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }
         
         public String getImagePath(String pName){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
             String[] mySearch = new String[]{pName};
             Cursor myCursor = myDB.rawQuery("SELECT "+ fItemTexture +" FROM "+ tItem +" WHERE "+ fItemName +"=?",mySearch);
             myCursor.moveToFirst();
             int index = myCursor.getColumnIndex(fItemTexture);
             String myAnswer = myCursor.getString(index);
             myCursor.close();
             return myAnswer;
         }         
         
         public int getPrice(String pName){
        	 SQLiteDatabase myDB = this.getReadableDatabase();
        	 String[] mySearch = new String[]{pName};
        	 Cursor myCursor = myDB.rawQuery("SELECT "+fItemPrice+" FROM "+tItem+" WHERE "+fItemName+"=?",mySearch);
        	 myCursor.moveToFirst();
        	 int index = myCursor.getColumnIndex(fItemPrice);
        	 String myAnswer = myCursor.getString(index);
        	 myCursor.close();
        	 return Integer.parseInt(myAnswer);
         }
        
         
         
         int getInventoryCount(){
                SQLiteDatabase db=this.getWritableDatabase();
                Cursor cur= db.rawQuery("Select * from "+tInventory, null);
                int x= cur.getCount();
                cur.close();
                return x;
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