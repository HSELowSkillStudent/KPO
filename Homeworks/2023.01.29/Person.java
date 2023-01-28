package org.example;

import java.util.HashMap;

@JsonSerializable
public final class Person {

    @JsonElement
    private String firstName;

    // @JsonElement("name")
    @JsonElement(key="name")
    private String lastName;

    @JsonElement
    private String age;

    @IgnoreNull
    @JsonElement
    private String weight;

    private String favouriteFood;

    public Person(HashMap<String, String> characteristics) {
        this.firstName = characteristics.get("firstName");
        this.lastName = characteristics.get("name");
        this.age = characteristics.get("age");
        this.weight = characteristics.get("weight");
        this.favouriteFood = characteristics.get("favouriteFood");
    }
}
