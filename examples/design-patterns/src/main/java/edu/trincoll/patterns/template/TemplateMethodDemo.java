package edu.trincoll.patterns.template;

import java.util.List;

/**
 * Demonstration of the Template Method Pattern.
 * <p>
 * Shows:
 * <ul>
 *   <li>Algorithm skeleton defined in base class</li>
 *   <li>Subclasses provide specific implementations</li>
 *   <li>Hook methods for optional customization</li>
 *   <li>Same processing flow for different data formats</li>
 * </ul>
 */
public class TemplateMethodDemo {

    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern Demonstrations ===\n");

        demonstrateCsvProcessing();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateJsonProcessing();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateXmlProcessing();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstratePolymorphism();
    }

    private static void demonstrateCsvProcessing() {
        System.out.println("--- CSV Data Processing ---");

        List<String> csvData = List.of(
            "name, age, city",
            "alice, 30, boston",
            "bob, 25, seattle",
            "carol, 35, austin"
        );

        var processor = new CsvDataProcessor(csvData);
        ProcessingResult result = processor.process();

        System.out.println("\nResult: " + result.message());
        System.out.println("Processed data:");
        processor.getProcessedData().forEach(line ->
            System.out.println("  " + line));
    }

    private static void demonstrateJsonProcessing() {
        System.out.println("--- JSON Data Processing ---");

        List<String> jsonData = List.of(
            "{\"name\": \"Alice\", \"age\": 30}",
            "{\"name\": \"Bob\", \"age\": 25}",
            "{\"name\": \"Carol\", \"age\": 35}"
        );

        var processor = new JsonDataProcessor(jsonData);
        ProcessingResult result = processor.process();

        System.out.println("\nResult: " + result.message());
        System.out.println("Processed data:");
        processor.getProcessedData().forEach(json ->
            System.out.println("  " + json));
    }

    private static void demonstrateXmlProcessing() {
        System.out.println("--- XML Data Processing ---");

        List<String> xmlData = List.of(
            "<person><name>Alice</name><age>30</age></person>",
            "<person><name>Bob</name><age>25</age></person>",
            "<person><name>Carol</name><age>35</age></person>"
        );

        var processor = new XmlDataProcessor(xmlData);
        ProcessingResult result = processor.process();

        System.out.println("\nResult: " + result.message());
        System.out.println("Processed data:");
        processor.getProcessedData().forEach(xml ->
            System.out.println("  " + xml));
    }

    private static void demonstratePolymorphism() {
        System.out.println("--- Polymorphic Processing ---");
        System.out.println("Processing multiple formats with same algorithm:\n");

        // Store different processors in a list
        List<DataProcessor<?>> processors = List.of(
            new CsvDataProcessor(List.of("x,y", "1,2")),
            new JsonDataProcessor(List.of("{\"x\": 1}", "{\"y\": 2}")),
            new XmlDataProcessor(List.of("<data>1</data>", "<data>2</data>"))
        );

        // Process all using same interface
        for (DataProcessor<?> processor : processors) {
            String type = processor.getClass().getSimpleName()
                .replace("DataProcessor", "");
            System.out.println("Processing " + type + " format:");

            ProcessingResult result = processor.process();
            System.out.println("  Status: " + result.message());
            System.out.println("  Items: " + result.itemsProcessed());
            System.out.println();
        }

        System.out.println("All processors used the same algorithm structure!");
        System.out.println("Only the implementation details differ.");
    }
}
