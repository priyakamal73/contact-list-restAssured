package tests;

import POJO.UpdatePartialContactDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import utils.reportInfo;

public class TC9_UpdateContact extends BaseClass {

    Response response;
    Properties prop = getProp();
    UpdatePartialContactDetails updatePartialContactDetails = new UpdatePartialContactDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Update Contact - Partial Update");
    }

    @Test(description = "Update first name in the contact details")
    public void updatePartialContactDetails() {
        try{
            String firstName = prop.getProperty("contact_firstname");

            updatePartialContactDetails.setFirstName(firstName);

            String token = prop.getProperty("token");
            String contact_id = prop.getProperty("contact_id");

            Response response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(updatePartialContactDetails)
                    .patch("contacts/" + contact_id);

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));
            String resEmail = response.jsonPath().getString("firstName");
            Assert.assertEquals(resEmail, prop.getProperty("contact_firstname"), "The first names are not the same");

            reportInfo.logPass(test, "Partial contact updated successfully", responseBody);
        }catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";

            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}