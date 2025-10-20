package edu.trincoll.patterns.template;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for processing JSON data.
 * <p>
 * Demonstrates Template Method pattern with different implementation
 * of the processing steps compared to CSV processor.
 */
public class JsonDataProcessor extends DataProcessor<String> {
    private final List<String> jsonObjects;
    private final List<String> processedData = new ArrayList<>();

    public JsonDataProcessor(List<String> jsonObjects) {
        this.jsonObjects = new ArrayList<>(jsonObjects);
    }

    @Override
    protected List<String> loadData() {
        // Simulate loading from JSON source
        System.out.println("Loading data from JSON source...");
        return new ArrayList<>(jsonObjects);
    }

    @Override
    protected boolean validateData(List<String> data) {
        // Validate JSON format (simple check: should start with { and end with })
        System.out.println("Validating JSON format...");

        if (data.isEmpty()) {
            System.out.println("No data to validate");
            return false;
        }

        for (String json : data) {
            String trimmed = json.trim();
            if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) {
                System.out.println("Invalid JSON: must be enclosed in braces");
                return false;
            }
        }

        return true;
    }

    @Override
    protected List<String> transformData(List<String> data) {
        // Transform JSON data: add metadata
        System.out.println("Transforming JSON data (add metadata)...");

        return data.stream()
            .map(json -> {
                // Remove closing brace, add metadata, add closing brace
                String withoutClosing = json.substring(0, json.lastIndexOf('}'));
                return withoutClosing + ", \"processed\": true}";
            })
            .toList();
    }

    @Override
    protected int saveData(List<String> data) {
        // Simulate saving to JSON file
        System.out.println("Saving transformed JSON data...");
        processedData.clear();
        processedData.addAll(data);
        return data.size();
    }

    @Override
    protected void onProcessingComplete(int itemCount) {
        System.out.println("JSON processing complete: " + itemCount + " objects processed");
        System.out.println("All objects marked as processed");
    }

    /**
     * Get the processed data.
     *
     * @return list of processed JSON objects
     */
    public List<String> getProcessedData() {
        return new ArrayList<>(processedData);
    }
}
