package yinote.android.todo;

import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.content.ContentValues;
import android.database.Cursor;

public class ToDoDataAccessSqlite {
    
    private SQLiteDatabase db = null;    
    private String tablename = "";
     
    static final String dataDir = "/yinote/";    
    static final String column_id = "id";
    static final String column_text = "text";
    static final String column_year = "year";
    static final String column_month = "month";
    static final String column_day = "day";    
    static final String column_complete = "complete";
    static final String column_usn = "usn";
    static final String column_dirty = "dirty";
    
    static final String todo_dbname = "todo.db";
    static final String todo_tbname = "todo";
    
    public ToDoDataAccessSqlite()
    {
        db = null;
        tablename = "";
    }
    
    public boolean isInitiated()
    {
        if(db == null)
            return false;
        
        return true;
    }
      
    public boolean init()
    {
        String sdcardDir = Environment.getExternalStorageDirectory().toString();
        if(sdcardDir.length() <= 0)
            return false;
        
        String dbfile = sdcardDir+dataDir+todo_dbname;        
        try
        {
            db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);           
            if(db == null)
                return false;           
            tablename = todo_tbname;           
            String createtableSql = "CREATE TABLE IF NOT EXISTS ";
            createtableSql += tablename;
            createtableSql +="(";
            createtableSql +=column_id;
            createtableSql +=" varchar(25) primary key, ";
            createtableSql +=column_text;
            createtableSql +=" varchar(250), ";
            createtableSql +=column_year;
            createtableSql +=" integer, ";
            createtableSql +=column_month;
            createtableSql +=" integer, ";
            createtableSql +=column_day;
            createtableSql +=" integer,";
            createtableSql +=column_complete;
            createtableSql +=" integer, ";
            createtableSql +=column_usn;
            createtableSql +=" integer, ";
            createtableSql +=column_dirty;
            createtableSql +=" integer);";
            db.execSQL(createtableSql);        
        }
        catch(Exception ex)
        { 
            db = null;
            return false;
        }
      
        return true;
    }
    
    private ToDoItem getItem(Cursor cur)
    {
        ToDoItem item = new ToDoItem();
        
        int idIndex = cur.getColumnIndex(column_id);
        int textIndex = cur.getColumnIndex(column_text);
        int yearIndex = cur.getColumnIndex(column_year);
        int monthIndex = cur.getColumnIndex(column_month);
        int dayIndex = cur.getColumnIndex(column_day);            
        int completeIndex = cur.getColumnIndex(column_complete);
        int usnIndex = cur.getColumnIndex(column_usn);
        int dirtyIndex = cur.getColumnIndex(column_dirty);
        
        ToDoDate date = new ToDoDate();
        item.setId(cur.getString(idIndex));
        item.setTitle(cur.getString(textIndex));
        date.setYear(cur.getInt(yearIndex));
        date.setMonth(cur.getInt(monthIndex));
        date.setDay(cur.getInt(dayIndex));
        item.setDate(date);           
        item.setComplete(cur.getLong(completeIndex));
        item.setUsn(cur.getInt(usnIndex));
        item.setDirty(cur.getInt(dirtyIndex));
        
        return item;
    }
    
    public ArrayList<ToDoItem> getAllData(boolean bIncludeComplete)
    {       
        ArrayList<ToDoItem> listToDo = new  ArrayList<ToDoItem>(); 
        if(db == null)
            return listToDo;
                          
        String orderbySql = column_year;
        orderbySql += ",";
        orderbySql += column_month;
        orderbySql += ",";
        orderbySql += column_day;      
        String strSql = "";
        if(!bIncludeComplete)
            strSql = "SELECT * FROM "+tablename+" WHERE "+column_complete +" = 0 " + "ORDER BY "+orderbySql;
        else
            strSql = "SELECT * FROM "+tablename+" ORDER BY "+orderbySql;
        
        Cursor cur = db.rawQuery(strSql, null);              
        if (cur.moveToFirst() == false)
        {
            cur.close();
            return listToDo;
        }      
        for(cur.moveToFirst();!cur.isAfterLast();cur.moveToNext())
        {
            ToDoItem item = getItem(cur);          
            listToDo.add(item);
        }       
        cur.close();            
        return listToDo;
    }
       
    public ToDoItem getRecord(String id)
    {      
        String strSql = "SELECT * FROM ";
        strSql += tablename;
        strSql += " WHERE ";
        strSql += column_id;
        strSql += " = '";
        strSql += id;
        strSql += "'";
        Cursor cur = db.rawQuery(strSql, null);              
        if (cur.moveToFirst() == false)
        {
            cur.close();
            return null;
        }           
        ToDoItem item = getItem(cur); 
        cur.close();
        return item;      
    }
    
    public boolean addRecord(String id, ToDoItem todoItem)
    {
        if(db == null)
            return false;
                
        ContentValues values=new ContentValues();
        values.put(column_id, id);
        values.put(column_text, todoItem.getTitle());
        values.put(column_year, todoItem.getDate().getYear());
        values.put(column_month, todoItem.getDate().getMonth());
        values.put(column_day, todoItem.getDate().getDay());
        values.put(column_complete,todoItem.getCompleteAt());
        values.put(column_usn, todoItem.getUsn());    
        values.put(column_dirty, todoItem.getDirty());
        
       if(db.insert(tablename, null, values) == -1)
            return false;
       
        return true;
    }
    
    public boolean removeRecord(String id)
    {
        if(db == null)
            return false;
        
        String strid = id;    
        String whereSql = column_id;
        whereSql += " = ?";              
        db.delete(tablename, whereSql, new String[]{strid});       
        return true;
    }
    
    public boolean completeRecord(String id,long time)
    {
        if(db == null)
            return false;
        
        String strid = id;    
        String whereSql = column_id;
        whereSql += " = ?";    
        
        ContentValues values = new ContentValues();
        values.put(column_complete, time);
        values.put(column_dirty, 1);                  
        db.update(tablename, values, whereSql, new String[]{strid});

        return true;
    }
    
    public boolean updateUSN(String id,int usn)
    {
        if(db == null)
            return false;
        
        ContentValues values = new ContentValues();
        values.put(column_usn, usn);
        String strid = id;    
        String whereSql = column_id;
        whereSql += " = ?";
        db.update(tablename, values, whereSql, new String[]{strid});
        
        return true;
    }
    
    public boolean updateRecord(String id, ToDoItem todoItem,boolean sync)
    {
        if(db == null)
            return false;
        
        ContentValues values = new ContentValues();
        values.put(column_year, todoItem.getDate().getYear());
        values.put(column_month, todoItem.getDate().getMonth());
        values.put(column_day, todoItem.getDate().getDay());        
        if(sync)
        {
            values.put(column_complete, todoItem.getCompleteAt());
            values.put(column_usn, todoItem.getUsn());
            values.put(column_text, todoItem.getTitle());
            values.put(column_dirty, 0);
        }
        else
            values.put(column_dirty, 1);
        
        String strid = id;    
        String whereSql = column_id;
        whereSql += " = ?";
        db.update(tablename, values, whereSql, new String[]{strid});
        
        return true;
    }
    
    public String getNextId()
    {
        ObjectId id = new ObjectId();
        return id.toString();        
    }   
    
    public boolean finish()
    {
        if(db == null)
            return false;
       
        db.close();
        return true;
    }
}
