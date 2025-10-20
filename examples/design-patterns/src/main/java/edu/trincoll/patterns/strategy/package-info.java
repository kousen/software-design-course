/**
 * Strategy Pattern implementation using modern Java features.
 * <p>
 * The Strategy pattern defines a family of algorithms, encapsulates each one,
 * and makes them interchangeable. Strategy lets the algorithm vary independently
 * from clients that use it.
 * <p>
 * <strong>Modern Java Implementation:</strong>
 * <ul>
 *   <li>Uses {@link java.util.function.Function} instead of custom strategy interfaces</li>
 *   <li>Strategies can be lambda expressions</li>
 *   <li>Method references can serve as strategies</li>
 *   <li>Easy to compose and combine strategies</li>
 *   <li>Works seamlessly with Stream API</li>
 * </ul>
 * <p>
 * <strong>Examples in this package:</strong>
 * <ul>
 *   <li>{@link edu.trincoll.patterns.strategy.ShippingCalculator} - Multiple shipping cost strategies</li>
 *   <li>{@link edu.trincoll.patterns.strategy.PayrollProcessor} - Multiple payroll calculation strategies</li>
 * </ul>
 * <p>
 * <strong>Traditional vs. Modern:</strong>
 * <pre>{@code
 * // Traditional approach (pre-Java 8)
 * interface Strategy {
 *     double calculate(Data data);
 * }
 *
 * class ConcreteStrategy implements Strategy {
 *     public double calculate(Data data) {
 *         return data.value() * 2;
 *     }
 * }
 *
 * // Modern approach (Java 8+)
 * Function<Data, Double> strategy = data -> data.value() * 2;
 * }</pre>
 * <p>
 * <strong>When to use Strategy Pattern:</strong>
 * <ul>
 *   <li>You have multiple algorithms for the same task</li>
 *   <li>You need to switch algorithms at runtime</li>
 *   <li>You want to isolate algorithm implementation from usage</li>
 *   <li>You have conditional logic that's hard to maintain</li>
 * </ul>
 * <p>
 * <strong>Real-world use cases:</strong>
 * <ul>
 *   <li>Payment processing (credit card, PayPal, Bitcoin)</li>
 *   <li>Compression algorithms (ZIP, GZIP, RAR)</li>
 *   <li>Sorting algorithms (quick sort, merge sort)</li>
 *   <li>Validation rules (email, phone, credit card)</li>
 *   <li>Pricing strategies (standard, discount, promotional)</li>
 * </ul>
 * <p>
 * <strong>Relationship to SOLID Principles:</strong>
 * <ul>
 *   <li><strong>Open-Closed Principle:</strong> Add new strategies without modifying context</li>
 *   <li><strong>Single Responsibility:</strong> Each strategy has one job</li>
 *   <li><strong>Dependency Inversion:</strong> Context depends on abstraction (Function)</li>
 * </ul>
 *
 * @since 1.0
 * @see edu.trincoll.patterns.strategy.ShippingCalculator
 * @see edu.trincoll.patterns.strategy.PayrollProcessor
 * @see edu.trincoll.patterns.strategy.StrategyPatternDemo
 */
package edu.trincoll.patterns.strategy;
