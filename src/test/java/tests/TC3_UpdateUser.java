package tests;

import POJO.UpdateUserDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.reportInfo;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TC3_UpdateUser extends BaseClass {
    Response response;
    Properties prop = getProp();
    UpdateUserDetails updateUserDetails = new UpdateUserDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Update User Details");
    }

    @Test(description = "Update the user details")
    public void updateuser() {
        try {
            String firstname = prop.getProperty("firstname");
            String lastname = prop.getProperty("lastname");
            String email = prop.getProperty("email");
            String password = prop.getProperty("password");

            updateUserDetails.setFirstName(firstname);
            updateUserDetails.setLastName(lastname);
            updateUserDetails.setEmail(email);
            updateUserDetails.setPassword(password);

            String token = prop.getProperty("token");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(updateUserDetails)
                    .patch("users/me");

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));

            reportInfo.logPass(test, "User details updated successfully", responseBody);

            String id = response.jsonPath().getString("_id");
            updatePropertyFile("id", id);
        } catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";

            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}