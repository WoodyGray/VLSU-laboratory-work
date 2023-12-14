package com.woody.lab;

import org.ini4j.Ini;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringJoiner;


public class Main {
    private static HashMap<String, HashMap<String, String>> matrix;
    private static String[] symbols;
    public static void main(String[] args) throws IOException {

        matrix = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader("matrix.txt"));
        symbols = reader.readLine().split(";");
        String[] s;
        while (reader.ready()){
            s = reader.readLine().split(";");
            matrix.put(s[0], new HashMap<>());
            for (int i = 0; i < symbols.length; i++) {
                matrix.get(s[0]).put(symbols[i],s[i+1]);
            }
        }
        reader.close();

        Ini config = new Ini(new File("config.ini"));

        String text = config.get("parameters", "text");
        char[] key = config.get("parameters", "key").toCharArray();
        StringJoiner itog = new StringJoiner("");
        text = text.toUpperCase();
        String now;
        for (int i = 0; i < text.length(); i++) {
            now = (""+key[i%4]).toUpperCase();
            if (matrix.containsKey(now)){
                for (String val:matrix.get(now).keySet()
                     ) {
                    if (matrix.get(now).get(val).equals(""+text.charAt(i))){
                        itog.add(val);
                    }
                }
            }
        }

        System.out.println((itog+"").replaceAll("Е/Ё", "Е"));

    }

}