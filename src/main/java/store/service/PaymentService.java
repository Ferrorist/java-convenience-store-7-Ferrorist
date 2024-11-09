package store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.response.PaymentProductResponse;
import store.model.dto.response.PurchaseResponse;
import store.service.manager.ProductManager;
import store.view.InputView;

public class PaymentService {

    private static final ProductManager productManager = ProductManager.getInstance();

    public PaymentPriceResponse generatePaymentPriceResponse(final List<PurchaseResponse> responses) {
        PaymentPriceResponse paymentPriceResponse = new PaymentPriceResponse();
        calcPurchaseAmount(paymentPriceResponse, responses);
        calcPromotionAmount(paymentPriceResponse, responses);
        paymentPriceResponse.setApplyMembership(calcMembershipAmount(paymentPriceResponse));
        paymentPriceResponse.calcActualAmount();
        return paymentPriceResponse;
    }

    private void calcPurchaseAmount(PaymentPriceResponse paymentPriceResponse, final List<PurchaseResponse> responses) {
        BigDecimal purchaseAmount = BigDecimal.ZERO;
        int totalQuantity = 0;
        for (PurchaseResponse response : responses) {
            BigDecimal productPrice = BigDecimal.valueOf(response.getProduct().getPrice());
            int quantity = response.getTotalQuantity();
            purchaseAmount = purchaseAmount.add(productPrice.multiply(new BigDecimal(quantity)));
            totalQuantity += quantity;
        }
        paymentPriceResponse.setPurchaseAmount(purchaseAmount);
        paymentPriceResponse.setPurchaseQuantity(totalQuantity);
    }

    private void calcPromotionAmount(PaymentPriceResponse paymentPriceResponse, final List<PurchaseResponse> responses) {
        BigDecimal promotionAmount = BigDecimal.ZERO;
        for (PurchaseResponse response : responses) {
            BigDecimal productPrice = BigDecimal.valueOf(response.getProduct().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(response.getFreeQuantity());
            promotionAmount = promotionAmount.add(productPrice.multiply(quantity));
        }
        paymentPriceResponse.setApplyPromotion(promotionAmount);
    }

    private BigDecimal calcMembershipAmount(PaymentPriceResponse response) {
        BigDecimal memberShip = BigDecimal.ZERO;
        if (InputView.ApplyMemberShip()) {
            int values = (int)(response.getPurchaseAmountIntValue() * 0.3);
            values = Math.min(values, 8000);
            values = values / 1000 * 1000;
            memberShip = new BigDecimal(values);
        }
        return memberShip;
    }

    public void updateProductsStock(List<PurchaseResponse> responses) {
        productManager.updateProductsStock(responses);
    }

    public List<PaymentProductResponse> generatePaymentProductResponses(List<PurchaseResponse> responses) {
        Map<String, Integer> purchaseResult = new HashMap<>();
        for (PurchaseResponse response : responses) {
            String productName = response.getProduct().getName();
            purchaseResult.put(productName, purchaseResult.getOrDefault(productName, 0) + response.getTotalQuantity());
        }
        List<PaymentProductResponse> paymentResponses = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : purchaseResult.entrySet()) {
            paymentResponses.add(generatePaymentProductResponse(entry));
        }
        return paymentResponses;
    }

    private PaymentProductResponse generatePaymentProductResponse(Map.Entry<String, Integer> entry) {
        int productPrice = productManager.getProductByName(entry.getKey()).getFirst().getPrice();
        int totalPrice = new BigDecimal(productPrice).multiply(new BigDecimal(entry.getValue())).intValue();
        return new PaymentProductResponse(entry.getKey(), entry.getValue(), totalPrice);
    }

    public List<PaymentFreeResponse> generatePaymentFreeResponses(List<PurchaseResponse> responses) {
        Map<String, Integer> purchaseResult = new HashMap<>();
        for (PurchaseResponse response : responses) {
            String productName = response.getProduct().getName();
            purchaseResult.put(productName, purchaseResult.getOrDefault(productName, 0) + response.getFreeQuantity());
        }
        List<PaymentFreeResponse> freeResponses = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : purchaseResult.entrySet()) {
            if(entry.getValue() > 0) {
                freeResponses.add(generatePaymentFreeResponse(entry));
            }
        }
        return freeResponses;
    }

    private PaymentFreeResponse generatePaymentFreeResponse(Entry<String, Integer> entry) {
        return new PaymentFreeResponse(entry.getKey(), entry.getValue());
    }
}
