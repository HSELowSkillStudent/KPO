package org.example;

import java.util.ArrayList;

import static org.example.Constants.*;

public class Printer {

    static void clearConsole() {
        System.out.print(CLEAR_CONSOLE);
    }

    static void displayInfoAboutProbablySteps(ArrayList<PairInt> probably_steps) {
        printlnString(PROBABLY_COORDINATES);
        for (PairInt coordinates : probably_steps) {
            char letter_x = (char) ('A' + coordinates.getFirst());
            int pos_y = coordinates.getSecond() + 1;
            System.out.print(letter_x + EMPTY_STRING + pos_y + SPACE);
        }
        newLine();
    }

    static void printChar(char c) {
        System.out.print(c);
    }

    static void formatIntToChar(int i) {
        System.out.format("%c", i);
    }

    static void newLine() {
        System.out.println();
    }

    static void printInt(int i) {
        System.out.print(i);
    }

    static void printString(String value) {
        System.out.print(value);
    }

    static void printlnString(String value) {
        System.out.println(value);
    }

//    static void formatString(String value) {
//        System.out.print(value);
//    }

    static void printList(String... a) {
        for (String s : a) {
            System.out.print(s);
        }
    }

    static void printlnList(String... a) {
        printList(a);
        newLine();
    }
}
