package org.example.multiple.client;

import org.example.multiple.database.Database;
import org.example.multiple.server.ClientHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class ClientApp {

    private static final int PORT = 8080;
    private static final int PORT_FOR_KIDS = 4040;
    private static final String HOST = "localhost";
    public static final String DATABASE_FILE_NAME = "users.txt";

    public static void main(String[] args) throws IOException {
        var scanner = new Scanner(System.in);
        System.out.println("Please, enter your username for the group chat: ");
        String username = scanner.nextLine();

        if (Database.isUserExists(DATABASE_FILE_NAME, username)) {
            System.out.println("Your username is already taken, please, try another one.");
            return;
        }
        System.out.println("Please, enter your age: ");
        int age = scanner.nextInt();
        Database.addUser(DATABASE_FILE_NAME, username, age);


        if (age < 18) {
            addToServer(PORT_FOR_KIDS, username, age, "Welcome to the kids chat, " + username + "!");
            return;
        }
        addToServer(PORT, username, age, "Welcome to the adult chat, " + username + "!");
    }

    private static void addToServer(int port, String username, int userAge, String greeting_message) throws IOException {
        var socket = new Socket(ClientApp.HOST, port);
        var client = new Client(socket, username, userAge);
        System.out.println(greeting_message);
        client.listenMessages();
        client.sendMessages();
    }
}

