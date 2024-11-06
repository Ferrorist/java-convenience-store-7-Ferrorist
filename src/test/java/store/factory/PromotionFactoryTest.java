package store.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.Promotion;

public class PromotionFactoryTest {
    @Test
    void GeneratePromotionTest() {
        Assertions.assertEquals(
                new Promotion("A", 1, 2, "2000-01-01", "2000-02-01"),
                PromotionFactory.createPromotion("A, 1, 2, 2000-01-01, 2000-02-01")
                );
    }

    @Test
    void IllegalArugmentExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PromotionFactory.createPromotion("A, 1, 2, B");
        });
    }

    @Test
    void NumberFormatExceptionTest() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            PromotionFactory.createPromotion("A, B, C, D, E");
        });
    }
}
