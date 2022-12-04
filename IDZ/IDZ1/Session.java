package org.example;

import java.util.ArrayList;


import static org.example.Constants.*;
import static org.example.Printer.*;


public class Session {
    private final Player player1;
    private final Player player2;

    private final StepsHistory steps_history;

    Session(boolean player1_is_player, boolean player2_is_player, boolean easy_bot) {
        steps_history = new StepsHistory();
        // Инициализация первого игрока
        if (player1_is_player) {
            // System.out.println(FIRST_PLAYER_INITIALIZING);
            printlnString(FIRST_PLAYER_INITIALIZING);
        }

        player1 = new Player(player1_is_player, true, easy_bot);

        // Инициализация второго игрока
        if (player2_is_player) {
            // System.out.println(SECOND_PLAYER_INITIALIZING);
            printlnString(SECOND_PLAYER_INITIALIZING);
        }
        player2 = new Player(player2_is_player, false, easy_bot);
    }

    ArrayList<PairStringInt> startGame() {
        Field field = new Field();
        int step_counter = 0;
        Player[] players_queue = new Player[]{player1, player2};
        boolean prev_player_pass_his_step = false;

        while (field.isPossibleToStep()) {
            // Атакующий - игрок, который делает ход
            Player attacker = players_queue[step_counter];
            Player defender = players_queue[(step_counter + 1) % 2];

            // Обновляем очередь хода(на следующий ход)
            step_counter = updateCounter(step_counter);

            // Если атакующий не может сделать ход, ход переходит следующему игроку(или игра завершается)
            if (!field.isPlayerCanStepNow(attacker.getColor())) {
                // System.out.println("Нет ходов " + prev_player_pass_his_step);
                if (attacker.getIs_player()) {
                    printlnList(attacker.getName(), NO_POSSIBLE_COORDINATES, PRESS_ENTER);
                    scanner.nextLine();
                }
                if (prev_player_pass_his_step) {
                    printlnList(attacker.getName(), PLAYERS_CANT_STEP_PART_1, defender.getName(), PLAYERS_CANT_STEP_PART_2, PRESS_ENTER);
                    scanner.nextLine();
                    return displayFinalResults(field);
                }
                prev_player_pass_his_step = true;
                continue;
            }
            prev_player_pass_his_step = false;
            // System.out.println("Можно сходить");

            // Ход делает человек
            if (attacker.getIs_player()) {
                clearConsole();
                field.displayField(attacker.getColor());
                // System.out.println(attacker.getName() + ", введите координаты, на который хотите поставить свою фишку(" + attacker.getColor() + "):");
                printlnList(attacker.getName(), ENTER_COORDINATES_PART_1, attacker.getColor(), ENTER_COORDINATES_PART_2);
                String step = scanner.nextLine();
                if (step.length() == 2) {
                    playerStep(step, field, attacker, defender);
                } else if (UNDO.equals(step)) {
                    step_counter = stepUndo(field, defender, step_counter);
                    // step_counter = updateCounter(step_counter);
                } else if (HELP.equals(step)) {
                    displayHelp();
                    step_counter = updateCounter(step_counter);
                    scanner.nextLine();
                } else if (INFO.equals(step)) {
                    displayInfo();
                    step_counter = updateCounter(step_counter);
                    scanner.nextLine();
                } else if (QUIT.equals(step)) {
                    return new ArrayList<>() {
                    };
                } else {
                    step_counter = updateCounter(step_counter);
                    continue;
                }
                // Ход делает бот
            } else {
                PairInt position = new PairInt();
                if (attacker.botStep(field, position)) {
                    // Если бот смог сделать ход
                    steps_history.addStep(position.getFirst(), position.getSecond(), attacker.getColor());
                }
            }
        }
        return displayFinalResults(field);
    }

    int updateCounter(int counter) {
        counter++;
        counter %= 2;
        return counter;
    }

    private ArrayList<PairStringInt> displayFinalResults(Field field) {
        clearConsole();
        player1.countScore(field);
        player2.countScore(field);
        // player2.setScore(field.countScore(player2.getColor()));
        if (player1.getScore() >= player2.getScore()) {
            displayWinnerAndLooser(player1, player2);
        } else {
            displayWinnerAndLooser(player2, player1);
        }
        field.displayField();
        // System.out.println(PRESS_ENTER);
        printlnString(PRESS_ENTER);
        scanner.nextLine();
        return getPlayersResultResult();
    }

