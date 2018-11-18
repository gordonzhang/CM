package com.amazonaws.lambda.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;

import com.amazonaws.lambda.model.CalendarList;
import com.amazonaws.lambda.model.CalendarModel;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class LPCHandler implements RequestStreamHandler {
    JSONParser parser = new JSONParser();

    @SuppressWarnings("unchecked")
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        LambdaLogger logger = context.getLogger();
        logger.log("Loading Java Lambda handler of ProxyWithStream");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject responseJson = new JSONObject();
        JSONObject responseBody = new JSONObject();
        String responseCode = "200";
        
        String val;
        Integer calendarID = null;
        CalendarModel calendar = null;
        JSONArray allTimeSlots;
        JSONArray meetings;

        try {
            JSONObject event = (JSONObject)parser.parse(reader);
            if (event.get("queryStringParameters") != null) {
                JSONObject qps = (JSONObject)event.get("queryStringParameters");
                if ( qps.get("calendarID") != null) {
                	val = (String) qps.get("calendarID");
                	calendarID = Integer.parseInt(val);
                } else {
                	// handle null calendarID
                }
            }
            
			try {
				calendar = CalendarList.getInstance().getCalendar(calendarID);
				allTimeSlots = calendar.getAllTimeSlotsInJSON();
				meetings = calendar.getAllMeetingsInJSON();
				responseBody.put("allTimeSlots", allTimeSlots);
				responseBody.put("meetings", meetings);
				responseBody.put("calendarID", String.valueOf(calendarID));
			} catch(Exception ex) {
				
			}
			
            JSONObject headerJson = new JSONObject();
            headerJson.put("Content-Type", "application/json");
            headerJson.put("Access-Control-Allow-Origin", "*");
            headerJson.put("Access-Control-Allow-Method", "GET,POST");

            responseJson.put("isBase64Encoded", false);
            responseJson.put("statusCode", responseCode);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toJSONString());

        } catch(ParseException pex) {
            responseJson.put("statusCode", "400");
            responseJson.put("exception", pex);
        }

        logger.log(responseJson.toJSONString());
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(responseJson.toJSONString());  
        writer.close();
    }
}