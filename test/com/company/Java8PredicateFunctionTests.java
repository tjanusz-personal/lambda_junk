package com.company;


import org.junit.Test;

import java.util.*;
import java.util.function.*;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Java8PredicateFunctionTests {

    public static List<String> greetFolks(Function<String, String> greeter) {
        List<String> greetings = new ArrayList<String>();
        for(String name : Arrays.asList("Alice", "Bob", "Cathy")) {
            greetings.add(greeter.apply(name));
        }
        return greetings;
    }

    @Test
    public void testBiFunctionWorksCorrectly() {
        BiFunction<String, String, String> concat = (a, b) -> a + b;
        List<String> greetings = greetFolks(whom -> concat.apply(" Hello ", whom));

        StringBuffer resultString = new StringBuffer();
        greetings.forEach(greeting -> resultString.append(greeting));
        assertThat(resultString.toString().trim(), is("Hello Alice Hello Bob Hello Cathy"));
    }

    @Test
    public void testIntFunctionWorksCorrectly() {
        IntFunction<Boolean> isEven = (number) -> (number % 2) == 0;
        assertTrue(isEven.apply(2));
        assertTrue(isEven.apply(8));
        assertFalse(isEven.apply(13));
    }

    @Test
    public void testToIntBiFunctionWorks() {
        ToIntBiFunction<Programmer, Programmer> salaryAverage = ((programmer, programmer2) -> {
            return (programmer.getAge() + programmer2.getSalary()) / 2;
        });
        Programmer programmer = new Programmer("Tom", "Jones", "Job", "Male", 45, 50000);
        Programmer programmer2 = new Programmer("Joe", "Blow", "Job", "Male", 45, 60000);
        assertThat(salaryAverage.applyAsInt(programmer,programmer2), is(30022));
    }

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


}
