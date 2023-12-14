import java.math.BigInteger;
import java.util.Random;
import java.util.StringJoiner;
import java.util.Timer;

public class Main {
    private static StringJoiner result;
    public static void main(String[] args) {
        result = new StringJoiner(" ");
        Random random = new Random();
        BigInteger n1 = BigInteger.probablePrime(30, random);
        BigInteger n2 = BigInteger.probablePrime(30, random);
        System.out.println(n1);
        System.out.println(n2);
        BigInteger n = n1.multiply(n2);
        long startTime = System.currentTimeMillis();
        boolean flag = true;
        BigInteger i = BigInteger.TWO;
        BigInteger root = n.sqrt();
        if (n.mod(i).equals(BigInteger.ZERO)){
            System.out.println("простое");
            decompositionOfNumber(n.divide(i));
            System.out.println(result);
        }else {
            i = i.add(BigInteger.ONE);
            System.out.println(n);
            while (i.compareTo(root) <= 0) {
                flag = !n.mod(i).equals(BigInteger.ZERO);
                //System.out.println(i);
                if (!flag) {
                    result.add(String.valueOf(i));
                    decompositionOfNumber(n.divide(i));
                    break;
                }
                //System.out.println(i);
                i = i.add(BigInteger.ONE);
            }
            if (flag) {
                System.out.println("простое");
            } else {
                System.out.println("непроcтое:");
                //System.out.println(i + " " +n.divide(new BigInteger(String.valueOf(i))));
                System.out.println(result);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        }
    }
    public static void decompositionOfNumber(BigInteger n){
        boolean flag = true;
        BigInteger i = BigInteger.TWO;
        BigInteger root = n.sqrt();
        while (i.compareTo(root) <=0){
            flag = !n.mod(i).equals(BigInteger.ZERO);
            if (!flag){
                result.add(String.valueOf(i));
                decompositionOfNumber(n.divide(i));
                break;
            }
            //System.out.println(i);
            i = i.add(BigInteger.TWO);
        }
    }
}
