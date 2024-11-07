package store;

import java.util.function.Supplier;
import store.controller.PurchaseController;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    private static PurchaseController purchaseController = new PurchaseController();

    public static void main(String[] args) {
        startProgram();
        progressConvenienceStore();
    }

    private static void startProgram() {
        OutputView.printWelcomeMessage();
    }

    private static void progressConvenienceStore() {
        OutputView.printProducts();
        progressPurchase();
    }

    private static void progressPurchase() {
        executeUntilNoException(() -> {
            OutputView.printRequestPurchaseMesssage();
            String input = InputView.inputPurchaseProducts();
            purchaseController.validatePurchaseRequest(input);
            return null;
        });
    }

    private static void executeUntilNoException(Supplier<?> supplier) {
        while (true) {
            try {
                supplier.get();
                return;
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}
