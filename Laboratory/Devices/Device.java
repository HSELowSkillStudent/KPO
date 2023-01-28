package org.example.Devices;

public interface Device {
    boolean isEnabled();
    void turnOn();
    void turnOff();
    int getChannel();
    int getMaxChannel();
    void setChannel(int channel);
    void setVolume(int volume);
    int getVolume();
    int getMaxVolume();
    String getName();
}
