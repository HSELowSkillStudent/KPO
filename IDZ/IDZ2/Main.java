package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.example.Constants.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ConsolePrinter printer = new ConsolePrinter();
        ArrayList<File> listOfFiles;

        String path = getPathFromConsoleUser(printer);

        FileFinder fileFinder = new FileFinder(path);
        path = fileFinder.getFileFolder() + SLASH;
        listOfFiles = fileFinder.findAllFiles(fileFinder.getFileFolder());

        if (listOfFiles.isEmpty()) {
            printer.displayInfo(START_FILE_EMPTY_OR_DOESNT_EXIST);
            return;
        }

        Constructor constructor;
        try {
            constructor = new Constructor(listOfFiles, path);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            printer.displayInfo(String.valueOf(e));
            return;
        }

        HashSet<MyFile> setOfMainParents = getSetOfMainParents(constructor);
        displayTheResults(constructor, setOfMainParents, printer);
    }

    /**
     * Returns the path to the start directory obtained by the {@link Printer} object.
     *
     * @param printer an object that helps to count the path to the starting directory
     * @return path to the start directory
     */
    public static String getPathFromConsoleUser(Printer printer) {
        Inputer inputer = new ConsoleInputer();
        printer.displayInfo(ENTER_THE_PATH);
        return inputer.nextLine();
    }

    /**
     * Returns a list of objects of the {@link MyFile} class that do not depend on other {@link MyFile} objects.
     *
     * @param constructor an object that contains related objects of the {@link MyFile} class
     * @return a set of objects of the {@link MyFile} class that do not depend on other {@link MyFile} objects.
     */
    public static HashSet<MyFile> getSetOfMainParents(Constructor constructor) {
        constructor.ConnectSonsWithParents();
        HashSet<MyFile> setOfMainParents = constructor.getMainParents();
        constructor.buildWeightHierarchy(setOfMainParents);
        return setOfMainParents;
    }

    /**
     * Outputs hierarchy and file data.
     *
     * @param constructor      an object that contains related objects of the {@link MyFile} class
     * @param setOfMainParents set of objects of the {@link MyFile} class that will head the hierarchy
     * @param printer          an object that helps to count the path to the starting directory
     */
    public static void displayTheResults(Constructor constructor, HashSet<MyFile> setOfMainParents, Printer printer) {
        printer.displayInfo(HIERARCHY);
        constructor.displayHeightHierarchy(setOfMainParents, printer);

        printer.displayInfo(NEW_LINE + CONTENT);
        constructor.displayHeightData(setOfMainParents, printer);
    }

}

