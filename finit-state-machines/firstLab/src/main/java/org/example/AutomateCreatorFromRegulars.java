package org.example;

import java.awt.image.BufferedImageFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AutomateCreatorFromRegulars {
    private static Map<String, Map<String, String>> nfaMatrix;
    private static String[] regularExpression;
    private static final List<String> operations = new ArrayList<>(Arrays.asList("(", ")", "*", "+"));

    private static char newVertex;
    private static String startVertex;
    private static String endVertex;

    public static void main(String[] args) {
        initRegulars("regularExpression.txt");

        createAutomate();
        System.out.println(1);
    }

    private static void initRegulars(String regularExpressionFileName){
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(regularExpressionFileName))){
            if (reader.ready()){
                regularExpression = reader.readLine().split("");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createAutomate(){
        if (regularExpression != null){
            nfaMatrix = new HashMap<>();
            newVertex = 'a';
            startVertex = String.valueOf(newVertex);
            nfaMatrix.put(startVertex, new HashMap<>());
            endVertex = String.valueOf(newVertex);
            int i = 0;
            while (i < regularExpression.length){
                if (!operations.contains(regularExpression[i])){
                    i = noOperationRegularSymbol(i);
                }else if(regularExpression[i].equals("(")){

                    i = bracketRegularSymbol(i,
                            regularExpression.length,
                            startVertex, endVertex);
                }else {
                    i++;
                }
            }
        }
    }

    private static int bracketRegularSymbol(int start, int end,
                                             String intermediateStartVertex,
                                             String intermediateEndVertex){
        start += 1;
        int i = start;
        int cntOfOpenBracket = 0;
        while (i != end) {
            if (regularExpression[i].equals("(")) {
                cntOfOpenBracket++;
            } else if (regularExpression[i].equals(")")) {
                if (cntOfOpenBracket == 0) {
                    break;
                }else {
                    cntOfOpenBracket--;
                }
            }
            i += 1;
        }
        end = i-1;

        i = start;

        while (i <= end){
            if (!operations.contains(regularExpression[i])){
                i = noOperationRegularSymbol(i);
                intermediateEndVertex = endVertex;
            }else if(regularExpression[i].equals("(")){
                i = bracketRegularSymbol(i, end, startVertex, intermediateEndVertex);
            }else if(regularExpression[i].equals("+")){
                i = plusRegularSymbol(i, end,
                        intermediateStartVertex,
                        intermediateEndVertex);
            }else{
                i++;
            }
        }

        return end+1;

    }

    private static int plusRegularSymbol(int start, int end,
                                          String intermediateStartVertex,
                                          String intermediateEndVertex){
        int i = start + 1;
        startVertex = intermediateStartVertex;
        while (i <= end){
            if (!operations.contains(regularExpression[i])) {
                if (i != end) {
                    i = noOperationRegularSymbol(i);
                }else {
                    i = noOperationRegularSymbol(i,
                            startVertex,
                            intermediateEndVertex);
                }
            }else if(regularExpression[i].equals("(")){
                i = bracketRegularSymbol(i, end, intermediateStartVertex, endVertex);
            }else {
                i++;
            }
        }
        return i;
    }

    private static int noOperationRegularSymbol(int i){
        newVertex++;
        endVertex = String.valueOf(newVertex);
        nfaMatrix.put(endVertex, new HashMap<>());
        if (nfaMatrix.get(startVertex).get(regularExpression[i]) == null) {
            nfaMatrix.get(startVertex).put(regularExpression[i],endVertex);
        }else {
            nfaMatrix.get(startVertex).put(regularExpression[i],
                    nfaMatrix.get(startVertex).get(regularExpression[i]) +
                            ";" + endVertex);
        }
        startVertex = endVertex;
        i += 1;
        return i;
    }

    private static int noOperationRegularSymbol(int i,
                                                String intermediateStartVertex,
                                                String intermediateEndVertex){

        if (!nfaMatrix.containsKey(intermediateEndVertex))
            nfaMatrix.put(intermediateEndVertex, new HashMap<>());
        if (nfaMatrix.get(intermediateStartVertex).get(regularExpression[i]) == null) {
            nfaMatrix.get(intermediateStartVertex).put(regularExpression[i],intermediateEndVertex);
        }else {
            nfaMatrix.get(intermediateStartVertex).put(regularExpression[i],
                    nfaMatrix.get(intermediateStartVertex).get(regularExpression[i]) +
                            ";" + intermediateEndVertex);
        }
        startVertex = intermediateEndVertex;
        i += 1;
        return i;
    }

}
