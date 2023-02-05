package org.example;

import static java.lang.Math.random;


public class Main {
    public static void main(String[] args) throws Exception {
        if (random() * 10 > 5) {
            IntClass intList = new IntClass();
            for (int i = 0; i < 10; i++) {
                intList.addInt((int) (random() * 100));
            }

            System.out.println("intList: " + intList.getIntList());

            // StringClass stringClass = new StringClass(intList.getIntList());  - не работает без адаптера
            StringClass stringClass = new StringClass(Adapter.converterIntToString(intList.getIntList())); // работает с адаптером

            System.out.println("stringClass: " + stringClass.getStringList());
        } else {
            StringClass stringClass = new StringClass();
            for (int i = 0; i < 10; i++) {
                stringClass.addString("a" + String.valueOf((int) (random() * 100)));
            }

            System.out.println("stringClass: " + stringClass.getStringList());

            IntClass intClass = new IntClass(Adapter.converterStringToInt(stringClass.getStringList()));
            System.out.println("intClass: " + intClass.getIntList());
        }
    }
}
