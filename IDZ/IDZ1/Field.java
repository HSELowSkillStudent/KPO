package org.example;

import java.util.ArrayList;

import static org.example.Constants.*;
import static org.example.Printer.*;

public class Field {
    private Chip[][] board;
    private final int field_size_x;
    private final int field_size_y;
    PairInt[] possible_directions = {new PairInt(-1, -1), new PairInt(-1, 0), new PairInt(-1, 1),
            new PairInt(0, -1), new PairInt(0, 1), new PairInt(1, -1),
            new PairInt(1, 0), new PairInt(1, 1)};


    Field() {
        field_size_x = SIZE_X;
        field_size_y = SIZE_Y;
        clearBoard();
        standardStart();
        // nonStandartStart();
    }

    Field(int field_size_x, int field_size_y) {
        this.field_size_x = field_size_x;
        this.field_size_y = field_size_y;
        clearBoard();
        standardStart();
        // nonStandartStart();
    }

    int getField_size_x() {
        return field_size_x;
    }

    int getField_size_y() {
        return field_size_y;
    }

    private void standardStart() {
        int middle_x = field_size_x / 2;
        int middle_y = field_size_y / 2;

        // Расстановка начальных фишек по умолчанию
        board[middle_y][middle_x].changeColor(WHITE_COLOR);
        board[middle_y - 1][middle_x - 1].changeColor(WHITE_COLOR);
        board[middle_y - 1][middle_x].changeColor(BLACK_COLOR);
        board[middle_y][middle_x - 1].changeColor(BLACK_COLOR);
    }

    void clearBoard() {
        board = new Chip[field_size_y][field_size_x];
        initializeBoard(board);
        standardStart();
    }

