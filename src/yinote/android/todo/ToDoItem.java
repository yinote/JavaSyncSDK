package yinote.android.todo;
import yinote.ydep.local.LocalVersioned;
import yinote.ydep.remote.dto.Todo;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

public class ToDoItem implements LocalVersioned,Todo{
    
    private String id;
    private ToDoDate date;
    private String text;
    
    private long complete;
    private int usn;
    private int dirty;
    
    public ToDoItem(){
        id = "";
        date = new ToDoDate();
        text = "";     
        complete = 0;
        usn = 0;
        dirty = 1;
    }
    
    public static ToDoItem createFromRemote(Todo remoteTodo) {
    	ToDoItem item = new ToDoItem();
        item.setId(remoteTodo.getId());
        item.setTitle(remoteTodo.getTitle());
        item.setDateLong(remoteTodo.getStartAt());
        item.setComplete(remoteTodo.getCompleteAt());     
        item.setUsn(remoteTodo.getUsn());
        item.setDirty(0);
        return item;
    }
    
    public ToDoItem(JSONObject jsonData)
    {
        if (jsonData == null) {
            return;
        }
        try {
            if (!jsonData.isNull("id")){
                id = jsonData.getString("id");
            }
            if (!jsonData.isNull("title")){
                text = jsonData.getString("title");
            }
            
            if (!jsonData.isNull("startAt")){
                
                Date dat = new Date(jsonData.getLong("startAt"));
                date = new ToDoDate();
                date.setYear(dat.getYear());
                date.setMonth(dat.getMonth());
                date.setDay(dat.getDate());
            }
            
            if (!jsonData.isNull("usn")){
                usn = jsonData.getInt("usn");
            }
            else
                usn = 0;
            
            if (!jsonData.isNull("completeAt")){
                complete = jsonData.getLong("completeAt");
            }
            else
                complete = 0;
            
        }catch (JSONException e){
            e.printStackTrace();
        }        
    }
    
    public JSONObject toJSONNew()
    {
        JSONObject jObject = new JSONObject();
        try {
            if (id.length() > 0) {
                jObject.put("id", id);
            }

            if (text.length() > 0) {
                jObject.put("title", text);
            }

            if (date != null) {         
                Date dat = new Date();
                int day = date.getDay();
                dat.setYear(date.getYear());
                dat.setMonth(date.getMonth());
                dat.setDate(day);
                dat.setHours(8);
                dat.setMinutes(0);
                dat.setSeconds(0);
                
                jObject.put("startAt", dat.getTime());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;        
    }
    
    public JSONObject toJSONUpdate()
    {
        JSONObject jObject = new JSONObject();
        try {

            if (text.length() > 0) {
                jObject.put("title", text);
            }

            if (date != null) {         
                Date dat = new Date();
                int day = date.getDay();
                dat.setYear(date.getYear());
                dat.setMonth(date.getMonth());
                dat.setDate(day);
                
                jObject.put("startAt", dat.getTime());
            }

            jObject.put("usn", usn);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;        
    }
    
    public JSONObject toJSONComplete()
    {
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("completeAt", complete);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jObject;        
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public void setDateLong(long time)
    {
        Date dat = new Date(time);
        date.setYear(dat.getYear());
        date.setMonth(dat.getMonth());
        date.setDay(dat.getDate());
    }
    
    public void setDate(ToDoDate date)
    {
        this.date.setYear(date.getYear());
        this.date.setMonth(date.getMonth());
        this.date.setDay(date.getDay());
    }
    
    public void setTitle(String text)
    {
        this.text = text;
    }
    
    public void setComplete(long complete)
    {
        this.complete = complete;
    }
    
    public void setUsn(int usn)
    {
        this.usn = usn;
    }
    
    public void setDirty(int dirty)
    {
        this.dirty = dirty;
    }
    
    public String getId()
    {
        return id;
    }
    
    public ToDoDate getDate()
    {
        return date;
    }
    
    public int getUsn()
    {
        return usn;
    }

    public int getDirty()
    {
        return dirty;
    }
    
    public boolean isDirty()
    {
       if(dirty > 0)
           return true;
       return false;
    }
    
    public boolean isExpunged()
    {
        return false;
    }
    
    public String getTitle()
    {
        return text;
    }
    
    public long getStartAt()
    {
        Date dat = new Date();
        int day = date.getDay();
        dat.setYear(date.getYear());
        dat.setMonth(date.getMonth());
        dat.setDate(day);
        
        return dat.getTime();
    }
    
    public long getCompleteAt()
    {
        return complete;
    }
}
