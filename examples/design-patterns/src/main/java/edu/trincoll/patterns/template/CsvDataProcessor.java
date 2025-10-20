package edu.trincoll.patterns.template;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for processing CSV data.
 * <p>
 * Demonstrates Template Method pattern by implementing the abstract methods
 * while inheriting the algorithm structure from DataProcessor.
 */
public class CsvDataProcessor extends DataProcessor<String> {
    private final List<String> csvLines;
    private final List<String> processedData = new ArrayList<>();

    public CsvDataProcessor(List<String> csvLines) {
        this.csvLines = new ArrayList<>(csvLines);
    }

    @Override
    protected List<String> loadData() {
        // Simulate loading from CSV file
        System.out.println("Loading data from CSV source...");
        return new ArrayList<>(csvLines);
    }

    @Override
    protected boolean validateData(List<String> data) {
        // Validate CSV format (simple check: each line should have commas)
        System.out.println("Validating CSV format...");

        if (data.isEmpty()) {
            System.out.println("No data to validate");
            return false;
        }

        // Check that all lines have the same number of fields
        int expectedFields = data.get(0).split(",").length;
        for (String line : data) {
            String[] fields = line.split(",");
            if (fields.length != expectedFields) {
                System.out.println("Invalid CSV: inconsistent field count");
                return false;
            }
        }

        return true;
    }

    @Override
    protected List<String> transformData(List<String> data) {
        // Transform CSV data: trim whitespace and convert to uppercase
        System.out.println("Transforming CSV data (trim and uppercase)...");

        return data.stream()
            .map(line -> {
                String[] fields = line.split(",");
                String[] trimmed = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    trimmed[i] = fields[i].trim().toUpperCase();
                }
                return String.join(",", trimmed);
            })
            .toList();
    }

    @Override
    protected int saveData(List<String> data) {
        // Simulate saving to CSV file
        System.out.println("Saving transformed CSV data...");
        processedData.clear();
        processedData.addAll(data);
        return data.size();
    }

    @Override
    protected void onProcessingComplete(int itemCount) {
        System.out.println("CSV processing complete: " + itemCount + " rows processed");
    }

    /**
     * Get the processed data.
     *
     * @return list of processed CSV lines
     */
    public List<String> getProcessedData() {
        return new ArrayList<>(processedData);
    }
}
