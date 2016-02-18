package com.company;


import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class Java8MiscTests {

    @Test
    public void testJavaScriptEngine() throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");
        System.out.println(engine.getClass().getName());
        double result = (double) engine.eval(" function f() { return 1; }; f() + 1;");
        assertThat(result, equalTo(2.0));
        System.out.println("Result: " + engine.eval(" function f() { return 1; }; f() + 1;"));
    }

    @Test
    public void testComparator() {
        List<String> strings = Arrays.asList("C", "a", "A", "b");
        System.out.println(strings);
        Collections.sort(strings, String::compareToIgnoreCase);
        System.out.println(strings);
    }

    @Test
    public void optionalScenarioMapWorks() {
        Optional<Programmer> programmer = Optional.of(new Programmer());
        assertThat(programmer.isPresent(), equalTo(true));
        assertThat(programmer.map(Programmer::getFirstName).orElse("None"), equalTo("None"));
        programmer.get().setFirstName("TestFirstName");
        assertThat(programmer.map(Programmer::getFirstName).orElse("None"), equalTo("TestFirstName"));
    }

    @Test
    public void optionalScenarioFlatMapWorks() {
        Optional<Programmer> optProgrammer = Optional.of(new Programmer());
        Optional<Address> optAddress = Optional.of(new Address());

        assertThat(optProgrammer.map(Programmer::getAddress).orElse(null), equalTo(Optional.empty()));

        optProgrammer.get().setAddress(optAddress);
        String state = optProgrammer.flatMap(Programmer::getAddress).map(Address::getState).orElse("NA");
        assertThat(state, equalTo("NA"));

        optAddress.get().setState("PA");
        state = optProgrammer.flatMap(Programmer::getAddress).map(Address::getState).orElse("NA");
        assertThat(state, equalTo("PA"));

        Predicate<Address> isInPA = (address -> address.getState().equals("PA"));
        assertThat(optAddress.filter(isInPA), not(Optional.empty()));
    }

    @Test
    public void optionalFilterExampleWorks() {
        Optional<Programmer> optProgrammer = Optional.of(new Programmer());
        Optional<Address> optAddress = Optional.of(new Address());
        optAddress.get().setState("NJ");
        optProgrammer.get().setAddress(optAddress);
        Predicate<Address> hasStatePA = (address -> "PA".equalsIgnoreCase(address.getState()));
        String state = optProgrammer.flatMap(Programmer::getAddress).filter(hasStatePA).map(Address::getState).orElse("Unk");
        assertThat(state, equalTo("Unk"));
    }


}
