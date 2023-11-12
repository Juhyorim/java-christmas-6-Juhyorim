package christmas.util;

import java.util.List;

public class SplitManager {
    public static List<String> split(String input, char delimiter) {
        List<String> splited = List.of(input.split(String.valueOf(delimiter)));
        if (input.charAt(input.length() - 1) == delimiter) {
            splited.add("");
        }

        return splited;
    }
}
