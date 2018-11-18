package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.CalendarList;
import com.amazonaws.lambda.model.CalendarModel;
import com.amazonaws.lambda.model.MeetingModel;
import com.amazonaws.lambda.model.aviliableTimeSlotModel;

public class DatabasePersistance {
	private Connection conn;

	// make initial connection
	public DatabasePersistance() {
		try {
			conn = DatabaseManage.connect();
		} catch (Exception e) {
			conn = null;
			e.printStackTrace();
		}
	}

	// get a single calendar using ID
	public CalendarModel getCalendar(int calendarID) {
		CalendarModel calendar = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Calendar WHERE calendarID=?;");
			ps.setInt(1, calendarID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int calID = resultSet.getInt("calendarID");
				String calendarName = resultSet.getString("calendarName");
				String startDate = resultSet.getString("startDate");
				String endDate = resultSet.getString("endDate");
				int startHour = resultSet.getInt("startHour");
				int endHouar = resultSet.getInt("endHouar");
				int duration = resultSet.getInt("duration");

				calendar = new CalendarModel(calID, calendarName, startDate, endDate, startHour, endHouar, duration);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendar;
	}

	// get all calendar in the database
	public CalendarList getAllCalendar() {
		CalendarList calendarList = CalendarList.getInstance();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Calendar;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int calID = resultSet.getInt("calendarID");
				String calendarName = resultSet.getString("calendarName");
				String startDate = resultSet.getString("startDate");
				String endDate = resultSet.getString("endDate");
				int startHour = resultSet.getInt("startHour");
				int endHouar = resultSet.getInt("endHouar");
				int duration = resultSet.getInt("duration");

				CalendarModel calendar = new CalendarModel(calID, calendarName, startDate, endDate, startHour, endHouar,
						duration);

				calendarList.add(calendar);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calendarList;
	}

	// delete a single calendar
	public boolean deleteCalendar(int calendarID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Calendar WHERE calendarID = ?;");
			ps.setInt(1, calendarID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// add a new calendar to the database
	public boolean addCalendar(CalendarModel calendar) throws Exception {
	       try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Calendar WHERE calendarID = ?;");
	            ps.setInt(1, calendar.getCalendarID());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) {	               
	                resultSet.close();
	                return false;
	            }

	            ps = conn.prepareStatement("INSERT INTO Calendar (name,startDate,endDate,startHour,endHour,duration) values(?,?,?,?,?,?);");
	            ps.setString(1,  calendar.getCalendarName());
	            ps.setString(2,  calendar.getStartDate());
	            ps.setString(3,  calendar.getEndDate());
	            ps.setInt(4,  calendar.getStartHour());
	            ps.setInt(5,  calendar.getEndHouar());
	            ps.setInt(6,  calendar.getDuration());
	            ps.execute();
	            return true;

	        } catch (Exception e) {
	            throw new Exception("Failed to insert constant: " + e.getMessage());
	        }
	}

	// add a new meeting to the table
	public boolean addMeeting(MeetingModel meeting) throws Exception {
		 try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meeting WHERE meetingID = ?;");
	            ps.setInt(1, meeting.getMeetingID());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) {	               
	                resultSet.close();
	                return false;
	            }

	            ps = conn.prepareStatement("INSERT INTO Meeting (meetingName,meetingLocaion,meetingPerticipent,meetingDate,timeSlotID,calendarID) values(?,?,?,?,?,?);");
	            ps.setString(1,  meeting.getMeetingName());
	            ps.setString(2,  meeting.getMeetingLocaion());
	            ps.setString(3,  meeting.getMeetingPerticipent());
	            ps.setString(4,  meeting.getMeetingDate());
	            ps.setInt(5,  meeting.getTimeSlotID());
	            ps.setInt(6,  meeting.getCalendarID());
	            ps.execute();
	            return true;

	        } catch (Exception e) {
	            throw new Exception("Failed to insert constant: " + e.getMessage());
	        }
	}

	// remove a meeting from meeting table
	public boolean removeMeeting(int meetingID) {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM Meeting WHERE meetingID = ?;");
			ps.setInt(1, meetingID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// add time slots to the table
	public boolean addTimeSlot(aviliableTimeSlotModel timeSlot) throws Exception {
		 try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM aviliableTimeSlot WHERE timeslotID = ?;");
	            ps.setInt(1, timeSlot.getTimeSlotID());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) {	               
	                resultSet.close();
	                return false;
	            }

	            ps = conn.prepareStatement("INSERT INTO aviliableTimeSlot (date,calendarID,timeSlotStatus) values(?,?,?);");
	            ps.setString(1,  timeSlot.getDate());
	            ps.setInt(2,  timeSlot.getCalendarID());
	            ps.setInt(3,  timeSlot.getTimeSlotStatus());
	            ps.execute();
	            return true;

	        } catch (Exception e) {
	            throw new Exception("Failed to insert constant: " + e.getMessage());
	        }
	}

	// remove a timeslot from the calendar
	public boolean removeTimeSlot(int timeslotID) {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM aviliableTimeSlot WHERE timeslotID = ?;");
			ps.setInt(1, timeslotID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// get all timeslot from the table
	public List<aviliableTimeSlotModel> getAllTimeslot(int calendarID) {
		List<aviliableTimeSlotModel> timeSlotList = new ArrayList<aviliableTimeSlotModel>();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM aviliableTimeSlotModel WHERE calendarID=?;");
			ps.setInt(1, calendarID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int timeSlotID = resultSet.getInt("timeSlotID");
				String date = resultSet.getString("date");
				int calID = resultSet.getInt("calendarID");
				int timeSlotStatus = resultSet.getInt("timeSlotStatus");

				aviliableTimeSlotModel timeSlot = new aviliableTimeSlotModel(timeSlotID, date, calendarID, timeSlotStatus);
				
				timeSlotList.add(timeSlot);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeSlotList;
	}

	// set the time slot status
	public boolean setTimeslotStatus(int timeslotID, int statusFlag) {
		CalendarModel calendar = null;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE aviliableTimeSlot SET timeSlotStatus=? WHERE timeslotID=?;");
			ps.setInt(1, statusFlag);
			ps.setInt(2, timeslotID);
			
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
