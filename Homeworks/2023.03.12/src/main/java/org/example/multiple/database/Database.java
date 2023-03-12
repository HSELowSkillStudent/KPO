package org.example.multiple.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    // Аналог нормальной базы данных
    private Database() {
    }


    public static void addUser(String fileName, String username, int age) throws IOException {
        File file = new File(fileName);
        createFile(file);
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(username + ";" + age);
        fileWriter.write(System.lineSeparator());
        fileWriter.close();
    }

    public static void deleteUser(String fileName, String username) throws IOException {
        File file = new File(fileName);
        createFile(file);
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.startsWith(username)) {
                lines.add(line);
            }
        }
        fileReader.close();
        scanner.close();

        FileWriter fileWriter = new FileWriter(file);
        for (String line : lines) {
            if (line.split(";")[0].equals(username)) {
                continue;
            }
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
        }
        fileReader.close();
    }

    public static boolean isUserExists(String fileName,  String username) throws IOException {
        File file = new File(fileName);
        createFile(file);
        FileReader fileReader = new FileReader(file);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith(username + ";" )) {
                return true;
            }
        }
        return false;
    }
    private static void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void clearDatabase(String fileName) throws IOException {
        File file = new File(fileName);
        createFile(file);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        fileWriter.close();
    }
}
