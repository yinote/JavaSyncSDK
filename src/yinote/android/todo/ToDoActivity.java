package yinote.android.todo;
import com.exina.android.calendar.CalendarActivity;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.json.JSONException;

import yinote.android.todoexample.R;
import yinote.android.ydep.store.impl.LocalSyncStateStoreImpl;
import yinote.android.ydep.store.impl.LocalTodoStoreImpl;
import yinote.ydep.exception.RequestError;
import yinote.ydep.remote.dto.UserInfo;
import yinote.ydep.remote.store.RemoteSetting;
import yinote.ydep.service.SyncService;
import yinote.ydep.service.impl.SyncServiceImpl;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.EditText;

import android.view.View.OnTouchListener;
import android.view.MotionEvent;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class ToDoActivity extends Activity {
       
    private MySimpleAdapter listItemAdapter;
    private ArrayList<HashMap<String,Object>> listItem ;
    private List<ToDoDate> listDate;
    private List<String> listText;
    private List<String> listID;
    private String todoString;
    private EditText todoText;
    private ListView todoList;
    
    private int todayItemPosition = -1;
    private int nextItemPosition = -1;       
    private int listPosition = -1;
    
    static final int checkboxmove = 50;
    static final int checkboxreturn = 40;
    static final int checkdelete = 90;
    static final int longclicktime = 600;
    static final int longclickmove = 10;
    static final int itemminheight = 40;
    
    ToDoDataAccessSqlite dbAccess  = new  ToDoDataAccessSqlite();
   
    @Override
    protected void onDestroy()
    {
        listDate.clear();
        listText.clear();
        listID.clear();
        listItem.clear();
        todoString = "";     
        todayItemPosition = -1;
        nextItemPosition = -1;       
        listPosition = -1;
        if(dbAccess != null)
            dbAccess.finish();
        super.onDestroy();
    }
  
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) findViewById(R.id.toast_layout));       
        final TextView text = (TextView) layout.findViewById(R.id.text);
        final Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
                
        if(!dbAccess.init())
        {
            text.setText(getString(R.string.dataerrorpromption).toString());
            toast.show();
        } 
                    
        todoList = (ListView)findViewById(R.id.listview_todo);               
        todoText = (EditText)findViewById(R.id.todo_input);
        listItem = new ArrayList<HashMap<String,Object>>();    
        listDate = new ArrayList<ToDoDate>();
        listText = new ArrayList<String>();
        listID = new ArrayList<String>();
        listItemAdapter = new MySimpleAdapter(this,listItem,R.layout.listview_todo, 
                new String[]{"ItemText"}, new int[]{R.id.ItemText});    
        todoList.setAdapter(listItemAdapter);             
        updateList();
        
        final Animation animation=AnimationUtils.loadAnimation(this, R.anim.todolist_animation);              
        todoList.setOnTouchListener(new OnTouchListener() {
            float x,y,ux,uy,mx,my,prex,prey;
            int cbleft,txleft,whleft;
            long startTime,endTime; 
            
            View whView;
            View cbView;
            View txView;           
            View itemView;
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    startTime = System.currentTimeMillis();
                    x=event.getX();
                    y=event.getY();
                    prex = x;
                    prey = y;
                    
                    int p1=todoList.pointToPosition((int)x, (int)y);                   
                    if(p1 != -1)
                    {
                        int firstVisiblePosition = todoList.getFirstVisiblePosition();
                        whView = todoList.getChildAt(p1-firstVisiblePosition).findViewById(R.id.item_whiteview);
                        cbView = todoList.getChildAt(p1-firstVisiblePosition).findViewById(R.id.item_checkbox);
                        txView = todoList.getChildAt(p1-firstVisiblePosition).findViewById(R.id.ItemText);
                        itemView = todoList.getChildAt(p1-firstVisiblePosition);                       
                        cbleft = cbView.getLeft();                
                        txleft = txView.getLeft();
                        whleft = whView.getLeft();
                    }                    
                    return false;
                }
                
                if(event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    mx=event.getX();
                    my=event.getY();                   
                    int p1=todoList.pointToPosition((int)x, (int)y);     
                    int p2=todoList.pointToPosition((int)mx, (int)my);
                    int deltax = (int)(mx-prex);                 
                    if(p1 != -1)
                    {
                        if(p2 != p1)
                            return false;                       
                        prex = mx;
                        prey = my;                                          
                        int leftDelta = cbView.getLeft()+deltax;                        
                        if(leftDelta > cbleft)
                        {
                            cbView.offsetLeftAndRight(deltax);
                            txView.offsetLeftAndRight(deltax);
                            whView.offsetLeftAndRight(deltax);
                                                       
                            cbView.invalidate();
                            txView.invalidate();
                            whView.invalidate();
                            itemView.invalidate();
                        }
                        else
                        {       
                            cbView.offsetLeftAndRight(cbleft-cbView.getLeft());
                            txView.offsetLeftAndRight(txleft-txView.getLeft());
                            whView.offsetLeftAndRight(whleft-whView.getLeft());

                            cbView.invalidate();
                            txView.invalidate();
                            whView.invalidate();
                            itemView.invalidate();
                        }                       
                    }                   
                   return false;                 
                }
                
                if(event.getAction()==MotionEvent.ACTION_UP){                 
                    endTime = System.currentTimeMillis();                   
                    ux=event.getX();
                    uy=event.getY();                
                    final int p1=todoList.pointToPosition((int)x, (int)y);
                    final int p2=todoList.pointToPosition((int)ux, (int)uy);                    
                    if(p1 == -1)
                    {
                        return true;
                    }
                    
                    int firstVisiblePosition = todoList.getFirstVisiblePosition(); 
                    if(p1==p2&&Math.abs(x-ux)<longclickmove && endTime-startTime > longclicktime)
                    {                                             
                        cbView.offsetLeftAndRight(cbleft-cbView.getLeft());
                        txView.offsetLeftAndRight(txleft-txView.getLeft());
                        whView.offsetLeftAndRight(whleft-whView.getLeft());
                        
                        cbView.invalidate();
                        txView.invalidate();
                        whView.invalidate();
                        itemView.invalidate();
                        
                        listPosition = p1;      
                        startActivityForResult(new Intent(Intent.ACTION_PICK).setDataAndType(null, CalendarActivity.MIME_TYPE), 100);
                        return true;                        
                    }
                    
                    if(p1==p2&&ux-x>checkdelete){            
                        
                          animation.setAnimationListener(new AnimationListener() {
                            public void onAnimationStart(Animation animation) {
                            }
                            public void onAnimationRepeat(Animation animation) {
                            }
                            public void onAnimationEnd(Animation animation) {
                                if(todayItemPosition != -1)
                                {
                                    if(todayItemPosition > p1)
                                    {
                                      todayItemPosition -= 1;    
                                    }
                                    else if(todayItemPosition == p1)
                                    {
                                        if(todayItemPosition == listItem.size()-1 ||
                                                (nextItemPosition != -1 && nextItemPosition-todayItemPosition == 1))
                                            todayItemPosition = -1;
                                    }
                                }
                                
                                if(nextItemPosition != -1 )
                                {
                                    if(nextItemPosition > p1)
                                    {
                                        nextItemPosition -= 1;    
                                    }
                                    else if(nextItemPosition == p1)
                                    {
                                        if(nextItemPosition == listItem.size()-1)
                                            nextItemPosition = -1;
                                    }
                                }
                                
                                listItem.remove(p1);
                                listDate.remove(p1);
                                listText.remove(p1);
                                
                                String id = listID.get(p1);
                                long time = System.currentTimeMillis();
                                dbAccess.completeRecord(id,time);
                                listID.remove(p1);                                            
                                listItemAdapter.notifyDataSetChanged();
                                
                            }              
                        });         
                        todoList.getChildAt(p1-firstVisiblePosition).startAnimation(animation);                           
                        animation.cancel();
                        return true;
                    }
                    else
                    {
                        cbView.offsetLeftAndRight(cbleft-cbView.getLeft());
                        txView.offsetLeftAndRight(txleft-txView.getLeft());
                        whView.offsetLeftAndRight(whleft-whView.getLeft());
                        cbView.invalidate();
                        txView.invalidate();
                        whView.invalidate();
                        itemView.invalidate();
                        return true;
                    }
                }
                return false;
            }
        });
        
        
        Button addButton = (Button)findViewById(R.id.todo_add);  
        addButton.setOnClickListener(new Button.OnClickListener() {        
            public void onClick(View v)
            {
                String strText = todoText.getText().toString().trim();
                if(strText.length() <= 0)
                {
                    text.setText( getApplication().getString(R.string.todopromption).toString());
                    toast.show();
                    return;
                }
                listPosition = -1;
                startActivityForResult(new Intent(Intent.ACTION_PICK).setDataAndType(null, CalendarActivity.MIME_TYPE), 100);
            }
        }); 
        
        Button syncButton = (Button)findViewById(R.id.todo_sync);      
        syncButton.setOnClickListener(new Button.OnClickListener() {        
            public void onClick(View v)
            {
            	RemoteSetting settings = new RemoteSetting("42.96.141.70", 9000, new UserInfo("accT1"));
            	try {
            		Log.d("ToDoActivity", "invoke sync");
					new SyncServiceImpl(settings, new LocalSyncStateStoreImpl())
						.enableTodoSync(new LocalTodoStoreImpl())
						.doSync();
				} catch (JSONException e) {
					Log.e("ToDoActivity", e.getMessage());
				} catch (RequestError e) {
					Log.e("ToDoActivity", e.getMessage());
				}
//                ToDoSync todoSync = new ToDoSync(dbAccess);
//                int nRet = todoSync.sync();
//                if(nRet == 1)
//                {
//                    text.setText(getString(R.string.syncinit).toString());
//                    toast.show();
//                }
//                else if(nRet == 2)
//                {
//                    text.setText(getString(R.string.syncserverstate).toString());
//                    toast.show();
//                }
//                else if(nRet == 3)
//                {
//                    text.setText(getString(R.string.syncserverchunk).toString());
//                    toast.show();
//                }
//                else if(nRet == 4)
//                {
//                    text.setText(getString(R.string.syncserverupdate).toString());
//                    toast.show();
//                }
//                else if(nRet == 5)
//                {
//                    text.setText(getString(R.string.synclocalupdate).toString());
//                    toast.show();
//                }
//                else if(nRet == 11)
//                {
//                    text.setText(getString(R.string.syncupdateflag).toString());
//                    toast.show();
//                }

              
                updateList();  
                listItemAdapter.notifyDataSetChanged();
            }
        }); 
        
    }
    
    private void updateList()
    {    
        listDate.clear();
        listText.clear();
        listID.clear();
        listItem.clear(); 
        todayItemPosition = -1;
        nextItemPosition = -1;       
        listPosition = -1;
        
        ArrayList<ToDoItem> listToDo = dbAccess.getAllData(false);

        final Calendar dat = Calendar.getInstance();
        for(int i = 0; i < listToDo.size(); i ++)
        {
            HashMap<String,Object> map = new HashMap<String, Object>();
            ToDoItem item = listToDo.get(i);
            ToDoDate date = item.getDate();
            
            if(todayItemPosition == -1)
            {
                if(date.getYear() == dat.get(dat.YEAR) && date.getMonth() == dat.get(dat.MONTH) && date.getDay() == dat.get(dat.DAY_OF_MONTH))
                {
                    todayItemPosition = i;
                }
            }
            if(nextItemPosition == -1)
            {
                if(date.getYear() > dat.get(dat.YEAR) ||
                        (date.getYear() == dat.get(dat.YEAR)&& date.getMonth() > dat.get(dat.MONTH) )||
                        (date.getYear() == dat.get(dat.YEAR)&& date.getMonth() == dat.get(dat.MONTH) && date.getDay() > dat.get(dat.DAY_OF_MONTH)))
                {
                    nextItemPosition = i;
                }
            }
            
            if(nextItemPosition != -1)
            {
                map.put("ItemText", item.getTitle() + "\n"+getString(R.string.startdate).toString()+ date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay());
            }
            else
            {
                map.put("ItemText", item.getTitle());
             }
            listItem.add(map);
            listText.add(item.getTitle());
            listDate.add(date);        
            listID.add(item.getId());
        }      
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {                  
        if(resultCode==RESULT_OK) {                       
            int year = data.getIntExtra("year", 0);
            int month = data.getIntExtra("month", 0);
            int day = data.getIntExtra("day", 0);
            
            final Calendar dat = Calendar.getInstance();     
            todoString = todoText.getText().toString().trim();
            HashMap<String,Object> map = new HashMap<String, Object>();
                  
            ToDoDate todoDate = new ToDoDate();
            todoDate.setYear(year);
            todoDate.setMonth(month);
            todoDate.setDay(day);
            
            String updateID = "";           
            if(listPosition != -1)
            {
                listItem.remove(listPosition);
                listDate.remove(listPosition);
                todoString = listText.get(listPosition).toString();
                listText.remove(listPosition);
                updateID = listID.get(listPosition);
                listID.remove(listPosition);
                
                if(todayItemPosition != -1 && todayItemPosition > listPosition)
                    todayItemPosition -= 1;
                
                if(nextItemPosition != -1 && nextItemPosition > listPosition)
                    nextItemPosition -= 1;                
            }
            else
                todoText.setText("");
          
            int index = -1;
            for(int i = 0; i < listDate.size(); i ++)
            {
                ToDoDate tmpDate = (ToDoDate)listDate.get(i);
                if(year < tmpDate.getYear() || 
                        (year == tmpDate.getYear() && month < tmpDate.getMonth())||
                        (year == tmpDate.getYear() && month == tmpDate.getMonth() && day < tmpDate.getDay()))
                {
                    index = i;
                    break;
                }
            }
                  
            if(year < dat.get(dat.YEAR) || 
                    (year == dat.get(dat.YEAR) && month < dat.get(dat.MONTH))||
                    (year==dat.get(dat.YEAR) && month == dat.get(dat.MONTH) && day <= dat.get(dat.DAY_OF_MONTH))) 
            {
                
                if(day == dat.get(dat.DAY_OF_MONTH))
                {
                    if(todayItemPosition == -1)
                    {
                        if(index == -1)
                            todayItemPosition = listDate.size();
                        else
                            todayItemPosition = index;
                    }
                    if(nextItemPosition != -1)
                    {
                        nextItemPosition += 1;
                    }
                }
                else
                {
                    if(todayItemPosition != -1)
                    {
                        todayItemPosition += 1;
                    }
                    if(nextItemPosition != -1)
                    {
                        nextItemPosition += 1;
                    }
                }
                
                map.put("ItemText",todoString);     
                String id = dbAccess.getNextId();

                if(index != -1)
                {
                    listItem.add(index,map);  
                    listDate.add(index,todoDate);
                    listText.add(index,todoString);
                    
                    if(listPosition == -1)
                        listID.add(index,id);
                    else
                        listID.add(index,updateID);
                }
                else
                {
                    listItem.add(map);
                    listDate.add(todoDate);
                    listText.add(todoString);
                    if(listPosition == -1)
                        listID.add(id);
                    else
                        listID.add(updateID);
                }
                
                if(listPosition == -1)
                {
                    ToDoItem item = new ToDoItem();
                    item.setId(id);
                    item.setTitle(todoString);
                    item.setDate(todoDate);
            
                    dbAccess.addRecord(id, item);
                }
                else
                {
                    ToDoItem item = new ToDoItem();
                    item.setId(updateID);
                    item.setTitle(todoString);
                    item.setDate(todoDate);
                    dbAccess.updateRecord(updateID, item,false);
                }
               
                listItemAdapter.notifyDataSetChanged();
                return;
            }       
            
            if(nextItemPosition == -1)
            {
                if(index == -1)
                    nextItemPosition = listDate.size();
            }
        
            map.put("ItemText", todoString + "\n"+getString(R.string.startdate).toString()+ year+"-"+(month+1)+"-"+day);
            String id = dbAccess.getNextId();            
            if(index != -1)
            {
                listItem.add(index,map);  
                listDate.add(index,todoDate);
                listText.add(index,todoString);
                if(listPosition == -1)
                    listID.add(index,id);
                else
                    listID.add(index,updateID);
            }
            else
            {
                listItem.add(map);
                listDate.add(todoDate);
                listText.add(todoString);
                if(listPosition == -1)
                    listID.add(id);
                else
                    listID.add(updateID);
            }
            
            if(listPosition == -1)
            {
                ToDoItem item = new ToDoItem();
                item.setId(id);
                item.setTitle(todoString);
                item.setDate(todoDate);
                dbAccess.addRecord(id, item);
            }
            else
            {
                ToDoItem item = new ToDoItem();
                item.setId(updateID);
                item.setTitle(todoString);
                item.setDate(todoDate);
                dbAccess.updateRecord(updateID, item,false);
            }           
            listItemAdapter.notifyDataSetChanged();               
        }
    }
 
    public class MySimpleAdapter extends SimpleAdapter{
          
        private int[] mColors = {R.color.red, R.color.blue, R.color.white };     
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
        {
            super(context,data,resource,from,to);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int selected = mColors[0];
            View localView = super.getView(position, convertView, parent);
            localView.setMinimumHeight(itemminheight);
            if((todayItemPosition != -1 && position >= todayItemPosition) && (nextItemPosition == -1 || position < nextItemPosition))
            {
                selected = mColors[1];
            }
            else if(nextItemPosition != -1 && position >= nextItemPosition)
            {
                selected = mColors[2];
            }
                   
            localView.setBackgroundResource(selected);
            return localView;
        }
    }
}
