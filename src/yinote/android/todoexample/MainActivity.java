package yinote.android.todoexample;

import yinote.android.todo.ToDoActivity;
import yinote.android.todoexample.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button todoButton = (Button)findViewById(R.id.button_todo);
		todoButton.setOnClickListener(new Button.OnClickListener() {	    
		    public void onClick(View v)
		    {
		        Intent todoIntent = new Intent(getApplicationContext(),ToDoActivity.class);
		        startActivity(todoIntent);
		        //startActivity(new Intent(Intent.ACTION_VIEW).setDataAndType(null, CalendarActivity.MIME_TYPE));
		    }
		});	
	}
	
}
