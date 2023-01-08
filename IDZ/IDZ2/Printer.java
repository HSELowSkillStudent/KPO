package org.example;

import java.util.List;

public interface Printer {
    /**
     * Prints an {@link Object} and then terminates the line.
     *
     * @param <T>  type of the text
     * @param text the text to be output
     */
    <T> void displayInfo(T text);

    /**
     * Prints a list of object from a new line and then terminates the line.
     *
     * @param <T>      type of the text
     * @param listText list the text to be output
     */
    <T> void displayListInfo(List<T> listText);
}