    ArrayList<PairStringInt> getPlayersResultResult() {
        ArrayList<PairStringInt> result = new ArrayList<>();
        if (player1.getIs_player()) {
            result.add(new PairStringInt(player1.getName(), player1.getScore()));
        }
        if (player2.getIs_player()) {
            result.add(new PairStringInt(player2.getName(), player2.getScore()));
        }
        return result;
    }

    private void displayWinnerAndLooser(Player winner, Player looser) {
        if (winner.getScore() != looser.getScore()) {
//            System.out.println("Игрок " + winner.getName() + "("+winner.getColor()+") победил игрока " + looser.getName() + " со счетом " +
//                    winner.getScore() + ":" + looser.getScore());
            printlnList(DISPLAY_WINNER_PART_1, winner.getName(), DISPLAY_WINNER_PART_2, winner.getColor(), DISPLAY_WINNER_PART_3, looser.getName(), DISPLAY_WINNER_PART_4, looser.getColor(), DISPLAY_WINNER_PART_5, Integer.toString(winner.getScore()), DISPLAY_WINNER_PART_6, Integer.toString(looser.getScore()));
        } else {
//            System.out.println("Игроки " + winner.getName() + " и " + looser.getName() + "сыграли в ничью со счетом " +
//                    winner.getScore() + ":" + looser.getScore());
            printlnList(DISPLAY_DRAW_PART_1, winner.getName(), DISPLAY_DRAW_PART_2, looser.getName(), DISPLAY_DRAW_PART_3, Integer.toString(winner.getScore()), DISPLAY_DRAW_PART_4, Integer.toString(looser.getScore()));
        }
        // System.out.println();
        printlnString(FINAL_BOARD);
        // System.out.println("");

    }

    int stepUndo(Field field, Player defender, int step_counter) {
        if (defender.getIs_player()) {
            if (!steps_history.back(field)) {
                step_counter--;
            }
        } else {
            if (steps_history.back(field)) {
                step_counter = updateCounter(step_counter);
                if (steps_history.back(field)) {
                    step_counter = updateCounter(step_counter);
                }
            }
            step_counter = updateCounter(step_counter);
        }
        return step_counter;
    }

    void displayInfo() {
        printlnList(INFO_MESSAGE, PRESS_ENTER);
    }

    void displayHelp() {
        // System.out.println(BLACK_COLOR + " - " + BLACK_CHIP + "\n" + WHITE_COLOR + " - " + WHITE_CHIP + "\n" + PRESS_ENTER);
        printlnList(BLACK_COLOR, DISPLAY_HELP_1, Character.toString(BLACK_CHIP),  DISPLAY_HELP_2, WHITE_COLOR, DISPLAY_HELP_1, Character.toString(WHITE_CHIP), DISPLAY_HELP_2, PRESS_ENTER);
    }

    void playerStep(String step, Field field, Player attacker, Player defender) {
        // У pos_y делаем еще -1, потому что нумерация на доске начинается с 1
        int pos_x = step.charAt(0) - 'A', pos_y = step.charAt(1) - '0' - 1;
        try {
            try {
                if (!field.setChipOnField(pos_x, pos_y, attacker.getColor())) {
                    // System.out.println("Данная клетка занята либо не удовлетворяет правилам размещения. Воспользуйтесь командой \"" + HELP + "\", чтобы посмотреть доступные варианты хода\n" + PRESS_ENTER);
                    printlnList( DISPLAY_PLAYER_STEP_1, PRESS_ENTER);
                    scanner.nextLine();
                    return;
                    // continue;
                }
            } catch (IndexOutOfBoundsException e) {
                printlnString(CANT_BE);
            }
            // добавим ход в очередь
            steps_history.addStep(pos_x, pos_y, attacker.getColor());
        } catch (IndexOutOfBoundsException e) {
            // Указали неверные координаты -> выпало с ошибкой
            // System.out.println();
            printlnList(DISPLAY_PLAYER_STEP_2, PRESS_ENTER);
            scanner.nextLine();
            return;
            // continue;
        }
        if (!defender.getIs_player()) {
            field.displayField();
            // System.out.println(PRESS_ENTER);
            printlnString(PRESS_ENTER);
            scanner.nextLine();
        }
    }

}
