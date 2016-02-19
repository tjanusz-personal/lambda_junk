package com.company;


import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.function.*;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

    public static List<String> greetFolks(Function<String, String> greeter) {
        List<String> greetings = new ArrayList<String>();
        for(String name : Arrays.asList("Alice", "Bob", "Cathy")) {
            greetings.add(greeter.apply(name));
        }
        return greetings;
    }

    @Test
    public void testLambdaBiFunctions() {
        BiFunction<String, String, String> concat = (a, b) -> a + b;
        List<String> greetings = greetFolks(whom -> concat.apply(" Hello ", whom));

        StringBuffer resultString = new StringBuffer();
        greetings.forEach(greeting -> resultString.append(greeting));
        assertThat(resultString.toString().trim(), equalTo("Hello Alice Hello Bob Hello Cathy"));
    }

    @Test
    public void testConsumerFunctionInterface() {
        StringBuffer buffer = new StringBuffer();
        Consumer<String> doGreet = name -> buffer.append(" Hello " + name);
        for (String name : Arrays.asList("Alice", "Bob", "Cathy")) {
            doGreet.accept(name);  // tells consumer to do its method
        }
        assertThat(buffer.toString().trim(), equalTo("Hello Alice Hello Bob Hello Cathy"));
    }

    @Test
    public void testBinaryOperator() {
        BinaryOperator<String> concat = (left, right) -> left + right;
        assertThat(concat.apply("Hello ", "World"), equalTo("Hello World"));
    }

    @Test
    public void testUnaryOperator() {
        UnaryOperator<String> upcase = str -> str.toUpperCase();
        assertThat(upcase.apply("Hello"), equalTo("HELLO"));
    }

    @Test
    public void testPredicateSingleArgument() {
        Predicate<String> notNullOrEmpty = s -> (s != null) && (s.length() > 0);
        assertTrue(notNullOrEmpty.test("Hello"));
        assertFalse(notNullOrEmpty.test(""));
    }

    @Test
    public void testBiPredicateTwoArguments() {
        BiPredicate<String, String> notNull = (string1, string2) -> (string1 != null) && (string2 != null);
        assertTrue(notNull.test("","Hello"));
        assertFalse(notNull.test(null, null));
    }

    private String stringify(List<String> stringList) {
        String longString = stringList.stream().map(Object::toString).reduce((t,u) -> t + " " + u).get();
        return longString;
    }

    @Test
    public void testComparableStringsReverseSortOrderUsingLambda() {
        List<String> stringList = Arrays.asList(new String[] { "Goodbye", "Hello" });
        stringList.sort( (x,y) -> y.compareToIgnoreCase(x) );
        assertThat(stringify(stringList), equalTo("Hello Goodbye"));
    }

    @Test
    public void testComparableStringsSortOrderUsingComparator() {
        Comparator<String> stdSort = String::compareToIgnoreCase;
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(stdSort);
        assertThat(stringify(stringList), equalTo("Goodbye Hello"));
    }

    @Test
    public void testComparableStringsReverseSortOrderUsingComparator() {
        Comparator<String> reverseSort = Collections.reverseOrder(String::compareToIgnoreCase);
        List<String> stringList = Arrays.asList(new String[] { "Hello", "Goodbye" });
        stringList.sort(reverseSort);
        assertThat(stringify(stringList), equalTo("Hello Goodbye"));
    }


}
