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
        try {
            Assertions.assertEquals(promotions.get(randomIdx),
                    inventoryManager.searchPromotionByName(promotions.get(randomIdx).getName()));
        } catch (ClassNotFoundException exception) {
            Assertions.fail(exception.getMessage());
        }
    }

    @Test
    void searchPromotionByNameExceptionTest() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> {
           inventoryManager.searchPromotionByName(null);
        });
    }

    @BeforeAll
    static void beforeAll() {
        inventoryManager = InventoryManager.getInstance();
    }
}
