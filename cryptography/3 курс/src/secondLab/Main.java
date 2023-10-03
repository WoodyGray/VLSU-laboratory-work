package secondLab;


import java.util.Random;

public class Main {
    private static int[] asciCode;
    public static void main(String[] args) {
        asciCode = mixAsci();

        Encoder.encodeSimpleSubstitution("first.exe", "secondFile.exe", asciCode);
        System.out.println();
        Decoder.decodeSimpleSabstitution("secondFile.exe", "secodFile.exe", asciCode);
        System.out.println();

    }
    private static int[] mixAsci(){
        int[] arr = new int[256];
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
