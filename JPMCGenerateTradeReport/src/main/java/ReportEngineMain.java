import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import constants.TradeConstants;
import model.InstructionDataDetails;
import service.IServiceReportGenerator;
import service.ReportGeneratorServiceImpl;

/**
 * Report Generation Main Class
 * @author Santhosh.Rayilla
 *
 */
public class ReportEngineMain {

	public static void main(String args[]){
		
		Set<InstructionDataDetails> reportData = getInstructionsData();
		
        IServiceReportGenerator reportGenerator = new ReportGeneratorServiceImpl();
        System.out.println(reportGenerator.generateInstructionsReport(reportData));
	}
	
	/**
	 * 
	 * Instructions dummy data to be provided in this method.
	 * @return
	 */
    private static Set<InstructionDataDetails> getInstructionsData(){
		Set<InstructionDataDetails> reportData = new HashSet<InstructionDataDetails>();
		reportData.add(new InstructionDataDetails(
                "foo",
                TradeConstants.TRADETYPE_BUY,
                LocalDate.of(2016, 01, 01),
                LocalDate.of(2016, 01, 02),
                Currency.getInstance("SGD"),
                BigDecimal.valueOf(0.50),
                200,
                BigDecimal.valueOf(100.25)));
		
		reportData.add( new InstructionDataDetails(
                "bar",
                TradeConstants.TRADETYPE_SELL,
                LocalDate.of(2016, 01, 05),
                LocalDate.of(2016, 01, 07),
                Currency.getInstance("AED"),
                BigDecimal.valueOf(0.22),
                450,
                BigDecimal.valueOf(150.5)));
		
		reportData.add(new InstructionDataDetails(
                "test1",
                TradeConstants.TRADETYPE_SELL,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 18),
                Currency.getInstance("EUR"),
                BigDecimal.valueOf(0.27),
                150,
                BigDecimal.valueOf(400.8)));

		reportData.add(new InstructionDataDetails(
                "test2",
                TradeConstants.TRADETYPE_SELL,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 21),
                Currency.getInstance("EUR"),
                BigDecimal.valueOf(0.34),
                50,
                BigDecimal.valueOf(500.6)));

		reportData.add(new InstructionDataDetails(
                "test3",
                TradeConstants.TRADETYPE_SELL,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 21),
                Currency.getInstance("EUR"),
                BigDecimal.valueOf(0.34),
                20,
                BigDecimal.valueOf(40.6)));

		reportData.add(new InstructionDataDetails(
                "test4",
                TradeConstants.TRADETYPE_BUY,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 21),
                Currency.getInstance("SAR"),
                BigDecimal.valueOf(0.34),
                20,
                BigDecimal.valueOf(40.6)));

		reportData.add(new InstructionDataDetails(
                "test5",
                TradeConstants.TRADETYPE_SELL,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 21),
                Currency.getInstance("EUR"),
                BigDecimal.valueOf(0.34),
                1000,
                BigDecimal.valueOf(160.6)));
		
    	return reportData;
    	
    }

}