    private void initializeBoard(Chip[][] board) {
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                board[y][x] = new Chip();
            }
        }
    }

    boolean isEmptyCell(int pos_x, int pos_y) {
        return EMPTY_COLOR.equals(board[pos_y][pos_x].getColor()) || PROBABLY_COLOR.equals(board[pos_y][pos_x].getColor());
    }

    int countScore(String player_color) {
        if (!BLACK_COLOR.equals(player_color) && !WHITE_COLOR.equals(player_color)) {
            return -1;
        }
        int score = 0;
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                if (board[y][x].getColor().equals(player_color)) {
                    score++;
                }
            }
        }
        return score;
    }

    /**
     * Возвращает true, если удалось поставить фишку. Иначе - нет(указал на занятую ячейку
     * или сюда нельзя ставить фишку)
     */
    boolean setChipOnField(int pos_x, int pos_y, String player_color) throws IndexOutOfBoundsException {
        if (pos_x < 0 || pos_y < 0 || pos_x >= field_size_x || pos_y >= field_size_y) {
            throw new IndexOutOfBoundsException("Going beyond the boundaries of the field!");
        }
        // Проервка на то, что эта клетка пустая, т.е. туда можно поставить значение
        if (!EMPTY_COLOR.equals(board[pos_y][pos_x].getColor()) && !PROBABLY_COLOR.equals(board[pos_y][pos_x].getColor())) {
            return false;
        }
        // Есть "связи" с другими клетками
        if (amountPossibleSetChip(pos_x, pos_y, player_color, false) > 0) {
            board[pos_y][pos_x].changeColor(player_color);
            amountPossibleSetChip(pos_x, pos_y, player_color, true);
            return true;
        }
        return false;
    }

    boolean isPossibleToStep() {
        boolean is_have_empty = false;
        boolean is_have_black = false;
        boolean is_have_white = false;
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                if (board[y][x].getChip_mark() == EMPTY_CELL || board[y][x].getChip_mark() == PROBABLY_STEP_CELL) {
                    is_have_empty = true;
                }
                if (board[y][x].getChip_mark() == BLACK_CHIP) {
                    is_have_black = true;
                }

                if (board[y][x].getChip_mark() == WHITE_CHIP) {
                    is_have_white = true;
                }
            }
        }
        return is_have_empty && is_have_black && is_have_white;
    }


    int amountPossibleSetChip(int pos_x, int pos_y, String player_color, boolean need_to_color) {
        int result = 0;
        for (PairInt direction : possible_directions) {
            if (checker(pos_x, pos_y, direction.getFirst(), direction.getSecond(), player_color, need_to_color)) {
                result++;
            }
            // System.out.println(direction.getFirst() + " " + direction.getSecond() + "\n" + result);
        }
        return result;
    }

    /**
     * Проверка, что игрок в принципе может ходить
     */
    boolean isPlayerCanStepNow(String player_color) {
        ArrayList<PairInt> possible_steps = findPossibleChips(player_color);
        return possible_steps.size() != 0;
    }

    boolean checker(int pos_x, int pos_y, int dx, int dy, String player_color, boolean need_to_color) {
        int x = pos_x + dx, y = pos_y + dy;
        boolean was_another_color = false;
        while (x >= 0 && y >= 0 && x < field_size_x && y < field_size_y) {
            // Встретил пустую клетку на пути
            if (EMPTY_COLOR.equals(board[y][x].getColor()) || PROBABLY_COLOR.equals(board[y][x].getColor())) {
                return false;
            }
            // Встретил клетку своего цвета на пути
            if (player_color.equals(board[y][x].getColor())) {
                // До этого был другой цвет => можно покрасить => клетка подходит
                if (was_another_color) {
                    // Если нужно закрасить линию
                    if (need_to_color) {
                        // добавим ход в очередь

                        while (pos_x != x || pos_y != y) {
                            try {
                                board[y][x].changeColor(player_color);
                            } catch (RuntimeException e) {
                                throw new RuntimeException("Такого не должно было случиться...");
                            }
                            x -= dx;
                            y -= dy;
                        }
                        board[pos_y][pos_x].changeColor(player_color);
                    }
                    return true;
                }
                // До этого не было другого цвета => нельзя покрасить => клетка не подходит
                return false;
            }
            // Встретил клетку противоположного цвета
            was_another_color = true;
            x += dx;
            y += dy;
        }
        return false;
    }

    double countCostOfCell(int pos_x, int pos_y, String player_color) {
        double weight = 0;
        for (PairInt direction : possible_directions) {
            double func_res = countWeight(pos_x, pos_y, direction.getFirst(), direction.getSecond(), player_color);
            if (func_res >= 1) {
                weight += func_res;
            }
        }
        return weight;
    }

    double countWeight(int pos_x, int pos_y, int dx, int dy, String player_color) {
        int x = pos_x + dx, y = pos_y + dy;
        boolean was_another_color = false;

        double weight = 0;

        if (!EMPTY_COLOR.equals(board[pos_y][pos_x].getColor())) {
            return weight;
        }
        if ((pos_x == 0 && (pos_y == 0 || pos_y == field_size_y - 1)) ||
                (pos_x == field_size_x - 1 && (pos_y == 0 || pos_y == field_size_y - 1))) {
            weight = 0.8;
        } else if (pos_x == 0 || pos_x == field_size_x - 1 || pos_y == 0 || pos_y == field_size_y - 1) {
            weight = 0.4;
        }
        while (x >= 0 && y >= 0 && x < field_size_x && y < field_size_y) {
            // Встретил пустую клетку на пути
            if (EMPTY_COLOR.equals(board[y][x].getColor()) || PROBABLY_COLOR.equals(board[y][x].getColor())) {
                break;
            }
            // Встретил клетку своего цвета на пути
            if (player_color.equals(board[y][x].getColor())) {
                // сделал шаг назад, чтобы не читать в вес свою фишку
                x -= dx;
                y -= dy;
                // До этого был другой цвет => можно покрасить => клетка подходит
                if (was_another_color) {
                    while (pos_x != x || pos_y != y) {
                        if (x == 0 || x == field_size_x - 1 || y == 0 || y == field_size_y - 1) {
                            weight += 2;
                        } else {
                            weight++;
                        }
                        x -= dx;
                        y -= dy;
                    }
                }
                // До этого не было другого цвета => нельзя покрасить => клетка не подходит
                break;
            }
            // Встретил клетку противоположного цвета
            was_another_color = true;
            x += dx;
            y += dy;
        }

        return weight;
    }

    ArrayList<PairInt> findPossibleChips(String player_color) {
        boolean need_to_add;
        ArrayList<PairInt> result = new ArrayList<>();
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                need_to_add = false;
                for (PairInt possible_direction : possible_directions) {
                    if (countWeight(x, y, possible_direction.getFirst(), possible_direction.getSecond(), player_color) >= 1) {
                        need_to_add = true;
                    }
                }
                if (need_to_add) {
                    result.add(new PairInt(x, y));
                }
            }
        }
        return result;
    }

    void displayFieldWithHelper(Chip[][] board) {
        // Выводим буквы
        printString(SPACE);
        for (char i = 0; i < field_size_x; i++) {
            printString(SPACE);
            formatIntToChar('A' + i);
            printString(SPACE);
        }
        newLine();
        // System.out.println();
        // выводим номера и содержимое клеток поля
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x + 1; x++) {
                if (x == 0) {
                    // выводим номера клеток
                    printInt(y + 1);
                    // System.out.print(y + 1);
                } else {
                    // выводим содержимое клеток поля
                    board[y][x - 1].displayChipMark();
                }
            }
            newLine();
            // System.out.print("\n");
        }
    }

    void displayField(String player_color) {
        // инициализируем выводимую доску
        Chip[][] displayed_board = new Chip[field_size_y][field_size_x];
        initializeBoard(displayed_board);
        // перенос значений на выводимую доску
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                displayed_board[y][x].changeColor(board[y][x].getColor());
            }
        }
        // заполняем возможными ходами
        ArrayList<PairInt> possible_steps = findPossibleChips(player_color);
        for (PairInt coordinates : possible_steps) {
            displayed_board[coordinates.getSecond()][coordinates.getFirst()].changeColor(PROBABLY_COLOR);
        }

        displayInfoAboutProbablySteps(possible_steps);
        // выводим поле с возможными ходами
        displayFieldWithHelper(displayed_board);
    }

    void displayField() {
        displayFieldWithHelper(board);
    }

    void transfer(Field where_to_transfer) {
        // перенос значений с доски board на доску where_to_transfer
        for (int y = 0; y < field_size_y; y++) {
            for (int x = 0; x < field_size_x; x++) {
                Chip[][] board = where_to_transfer.board;
                board[y][x].changeColor(this.board[y][x].getColor());
            }
        }
    }

}
