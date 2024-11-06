package store.model;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion productPromotion;


    public Product(String name, int price, int quantity, Promotion productPromotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productPromotion = productPromotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
