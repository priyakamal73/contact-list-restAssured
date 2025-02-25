package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class reportInfo {
    public static void logPass(ExtentTest test, String customMessage, String responseBody) {
        String formattedResponse = formatJson(responseBody);
        test.log(Status.PASS, customMessage + "<br><br><b>Response Body:</b><br><pre>" + formattedResponse + "</pre>");
    }

    public static void logFail(ExtentTest test, String customMessage, String responseBody) {
        String formattedResponse = formatJson(responseBody);
        test.log(Status.FAIL, customMessage + "<br><br><b>Response Body:</b><br><pre>" + formattedResponse + "</pre>");
    }

    private static String formatJson(String jsonString) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(JsonParser.parseString(jsonString));
        } catch (Exception e) {
            return jsonString;
        }
    }
}