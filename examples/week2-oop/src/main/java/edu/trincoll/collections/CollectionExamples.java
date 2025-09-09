package edu.trincoll.collections;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionExamples {
    
    public static class ListExamples {
        
        public List<String> demonstrateArrayList() {
            List<String> languages = new ArrayList<>();
            
            languages.add("Java");
            languages.add("Python");
            languages.add("JavaScript");
            languages.add("Java");
            
            languages.set(1, "Kotlin");
            
            languages.add(2, "Go");
            
            languages.removeLast();
            
            return languages;
        }
        
        public List<Integer> demonstrateLinkedList() {
            LinkedList<Integer> numbers = new LinkedList<>();
            
            numbers.addFirst(10);
            numbers.addLast(20);
            numbers.addFirst(5);
            numbers.addLast(30);
            
            numbers.removeFirst();
            numbers.removeLast();
            
            return numbers;
        }
        
        public List<String> filterAndTransform(List<String> input) {
            return input.stream()
                    .filter(s -> s.length() > 3)
                    .map(String::toUpperCase)
                    .sorted()
                    .collect(Collectors.toList());
        }
        
        public List<Integer> removeDuplicates(List<Integer> numbers) {
            return numbers.stream()
                    .distinct()
                    .collect(Collectors.toList());
        }
    }
    
    public static class SetExamples {
        
        public Set<String> demonstrateHashSet() {
            Set<String> uniqueTags = new HashSet<>();
            
            uniqueTags.add("java");
            uniqueTags.add("spring");
            uniqueTags.add("java");
            uniqueTags.add("microservices");
            
            return uniqueTags;
        }
        
        public Set<Integer> demonstrateTreeSet() {

            Set<Integer> sortedScores = new TreeSet<>(Arrays.asList(85, 92, 78, 92, 88, 95));
            
            return sortedScores;
        }
        
        public Set<String> findIntersection(Set<String> set1, Set<String> set2) {
            Set<String> intersection = new HashSet<>(set1);
            intersection.retainAll(set2);
            return intersection;
        }
        
        public Set<String> findUnion(Set<String> set1, Set<String> set2) {
            Set<String> union = new HashSet<>(set1);
            union.addAll(set2);
            return union;
        }
        
        public Set<String> findDifference(Set<String> set1, Set<String> set2) {
            Set<String> difference = new HashSet<>(set1);
            difference.removeAll(set2);
            return difference;
        }
    }
    
    public static class MapExamples {
        
        public Map<String, Integer> demonstrateHashMap() {
            Map<String, Integer> wordCount = new HashMap<>();
            
            wordCount.put("hello", 1);
            wordCount.put("world", 2);
            wordCount.put("hello", wordCount.getOrDefault("hello", 0) + 1);
            
            wordCount.compute("java", (k, v) -> v == null ? 1 : v + 1);
            
            wordCount.putIfAbsent("spring", 0);
            wordCount.computeIfPresent("spring", (k, v) -> v + 1);
            
            return wordCount;
        }
        
        public Map<String, List<String>> groupByFirstLetter(List<String> words) {
            return words.stream()
                    .collect(Collectors.groupingBy(
                            word -> word.substring(0, 1).toUpperCase()
                    ));
        }
        
        public Map<Integer, Long> countByLength(List<String> words) {
            return words.stream()
                    .collect(Collectors.groupingBy(
                            String::length,
                            Collectors.counting()
                    ));
        }
        
        public Map<String, Integer> invertMap(Map<Integer, String> original) {
            return original.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getValue,
                            Map.Entry::getKey,
                            (existing, replacement) -> existing
                    ));
        }
        
        public Map<String, Double> calculateAverages(Map<String, List<Integer>> scores) {
            Map<String, Double> averages = new HashMap<>();
            
            scores.forEach((name, scoreList) -> {
                double average = scoreList.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);
                averages.put(name, average);
            });
            
            return averages;
        }
    }
    
    public static class QueueExamples {
        
        public Queue<String> demonstratePriorityQueue() {
            Queue<String> taskQueue = new PriorityQueue<>();
            
            taskQueue.offer("Low priority task");
            taskQueue.offer("Critical task");
            taskQueue.offer("Medium priority task");
            
            List<String> processed = new ArrayList<>();
            while (!taskQueue.isEmpty()) {
                processed.add(taskQueue.poll());
            }
            
            return taskQueue;
        }
        
        public Deque<String> demonstrateArrayDeque() {
            Deque<String> deque = new ArrayDeque<>();
            
            deque.addFirst("First");
            deque.addLast("Last");
            deque.offerFirst("New First");
            deque.offerLast("New Last");
            
            deque.peekFirst();
            deque.peekLast();
            
            return deque;
        }
    }
    
    public static class CollectionUtilities {
        
        @SafeVarargs
        public final <T> List<T> getImmutableList(T... elements) {
            return List.of(elements);
        }
        
        @SafeVarargs
        public final <T> Set<T> getImmutableSet(T... elements) {
            return Set.of(elements);
        }
        
        public <K, V> Map<K, V> getImmutableMap(K key1, V value1, K key2, V value2) {
            return Map.of(key1, value1, key2, value2);
        }
        
        public <T> List<T> defensiveCopy(List<T> original) {
            return new ArrayList<>(original);
        }
        
        public <T> List<T> synchronizedList(List<T> list) {
            return Collections.synchronizedList(list);
        }
        
        public <T extends Comparable<T>> void sortList(List<T> list) {
            Collections.sort(list);
        }
        
        public <T> void reverseList(List<T> list) {
            Collections.reverse(list);
        }
        
        public <T> void shuffleList(List<T> list) {
            Collections.shuffle(list);
        }
        
        public <T> T findMax(Collection<T> collection, Comparator<T> comparator) {
            return Collections.max(collection, comparator);
        }
        
        public <T> T findMin(Collection<T> collection, Comparator<T> comparator) {
            return Collections.min(collection, comparator);
        }
    }
}