package edu.trincoll.patterns.template;

/**
 * Result of data processing operation.
 *
 * @param success true if processing succeeded
 * @param message descriptive message
 * @param itemsProcessed number of items processed
 */
public record ProcessingResult(
    boolean success,
    String message,
    int itemsProcessed
) {
}
