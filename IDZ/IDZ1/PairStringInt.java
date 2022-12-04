package org.example;

public class PairStringInt {
    private final String string;
    private final int digit;

    PairStringInt(String string, int digit) {
        this.string = string;
        this.digit = digit;
    }

    String getString() {
        return string;
    }

    int getDigit() {
        return digit;
    }

}
