package service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;
import model.InstructionDataDetails;
import model.InstructionRanking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import constants.TradeConstants;
import reportUtils.DefaultWorkingDays;
import reportUtils.IWorkingDays;
import reportUtils.WorkingDaysForAEDSAR;

public class ReportGeneratorServiceImpl implements IServiceReportGenerator {
    
	private StringBuilder stringBuilder = new StringBuilder();
	
	//predicates trade type with boolean
    private final static Predicate<InstructionDataDetails> outgoingInstructions =
            instruction -> instruction.getTradeType().equals(TradeConstants.TRADETYPE_BUY);

    private final static Predicate<InstructionDataDetails> incomingInstructions =
            instruction -> instruction.getTradeType().equals(TradeConstants.TRADETYPE_SELL);

    @Override
    public String generateInstructionsReport(Set<InstructionDataDetails> instructions) {
        //  calculate the correct settlement dates
        calculateSettlementDates(instructions);

        // Build the report string
        return generateDailyOutgoingRanking(instructions,
                generateDailyIncomingRanking(instructions,
                generateDailyIncomingAmount(instructions,
                generateDailyOutgoingAmount(instructions, stringBuilder))))
            .toString();
    }

    private static StringBuilder generateDailyOutgoingAmount(Set<InstructionDataDetails> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
        		calculateDailyAmount(instructions,outgoingInstructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Daily Amount          \n")
                .append("----------------------------------------\n")
                .append("      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                .append(date).append("       |      ").append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingAmount(Set<InstructionDataDetails> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
        		calculateDailyAmount(instructions,incomingInstructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Daily Amount          \n")
                .append("----------------------------------------\n")
                .append("      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                    .append(date).append("       |      ").append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyOutgoingRanking(Set<InstructionDataDetails> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, LinkedList<InstructionRanking>> dailyOutgoingRanking =
        		calculateRanking(instructions,outgoingInstructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Daily Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Date    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingRanking.keySet()) {
            for (InstructionRanking rank : dailyOutgoingRanking.get(date)) {
                stringBuilder
                    .append(date).append("   |      ")
                    .append(rank.getRank()).append("     |    ")
                    .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingRanking(Set<InstructionDataDetails> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, LinkedList<InstructionRanking>> dailyIncomingRanking =
        		calculateRanking(instructions,incomingInstructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Daily Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Date    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyIncomingRanking.keySet()) {
            for (InstructionRanking rank : dailyIncomingRanking.get(date)) {
                stringBuilder
                        .append(date).append("   |      ")
                        .append(rank.getRank()).append("     |    ")
                        .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }
    
    public static void calculateSettlementDates(Set<InstructionDataDetails> instructions) {
        instructions.forEach(ReportGeneratorServiceImpl::calculateSettlementDate);
    }

    /**
     * 
     * @param instruction
     */
    public static void calculateSettlementDate(InstructionDataDetails instruction) {
        // Select proper strategy based on the Currency
        final IWorkingDays workingDaysMechanism = getWorkingDaysStrategy(instruction.getCurrency());

        // find the correct settlement date
        final LocalDate newSettlementDate =
                workingDaysMechanism.findFirstWorkingDate(instruction.getSettlementDate());

        if (newSettlementDate != null) {
            // set the correct settlement date
            instruction.setSettlementDate(newSettlementDate);
        }
    }
    /**
     * 
     * @param currency
     * @return
     */
    private static IWorkingDays getWorkingDaysStrategy(Currency currency) {
        if ((currency.getCurrencyCode().equals("AED")) ||
            (currency.getCurrencyCode().equals("SAR")))
        {
            return WorkingDaysForAEDSAR.getInstance();
        }
        return DefaultWorkingDays.getInstance();
    }
    
    /**
     * 
     * @param instructions
     * @param predicate
     * @return
     */
    private static Map<LocalDate, BigDecimal> calculateDailyAmount(
            Set<InstructionDataDetails> instructions, Predicate<InstructionDataDetails> predicate)
    {
        return instructions.stream()
                .filter(predicate)
                .collect(groupingBy(InstructionDataDetails::getSettlementDate,
                    mapping(InstructionDataDetails::getTradeAmount,
                        reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    /**
     * 
     * @param instructions
     * @param predicate
     * @return
     */
    private static Map<LocalDate, LinkedList<InstructionRanking>> calculateRanking(
            Set<InstructionDataDetails> instructions, Predicate<InstructionDataDetails> predicate)
    {
        final Map<LocalDate, LinkedList<InstructionRanking>> ranking = new HashMap<>();

        instructions.stream()
            .filter(predicate)
            .collect(groupingBy(InstructionDataDetails::getSettlementDate, toSet()))
            .forEach((date, dailyInstructionSet) -> {
                final AtomicInteger rank = new AtomicInteger(1);

                final LinkedList<InstructionRanking> ranks = dailyInstructionSet.stream()
                    .sorted((a, b) -> b.getTradeAmount().compareTo(a.getTradeAmount()))
                    .map(instruction -> new InstructionRanking(rank.getAndIncrement(), instruction.getEntity(), date))
                    .collect(toCollection(LinkedList::new));

                ranking.put(date, ranks);
            });

        return ranking;
    }
    
}
