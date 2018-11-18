package com.amazonaws.lambda.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarList {
	private List<CalendarModel> calendarList;
	
	
	private static CalendarList _instance;
	
	public static CalendarList getInstance(){
		if(_instance == null) {
			_instance = new CalendarList();
		}
		return _instance; 
	}
	
	private CalendarList() {
		calendarList = new ArrayList<CalendarModel>();
	}

	
	public void add(CalendarModel cal) {
		calendarList.add(cal);
	}
	
	public CalendarModel remove(int CalendarID) {
		CalendarModel result = calendarList.remove(CalendarID);
		return result;
	}
	
	
	public List<CalendarModel> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<CalendarModel> calendarList) {
		this.calendarList = calendarList;
	}
	
}
