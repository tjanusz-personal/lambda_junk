package com.company;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class Java8MiscTests {

    @Test
    public void testJavaScriptEngine() throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("JavaScript");
        double result = (double) engine.eval(" function f() { return 1; }; f() + 1;");
        assertThat(result, is(2.0));
    }

    @Test
    public void testComparator() {
        List<String> strings = Arrays.asList("C", "a", "A", "b");
        Collections.sort(strings, String::compareToIgnoreCase);
        assertThat(strings, is(Arrays.asList("a", "A","b", "C")));
    }

    @Test
    public void testOptionalScenarioMapWorks() {
        Optional<Programmer> programmer = Optional.of(new Programmer());
        assertThat(programmer.isPresent(), is(true));
        assertThat(programmer.map(Programmer::getFirstName).orElse("None"), is("None"));
        programmer.get().setFirstName("TestFirstName");
        assertThat(programmer.map(Programmer::getFirstName).orElse("None"), is("TestFirstName"));
    }

    @Test
    public void testOptionalScenarioFlatMapWorks() {
        Optional<Programmer> optProgrammer = Optional.of(new Programmer());
        Optional<Address> optAddress = Optional.of(new Address());

        assertThat(optProgrammer.map(Programmer::getAddress).orElse(null), is(Optional.empty()));

        optProgrammer.get().setAddress(optAddress);
        String state = optProgrammer.flatMap(Programmer::getAddress).map(Address::getState).orElse("NA");
        assertThat(state, is("NA"));

        optAddress.get().setState("PA");
        state = optProgrammer.flatMap(Programmer::getAddress).map(Address::getState).orElse("NA");
        assertThat(state, is("PA"));

        Predicate<Address> isInPA = (address -> address.getState().equals("PA"));
        assertThat(optAddress.filter(isInPA), not(Optional.empty()));
    }

    @Test
    public void testOptionalFilterExampleWorks() {
        Optional<Programmer> optProgrammer = Optional.of(new Programmer());
        Optional<Address> optAddress = Optional.of(new Address());
        optAddress.get().setState("NJ");
        optProgrammer.get().setAddress(optAddress);
        Predicate<Address> hasStatePA = (address -> "PA".equalsIgnoreCase(address.getState()));
        String state = optProgrammer.flatMap(Programmer::getAddress).filter(hasStatePA).map(Address::getState).orElse("Unk");
        assertThat(state, is("Unk"));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testObjectsThrowsNullPointerExWithCorrectText() {
        Object theObject = null;
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("Null Object");
        Objects.requireNonNull(theObject, "Null Object");
    }

    @Test
    public void testObjectsToStringReturnsNullStringOnNullObject() {
        Object theObject = null;
        assertThat(Objects.toString(theObject), is("null"));
        theObject = new Object();
        assertThat(Objects.toString(theObject), containsString("java.lang.Object"));
    }


}
