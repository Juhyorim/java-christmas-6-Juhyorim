package christmas.util;

import java.util.ArrayList;
import java.util.List;

public class SplitManager {
    public static List<String> split(String input, char delimiter) {
        List<String> splited = new ArrayList<>();
        splited.addAll(List.of(input.split(String.valueOf(delimiter))));
        if (input.charAt(input.length() - 1) == delimiter) {
            splited.add("");
        }

        return trim(splited);
    }

    private static List<String> trim(List<String> splited) {
        List<String> trimmed = new ArrayList<>();
        for (String token: splited) {
            trimmed.add(token.trim());
        }

        return trimmed;
    }
}
