package store.view;

import java.util.List;
import store.model.Product;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.response.PaymentProductResponse;
import store.service.manager.ProductManager;
import util.StringUtils;

public class OutputView {
    public static void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public static void printProducts() {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        System.out.println(generateProductListString());
    }

    private static String generateProductListString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : ProductManager.getInstance().getProducts()) {
            stringBuilder.append("- ");
            stringBuilder.append(product.getName()).append(" ");
            stringBuilder.append(StringUtils.convertToDecimalFormat(product.getPrice())).append("원 ");
            stringBuilder.append(product.getQuantitytoString()).append(" ");
            stringBuilder.append(product.getProductPromotionName()).append("\n");
        }

        return stringBuilder.toString();
    }

    public static void printPaymentResult(List<PaymentProductResponse> productResponses, List<PaymentFreeResponse> freeResponses, PaymentPriceResponse priceResponse) {
        StringBuilder stringBuilder = new StringBuilder("===========W 편의점=============\n");
        printPaymentProductRespsonses(productResponses, stringBuilder);
        printPaymentFreeRespsonses(freeResponses, stringBuilder);
        printPaymentPriceResponse(priceResponse, stringBuilder);

        System.out.println(stringBuilder);
    }

    private static void printPaymentProductRespsonses(List<PaymentProductResponse> productResponses, StringBuilder stringBuilder) {
        stringBuilder.append("상품명\t\t수량\t금액").append("\n");
        for (PaymentProductResponse response : productResponses) {
            stringBuilder.append(response.getProductName()).append("\t\t");
            stringBuilder.append(response.getQuantity()).append("\t");
            stringBuilder.append(StringUtils.convertToDecimalFormat(response.getTotalPrice())).append("\n");
        }
    }

    private static void printPaymentFreeRespsonses(List<PaymentFreeResponse> freeResponses, StringBuilder stringBuilder) {
        stringBuilder.append("===========증\t정=============").append("\n");
        for (PaymentFreeResponse response : freeResponses) {
            stringBuilder.append(response.getProductName()).append("\t\t");
            stringBuilder.append(response.getQuantity()).append("\n");
        }
    }

    private static void printPaymentPriceResponse(PaymentPriceResponse response, StringBuilder stringBuilder) {
        stringBuilder.append("==============================").append("\n");
        stringBuilder.append("총구매액\t\t").append(response.getPurchaseQuantity()).append("\t");
        stringBuilder.append(StringUtils.convertToDecimalFormat(response.getPurchaseAmountIntValue())).append("\n");
        stringBuilder.append("행사할인\t\t\t").append("-");
        stringBuilder.append(StringUtils.convertToDecimalFormat(response.getApplyPromotionIntValue())).append("\n");
        stringBuilder.append("멤버십할인\t\t\t").append("-");
        stringBuilder.append(StringUtils.convertToDecimalFormat(response.getApplyMemberShipIntValue())).append("\n");
        stringBuilder.append("내실돈\t\t\t").append(StringUtils.convertToDecimalFormat(response.getActualAmountIntValue())).append("\n");
    }
}
