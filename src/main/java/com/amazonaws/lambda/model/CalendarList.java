package com.amazonaws.lambda.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarList {
	private List<Calendar> calendarList;
	
	
	private static CalendarList _instance;
	
	public static CalendarList getInstance(){
		if(_instance == null) {
			_instance = new CalendarList();
		}
		return _instance; 
	}
	
	private CalendarList() {
		calendarList = new ArrayList<Calendar>();
	}

	
	public void add(Calendar cal) {
		calendarList.add(cal);
	}
	
	public Calendar remove(int CalendarID) {
		Calendar result = calendarList.remove(CalendarID);
		return result;
	}
	
	
	public List<Calendar> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<Calendar> calendarList) {
		this.calendarList = calendarList;
	}
	
}
