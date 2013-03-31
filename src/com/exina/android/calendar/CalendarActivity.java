/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exina.android.calendar;

import yinote.android.todoexample.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Button;


public class CalendarActivity extends Activity  implements CalendarView.OnCellTouchListener{
	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
	CalendarView mView = null;
	TextView mHit;
	TextView mTitle;
	TextView mLeft;
	TextView mRight;
	Handler mHandler = new Handler();
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setOnCellTouchListener(this);
        
     
        //if(getIntent().getAction().equals(Intent.ACTION_PICK))
        //	findViewById(R.id.hint).setVisibility(View.INVISIBLE);
        mLeft = (TextView)findViewById(R.id.dateleft);
        mRight = (TextView)findViewById(R.id.dateright);
                
        mTitle = (TextView)findViewById(R.id.datetitle);
        
        mLeft.getBackground().setAlpha(0);
        mRight.getBackground().setAlpha(0);
        mTitle.getBackground().setAlpha(0);
        
        
        int year = mView.getYear();
        int month = mView.getMonth()+1;
        mTitle.setText(year+ getString(R.string.year).toString()+month+getString(R.string.month).toString());
        
        
        Button addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(new Button.OnClickListener() {        
            public void onClick(View v)
            {
                mView.nextMonth();
                int year = mView.getYear();
                int month = mView.getMonth()+1;
                mTitle.setText(year+ getString(R.string.year).toString()+month+getString(R.string.month).toString());
            }
        }); 
        
        Button minusButton = (Button)findViewById(R.id.minus_button);
        minusButton.setOnClickListener(new Button.OnClickListener() {        
            public void onClick(View v)
            {
                mView.previousMonth();
                int year = mView.getYear();
                int month = mView.getMonth()+1;
                mTitle.setText(year+ getString(R.string.year).toString()+month+getString(R.string.month).toString());
            }
        }); 
        
        
    }

	public void onTouch(Cell cell) {
		Intent intent = getIntent();
		String action = intent.getAction();
		if(action.equals(Intent.ACTION_PICK) || action.equals(Intent.ACTION_GET_CONTENT)) {
			int year  = mView.getYear();
			int month = mView.getMonth();
			int day   = cell.getDayOfMonth();
			
			// FIX issue 6: make some correction on month and year
			if(cell instanceof CalendarView.GrayCell) {
				// oops, not pick current month...				
				if (day < 15) {
					// pick one beginning day? then a next month day
					if(month==11)
					{
						month = 0;
						year++;
					} else {
						month++;
					}
					
				} else {
					// otherwise, previous month
					if(month==0) {
						month = 11;
						year--;
					} else {
						month--;
					}
				}
			}
			
			Intent ret = new Intent();
			ret.putExtra("year", year);
			ret.putExtra("month", month);
			ret.putExtra("day", day);
			this.setResult(RESULT_OK, ret);
			finish();
			return;
		}
		int day = cell.getDayOfMonth();
		if(mView.firstDay(day))
			mView.previousMonth();
		else if(mView.lastDay(day))
			mView.nextMonth();
		else
			return;

		mHandler.post(new Runnable() {
			public void run() {
				Toast.makeText(CalendarActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
			}
		});
	}

}