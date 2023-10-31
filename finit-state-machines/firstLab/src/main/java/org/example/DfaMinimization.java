package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//3 lab

public class DfaMinimization {
    private static Map<String,Map<String, String>> matrixAutomate;
    private static ArrayList<String> admittingVertexes;
    private static Map<String,Map<String, String>> optimizedAutomate;
    private static String startVertex;

    public static void main(String[] args) {
        initMatrixAutomate("matrix.csv");
        initAdmittingVertexes("admittingVertexes.txt");

        startMinimization();
        System.out.println(1);
    }

    private static void initMatrixAutomate(String matrixFileName){
        matrixAutomate = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(matrixFileName))){
            if (reader.ready()) {
                String[] columns = reader.readLine().split(";");
                startVertex = columns[0];
                String[] line;
                while (reader.ready()) {
                    line = reader.readLine().split(";");
                    matrixAutomate.put(line[0], new HashMap<>());
                    for (int i = 1; i < line.length; i++) {
                        matrixAutomate.get(line[0]).put(columns[i], line[i]);
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initAdmittingVertexes(String admittingVertexesFileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(admittingVertexesFileName))){
            if (reader.ready()){
                admittingVertexes = new ArrayList<>();
                admittingVertexes.addAll(Arrays.asList(reader.readLine().split(";")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startMinimization(){
        if (
                matrixAutomate != null
                        && !matrixAutomate.isEmpty()
                        && admittingVertexes != null
                        && startVertex != null
        ){
            Map<String, Map<String, Integer>> certaintyMatrix =
                    initCertaintyMatrix();
            boolean flag = true;
            while (flag){
                String[] variants;
                for (String v: certaintyMatrix.keySet()
                     ) {
                    for (String vv: certaintyMatrix.get(v).keySet()
                         ) {
                        if (certaintyMatrix.get(v).get(vv) == 0) {
                            for (String way : matrixAutomate.get(v).keySet()
                            ) {
                                variants = new String[]{
                                        matrixAutomate.get(v).get(way),
                                        matrixAutomate.get(vv).get(way)
                                };
                                if ((!variants[0].equals(variants[1]))
                                        && (certaintyMatrix.get(variants[0]).get(variants[1]) == 1
                                        || (admittingVertexes.contains(variants[0])
                                        && !admittingVertexes.contains(variants[1]))
                                        || (admittingVertexes.contains(variants[1])
                                        && !admittingVertexes.contains(variants[0])))) {
                                    certaintyMatrix.get(v).put(vv, 1);
                                    certaintyMatrix.get(vv).put(v, 1);
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                System.out.println(1);
                if (!flag){
                    flag = true;
                }else {
                    flag = false;
                }
            }

            createOptimizedAutomate(certaintyMatrix);
        }
    }

    private static Map<String, Map<String, Integer>> initCertaintyMatrix(){
        Map<String, Map<String, Integer>> certaintyMatrix = new HashMap<>();

        for (String v: matrixAutomate.keySet()
             ) {
            certaintyMatrix.put(v, new HashMap<>());
            for (String vv: matrixAutomate.keySet()
                 ) {
                if (!vv.equals(v)) certaintyMatrix.get(v).put(vv, 0);
            }
        }
        return certaintyMatrix;
    }

    private static void createOptimizedAutomate(
            Map<String, Map<String, Integer>> certaintyMatrix){
        String[] columns = matrixAutomate.get(startVertex).keySet().toArray(new String[0]);

        optimizedAutomate = new HashMap<>();
        Set<String> usageVertexes = new HashSet<>();
        String[] variants;
        for (String v: certaintyMatrix.keySet()
             ) {
            for (String vv: certaintyMatrix.get(v).keySet()
                 ) {
                if (certaintyMatrix.get(v).get(vv) == 0){
                    if (!optimizedAutomate.containsKey(v + vv)
                            && !optimizedAutomate.containsKey(vv + v)){
                        optimizedAutomate.put(v+vv, new HashMap<>());
                        usageVertexes.add(v);
                        usageVertexes.add(vv);
                        for (String way: columns
                             ) {
                            variants = new String[]{
                                    matrixAutomate.get(v).get(way),
                                    matrixAutomate.get(vv).get(way)
                            };
                            if (variants[0].equals(variants[1])){
                                optimizedAutomate.get(v+vv).put(way, variants[0]);
                            }else {
                                optimizedAutomate.get(v+vv).put(way, variants[0] + variants[1]);
                            }
                        }
                    }
                }
            }
        }
        for (String v: matrixAutomate.keySet()
             ) {
            if (!usageVertexes.contains(v)){
                optimizedAutomate.put(v, new HashMap<>());
                for (String way: columns
                     ) {
                    optimizedAutomate.get(v).put(way, matrixAutomate.get(v).get(way));
                }
            }
        }
        System.out.println(1);

    }


}
