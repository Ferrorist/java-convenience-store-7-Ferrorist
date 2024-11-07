package store.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class PurchaseServiceTest {

    private static PurchaseService purchaseService;
    @Test
    void validatePurchaseRequestTrueTest() {
        Assertions.assertTrue(purchaseService.validatePurchaseRequest("[사이다-3], [물-1]"));
    }

    @Test
    void validatePurchaseRequestExceptionSingleTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           purchaseService.validatePurchaseRequest("[사이다3][물-2]");
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/validatePurchaseRequestExceptionTestFile.csv")
    void validatePurchaseRequestExceptionTest(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            purchaseService.validatePurchaseRequest(input);
        });
    }

    @Test
    void checkProductStocksTest() {
        Assertions.assertDoesNotThrow(() -> {
            purchaseService.getPurchaseRequests("[사이다-3]");
        });
    }

    @Test
    void checkProductStocksExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           purchaseService.getPurchaseRequests("[컵라면-100]");
        });
    }

    @BeforeAll
    static void beforeAll() {
        purchaseService = new PurchaseService();
    }
}
