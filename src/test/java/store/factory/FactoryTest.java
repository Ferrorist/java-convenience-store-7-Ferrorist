package store.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import store.model.Promotion;

public class FactoryTest {
    @Test
    void GeneratePromotionTest() {
        Assertions.assertEquals(
                new Promotion("A", 1, 2, "B", "C"),
                PromotionFactory.createPromotion("A, 1, 2, B, C")
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
