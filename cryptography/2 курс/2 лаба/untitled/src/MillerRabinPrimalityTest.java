import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public class MillerRabinPrimalityTest {

    /**
     * Проверяет, является ли заданное большое число простым.
     * @param n число, которое нужно проверить
     * @param k количество итераций алгоритма
     * @return true, если число простое, и false в противном случае
     */
    public static boolean isPrime(BigInteger n, int k) {
        // Проверяем, что число не меньше 2
        if (n.compareTo(BigInteger.valueOf(2)) < 0) {
            return false;
        }

        // Проверяем, что число нечетное
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            return false;
        }

        // Разложение числа n-1 на множители вида d * 2^s
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.valueOf(2));
            s++;
        }

        // k итераций алгоритма Миллера-Рабина
        Random rnd = new Random();
        for (int i = 0; i < k; i++) {
            BigInteger a = new BigInteger(n.bitLength(), rnd).mod(n.subtract(BigInteger.valueOf(2))).add(BigInteger.valueOf(2));

            BigInteger x = a.modPow(d, n);
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }

            boolean isComposite = true;
            for (int r = 0; r < s - 1; r++) {
                x = x.modPow(BigInteger.valueOf(2), n);
                if (x.equals(BigInteger.ONE)) {
                    return false;
                }
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    isComposite = false;
                    break;
                }
            }

            if (isComposite) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        HashMap<String, String> counts = new HashMap<>();
        for (int i = 15; i < 1000; i++) {
            BigInteger n = BigInteger.probablePrime(i, random);
            long startTime = System.currentTimeMillis();
            int k = 10;
            if (isPrime(n, k)) {
                System.out.println(n + " is prime");
            } else {
                System.out.println(n + " is not prime");
            }
            long endTime = System.currentTimeMillis();
            counts.put(n.toString(), String.valueOf(endTime - startTime));
            System.out.println(endTime - startTime);
        }
        printFile(counts, "file.csv");

    }
    private static void printFile(HashMap<String, String> counts, String path) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        for (String c:counts.keySet()
             ) {
            file.write(c +";" +counts.get(c) + "\n");
            file.flush();
        }
    }
}
