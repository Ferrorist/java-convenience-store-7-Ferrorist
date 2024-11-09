package store.controller;

import java.util.List;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.response.PaymentProductResponse;
import store.model.dto.response.PurchaseResponse;
import store.service.PaymentService;

public class PaymentController {

    private final PaymentService paymentService = new PaymentService();

    public List<PaymentProductResponse> generatePaymentProductResponses(final List<PurchaseResponse> responses) {
        return paymentService.generatePaymentProductResponses(responses);
    }

    public List<PaymentFreeResponse> generatePaymentFreeResponses(final List<PurchaseResponse> responses) {
        return paymentService.generatePaymentFreeResponses(responses);
    }

    public PaymentPriceResponse generatePaymentPriceResponse(final List<PurchaseResponse> responses) {
        PaymentPriceResponse response = paymentService.generatePaymentPriceResponse(responses);
        paymentService.updateProductsStock(responses);
        return response;
    }
}
