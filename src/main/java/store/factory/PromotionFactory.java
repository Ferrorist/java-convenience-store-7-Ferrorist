package store.factory;

import store.model.Promotion;
import util.StringUtils;

public class PromotionFactory {
    public static Promotion createPromotion(String input) throws IllegalArgumentException {
        return createPromotion(StringUtils.splitLinetoArray(input));
    }

    public static Promotion createPromotion(String[] inputs) throws IllegalArgumentException {
        if (checkInputArgument(inputs)) {
            int buyQuantity = checkAndparseInt(inputs[1]);
            int freeQuantity = checkAndparseInt(inputs[2]);

            return new Promotion(inputs[0], buyQuantity, freeQuantity, inputs[3], inputs[4]);
        }
        return null;
    }

    private static boolean checkInputArgument(String[] inputs) {
        if ((inputs.length != Promotion.class.getDeclaredFields().length) ||
                !(StringUtils.checkDatePattern(inputs[3]) && StringUtils.checkDatePattern(inputs[4]))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않는 프로모션 입력값입니다.");
        }

        return true;
    }

    private static int checkAndparseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("[ERROR] 올바르지 않는 프로모션 입력값입니다.");
        }
    }
}
