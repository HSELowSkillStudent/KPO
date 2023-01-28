package org.example;


import org.junit.Test;

import java.util.HashMap;

public class Tester {

    private boolean findNeeded(String givenProduct, HashMap<String, String> hashMap) {
        for (String key : hashMap.keySet()) {

        }
        return true;
    }

    public static void Check(HashMap<String, String> hashMap, String expectedProduct, String test) throws RuntimeException {
        Person person = new Person(hashMap);
        String actualProduct;

        try {
            actualProduct = Converter.getJsonString(person);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(test + " generate actual product exception");
        }

//         System.out.println(actualProduct);
//         System.out.println(expectedProduct);

        if (!expectedProduct.equals(actualProduct)) {
            throw new RuntimeException(test + " Exception");
        }
    }

    @Test
    public void runTests() {
        everythingIncludedTest();
        withoutAgeTest();
        withoutAgeAndWeightTest();
        withoutEverything();
        withoutFavouriteFood();
        withoutBothNames();
    }

    @Test
    public void everythingIncludedTest() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("firstName", "Tester");
            put("name", "Testerov");
            put("age", "17");
            put("weight", "75");
            put("favouriteFood", "apple");
        }};

        Check(hashMap, """
                {
                "firstName": "Tester",\s
                "name": "Testerov",\s
                "weight": 75,\s
                "age": 17
                }
                """.strip(), "everythingIncludedTest");
    }

    @Test
    public void withoutAgeTest() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("firstName", "Tester");
            put("name", "Testerov");
            put("weight", "75");
            put("favouriteFood", "apple");
        }};

        Check(hashMap, """
                {
                "firstName": "Tester",\s
                "name": "Testerov",\s
                "weight": 75,\s
                "age": "null"
                }
                """.strip(), "withoutAgeTest");
    }

    @Test
    public void withoutAgeAndWeightTest() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("firstName", "Tester");
            put("name", "Testerov");
            put("favouriteFood", "banana");
        }};

        Check(hashMap, """
                {
                "firstName": "Tester",\s
                "name": "Testerov",\s
                "age": "null"
                }
                """.strip(), "withoutAgeAndWeightTest");
    }

    @Test
    public void withoutEverything() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>();

        Check(hashMap, """
                {
                "firstName": "null",\s
                "name": "null",\s
                "age": "null"
                }
                """.strip(), "withoutEverything");
    }

    @Test
    public void withoutFavouriteFood() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("firstName", "Tester");
            put("name", "Testerov");
            put("age", "17");
            put("weight", "75");
        }};

        Check(hashMap, """
                {
                "firstName": "Tester",\s
                "name": "Testerov",\s
                "weight": 75,\s
                "age": 17
                }
                """.strip(), "withoutFavouriteFood");
    }

    @Test
    public void withoutBothNames() throws RuntimeException {
        HashMap<String, String> hashMap = new HashMap<>() {{
            put("firstName", "null");
            put("name", "null");
            put("age", "1");
            put("weight", "5");
            put("favouriteFood", "apple");
        }};

        Check(hashMap, """
                {
                "firstName": "null",\s
                "name": "null",\s
                "weight": 5,\s
                "age": 1
                }
                """.strip(), "withoutBothNames");
    }
}
