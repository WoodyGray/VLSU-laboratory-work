package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class turingMachine {
    private static Map<String, Map<String, String>> turingMatrix;
    private static String startPoint;
    private static int cursor;
    private static String[] line;
    public static void main(String[] args) {
        initTuringMatrix("turingMatrix.csv");
        initTuringLine("turingLine.csv", startPoint);
        //System.out.println(startMachine());
    }

    private static void initTuringMatrix(String matrixFile){
        turingMatrix = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(matrixFile))){
            if (reader.ready()){
                String[] columns = reader.readLine().split(";");
                startPoint = columns[0];
                String[] mLine;
                while (reader.ready()){
                    mLine = reader.readLine().split(";");
                    turingMatrix.put(mLine[0], new HashMap<>());
                    for (int i = 1; i < columns.length; i++) {
                        if (!mLine[i].equals("-")) {
                            turingMatrix.get(mLine[0]).put(columns[i], mLine[i]);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initTuringLine(String lineFile, String startPoint){
        try (BufferedReader reader = new BufferedReader(new FileReader(lineFile))) {
            if (reader.ready()){
                String[] splitLine = reader.readLine().split("");
                line = new String[splitLine.length + 1];
                line[0] = startPoint;
                for (int i = 1; i < 2; i++) {

                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
