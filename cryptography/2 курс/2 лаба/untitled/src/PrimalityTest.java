import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class PrimalityTest {

    /**
     * Проверяет, является ли заданное большое число простым.
     * @param n число, которое нужно проверить
     * @return true, если число простое, и false в противном случае
     */
    public static boolean isPrime(BigInteger n) {
        // Проверяем, что число не меньше 2
        if (n.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }

        // Проверяем, что число не четное
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            return false;
        }

        // Проверяем, что число не делится на какое-то другое нечетное число
        BigInteger i = BigInteger.valueOf(3);
        while (i.multiply(i).compareTo(n) <= 0) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
            i = i.add(BigInteger.valueOf(2));
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        //BigInteger n = new BigInteger("1234567890123456789");
        Random random = new Random();
        ArrayList<String> counts = new ArrayList<>();
        for (int i = 30; i < 60; i++) {
            BigInteger n = BigInteger.probablePrime(i, random);
            long startTime = System.currentTimeMillis();
            if (isPrime(n)) {
                System.out.println(n + " is prime");
            } else {
                System.out.println(n + " is not prime");
            }
            long endTime = System.currentTimeMillis();
            counts.add(n.toString().length() + ";" + (endTime - startTime));
            System.out.println(endTime - startTime);
        }
        printFile(counts, "file.csv");
    }
    private static void printFile(ArrayList<String> counts, String path) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        for (String c:counts
        ) {
            file.write(c + "\n");
            file.flush();
        }
    }
}
