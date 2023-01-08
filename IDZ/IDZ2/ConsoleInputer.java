package org.example;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleInputer implements Inputer {
    Scanner scanner = new Scanner(System.in);

    /**
     * Advances this scanner past the current line and returns the input that was skipped.
     *
     * @return Advances this scanner past the current line and returns the input that was skipped.
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException  if this scanner is closed
     */
    @Override
    public String nextLine() throws NoSuchElementException, IllegalStateException {
        return scanner.nextLine();
    }
}
