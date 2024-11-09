package store;

import java.util.List;
import store.controller.PurchaseController;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PaymentProductResponse;
import store.model.dto.response.PurchaseResponse;
import store.view.InputView;
import store.view.OutputView;
import util.RecursiveUtils;

public class Application {

    private static final PurchaseController purchaseController = new PurchaseController();

    public static void main(String[] args) {
        startProgram();
        진행();
    }

    private static void startProgram() {
        OutputView.printWelcomeMessage();
    }

    private static void 진행() {
        boolean continuedProcess;
        do {
            Object selectProductsResult = selectProducts();
            if (!(selectProductsResult instanceof Boolean)) {
                progressPayment((List<PurchaseResponse>) selectProductsResult);
            }
            continuedProcess = InputView.continueToPurchase();
        } while (continuedProcess);
    }

    private static void progressPayment(List<PurchaseResponse> responses) {
        List<PaymentProductResponse> paymentProductResponses = purchaseController.generatePaymentProductResponses(responses);
        List<PaymentFreeResponse> paymentFreeResponses = purchaseController.generatePaymentFreeResponses(responses);
        PaymentPriceResponse paymentPriceResponse = purchaseController.generatePaymentPriceResponse(responses);
        OutputView.printPaymentResult(paymentProductResponses, paymentFreeResponses, paymentPriceResponse);
    }

    private static Object selectProducts() {
        OutputView.printProducts();
        return RecursiveUtils.executeUntilNoException(() -> {
            List<PurchaseRequest> requests = progressPurchase();
            return purchaseController.generatePurchaseResponses(requests);
        });
    }

    private static List<PurchaseRequest> progressPurchase() {
        return RecursiveUtils.executeUntilNoException(() -> {
            String input = InputView.printRequestPurchaseMessage();
            purchaseController.validatePurchaseRequest(input);
            return purchaseController.getPurchaseRequests(input);
        });
    }
}
