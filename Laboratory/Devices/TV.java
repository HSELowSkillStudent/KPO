package org.example.Devices;

import org.example.SpareParts.Screen;
import org.example.SpareParts.Speakers;

public class TV implements Device {
    private String name;
    private boolean isEnable;
    private int channel;
    private final int maxChannel;
    private final Screen screen;
    private final Speakers speakers;

    public TV(String name, int maxChannel) {
        this.name = name;
        this.channel = 1;
        this.maxChannel = maxChannel;
        this.isEnable = false;
        this.screen = new Screen();
        speakers = new Speakers(100);
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    @Override
    public void turnOn() {
        isEnable = true;
        screen.setEnable(true);
    }

    @Override
    public void turnOff() {
        isEnable = false;
        screen.setEnable(false);
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public int getMaxChannel() {
        return maxChannel;
    }

    @Override
    public void setChannel(int channel) {
        if (channel < 1 || channel > maxChannel) {
            return;
        }
        this.channel = channel;
    }

    @Override
    public void setVolume(int volume) {
        speakers.setVolume(volume);
    }

    @Override
    public int getVolume() {
        return speakers.getVolume();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxVolume() {
        return speakers.getMaxVolume();
    }
}
