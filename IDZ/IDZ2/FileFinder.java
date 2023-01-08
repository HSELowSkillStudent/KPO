package org.example;

import java.io.File;
import java.io.InvalidObjectException;
import java.util.ArrayList;

import static org.example.Constants.FILE_BUT_NOT_DIRECTORY;
import static org.example.Constants.THIS;

public class FileFinder {
    private File startFolder;

    private final ArrayList<File> listOfFiles;

    /**
     * Helps to collect all files in the specified directory.
     *
     * @param startFolderPath the folder from which the file search will start
     * @throws InvalidObjectException the path points to a file, not a directory
     */
    FileFinder(String startFolderPath) throws InvalidObjectException {
        setStartFolder(startFolderPath);
        if (!startFolder.isDirectory()) {
            throw new InvalidObjectException(FILE_BUT_NOT_DIRECTORY + startFolder.getAbsolutePath());
        }
        listOfFiles = new ArrayList<>();
    }

    /**
     * Returns the specified directory
     *
     * @return the specified directory
     */
    File getFileFolder() {
        return startFolder;
    }


    /**
     * Sets the initial file for further work.
     *
     * @param file_path The path to the folder from where the search will start
     */
    void setStartFolder(String file_path) {
        if (THIS.equals(file_path)) {
            file_path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        }
        startFolder = new File(file_path);
    }

    /**
     * Returns a list of all files stored in this directory and its subdirectories. By changing it outside
     * the class object, it will also change in the file object.
     *
     * @param start the folder from which the search for other files will begin.
     * @return a list of all files stored in this directory and its subdirectories
     */
    public ArrayList<File> findAllFiles(File start) {
        listOfFiles.clear();
        findAllFilesHelper(start);
        return listOfFiles;
    }

    /**
     * Participates in the findAllFiles() function recursively solving the task
     *
     * @param start the folder from which the search for other files will begin.
     */
    private void findAllFilesHelper(File start) {
        File[] list = start.listFiles();
        if (list == null) {
            return;
        }
        for (File file : list) {
            if (file.isDirectory()) {
                findAllFilesHelper(file);
            } else {
                listOfFiles.add(file);
            }
        }
    }
}
