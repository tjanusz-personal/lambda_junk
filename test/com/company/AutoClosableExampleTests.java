package com.company;


import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class AutoClosableExampleTests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private AutoClosableExample example;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        example = new AutoClosableExample();
    }

    @After
    public void cleanUpStreams() {
        System.setOut(System.err);
    }

    @Test
    public void testAutoCloseMethodClosesOnNormalExecution() throws Exception {
        try(AutoClosableExample example = new AutoClosableExample()) {
            assertTrue(example.doIt(false));
        }
        assertThat(outContent.toString(), is(AutoClosableExample.CLOSE_MESSAGE));
    }

    @Test(expected = NullPointerException.class)
    public void testAutoCloseMethodClosesEvenAfterExceptionThrown() throws Exception {
        try(AutoClosableExample example = new AutoClosableExample()) {
            example.doIt(true);
            fail();
        }
        assertThat(outContent.toString(), is(AutoClosableExample.CLOSE_MESSAGE));
    }

    @Test
    public void testDiamondOperatorExample() {
        // showing Generic doesn't need to specify on both sides..
        List<AutoClosableExample> closableExampleList = new ArrayList<>();
        assertThat(closableExampleList.size(), is(0));
    }

    @Test
    public void testSwitchStringExampleWorks() {
        HashMap<String, Boolean> scenarios = new HashMap<String, Boolean>() {
            {
                put("HELLO", Boolean.TRUE);
                put("YoYo", Boolean.FALSE);
                put("GOODBYE", Boolean.TRUE);
            }
        };
        scenarios.forEach((greeting, expectedResult) -> assertThat(example.stringIsGreeting(greeting), is(expectedResult)));
    }

    @Test
    public void testNumericUnderscoreLiteralsAreDefinedCorrectly() {
        assertThat(1_000, is(1000));
        assertThat(1_000_000, is(1000000));
    }

    @Test
    public void testMultipleExceptionsCatchingWorks() {
        try {
            example.doIt(true);
            fail();
        } catch (NullPointerException | ArithmeticException ex) {
            // expected behavior
            assertTrue(true);
        }
    }

    @Test
    public void testInvokeDynamicMethodWorks() throws Throwable {
        // Dynamically find the "close" method and invoke it
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findVirtual(AutoClosableExample.class, "close", MethodType.methodType(void.class));
        methodHandle.invoke(example);
        assertThat(outContent.toString(), is(AutoClosableExample.CLOSE_MESSAGE));
    }

    @Test(expected = IllegalAccessException.class)
    public void testInvokeDynamicPrivateMethodThrowsIllegalAccessException() throws Throwable {
        // Dynamically find the "close" method and invoke it
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findVirtual(AutoClosableExample.class, "privateClose", MethodType.methodType(void.class));
        methodHandle.invoke(example);
        assertThat(outContent.toString(), is(AutoClosableExample.CLOSE_MESSAGE));
    }

}
