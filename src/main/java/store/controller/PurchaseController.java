package store.controller;

import java.util.List;
import store.model.dto.PaymentResponse;
import store.model.dto.PurchaseRequest;
import store.model.dto.PurchaseResponse;
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
}
