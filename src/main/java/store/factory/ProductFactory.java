package store.factory;

import store.model.Product;
import store.model.Promotion;
import store.service.manager.PromotionManager;
import util.StringUtils;

public class ProductFactory {

    private static final PromotionManager promotionManager = PromotionManager.getInstance();

    public static Product createProduct (String input) throws IllegalArgumentException {
        return createProduct(StringUtils.splitLinetoArray(input));
    }
    public static Product createProduct (String[] inputs) throws IllegalArgumentException {
        if(checkInputArgument(inputs)) {
            String productName = inputs[0];
            int productPrice = StringUtils.checkAndparseInt(inputs[1]);
            int productQuantity = StringUtils.checkAndparseInt(inputs[2]);
            Promotion promotion = promotionManager.searchPromotionByName(inputs[3]);
            return new Product(productName, productPrice, productQuantity, promotion);
        }

        return null;
    }

    private static boolean checkInputArgument(String[] inputs) {
        if (inputs.length != Product.class.getDeclaredFields().length
        || (!inputs[3].strip().equals("null")
                && promotionManager.searchPromotionByName(inputs[3].strip()) == null)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않는 상품 입력값입니다.");
        }

        return true;
    }
}
