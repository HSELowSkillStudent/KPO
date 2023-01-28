package org.example.SpareParts;

public class Speakers {
    int volume;
    final int maxVolume;
    public Speakers(int maxVolume) {
        this.maxVolume = maxVolume;
        this.volume = maxVolume / 4;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume < 0 || volume > this.maxVolume) {
            return;
        }
        this.volume = volume;
    }

    public int getMaxVolume() {
        return maxVolume;
    }
}
