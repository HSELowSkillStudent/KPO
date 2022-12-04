package org.example;

import java.util.ArrayList;

import static org.example.Constants.*;
import static org.example.Printer.*;


public class Player {
    private final boolean is_player;
    private String name;
    private int score;
    private final boolean is_simple;

    /**
     * Принимает значения:
     * 1 - черная фишка.
     * 2 - белая фишка.
     */
    private String color;

    Player(boolean is_player, boolean is_black_team, boolean is_simple_bot) {
        score = 0;
        is_simple = is_simple_bot;
        this.is_player = is_player;
        setTeam(is_black_team);
        if (is_player) {
            // System.out.println("Enter your name:");
            printlnString(ENTER_NAME);
            setName(scanner.nextLine());
            return;
        }
        setName(Constants.COMPUTER_NAME1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    String getColor() {
        return color;
    }

    boolean getIs_player() {
        return is_player;
    }

    int getScore() {
        return score;
    }

    private void setTeam(boolean is_black_team) {
        if (is_black_team) {
            color = BLACK_COLOR;
        } else {
            color = WHITE_COLOR;
        }
    }

    static String enemyColor(String player_color) {
        if (BLACK_COLOR.equals(player_color)) {
            return WHITE_COLOR;
        }
        return BLACK_COLOR;
    }

    void countScore(Field field) {
        score = field.countScore(color);
    }

    static ArrayList<PairInt> findPossibleChips(Field field, String player_color) {
        ArrayList<PairInt> possible_coordinates = new ArrayList<>();
        for (int y = 0; y < field.getField_size_y(); y++) {
            for (int x = 0; x < field.getField_size_y(); x++) {
                if (field.isEmptyCell(x, y) && field.amountPossibleSetChip(x, y, player_color, false) > 0) {
                    possible_coordinates.add(new PairInt(x, y));
                }
            }
        }
        return possible_coordinates;
    }

    boolean botStep(Field field, PairInt positions) {
        if (is_simple) {
            return botSimpleStep(field, positions);
        }
        return botHardStep(field, positions);
    }

    boolean botSimpleStep(Field field, PairInt positions) {
        ArrayList<PairInt> possible_coordinates = findPossibleChips(field, color);
        // Нет возможных ходов
        if (possible_coordinates.size() == 0) {
            return false;
        }

        // расчет веса хода
        double max_weight = 0;
        int max_weight_i = 0;
        for (int i = 0; i < possible_coordinates.size(); i++) {
            PairInt pair = possible_coordinates.get(i);
            double tmp = field.countCostOfCell(pair.getFirst(), pair.getSecond(), color);
            if (/*tmp >= 1 &&*/ tmp > max_weight) {
                max_weight = tmp;
                max_weight_i = i;
            }
        }

        if (max_weight >= 1) {
            PairInt coordinates = possible_coordinates.get(max_weight_i);
            positions.setFirst(coordinates.getFirst());
            positions.setSecond(coordinates.getSecond());
            field.amountPossibleSetChip(coordinates.getFirst(), coordinates.getSecond(), color, true);
            return true;
        }
        return false;
    }

    boolean botHardStep(Field field, PairInt positions) {
        ArrayList<PairInt> possible_coordinates = findPossibleChips(field, color);
        // Нет возможных ходов
        if (possible_coordinates.size() == 0) {
            return false;
        }

        Field helper_field = new Field(field.getField_size_x(), field.getField_size_y());

        // расчет веса хода
        double max_difference = -10000;
        int max_difference_i = 0;
        for (int i = 0; i < possible_coordinates.size(); i++) {
            PairInt pair = possible_coordinates.get(i);
            // смотрим, сколько весит ход для этой координаты сейчас(уменьшаемое)
            double reduced = field.countCostOfCell(pair.getFirst(), pair.getSecond(), color);
            // переносим значения
            field.transfer(helper_field);
            // сделали "ход"
            helper_field.amountPossibleSetChip(pair.getFirst(), pair.getSecond(), color, true);

            // теперь надо посмотреть, сколько максимально баллов может набрать противник при такой ситуации
            String enemy_color = enemyColor(color);
            // ищем возможные ходы противника
            ArrayList<PairInt> possible_enemy_coordinates = findPossibleChips(helper_field, enemy_color);
            double deductible = 0;
            double difference = 128;
            for (PairInt enemy_pair : possible_enemy_coordinates) {
                // смотрим, сколько весит ход для этой координаты у противника(вычитаемое)
                deductible = Math.max(deductible, helper_field.countCostOfCell(enemy_pair.getFirst(), enemy_pair.getSecond(), enemy_color));
                difference = Math.min(difference, reduced - deductible);
            }
            if (difference > max_difference) {
                max_difference = difference;
                max_difference_i = i;
            }
        }

        PairInt coordinates = possible_coordinates.get(max_difference_i);
        positions.setFirst(coordinates.getFirst());
        positions.setSecond(coordinates.getSecond());
        field.amountPossibleSetChip(coordinates.getFirst(), coordinates.getSecond(), color, true);
        return true;
    }

}
