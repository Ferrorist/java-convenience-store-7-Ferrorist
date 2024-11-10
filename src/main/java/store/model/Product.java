package store.model;

import java.util.Objects;
import util.StringUtils;

public class Product implements Comparable<Product> {
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

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPriceString() {
        return StringUtils.convertToDecimalFormat(price);
    }

    public int getQuantity() {
        return quantity;
    }

    public String getQuantitytoString() {
        if(quantity > 0) {
            return Integer.toString(quantity) + "개";
        }
        return "재고 없음";
    }

    public Promotion getProductPromotion() {
        return productPromotion;
    }

    public String getProductPromotionName() {
        if (productPromotion == null) {
            return "";
        }
        return productPromotion.getName();
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
        return Objects.hash(name, price, productPromotion);
    }


    @Override
    public int compareTo(Product o) {
        if (this.name.equals(o.name)) {
            return Integer.compare(this.calcPriority(), o.calcPriority());
        }
        return this.name.compareTo(o.name);
    }

    private int calcPriority() {
        int priority = 2;
        if(this.productPromotion != null) {
            priority = 1;
        }
        if(this.quantity <= 0) {
            priority += 2;
        }
        return priority;
    }
}
