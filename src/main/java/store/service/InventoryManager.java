package store.service;

import java.util.ArrayList;
import java.util.List;
import store.factory.PromotionFactory;
import store.model.Product;
import store.model.Promotion;
import util.MarkDownUtils;

public class InventoryManager {
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static InventoryManager instance;
    private List<Product> products;
    private List<Promotion> promotions;

    private InventoryManager() {
        products = new ArrayList<>();
        promotions = null;
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        if(promotions == null) {
            initPromotions();
        }

        return promotions;
    }

    public void addProduct (Product product) {
        products.add(product);
    }

    public void addPromotion (Promotion promotion) {
        if (promotions == null) {
            initPromotions();
        }
        promotions.add(promotion);
    }

    private void initPromotions() {
        if (promotions == null) {
            promotions = new ArrayList<>();
            List<String> promotionLines = MarkDownUtils.readMarkDownFile(PROMOTIONS_FILE_PATH);
            for (String line : promotionLines) {
                Promotion promotion = PromotionFactory.createPromotion(line);
                promotions.add(promotion);
            }
        }
    }
}