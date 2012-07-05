package com.quest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class myDatabase extends SQLiteOpenHelper {
 
        static final String dbName = "myDB";
        static final String tLevels = "Levels";
        static final String fLevelID = "levelNum";
        static final String fLevelUnLocked = "levelLocked";
        static final String fLevelBeat = "levelBeat";
        static final String fLevelScore = "levelScore";
       
        public myDatabase(Context context) {
// THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
// IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
// DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
                super(context, dbName, null, 1);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db) {
// ESTABLISH NEW DATABASE TABLES IF THEY DON'T ALREADY EXIST IN THE DATABASE
                db.execSQL("CREATE TABLE IF NOT EXISTS "+tLevels+" (" +
                                        fLevelID + " INTEGER PRIMARY KEY , " +
                                        fLevelUnLocked + " TEXT, " +
                                        fLevelBeat + " TEXT, " +
                                        fLevelScore + " TEXT" +
                                        ")");
       
// OPTIONALLY PREPOPULATE THE TABLE WITH SOME VALUES   
                 ContentValues cv = new ContentValues();
                        cv.put(fLevelID, 1);
                        cv.put(fLevelUnLocked, "true");
                        cv.put(fLevelBeat, "false");
                        cv.put(fLevelScore, "10");
                                db.insert(tLevels, null, cv);
                        cv.put(fLevelID, 2);
                        cv.put(fLevelUnLocked, "false");
                        cv.put(fLevelBeat, "false");
                        cv.put(fLevelScore, "0");
                                db.insert(tLevels, null, cv);
                        cv.put(fLevelID, 3);
                        cv.put(fLevelUnLocked, "false");
                        cv.put(fLevelBeat, "false");
                        cv.put(fLevelScore, "0");
                                db.insert(tLevels, null, cv);
                                       
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
                db.execSQL("DROP TABLE IF EXISTS "+tLevels);
                onCreate(db);
        }
       
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