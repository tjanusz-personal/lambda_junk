package com.company;


import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8ConsumerSupplierTests {

    @Test
    public void testConsumerFunctionInterface() {
        StringBuffer buffer = new StringBuffer();
        Consumer<String> doGreet = name -> buffer.append("Hello " + name);
        doGreet.accept("Alice");  // tells consumer to do its method
        assertThat(buffer.toString().trim(), is("Hello Alice"));
    }

    @Test
    public void testBiConsumerFunctionInterface() {
        StringBuffer buffer = new StringBuffer();
        BiConsumer<String, String> greetTwoPeople = (name1, name2) -> buffer.append("Hello ").append(name1).append(" And ").append(name2);
        greetTwoPeople.accept("Bill", "Jane");
        assertThat(buffer.toString(), is("Hello Bill And Jane"));
    }

    private String echoStringSupplied(Supplier<String> stringSupplier) {
        return stringSupplier.get();
    }

    @Test
    public void testSupplierFunctionInterface() {
        Supplier<String> hardCodeName = () -> "Hard Coded Name";
        assertThat(this.echoStringSupplied(hardCodeName), is("Hard Coded Name"));
    }

    private int shouldAdd50To(int amount, BooleanSupplier booleanSupplier) {
        if (booleanSupplier.getAsBoolean()) {
            return amount + 50;
        } else {
            return amount;
        }
    }

    @Test
    public void testBooleanSupplierInterface() {
        BooleanSupplier hardCodeTrue = () -> true;
        assertThat(this.shouldAdd50To(10, hardCodeTrue), is(60));
    }
}
