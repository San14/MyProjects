
import org.junit.Before;
import org.junit.Test;

import reportUtils.DefaultWorkingDays;
import reportUtils.IWorkingDays;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;

public class TestDefaultWorkingDays {


	    private IWorkingDays workingDays;
	    @Before
	    public void setUp() throws Exception {
	        workingDays = DefaultWorkingDays.getInstance();
	    }

	    @Test
	    public void testFindFirstWorkingDate_Monday() throws Exception {
	        final LocalDate aMonday = LocalDate.of(2017, 3, 20);

	        assertEquals(aMonday, workingDays.findFirstWorkingDate(aMonday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Friday() throws Exception {
	        final LocalDate aFriday = LocalDate.of(2017, 3, 24);

	        assertEquals(aFriday, workingDays.findFirstWorkingDate(aFriday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Saturday() throws Exception {
	        final LocalDate aSaturday = LocalDate.of(2017, 3, 25);

	        assertEquals(LocalDate.of(2017, 3, 27), workingDays.findFirstWorkingDate(aSaturday));
	    }

	    @Test
	    public void testFindFirstWorkingDate_Sunday() throws Exception {
	        final LocalDate aSunday = LocalDate.of(2017, 3, 26);

	        assertEquals(LocalDate.of(2017, 3, 27), workingDays.findFirstWorkingDate(aSunday));
	    }
}
