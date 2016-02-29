package com.company;


import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class Java8CollectorTests {

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

    private Stream<Programmer> getHardCodedJavaProgrammersStream() {
        return this.getHardcodedJavaProgrammerList().stream();
    }

    private Function<Programmer, String> concatPlayerNameFunction = p -> {
        StringBuffer buffer = new StringBuffer();
        buffer.append(p.getFirstName()).append(" ").append(p.getLastName());
        return buffer.toString();
    };

    @Test
    public void testToMapCreatesMapOfProgrammerNamesToAges() {
        Map<String, Integer> programmersByAge = getHardCodedJavaProgrammersStream().collect(
                Collectors.toMap(concatPlayerNameFunction, y -> y.getAge())
        );
        assertThat(programmersByAge.get("Aaron Kuehler"), is(43));
        assertThat(programmersByAge.get("Terry Finn"), is(23));
    }

    @Test
    public void testAveragingIntAveragesProgrammerAges() {
        Double result = getHardCodedJavaProgrammersStream().collect(Collectors.averagingInt(p -> p.getAge()));
        assertThat(result, is(33.0));
    }

    @Test
    public void testCountingReturnsTotalNumberOfProgrammers() {
        Long total = getHardCodedJavaProgrammersStream().collect(Collectors.counting());
        assertThat(total, is(4L));
    }

    @Test
    public void testJoiningReturnsSingleStringOfProgrammerFullNames() {
        String result = getHardCodedJavaProgrammersStream().map(concatPlayerNameFunction).collect(joining(", ","(",")"));
        assertThat(result, is("(Aaron Kuehler, Terry Finn, Jeremiah Lusby, Tim Janusz)"));
    }

    @Test
    public void testSummarizingIntSummarizesTheProgrammerAges() {
        IntSummaryStatistics summaryStatistics = getHardCodedJavaProgrammersStream().collect(Collectors.summarizingInt(Programmer::getAge));
        assertThat(summaryStatistics.getSum(), is(132L));
        assertThat(summaryStatistics.getCount(), is(4L));
        assertThat(summaryStatistics.getAverage(), is(33.0));
    }

    @Test
    public void testMaxByReturnsMaxProgrammerSalary() {
        Comparator<Programmer> integerComparator = Comparator.comparingInt(Programmer::getSalary);
        Optional<Programmer> optional = getHardCodedJavaProgrammersStream().collect(Collectors.maxBy(integerComparator));
        assertThat(optional.get().getFirstName(), is("Tim"));
    }

    @Test
    public void testMinByReturnsMinProgrammerSalary() {
        Comparator<Programmer> integerComparator = Comparator.comparingInt(Programmer::getSalary);
        Optional<Programmer> optional = getHardCodedJavaProgrammersStream().collect(Collectors.minBy(integerComparator));
        assertThat(optional.get().getFirstName(), is("Aaron"));
    }

    @Test
    public void testPartitioningReturnsMapOfProgrammersBySex() {
        Predicate<Programmer> maleProgrammersPredicate = p -> p.getGender().equalsIgnoreCase("male");
        Map<Boolean, List<Programmer>> maleProgrammerMap = getHardCodedJavaProgrammersStream().collect(Collectors.partitioningBy(maleProgrammersPredicate));
        assertThat(maleProgrammerMap.get(true).size(), is(3));
        assertThat(maleProgrammerMap.get(false).size(), is(1));
    }

    @Test
    public void testPartitioningReturnsMapOfProgrammersBySexUsingSetNotList() {
        Predicate<Programmer> maleProgrammersPredicate = p -> p.getGender().equalsIgnoreCase("male");
        Map<Boolean, Set<Programmer>> maleProgrammerMap = getHardCodedJavaProgrammersStream().collect(
                Collectors.partitioningBy(maleProgrammersPredicate, Collectors.toSet()));
        assertThat(maleProgrammerMap.get(true).size(), is(3));
        assertThat(maleProgrammerMap.get(false).size(), is(1));
    }

    @Test
    public void testGroupingBySexAndReducingReturnsMapKeyedBySexWithHighestSalary() {
        BinaryOperator<Programmer> bySalaryReducer = ((programmer, programmer2) -> {
            return programmer.getSalary() > programmer2.getSalary() ? programmer : programmer2;
        });
        Map<String, Optional<Programmer>> theMap = getHardCodedJavaProgrammersStream().collect(
                Collectors.groupingBy(Programmer::getGender,
                Collectors.reducing(bySalaryReducer)
        ));
        assertThat(theMap.get("male").get().getFirstName(), is("Tim"));
        assertThat(theMap.get("female").get().getFirstName(), is("Terry"));
    }

    @Test
    public void testGroupingByAgeAndReducingBySalaryReturnsMap() {
        BinaryOperator<Programmer> bySalaryReducer = ((programmer, programmer2) -> {
            return programmer.getSalary() > programmer2.getSalary() ? programmer : programmer2;
        });
        Map<Integer, Optional<Programmer>> theMap = getHardCodedJavaProgrammersStream().collect(
                Collectors.groupingBy(Programmer::getAge,
                        Collectors.reducing(bySalaryReducer)
                ));
        assertThat(theMap.get(23).get().getFirstName(), is("Terry"));
        assertThat(theMap.get(33).get().getFirstName(), is("Tim"));
        assertThat(theMap.get(43).get().getFirstName(), is("Aaron"));
    }

    @Test
    public void testCollectAllProgrammersOver30AndReturnAverageAge() {
        Predicate<Programmer> ageOver30 = (programmer -> programmer.getAge() > 30);
        Double result = this.getHardCodedJavaProgrammersStream().filter(ageOver30).collect(
                Collectors.averagingInt(Programmer::getAge)
        );
        assertThat(result, is(closeTo(36.333, .003)));
    }

    @Test
    public void testCollectingAndThenForProgrammersCanModifyExistingCollectorResult() {
        // TODO: Not really sure what this does..  Not finding lots of examples of this usage anywhere on web.
        Set<Programmer> programmers = getHardCodedJavaProgrammersStream().collect(Collectors.collectingAndThen(
           Collectors.toSet(), Collections::unmodifiableSet
        ));
        assertThat(programmers.size(), is(4));
    }

    @Test
    public void testMappingReturnsProgrammersByAge() {
        Map<Integer, Set<String>> theMap = getHardCodedJavaProgrammersStream().collect(Collectors.groupingBy(Programmer::getAge,
                mapping(Programmer::getFirstName, toSet())));
        assertThat(theMap.get(23).size(), is(1));
        assertTrue(theMap.get(23).contains("Terry"));
        assertThat(theMap.get(33).size(), is(2));
        String [] oldGuysArray = new String[] { "Jeremiah", "Tim"};
        assertThat(theMap.get(33).toArray(), is(oldGuysArray));
    }


}
