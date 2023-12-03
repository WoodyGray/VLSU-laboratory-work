package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalyzerXML {
    private static boolean isOpenTag;
    private static boolean isElement;

    public static void main(String[] args) {
        analyzeTheFile("pom.xml");
    }

    public static void analyzeTheFile(String fileName){
        isOpenTag = false;
        isElement = false;
        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(fileName))){
            String line;
            while (reader.ready()){
                line = reader.readLine().trim();
                analyzeTheLine(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeTheLine(String line){
        char buffer;
        int i = 0;
        while (i < line.length()){
            buffer = line.charAt(i);
            if (buffer == '<'){
                i = analyseOpenTag(i, line);
            }else if (buffer != ' ' && isElement) {
                i = analyseElement(i, line);
            }else if (buffer != ' ' && isOpenTag) {
                i = analyseProperty(i, line);
            }else if (buffer != ' ' && !isOpenTag){
                i = analyseData(i, line);
            }else{
                i++;
            }
        }
    }

    private static int analyseOpenTag(int i, String line){
        if (i+1 < line.length()
                && (line.charAt(i+1) == '?'
                || line.charAt(i+1) == '/')){
            System.out.println(
                    line.substring(i, i+2)
                            + " (open tag)"
            );
            i+=2;
        }else{
            System.out.println(
                    "< (open tag)"
            );
            i++;
        }
        isElement = true;
        isOpenTag = true;
        return i;
    }

    private static int analyseElement(int i, String line){
        int j = i;
        boolean isEnd = false;
        j++;
        while (j < line.length()){
            char buffer = line.charAt(j);
            if (buffer == ' '
                    || buffer == '?'
                    || buffer == '>') {
                System.out.println(
                        line.substring(i, j)
                                + " (element)"
                );
                i = j;
                isElement = false;
                isEnd = true;
                if (buffer == '?' || buffer == '>'){
                    i = analyseCloseTag(i,line);
                }
                break;
            }
            j++;
        }
        if (!isEnd){
            System.out.println(
                    line.substring(i)
                            + " (element)"
            );
            i = j;
            isElement = false;
        }
        return i;
    }

    private static int analyseCloseTag(int i, String line){
        if (line.charAt(i) == '?'){
            System.out.println(
                    "?> (close tag)"
            );
            i+= 2;
        }else if (line.charAt(i) == '>'){
            System.out.println(
                    "> (close tag)"
            );
            i++;
        }
        isOpenTag = false;
        return i;
    }

    private static int analyseProperty(int i, String line){
        int j = i;
        boolean isEnd = false;
        j++;
        while (j < line.length()){
            char buffer = line.charAt(j);
            if ((buffer == ' '
                    || buffer == '?'
                    || buffer == '>')
            && line.charAt(j-1) == '"') {
                System.out.println(
                        line.substring(i, j)
                                + " (property)"
                );
                i = j;

                if (buffer == '?' || buffer == '>'){
                    i = analyseCloseTag(i,line);
                }
                isEnd = true;
                break;
            }
            j++;
        }
        if (!isEnd){
            System.out.println(
                    line.substring(i)
                            + " (property)"
            );
            i = j;
        }
        return i;
    }

    private static int analyseData(int i, String line){
        int j = i;
        boolean isEnd = false;
        j++;
        while (j < line.length()) {
            char buffer = line.charAt(j);
            if (buffer == '<') {
                System.out.println(
                        line.substring(i, j)
                                + " (data)"
                );
                i = j;
                break;
            }
            j++;
        }
        if (!isEnd){
            System.out.println(
                    line.substring(i)
                            + " (data)"
            );
            i = j;
        }
        return i;
    }
}
