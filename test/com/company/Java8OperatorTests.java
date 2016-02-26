package com.company;


import org.junit.Test;

import java.util.function.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8OperatorTests {

    @Test
    public void testUnaryOperator() {
        UnaryOperator<String> upcase = str -> str.toUpperCase();
        assertThat(upcase.apply("Hello"), is("HELLO"));
    }

    @Test
    public void testIntUnaryOperator() {
        IntUnaryOperator addDefaultValueOfThree = (initialValue) -> initialValue + 3;
        assertThat(addDefaultValueOfThree.applyAsInt(3), is(6));
    }

    @Test
    public void testLongUnaryOperator() {
        LongUnaryOperator addDefaultValueOfThree = (initialValue) -> initialValue + 3;
        assertThat(addDefaultValueOfThree.applyAsLong(3), is(6L));
    }

    @Test
    public void testBinaryOperatorWithStrings() {
        BinaryOperator<String> concat = (left, right) -> left + right;
        assertThat(concat.apply("Hello ", "World"), is("Hello World"));
    }

    @Test
    public void testIntBinaryOperatorWorks() {
        IntBinaryOperator returnLargest = (left, right) -> {
            if (left > right) {
                return left;
            } else {
                return right;
            }
        };
        assertThat(returnLargest.applyAsInt(100,200), is(200));
    }

    @Test
    public void testDoubleBinaryOperatorWorks() {
        DoubleBinaryOperator returnLargest = (left, right) -> {
            if (left > right) {
                return left;
            }
            return right;
        };
        assertThat(returnLargest.applyAsDouble(100.25,200.25), is(200.25));
    }

}
