package pojo;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ShopperLogin extends BaseClass {

//	@Test
//	public void loginTest() {
//		ShopperLoginPojo data = new ShopperLoginPojo("snehashismandal43@gmail.com", "Snehashis2026@", "SHOPPER");
//		Response res = given()
//				.relaxedHTTPSValidation()
//				.contentType("application/json")
//				.body(data)
//
//				.when()
//				.post("https://www.shoppersstack.com/shopping/users/login");
//
//		res.then().log().all();
//	}
	 @Test
    public void getShopperProfile() {

        Response res = given()
                .relaxedHTTPSValidation()
                .auth().oauth2(jwtToken)

        .when()
                .get("https://www.shoppersstack.com/shopping/shoppers/" + shopperID);

        res.then()
                .statusCode(200)
                .log().all();
    }
}
