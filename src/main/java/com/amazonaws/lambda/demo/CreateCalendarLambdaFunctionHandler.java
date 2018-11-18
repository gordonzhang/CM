package com.amazonaws.lambda.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.lambda.model.CalendarList;
import com.amazonaws.lambda.model.CalendarModel;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class CreateCalendarLambdaFunctionHandler implements RequestStreamHandler {
	private static LambdaLogger logger;
	private final static JSONParser parser = new JSONParser();
	private final static JSONObject handerJson = new JSONObject();

	@Override
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

		try {
			String calendarName = null;
			String startDate = null;
			String endDate = null;
			int startHour = -1;
			int endHour = -1;
			int duration = -1;

			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			JSONObject input_asJson = (JSONObject) parser.parse(reader);

			logger.log("Received body as Json is " + input_asJson);

			String body = (String) input_asJson.get("body");
			if (body != null) {
				input_asJson = (JSONObject) parser.parse(body);
			} else {
				// handle the empty input
				reponseJson = responseCreator("unsuccesful null input", 301, reponseJson, responseBody);
			}

			if (input_asJson != null) {
				calendarName = (String) input_asJson.get("calendarName");
				startDate = (String) input_asJson.get("startDate");
				endDate = (String) input_asJson.get("endDate");
				startHour = Integer.parseInt((String) input_asJson.get("startHour"));
				endHour = Integer.parseInt((String) input_asJson.get("endHour"));
				duration = Integer.parseInt((String) input_asJson.get("duration"));
			} else {
				reponseJson = responseCreator("unsuccesful null value", 306, reponseJson, responseBody);
			}
			try {
				CalendarModel calendarModel = new CalendarModel(calendarName, startDate, endDate, startHour, endHour,
						duration);
				CalendarList.getInstance().add(calendarModel);
				String insertCal_asJson = new Gson().toJson(calendarModel);
				responseBody.put("validation", insertCal_asJson);
				reponseJson = responseCreator("succesful！！", 200, reponseJson, responseBody);
			} catch (Exception ex) {
				reponseJson = responseCreator("unsuccesful unable to add to model", 300, reponseJson, responseBody);
			}

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
