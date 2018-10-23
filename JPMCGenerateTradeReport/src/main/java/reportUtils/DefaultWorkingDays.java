package reportUtils;

import java.time.DayOfWeek;

/**
 * Sigleton class for default working days.
 *
 */
public class DefaultWorkingDays extends WorkingDays {

    private static DefaultWorkingDays instance = null;

    public static DefaultWorkingDays getInstance() {
        if (instance == null) {
            instance = new DefaultWorkingDays();
        }
        return instance;
    }

    private DefaultWorkingDays() {
        super();
    }

    @Override
    protected void setupWorkingDays() {
        this.workingDayMap.put(DayOfWeek.MONDAY, true);
        this.workingDayMap.put(DayOfWeek.TUESDAY, true);
        this.workingDayMap.put(DayOfWeek.WEDNESDAY, true);
        this.workingDayMap.put(DayOfWeek.THURSDAY, true);
        this.workingDayMap.put(DayOfWeek.FRIDAY, true);
        this.workingDayMap.put(DayOfWeek.SATURDAY, false);
        this.workingDayMap.put(DayOfWeek.SUNDAY, false);
    }
}
