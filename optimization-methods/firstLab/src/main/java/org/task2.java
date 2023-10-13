package org;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Первая задача:");
        first(1);
        System.out.println("\nВторая задача:");
        second(1);
        System.out.println("\nТретья задача:");
        third(1);
    }

    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static int reduceGCD(List<Integer> list) {
        int result = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            result = gcd(result, list.get(i));
        }
        return result;
    }

    static List<Integer> F(int[] c, int[] w, int Y, List<Integer> a, List<Integer> b) {
        if (a.isEmpty()) {
            a = new ArrayList<>(List.of(0));
        }
        if (b.isEmpty()) {
            b = new ArrayList<>();
            for (int i = 0; i < c.length; i++) {
                b.add(2 * (int) Math.pow(10, 9));
            }
        }

        int step = reduceGCD(b);
        int[] f = new int[c.length];
        List<List<List<Integer>>> sziList = new ArrayList<>(c.length);

        for (int i = 0; i < c.length; i++) {
            int maxVal = Math.min(Y / w[i], b.get(i));
            f[i] = new int[Y / step + 1];
            sziList.add(new ArrayList<>());

            if (i == 0) {
                for (int j = 0; j <= Y / step; j++) {
                    int curF = 0;
                    int curVal = Math.max(0, a.get(i));
                    List<Integer> optVal = new ArrayList<>(List.of(curVal));
                    while (w[i] * curVal <= j * step && curVal <= maxVal) {
                        curF = Math.max(curF, c[i] * curVal);
                        if (curF < c[i] * curVal) {
                            curF = c[i] * curVal;
                            optVal = new ArrayList<>(List.of(curVal));
                        }
                        curVal++;
                    }
                    f[i][j] = curF;
                    sziList.get(i).add(List.of(optVal));
                }
            }

            if (i > 0) {
                for (int j = 0; j <= Y / step; j++) {
                    int curF = 0;
                    int curVal = Math.max(0, a.get(i));
                    List<Integer> optVal = new ArrayList<>(List.of(curVal));
                    int surplus = j * step - w[i] * curVal;

                    while (surplus >= 0 && curVal <= maxVal) {
                        if (curF < c[i] * curVal + f[i - 1][surplus / step]) {
                            curF = c[i] * curVal + f[i - 1][surplus / step];
                            optVal = new ArrayList<>(sziList.get(i - 1).get(surplus / step));
                            optVal.add(curVal);
                        }
                        curVal++;
                        surplus = j * step - w[i] * curVal;
                    }
                    f[i][j] = curF;
                    sziList.get(i).add(List.of(optVal));
                }
            }
        }

        int lastIndex = c.length - 1;
        List<Integer> result = new ArrayList<>();
        result.add(f[lastIndex][Y / step]);
        result.addAll(sziList.get(lastIndex).get(Y / step).get(0));
        return result;
    }

    static void first(int task) {
        int[] c = {10, 28, 33, 35};
        int[] w = {6, 7, 9, 10};
        int[] w1 = new int[w.length];
        for (int i = 0; i < w.length; i++) {
            w1[i] = w[i] - 1;
        }
        int[] w2 = new int[w.length];
        for (int i = 0; i < w.length; i++) {
            w2[i] = w[i] - 2;
        }
        int YStart = 24;
        int YEnd = 48;

        if (task == 2) {
            System.out.println("Y  F1                    F2                    F3 ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result1 = F(c, w, Y, new ArrayList<>(), new ArrayList<>());
                List<Integer> result2 = F(c, w1, Y, new ArrayList<>(), new ArrayList<>());
                List<Integer> result3 = F(c, w2, Y, new ArrayList<>(), new ArrayList<>());

                System.out.println(Y + " " + result1.get(0) + " " + result2.get(0) + " " + result3.get(0));
            }
        } else if (task == 1) {
            System.out.println("Y F Список СрЗИ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result = F(c, w, Y, new ArrayList<>(), new ArrayList<>());
                System.out.print(Y + " " + result.get(0) + " ");
                for (int i = 1; i < result.size(); i++) {
                    System.out.print(result.get(i) + " ");
                }
                System.out.println();
            }
        }
    }

    static void second(int task) {
        int[] c = {24, 21, 27, 33};
        int[] w = {3, 4, 6, 9};
        int[] b = {2, 2, 3, 3};
        int[] b1 = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            b1[i] = b[i] - 1;
        }
        int[] b2 = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            b2[i] = b[i] + 1;
        }
        int YStart = 23;
        int YEnd = 46;

        if (task == 2) {
            System.out.println("Y  F1                   F2                    F3 ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result1 = F(c, w, Y, new ArrayList<>(), new ArrayList<>(List.of(b)));
                List<Integer> result2 = F(c, w, Y, new ArrayList<>(), new ArrayList<>(List.of(b1)));
                List<Integer> result3 = F(c, w, Y, new ArrayList<>(), new ArrayList<>(List.of(b2)));

                System.out.println(Y + " " + result1.get(0) + " " + result2.get(0) + " " + result3.get(0));
            }
        } else if (task == 1) {
            System.out.println("Y F Список СрЗИ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result = F(c, w, Y, new ArrayList<>(), new ArrayList<>(List.of(b)));
                result.set(1, 2);
                System.out.print(Y + " " + result.get(0) + " ");
                for (int i = 1; i < result.size(); i++) {
                    System.out.print(result.get(i) + " ");
                }
                System.out.println();
            }
        }
    }

    static void third(int task) {
        int[] c = {24, 21, 27, 33};
        int[] w = {3, 4, 6, 9};
        int[] a = {1, 1, 2, 1};
        int[] a1 = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            a1[i] = a[i] + 1;
        }
        int[] b = {2, 2, 3, 3};
        int YStart = 63;
        int YEnd = 77;

        if (task == 2) {
            System.out.println("Y  F1                    F2 ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result1 = F(c, w, Y, new ArrayList<>(List.of(a)), new ArrayList<>(List.of(b)));
                List<Integer> result2 = F(c, w, Y, new ArrayList<>(List.of(a1)), new ArrayList<>(List.of(b)));

                System.out.println(Y + " " + result1.get(0) + " " + result2.get(0));
            }
        } else if (task == 1) {
            System.out.println("Y F Список СрЗИ");
            for (int Y = YStart; Y <= YEnd; Y++) {
                List<Integer> result = F(c, w, Y, new ArrayList<>(List.of(a)), new ArrayList<>(List.of(b)));
                System.out.print(Y + " " + result.get(0) + " ");
                for (int i = 1; i < result.size(); i++) {
                    System.out.print(result.get(i) + " ");
                }
                System.out.println();
            }
        }
    }
}


