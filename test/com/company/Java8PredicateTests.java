package com.company;


import org.junit.Test;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class Java8PredicateTests {

    @Test
    public void testPredicateSingleArgument() {
        Predicate<String> notNullOrEmpty = s -> (s != null) && (s.length() > 0);
        assertTrue(notNullOrEmpty.test("Hello"));
        assertFalse(notNullOrEmpty.test(""));
    }

    @Test
    public void testIntPredicateWorks() {
        IntPredicate isEven = number -> (number % 2) == 0;
        assertTrue(isEven.test(4));
        assertTrue(isEven.test(8));
        assertFalse(isEven.test(5));
    }

    @Test
    public void testBiPredicateTwoArguments() {
        BiPredicate<String, String> notNull = (string1, string2) -> (string1 != null) && (string2 != null);
        assertTrue(notNull.test("","Hello"));
        assertFalse(notNull.test(null, null));
    }

    // Simple example predicates to use for test
    private IntPredicate isEven = number -> (number % 2) == 0;
    private IntPredicate isNonZero = number -> number != 0;

    @Test
    public void testPredicateAndWorks() {
        assertFalse(isNonZero.and(isEven).test(1));
        assertTrue(isNonZero.and(isEven).test(10));
    }

    @Test
    public void testPredicateOrWorks() {
        IntPredicate isGreaterThanZero = number -> number > 0;
        assertTrue(isEven.or(isGreaterThanZero).test(11));
        assertFalse(isEven.or(isGreaterThanZero).test(-1));
    }

    @Test
    public void testPredicateNegateWorks() {
        assertFalse(isEven.negate().test(10));
        assertTrue(isEven.negate().test(1));
    }

    @Test
    public void testPredicateIsEqualWorks() {
        Programmer programmer = new Programmer("Joe", "Jones", "Programmer", "male", 25, 50000);
        Programmer programmer2 = new Programmer("Joe", "Jones", "Programmer", "male", 25, 50000);
        assertTrue(Predicate.isEqual(100).test(100));
        assertTrue(Predicate.isEqual(programmer).test(programmer2));
        // same everything but age is different
        Programmer programmer3 = new Programmer("Joe", "Jones", "Programmer", "male", 30, 50000);
        assertFalse(Predicate.isEqual(programmer).test(programmer3));
    }
}
