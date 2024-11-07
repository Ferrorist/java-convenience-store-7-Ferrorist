package store.model.dto;

import store.model.Product;

public class PurchaseResponse {
    private Product product;
    private int totalQuantity;
    private int freeQuantity;
    private int notApplyPromotionQuantity;

    public PurchaseResponse(Product product, int totalQuantity, int freeQuantity, int notApplyPromotionQuantity) {
        this.product = product;
        this.totalQuantity = totalQuantity;
        this.freeQuantity = freeQuantity;
        this.notApplyPromotionQuantity = notApplyPromotionQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(int freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public int getNotApplyPromotionQuantity() {
        return notApplyPromotionQuantity;
    }

    public void setNotApplyPromotionQuantity(int notApplyPromotionQuantity) {
        this.notApplyPromotionQuantity = notApplyPromotionQuantity;
    }
}
