package store.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseServiceTest {

    private static PurchaseService purchaseService;
    @Test
    void validatePurchaseRequestTrueTest() {
        Assertions.assertTrue(purchaseService.validatePurchaseRequest("[사이다-3], [물-1]"));
    }

    @Test
    void validatePurchaseRequestFalseTest() {
        Assertions.assertFalse(purchaseService.validatePurchaseRequest("{사이다-3}, [물2]"));
    }

    @Test
    void checkProductStocksTest() {
        Assertions.assertTrue(purchaseService.checkProductStocks("[사이다-3]"));
    }

    @Test
    void checkProductStocksExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           purchaseService.checkProductStocks("[컵라면-100]");
        });
    }

    @BeforeAll
    static void beforeAll() {
        purchaseService = new PurchaseService();
    }
}
