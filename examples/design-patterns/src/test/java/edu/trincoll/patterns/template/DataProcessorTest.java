package edu.trincoll.patterns.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DataProcessor Tests")
class DataProcessorTest {

    @Nested
    @DisplayName("CsvDataProcessor Tests")
    class CsvDataProcessorTests {

        @Test
        @DisplayName("Should process valid CSV data")
        void shouldProcessValidCsvData() {
            List<String> csvData = List.of(
                "name, age, city",
                "alice, 30, boston",
                "bob, 25, seattle"
            );

            var processor = new CsvDataProcessor(csvData);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isTrue();
            assertThat(result.itemsProcessed()).isEqualTo(3);
            assertThat(result.message()).isEqualTo("Processing complete");
        }

        @Test
        @DisplayName("Should transform CSV data correctly")
        void shouldTransformCsvData() {
            List<String> csvData = List.of(
                "name, age",
                "alice, 30"
            );

            var processor = new CsvDataProcessor(csvData);
            processor.process();

            List<String> processed = processor.getProcessedData();

            assertThat(processed).hasSize(2);
            assertThat(processed.get(0)).isEqualTo("NAME,AGE");
            assertThat(processed.get(1)).isEqualTo("ALICE,30");
        }

        @Test
        @DisplayName("Should reject empty CSV data")
        void shouldRejectEmptyCsvData() {
            var processor = new CsvDataProcessor(List.of());
            ProcessingResult result = processor.process();

            assertThat(result.success()).isFalse();
            assertThat(result.message()).isEqualTo("Validation failed");
        }

        @Test
        @DisplayName("Should reject inconsistent CSV format")
        void shouldRejectInconsistentCsvFormat() {
            List<String> invalidCsv = List.of(
                "name,age,city",
                "alice,30"  // Missing city field
            );

            var processor = new CsvDataProcessor(invalidCsv);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isFalse();
            assertThat(result.message()).isEqualTo("Validation failed");
        }
    }

    @Nested
    @DisplayName("JsonDataProcessor Tests")
    class JsonDataProcessorTests {

        @Test
        @DisplayName("Should process valid JSON data")
        void shouldProcessValidJsonData() {
            List<String> jsonData = List.of(
                "{\"name\": \"Alice\"}",
                "{\"name\": \"Bob\"}"
            );

            var processor = new JsonDataProcessor(jsonData);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isTrue();
            assertThat(result.itemsProcessed()).isEqualTo(2);
        }

        @Test
        @DisplayName("Should add processed metadata to JSON")
        void shouldAddProcessedMetadataToJson() {
            List<String> jsonData = List.of(
                "{\"name\": \"Alice\"}"
            );

            var processor = new JsonDataProcessor(jsonData);
            processor.process();

            List<String> processed = processor.getProcessedData();

            assertThat(processed).hasSize(1);
            assertThat(processed.get(0)).contains("\"processed\": true");
        }

        @Test
        @DisplayName("Should reject invalid JSON format")
        void shouldRejectInvalidJsonFormat() {
            List<String> invalidJson = List.of(
                "name: Alice"  // Not valid JSON
            );

            var processor = new JsonDataProcessor(invalidJson);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isFalse();
            assertThat(result.message()).isEqualTo("Validation failed");
        }

        @Test
        @DisplayName("Should reject JSON without closing brace")
        void shouldRejectJsonWithoutClosingBrace() {
            List<String> invalidJson = List.of(
                "{\"name\": \"Alice\""  // Missing }
            );

            var processor = new JsonDataProcessor(invalidJson);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isFalse();
        }
    }

    @Nested
    @DisplayName("XmlDataProcessor Tests")
    class XmlDataProcessorTests {

        @Test
        @DisplayName("Should process valid XML data")
        void shouldProcessValidXmlData() {
            List<String> xmlData = List.of(
                "<person><name>Alice</name></person>",
                "<person><name>Bob</name></person>"
            );

            var processor = new XmlDataProcessor(xmlData);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isTrue();
            assertThat(result.itemsProcessed()).isEqualTo(2);
        }

        @Test
        @DisplayName("Should add namespace to XML")
        void shouldAddNamespaceToXml() {
            List<String> xmlData = List.of(
                "<person>Alice</person>"
            );

            var processor = new XmlDataProcessor(xmlData);
            processor.process();

            List<String> processed = processor.getProcessedData();

            assertThat(processed).hasSize(1);
            assertThat(processed.get(0)).contains("xmlns=\"http://example.com\"");
        }

        @Test
        @DisplayName("Should reject invalid XML format")
        void shouldRejectInvalidXmlFormat() {
            List<String> invalidXml = List.of(
                "person: Alice"  // Not valid XML
            );

            var processor = new XmlDataProcessor(invalidXml);
            ProcessingResult result = processor.process();

            assertThat(result.success()).isFalse();
        }
    }

    @Nested
    @DisplayName("Template Method Pattern Tests")
    class TemplateMethodPatternTests {

        @Test
        @DisplayName("All processors should use same algorithm structure")
        void allProcessorsShouldUseSameAlgorithmStructure() {
            // Create different processors
            var csvProcessor = new CsvDataProcessor(List.of("a,b", "1,2"));
            var jsonProcessor = new JsonDataProcessor(List.of("{\"x\": 1}"));
            var xmlProcessor = new XmlDataProcessor(List.of("<data>1</data>"));

            // All should execute the same sequence of steps
            csvProcessor.process();
            jsonProcessor.process();
            xmlProcessor.process();

            // All should have processed their data
            assertThat(csvProcessor.getProcessedData()).isNotEmpty();
            assertThat(jsonProcessor.getProcessedData()).isNotEmpty();
            assertThat(xmlProcessor.getProcessedData()).isNotEmpty();
        }

        @Test
        @DisplayName("Should allow polymorphic processing")
        void shouldAllowPolymorphicProcessing() {
            // Store different processors in a list using base class
            List<DataProcessor<?>> processors = List.of(
                new CsvDataProcessor(List.of("x,y", "1,2")),
                new JsonDataProcessor(List.of("{\"x\": 1}")),
                new XmlDataProcessor(List.of("<data>1</data>"))
            );

            // Process all using same interface
            List<ProcessingResult> results = processors.stream()
                .map(DataProcessor::process)
                .toList();

            // All should succeed
            assertThat(results)
                .allMatch(ProcessingResult::success)
                .allMatch(r -> r.itemsProcessed() > 0);
        }

        @Test
        @DisplayName("Hook method should be optional")
        void hookMethodShouldBeOptional() {
            // XmlDataProcessor doesn't override onProcessingComplete()
            var xmlProcessor = new XmlDataProcessor(List.of("<data>1</data>"));

            // Should still process successfully
            ProcessingResult result = xmlProcessor.process();

            assertThat(result.success()).isTrue();
        }
    }
}
