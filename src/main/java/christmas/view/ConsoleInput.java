package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInput {
    public String getVisitDayOfMonth() {
        return getValue();
    }

    public String getMenuAndCount() {
        return getValue();
    }

    private String getValue() {
        return Console.readLine();
    }
}
