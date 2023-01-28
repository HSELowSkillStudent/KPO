package org.example;

import org.example.Devices.Device;
import org.example.Devices.Remote;
import org.example.Factories.AbstractFactory;
import org.example.Factories.LGFactory;
import org.example.Factories.SamsungFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        AbstractFactory factory = getFactory();
        Remote remote = getRadioOrTV(factory);
        playWithRemote(remote);
    }

    public static AbstractFactory getFactory() {
        String input;
        AbstractFactory factory;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("""
                    LG or Samsung?

                    1. LG
                    2. Samsung""");
            input = scanner.nextLine();
            if ("1".equals(input)) {
                factory = new LGFactory();
                break;
            } else if ("2".equals(input)) {
                factory = new SamsungFactory();
                break;
            }
        }
        return factory;
    }

    public static Remote getRadioOrTV(AbstractFactory factory) {
        String input;
        Remote remote;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    TV or Radio?

                    1. TV
                    2. Radio""");
            input = scanner.nextLine();
            if ("1".equals(input)) {
                remote = new Remote(factory.createTV());
                break;
            } else if ("2".equals(input)) {
                remote = new Remote(factory.createRadio());
                break;
            }
        }
        return remote;
    }

    public static void playWithRemote(Remote remote) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("""
                    What you want to do? (enter digit)
                    1. Toggle Power
                    2. Volume Down
                    3. Volume Up
                    4. Mute
                    5. Channel Down
                    6. Channel Up
                    7. Get Device Info
                    8. Quit
                    """);
            input = scanner.nextLine();
            if ("1".equals(input)) {
                remote.togglePower();
                System.out.println("Power toggled\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("2".equals(input)) {
                remote.volumeDown();
                System.out.println("Volume++\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("3".equals(input)) {
                remote.volumeUp();
                System.out.println("Volume--\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("4".equals(input)) {
                remote.mute();
                System.out.println("Muted\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("5".equals(input)) {
                remote.channelDown();
                System.out.println("Channel--\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("6".equals(input)) {
                remote.channelUp();
                System.out.println("Channel++\n(Press \"Enter\")");
                input = scanner.nextLine();
            } else if ("7".equals(input)) {
                System.out.println(remote.getDeviceInfo() + "\n(Press \"Enter\")");
                input = scanner.nextLine();
            }
        } while (!"quit".equals(input) && !"8".equals(input));
    }
}
