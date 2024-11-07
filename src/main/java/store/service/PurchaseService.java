package store.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.enums.ErrorCode;
import store.model.dto.PurchaseRequest;
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
}
