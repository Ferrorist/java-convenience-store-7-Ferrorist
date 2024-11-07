package store.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.factory.ProductFactory;
import store.model.Product;
import util.MarkDownUtils;

public class ProductManager {

    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static ProductManager instance;

    private Map<String, List<Product>> productsByName;
    private Map<String, Integer> stockByName;

    private ProductManager() {
        productsByName = null;
        stockByName = null;
    }

    public static ProductManager getInstance() {
        if (instance == null) {
           instance = new ProductManager();
        }
        return instance;
    }

    public List<Product> getProductByName(String productName) {
        initProducts();
        return productsByName.getOrDefault(productName, null);
    }

    public List<Product> getProducts() {
        initProducts();
        return generateProductList();
    }

    public int getProductStockByName(String productName) {
        initProducts();
        return stockByName.getOrDefault(productName, 0);
    }

    private void initProducts() {
        if (productsByName == null) {
            productsByName = new HashMap<>();
            stockByName = new HashMap<>();
            List<String> productLines = MarkDownUtils.readMarkDownFile(PRODUCTS_FILE_PATH);
            for (String line : productLines) {
                Product product = ProductFactory.createProduct(line);
                applyProduct(product);
            }
        }
    }

    private void applyProduct(Product product) {
        if (productsByName != null && stockByName != null) {
            registerProduct(product);
            registerProductStock(product);
        }
    }

    private void registerProduct(Product product) {
        List<Product> products = productsByName.getOrDefault(product.getName(), new ArrayList<>());
        if(products.isEmpty()) {
            products.add(new Product(product.getName(), product.getPrice(), 0, null));
        }
        if(!insertProductToList(products, product)) {
            products.add(product);
        }
        productsByName.put(product.getName(), products);
    }

    private boolean insertProductToList(List<Product> products, Product product) {
        for (Product value : products) {
            if (value.equals(product)) {
                int quantity = value.getQuantity() + product.getQuantity();
                value.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    private void registerProductStock(Product product) {
        int quantity = stockByName.getOrDefault(product.getName(), 0);
        stockByName.put(product.getName(), quantity + product.getQuantity());
    }

    private List<Product> generateProductList() {
        List<Product> products = new ArrayList<>();
        for(Map.Entry<String, List<Product>> entry : productsByName.entrySet()) {
            products.addAll(entry.getValue());
        }
        Collections.sort(products);
        return products;
    }


}
