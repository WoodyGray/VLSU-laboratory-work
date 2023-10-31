package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

//1-2 lab

public class DfaNfaAnalyse {
    private static HashMap<String, HashMap<String, String>> matrix;
    private static HashMap<String, HashMap<String, String[]>> dMatrix;
    private static String[] queue;
    private static String strStatus;
    private static String finish;
    public static void main(String[] args) {
        initDMatrix("matrix.csv");
        initQueue("queue.txt");
        if (validationOfValidity()){
            System.out.println("the queue is valid");
        }else {
            System.out.println("the queue is not valid");
        }
    }

    private static void initDMatrix(String fileName){
        dMatrix = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            if (reader.ready()) {
                String[] columns = reader.readLine().split(";");
                strStatus = columns[0];
                for (int i = 1; i < columns.length; i++) {
                    dMatrix.put(columns[i], new HashMap<>());
                }
                while (reader.ready()) {
                    String[] line = reader.readLine().split(";");
                    for (int i = 1; i < line.length; i++) {
                        dMatrix.get(columns[i]).put(line[0], line[i].split(","));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initMatrix(String fileName){
        matrix = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            if (reader.ready()) {
                String[] columns = reader.readLine().split(";");
                strStatus = columns[0];
                for (int i = 1; i < columns.length; i++) {
                    matrix.put(columns[i], new HashMap<>());
                }
                while (reader.ready()) {
                    String[] line = reader.readLine().split(";");
                    for (int i = 1; i < line.length; i++) {
                        matrix.get(columns[i]).put(line[0], line[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void initQueue(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            if (reader.ready()){
                finish = reader.readLine().trim().toLowerCase(Locale.ROOT);
            }
            if (reader.ready()){
                queue = reader.readLine().split(" ");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean validationOfValidity(){
        if (queue != null && strStatus != null && finish != null) {
            if (matrix != null) {
                return DfaAnalyse();
            } else if (dMatrix != null) {
                return NfaAnalyse();
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
    private static boolean DfaAnalyse(){
        boolean status = true;
        String buffer = null;
        for (String i: queue) {
            buffer = matrix.get(i).get(strStatus);
            if (buffer != null){
                strStatus = buffer;
            }else {
                status = false;
                break;
            }
        }
        if (status){
            if (strStatus.equals(finish)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }

    }
    private static boolean NfaAnalyse(){
        String status;

        status = recursionAnalyse(strStatus, 0);
        if (status.equals(finish)){
            return true;
        }else {
            return false;
        }
    }

    private static String recursionAnalyse(String strStatus, int partOfQueue){
        for (int i = partOfQueue; i < queue.length; i++) {
            String[] variants = dMatrix.get(queue[partOfQueue]).get(strStatus);
            if (variants != null) {
                for (String j: variants
                     ) {
                    strStatus = j;
                    if (recursionAnalyse(j, i+1).equals(finish)) {
                        return recursionAnalyse(j, i + 1);
                    }
                }
            } else {
                return "false";
            }
        }
        return strStatus;
    }
}