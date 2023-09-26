package org;

public class task3 {
    public static void main(String[] args) {
        // Вариант 19
        // Заданные исходные данные
        int m = 4; // количество типов СрЗИ
        int n = 23; // количество значений переменной Y
        int minY = 122; // минимальное значение переменной Y
        int maxY = 144; // максимальное значение переменной Y

        int[] w = {4, 5, 8, 9}; // стоимость СрЗИ каждого типа
        int[] c = {55, 75, 65, 69}; // эффект применения СрЗИ каждого типа
        int[] b = {4, 2, 2, 3}; // максимальное количество СрЗИ каждого типа
        int[] a = {2, 1, 1, 1}; // минимальное количество СрЗИ каждого типа

        // Создаем массив для хранения решения
        int[][][] dp = new int[m + 1][maxY + 1][b.length + 1];

        // Заполняем массив с использованием метода динамического программирования
        for (int i = 1; i <= m; i++) {
            for (int y = minY; y <= maxY; y++) {
                for (int k = a[i - 1]; k <= b[i - 1] && k * w[i - 1] <= y; k++) {
                    dp[i][y][k] = Math.max(dp[i - 1][y][k], dp[i][y - k * w[i - 1]][k - a[i - 1]] + k * c[i - 1]);
                }
            }
        }

        // Выводим наилучшее значение ЦФ для каждого значения Y
        for (int y = minY; y <= maxY; y++) {
            System.out.println("Максимальный эффект для Y = " + y + ": " + dp[m][y][b[m - 1]]);
        }
    }
}
