package tests;

import POJO.AddNewUserDetails;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.reportInfo;
import java.util.Properties;
import static io.restassured.RestAssured.given;

public class TC1_AddNewUser extends BaseClass {
    Response response;
    Properties prop = getProp();
    AddNewUserDetails addNewUserDetails = new AddNewUserDetails();

    @BeforeMethod
    public void startTest() {
        test = extent.createTest("Add New User");
    }

    @Test(description = "Create a new user")
    public void addNewUser() {
        String firstname = prop.getProperty("firstname");
        String lastname = prop.getProperty("lastname");
        String email = prop.getProperty("email");
        String password = prop.getProperty("password");

        addNewUserDetails.setFirstName(firstname);
        addNewUserDetails.setLastName(lastname);
        addNewUserDetails.setEmail(email);
        addNewUserDetails.setPassword(password);

        response = given()
                .header("Content-Type", "application/json")
                .body(addNewUserDetails)
                .post("users");

        String responseBody = response.getBody().asPrettyString();
        System.out.println(responseBody);

        try {
            Assert.assertEquals(response.getStatusCode(), 201, "Status code is not 201");
            Assert.assertTrue(response.getStatusLine().contains("Created"));

            reportInfo.logPass(test, "User created successfully", responseBody);

            String token = response.jsonPath().getString("token");
            updatePropertyFile("token", token);
        } catch (AssertionError e) {
            reportInfo.logFail(test, "Failed to create user: " + e.getMessage(), responseBody);
            throw e;
        }
    }
}