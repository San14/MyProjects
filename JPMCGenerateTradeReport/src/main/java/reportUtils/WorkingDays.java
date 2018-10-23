package reportUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class WorkingDays implements IWorkingDays {
    
	Map<DayOfWeek, Boolean> workingDayMap = new HashMap<>();

    abstract void setupWorkingDays();

    public WorkingDays() {
        setupWorkingDays();
    }

    public LocalDate findFirstWorkingDate(LocalDate date) {

        if (workingDayMap.values().stream().noneMatch(b -> b)) {
            return null;
        }
        return findFirstWorkingDateRec(date);
    }

    private LocalDate findFirstWorkingDateRec(LocalDate date) {
        final DayOfWeek inputDay = date.getDayOfWeek();

        if (workingDayMap.get(inputDay)) {
            return date;
        } else {
            return findFirstWorkingDateRec(date.plusDays(1));
        }
    }
}
