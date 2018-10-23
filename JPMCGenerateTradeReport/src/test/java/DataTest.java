

import model.InstructionDataDetails;

import org.junit.Test;

import constants.TradeConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class DataTest {

    @Test
    public void tradeAmountTest() throws Exception {
        final BigDecimal agreedFx = BigDecimal.valueOf(0.50);
        final BigDecimal pricePerUnit = BigDecimal.valueOf(100.25);
        final int units = 200;

        final InstructionDataDetails fakeInstruction = new InstructionDataDetails(
                "JUnitTest1",
                TradeConstants.TRADETYPE_BUY,
                LocalDate.of(2017, 3, 10),
                LocalDate.of(2017, 3, 20), // Its a Monday
                Currency.getInstance("SGD"),
                agreedFx,
                units,
                pricePerUnit);

        assertEquals(agreedFx, fakeInstruction.getAgreedFx());
        assertEquals(pricePerUnit, fakeInstruction.getPricePerUnit());
        assertEquals(units, fakeInstruction.getUnits());

        final BigDecimal correct = pricePerUnit
                                    .multiply(agreedFx)
                                    .multiply(BigDecimal.valueOf(units));
        assertEquals(correct, fakeInstruction.getTradeAmount());
    }
}