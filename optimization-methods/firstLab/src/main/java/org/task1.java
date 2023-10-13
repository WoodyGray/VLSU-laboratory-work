package org;

public class task1 {

    public static void main(String[] args) {
        int Y = 80;
        int[] c = {25, 80, 65};
        int[] w = {10, 30, 20};
        int m = c.length;

        int[][] table = new int[m + 1][Y + 1];

        for (int i = 1; i <= m; i++) {
            for (int y = 0; y <= Y; y++) {
                int maxValWithoutI = table[i - 1][y];
                int maxValWithI = 0;

                for (int x = 1; x <= y / w[i - 1]; x++) {
                    int valWithI = c[i - 1] * x + table[i - 1][y - w[i - 1] * x];
                    maxValWithI = Math.max(maxValWithI, valWithI);
                }

                table[i][y] = Math.max(maxValWithoutI, maxValWithI);
            }
        }

        // Вывод таблицы
        System.out.print("y\t");
        for (int i = 0; i <= Y; i+=10) {
            System.out.print(i + "\t");
        }
        System.out.println();

        for (int i = 0; i <= m; i++) {
            System.out.print("f" + i + "\t");
            for (int y = 0; y <= Y; y+=10) {
                System.out.print(table[i][y] + "\t");
            }
            System.out.println();
        }
    }

    public static int[] findOptimalStrategies(int[][][] table, int stage, int Y, int[] w) {
        int[] optimalStrategies = new int[stage];
        int x = Y / w[stage - 1];
        optimalStrategies[stage - 1] = x;

        for (int i = stage - 1; i >= 1; i--) {
            int maxValWithI = table[i][Y][x];
            int maxValWithoutI = table[i - 1][Y][x];
            if (maxValWithI > maxValWithoutI) {
                optimalStrategies[i - 1] = x;
            } else {
                x--;
                optimalStrategies[i - 1] = x;
            }
            Y -= x * w[i - 1];
        }

        return optimalStrategies;
    }
}


