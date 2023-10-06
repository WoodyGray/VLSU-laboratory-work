package secondLab;


import java.util.Arrays;
import java.util.Random;

public class Main {
    private static int[] asciCode;
    private static int fileSize;
    public static void main(String[] args) {
//********************Простая подстановка***************************
//        asciCode = mixArray(256);
//        Encoder.encodeSimpleSubstitution("firstFile.txt",
//                "secondFile.txt", asciCode);
//        Decoder.decodeSimpleSabstitution("secondFile.txt",
//                "thirdFile.txt", asciCode);

//***********************Перестановка**********************************
//        asciCode = mixArray(8);
//
//        fileSize = Encoder.encodePermutation("first.exe", "secondFile.exe", asciCode);
//        Decoder.decodePermutation("secondFile.exe", "secodFile.exe", asciCode, fileSize);

//************************Гамирование***********************************
//        asciCode = rndArrayOfByte(5);
//        Encoder.encodeGamming("firstFile.exe", "secondFile.exe", asciCode);
//        Decoder.decodeGamming("secondFile.exe", "secodFile.exe", asciCode);

//*********************Одноразовый блокнот***************************
//        Encoder.encodeGamming("firstFile.exe",
//                "secondFile.exe", "notepad.txt");
//        Decoder.decodeGamming("secondFile.exe",
//                "secodFile.exe", "notepad.txt");

    }

    private static int[] mixArray(int length){
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        Random rnd = new Random();
        for(int i = 0; i < arr.length; i++) {
            int index = rnd.nextInt(i + 1);
            int a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
        return arr;
    }

    private static int[] rndArrayOfByte(int length){
        int[] arr = new int[length];
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(256);
        }

        return arr;
    }
}
