package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.lambda.model.CalendarList;
import com.amazonaws.lambda.model.CalendarModel;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReLoadCalendarLambdaFunctionHandler implements RequestStreamHandler {
	private static LambdaLogger logger;
	private final static JSONParser parser = new JSONParser();
	private final static JSONObject handerJson = new JSONObject();

	@SuppressWarnings("unchecked")
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		// TODO Auto-generated method stub
		logger = context.getLogger();

		handerJson.put("Content-Type", "application/json");
		handerJson.put("Access-Control-Allow-Origin", "*");
		handerJson.put("Access-Control-Allow-Method", "GET,POST");

		JSONObject reponseJson = new JSONObject();
		reponseJson.put("isBase64Encoded", false);
		reponseJson.put("headers", handerJson);

		JSONObject responseBody = new JSONObject();

		CalendarList instance = CalendarList.getInstance();
		CalendarModel calendarModel = new CalendarModel("123123", "123123", "123123", 0, 0, 0);
		CalendarModel calendarModel1 = new CalendarModel("123123", "123123", "123123", 0, 0, 0);
		CalendarModel calendarModel2 = new CalendarModel("123123", "123123", "123123", 0, 0, 0);
		CalendarModel calendarModel3 = new CalendarModel("123123", "123123", "123123", 0, 0, 0);
		instance.add(calendarModel);
		instance.add(calendarModel1);
		instance.add(calendarModel2);
		instance.add(calendarModel3);

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONObject event = (JSONObject) parser.parse(reader);
			logger.log("Received body as Json is " + event);

			JSONObject header = (JSONObject) event.get("queryStringParameters");
			int count = 0;
			List<Integer> calendarL = new ArrayList<Integer>();
			for (CalendarModel m : instance.getCalendarList()) {
				calendarL.add(count);
				count++;
			}
			Type typeToken = new TypeToken<ArrayList<Integer>>() {
			}.getType();
			
			
			String calendarList_asJson = new Gson().toJson(calendarL);
			responseBody.put("CalendarList", calendarList_asJson);
			reponseJson = responseCreator("successful input", 200, reponseJson, responseBody);

		} catch (Exception ex) {
			reponseJson = responseCreator("unsuccesful" + ex.getStackTrace(), 500, reponseJson, responseBody);
			logger.log("" + ex);
			ex.printStackTrace();
		}

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(reponseJson.toJSONString());
		writer.close();
	}

	private JSONObject responseCreator(String result, int statusCode, JSONObject responseJson,
			JSONObject responseBody) {
		responseBody.put("result", result);
		responseJson.put("statusCode", statusCode);
		responseJson.put("body", responseBody.toString());

		return responseJson;

	}

}
