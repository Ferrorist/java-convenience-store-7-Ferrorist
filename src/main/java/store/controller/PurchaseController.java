package store.controller;

import java.util.List;
import store.model.dto.PurchaseRequest;
import store.service.PurchaseService;

public class PurchaseController {

    private final PurchaseService purchaseService = new PurchaseService();
    public void validatePurchaseRequest(String input) throws IllegalArgumentException {
        purchaseService.validatePurchaseRequest(input);
    }

    public List<PurchaseRequest> getPurchaseRequests(String input) throws IllegalArgumentException {
        return purchaseService.getPurchaseRequests(input);
    }

    public void progressPayment(List<PurchaseRequest> requests) throws RuntimeException {
        purchaseService.progressPayment(requests);
    }
}
