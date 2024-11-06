package store;

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
    }
}
