package org.example;


/**
 * Contains constants used in the program.
 */
public class Constants {
    public final static String THIS = "this";
    public final static String FILE_DOESNT_EXIST = "This file does not exist: ";
    public final static String DIRECTORY_BUT_NOT_FILE = "This is directory, but must be file: ";
    public final static String FILE_BUT_NOT_DIRECTORY = "This is file, but must be directory: ";
    public final static String REQUIRE = "require ";
    public final static String START_FILE_EMPTY_OR_DOESNT_EXIST = "Start directory is empty or doesn't exist";
    public final static String RING_DEPENDENCY_DISCOVERED_M1 = "A ring dependency between files has been discovered!\n";
    public final static String RING_DEPENDENCY_DISCOVERED_M2 = " got into a loop";
    public final static String HIERARCHY = "FILES HIERARCHY";
    public final static String CONTENT = "FILES CONTENT";
    public final static String ENTER_THE_PATH = "Enter the full path to the start folder or enter \"this\" to " +
            "start with the folder with the executable file:";
    public final static String SLASH = "/";
    public final static String NEW_LINE = "\n";
    public final static String SPACE = " ";
    public final static String EMPTY = "";
    public final static String DELIMITER_1 = "'";
    public final static String DELIMITER_2_OPEN = "‘";
    public final static String DELIMITER_2_CLOSE = "’";

    private Constants() {
    }
}
