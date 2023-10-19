package org;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Данные для первой задачи
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        int[][] data1 = {
                {19, 123, 199, 280},
                {20, 129, 199, 280},
                {21, 129, 199, 280},
                {22, 144, 209, 315},
                {23, 144, 213, 335},
                {24, 150, 213, 335},
                {25, 150, 213, 335},
                {26, 150, 264, 335},
                {27, 156, 268, 390},
                {28, 171, 268, 390},
                {29, 171, 268, 390},
                {30, 171, 268, 390},
                {31, 177, 278, 400},
                {32, 177, 278, 404},
                {33, 183, 278, 404},
                {34, 183, 278, 404},
                {35, 183, 333, 455},
                {36, 189, 333, 459},
                {37, 204, 333, 459},
                {38, 204, 333, 459},
        };
        for (int[] row : data1) {
            int y = row[0];
            int W = row[1];
            int W_1 = row[2];
            int W_2 = row[3];
            dataset1.addValue(W, "b", String.valueOf(y));
            dataset1.addValue(W_1, "b-1", String.valueOf(y));
            dataset1.addValue(W_2, "b-2", String.valueOf(y));
        }

        JFreeChart chart1 = ChartFactory.createLineChart(
                "Задача 2",
                "Средства",
                "Эффективность",
                dataset1,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel1 = new ChartPanel(chart1);

        // Данные для второй задачи
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        int[][] data2 = {
                {21, 78, 92, 114},
                {22, 80, 96, 118},
                {23, 82, 100, 122},
                {24, 88, 104, 132},
                {25, 92, 110, 136},
                {26, 96, 114, 140},
                {27, 100, 118, 144},
                {28, 104, 122, 154},
                {29, 106, 126, 158},
                {30, 110, 132, 162},
                {31, 114, 136, 166},
                {32, 118, 140, 176},
                {33, 122, 144, 180},
                {34, 126, 148, 184},
                {35, 130, 154, 188},
                {36, 132, 158, 198},
                {37, 136, 162, 202},
                {38, 140, 166, 206},
                {39, 144, 170, 210},
                {40, 148, 176, 220},
                {41, 152, 180, 224},
                {42, 156, 184, 228}
        };
        for (int[] row : data2) {
            int y = row[0];
            int W = row[1];
            int W_1 = row[2];
            int W_2 = row[3];
            dataset2.addValue(W, "W", String.valueOf(y));
            dataset2.addValue(W_1, "W-1", String.valueOf(y));
            dataset2.addValue(W_2, "W-2", String.valueOf(y));
        }

        JFreeChart chart2 = ChartFactory.createLineChart(
                "Задача 1",
                "Средства",
                "Эффективность",
                dataset2,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel2 = new ChartPanel(chart2);

        // Данные для третьей задачи
        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
        int[][] data3 = {
                {122, 707, 707},
                {123, 707, 707},
                {125, 707, 707},
                {126, 707, 707},
                {127, 707, 707},
                {128, 707, 707},
                {129, 707, 707},
                {130, 707, 707},
                {131, 707, 707},
                {132, 707, 707},
                {133, 707, 707},
                {134, 707, 707},
                {135, 707, 707},
                {136, 707, 707},
                {137, 707, 707},
                {138, 707, 707},
                {139, 707, 707},
                {140, 707, 707},
                {141, 707, 707},
                {142, 707, 707},
                {143, 707, 707},
                {143, 707, 707}
        };
        for (int[] row : data3) {
            int Y = row[0];
            int a = row[1];
            int a_plus_1 = row[2];
            dataset3.addValue(a, "a", String.valueOf(Y));
            dataset3.addValue(a_plus_1, "a+1", String.valueOf(Y));
        }

        JFreeChart chart3 = ChartFactory.createLineChart(
                "Задача 3",
                "Средства",
                "Эффективность",
                dataset3,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel3 = new ChartPanel(chart3);

        // Отображение графиков
        JFrame frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(chartPanel1);
        frame.add(chartPanel2);
        frame.add(chartPanel3);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


