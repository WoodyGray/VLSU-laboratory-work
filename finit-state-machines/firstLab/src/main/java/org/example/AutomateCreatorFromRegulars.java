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
                }else if(regularExpression[i].equals('(')){
                    int start = i+1;
                    while (!regularExpression[i].equals(')')
                            && (i != regularExpression.length
                            && !regularExpression[i].equals(')')))
                        i += 1;

                    bracketRegularSymbol(start, i-1, startVertex, endVertex);
                }
            }
        }
    }

    private static void bracketRegularSymbol(int start, int end, String startVertex, String endVertex){
        String intermediateStartVertex = startVertex;
        for (int i = start; i <= end; i++) {
            if (!operations.contains(regularExpression[i])){
                i = noOperationRegularSymbol(i);
            }else if(regularExpression[i].equals('(')){
                int newStart = i+1;
                while (!regularExpression[i].equals(')')
                        && (i != end
                        && !regularExpression[i].equals(')')))
                    i += 1;

                bracketRegularSymbol(start, i-1, startVertex, endVertex);
            }else if(regularExpression[i].equals('+')){
                
            }
        }

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

}
