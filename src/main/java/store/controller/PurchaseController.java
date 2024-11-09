package store.controller;

import java.util.List;
import store.model.dto.request.PurchaseRequest;
import store.service.PurchaseService;

public class PurchaseController {

    private final PurchaseService purchaseService = new PurchaseService();
    public void validatePurchaseRequest(String input) throws IllegalArgumentException {
        purchaseService.validatePurchaseRequest(input);
    }

    public List<PurchaseRequest> getPurchaseRequests(String input) throws IllegalArgumentException {
        return purchaseService.getPurchaseRequests(input);
    }

    public Object generatePurchaseResponses(List<PurchaseRequest> requests) throws RuntimeException {
        try {
            return purchaseService.generatePurchaseResponses(requests);
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            return Boolean.FALSE;
        }
    }
}
