package util;

import java.util.List;
import java.util.stream.Stream;

public class StringUtils {

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
}
