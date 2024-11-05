package utils;

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
    void StringSplitTest() {
        Assertions.assertEquals(List.of("name", "buy", "get"), Arrays.asList(StringUtils.splitLine("  name, buy  ,get     ")));
    }
}
