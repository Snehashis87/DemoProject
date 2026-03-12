package shopper;

import java.util.HashMap;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

import org.testng.annotations.Test;

public class LoginTest {
	String jwtToken;
	String shopperID;

	@Test
	public void LogInTest() {
		HashMap<String, Object> bodyMap = new HashMap<>();
	
	    bodyMap.put("email", "snehashismandal43@gmail.com");
	    bodyMap.put("password", "Snehashis2026@");
	    bodyMap.put("role", "SHOPPER");
	    
	    Response res = given()
	    		.relaxedHTTPSValidation()
	    		.contentType("application/json")
            .body(bodyMap)

            .when()
            .post("https://www.shoppersstack.com/shopping/users/login");
            
//            res.then()
//            .assertThat().statusCode(200)
//            .log().all();
//            
            
        shopperID = res.jsonPath().getString("data.userId");
	    System.out.println("shopperId is nothing but userId "+shopperID);
	    jwtToken = res.jsonPath().getString("data.jwtToken");
	    System.out.println("Bearer Token" + jwtToken);
            
	}
	
	@Test(dependsOnMethods = "LogInTest")
	public void loginAuthTest() {
			
			Response res = given()
			.contentType("application/json")
			.relaxedHTTPSValidation()
			.baseUri("https://www.shoppersstack.com/shopping")
			.auth().oauth2(jwtToken)
			.pathParam("shopperId", shopperID)
			.baseUri("https://www.shoppersstack.com/shopping")
			.when()
			.get("/shoppers/{shopperId}");
			
//			.then()
//			.assertThat().statusCode(200)
//			.log().all();
			
			System.out.println(res.prettyPrint());
		}

	
}
