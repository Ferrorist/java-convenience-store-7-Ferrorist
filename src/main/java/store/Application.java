package store;

import java.util.List;
import java.util.function.Supplier;
import store.controller.PurchaseController;
import store.model.dto.response.PaymentFreeResponse;
import store.model.dto.response.PaymentPriceResponse;
import store.model.dto.request.PurchaseRequest;
import store.model.dto.response.PaymentProductResponse;
import store.model.dto.response.PurchaseResponse;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    private static final PurchaseController purchaseController = new PurchaseController();

    public static void main(String[] args) {
        startProgram();
        progressConvenienceStore();
    }

    private static void startProgram() {
        OutputView.printWelcomeMessage();
    }

    private static Void progressConvenienceStore() {
        OutputView.printProducts();
        return executeUntilNoException(() -> {
            List<PurchaseRequest> requests = progressPurchase();
            List<PurchaseResponse> responses = purchaseController.progressPayment(requests);
            List<PaymentProductResponse> paymentProductResponses = purchaseController.generatePaymentProductResponses(responses);
            List<PaymentFreeResponse> paymentFreeResponses = purchaseController.generatePaymentFreeResponses(responses);
            PaymentPriceResponse paymentPriceResponse = purchaseController.generatePaymentPriceResponse(responses);
            OutputView.printPaymentResult(paymentProductResponses, paymentFreeResponses, paymentPriceResponse);
            return null;
        });
    }

    private static List<PurchaseRequest> progressPurchase() {
        return executeUntilNoException(() -> {
            String input = InputView.printRequestPurchaseMessage();
            purchaseController.validatePurchaseRequest(input);
            return purchaseController.getPurchaseRequests(input);
        });
    }

    private static <T> T executeUntilNoException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void progressPayment(List<PurchaseRequest> requests) {
        executeUntilNoException(() -> {

            return null;
        });
    }
}
