package cart;

import static io.restassured.RestAssured.given;

import java.io.FileWriter;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class CartTest extends BaseClass {

	@Test
	public void fetchAllProducts() throws Exception {

	    Response res = given()
	            .relaxedHTTPSValidation()
	            .contentType("application/json")
	            .auth().oauth2(jwtToken)
	            .baseUri("https://www.shoppersstack.com/shopping")

	    .when()
	            .get("/products/alpha");

	    List<Integer> productIds = res.jsonPath().getList("data.productId");

	    int randomIndex = (int)(Math.random() * productIds.size());
	    productId = productIds.get(randomIndex);

	    quantity = 1;

	    System.out.println("Random Product ID: " + productId);

	    FileWriter file = new FileWriter("response.json");
	    file.write(res.asPrettyString());
	    file.close();
	}
	@Test(dependsOnMethods = "fetchAllProducts")
	public void addProductToCart() {
		AddToCartPojo cart=new AddToCartPojo(productId, quantity);
			Response res=given()
		   .relaxedHTTPSValidation()
		   .contentType("application/json")
		   .pathParam("userId", shopperID)
		   .auth().oauth2(jwtToken)
		   .baseUri("https://www.shoppersstack.com/shopping")
		   .body(cart)
		   .when()
		   .post("/shoppers/{userId}/carts");
			
			res.then()
			   .assertThat().statusCode(201)
			   .log().all();
			
			itemID = res.jsonPath().getInt("data.itemId");
			System.out.println("Item ID: " + itemID);
			
			
	}
    @Test(dependsOnMethods = "addProductToCart")
    public void updateCart() {

    	UpdateCartPojo update = new UpdateCartPojo(productId, 2);

        Response res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .pathParam("userId", shopperID)
                .pathParam("itemId", itemID)
                .auth().oauth2(jwtToken)
                .baseUri("https://www.shoppersstack.com/shopping")
                .body(update)

        .when()
                .put("/shoppers/{userId}/carts/{itemId}");

        res.then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }

    @Test(dependsOnMethods = "updateCart")
    public void deleteProductFromCart() {

        Response res = given()
                .relaxedHTTPSValidation()
                .pathParam("userId", shopperID)
                .pathParam("productId", productId)
                .auth().oauth2(jwtToken)
                .baseUri("https://www.shoppersstack.com/shopping")

        .when()
                .delete("/shoppers/{userId}/carts/{productId}");

        res.then()
                .statusCode(200)
                .log().all();
    }
    
  //  @Test(priority = 0)
    public void deleteAllCartItems() {

        Response res = given()
                .relaxedHTTPSValidation()
                .auth().oauth2(jwtToken)
                .pathParam("userId", shopperID)
                .baseUri("https://www.shoppersstack.com/shopping")

        .when()
                .get("/shoppers/{userId}/carts");

        List<Integer> itemIds = res.jsonPath().getList("data.itemId");

        if(itemIds == null || itemIds.isEmpty()) {
            System.out.println("Cart already empty !!!");
            return;
        }

        for(Integer id : itemIds) {

            given()
                .relaxedHTTPSValidation()
                .auth().oauth2(jwtToken)
                .pathParam("userId", shopperID)
                .pathParam("itemId", id)
                .baseUri("https://www.shoppersstack.com/shopping")

            .when()
                .delete("/shoppers/{userId}/carts/{itemId}")

            .then()
                .log().all();
        }

        System.out.println("All cart items deleted successfully ....");
    }
}
