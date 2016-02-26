package com.company;


import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Java8MapTests {

    private Map<String, Integer> getHardCodedMapWithTwoValues() {
        return new HashMap<String, Integer>() {
            {
                put("One", 1);
                put("Two", 2);
            }
        };
    }

    private List<String> getHardCodedListWithTwoValues() {
        return new ArrayList<String>() {
            {
                add("One");
                add("Two");
            }
        };
    }

    @Test
    public void testMapForEachUsingBiConsumer() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        final StringBuffer integerStringBuffer = new StringBuffer();
        stringIntegerMap.forEach((name, count) -> {
            if (name.equalsIgnoreCase("Two")) {
              integerStringBuffer.append(count);
            }
        });
        assertThat(integerStringBuffer.toString(), is("2"));
    }

    @Test
    public void testListIteratorForEachRemaining() {
        List<String> stringList = this.getHardCodedListWithTwoValues();
        StringBuffer buffer = new StringBuffer();
        Iterator it = stringList.iterator();
        it.forEachRemaining( (theString) -> buffer.append(theString));
        assertThat(buffer.toString(), is("OneTwo"));
    }

    @Test
    public void testSpliteratorForEachRemaining() {
        List<String> stringList = this.getHardCodedListWithTwoValues();
        StringBuffer buffer = new StringBuffer();
        Spliterator<String> spliterator = stringList.spliterator();
        spliterator.forEachRemaining( (theString) -> buffer.append(theString));
        assertThat(buffer.toString(), is("OneTwo"));
    }

    @Test
    public void testComputeIfAbsentAdsMissingValue() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.computeIfAbsent("Three", (newValue) -> {
           return 3;
        });
        assertThat(stringIntegerMap.containsKey("Three"), is(true));
        assertThat(stringIntegerMap.get("Three"), is(3));
    }

    @Test
    public void testComputeIfAbsentModifiesIgnoresExistingValue() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.computeIfAbsent("Two", (newValue) -> {
            return 3;
        });
        assertThat(stringIntegerMap.containsKey("Two"), is(true));
        assertThat(stringIntegerMap.get("Two"), is(2));
    }

    @Test
    public void testComputeIfPresentModifiesExistingMatchingKeyValue() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.computeIfPresent("Two", (key, value) -> value + 5);
        assertThat(stringIntegerMap.containsKey("Two"), is(true));
        assertThat(stringIntegerMap.get("Two"), is(7));
    }

    @Test
    public void testMergeModifiesExistingMatchingMapItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.merge("Two", 2, (key, value) -> value + 5);
        assertThat(stringIntegerMap.get("Two"), is(7));
    }

    @Test
    public void testMergeAddsMissingMapItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.merge("Three", 3, (key, value) -> value + 5);
        assertThat(stringIntegerMap.get("Three"), is(3));
    }

    @Test
    public void testComputeModifiesExistingItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.compute("Two", (key, value) -> value + 5);
        assertThat(stringIntegerMap.get("Two"), is(7));
    }

    @Test
    public void testComputeAddsNewItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.compute("Three", (key, value) -> {
            if (value == null) {
                return 3;
            }
            return value + 5;
        });
        assertThat(stringIntegerMap.get("Three"), is(3));
    }

    @Test
    public void testComputeRemovesExistingItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.compute("Two", (key, value) -> { return null; });
        assertThat(stringIntegerMap.containsKey("Two"), is(false));
    }

    @Test
    public void testGetOrDefaultReturnsExistingItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        assertThat(stringIntegerMap.getOrDefault("Two", 7), is(2));
    }

    @Test
    public void testGetOrDefaultReturnsDefaultItem() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        assertThat(stringIntegerMap.getOrDefault("Three", 7), is(7));
    }

    @Test
    public void testGetOrDefaultReturnsNullItemAndNotDefault() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.put("Three", null);
        assertThat(stringIntegerMap.getOrDefault("Three", 7), equalTo(null));
    }

    @Test
    public void testReplaceAllUpdatesAllItems() {
        Map<String, Integer> stringIntegerMap = this.getHardCodedMapWithTwoValues();
        stringIntegerMap.replaceAll( (key, value) -> value + 10);
        assertThat(stringIntegerMap.get("One"), equalTo(11));
        assertThat(stringIntegerMap.get("Two"), equalTo(12));
        stringIntegerMap.replaceAll( (key, value) -> {
            if (key.equalsIgnoreCase("One")) {
                return value;
            } else {
                return value + 20;
            }
        });
        assertThat(stringIntegerMap.get("One"), equalTo(11));
        assertThat(stringIntegerMap.get("Two"), equalTo(32));
    }

}
