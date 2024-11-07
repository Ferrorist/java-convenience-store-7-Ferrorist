package store.view;

import java.text.DecimalFormat;
import store.model.Product;
import store.service.ProductManager;

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
            stringBuilder.append(new DecimalFormat("###,###").format(product.getPrice())).append("원 ");
            stringBuilder.append(product.getQuantitytoString()).append(" ");
            stringBuilder.append(product.getProductPromotionName()).append("\n");
        }

        return stringBuilder.toString();
    }
}
