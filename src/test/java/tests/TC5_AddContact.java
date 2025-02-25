package tests;

import POJO.AddContactDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.reportInfo;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TC5_AddContact extends BaseClass {
    Response response;
    Properties prop = getProp();
    AddContactDetails addContactDetails = new AddContactDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Add Contact Details");
    }

    @Test(description = "Add contact details")
    public void addContactDetails() {
        try {
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

            addContactDetails.setFirstName(firstName);
            addContactDetails.setLastName(lastName);
            addContactDetails.setBirthdate(birthdate);
            addContactDetails.setEmail(email);
            addContactDetails.setPhone(phone);
            addContactDetails.setStreet1(street1);
            addContactDetails.setStreet2(street2);
            addContactDetails.setCity(city);
            addContactDetails.setStateProvince(stateProvince);
            addContactDetails.setPostalCode(postalCode);
            addContactDetails.setCountry(country);

            String token = prop.getProperty("token");

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(addContactDetails)
                    .post("contacts");

            String responseBody = response.getBody().asPrettyString();
            System.out.println(responseBody);

            Assert.assertEquals(response.getStatusCode(), 201, "The status code is not 201");
            Assert.assertTrue(response.getStatusLine().contains("Created"));

            reportInfo.logPass(test, "Contact details added successfully!", responseBody);

            String contact_id = response.jsonPath().getString("_id");
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