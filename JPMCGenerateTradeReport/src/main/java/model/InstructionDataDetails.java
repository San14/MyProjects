package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;


/**
 * Bean class for all given Instruction data.
 * @author Santhosh.Rayilla
 */
public class InstructionDataDetails {

	private final Currency currency;
    private final BigDecimal agreedFx;
    private final int units;
    private final BigDecimal pricePerUnit;
    private final BigDecimal tradeAmount;
    private final String entity;
    private final String tradeType;
    private final LocalDate instructionDate;
    private LocalDate settlementDate;

    public InstructionDataDetails(String entity,
    		String tradeType,
            LocalDate instructionDate,
            LocalDate settlementDate,
            Currency currency, 
            BigDecimal agreedFx, int units, BigDecimal pricePerUnit) {
    	this.entity = entity;
        this.tradeType = tradeType;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.currency = currency;
        this.agreedFx = agreedFx;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.tradeAmount = calculateAmount(this);
    }

    private static BigDecimal calculateAmount(InstructionDataDetails ins) {
        return ins.getPricePerUnit()
                .multiply(BigDecimal.valueOf(ins.getUnits()))
                .multiply(ins.getAgreedFx());
    }

    public String getEntity() {
        return entity;
    }

    public String getTradeType() {
        return tradeType;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public void setSettlementDate(LocalDate newDate) {
        settlementDate = newDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public BigDecimal getAgreedFx() {
        return agreedFx;
    }

    public int getUnits() {
        return units;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
