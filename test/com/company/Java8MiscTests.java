package com.company;


import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
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
    public void messWithJava8DateTime() {
        System.out.println(LocalDateTime.now().plusHours(2));
    }


}
