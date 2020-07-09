package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static int begin, end;

    public static void main(String[] args) {
        read();
        validator();
        printPrime();
    }

    private static void read() {

        System.out.println("Введите начало промежутка");
        try (Scanner scan = new Scanner(System.in)){
            begin = scan.nextInt();
            System.out.println("Введите конец промежутка");
            end = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода данных");
            System.exit(1);
        }
    }

    private static void validator() {
        if (begin < 2 && end < 2) {
            System.out.println("Простных чисел нет");
            return;
        }
        if (begin > end) {
            System.out.println("Границы будут перевёрнуты");
            int temp = begin;
            begin = end;
            end = temp;
        }
        if (begin < 2) {
            System.out.println("Начало промежутка было отрицательным, вывод будет начинатся с 2");
            begin = 2;
        }
    }

    private static void printPrime() {
        for (int i = begin; i <= end; i++) {
            boolean flag = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.print(i + " ");
            }
        }
    }
}
