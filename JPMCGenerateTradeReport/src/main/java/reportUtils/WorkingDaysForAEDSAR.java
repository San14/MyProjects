package reportUtils;

import java.time.DayOfWeek;

/**
 * Singleton class for setting the Arabia working days
 * @author Santhosh.Rayilla
 *
 */
public class WorkingDaysForAEDSAR extends WorkingDays {

    private static WorkingDaysForAEDSAR instance = null;

    public static WorkingDaysForAEDSAR getInstance() {
        if (instance == null) {
            instance = new WorkingDaysForAEDSAR();
        }
        return instance;
    }

    private WorkingDaysForAEDSAR() {
        super();
    }

    @Override
    protected void setupWorkingDays() {
        this.workingDayMap.put(DayOfWeek.SUNDAY, true);
        this.workingDayMap.put(DayOfWeek.MONDAY, true);
        this.workingDayMap.put(DayOfWeek.TUESDAY, true);
        this.workingDayMap.put(DayOfWeek.WEDNESDAY, true);
        this.workingDayMap.put(DayOfWeek.THURSDAY, true);
        this.workingDayMap.put(DayOfWeek.FRIDAY, false); 
        this.workingDayMap.put(DayOfWeek.SATURDAY, false); 
    }
}
