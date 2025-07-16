package edu.trincoll;

import net.jqwik.api.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FizzBuzzTest {
    
    @Property
    boolean every_third_element_starts_with_Fizz(@ForAll("divisibleBy3") int i) {
        return fizzBuzz().get(i - 1)
                .startsWith("Fizz");
    }

    @Property
    boolean every_fifth_element_ends_with_Buzz(@ForAll("divisibleBy5") int i) {
        return fizzBuzz().get(i - 1)
                .endsWith("Buzz");
    }

    @Property
    boolean every_fifteenth_element_is_FizzBuzz(@ForAll("divisibleBy15") int i) {
        return fizzBuzz().get(i - 1)
                .equals("FizzBuzz");
    }

    @Property
    boolean numbers_not_divisible_by_3_or_5_return_themselves(@ForAll("notDivisibleBy3Or5") int i) {
        return fizzBuzz().get(i - 1)
                .equals(String.valueOf(i));
    }

    @Provide
    Arbitrary<Integer> divisibleBy3() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 3 == 0 && i % 5 != 0);
    }

    @Provide
    Arbitrary<Integer> divisibleBy5() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 5 == 0 && i % 3 != 0);
    }

    @Provide
    Arbitrary<Integer> divisibleBy15() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 15 == 0);
    }

    @Provide
    Arbitrary<Integer> notDivisibleBy3Or5() {
        return Arbitraries.integers()
                .between(1, 100)
                .filter(i -> i % 3 != 0 && i % 5 != 0);
    }

    private List<String> fizzBuzz() {
        return IntStream.rangeClosed(1, 100)
                .mapToObj(UtilityMethods::int2fizzbuzz)
                .collect(Collectors.toList());
    }
}