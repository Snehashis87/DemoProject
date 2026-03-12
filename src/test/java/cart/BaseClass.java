package cart;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.BeforeClass;

import io.restassured.response.Response;

public class BaseClass {

    public static String jwtToken;
    public static String shopperID;
    public static int productId;
    public static int quantity;
    public static int itemID;

    @BeforeClass
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

        res.then()
                .assertThat()
                .statusCode(200);

        shopperID = res.jsonPath().getString("data.userId");
        jwtToken = res.jsonPath().getString("data.jwtToken");
    }
}
