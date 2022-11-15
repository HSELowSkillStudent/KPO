package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Class group = new Class();

        String file_name;
        System.out.print("Введите полный путь до файла со студентами:\nПуть: ");
        file_name = input.nextLine();

        if (group.getList(file_name)) {
            System.out.println("Загрузка файла прошла успешно");
        } else {
            System.out.println("Возникла ошибка при загрузке файла");
            System.exit(0);
        }

        group.work();

        System.out.print("Введите полный путь до файла, в который будете сохранять результаты:\nПуть: ");
        file_name = input.nextLine();

        if (group.saveClass(file_name)) {
            System.out.println("Сохранение в файл прошло успешно");
        } else {
            System.out.println("Возникла ошибка при сохранении в файл");
        }
    }
}

class Student {
    private final String name;
    private int score;
    private boolean attendance;

    public Student() {
        name = null;
        score = -1;
        attendance = false;
    }

    public Student(String name1, int score1) {
        name = name1;
        score = score1;
        attendance = true;
    }

    public Student(String name1) {
        name = name1;
        score = -1;
        attendance = false;
    }

    public String getInfo() {
        if (score != -1) {
            return name + ": " + score;
        } else {
            return name + ": нет оценки";
        }
    }

    public boolean setScore(int score1) {
        if (score == -1) {
            score = score1;
            return true;
        }
        return false;
    }

    public boolean setAttendance(boolean attendance1) {
        attendance = attendance1;
        return true;
    }

    String getName() {
        return name;
    }

    int getScore() {
        return score;
    }

    boolean getAttendance() {
        return attendance;
    }
}


class Class {
    int responded;
    int number_of_students;
    HashMap<String, Student> group;

    Class() {
        responded = 0;
        number_of_students = 0;
        group = new HashMap<>();
    }

    public boolean getList(String input_file_name) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(input_file_name));
        } catch (FileNotFoundException e) {
            return false;
        }
        String S;
        while ((S = reader.readLine()) != null) {
            String[] student_card = S.split(";");
            Student student = null;
            try {
                // есть оценка - строка состоит из "<имя>;<оценка>"
                student = new Student(student_card[0], Integer.parseInt(student_card[1]));
                group.put(student_card[0], student);
            } catch (Exception q) {
                // нет оценки - строка состоит из "<имя>;"
                try {
                    student = new Student(student_card[0]);
                    group.put(student_card[0], student);
                } catch (Exception e) {
                    // пустая строка
                    System.out.println(e.getMessage());
                }
            }
            if (student != null && student.getScore() != -1) {
                responded++;
            }
        }
        reader.close();
        number_of_students = group.keySet().size();
        return true;
    }

    public boolean saveClass(String output_file_name) throws IOException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(output_file_name));
        } catch (IOException e) {
            return false;
        }

        Student student;
        for (String key : group.keySet()) {
            student = group.get(key);
            writer.write(student.getName() + ";" + student.getScore() + "\n");
        }

        writer.close();
        return true;
    }

    public void printGroup(boolean print_without_score) {
        int i = 1;
        if (print_without_score) {
            System.out.println("Список всех студентов:");
        } else {
            System.out.println("Список ответивших студентов:");
        }
        boolean empty = true;
        if (group.size() != 0) {
            for (String key : group.keySet()) {
                if (!print_without_score && group.get(key).getScore() == -1) {
                    continue;
                }
                empty = false;
                System.out.println(i++ + ". " + group.get(key).getInfo());
            }
        }
        if (empty) {
            System.out.println("Список пуст");
        }
    }

    public Student chooseRandomStudent() {
        Student student = null;
        String[] keys = group.keySet().toArray(new String[0]);
        Random random = new Random();
        if (responded < number_of_students) {
            do {
                student = group.get(keys[random.nextInt(keys.length)]);
            } while (student.getAttendance());
        } else {
            System.out.println("Все студенты уже ответили");
        }
        return student;
    }

    public void work() {
        Scanner input = new Scanner(System.in);
        Student student;

        while (true) {
            System.out.print("Введите запрос: ");
            String request = input.nextLine();
            if (Objects.equals(request, "/h")) {
                System.out.println("""
                        Список команд:
                        1. /h  - вызов раздела помощи
                        2. /r  - выбор случайного студента
                        3. /l  - вывести список ответивших студентов
                        4. /la - вывести список всех студентов
                        5. /q  - завершить работу программы
                        """);
            } else if (Objects.equals(request, "/r")) {
                student = chooseRandomStudent();
                if (student == null) {
                    // все студенты уже ответили, пропускаем дальнейшие действия
                    continue;
                }
                System.out.println("Отвечает: " + student.getName() + ".\nПрисутствует ли на паре?");
                request = input.nextLine();
                if (Objects.equals(request, "y")) {
                    student.setAttendance(true);
                    System.out.print("Оценка за ответ: ");
                    int score = input.nextInt();
                    student.setScore(score);
                    // количество ответивших увеличилось на 1
                    responded++;
                    // костыль от двойного вывода "Введите запрос: " после чтения числа
                    input.nextLine();
                } else {
                    student.setAttendance(false);
                    student.setScore(-1);
                }
            } else if (Objects.equals(request, "/l")) {
                printGroup(false);
            } else if (Objects.equals(request, "/la")) {
                printGroup(true);
            } else if (Objects.equals(request, "/q")) {
                break;
            }
        }
    }
}
