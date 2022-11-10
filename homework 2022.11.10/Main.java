package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter size of array:");
        int size = input.nextInt();
        if (size <= 0) {
            System.out.println("The size of the array must be greater than 0");
            System.exit(-1);
        }
        System.out.println("Enter " + size + " elements of the array:");

        int[] array = new int [size];
        for (int i = 0; i < size; i++) {
            array[i] = input.nextInt();
        }

        System.out.println("Entered array:");
        printArray(array);

        BubbleSort(array);
        System.out.println("Sorted array:");
        printArray(array);
    }

    public static void printArray(int[] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            System.out.printf(array[i] + " ");
        }
        System.out.println();
    }

    public static void BubbleSort(int[] array) {
        int tmp, size = array.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (array[j] < array[i]) {
                    tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }
    }

}
