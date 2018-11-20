package com.amazonaws.lambda.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.model.CalendarList;
import com.amazonaws.lambda.model.CalendarModel;
import com.amazonaws.lambda.model.MeetingModel;
import com.amazonaws.lambda.model.TimeSlot;

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
		CalendarModel cal = null;
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

				cal = new CalendarModel(calID, calendarName, startDate, endDate, startHour, endHouar, duration);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cal;
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

				CalendarModel calendarModel = new CalendarModel(calID, calendarName, startDate, endDate, startHour, endHouar,
						duration);

				calendarList.add(calendarModel);
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
	public boolean addCalendar(CalendarModel calendarModel) throws Exception {
	       try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Calendar WHERE calendarID = ?;");
	            ps.setInt(1, calendarModel.getCalendarID());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) {	               
	                resultSet.close();
	                return false;
	            }

	            ps = conn.prepareStatement("INSERT INTO Calendar (name,startDate,endDate,startHour,endHour,duration) values(?,?,?,?,?,?);");
	            ps.setString(1,  calendarModel.getCalendarName());
	            ps.setString(2,  calendarModel.getStartDate());
	            ps.setString(3,  calendarModel.getEndDate());
	            ps.setInt(4,  calendarModel.getStartHour());
	            ps.setInt(5,  calendarModel.getEndHouar());
	            ps.setInt(6,  calendarModel.getDuration());
	            ps.execute();
	            return true;

	        } catch (Exception e) {
	            throw new Exception("Failed to insert constant: " + e.getMessage());
	        }
	}

	// add a new meeting to the table
	public boolean addMeeting(MeetingModel meetingModel) throws Exception {
		 try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Meeting WHERE meetingID = ?;");
	            ps.setInt(1, meetingModel.getMeetingID());
	            ResultSet resultSet = ps.executeQuery();
	            
	            // already present?
	            while (resultSet.next()) {	               
	                resultSet.close();
	                return false;
	            }

	            ps = conn.prepareStatement("INSERT INTO Meeting (meetingName,meetingLocaion,meetingPerticipent,meetingDate,timeSlotID,calendarID) values(?,?,?,?,?,?);");
	            ps.setString(1,  meetingModel.getMeetingName());
	            ps.setString(2,  meetingModel.getMeetingLocaion());
	            ps.setString(3,  meetingModel.getMeetingPerticipent());
	            ps.setString(4,  meetingModel.getMeetingDate());
	            ps.setInt(5,  meetingModel.getTimeSlotID());
	            ps.setInt(6,  meetingModel.getCalendarID());
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
	public boolean addTimeSlot(TimeSlot timeSlot) throws Exception {
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
	public List<TimeSlot> getAllTimeslot(int calendarID) {
		List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM aviliableTimeSlotModel WHERE calendarID=?;");
			ps.setInt(1, calendarID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int timeSlotID = resultSet.getInt("timeSlotID");
				String date = resultSet.getString("date");
				int calID = resultSet.getInt("calendarID");
				int timeSlotStatus = resultSet.getInt("timeSlotStatus");

				TimeSlot timeSlot = new TimeSlot(timeSlotID, date, calendarID, timeSlotStatus);
				
				timeSlotList.add(timeSlot);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeSlotList;
	}

	// get all meetings from the table
	public List<MeetingModel> getAllMeetings(int calendarID) {
		List<MeetingModel> MeetingModels = new ArrayList<MeetingModel>();
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM MeetingsTable WHERE calendarID=?;");
			ps.setInt(1, calendarID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int meetingID = resultSet.getInt("meetingID");
				String meetingName = resultSet.getString("meetingName");
				String meetingLocaion = resultSet.getString("meetingLocaion");
				String meetingPerticipent = resultSet.getString("meetingPerticipent");
				String meetingDate = resultSet.getString("meetingDate");
				int timeSlotID = resultSet.getInt("timeSlotID");

				MeetingModel meetingModel = new MeetingModel(meetingID, meetingName, meetingLocaion, meetingPerticipent,
						meetingDate, timeSlotID, calendarID);
				
				MeetingModels.add(meetingModel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MeetingModels;
	}
	
	// set the time slot status
	public boolean setTimeslotStatus(int timeslotID, int statusFlag) {
		CalendarModel calendarModel = null;
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
	
	// get meetings
	public List<MeetingModel> getMeetings(int calendarID, String year, String month, String day) {
		List<MeetingModel> MeetingModels = new ArrayList<MeetingModel>();
		PreparedStatement ps;
		try {
			if (day.equals("00")) {
				ps = conn.prepareStatement("SELECT * FROM Meeting WHERE calendarID=? AND date LIKE ?;");
				ps.setInt(1, calendarID);
				ps.setString(2, year+month+"%");
			} else {
				ps = conn.prepareStatement("SELECT * FROM Meeting WHERE calendarID=? AND date=?;");
				ps.setInt(1, calendarID);
				ps.setString(2, year+month+day+"%");
			}
			
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int meetingID = resultSet.getInt("meetingID");
				String meetingName = resultSet.getString("name");
				String meetingLocaion = resultSet.getString("location");
				String meetingPerticipent = resultSet.getString("participant");
				String meetingDate = resultSet.getString("date");
				int timeSlotID = resultSet.getInt("timeSlotID");

				MeetingModel meetingModel = new MeetingModel(meetingID, meetingName, meetingLocaion, meetingPerticipent,
						meetingDate, timeSlotID, calendarID);
				
				MeetingModels.add(meetingModel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MeetingModels;
	}
	
	// get timeslots
	public List<TimeSlot> getTimeSlots(int calendarID, String year, String month, String day) {
		List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
		PreparedStatement ps;
		try {
			if (day.equals("00")) {
				ps = conn.prepareStatement("SELECT * FROM avilableTimeSlot WHERE calendarID=? AND date LIKE ?;");
				ps.setInt(1, calendarID);
				ps.setString(2, month+"-"+"%"+"-"+year+"%");
			} else {
				ps = conn.prepareStatement("SELECT * FROM avilableTimeSlot WHERE calendarID=? AND date LIKE ?;");
				ps.setInt(1, calendarID);
				ps.setString(2, month+"-"+day+"-"+year+"%");
			}
			
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				int timeSlotID = resultSet.getInt("timeSlotID");
				String date = resultSet.getString("date");
				int timeSlotStatus = resultSet.getInt("timeSlotStatus");
				int calID = resultSet.getInt("calendarID");

				TimeSlot ts = new TimeSlot(timeSlotID, date, calID, timeSlotStatus);
				
				timeSlots.add(ts);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeSlots;
	}

}
