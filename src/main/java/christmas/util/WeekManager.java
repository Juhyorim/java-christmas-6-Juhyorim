package christmas.util;

import java.time.DayOfWeek;
import java.util.List;

public enum WeekManager {
    WEEKDAY(List.of(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY)),
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
        if (WEEKEND.includedDayOfWeeks.contains(dayOfWeek)) {
            return true;
        }

        return false;
    }
}
