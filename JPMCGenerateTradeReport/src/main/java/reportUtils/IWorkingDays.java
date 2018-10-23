package reportUtils;

import java.time.LocalDate;

public interface IWorkingDays {
    LocalDate findFirstWorkingDate(LocalDate date);
}
