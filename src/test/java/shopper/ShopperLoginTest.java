package shopper;

import java.util.HashMap;
// HashMap is part of Java Collections framework.
// It stores data in key-value pairs.
// Here it will be used to create JSON request body dynamically.

import org.testng.annotations.Test;
// Test annotation comes from TestNG framework.
// It is used to mark a method as a test case.

import io.restassured.RestAssured;
// RestAssured class provides entry point for API testing.
// It contains methods like given(), when(), then().

public class ShopperLoginTest {

    @Test(priority = 1)
    public void loginTest() {

        // Creating HashMap to store request body parameters
        // Key -> JSON field name
        // Value -> Actual value sent to API
        HashMap<String, Object> bodyMap = new HashMap<>();

        bodyMap.put("email", "snehashismandal43@gmail.com");
        bodyMap.put("password", "Snehashis2026@");
        bodyMap.put("role", "SHOPPER");

        // REST Assured request building starts here
        RestAssured
        .given()  
        // given() is used to specify request specifications
        // Return Type: RequestSpecification

        .contentType("application/json")  
        // Specifies request content type
        // Here we are sending JSON data

        .relaxedHTTPSValidation()
        // Used to ignore SSL certificate validation
        // Useful when API has invalid SSL certificate

        .body(bodyMap)
        // Passing HashMap as request body
        // REST Assured automatically converts it to JSON

        .when()
        // when() indicates the action to be performed

        .post("https://www.shoppersstack.com/shopping/users/login")
        // post() sends HTTP POST request to given endpoint
        // Return Type: Response

        .then()
        // then() is used for response validation

        .assertThat().statusCode(200)
        // Validates that response status code is 200 (Success)

        .log().all();
        // Logs full response in console
    }


    @Test(priority = 2, invocationCount = 5, dependsOnMethods = "loginTest", enabled = true)
    public void fetchData() {

        RestAssured
        .given()

        .contentType("application/json")

        .auth().oauth2("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzbmVoYXNoaXNtYW5kYWw0M0BnbWFpbC5jb20gU0hPUFBFUiIsImV4cCI6MTc3MzE1NDA0NSwiaWF0IjoxNzczMTE4MDQ1fQ.mNcX78jhkOpzgPVmZEJxND_GQE6sCoS-lEr2wfOTE8Y")
        // oauth2() method adds Authorization header
        // Used when API requires JWT token authentication

        .relaxedHTTPSValidation()

        .when()

        .get("https://www.shoppersstack.com/shopping/shoppers/363295")
        // Sends GET request to fetch shopper data

        .then()

        .assertThat().statusCode(200)

        .log().all();
    }
}