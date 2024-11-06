package util;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    private final String productionPromotionFilePath = "src/main/resources/promotions.md";

    @Test
    void readMarkDownFileTest() {
        Assertions.assertDoesNotThrow(() -> {
            MarkDownUtils.readMarkDownFile(productionPromotionFilePath);
        });
    }

    @Test
    void StringSplitListTest() {
        Assertions.assertEquals(List.of("name", "buy", "get"), StringUtils.splitLineToList("name     ,    buy    ,get      "));
    }

    @Test
    void StringSplitArrayTest() {
        Assertions.assertArrayEquals(new String[]{"name", "buy", "get"}, StringUtils.splitLinetoArray("name     ,    buy    ,get      "));
    }
}
