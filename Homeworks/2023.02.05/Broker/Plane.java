package org.example;

public class Plane {
    private final String name;

    public Plane(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void landing() {
        System.out.println("Plane " + name + " is landing");
    }
}
