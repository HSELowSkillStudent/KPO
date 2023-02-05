package org.example;

import static java.lang.Math.random;
import static java.lang.Thread.sleep;


public class Main {
    public static void main(String[] args) throws Exception {
        Dispatcher dispatcher = new Dispatcher();
        // Самолеты добавляются в очередь каждые 3 секунды(прилетают в аэропорт и начинают кружится над ним)
        // и каждые 3 секунды пытаются садиться на посадку (если есть свободные места)
        // Свободные места появляются путем проверки равенства random() * 10 % 3 и 0
        while (true) {
            sleep(3000);
            System.out.println("____________________________________________________");
            int r = (int) (random() * 100);
            Plane plane = new Plane("Plane " + r);
            dispatcher.addPlane(plane);
            if ((int) (random() * 10) % 3 == 0) {
                dispatcher.landPlane();
            }
        }
    }
}
