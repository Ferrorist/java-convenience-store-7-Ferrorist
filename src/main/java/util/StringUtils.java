package util;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringUtils {

    public static final String SPECIAL_CHAR_REGEX = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ\\s]";
    public static String[] splitLinetoArray(String line) {
        String[] values = line.split(",");

        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].strip();
        }

        return values;
    }

    public static List<String> splitLineToList(String line) {
        return Stream.of(line.split(","))
                .map(String::strip)
                .toList();
    }

    public static boolean checkDatePattern(String date) {
        return Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", date);
    }

    public static int checkAndparseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("[ERROR] 올바르지 않는 입력값입니다.");
        }
    }

    public static boolean checkSpecialChar(char input) {
        return Pattern.matches(SPECIAL_CHAR_REGEX, Character.toString(input));
    }
}
