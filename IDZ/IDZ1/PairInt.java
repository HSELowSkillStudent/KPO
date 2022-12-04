package org.example;

public class PairInt {
    private int first;
    private int second;

    PairInt(int a, int b) {
        setFirst(a);
        setSecond(b);
    }

    PairInt() {
        this(0, 0);
    }

    int getFirst() {
        return first;
    }

    int getSecond() {
        return second;
    }

    void setFirst(int value) {
        first = value;
    }

    void setSecond(int value) {
        second = value;
    }
}

