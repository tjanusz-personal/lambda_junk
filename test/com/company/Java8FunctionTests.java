package com.company;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Java8FunctionTests {

    @Test
    public void testFunctionSimpleExample() {
        Function<Boolean, Integer> nonZeroIfTrue = (shouldReturnNumber) -> {
            if (shouldReturnNumber) {
                return 1;
            }
            else return 0;
        };
        assertThat(nonZeroIfTrue.apply(true), is(1));
        assertThat(nonZeroIfTrue.apply(false), is(0));
    }

    @Test
    public void testIntFunctionWorksCorrectly() {
        IntFunction<Boolean> isEven = (number) -> (number % 2) == 0;
        assertTrue(isEven.apply(2));
        assertTrue(isEven.apply(8));
        assertFalse(isEven.apply(13));
    }

    @Test
    public void testBiFunctionWorksCorrectlyToAddTwoInts() {
        BiFunction<Integer, Integer, Integer> sumInts = (a, b) -> a + b;
        Integer result = sumInts.apply(1,10);
        assertThat(result, is(11));
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

    private Function<Programmer, Programmer> defaultSalaryTo50K = programmer -> {
        programmer.setSalary(50000);
        return programmer;
    };

    private Function<Programmer, Programmer> determineHighlyCompensated = programmer -> {
        programmer.determineHighlyCompensated();
        return programmer;
    };

    private Programmer createDummyProgrammerWithSalary(int salary) {
        Programmer programmerInstance = new Programmer("Tim","Janusz","","male",48,salary);
        return programmerInstance;
    }

    @Test
    public void testComposeMultipleFunctionsWorksFromRightToLeft() {
        Programmer programmerInstance = createDummyProgrammerWithSalary(10);
        // determine BEFORE default salary (Compose)
        defaultSalaryTo50K.compose(determineHighlyCompensated).apply(programmerInstance);
        assertThat(programmerInstance.getSalary(), is(50000));
        assertThat(programmerInstance.isHighlyCompensated(), is(false));
    }

    @Test
    public void testAndThenFunctionsWorksLeftToRight() {
        Programmer programmerInstance = createDummyProgrammerWithSalary(10);
        // determine AFTER default salary (andThen)
        defaultSalaryTo50K.andThen(determineHighlyCompensated).apply(programmerInstance);
        assertThat(programmerInstance.getSalary(), is(50000));
        assertThat(programmerInstance.isHighlyCompensated(), is(true));
    }

    @Test
    public void testFunctionIdentityReturnsArgument() {
        Function<Integer, Integer> asId = Function.identity();
        assertThat(asId.apply(3), is(3));
        assertNull(asId.apply(null));
    }

    @Test
    public void testFunctionIdentityUsedInCollectorsMapping() {
        List<String> stringList = Arrays.asList( new String[] { "One", "Two", "Three"});
        Map<String, String> stringMapUppercaseKeys = stringList.stream().collect(
            Collectors.toMap((String k) ->  k.toUpperCase(), Function.identity()));
        assertThat(stringMapUppercaseKeys.get("ONE"), is("One"));

        Map<String, String> stringMapUppercaseValues = stringList.stream().collect(
                Collectors.toMap((String k) -> k, p -> p.toUpperCase()));
        assertThat(stringMapUppercaseValues.get("One"), is("ONE"));
    }


}
