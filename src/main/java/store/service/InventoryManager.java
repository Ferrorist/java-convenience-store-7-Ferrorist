package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.factory.ProductFactory;
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
    private Map<String, Promotion> promotionsByNames;

    private InventoryManager() {
        products = null;
        promotions = null;
        promotionsByNames = null;
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public List<Product> getProducts() {
        initProducts();
        return products;
    }

    public List<Promotion> getPromotions() {
        initPromotions();
        return promotions;
    }

    public void addProduct (Product product) {
        initProducts();
        products.add(product);
    }

    public void addPromotion (Promotion promotion) {
        initPromotions();
        promotions.add(promotion);
        promotionsByNames.put(promotion.getName(), promotion);
    }

    public Promotion searchPromotionByName(String promotionName) {
        initPromotions();
        Promotion promotion = promotionsByNames.getOrDefault(promotionName, null);
        if(!(promotionName == null || promotionName.equals("null")) && promotion == null)   {
            System.out.println("[ERROR] 찾을 수 없는 프로모션 입니다.");
        }
        return promotion;
    }

    private void initPromotions() {
        if (promotions == null) {
            promotions = new ArrayList<>();
            promotionsByNames = new HashMap<>();
            List<String> promotionLines = MarkDownUtils.readMarkDownFile(PROMOTIONS_FILE_PATH);
            for (String line : promotionLines) {
                Promotion promotion = PromotionFactory.createPromotion(line);
                promotions.add(promotion);
                promotionsByNames.put(promotion.getName(), promotion);
            }
        }
    }

    private void initProducts() {
        if (products == null) {
            products = new ArrayList<>();
            List<String> productLines = MarkDownUtils.readMarkDownFile(PRODUCTS_FILE_PATH);
            for (String line : productLines) {
                Product product = ProductFactory.createProduct(line);
                products.add(product);
            }
        }
    }
}