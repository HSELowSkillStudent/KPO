package org.example.Devices;

public class Remote {
    Device device;

    public Remote(Device devise) {
        this.device = devise;
    }

    public void togglePower() {
        if (device.isEnabled()) {
            device.turnOff();
        } else {
            device.turnOn();
        }
    }

    public void volumeDown() {
        device.setVolume(device.getVolume() - 5);
    }

    public void volumeUp() {
        device.setVolume(device.getVolume() + 5);
    }

    public void channelDown() {
        device.setChannel(device.getChannel() - 1);
    }

    public void channelUp() {
        device.setChannel(device.getChannel() + 1);
    }

    public void mute() {
        device.setChannel(0);
    }

    public String getDeviceInfo() {
        String result = "";
        result += "Device name: " + device.getName() + "\n";
        if (device.isEnabled()) {
            result += "Device enable \n";
        } else {
            result += "Device does not enable \n";
        }
        result += "Channel: " + device.getChannel() + "/" + device.getMaxChannel() + "\n";
        result += "Volume: " + device.getVolume() + "/" + device.getMaxVolume() + "\n";
        return result;
    }
}
