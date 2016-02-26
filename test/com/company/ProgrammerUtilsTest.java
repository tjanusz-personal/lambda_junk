package com.company;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Created by timothy.janusz on 2/16/2016.
 */
public class ProgrammerUtilsTest {
    private ProgrammerUtils utils;

    List<Programmer> javaProgrammers;

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

    @Before
    public void setUp() {

        utils = new ProgrammerUtils();
        this.javaProgrammers = getHardcodedJavaProgrammerList();
    }

    @Test
    public void testFilterProgrammersByAgeReturnsCorrectResults() throws Exception {
        Predicate<Programmer> ageFilter = (programmer -> programmer.getAge() > 30);
        List<Programmer> resultList = utils.filterProgrammersBy(javaProgrammers, ageFilter);
        assertThat(resultList.size(), is(3));
    }

    @Test
    public void testFilterProgrammersByFirstNameStartingWithLetterTReturnsCorrectResults() throws Exception {
        Predicate<Programmer> ageFilter = (programmer -> programmer.getFirstName().startsWith("T"));
        List<Programmer> resultList = utils.filterProgrammersBy(javaProgrammers, ageFilter);
        assertThat(resultList.size(), is(2));
    }

    @Test
    public void testAllProgrammersMatchReturnsTrueForAgePredicate() throws Exception {
        Predicate<Programmer> ageFilter = (p) -> (p.getAge() > 20);
        assertThat(utils.allProgrammersMatch(this.javaProgrammers, ageFilter), is(true));
    }

    @Test
    public void testFindFirstProgrammerMatchingFindsPlayersLessThanMaxAge() throws Exception {
        Integer[] allTrue = { 20, 30 };
        for (Integer scenario : allTrue) {
            Predicate<Programmer> ageFilter = (p) -> (p.getAge() > scenario);
            Optional<Programmer> returnedPlayer = utils.findFirstProgrammerMatching(this.javaProgrammers, ageFilter);
            assertThat(returnedPlayer.isPresent(), is(true));
        }
    }

    @Test
    public void findFirstProgrammerMatchingDoesNotFindProgrammersGreaterThanMaxAge() {
        Integer[] allFalse = { 44, 50};
        for (Integer scenario : allFalse) {
            Predicate<Programmer> ageFilter = (p) -> (p.getAge() > scenario);
            Optional<Programmer> returnedPlayer = utils.findFirstProgrammerMatching(this.javaProgrammers, ageFilter);
            assertThat(returnedPlayer.isPresent(), is(false));
        }
    }

    @Test
    public void returnsFirstNamesOnlyAsStringReturnsCorrectString() {
        String firstNames = utils.returnFirstNamesOnlyAsString(javaProgrammers);
        assertThat(firstNames, is("Aaron Terry Jeremiah Tim"));
    }

    @Test
    public void returnsFirstNamesMatchingFirstLetterCorrectly() {
        Predicate<Programmer> nameFilter = (p) -> (p.getFirstName().startsWith("T"));
        String firstNames = utils.returnFirstNamesMatching(javaProgrammers, nameFilter);
        assertThat(firstNames, is("Terry Tim"));
    }

    @Test
    public void returnsFirstNamesMatchingFirstLetterOfLastNameCorrectly() {
        Predicate<Programmer> nameFilter = (p) -> (p.getLastName().startsWith("J"));
        String firstNames = utils.returnFirstNamesMatching(javaProgrammers, nameFilter);
        assertThat(firstNames, is("Tim"));
    }

    @Test
    public void increasesProgrammerSalaryByAmountUsingForEachModifiesProgrammersInListCorrectly() {
        List<Programmer> programmers = utils.increaseProgrammerSalaryByAmountUsingForEach(javaProgrammers, 100);
        List<Integer> expectedAmounts = Arrays.asList(new Integer[] { 1100, 1600, 1600, 2100 });
        List<Integer> actualAmounts = programmers.stream().map(Programmer::getSalary).collect(Collectors.toList());
        assertThat(expectedAmounts, is(actualAmounts));
    }

    @Test
    public void increasesProgrammerSalaryByAmountUsingReplaceAllModifiesProgrammersInListCorrectly() {
        List<Programmer> programmers = utils.increaseProgrammerSalaryByAmountUsingReplaceAll(javaProgrammers, 200);
        List<Integer> expectedAmounts = Arrays.asList(new Integer[] { 1200, 1700, 1700, 2200 });
        List<Integer> actualAmounts = programmers.stream().map(Programmer::getSalary).collect(Collectors.toList());
        assertThat(expectedAmounts, is(actualAmounts));
    }

    @Test
    public void totalSalaryForProgrammersOlderThanReturnsCorrectResults() {
        Integer[][] scenarios = { {20, 6000}, {40, 1000}, { 30, 4500} };
        for (Integer[] scenario: scenarios) {
            Predicate<Programmer> ageFilter = (p) -> (p.getAge() > scenario[0]);
            int salaryTotal = utils.totalSalaryForProgrammers(this.javaProgrammers, ageFilter);
            assertThat(salaryTotal, is(scenario[1]));
        }
    }

