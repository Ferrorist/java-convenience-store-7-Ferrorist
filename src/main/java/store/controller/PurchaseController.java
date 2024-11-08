package store.controller;

import java.util.List;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PaymentProductResponse;
import store.model.dto.response.PurchaseResponse;
import store.service.PurchaseService;

public class PurchaseController {

    private final PurchaseService purchaseService = new PurchaseService();
    public void validatePurchaseRequest(String input) throws IllegalArgumentException {
        purchaseService.validatePurchaseRequest(input);
    }

    public List<PurchaseRequest> getPurchaseRequests(String input) throws IllegalArgumentException {
        return purchaseService.getPurchaseRequests(input);
    }

    public List<PurchaseResponse> progressPayment(List<PurchaseRequest> requests) throws RuntimeException {
        return purchaseService.progressPayment(requests);
    }

    public List<PaymentProductResponse> generatePaymentProductResponses(final List<PurchaseResponse> responses) {
        return purchaseService.generatePaymentProductResponses(responses);
    }

    public List<PaymentFreeResponse> generatePaymentFreeResponses(final List<PurchaseResponse> responses) {
        return purchaseService.generatePaymentFreeResponses(responses);
    }

    public PaymentPriceResponse generatePaymentPriceResponse(final List<PurchaseResponse> responses) {
        PaymentPriceResponse response = purchaseService.generatePaymentPriceResponse(responses);
        purchaseService.updateProductsStock(responses);
        return response;
    }
}
