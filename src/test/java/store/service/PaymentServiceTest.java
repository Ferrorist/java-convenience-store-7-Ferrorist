package store.service;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.response.PurchaseResponse;

public class PaymentServiceTest {

    private static PurchaseService purchaseService;
    private static PaymentService paymentService;

    @Test
    void PaymentResponseTest() {
        List<PurchaseRequest> requests = purchaseService.getPurchaseRequests("[사이다-3]");
        List<PurchaseResponse> responses = (List<PurchaseResponse>) purchaseService.generatePurchaseResponses(requests);
        Assertions.assertTrue(paymentService.generatePaymentPriceResponse(responses) instanceof PaymentPriceResponse);
    }

    @BeforeAll
    static void beforeAll() {
        purchaseService = new PurchaseService();
        paymentService = new PaymentService();
    }
}
