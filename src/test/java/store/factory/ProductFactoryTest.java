package store.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Promotion;
import store.service.PromotionManager;

public class ProductFactoryTest {

    private static PromotionManager promotionManager;

    @Test
    void GenerateProductTest() {
        Promotion promotion = promotionManager.searchPromotionByName("탄산2+1");
        Assertions.assertEquals(new Product("콜라", 1000, 10, promotion), ProductFactory.createProduct("콜라,1000,10,탄산2+1"));
    }

    @Test
    void GenerateProductExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           ProductFactory.createProduct("컵라면,1700,10,nulls");
        });
    }

    @BeforeAll
    static void beforeAll() {
        promotionManager = PromotionManager.getInstance();
    }
}
