package org.example;


import org.json.JSONObject;
import org.sql.ConnectionFactory;
import org.sql.Database;

import java.security.MessageDigest;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Database database = new Database(ConnectionFactory.getConnection());
        Server server = new Server(database, "users");

        Scanner scanner = new Scanner(System.in);
        User user = null;
        while (user == null) {
            System.out.println("\n------------------------------------------\n" +
                    "Enter your name and password to register or log in");
            System.out.println("Enter your name:");
            String name = scanner.nextLine().trim();
            System.out.println("Enter your password:");
            String password = scanner.nextLine().trim();

            if ("".equals(name) || "".equals(password)) {
                System.out.println("Name or password can't be empty");
                continue;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            StringBuilder myHash = new StringBuilder();
            for (byte b : digest) {
                myHash.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            if (server.isUserExist(name)) {
                if (server.isPasswordCorrect(name, myHash.toString())) {
                    System.out.println("You are logged in");
                    user = server.getUser(name);
                } else {
                    System.out.println("Wrong password");
                }
            } else {
                user = server.registerUser(new JSONObject().put("name", name).put("password", myHash.toString()));
                System.out.println("You are registered");
            }
        }
    }
}
