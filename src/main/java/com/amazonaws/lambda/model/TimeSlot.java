package com.amazonaws.lambda.model;

public class TimeSlot {
	private int timeSlotID;
	private String date;
	private int calendarID;
	private int timeSlotStatus;

	public TimeSlot(int timeSlotID, String date, int calendarID, int timeSlotStatus) {
		super();
		this.timeSlotID = timeSlotID;
		this.date = date;
		this.calendarID = calendarID;
		this.timeSlotStatus = timeSlotStatus;
	}

	public TimeSlot(String date, int calendarID, int timeSlotStatus) {
		super();
		this.timeSlotID = timeSlotID;
		this.date = date;
		this.calendarID = calendarID;
		this.timeSlotStatus = timeSlotStatus;
	}

	public int getTimeSlotID() {
		return timeSlotID;
	}

	public void setTimeSlotID(int timeSlotID) {
		this.timeSlotID = timeSlotID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCalendarID() {
		return calendarID;
	}

	public void setCalendarID(int calendarID) {
		this.calendarID = calendarID;
	}

	public int getTimeSlotStatus() {
		return timeSlotStatus;
	}

	public void setTimeSlotStatus(int timeSlotStatus) {
		this.timeSlotStatus = timeSlotStatus;
	}

}
