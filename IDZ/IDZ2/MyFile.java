package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.example.Constants.*;

/**
 * An entity that simplifies working with files and their data.
 */
public class MyFile {
    private String name;
    private final ArrayList<String> data;
    private final ArrayList<String> required;
    private final ArrayList<MyFile> sons;
    private boolean wasEntered;
    private int weight;

    /**
     * An entity that simplifies working with files and their data.
     *
     * @param file      absolute path to the file
     * @param startPath path to the start directory
     * @throws IOException if the file does not exist, is a directory rather than a regular file, or for some other
     */
    MyFile(File file, String startPath) throws IOException {
        weight = 0;
        sons = new ArrayList<>();
        wasEntered = false;
        name = file.getAbsolutePath();
        data = new ArrayList<>();
        required = new ArrayList<>();
        name = file.getPath();
        if (file.isDirectory()) {
            throw new RuntimeException(DIRECTORY_BUT_NOT_FILE + file.getAbsolutePath());
        }
        FileReader reader = new FileReader(file);
        Scanner scan = new Scanner(reader);
        String line;
        String[] line_array;

        while (scan.hasNext()) {
            line = scan.nextLine();
            if (line.contains(REQUIRE)) {
                line_array = line.split(REQUIRE);
                if (line_array.length == 2) {
                    String second_word = line_array[1];
                    second_word = second_word.strip();
                    if (second_word != null && ((second_word.startsWith(DELIMITER_1) &&
                            second_word.endsWith(DELIMITER_1)) ||
                            (second_word.startsWith(DELIMITER_2_OPEN) && second_word.endsWith(DELIMITER_2_CLOSE)))) {
                        second_word = second_word.substring(1);
                        second_word = second_word.substring(0, second_word.length() - 1);
                        // startPath + second_word == absolutePath
                        addReq(startPath + second_word);
                    }
                }
            }
            data.add(line);
        }
        reader.close();
    }

    /**
     * Returns the value of field name.
     *
     * @return the value of field name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of field data.
     *
     * @return the value of field data
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * Returns the short path to the file: absolute path - the path to the start directory.
     *
     * @param startPath path to the start directory
     * @return the short path to the file: absolute path - the path to the start directory
     */
    public String getShortName(String startPath) {
        return name.replace(startPath, EMPTY);
    }

    /**
     * Returns the value of field wasEntered.
     *
     * @return the value of field wasEntered
     */
    public boolean getWasEntered() {
        return wasEntered;
    }

    /**
     * Returns the value of field required.
     *
     * @return the value of field required
     */
    public ArrayList<String> getRequired() {
        return required;
    }

    /**
     * Sets the wasEntered field to value.
     *
     * @param value this value will be taken by field wasEntered
     */
    public void setWasEntered(boolean value) {
        wasEntered = value;
    }

    /**
     * Sets the weight field to value.
     *
     * @param value this value will be taken by field weight
     */
    public void setWeight(int value) {
        weight = value;
    }

    /**
     * Add value to the required field.
     *
     * @param value this value will be added to required field
     */
    public void addReq(String value) {
        required.add(value);
    }

    /**
     * Add son to the sons list.
     *
     * @param son his value will be added to list
     */
    public void addSon(MyFile son) {
        sons.add(son);
    }

    /**
     * Sets weights for objects of the MyFile class.
     *
     * @param weight the weight to be set to the object
     */
    public void buildWeightHierarchy(int weight) {
        buildWeightHierarchyHelper(weight);
    }

    /**
     * Auxiliary function for buildWeightHierarchy - allows to solve the task recursively.
     *
     * @param weight the weight to be set to the object
     */
    private void buildWeightHierarchyHelper(int weight) {
        if (this.weight < weight) {
            this.weight = weight;
        }
        if (!wasEntered) {
            wasEntered = true;
            for (MyFile son : sons) {
                son.buildWeightHierarchyHelper(weight + 1);
            }
        }
    }

    /**
     * Outputs the object short name and its dependent objects according to their weights.
     *
     * @param startPath path to the start directory
     * @param printer   the object that will be used to output to the desired stream
     */
    public void displayWeightHierarchy(String startPath, Printer printer) {
        displayWeightHierarchyHelper(startPath, weight - 1, printer);
    }

    /**
     * Auxiliary function for displayWeightHierarchy - allows to solve the task recursively.
     *
     * @param startPath    path to the start directory
     * @param parentWeight the weight of the MyFile object on which the current object depends
     * @param printer      the object that will be used to output to the desired stream
     */
    private void displayWeightHierarchyHelper(String startPath, int parentWeight, Printer printer) {
        if (weight - parentWeight != 1) {
            return;
        }
        if (!wasEntered) {
            printer.displayInfo(SPACE.repeat(weight) + getShortName(startPath));
            wasEntered = true;
            for (MyFile son : sons) {
                son.displayWeightHierarchyHelper(startPath, weight, printer);
            }
        }
    }

    /**
     * Outputs the object data and its dependent objects according to their weights.
     *
     * @param printer the object that will be used to output to the desired stream
     */
    public void displayWeightData(Printer printer) {
        displayWeightDataHelper(weight - 1, printer);
    }

    /**
     * Auxiliary function for displayWeightData - allows to solve the task recursively.
     *
     * @param parentWeight the weight of the MyFile object on which the current object depends
     * @param printer      the object that will be used to output to the desired stream
     */
    private void displayWeightDataHelper(int parentWeight, Printer printer) {
        if (weight - parentWeight != 1) {
            return;
        }
        if (!wasEntered) {
            printer.displayListInfo(getData());
            wasEntered = true;
            for (MyFile son : sons) {
                son.displayWeightDataHelper(weight, printer);
            }
        }
    }
}
