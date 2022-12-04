package org.example;

import java.util.ArrayList;

import static org.example.Constants.*;
import static org.example.Printer.*;

public class StepsHistory {
    private record Step(int pos_x, int pos_y, String player_color) {}

    private final ArrayList<Step> steps_history;

    StepsHistory() {
        steps_history = new ArrayList<>();
    }

    void addStep(int pos_x, int pos_y, String player_color) {
        steps_history.add(new Step(pos_x, pos_y, player_color));
    }

    boolean back(Field field) {
        if (steps_history.size() == 0) {
            // System.out.println("Пока что нельзя вернуться назад.\n" + PRESS_ENTER);
            printlnList(DISPLAY_BACK, PRESS_ENTER);
            scanner.nextLine();
            return false;
        }
        field.clearBoard();
        for (int i = 0; i < steps_history.size() - 1; i++) {
            field.amountPossibleSetChip(steps_history.get(i).pos_x(), steps_history.get(i).pos_y(), steps_history.get(i).player_color(), true);
        }
        steps_history.remove(steps_history.size() - 1);
        return true;
    }
}
