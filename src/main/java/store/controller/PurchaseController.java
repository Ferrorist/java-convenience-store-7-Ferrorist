package store.controller;

import store.service.PurchaseService;

public class PurchaseController {

    private final PurchaseService purchaseService = new PurchaseService();
    public boolean validatePurchaseRequest(String input) throws IllegalArgumentException {
        return purchaseService.validatePurchaseRequest(input);
    }

}
