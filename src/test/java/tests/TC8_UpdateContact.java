package tests;

import POJO.UpdateFullContactDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Properties;
import static io.restassured.RestAssured.given;
import utils.reportInfo;

public class TC8_UpdateContact extends BaseClass {
    Response response;
    Properties prop = getProp();
    UpdateFullContactDetails updateContactDetails = new UpdateFullContactDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Update Contact - Full Update");
    }

    @Test(description = "Update full contact details")
    public void updateContact() {
        try{
            String firstName = prop.getProperty("contact_firstname");
            String lastName = prop.getProperty("contact_lastname");
            String birthdate = prop.getProperty("contact_birthdate");
            String email = prop.getProperty("contact_email");
            String phone = prop.getProperty("contact_phone");
            String street1 = prop.getProperty("street1");
            String street2 = prop.getProperty("street2");
            String city = prop.getProperty("city");
            String stateProvince = prop.getProperty("stateProvince");
            String postalCode = prop.getProperty("postalCode");
            String country = prop.getProperty("country");

            updateContactDetails.setFirstName(firstName);
            updateContactDetails.setLastName(lastName);
            updateContactDetails.setBirthdate(birthdate);
            updateContactDetails.setEmail(email);
            updateContactDetails.setPhone(phone);
            updateContactDetails.setStreet1(street1);
            updateContactDetails.setStreet2(street2);
            updateContactDetails.setCity(city);
            updateContactDetails.setStateProvince(stateProvince);
            updateContactDetails.setPostalCode(postalCode);
            updateContactDetails.setCountry(country);

            String token = prop.getProperty("token");
            String contact_id = prop.getProperty("contact_id");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(updateContactDetails)
                    .put("contacts/" + contact_id);

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 200, "The status code is not 200");
            Assert.assertTrue(response.getStatusLine().contains("OK"));
            String resEmail = response.jsonPath().getString("email");
            Assert.assertEquals(resEmail,prop.getProperty("contact_email"),"The emails are not the same");

            reportInfo.logPass(test, "Full contact updated successfully", responseBody);

            String updatedContactId = response.jsonPath().getString("_id");
            updatePropertyFile("contact_id", updatedContactId);
        }catch (AssertionError e) {
            String errorMessage = (response != null && response.jsonPath().getString("message") != null)
                    ? response.jsonPath().getString("message")
                    : "No response message received";

            reportInfo.logFail(test, "Test failed: " + errorMessage, response != null ? response.getBody().asPrettyString() : "No response body received");
            throw e;
        }
    }
}