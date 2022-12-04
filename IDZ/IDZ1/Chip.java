package org.example;

import java.util.HashMap;

import static org.example.Constants.*;
import static org.example.Printer.*;

public class Chip {
    /**
     * Возможные варианты:
     * empty('.') - пустая клетка.
     * black('#') - черная фишка.
     * white('@') - белая фишка.
     * probably('+') - возможный ход (в начале следующего хода переходит в 0)
     */
    char chip_mark;
    String color;
    HashMap<String, Character> colors;

    char getChip_mark() {
        return chip_mark;
    }

    String getColor() {
        return color;
    }


    Chip() {
        colors = new HashMap<>() {{
            put(BLACK_COLOR, BLACK_CHIP);
            put(WHITE_COLOR, WHITE_CHIP);
            put(EMPTY_COLOR, EMPTY_CELL);
            put(PROBABLY_COLOR, PROBABLY_STEP_CELL);
        }};
        color = EMPTY_COLOR;
        chip_mark = colors.get(color);
    }

    /**
     * Меняет цвет фишки на переданное значение ("black" или "white", иначе - ошибка)
     */
    void changeColor(String color) throws RuntimeException{
        if (!WHITE_COLOR.equals(color) && !BLACK_COLOR.equals(color) && !PROBABLY_COLOR.equals(color) && !EMPTY_COLOR.equals(color)) {
            throw new RuntimeException("Wrong player's color");
        }
        this.color = color;
        chip_mark = colors.get(this.color);
    }

    /**
     * Вывод цвет фишки на ячейке(либо пустую ячейку)
     */
    void displayChipMark() {
        printChar(chip_mark);
    }

}
