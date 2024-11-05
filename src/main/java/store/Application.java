package store;

import store.views.OutputView;

public class Application {

    private static final OutputView outputView = new OutputView();

    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        outputView.printWelcomeMessage();
    }
}
