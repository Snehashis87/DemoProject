package cart;

public class AddToCartPojo {
	private int quantity;
	private int productId;

    public AddToCartPojo(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
