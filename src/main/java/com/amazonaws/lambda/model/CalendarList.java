package com.amazonaws.lambda.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.lambda.db.*;

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
	
	public CalendarModel getCalendar(int CalendarID) throws Exception {
		DatabasePersistance dbp = new DatabasePersistance();
		CalendarModel cal = dbp.getCalendar(CalendarID);
		return cal;
	}
	
		
}
