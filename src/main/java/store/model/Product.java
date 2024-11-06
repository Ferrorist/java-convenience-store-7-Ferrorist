package store.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return this.hashCode() == product.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, productPromotion);
    }
}
