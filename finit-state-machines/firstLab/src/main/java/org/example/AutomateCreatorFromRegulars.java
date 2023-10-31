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
            char newVertex = 'a';
            String startVertex = String.valueOf(newVertex);
            nfaMatrix.put(startVertex, new HashMap<>());
            String endVertex = String.valueOf(newVertex);
            int i = 0;
            while (i < regularExpression.length){
                if (!operations.contains(regularExpression[i])){
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
                    i++;
                }
            }
        }
    }

}
