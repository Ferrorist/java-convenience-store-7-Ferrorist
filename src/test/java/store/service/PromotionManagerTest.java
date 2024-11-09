package store.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.model.Promotion;
import store.service.manager.PromotionManager;
import util.MarkDownUtils;

public class PromotionManagerTest {

    private static PromotionManager promotionManager;

    @Test
    void InitPromotionTest() {
        String promotionFilePath = "src/main/resources/promotions.md";
        Assertions.assertEquals(
                MarkDownUtils.readMarkDownFile(promotionFilePath).size(),
                promotionManager.getPromotions().size()
        );
    }

    @Test
    void searchPromotionByNameTest() {
        List<Promotion> promotions = promotionManager.getPromotions();
        int randomIdx = Randoms.pickNumberInRange(0, promotions.size()-1);
        Assertions.assertEquals(promotions.get(randomIdx),
                promotionManager.searchPromotionByName(promotions.get(randomIdx).getName()));
    }

    @Test
    void searchPromotionByNameExceptionTest() {
        Assertions.assertNull(promotionManager.searchPromotionByName(null));
    }

    @BeforeAll
    static void beforeAll() {
        promotionManager = PromotionManager.getInstance();
    }
}
