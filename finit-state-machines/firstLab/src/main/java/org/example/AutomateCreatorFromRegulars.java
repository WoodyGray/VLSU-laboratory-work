package org.example;

import java.awt.image.BufferedImageFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AutomateCreatorFromRegulars {
    private static Map<String, Map<String, String>> nfaMatrix;
    private static Map<String, Map<String, String[]>> dMatrix;
    private static String[] regularExpression;
    private static final List<String> operations = new ArrayList<>(Arrays.asList("(", ")", "*", "+"));

    private static char newVertex;
    private static String startVertex;
    private static String endVertex;

    public static void main(String[] args) {
        initRegulars("regularExpression.txt");

        createAutomate();
        convertToDMatrix();
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

    private static void convertToDMatrix(){
        dMatrix = new HashMap<>();
        for (String v:nfaMatrix.keySet()
             ) {
            for (String vv:nfaMatrix.get(v).keySet()
                 ) {
                if (!dMatrix.containsKey(vv)){
                    dMatrix.put(vv, new HashMap<>());
                }
                dMatrix.get(vv).put(v,
                        nfaMatrix.get(v).get(vv).split(";"));
            }
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
                    if (i < regularExpression.length-1
                            && regularExpression[i+1].equals("*")){
                        i = noOperationRegularSymbol(i, startVertex, startVertex);
                    }else{
                        i = noOperationRegularSymbol(i);
                    }
                }else if(regularExpression[i].equals("(")){
                    newVertex++;
                    endVertex = String.valueOf(newVertex);
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
        boolean isStarSymbol = false;
        if (i < regularExpression.length-1
                && regularExpression[i+1].equals("*"))
            isStarSymbol = true;
        end = i-1;

        i = start;

        while (i <= end){
            if (!operations.contains(regularExpression[i])){
                if (!operations.contains(regularExpression[i+1])) {
                    i = noOperationRegularSymbol(i);
                }else{
                    if (i < regularExpression.length-1
                            && regularExpression[i+1].equals("*")){
                        i = noOperationRegularSymbol(i, startVertex, startVertex);
                    }else{
                        if (!isStarSymbol) {
                            i = noOperationRegularSymbol(i, startVertex,
                                    intermediateEndVertex);
                        }else{
                            i = noOperationRegularSymbol(i, startVertex,
                                    intermediateStartVertex);
                        }
                    }
                }
            }else if(regularExpression[i].equals("(")){
                if (!isStarSymbol) {
                    i = bracketRegularSymbol(i, end, startVertex, intermediateEndVertex);
                }else{
                    i = bracketRegularSymbol(i, end, startVertex, intermediateStartVertex);
                }

//                intermediateEndVertex = endVertex;
            }else if(regularExpression[i].equals("+")){
                if (!isStarSymbol) {
                    i = plusRegularSymbol(i, end, intermediateStartVertex,
                            intermediateEndVertex);
                }else{
                    i = plusRegularSymbol(i, end, intermediateStartVertex,
                            intermediateStartVertex);
                }
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
                    if (i < regularExpression.length-1
                            && regularExpression[i+1].equals("*")){
                        i = noOperationRegularSymbol(i, startVertex, startVertex);
                        if (i == end){
                            i--;
                            i = noOperationRegularSymbol(i,
                                    startVertex,
                                    intermediateEndVertex);
                        }else{
                            i--;
                            i = noOperationRegularSymbol(i);
                        }
                    }else {
                        i = noOperationRegularSymbol(i);
                    }

                }else {
                    if (i < regularExpression.length-1
                            && regularExpression[i+1].equals("*")) {
                        i = noOperationRegularSymbol(i, startVertex, startVertex);
                        i--;
                    }

                    i = noOperationRegularSymbol(i,
                                startVertex,
                                intermediateEndVertex);

                }
            }else if(regularExpression[i].equals("(")){
                i = bracketRegularSymbol(i, end, intermediateStartVertex, intermediateEndVertex);
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

    public static Map<String, Map<String, String[]>> getdMatrix() {
        return dMatrix;
    }
}
