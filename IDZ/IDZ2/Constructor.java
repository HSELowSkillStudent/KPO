package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import static org.example.Constants.*;

/**
 * Connects objects of the {@link MyFile} class to each other, contributing to the construction of a hierarchy.
 */
public class Constructor {
    private final ArrayList<MyFile> listOfFiles;
    private final HashMap<String, MyFile> filesDatabase;
    private final String startPath;

    /**
     * Connects objects of the {@link MyFile} class to each other, contributing to the construction of a hierarchy.
     *
     * @param listOfFiles list of files to be worked on
     * @param startPath   the path to the directory where the transferred folders are located
     * @throws IOException               if the file does not exist, is a directory rather than a regular file, or for some other
     *                                   reason cannot be opened for reading
     * @throws IndexOutOfBoundsException if a ring dependency was detected
     */
    Constructor(ArrayList<File> listOfFiles, String startPath) throws IOException, IndexOutOfBoundsException {
        this.startPath = startPath;
        filesDatabase = new HashMap<>();
        this.listOfFiles = new ArrayList<>();
        for (File file : listOfFiles) {
            MyFile newFile = new MyFile(file, startPath);
            this.listOfFiles.add(newFile);
            filesDatabase.put(newFile.getName(), newFile);
        }
        // Sorting listOfFiles in ascending order by file names
        this.listOfFiles.sort(Comparator.comparing(MyFile::getName));
        checkDependencies();
    }

    /**
     * Binds files to those that depend on them.
     */
    public void ConnectSonsWithParents() {
        for (MyFile file : listOfFiles) {
            ArrayList<String> parents = file.getRequired();
            for (String strParent : parents) {
                MyFile parent = filesDatabase.get(strParent);
                parent.addSon(file);
            }
        }
    }

    /**
     * Returns a set containing objects of the {@link MyFile} type that do not depend on other MyFile's.
     *
     * @return a set containing objects of the {@link MyFile} type that do not depend on other MyFile's
     */
    HashSet<MyFile> getMainParents() {
        prepareWasCheck();
        HashSet<MyFile> setOfMainParents = new HashSet<>();
        for (MyFile file : listOfFiles) {
            MyFile mainParent = findTheMainParent(file);
            if (mainParent != null) {
                setOfMainParents.add(mainParent);
            }
        }
        return setOfMainParents;
    }

    /**
     * Places the weight - the distance to the top of the hierarchy. <br>
     * The higher the element, the less its weight.
     *
     * @param setOfMainParents a set containing objects of the {@link MyFile} type
     */
    public void buildWeightHierarchy(HashSet<MyFile> setOfMainParents) {
        prepareWeight();
        for (MyFile parent : setOfMainParents) {
            parent.buildWeightHierarchy(0);
        }
    }

    /**
     * Using printer outputs a hierarchy of files based on the weight of the {@link MyFile} object.
     *
     * @param setOfMainParents a set of {@link MyFile} objects from which hierarchy output will begin
     * @param printer          the object that will be used to output to the desired stream
     */
    void displayHeightHierarchy(HashSet<MyFile> setOfMainParents, Printer printer) {
        prepareWasCheck();
        for (MyFile file : setOfMainParents) {
            file.displayWeightHierarchy(startPath, printer);
        }
    }

    /**
     * Outputs the data of {@link MyFile} objects in hierarchical order based on "weight".
     *
     * @param setOfMainParents a set of {@link MyFile} objects from which data output will begin
     * @param printer          the object that will be used to output to the desired stream
     */
    void displayHeightData(HashSet<MyFile> setOfMainParents, Printer printer) {
        prepareWasCheck();
        for (MyFile file : setOfMainParents) {
            file.displayWeightData(printer);
        }
    }

    /**
     * Returns the main ancestor of the file.
     *
     * @param file the file for which the main ancestor will be searched
     * @return the oldest ancestor of the file
     */
    MyFile findTheMainParent(MyFile file) {
        if (file.getWasEntered()) {
            return null;
        }
        if (file.getRequired().size() == 0) {
            return file;
        }
        return findTheMainParent(filesDatabase.get(file.getRequired().get(0)));
    }

    /**
     * Auxiliary function - resets the visiting flags of {@link MyFile} objects for further work.
     */
    public void prepareWasCheck() {
        for (MyFile file : listOfFiles) {
            file.setWasEntered(false);
        }
    }

    /**
     * Auxiliary function - resets the weight of {@link MyFile} objects for further work.
     */
    public void prepareWeight() {
        for (MyFile file : listOfFiles) {
            file.setWeight(0);
        }
    }

    /**
     * Checks for ring dependencies.
     *
     * @throws IndexOutOfBoundsException if a ring dependency was detected
     */
    private void checkDependencies() throws IndexOutOfBoundsException {
        ArrayList<String> queue = new ArrayList<>();
        for (MyFile file : listOfFiles) {
            checkDependenceHelper(file, queue);
            queue.remove(queue.size() - 1);
        }
    }

    /**
     * Auxiliary function for checkDependencies - allows to solve the task recursively.
     * Puts {@link MyFile} objects in a queue, checking that none of the inserted objects occur more than once
     *
     * @param file  the file that is being worked on
     * @param queue file queue - helps to find a ring dependency in case of re-adding one of the files
     * @throws IndexOutOfBoundsException if a ring dependency was detected
     */
    private void checkDependenceHelper(MyFile file, ArrayList<String> queue) throws IndexOutOfBoundsException {
        queue.add(file.getName());
        if (queue.indexOf(file.getName()) != queue.lastIndexOf(file.getName())) {
            throw new IndexOutOfBoundsException(RING_DEPENDENCY_DISCOVERED_M1 + file.getShortName(startPath) +
                    RING_DEPENDENCY_DISCOVERED_M2);
        }
        for (String reqFilePath : file.getRequired()) {
            MyFile reqFile = filesDatabase.get(reqFilePath);
            if (reqFile == null) {
                throw new NullPointerException(FILE_DOESNT_EXIST + reqFilePath);
            }
            reqFile.setWasEntered(true);
            checkDependenceHelper(reqFile, queue);
            queue.remove(queue.size() - 1);
        }
    }
}
