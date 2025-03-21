package tests;

import POJO.LoginUserDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.reportInfo;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TC4_LoginUser extends BaseClass {
    Response response;
    Properties prop = getProp();
    LoginUserDetails loginUserDetails = new LoginUserDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Login User");
    }

    @Test(description = "Login user")
    public void loginUser() {
        try {
            String email = prop.getProperty("email");
            String password = prop.getProperty("password");

            loginUserDetails.setEmail(email);
            loginUserDetails.setPassword(password);

            String token = prop.getProperty("token");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(loginUserDetails)
                    .post("users/login");

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));

            reportInfo.logPass(test, "User logged in successfully", responseBody);

            String updatedToken = response.jsonPath().getString("token");
            updatePropertyFile("token", updatedToken);
        } catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";

            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}