package org.example.Devices;

import org.example.SpareParts.Speakers;

public class Radio implements Device {
    private final String name;
    private final Speakers speakers;

    private boolean isEnable;
    private int channel;
    private final int maxChannel;
    public Radio(String name, int maxChannel) {
        this.name = name;
        this.channel = 1;
        this.maxChannel = maxChannel;
        this.speakers = new Speakers(60);
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }

    @Override
    public void turnOn() {
        isEnable = true;
    }

    @Override
    public void turnOff() {
        isEnable = false;
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
        if (channel < 1 || channel > this.maxChannel) {
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
