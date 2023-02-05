package org.example;

import java.util.ArrayList;

public class Adapter {
    public static ArrayList<String> converterIntToString(ArrayList<Integer> intList) {
        ArrayList<String> stringList = new ArrayList<>();
        for (int i : intList) {
            stringList.add(String.valueOf(i));
        }
        return stringList;
    }

    public static ArrayList<Integer> converterStringToInt(ArrayList<String> stringList) {
        ArrayList<Integer> intList = new ArrayList<>();
        for (String s : stringList) {
            try {
                intList.add(findIntInString(s));
            } catch (NumberFormatException e) {
                intList.add(0);
            }
        }
        return intList;
    }

    public static int findIntInString(String s) {
        int value = 0;
        int degree = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                value += (s.charAt(i) - '0') * Math.pow(10, degree);
                degree++;
            }
        }
        return value;
    }
}
