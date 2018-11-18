package com.amazonaws.lambda.model;

public class MeetingModel {
	
	private int meetingID;
	private String meetingName;
	private String meetingLocaion;
	private String meetingPerticipent;
	private String meetingDate;
	private int timeSlotID;	//use to match the time slot stored in the time slot table
	private int calendarID; // use to identify which calendar it is in
	
	
	public MeetingModel(int meetingID, String meetingName, String meetingLocaion, String meetingPerticipent,
			String meetingDate,int timeSlotID, int calendarID) {
		super();
		this.meetingID = meetingID;
		this.meetingName = meetingName;
		this.meetingLocaion = meetingLocaion;
		this.meetingPerticipent = meetingPerticipent;
		this.meetingDate = meetingDate;
		this.calendarID = calendarID;
	}
	
	public int getTimeSlotID() {
		return timeSlotID;
	}

	public void setTimeSlotID(int timeSlotID) {
		this.timeSlotID = timeSlotID;
	}

	public MeetingModel(String meetingName, String meetingLocaion, String meetingPerticipent,
			String meetingDate, int timeSlotID, int calendarID) {
		super();
		this.meetingName = meetingName;
		this.meetingLocaion = meetingLocaion;
		this.meetingPerticipent = meetingPerticipent;
		this.meetingDate = meetingDate;
		this.calendarID = calendarID;
	}
	
	public int getMeetingID() {
		return meetingID;
	}
	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public String getMeetingLocaion() {
		return meetingLocaion;
	}
	public void setMeetingLocaion(String meetingLocaion) {
		this.meetingLocaion = meetingLocaion;
	}
	public String getMeetingPerticipent() {
		return meetingPerticipent;
	}
	public void setMeetingPerticipent(String meetingPerticipent) {
		this.meetingPerticipent = meetingPerticipent;
	}
	public String getMeetingDate() {
		return meetingDate;
	}
	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}
	public int getCalendarID() {
		return calendarID;
	}
	public void setCalendarID(int calendarID) {
		this.calendarID = calendarID;
	}
	
}
