package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SfaAnalyse {
    private static Map<String, Set<Rule>> automate;
    private static Stack<String> stack;
    private static Stack<String> vertexWay;
    private static String[] queue;
    private static Set<String> endVertexes;

    private static class Rule {
        public String whatCame;
        public String whatLies;
        public String whereToGo;
        public String[] whatPut;

        public Rule(String whatCame, String whatLies, String whereToGo, String[] whatPut) {
            this.whatCame = whatCame;
            this.whatLies = whatLies;
            this.whereToGo = whereToGo;
            this.whatPut = whatPut;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rule rule = (Rule) o;
            return whatCame.equals(rule.whatCame) && whatLies.equals(rule.whatLies) && whereToGo.equals(rule.whereToGo) && Arrays.equals(whatPut, rule.whatPut);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(whatCame, whatLies, whereToGo);
            result = 31 * result + Arrays.hashCode(whatPut);
            return result;
        }
    }

    public static void main(String[] args) {
        initSfa("matrixSfa.csv");
        initQueue("queueSfa.txt");
        if (validationOfValidity()){
            System.out.println("the queue is valid");
        }else {
            System.out.println("the queue is not valid");
        }
//        Stack<String> test = new Stack<>();
//        test.add("bb");
//        test1(test);

    }

//    private static void test1(Stack<String> a){
//        a.forEach(System.out::println);
//        test2(a);
//        a.forEach(System.out::println);
//    }
//    private static void test2(Stack<String> a){
//        a = (Stack<String>) a.clone();
//        a.add("aaa");
//    }

    private static void initSfa(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            automate = new HashMap<>();
            endVertexes = new HashSet<>();
            String[] line;
            String[] subLine;
            while (reader.ready()){
                line = reader.readLine().split(";");
                if (line[0].contains("()")) {
                    line[0] = line[0].substring(2);
                    endVertexes.add(line[0]);
                }
                automate.put(line[0], new HashSet<>());

                for (int i = 1; i < line.length; i++) {
                    automate.get(line[0])
                            .add(convertLineToRule(line[i]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initQueue(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            vertexWay = new Stack<>();
            vertexWay.add(reader.readLine());
//            startVertex = reader.readLine();
//            String line = reader.readLine().replaceAll(" ", "e");
//            line = "e" + line + "e";
            queue = reader.readLine().split(" ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Rule convertLineToRule(String line){
        String whereToGo = line.split(":")[0];
        line = line.split(":")[1];
        String whatCame = line.split(",")[0];
        line = line.split(",")[1];
        String whatLies = line.split("/")[0];
        line = line.split("/")[1];
        String[] whatPut = line.split("\\+");
        return new Rule(whatCame, whatLies, whereToGo, whatPut);
    }

    private static boolean validationOfValidity(){
        if (automate != null && queue != null){
            stack = new Stack<>();
            stack.push("z0");
            Set<Rule> simplyRules;
            Set<Rule> epsilRules;
            for (int i = 0; i < queue.length; i++) {
                if (automate.containsKey(vertexWay.peek())){
                    simplyRules = getSimilarRules(
                            vertexWay.peek(),
                            queue[i],
                            stack.peek()
                    );
                    epsilRules = getSimilarRules(
                            vertexWay.peek(),
                            "e",
                            stack.peek()
                    );
                    if (!epsilRules.isEmpty()){
                        for (Rule rule: epsilRules) {
                            if (recursiveValidation(
                                    i,
                                    stack,
                                    vertexWay,
                                    rule
                            )) return true;
                        }
                    }
                    if (!simplyRules.isEmpty()){
                        for (Rule rule: simplyRules) {
                            if (recursiveValidation(
                                    i,
                                    stack,
                                    vertexWay,
                                    rule
                            )){
                                return true;
                            }
                        }
                    }

                    break;
                }
            }
            return false;
        }else {
            return false;
        }
    }

    private static Set<Rule> getSimilarRules(
            String vertex, String whatCame, String whatLies){
        if (automate.get(vertex).size() > 1){
            Set<Rule> rules = new HashSet<>();
            for (Rule rule: automate.get(vertex)
                 ) {
                if (rule.whatCame.equals(whatCame) && rule.whatLies.equals(whatLies)){
                    rules.add(rule);
                }
            }
            return rules;
        }else {
            return null;
        }
    }

    private static boolean recursiveValidation(
            Integer position
            ,Stack<String> stack
            ,Stack<String> vertexWay
            ,Rule rule
    ){
        Set<Rule> rules;
        Set<Rule> simplyRules;
        Set<Rule> epsilRules;
        stack = (Stack<String>) stack.clone();
        vertexWay = (Stack<String>) vertexWay.clone();

        vertexWay.add(changeStack(stack, rule));
        if (!rule.whatCame.equals("e")) position++;

        for (int i = position; i < queue.length; i++) {
//            if (!queue[i].equals("e")){
            if (automate.containsKey(vertexWay.peek())){
                rules = automate.get(vertexWay.peek());
                for (Rule r: rules) {
                    if (queue[i].equals(r.whatCame)
                            && stack.peek().equals(r.whatLies)){
                        simplyRules = getSimilarRules(
                                vertexWay.peek(),
                                r.whatCame,
                                r.whatLies
                        );
                        epsilRules = getSimilarRules(
                                vertexWay.peek(),
                                "e",
                                r.whatLies
                        );
                        if (!epsilRules.isEmpty()){
                            for (Rule rr: epsilRules) {
                                if (recursiveValidation(
                                        i,
                                        stack,
                                        vertexWay,
                                        rr
                                )) return true;
                            }
                        }
                        if (!simplyRules.isEmpty()){
                            for (Rule rr: simplyRules) {
                                if (recursiveValidation(
                                        i,
                                        stack,
                                        vertexWay,
                                        rr
                                )) return true;
                            }
                        }
                        break;
                    }
                }
            }else {
                logOfWay(false, vertexWay);
                return false;
            }
            logOfWay(false, vertexWay);
            return false;
//            }
        }
        if (stack.peek().equals("z0")||endVertexes.contains(vertexWay.peek())){
            logOfWay(true, vertexWay);
            return true;
        }else {
            logOfWay(false, vertexWay);
            return false;
        }
    }

    private static void logOfWay(boolean wayType, Stack<String> vertexWay){
        if (wayType){
            System.out.println("true way:");
            vertexWay.forEach(System.out::print);
            System.out.println();
        }else {
            System.out.println("false way:");
            vertexWay.forEach(System.out::print);
            System.out.println();
        }

    }

    private static String changeStack(Stack<String> stack, Rule rule){
        if (rule.whatPut.length == 1){
            stack.pop();
            if (!rule.whatPut[0].equals("E"))
                stack.add(rule.whatPut[0]);
        }else if(rule.whatPut.length == 2){
            stack.pop();
            stack.add(rule.whatPut[1]);
            stack.add(rule.whatPut[0]);
        }
        return rule.whereToGo;
    }

}
