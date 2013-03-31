package yinote.android.todo;

import java.util.Calendar;

public class ToDoDate
{
    private int year;
    private int month;
    private int day;
    
    public ToDoDate()
    {
        final Calendar cal = Calendar.getInstance();   
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH);
        day = cal.get(cal.DAY_OF_MONTH);
    }
    
    public void setYear(int year)
    {
        this.year = year;
    }
    public void setMonth(int month)
    {
        this.month = month;
    }
    public void setDay(int day)
    {
        this.day = day;
    }
    
    public int getYear()
    {
        return year;
    }
    public int getMonth()
    {
        return month;
    }
    public int getDay()
    {
        return day;
    } 
}