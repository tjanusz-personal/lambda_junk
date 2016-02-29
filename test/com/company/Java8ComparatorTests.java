package com.company;


import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8ComparatorTests {

    private String stringify(List<String> stringList) {
        String longString = stringList.stream().map(Object::toString).reduce((t,u) -> t + " " + u).get();
        return longString;
    }

    @Test
    public void testSortStringsReverseSortOrderUsingLambda() {
        List<String> stringList = Arrays.asList(new String[] { "Goodbye", "Hello" });
        stringList.sort( (x,y) -> y.compareToIgnoreCase(x) );
        assertThat(stringify(stringList), is("Hello Goodbye"));
    }

    @Test
    public void testSortStringsSortOrderUsingComparator() {
        Comparator<String> stdSort = String::compareToIgnoreCase;
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(stdSort);
        assertThat(stringify(stringList), is("Goodbye Hello"));
    }

    @Test
    public void testSortStringsReverseSortOrderUsingCollectionsReverseOrderComparator() {
        Comparator<String> reverseSort = Comparator.reverseOrder();
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(reverseSort);
        assertThat(stringify(stringList), is("Hello Goodbye"));
    }

    private List<Programmer> getHardcodedJavaProgrammerList() {
        return new ArrayList<Programmer>() {
            {
                add(new Programmer("Aaron", "Kuehler", "Java programmer", "male", 43, 1000));
                add(new Programmer("Terry", "Finn", "Java programmer", "female", 23, 1500));
                add(new Programmer("Jeremiah", "Lusby", "Java programmer", "male", 33, 1500));
                add(new Programmer("Tim", "Janusz", "Java programmer", "male", 33, 2000));
            }
        };
    }

    private void assertProgrammerListHasFirstNameString(List<Programmer> programmerList, String nameStringToMatch) {
        String result = programmerList.stream().map(Programmer::getFirstName).collect(Collectors.joining(", "));
        assertThat(result, is(nameStringToMatch));
    }

    @Test
    public void testComparatorSortingUsingComparingFirstNameMethodReference() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.sort(comparing(Programmer::getFirstName));
        assertProgrammerListHasFirstNameString(initialList, "Aaron, Jeremiah, Terry, Tim");
    }

    @Test
    public void testComparatorSortingUsingComparingIntAgeMethodReference() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.sort(Comparator.comparingInt(Programmer::getAge));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Jeremiah, Tim, Aaron");
    }

    @Test
    public void testComparatorSortUsingNullsLastToSortByJobType() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.get(0).setJob(null);  // set aaron to null
        initialList.sort(comparing(Programmer::getJob,
                        Comparator.nullsLast(String::compareTo)));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Jeremiah, Tim, Aaron");
    }

    @Test
    public void testComparatorSortUsingNullsFirstToSortByJobType() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.get(3).setJob(null);  // set Tim to null
        initialList.sort(comparing(Programmer::getJob,
                Comparator.nullsFirst(String::compareTo)));
        assertProgrammerListHasFirstNameString(initialList, "Tim, Aaron, Terry, Jeremiah");
    }

    @Test
    public void testThenComparingSortingProgrammersByGenderThenAge() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.sort(comparing(Programmer::getGender).thenComparing(
                comparing(Programmer::getAge)));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Jeremiah, Tim, Aaron");
    }

    @Test
    public void testThenComparingIntSortingProgrammersByGenderThenAge() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.sort(comparing(Programmer::getGender).thenComparingInt(
                Programmer::getAge));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Jeremiah, Tim, Aaron");
    }

    @Test
    public void testThenComparingIntSortingProgrammersByGenderThenAgeAllStaticMethods() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.sort(comparing(Programmer::getGender).thenComparingInt(Programmer::getAge));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Jeremiah, Tim, Aaron");
    }

    @Test
    public void testComparingIntSortingProgrammersByAgeThenByGenderAllStaticMethods() {
        List<Programmer> initialList = this.getHardcodedJavaProgrammerList();
        initialList.get(3).setGender("female");
        initialList.sort(comparingInt(Programmer::getAge).thenComparing(Programmer::getGender, String::compareTo));
        assertProgrammerListHasFirstNameString(initialList, "Terry, Tim, Jeremiah, Aaron");
    }

}
