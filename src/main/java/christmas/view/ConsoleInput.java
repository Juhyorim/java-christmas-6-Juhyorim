package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.view.message.OutputMessage;

public class ConsoleInput {
    public String getVisitDayOfMonth() {
        visitDayRequest();
        return getValue();
    }

    public String getMenuAndCount() {
        menuAndCountRequest();
        return getValue();
    }

    private String getValue() {
        return Console.readLine();
    }

    private void visitDayRequest() {
        System.out.println(OutputMessage.VISIT_DAY_REQUEST.getMessage());
    }

    private void menuAndCountRequest() {
        System.out.println(OutputMessage.MENU_AND_COUNT_REQUEST.getMessage());
    }
}
