package store.service;

import util.StringUtils;

public class PurchaseService {

    public boolean validatePurchaseRequest(String input) throws NumberFormatException, IllegalArgumentException {
        String[] inputValues = StringUtils.splitLinetoArray(input);
        for (String value : inputValues) {
            checkPurchaseRequestFormat(value);
        }

        return true;
    }

    // 1. [ ]으로 감싸져야 함.
    // 2. (물품명)-(개수) 형태이어야 함.
    // 3. (개수)가 0 이상의 수
    private void checkPurchaseRequestFormat(String input)  {
        if (!(input.startsWith("[") && input.endsWith("]") && input.contains("-"))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        String[] values = input.substring(1, input.length() - 1).split("-");

        int requestQuantity = StringUtils.checkAndparseInt(values[1]);
        if(requestQuantity < 0) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }
}
