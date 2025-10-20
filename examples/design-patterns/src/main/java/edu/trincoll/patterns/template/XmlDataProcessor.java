package edu.trincoll.patterns.template;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation for processing XML data.
 * <p>
 * Demonstrates Template Method pattern with XML-specific processing.
 * Note: Does not override the hook method, using default behavior.
 */
public class XmlDataProcessor extends DataProcessor<String> {
    private final List<String> xmlElements;
    private final List<String> processedData = new ArrayList<>();

    public XmlDataProcessor(List<String> xmlElements) {
        this.xmlElements = new ArrayList<>(xmlElements);
    }

    @Override
    protected List<String> loadData() {
        // Simulate loading from XML source
        System.out.println("Loading data from XML source...");
        return new ArrayList<>(xmlElements);
    }

    @Override
    protected boolean validateData(List<String> data) {
        // Validate XML format (simple check: should have matching tags)
        System.out.println("Validating XML format...");

        if (data.isEmpty()) {
            System.out.println("No data to validate");
            return false;
        }

        for (String xml : data) {
            String trimmed = xml.trim();
            if (!trimmed.startsWith("<") || !trimmed.endsWith(">")) {
                System.out.println("Invalid XML: must be enclosed in tags");
                return false;
            }
        }

        return true;
    }

    @Override
    protected List<String> transformData(List<String> data) {
        // Transform XML data: add namespace
        System.out.println("Transforming XML data (add namespace)...");

        return data.stream()
            .map(xml -> {
                // Simple transformation: add namespace to root element
                if (xml.startsWith("<") && xml.contains(">")) {
                    int closingBracket = xml.indexOf('>');
                    String tag = xml.substring(0, closingBracket);
                    String rest = xml.substring(closingBracket);
                    return tag + " xmlns=\"http://example.com\"" + rest;
                }
                return xml;
            })
            .toList();
    }

    @Override
    protected int saveData(List<String> data) {
        // Simulate saving to XML file
        System.out.println("Saving transformed XML data...");
        processedData.clear();
        processedData.addAll(data);
        return data.size();
    }

    // Note: Does not override onProcessingComplete() - uses default (no-op) behavior

    /**
     * Get the processed data.
     *
     * @return list of processed XML elements
     */
    public List<String> getProcessedData() {
        return new ArrayList<>(processedData);
    }
}
