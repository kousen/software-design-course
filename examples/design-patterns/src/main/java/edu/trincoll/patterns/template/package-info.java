/**
 * Template Method Pattern implementation for data processing pipelines.
 * <p>
 * The Template Method pattern defines the skeleton of an algorithm in a method,
 * deferring some steps to subclasses. Subclasses can redefine certain steps
 * without changing the algorithm's structure.
 * <p>
 * <strong>Key Components:</strong>
 * <ul>
 *   <li>{@link edu.trincoll.patterns.template.DataProcessor} - Abstract class with template method</li>
 *   <li>Concrete processors: {@link edu.trincoll.patterns.template.CsvDataProcessor},
 *       {@link edu.trincoll.patterns.template.JsonDataProcessor},
 *       {@link edu.trincoll.patterns.template.XmlDataProcessor}</li>
 *   <li>{@link edu.trincoll.patterns.template.ProcessingResult} - Result record</li>
 * </ul>
 * <p>
 * <strong>Template Method Structure:</strong>
 * <pre>{@code
 * public final ProcessingResult process() {  // Template method (final)
 *     List<T> data = loadData();           // Abstract - subclass implements
 *     if (!validateData(data)) return fail;   // Abstract - subclass implements
 *     List<T> transformed = transformData(data); // Abstract - subclass implements
 *     int saved = saveData(transformed);      // Abstract - subclass implements
 *     onProcessingComplete(saved);         // Hook - subclass can override
 *     return success;
 * }
 * }</pre>
 * <p>
 * <strong>Example Usage:</strong>
 * <pre>{@code
 * // Process CSV data
 * var csvProcessor = new CsvDataProcessor(csvLines);
 * ProcessingResult result = csvProcessor.process();
 *
 * // Process JSON data - same algorithm, different implementation
 * var jsonProcessor = new JsonDataProcessor(jsonObjects);
 * result = jsonProcessor.process();
 *
 * // Polymorphic processing
 * List<DataProcessor<?>> processors = List.of(csvProcessor, jsonProcessor);
 * processors.forEach(DataProcessor::process);
 * }</pre>
 * <p>
 * <strong>Abstract Methods vs. Hook Methods:</strong>
 * <ul>
 *   <li><strong>Abstract methods</strong>: Must be implemented by subclasses</li>
 *   <li><strong>Hook methods</strong>: Optional, have default implementation (often empty)</li>
 * </ul>
 * <p>
 * <strong>When to use Template Method:</strong>
 * <ul>
 *   <li>Multiple classes share same algorithm structure</li>
 *   <li>Want to control the algorithm's invariant parts</li>
 *   <li>Need to enforce a specific sequence of operations</li>
 *   <li>Building a framework with extension points</li>
 * </ul>
 * <p>
 * <strong>Real-world applications:</strong>
 * <ul>
 *   <li>JUnit test lifecycle (@BeforeEach, @Test, @AfterEach)</li>
 *   <li>Spring templates (JdbcTemplate, RestTemplate)</li>
 *   <li>ETL pipelines (Extract, Transform, Load)</li>
 *   <li>Servlet filters (doFilter method)</li>
 *   <li>Build tool lifecycles (Maven, Gradle)</li>
 * </ul>
 *
 * @since 1.0
 * @see edu.trincoll.patterns.template.TemplateMethodDemo
 */
package edu.trincoll.patterns.template;
