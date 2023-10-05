package secondLab;


import java.util.Random;

public class Main {
    private static int[] asciCode;
    private static int fileSize;
    public static void main(String[] args) {
        asciCode = mixArray(8);

        fileSize = Encoder.encodePermutation("first.exe", "secondFile.exe", asciCode);
        System.out.println();
        Decoder.decodePermutation("secondFile.exe", "secodFile.exe", asciCode, fileSize);
        System.out.println();

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
}
