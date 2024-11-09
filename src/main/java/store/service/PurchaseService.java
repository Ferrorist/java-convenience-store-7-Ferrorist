package store.service;

import java.util.*;
import store.enums.ErrorCode;
import store.model.Product;
import store.model.Promotion;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PurchaseResponse;
import store.service.manager.ProductManager;
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

    public List<PurchaseResponse> generatePurchaseResponses(List<PurchaseRequest> requests) throws IllegalArgumentException {
        List<PurchaseResponse> responses = new ArrayList<>();
        for (PurchaseRequest request : requests) {
            validateRequestStocks(request);
            List<Product> products = getSortedProducts(request.getProductName());
            responses.addAll(이름뭘로짓지(products, request));
        }
        checkContinuePurchase(responses);
        return responses;
    }

    public List<PurchaseResponse> 이름뭘로짓지(List<Product> products, PurchaseRequest request) {
        Promotion productPromotion = products.getFirst().getProductPromotion();
        if (productPromotion == null) {
            return List.of(new PurchaseResponse(products.getFirst(), request.getQuantity(), 0, 0));
        }

        return getPromotionPurchaseResponses(products, request);
    }

    public List<PurchaseResponse> getPromotionPurchaseResponses(List<Product> products, PurchaseRequest request) {
        Promotion promotion = products.getFirst().getProductPromotion();
        List<PurchaseResponse> responses = new ArrayList<>();
        int acceptPromotionQuantity = Math.min(products.getFirst().getQuantity(), request.getQuantity());
        responses.add(generatePromotionPurchaseResponse(products.getFirst(), promotion, acceptPromotionQuantity));
        if (request.getQuantity() > acceptPromotionQuantity) {
            int notApplyPromotionQuantity = request.getQuantity() - acceptPromotionQuantity;
            responses.add(new PurchaseResponse(products.get(1), notApplyPromotionQuantity, 0, notApplyPromotionQuantity));
        }

        return responses;
    }

    public PurchaseResponse generatePromotionPurchaseResponse(Product product, Promotion promotion, int quantity) {
        int promotionQuantity = promotion.getBuyQuantity() + promotion.getFreeQuantity();
        int ApplyPromotionCount = quantity / promotionQuantity;
        int leftQuantity = quantity - ApplyPromotionCount * promotionQuantity;
        int freeQuantity = ApplyPromotionCount * promotion.getFreeQuantity();

        if (leftQuantity == 0) {
            return new PurchaseResponse(product, quantity, freeQuantity, 0);
        }

        return calcQuantityToGenerateResponse(product, promotion, quantity, leftQuantity, promotionQuantity, freeQuantity);
    }

    private PurchaseResponse calcQuantityToGenerateResponse(Product product, Promotion promotion, int quantity, int leftQuantity, int promotionQuantity, int freeQuantity) {
        if (leftQuantity >= promotion.getBuyQuantity()) {
            if (getAnotherFreeQuantity(product, quantity, promotionQuantity - leftQuantity)) {
                quantity += (promotionQuantity - leftQuantity);
                freeQuantity += (promotionQuantity - leftQuantity);
            }
            return new PurchaseResponse(product, quantity, freeQuantity, 0);
        }
        return new PurchaseResponse(product, quantity, freeQuantity, leftQuantity);
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
