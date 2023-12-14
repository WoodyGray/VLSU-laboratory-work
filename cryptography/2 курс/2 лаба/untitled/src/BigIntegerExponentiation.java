import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class BigIntegerExponentiation {

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        ArrayList<String> counts = new ArrayList<>();
        BigInteger base = BigInteger.probablePrime(8, random);
        BigInteger mod = BigInteger.probablePrime(100, random);
        for (int i = 1000; i < 1030; i++) {
            BigInteger exponent = BigInteger.probablePrime(i, random);
            long startTime = System.currentTimeMillis();
            System.out.println(modPow(base, exponent,mod));
            long endTime = System.currentTimeMillis();
            counts.add(exponent.toString().length() + ";" + (endTime - startTime));
            System.out.println(endTime - startTime);
        }
        printFile(counts, "file2.csv");
    }
    private static void printFile(ArrayList<String> counts, String path) throws IOException {
        BufferedWriter file = new BufferedWriter(new FileWriter(path));
        for (String c:counts
        ) {
            file.write(c + "\n");
            file.flush();
        }
    }

    public static BigInteger modPow(BigInteger base, BigInteger exponent, BigInteger mod) {
        BigInteger result = BigInteger.ONE;
        base = base.mod(mod);
        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = result.multiply(base).mod(mod);
            }
            base = base.multiply(base).mod(mod);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }
}
