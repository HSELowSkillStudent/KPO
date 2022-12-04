package org.example;

import java.util.Random;
import java.util.Scanner;

final public class Constants {
    // Console
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Constants for Chips
    static final String BLACK_COLOR = "black";
    static final String WHITE_COLOR = "white";
    static final String EMPTY_COLOR = "empty";
    static final String PROBABLY_COLOR = "probably";
    static final char WHITE_CHIP = '●';
    static final char BLACK_CHIP = '○';
    static final char EMPTY_CELL = '.';
    static final char PROBABLY_STEP_CELL = '+';

    // Constants for Field
    static int SIZE_Y = 8;
    static int SIZE_X = 8;

    // Constants for Menu
    static String MAIN_MENU =
                    """
                    ########### REVERSI ###########
                                    
                    1. player vs computer
                    2. player vs player
                    3. info
                    4. best results
                    5. quit
                                    
                    Input:\040
                    """;
    static String INFO_MESSAGE =
                    """
                    INFO MESSAGE
                    
                    rules: https://drive.google.com/file/d/18WzMWH6D0GnBnBvc7DpxP15lWPaary1U/view
                    
                    actions in game:
                      * make step - enter coordinates(format <LETTER><DIGIT>)
                      * step back - command "-undo"
                      * display info - command "-info"
                      * display help - command "-help"
                      * quit - command "-quit"
                    
                    """ ;

    static String PICK_BOT_DIFFICULTY_MESSAGE =
                    """
                    CHOOSE THE MODE
                    
                    1. normal
                    2. hard
                    
                    Input:\040
                    """;
    static String FIRST_PLAYER_INITIALIZING = "Initializing Player 1";
    static String SECOND_PLAYER_INITIALIZING = "Initializing Player 2";
    static String PRESS_ENTER = "Press \"Enter\" to continue";

    static final String NO_RESULTS = "No results yet :(";

    static final String DISPLAY_RESULTS_1 = " - ";
    static final String DISPLAY_RESULTS_2 = " points";



    // Constants for ComputerPlayer
    static String COMPUTER_NAME1 = "Computer 1";
    static String COMPUTER_NAME2 = "Computer 2";

    // Constants for Session
    static final String UNDO = "-undo";
    static final String HELP = "-help";
    static final String INFO = "-info";
    static final String QUIT = "-quit";

    static final String ENTER_COORDINATES_PART_1 = ", enter the coordinates on which you want to put your chip (";
    static final String ENTER_COORDINATES_PART_2 = " color) or enter \"-info\" to display more actions:";

    static final String PLAYERS_CANT_STEP_PART_1 = " and ";
    static final String PLAYERS_CANT_STEP_PART_2 = " can't make moves.\nThe game is over\n";

    static final String DISPLAY_WINNER_PART_1 = "Player ";
    static final String DISPLAY_WINNER_PART_2 = "(";
    static final String DISPLAY_WINNER_PART_3 = " color) defeated the player ";
    static final String DISPLAY_WINNER_PART_4 = "(";
    // static final String DISPLAY_WINNER_PART_5 = " color)";
    static final String DISPLAY_WINNER_PART_5 = " color) with the score ";
    static final String DISPLAY_WINNER_PART_6 = ":";

    static final String DISPLAY_DRAW_PART_1 = "Players ";
    static final String DISPLAY_DRAW_PART_2 = " and ";
    static final String DISPLAY_DRAW_PART_3 = "drew with the score ";
    static final String DISPLAY_DRAW_PART_4 = ":";

    static final String FINAL_BOARD = "Final board:";

    static final String DISPLAY_HELP_1 = " - ";
    static final String DISPLAY_HELP_2 = "\n";

    static final String DISPLAY_PLAYER_STEP_1 = "This cell is occupied or does not meet the placement rules\n";
    static final String DISPLAY_PLAYER_STEP_2 = "It looks like you entered the wrong coordinates, try again\n";

    static final String DISPLAY_BACK = "You can't go back yet.\n";

    static final String CANT_BE = "Entered the wrong coordinates.";

    static final String PROBABLY_COORDINATES = "Possible options to put a chip:";
    static final String NO_POSSIBLE_COORDINATES = ", there are no possible options, skipping a move...";
    static final String EMPTY_STRING = "";
    static final String SPACE = " ";
    static final String CLEAR_CONSOLE = "\033[H\033[J";

    // Constants for Player
    static final String ENTER_NAME = "Enter your name:";

    private Constants() {
    }
}
