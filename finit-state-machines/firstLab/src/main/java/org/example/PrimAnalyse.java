package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PrimAnalyse {
    private static List<Rule> way;
    private static String[] que;
    private static Set<Rule> rules;
    private static Stack<String> stack;
    private static class Rule{
        public String whatLies;
        public String[] whatToPut;

        public Rule(String whatLies, String[] whatToPut) {
            this.whatLies = whatLies;
            this.whatToPut = whatToPut;
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "whatLies='" + whatLies + '\'' +
                    ", whatToPut=" + Arrays.toString(whatToPut) +
                    '}';
        }
    }
    public static void main(String[] args) {
        initRules("primitiveRules.txt");
        initQue("primitiveQue.txt");
        if (validationOfValidity()){
            System.out.println("queue is valid");
            System.out.println("way:");
            way.forEach(System.out::println);
        }else{
            System.out.println("queue is not valid");
        }
    }

    private static void initRules(String rulesFileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(rulesFileName))){
            rules = new HashSet<>();
            String[] line;
            while (reader.ready()){
                line = reader.readLine().split("->");
                Rule rule = new Rule(
                        line[0],
                        line[1].split(";")
                );
                rules.add(rule);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void initQue(String queFileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(queFileName))){
            if (reader.ready()){
                que = reader.readLine().split(" ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean validationOfValidity(){
        if (rules!= null && que != null){
            stack = new Stack<>();
            stack.push("S");
            Set<Rule> simplyRules = getSimplyRules(stack.peek());
            way =  new ArrayList<>();
            for (Rule rule: simplyRules
                 ) {
                if (recursiveValidation(rule, stack, way, 0)){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean recursiveValidation(
            Rule rule,
            Stack<String> copyStack,
            List<Rule> copyWay,
            int i
    ){
        if (copyStack.peek().equals(rule.whatLies)){
            copyStack = changeStack(rule, copyStack);
            if (que[i].equals(copyStack.peek())){
                copyStack.pop();
                if (copyStack.size() == 0){
                    copyStack.push("z0");
                }
                copyWay = changeWay(rule, copyWay);
                i++;
            }else{
                return false;
            }
        }

        if (!copyStack.peek().equals("z0")) {
            Set<Rule> simplyRules = getSimplyRules(copyStack.peek());
            for (Rule r : simplyRules) {
                if (recursiveValidation(r, copyStack, copyWay, i)) {
                    return true;
                }
            }
        }else if (i >= que.length){
            way = copyWay;
            return true;
        }
        return false;

    }
    private static Stack<String> changeStack(Rule rule, Stack<String> stack){
        stack = (Stack<String>) stack.clone();
        stack.pop();
        for (String whatPut: rule.whatToPut
             ) {
            stack.push(whatPut);
        }
        return stack;
    }

    private static List<Rule> changeWay(Rule rule, List<Rule> way){
        way = new ArrayList<>(way);
        way.add(rule);
        return way;
    }
    private static Set<Rule> getSimplyRules(String whatLies){
        Set<Rule> simplyRules = new HashSet<>();
        for (Rule rule: rules
             ) {
            if (rule.whatLies.equals(whatLies)){
                simplyRules.add(rule);
            }
        }
        return simplyRules;
    }
}
