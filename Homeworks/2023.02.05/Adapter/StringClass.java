package org.example;

import java.util.ArrayList;

public class StringClass {
    private final ArrayList<String> stringList;

    public StringClass() {
        this.stringList = new ArrayList<>();
    }

    public StringClass(ArrayList<String> stringList) {
        this.stringList = new ArrayList<>();
        for (String s : stringList) {
            addString(s);
        }
    }

    public void addString(String s) {
        stringList.add(s);
    }

    public ArrayList<String> getStringList() {
        return stringList;
    }
}
