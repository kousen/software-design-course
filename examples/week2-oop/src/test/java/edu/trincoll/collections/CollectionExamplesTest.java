package edu.trincoll.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class CollectionExamplesTest {
    
    @Nested
    @DisplayName("List Examples Tests")
    class ListExamplesTests {
        private CollectionExamples.ListExamples listExamples;
        
        @BeforeEach
        void setUp() {
            listExamples = new CollectionExamples.ListExamples();
        }
        
        @Test
        @DisplayName("Should demonstrate ArrayList operations")
        void testArrayListOperations() {
            List<String> result = listExamples.demonstrateArrayList();
            
            assertThat(result)
                    .hasSize(4)
                    .containsExactly("Java", "Kotlin", "Go", "JavaScript");
        }
        
        @Test
        @DisplayName("Should demonstrate LinkedList operations")
        void testLinkedListOperations() {
            List<Integer> result = listExamples.demonstrateLinkedList();
            
            assertThat(result)
                    .hasSize(2)
                    .containsExactly(10, 20);
        }
        
        @Test
        @DisplayName("Should filter and transform list")
        void testFilterAndTransform() {
            List<String> input = List.of("hi", "hello", "world", "a", "java");
            List<String> result = listExamples.filterAndTransform(input);
            
            assertThat(result)
                    .hasSize(3)
                    .containsExactly("HELLO", "JAVA", "WORLD");
        }
        
        @Test
        @DisplayName("Should remove duplicates from list")
        void testRemoveDuplicates() {
            List<Integer> input = List.of(1, 2, 3, 2, 4, 3, 5);
            List<Integer> result = listExamples.removeDuplicates(input);
            
            assertThat(result)
                    .hasSize(5)
                    .containsExactly(1, 2, 3, 4, 5);
        }
    }
    
    @Nested
    @DisplayName("Set Examples Tests")
    class SetExamplesTests {
        private CollectionExamples.SetExamples setExamples;
        
        @BeforeEach
        void setUp() {
            setExamples = new CollectionExamples.SetExamples();
        }
        
        @Test
        @DisplayName("Should demonstrate HashSet uniqueness")
        void testHashSetUniqueness() {
            Set<String> result = setExamples.demonstrateHashSet();
            
            assertThat(result)
                    .hasSize(3)
                    .contains("java", "spring", "microservices");
        }
        
        @Test
        @DisplayName("Should demonstrate TreeSet sorting")
        void testTreeSetSorting() {
            Set<Integer> result = setExamples.demonstrateTreeSet();
            
            assertThat(result)
                    .hasSize(4)
                    .containsExactly(78, 85, 88, 92, 95);
        }
        
        @Test
        @DisplayName("Should find set intersection")
        void testSetIntersection() {
            Set<String> set1 = Set.of("Java", "Python", "JavaScript");
            Set<String> set2 = Set.of("Java", "C++", "JavaScript");
            
            Set<String> intersection = setExamples.findIntersection(set1, set2);
            
            assertThat(intersection)
                    .hasSize(2)
                    .containsExactlyInAnyOrder("Java", "JavaScript");
        }
        
        @Test
        @DisplayName("Should find set union")
        void testSetUnion() {
            Set<String> set1 = Set.of("A", "B");
            Set<String> set2 = Set.of("B", "C");
            
            Set<String> union = setExamples.findUnion(set1, set2);
            
            assertThat(union)
                    .hasSize(3)
                    .containsExactlyInAnyOrder("A", "B", "C");
        }
        
        @Test
        @DisplayName("Should find set difference")
        void testSetDifference() {
            Set<String> set1 = Set.of("A", "B", "C");
            Set<String> set2 = Set.of("B", "C", "D");
            
            Set<String> difference = setExamples.findDifference(set1, set2);
            
            assertThat(difference)
                    .hasSize(1)
                    .contains("A");
        }
    }
    
    @Nested
    @DisplayName("Map Examples Tests")
    class MapExamplesTests {
        private CollectionExamples.MapExamples mapExamples;
        
        @BeforeEach
        void setUp() {
            mapExamples = new CollectionExamples.MapExamples();
        }
        
        @Test
        @DisplayName("Should demonstrate HashMap operations")
        void testHashMapOperations() {
            Map<String, Integer> result = mapExamples.demonstrateHashMap();
            
            assertThat(result)
                    .containsEntry("hello", 2)
                    .containsEntry("world", 2)
                    .containsEntry("java", 1)
                    .containsEntry("spring", 1);
        }
        
        @Test
        @DisplayName("Should group words by first letter")
        void testGroupByFirstLetter() {
            List<String> words = List.of("apple", "banana", "apricot", "cherry", "carrot");
            Map<String, List<String>> result = mapExamples.groupByFirstLetter(words);
            
            assertThat(result.get("A")).containsExactlyInAnyOrder("apple", "apricot");
            assertThat(result.get("B")).containsExactly("banana");
            assertThat(result.get("C")).containsExactlyInAnyOrder("cherry", "carrot");
        }
        
        @Test
        @DisplayName("Should count words by length")
        void testCountByLength() {
            List<String> words = List.of("a", "bb", "ccc", "dd", "e");
            Map<Integer, Long> result = mapExamples.countByLength(words);
            
            assertThat(result)
                    .containsEntry(1, 2L)
                    .containsEntry(2, 2L)
                    .containsEntry(3, 1L);
        }
        
        @Test
        @DisplayName("Should invert map")
        void testInvertMap() {
            Map<Integer, String> original = Map.of(1, "one", 2, "two", 3, "three");
            Map<String, Integer> inverted = mapExamples.invertMap(original);
            
            assertThat(inverted)
                    .containsEntry("one", 1)
                    .containsEntry("two", 2)
                    .containsEntry("three", 3);
        }
        
        @Test
        @DisplayName("Should calculate averages from map")
        void testCalculateAverages() {
            Map<String, List<Integer>> scores = Map.of(
                    "Alice", List.of(90, 85, 95),
                    "Bob", List.of(80, 75, 85)
            );
            
            Map<String, Double> averages = mapExamples.calculateAverages(scores);
            
            assertThat(averages.get("Alice")).isEqualTo(90.0);
            assertThat(averages.get("Bob")).isEqualTo(80.0);
        }
    }
    
    @Nested
    @DisplayName("Collection Utilities Tests")
    class CollectionUtilitiesTests {
        private CollectionExamples.CollectionUtilities utilities;
        
        @BeforeEach
        void setUp() {
            utilities = new CollectionExamples.CollectionUtilities();
        }
        
        @Test
        @DisplayName("Should create immutable collections")
        void testImmutableCollections() {
            List<String> immutableList = utilities.getImmutableList("a", "b", "c");
            Set<Integer> immutableSet = utilities.getImmutableSet(1, 2, 3);
            Map<String, Integer> immutableMap = utilities.getImmutableMap("one", 1, "two", 2);
            
            assertThatThrownBy(() -> immutableList.add("d"))
                    .isInstanceOf(UnsupportedOperationException.class);
            
            assertThatThrownBy(() -> immutableSet.add(4))
                    .isInstanceOf(UnsupportedOperationException.class);
            
            assertThatThrownBy(() -> immutableMap.put("three", 3))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
        
        @Test
        @DisplayName("Should create defensive copy")
        void testDefensiveCopy() {
            List<String> original = new ArrayList<>(List.of("a", "b", "c"));
            List<String> copy = utilities.defensiveCopy(original);
            
            copy.add("d");
            
            assertThat(original).hasSize(3);
            assertThat(copy).hasSize(4);
        }
        
        @Test
        @DisplayName("Should sort list")
        void testSortList() {
            List<Integer> numbers = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9));
            utilities.sortList(numbers);
            
            assertThat(numbers).containsExactly(1, 1, 3, 4, 5, 9);
        }
        
        @Test
        @DisplayName("Should reverse list")
        void testReverseList() {
            List<String> letters = new ArrayList<>(List.of("a", "b", "c", "d"));
            utilities.reverseList(letters);
            
            assertThat(letters).containsExactly("d", "c", "b", "a");
        }
        
        @Test
        @DisplayName("Should find min and max")
        void testFindMinMax() {
            List<String> words = List.of("apple", "zebra", "banana", "mango");
            
            String min = utilities.findMin(words, Comparator.naturalOrder());
            String max = utilities.findMax(words, Comparator.naturalOrder());
            
            assertThat(min).isEqualTo("apple");
            assertThat(max).isEqualTo("zebra");
        }
    }
}