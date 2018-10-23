package service;

import model.InstructionDataDetails;

import java.util.Set;

/**
 * 
 * @author Santhosh.Rayilla
 *
 */
public interface IServiceReportGenerator {
    String generateInstructionsReport(Set<InstructionDataDetails> instructionDataSet);
}
