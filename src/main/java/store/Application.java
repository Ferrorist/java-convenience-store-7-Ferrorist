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
        boolean continuedProcess;
        do {
            continuedProcess = progressConvenienceStore();
        } while (continuedProcess);
    }

    private static void startProgram() {
        OutputView.printWelcomeMessage();
    }

    private static Boolean progressConvenienceStore() {
        OutputView.printProducts();
        return RecursiveUtils.executeUntilNoException(() -> {
            List<PurchaseRequest> requests = progressPurchase();
            List<PurchaseResponse> responses = purchaseController.progressPayment(requests);
            List<PaymentProductResponse> paymentProductResponses = purchaseController.generatePaymentProductResponses(responses);
            List<PaymentFreeResponse> paymentFreeResponses = purchaseController.generatePaymentFreeResponses(responses);
            PaymentPriceResponse paymentPriceResponse = purchaseController.generatePaymentPriceResponse(responses);
            OutputView.printPaymentResult(paymentProductResponses, paymentFreeResponses, paymentPriceResponse);
            return InputView.continueToPurchase();
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
