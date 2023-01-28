package org.example.Factories;

import org.example.Devices.Radio;
import org.example.Devices.TV;

public interface AbstractFactory {
    TV createTV();
    Radio createRadio();
}
