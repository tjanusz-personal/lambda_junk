package com.company;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgrammerUtils {

    public List<Programmer> filterProgrammersBy(List<Programmer> programmers, Predicate<Programmer> predicate) {
        List<Programmer> filteredProgrammers = programmers.stream().filter(predicate).collect(Collectors.toList());
        return filteredProgrammers;
    }

    public boolean allProgrammersMatch(List<Programmer> programmers, Predicate<Programmer> predicate) {
        return programmers.stream().allMatch(predicate);
    }

    public Optional<Programmer> findFirstProgrammerMatching(List<Programmer> programmers, Predicate<Programmer> predicate) {
        return programmers.stream().filter(predicate).findFirst();
    }

    public String returnFirstNamesOnlyAsString(List<Programmer> programmers) {
        String firstNames = programmers.stream().map(Programmer::getFirstName)
                .reduce((acc, item) -> acc + " " + item)
                .get();
        return firstNames;
    }

    public String returnFirstNamesMatching(List<Programmer> programmers, Predicate<Programmer> predicate) {
        String firstNames = programmers.stream().filter(predicate)
                .map(Programmer::getFirstName)
                .reduce((acc, item) -> acc + " " + item)
                .get();
        return firstNames;
    }

    public List<Programmer> increaseProgrammerSalaryByAmount(List<Programmer> programmers, int amountToIncrease) {
        programmers.forEach( (p) -> p.setSalary(p.getSalary() + amountToIncrease));
        return programmers;
    }

    public Map<String, List<Programmer>> groupProgrammersBy(List<Programmer> programmers, Predicate<Programmer> predicate,
                                                                  Function<Programmer,String> functionToCall) {
        return programmers.stream().filter(predicate).collect(Collectors.groupingBy(functionToCall));
    }

    public int totalSalaryForProgrammers(List<Programmer> programmers, Predicate<Programmer> predicate) {
        return programmers.stream().filter(predicate)
                .map(Programmer::getSalary)
                .reduce(0, (a, b) -> a + b);
    }

    public List<Programmer> findTopProgrammers(List<Programmer> programmers, Predicate<Programmer> predicate,
                                                  Comparator<Programmer> sortOrder, int limit) {
        List<Programmer> topTwo = programmers.stream().filter(predicate)
                .sorted(sortOrder)
                .limit(limit)
                .collect(Collectors.toList());
        return topTwo;
    }

    public String findAllProgrammersWithAddresses(List<Programmer> programmers) {
        Predicate<Programmer> hasAddress = (programmer -> programmer.getAddress().isPresent());
        return programmers.stream().filter(hasAddress).map(Programmer::getFirstName)
                .reduce((acc, item) -> acc + " " + item).get();
    }

    public void throwNamedEx(String message) throws Exception {
        throw new Exception(message);
    }

    public int totalForProgrammers(List<Programmer> programmers, Predicate<Programmer> predicate,
                                   Function<Programmer,Integer> functionToCall) {
        // support null Predicates using ternary operator to substitute forced true lambda
        Predicate<Programmer> calledPredicate = (predicate == null) ? x -> true : predicate;
        return programmers.stream().filter(calledPredicate)
                .map(functionToCall)
                .reduce(0, (a, b) -> a + b);
    }

}
