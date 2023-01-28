package org.example.Factories;

import org.example.Devices.Radio;
import org.example.Devices.TV;

public class SamsungFactory implements AbstractFactory {
    @Override
    public TV createTV() {
        String tvId = Integer.toString((int) (Math.random() * 1000));
        return new TV("LgTV-" + tvId, 60);
    }

    @Override
    public Radio createRadio() {
        String radioId = Integer.toString((int) (Math.random() * 1000));
        return new Radio("LgRadio-" + radioId, 100);
    }
}
