package store;

import java.util.function.Supplier;
import store.view.InputView;
import store.view.OutputView;

public class Application {

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
