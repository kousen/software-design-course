package edu.trincoll.patterns.template;

import java.util.List;

/**
 * Abstract class demonstrating the Template Method Pattern.
 * <p>
 * The Template Method pattern defines the skeleton of an algorithm in a method,
 * deferring some steps to subclasses. This allows subclasses to redefine certain
 * steps without changing the algorithm's structure.
 * <p>
 * This example shows a data processing pipeline with fixed steps:
 * 1. Load data
 * 2. Validate data
 * 3. Transform data
 * 4. Save data
 */
public abstract class DataProcessor<T> {

    /**
     * Template method - defines the algorithm skeleton.
     * <p>
     * This method is final to prevent subclasses from changing the algorithm.
     * Subclasses can only customize the individual steps.
     */
    public final ProcessingResult process() {
        System.out.println("Starting data processing...");

        // Step 1: Load data
        List<T> data = loadData();
        System.out.println("Loaded " + data.size() + " items");

        // Step 2: Validate data
        if (!validateData(data)) {
            return new ProcessingResult(false, "Validation failed", 0);
        }
        System.out.println("Validation passed");

        // Step 3: Transform data
        List<T> transformed = transformData(data);
        System.out.println("Transformed " + transformed.size() + " items");

        // Step 4: Save data
        int savedCount = saveData(transformed);
        System.out.println("Saved " + savedCount + " items");

        // Optional hook - subclasses can override
        onProcessingComplete(savedCount);

        return new ProcessingResult(true, "Processing complete", savedCount);
    }

    /**
     * Abstract method - subclasses must implement.
     * Load data from the source.
     *
     * @return list of data items
     */
    protected abstract List<T> loadData();

    /**
     * Abstract method - subclasses must implement.
     * Validate the loaded data.
     *
     * @param data data to validate
     * @return true if valid, false otherwise
     */
    protected abstract boolean validateData(List<T> data);

    /**
     * Abstract method - subclasses must implement.
     * Transform the data.
     *
     * @param data data to transform
     * @return transformed data
     */
    protected abstract List<T> transformData(List<T> data);

    /**
     * Abstract method - subclasses must implement.
     * Save the transformed data.
     *
     * @param data data to save
     * @return number of items saved
     */
    protected abstract int saveData(List<T> data);

    /**
     * Hook method - subclasses can optionally override.
     * Called after processing completes successfully.
     * <p>
     * Default implementation does nothing.
     *
     * @param itemCount number of items processed
     */
    protected void onProcessingComplete(int itemCount) {
        // Default: do nothing
        // Subclasses can override to add custom behavior
    }
}
