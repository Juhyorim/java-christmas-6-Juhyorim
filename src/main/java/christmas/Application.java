package christmas;

import christmas.domain.benefit.EventPlanner;

public class Application {
    public static void main(String[] args) {
        EventPlanner eventPlanner = new EventPlanner();
        eventPlanner.startOrder();
    }
}
