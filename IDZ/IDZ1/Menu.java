package org.example;

import java.util.*;

import static org.example.Constants.*;
import static org.example.Printer.*;
import static org.example.Constants.scanner;

public class Menu {
    private HashMap<String, Integer> players_result;
    void mainMenuStart() {
        players_result = new HashMap<>();
        while (true) {
            int mode = start();
            Session session;
            if (mode == 1) {
                boolean players_step_first = true;
                // players_step_first = random.nextInt(2) == 0;
                session = new Session(players_step_first, !players_step_first, pickDifficulty());
                addPlayersResult(session.startGame());
            } else if (mode == 2) {
                session = new Session(true, true, true);
                addPlayersResult(session.startGame());
            } else if (mode == 3) {
                displayInfo();
            } else if (mode == 4) {
                displayResults();
            } else if (mode == 5) {
                return;
            } else if (mode == 6) {
                session = new Session(false, false, true);
                addPlayersResult(session.startGame());
            }
        }
    }

    static int start() {
        while (true) {
            clearConsole();
            printString(MAIN_MENU);
            // System.out.print(MAIN_MENU);
            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                input = 0;
                scanner.nextLine();
            }

            switch (input) {
                case 1, 2, 3, 4, 5 -> {
                    scanner.nextLine();
                    return input;
                }
            }
        }
    }

    static void displayInfo() {
        // System.out.print(INFO_MESSAGE);
        printString(INFO_MESSAGE);
        printlnString(PRESS_ENTER);
        scanner.nextLine();
    }

    void displayResults() {
        if (!(players_result.size() == 0)) {

            ArrayList<PairStringInt> sorted_results = new ArrayList<>();
            String[] keys = players_result.keySet().toArray(new String[0]);
            for (int i = 0; i < players_result.size(); i++) {
                sorted_results.add(new PairStringInt(keys[i], players_result.get(keys[i])));
            }

            sorted_results.sort(new Comparator<PairStringInt>() {
                public int compare(PairStringInt o1, PairStringInt o2) {
                    return Integer.compare(o2.getDigit(), o1.getDigit());
                }
            });

            for (PairStringInt sorted_result : sorted_results) {
                // System.out.println(sorted_result.getString() + " - " + sorted_result.getDigit() + "очков");
                printlnList(sorted_result.getString(), DISPLAY_RESULTS_1, Integer.toString(sorted_result.getDigit()), DISPLAY_RESULTS_2);
            }
        } else {
            printlnString(NO_RESULTS);
            // System.out.println("Пока что нет результатов :(");
        }
        // System.out.println(PRESS_ENTER);
        printlnString(PRESS_ENTER);
        scanner.nextLine();
    }

    void addPlayersResult(ArrayList<PairStringInt> results) {
        String name;
        int score;
        for (PairStringInt result : results) {
            if (result == null) {
                continue;
            }
            name = result.getString();
            score = result.getDigit();
            if (name != null) {
                if (players_result.containsKey(name)) {
                    players_result.put(name, Math.max(score, players_result.get(name)));
                } else {
                    players_result.put(name, score);
                }
            }
        }
    }

    boolean pickDifficulty() {
        while (true) {
            clearConsole();
            // System.out.print(PICK_BOT_DIFFICULTY_MESSAGE);
            printString(PICK_BOT_DIFFICULTY_MESSAGE);
            int input;
            try {
                input = scanner.nextInt();
            } catch (InputMismatchException e) {
                input = 0;
                scanner.nextLine();
            }
            switch (input) {
                case 1 -> {
                    scanner.nextLine();
                    // простой бот
                    return true;
                }
                case 2 -> {
                    scanner.nextLine();
                    // сложный бот
                    return false;
                }
            }
        }
    }

}
