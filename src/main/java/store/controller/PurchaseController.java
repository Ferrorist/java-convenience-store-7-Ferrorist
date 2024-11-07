package store.controller;

import java.util.List;
import store.model.dto.PurchaseRequest;
import store.service.PurchaseService;

public class PurchaseController {

    private final PurchaseService purchaseService = new PurchaseService();
    public boolean validatePurchaseRequest(String input) throws IllegalArgumentException {
        return purchaseService.validatePurchaseRequest(input);
    }

    public List<PurchaseRequest> getPurchaseRequests(String input) throws IllegalArgumentException {
        return purchaseService.getPurchaseRequests(input);
    }
}
