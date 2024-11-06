package store.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Promotion;
import store.service.InventoryManager;

public class ProductFactoryTest {

    private static final InventoryManager inventoryManager = InventoryManager.getInstance();

    @Test
    void GenerateProductTest() {
        Promotion promotion = inventoryManager.searchPromotionByName("탄산2+1");
        Assertions.assertEquals(new Product("콜라", 1000, 10, promotion), ProductFactory.createProduct("콜라,1000,10,탄산2+1"));
    }

    @Test
    void GenerateProductExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           ProductFactory.createProduct("컵라면,1700,10,nulls");
        });
    }

}
