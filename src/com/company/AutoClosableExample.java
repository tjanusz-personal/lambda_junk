package com.company;


public class AutoClosableExample implements AutoCloseable {

    public static final String CLOSE_MESSAGE = "Custom auto invoked!";

    public boolean doIt(boolean throwEx) {
        if (throwEx) {
            Object obj = null;
            obj.toString();
        }
        return true;
    }

    public boolean stringIsGreeting(String stringToCompare) {
        switch (stringToCompare) {
            case "HELLO":
            case "GOODBYE":
                return true;
            default:
                return false;
        }
    }

    @Override
    public void close() throws Exception {
        System.out.print(CLOSE_MESSAGE);
    }

    private void privateClose() {
        System.out.print(CLOSE_MESSAGE);
    }
}
