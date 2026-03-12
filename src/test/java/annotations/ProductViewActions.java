package annotations;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductViewActions extends BaseClass{
	
	@Test
	public void fetchAllProducts() throws IOException {
		Response res = given()
				.auth().oauth2(jwtToken)
				.relaxedHTTPSValidation()
				.contentType("application/json")
				.baseUri("https://www.shoppersstack.com/shopping")
				
				
				.when()
				.get("/products/alpha");
//		productId = res.jsonPath().getInt("data[0].productId");
//		res.prettyPrint();
		
		List<Integer> productIds = res.jsonPath().getList("data.productId");
//		List<String> productNames = res.jsonPath().getList("data.name");
		int productId = productIds.get(0);
//		String productName = productNames.get(0);
		System.out.println("Product id is : "+ productId);
//		System.out.println("Product name is : "+ productName);
		int quantity = res.jsonPath().getInt("data[0].quantity");
		
		res.then().assertThat().statusCode(200);
		
		FileWriter file = new FileWriter("response.json");
		file.write(res.asPrettyString());
		file.close();

	}
	

}
