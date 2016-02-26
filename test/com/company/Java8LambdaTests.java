package com.company;


import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8LambdaTests {

    private String stringify(List<String> stringList) {
        String longString = stringList.stream().map(Object::toString).reduce((t,u) -> t + " " + u).get();
        return longString;
    }

    @Test
    public void testComparableStringsReverseSortOrderUsingLambda() {
        List<String> stringList = Arrays.asList(new String[] { "Goodbye", "Hello" });
        stringList.sort( (x,y) -> y.compareToIgnoreCase(x) );
        assertThat(stringify(stringList), is("Hello Goodbye"));
    }

    @Test
    public void testComparableStringsSortOrderUsingComparator() {
        Comparator<String> stdSort = String::compareToIgnoreCase;
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(stdSort);
        assertThat(stringify(stringList), is("Goodbye Hello"));
    }

    @Test
    public void testComparableStringsReverseSortOrderUsingComparator() {
        Comparator<String> reverseSort = Collections.reverseOrder(String::compareToIgnoreCase);
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(reverseSort);
        assertThat(stringify(stringList), is("Hello Goodbye"));
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

}
