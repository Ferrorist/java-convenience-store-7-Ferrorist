package store.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.enums.ErrorCode;
import store.model.Product;
import store.model.Promotion;
import store.model.dto.PurchaseRequest;
import store.model.dto.PurchaseResponse;
import store.view.InputView;
import util.InputCheckerUtils;
import util.StringUtils;

public class PurchaseService {

    private static final ProductManager productManager = ProductManager.getInstance();

    public boolean validatePurchaseRequest(String input) throws IllegalArgumentException {
        if (!InputCheckerUtils.checkPurchaseRequestInputFormat(input)) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
        String[] inputValues = StringUtils.splitLinetoArray(input);
        for (String value : inputValues) {
            InputCheckerUtils.checkPurchaseRequestFormat(value);
        }

        return true;
    }


    public List<PurchaseRequest> getPurchaseRequests(String input) throws IllegalArgumentException {
        List<String> requests = StringUtils.splitLineToList(input);
        List<PurchaseRequest> purchaseRequests = new ArrayList<>();

        for (String request : requests) {
            purchaseRequests.add(generatePurchaseRequest(request));
        }

        return purchaseRequests;
    }

    private PurchaseRequest generatePurchaseRequest(String inputRequest) {
        String[] inputValue = inputRequest.substring(1, inputRequest.length() - 1).split("-");
        if (productManager.getProductByName(inputValue[0]) == null) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_PRODUCT.getMessage());
        }
        int requestQuantity = Integer.parseInt(inputValue[1]);
        if (requestQuantity > productManager.getProductStockByName(inputValue[0])) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_REQUEST_QUANTITY.getMessage());
        }
        return new PurchaseRequest(inputValue[0], requestQuantity);
    }

    public List<PurchaseResponse> progressPayment(List<PurchaseRequest> requests) throws RuntimeException {
        List<PurchaseResponse> responses = new ArrayList<>();
        for (PurchaseRequest request : requests) {
            validateRequestStocks(request);
            List<Product> products = getSortedProducts(request.getProductName());
            responses.addAll(generatePurchaseResponse(products, request.getQuantity()));
        }

        return responses;
    }

    private void validateRequestStocks(PurchaseRequest request) {
        if (productManager.getProductByName(request.getProductName()) == null) {
            throw new IllegalArgumentException(ErrorCode.NOT_FOUND_PRODUCT.getMessage());
        }
        if (request.getQuantity() > productManager.getProductStockByName(request.getProductName())) {
            throw new IllegalArgumentException(ErrorCode.EXCEED_REQUEST_QUANTITY.getMessage());
        }
    }

    private List<Product> getSortedProducts(String productName) {
        List<Product> products = productManager.getProductByName(productName);
        Collections.sort(products);
        return products;
    }

    private List<PurchaseResponse> generatePurchaseResponse(List<Product> products, int requestQuantity) throws RuntimeException {
        Promotion productPromotion = products.getFirst().getProductPromotion();
        if (productPromotion == null) {
            return List.of(new PurchaseResponse(products.getFirst(), requestQuantity, 0, 0));
        }
        List<PurchaseResponse> responses = new ArrayList<>();
        int applyPromotionQuantity = Math.min(products.getFirst().getQuantity(), requestQuantity);
        PurchaseResponse currentResponse = calcApplyPromotionQuantity(products.getFirst(), productPromotion, applyPromotionQuantity);
        responses.add(currentResponse);
        if (requestQuantity != applyPromotionQuantity) {
            int notApplyPromotionQuantity = requestQuantity - applyPromotionQuantity;
            PurchaseResponse noPromotionResponse = new PurchaseResponse(products.get(1), notApplyPromotionQuantity, 0, notApplyPromotionQuantity);
            responses.add(noPromotionResponse);
        }

        checkContinuePurchase(responses);
        return responses;
    }

    private PurchaseResponse calcApplyPromotionQuantity (Product product, Promotion promotion, int quantity) {
        int totalPromotionQuantity = promotion.getBuyQuantity() + promotion.getFreeQuantity();
        int ApplyPromotionCount = quantity / totalPromotionQuantity;
        int leftQuantity = quantity - ApplyPromotionCount * totalPromotionQuantity;
        int freeQuantity = ApplyPromotionCount * promotion.getFreeQuantity();

        if (leftQuantity == 0) {
            return new PurchaseResponse(product, quantity, freeQuantity, 0);
        }
        if (leftQuantity >= promotion.getBuyQuantity()) {
            if (getAnotherFreeQuantity(product, quantity, totalPromotionQuantity - leftQuantity)) {
                quantity += (totalPromotionQuantity - leftQuantity);
                freeQuantity += (totalPromotionQuantity - leftQuantity);
            }
            return new PurchaseResponse(product, quantity, freeQuantity, 0);
        }
        return new PurchaseResponse(product, quantity, freeQuantity, leftQuantity);
    }

    private boolean getAnotherFreeQuantity(Product product, int requestQuantity, int leftQuantity) {
        if (product.getQuantity() >= requestQuantity + leftQuantity
            && InputView.applyPromotionMessage(product, leftQuantity)) {
            return true;
        }

        return false;
    }

    private void checkContinuePurchase(List<PurchaseResponse> responses) {
        int noPromotionQuantity = 0;
        for (PurchaseResponse response : responses) {
            noPromotionQuantity += response.getNotApplyPromotionQuantity();
        }
        if (noPromotionQuantity > 0
                && !InputView.haveToNoPromotion(responses.getFirst().getProduct(), noPromotionQuantity)) {
            throw new RuntimeException("현재 선택하신 상품의 구매를 취소했습니다. 결제를 중단합니다.");
        }
    }
}
