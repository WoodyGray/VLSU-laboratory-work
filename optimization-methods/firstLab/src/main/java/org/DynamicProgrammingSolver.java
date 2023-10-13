package org;

import java.util.Arrays;

public class DynamicProgrammingSolver {
    static class StageResult {
        int y;
        int[] x;
        int value;

        public StageResult(int y, int[] x, int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        // Задача 1: Неограниченная задача выбора комплекса СрЗИ
        int m1 = 3; // Количество типов СрЗИ
        int[] w1 = {10, 30, 20}; // Стоимость СрЗИ каждого типа
        int[] c1 = {25, 80, 65}; // Эффект применения СрЗИ каждого типа

        int Y1 = 80; // Общий объем финансирования

        // Создаем таблицу для хранения результатов
        StageResult[][] table1 = new StageResult[m1 + 1][Y1 + 1];

        for (int i = 1; i <= m1; i++) {
            for (int y = 0; y <= Y1; y++) {
                int maxValue = 0;
                int[] maxX = new int[m1];
                for (int x = 0; x * w1[i - 1] <= y; x++) {
                    int value = x * c1[i - 1];
                    int prevY = y - x * w1[i - 1];
                    if (prevY >= 0 && table1[i - 1][prevY] != null) {
                        value += table1[i - 1][prevY].value;
                    }
                    if (value > maxValue) {
                        maxValue = value;
                        if (table1[i - 1][prevY] != null) {
                            maxX = Arrays.copyOf(table1[i - 1][prevY].x, m1);
                        } else {
                            maxX = new int[m1];
                        }
                        maxX[i - 1] = x;
                    }
                }
                table1[i][y] = new StageResult(y, maxX, maxValue);
            }
        }

        // Выводим таблицу для задачи 1
        System.out.println("Таблица для задачи 1:");
        System.out.println("y1; x1, x2, x3; f1(y1); x1");
        for (int y = 0; y <= Y1; y+=10) {
            StageResult result = table1[m1][y];
            System.out.print(y + "; ");
            for (int i = 0; i < m1; i++) {
                System.out.print(result.x[i]);
                if (i < m1 - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("; " + result.value + "; " + result.x[0]);
        }

        // Динамическая шкала для задачи 1
        System.out.println("Динамическая шкала для задачи 1:");
        System.out.print("fi ={");
        for (int i = 1; i <= m1; i++) {
            int y = table1[i][Y1].y;
            int[] x = table1[i][Y1].x;
            int value = table1[i][Y1].value;
            System.out.print(value + ", ");
        }
        System.out.println("}.");
    }
}

