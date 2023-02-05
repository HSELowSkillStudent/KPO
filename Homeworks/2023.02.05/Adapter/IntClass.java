package org.example;

import java.util.ArrayList;

public class IntClass {
    private final ArrayList<Integer> intList;

    public IntClass(ArrayList<Integer> intList) {
        this.intList = new ArrayList<>();
        for (int integer : intList) {
            addInt(integer);
        }
    }

    public IntClass() {
        this.intList = new ArrayList<>();
    }

    public void addInt(int i) {
        intList.add(i);
    }



    public ArrayList<Integer> getIntList() {
        return intList;
    }
}
