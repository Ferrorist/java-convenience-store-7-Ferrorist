package util;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import store.enums.ErrorCode;

public class InputCheckerUtils {

    public static boolean checkPurchaseRequestInputFormat(String input) throws IllegalArgumentException {
        char[] searchChars = {',', '[', ']'};
        int[] charCounts = countChars(input, searchChars);
        return (charCounts[0] + 1) == charCounts[1] && charCounts[1] == charCounts[2];
    }

    private static int[] countChars(String input, char[] searchChars) {
        int[] charCounts = {0, 0, 0};
        for (char inputChar : input.toCharArray()) {
            int idx = Arrays.binarySearch(searchChars, inputChar);
            if (inputChar != '-' && StringUtils.checkSpecialChar(inputChar) && idx < 0) {
                throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
            }
            if (idx >= 0) {
                charCounts[idx]++;
            }
        }
        return charCounts;
    }

    public static void checkPurchaseRequestFormat(String input)  {
        if (!(input.startsWith("[") && input.endsWith("]") && input.contains("-"))) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
        String[] values = input.substring(1, input.length() - 1).split("-");
        if(values.length != 2) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
        if(StringUtils.checkAndparseInt(values[1]) <= 0) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
    }

    public static boolean checkAnswerYesOrNo() {
        while (true) {
            String input = Console.readLine();
            if (input.equals("Y") || input.equals("y")) {
                return true;
            }
            if (input.equals("N") || input.equals("n")) {
                return false;
            }
            System.out.println(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
    }
}
