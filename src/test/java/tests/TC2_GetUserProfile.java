package tests;

import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Properties;
import utils.reportInfo;
import static io.restassured.RestAssured.given;

public class TC2_GetUserProfile extends BaseClass {
    Properties prop = getProp();
    Response response;

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Get User Profile");
    }

    @Test(description = "Get the user profile details")
    public void getUserProfile() {
        try {
            String token = prop.getProperty("token");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .header("accept", "application/json")
                    .get("/users/me");

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));

            reportInfo.logPass(test, "User profile retrieved successfully", responseBody);
        } catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";
            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}