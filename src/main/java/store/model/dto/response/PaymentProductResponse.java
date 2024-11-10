package store.model.dto.response;

import util.StringUtils;

public class PaymentProductResponse {
    private String productName;
    private int quantity;
    private int totalPrice;

    public PaymentProductResponse(String productName, int quantity, int totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getTotalPriceString() {
        return StringUtils.convertToDecimalFormat(totalPrice);
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
