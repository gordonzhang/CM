package com.amazonaws.lambda.model;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.amazonaws.lambda.db.DatabasePersistance;

public class CalendarModel {

	private int calendarID;
	private String calendarName;
	private String startDate;
	private String endDate;
	private int startHour;
	private int endHouar;
	private int duration;
	public List<TimeSlot> timeSlots;
	public List<MeetingModel> meetings;
	
	public CalendarModel(String calendarName, String startDate, String endDate, int startHour, int endHouar,
			int duration) {
		this.calendarName = calendarName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startHour = startHour;
		this.endHouar = endHouar;
		this.duration = duration;
	}

	public CalendarModel(int calendarID, String calendarName, String startDate, String endDate, int startHour,
			int endHouar, int duration) {
		this.calendarID = calendarID;
		this.calendarName = calendarName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startHour = startHour;
		this.endHouar = endHouar;
		this.duration = duration;
	}

	public int getCalendarID() {
		return calendarID;
	}

	public void setCalendarID(int calendarID) {
		this.calendarID = calendarID;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHouar() {
		return endHouar;
	}

	public void setEndHouar(int endHouar) {
		this.endHouar = endHouar;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	public CalendarModel getCalendar(int CalendarID) throws Exception {
		DatabasePersistance dp = new DatabasePersistance();
		CalendarModel cal = dp.getCalendar(CalendarID);
		return cal;
	}
	
	public JSONArray getAllTimeSlotsInJSON() {
		JSONArray JSONTimeSlots = new JSONArray();
		for (int i = 0; i < timeSlots.size(); ++i) {
			TimeSlot timeSlot = timeSlots.get(i);
			JSONObject ts = new JSONObject();
			ts.put("timeSlotID", timeSlot.getTimeSlotID());
			ts.put("date", timeSlot.getDate());
			ts.put("calendarID", timeSlot.getCalendarID());
			ts.put("timeSlotStatus", timeSlot.getTimeSlotStatus());
			JSONTimeSlots.add(ts);
		}
		return JSONTimeSlots;
	}
	
	
	public JSONArray getAllMeetingsInJSON() {
		JSONArray JSONMeetings = new JSONArray();
		for (int i = 0; i < meetings.size(); ++i) {
			MeetingModel meetingModel = meetings.get(i);
			JSONObject mt = new JSONObject();
			mt.put("meetingID", meetingModel.getTimeSlotID());
			mt.put("meetingName", meetingModel.getMeetingName());
			mt.put("meetingLocaion", meetingModel.getMeetingLocaion());
			mt.put("meetingPerticipent", meetingModel.getMeetingPerticipent());
			mt.put("meetingDate", meetingModel.getMeetingDate());
			mt.put("timeSlotID", meetingModel.getTimeSlotID());
			mt.put("calendarID", meetingModel.getCalendarID());

			JSONMeetings.add(mt);
		}
		return JSONMeetings;
	}

}
