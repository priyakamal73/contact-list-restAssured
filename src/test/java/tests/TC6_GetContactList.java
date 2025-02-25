package tests;

import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.reportInfo;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TC6_GetContactList extends BaseClass {
    Response response;
    Properties prop = getProp();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Get Contact List");
    }

    @Test(description = "Get the contact list")
    public void getContactList() {
        try {
            String token = prop.getProperty("token");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .get("contacts");

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));

            reportInfo.logPass(test, "Contact list retrieved successfully", responseBody);

            String contact_id = response.jsonPath().getString("[0]._id");
            updatePropertyFile("contact_id", contact_id);
        } catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";

            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}