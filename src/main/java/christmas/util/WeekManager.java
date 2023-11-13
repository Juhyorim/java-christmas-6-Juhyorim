package christmas.util;

import java.time.DayOfWeek;
import java.util.List;

public enum WeekManager {
    WEEKEND(List.of(DayOfWeek.FRIDAY, DayOfWeek.SATURDAY));

    private List<DayOfWeek> includedDayOfWeeks;

    WeekManager(List<DayOfWeek> dayOfWeeks) {
        this.includedDayOfWeeks = dayOfWeeks;
    }

    public static boolean isWeekday(DayOfWeek dayOfWeek) {
        if (WEEKEND.includedDayOfWeeks.contains(dayOfWeek)) {
            return false;
        }

        return true;
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return !isWeekday(dayOfWeek);
    }
}
