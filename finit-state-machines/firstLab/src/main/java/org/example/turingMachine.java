package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class turingMachine {
    private static Map<String, Map<String, String[]>> turingMatrix;
    private static String startPoint;
    private static int cursor;
    private static List<String> line;
    public static void main(String[] args) {
        initTuringMatrix("turingMatrix.csv");
        initTuringLine("turingLine.csv", startPoint);
        startMachine();
        System.out.println(1);
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
                            turingMatrix.get(mLine[0]).put(columns[i], mLine[i].substring(1,mLine[i].length()-1).split(","));
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
                line = new ArrayList<>();
                line.add(startPoint);
                line.addAll(Arrays.stream(reader.readLine().split("")).toList());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void startMachine(){
        String[] currentDoing = turingMatrix.get(startPoint).get(line.get(1));
        int cursor = 0;
        String buffer;
        if (currentDoing != null){
            while (currentDoing != null){
                if (cursor < line.size()){
                    line.set(cursor+1, currentDoing[1]);
                }

                if (currentDoing[2].equals("r")){
                    line.set(cursor, line.get(cursor + 1));
                    line.set(cursor + 1, currentDoing[0]);
                    cursor++;
                    if (cursor == line.size()-1){
                        line.add("b");
                    }
                }else if (currentDoing[2].equals("l")){
                    line.set(cursor, line.get(cursor-1));
                    line.set(cursor-1, currentDoing[0]);
                    cursor--;
                }

                try {
                    currentDoing = turingMatrix.get(line.get(cursor)).get(line.get(cursor + 1));
                }catch (Exception e){
                    currentDoing = null;
                }
                for (String s:line
                     ) {
                    System.out.print(s);
                }
                System.out.println();
            }
        }else {
            for (String s : line
            ) {
                System.out.print(s);
            }
            System.out.println();
        }

    }
}
