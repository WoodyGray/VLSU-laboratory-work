package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DfaMinimization {
    private static Map<String,Map<String, String>> matrixAutomate;
    private static ArrayList<String> admittingVertexes;
    private static Map<String,Map<String, String>> optimizedAutomate;
    private static String startVertex;

    public static void main(String[] args) {
        initMatrixAutomate("matrix.csv");
        initAdmittingVertexes("admittingVertexes.txt");

        startMinimization();
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
                for (String v: certaintyMatrix.keySet()
                     ) {
                    for (String vv: certaintyMatrix.keySet()
                         ) {
                        if (!vv.equals(v)
                                && certaintyMatrix.get(v).get(vv) != 1){
                            String[] variants = new String[]{
                                    matrixAutomate.get(v).get("0"),
                                    matrixAutomate.get(vv).get("0")
                            };

                            if (certaintyMatrix.get(variants[0]).get(variants[1]) == 1){
                                certaintyMatrix.get(vv).put(v, 1);
                                certaintyMatrix.get(v).put(vv, 1);
                                flag = false;
                            }else {
                                if (!((admittingVertexes.contains(variants[0])
                                        && admittingVertexes.contains(variants[1]))
                                        || (!admittingVertexes.contains(variants[0])
                                        && !admittingVertexes.contains(variants[1])))) {
                                    certaintyMatrix.get(vv).put(v, 1);
                                    certaintyMatrix.get(v).put(vv, 1);
                                    flag = false;
                                }
                            }
                            variants = new String[]{
                                    matrixAutomate.get(v).get("1"),
                                    matrixAutomate.get(vv).get("1")
                            };
                            if (certaintyMatrix.get(variants[0]).get(variants[1]) == 1){
                                certaintyMatrix.get(vv).put(v, 1);
                                certaintyMatrix.get(v).put(vv, 1);
                                flag = false;

                            }else {
                                if (!((admittingVertexes.contains(variants[0])
                                        && admittingVertexes.contains(variants[1]))
                                        || (!admittingVertexes.contains(variants[0])
                                        && !admittingVertexes.contains(variants[1])))) {
                                    certaintyMatrix.get(vv).put(v, 1);
                                    certaintyMatrix.get(v).put(vv, 1);
                                    flag = false;
                                }
                            }
                        }
                    }
                }
                if (!flag){
                    flag = true;
                }
            }
            System.out.println(1);
        }
    }

    private static Map<String, Map<String, Integer>> initCertaintyMatrix(){
        Map<String, Map<String, Integer>> certaintyMatrix = new HashMap<>();

        for (String v: matrixAutomate.keySet()
             ) {
            certaintyMatrix.put(v, new HashMap<>());
            for (String vv: matrixAutomate.keySet()
                 ) {
                certaintyMatrix.get(v).put(vv, 0);
            }
        }
        return certaintyMatrix;
    }
}
