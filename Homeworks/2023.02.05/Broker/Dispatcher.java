package org.example;

import java.util.ArrayList;

public class Dispatcher {
    private ArrayList<Plane> planeQueue;

    public Dispatcher() {
        this.planeQueue = new ArrayList<>();
    }

    public void addPlane(Plane plane) {
        planeQueue.add(plane);
        System.out.println("Plane " + plane.getName() + " added to queue");
    }

    public void landPlane() {
        if (planeQueue.size() > 0) {
            Plane plane = planeQueue.get(0);
            sendMessageToPlane(plane);
            plane.landing();
            planeQueue.remove(plane);
            System.out.println("Plane " + plane.getName() + " removed from queue");
        } else {
            System.out.println("No planes in queue");
        }
    }

    private void sendMessageToPlane(Plane plane) {
        System.out.println("Message sent to plane " + plane.getName());
    }

}
