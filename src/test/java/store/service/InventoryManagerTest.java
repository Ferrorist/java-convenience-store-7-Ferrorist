package store.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void searchPromotionByNameTest() {
        List<Promotion> promotions = inventoryManager.getPromotions();
        int randomIdx = Randoms.pickNumberInRange(0, promotions.size()-1);
        Assertions.assertEquals(promotions.get(randomIdx),
                    inventoryManager.searchPromotionByName(promotions.get(randomIdx).getName()));
    }

    @Test
    void searchPromotionByNameExceptionTest() {
        Assertions.assertNull(inventoryManager.searchPromotionByName(null));
    }

    @Test
    void InitProductsTest() {
        String productFilePath = "src/main/resources/products.md";
        Assertions.assertEquals(
                MarkDownUtils.readMarkDownFile(productFilePath).size(),
                inventoryManager.getProducts().size()
        );
    }

    @BeforeAll
    static void beforeAll() {
        inventoryManager = InventoryManager.getInstance();
    }
}
