package store.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PurchaseResponse;

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

    @Test
    void progressPaymentTest() {
        Assertions.assertDoesNotThrow(() -> {
            List<PurchaseRequest> requests = purchaseService.getPurchaseRequests("[사이다-3]");
            purchaseService.generatePurchaseResponses(requests);
        });
    }

    @BeforeAll
    static void beforeAll() {
        purchaseService = new PurchaseService();
    }
}
