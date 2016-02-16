package com.company;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgrammerUtils {

    public List<Programmer> findProgrammersBy(List<Programmer> programmers, Predicate<Programmer> predicate) {
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

    public int totalSalaryForProgrammers(List<Programmer> programmers, Predicate<Programmer> predicate) {
        return programmers.stream().filter(predicate)
                .map(Programmer::getSalary)
                .reduce(0, (a, b) -> a + b);
    }

    public void throwNamedEx(String message) throws Exception {
        throw new Exception(message);
    }

}
