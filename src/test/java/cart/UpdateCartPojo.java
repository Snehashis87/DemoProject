package cart;

public class UpdateCartPojo {
	private int productId;
    private int quantity;

    public UpdateCartPojo(int productId, int quantity) {
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
