package org.example;

import java.util.List;

public class ConsolePrinter implements Printer {
    /**
     * Prints the object to the console, and then completes the line.
     *
     * @param <T>  type of the text
     * @param text the text to be output
     */
    @Override
    public <T> void displayInfo(T text) {
        System.out.println(text);
    }

    /**
     * Outputs a list of objects from a new line to the console, and then completes the line.
     *
     * @param <T>      type of the text
     * @param listText list the text to be output
     */
    @Override
    public <T> void displayListInfo(List<T> listText) {
        for (Object el : listText) {
            displayInfo(el);
        }
    }
}
