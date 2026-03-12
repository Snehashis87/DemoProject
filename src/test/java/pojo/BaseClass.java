package pojo;

import static io.restassured.RestAssured.given;

//import java.util.HashMap;

import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;

public class BaseClass {

	public String jwtToken;
	public String shopperID;
	public int productId = 55;

	@BeforeClass
	public void LogInTest() {

//        HashMap<String, Object> bodyMap = new HashMap<>();
//
//        bodyMap.put("email", "snehashismandal43@gmail.com");
//        bodyMap.put("password", "Snehashis2026@");
//        bodyMap.put("role", "SHOPPER");

		ShopperLoginPojo loginBody = new ShopperLoginPojo("snehashismandal43@gmail.com", "Snehashis2026@", "SHOPPER");

		Response res = given().relaxedHTTPSValidation().contentType("application/json").body(loginBody)

				.when().post("https://www.shoppersstack.com/shopping/users/login");

		res.then().assertThat().statusCode(200).log().all();

		shopperID = res.jsonPath().getString("data.userId");
        System.out.println("ShopperId: " + shopperID);

		jwtToken = res.jsonPath().getString("data.jwtToken");
        System.out.println("Bearer Token: " + jwtToken);
	}
}
