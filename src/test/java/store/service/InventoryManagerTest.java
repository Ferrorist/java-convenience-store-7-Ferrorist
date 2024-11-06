package store.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.Promotion;
import util.MarkDownUtils;

public class InventoryManagerTest {

    private static InventoryManager inventoryManager;

    @Test
    void InitPromotionTest() {
        String promotionFilePath = "src/main/resources/promotions.md";
        Assertions.assertEquals(
                MarkDownUtils.readMarkDownFile(promotionFilePath).size(),
                inventoryManager.getPromotions().size()
        );
    }

    @BeforeAll
    static void beforeAll() {
        inventoryManager = InventoryManager.getInstance();
    }
}