    @Test
    public void sumAllProgrammersSalaries() {
        int value = javaProgrammers.stream().map(Programmer::getSalary).reduce(0, Integer::sum);
        assertThat(value, is(6000));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test(expected = Exception.class)
    public void throwNamedExOldSchool() throws Exception {
        utils.throwNamedEx("Test Ex");
    }

    @Test
    public void throwNamedExUsingRule() throws Exception {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("Unit Test");
        utils.throwNamedEx("Unit Test");
    }

    @Test
    public void testGroupProgrammersByGenderReturnsCorrectBreakdown() {
        Predicate<Programmer> allProgrammers = (programmer) -> true;
        Map<String, List<Programmer>> programmersByGender =  utils.groupProgrammersBy(this.javaProgrammers, allProgrammers, Programmer::getGender);
        assertThat(programmersByGender.get("male").size(), is(3));
        assertThat(programmersByGender.get("female").size(), is(1));
    }

    @Test
    public void testGroupProgrammersByGenderFilteredByAgeReturnsCorrectList() {
        Predicate<Programmer> programmersOlderThan30 = (programmer) -> programmer.getAge() > 30;
        Map<String, List<Programmer>> programmersByGender =  utils.groupProgrammersBy(this.javaProgrammers, programmersOlderThan30, Programmer::getGender);
        assertThat(programmersByGender.get("male").size(), is(3));
        assertThat(programmersByGender, hasKey(not(equalTo("female"))));
    }

    @Test
    public void testFindTopProgrammersReturnsTopTwoSalaried() {
        Comparator<Programmer> sortBySalaryDescending = Collections.reverseOrder(Comparator.comparingInt(Programmer::getSalary));
        Predicate<Programmer> maleProgrammers = (programmer -> programmer.getGender().equals("male"));
        List<Programmer> topTwo = utils.findTopProgrammers(this.javaProgrammers, maleProgrammers, sortBySalaryDescending, 2);
        assertThat(utils.returnFirstNamesOnlyAsString(topTwo), is("Tim Jeremiah"));
    }

    @Test
    public void testFindTopTwoCheapestSalariedProgrammers() {
        Comparator<Programmer> sortBySalaryAscending = Comparator.comparingInt(Programmer::getSalary);
        Predicate<Programmer> maleProgrammers = (programmer -> programmer.getJob().equals("Java programmer"));
        List<Programmer> topTwo = utils.findTopProgrammers(this.javaProgrammers, maleProgrammers, sortBySalaryAscending, 2);
        assertThat(utils.returnFirstNamesOnlyAsString(topTwo), is("Aaron Terry"));
    }

    @Test
    public void testFindAllProgrammersWithAddressesReturnsCorrectNames() {
        Programmer programmer = new Programmer("Tim", "Janusz", "Java programmer", "male", 33, 2000);
        Programmer programmer2 = new Programmer("Joe", "Jones", "Java programmer", "male", 22, 1000);
        programmer.setAddress(Optional.of(new Address()));
        List<Programmer> programmersWithAddresses = new ArrayList<>();
        programmersWithAddresses.add(programmer);
        programmersWithAddresses.add(programmer2);
        String names = utils.findAllProgrammersWithAddresses(programmersWithAddresses);
        assertThat(names, is("Tim"));
    }

    @Test
    public void totalForProgrammersReturnsCorrectResultsForSalary() {
        Integer[][] scenarios = { {20, 6000}, {40, 1000}, { 30, 4500} };
        for (Integer[] scenario: scenarios) {
            Predicate<Programmer> ageFilter = (p) -> (p.getAge() > scenario[0]);
            int salaryTotal = utils.totalForProgrammers(this.javaProgrammers, ageFilter, Programmer::getSalary);
            assertThat(salaryTotal, is(scenario[1]));
        }
    }

    @Test
    public void totalForProgrammersReturnsCorrectResultsForAge() {
        int ageTotal = utils.totalForProgrammers(this.javaProgrammers, x -> true, Programmer::getAge);
        assertThat(ageTotal, is(132));
    }

    @Test
    public void totalForProgrammersReturnsCorrectResultsForAgeWithNullPredicate() {
        int ageTotal = utils.totalForProgrammers(this.javaProgrammers, null, Programmer::getAge);
        assertThat(ageTotal, is(132));
    }

    @Test
    public void removeMaleProgrammersExample() {
        List<Programmer> allJavaProgrammers = this.getHardcodedJavaProgrammerList();
        assertThat(allJavaProgrammers.size(), is(4));
        allJavaProgrammers.removeIf( (programmer -> programmer.getGender().equalsIgnoreCase("male")));
        assertThat(allJavaProgrammers.size(), is(1));
    }


}