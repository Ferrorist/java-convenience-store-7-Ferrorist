package util;

import java.util.function.Supplier;

public class RecursiveUtils {
    public static <T> T executeUntilNoException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
}