package com.amazonaws.lambda.model;

public class Calendar {

	private int calendarID;
	private String calendarName;
	private String startDate;
	private String endDate;
	private int startHour;
	private int endHouar;
	private int duration;

	public Calendar(String calendarName, String startDate, String endDate, int startHour, int endHouar,
			int duration) {
		this.calendarName = calendarName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startHour = startHour;
		this.endHouar = endHouar;
		this.duration = duration;
	}

	public Calendar(int calendarID, String calendarName, String startDate, String endDate, int startHour,
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
	

}
