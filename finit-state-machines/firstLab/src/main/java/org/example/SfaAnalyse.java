package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SfaAnalyse {
    private static Map<String, Set<Rule>> automate;
    private static Stack<String> stack;
    private static String[] queue;
    private static Set<String> endVertexes;
    private static String startVertex;

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

    }

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
            startVertex = reader.readLine();
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
            Set<Rule> rules;
            Set<Rule> simplyRules;
            Stack<String> copyStack;
            boolean queIsNotValid;
            for (int i = 0; i < queue.length; i++) {
                if (automate.containsKey(startVertex)){
                    queIsNotValid = true;
                    rules = automate.get(startVertex);
                    for (Rule rule: rules) {
                        if (queue[i].equals(rule.whatCame)
                                && stack.peek().equals(rule.whatLies)){
                            simplyRules = getSimilarRules(
                                    startVertex,
                                    rule.whatCame,
                                    rule.whatLies
                            );
                            if (simplyRules != null && simplyRules.size()>1){
                                copyStack = stack;
                                for (Rule r: simplyRules
                                     ) {
                                    if (recursiveValidation(i, r)){
                                        return true;
                                    }
                                }
                                stack = copyStack;
                            }else {
                                startVertex = changeStack(stack, rule);
                            }
                            queIsNotValid = false;
                            break;
                        }
                    }
                    if (queIsNotValid)
                        return false;
                }else{
                    return false;
                }
            }
            if (stack.peek().equals("z0") || endVertexes.contains(startVertex)) {
                return true;
            }else{
                return false;
            }
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
            Integer position, Rule startRule){
        String copyVertex = changeStack(stack, startRule);
        Stack<String> copyStack = stack;
        Set<Rule> rules;
        Set<Rule> simplyRules;
        boolean queIsNotValid;
        for (int i = position; i < queue.length; i++) {
            if (automate.containsKey(copyVertex)){
                queIsNotValid = true;
                rules = automate.get(copyVertex);
                for (Rule rule: rules) {
                    if (queue[i].equals(rule.whatCame)
                            && copyStack.peek().equals(rule.whatLies)){
                        simplyRules = getSimilarRules(
                                copyVertex,
                                rule.whatCame,
                                rule.whatLies
                        );
                        if (simplyRules != null && simplyRules.size()>1){
                            stack = copyStack;
                            for (Rule r: simplyRules
                            ) {
                                if (recursiveValidation(i, r)){
                                    return true;
                                }
                            }
                            copyStack = stack;
                        }else {
                            startVertex = changeStack(copyStack, rule);
                        }
                        queIsNotValid = false;
                        break;
                    }
                }
                if (queIsNotValid)
                    return false;
            }else{
                return false;
            }
        }
        if (copyStack.peek().equals("z0") || endVertexes.contains(copyVertex)) {
            return true;
        }else{
            return false;
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
