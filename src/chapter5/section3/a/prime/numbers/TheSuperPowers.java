package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 20/05/25.
 */
public class TheSuperPowers {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);
        boolean[] isPrime = eratosthenesSieve();
        Set<BigInteger> superPowerNumbersSet = new HashSet<>();
        superPowerNumbersSet.add(BigInteger.ONE);

        BigInteger bigInteger2 = BigInteger.valueOf(2);
        BigInteger maxValue = bigInteger2.pow(64).subtract(BigInteger.ONE);

        for (int base = 2; base <= 65536; base++) {
            for (int power = 2; power < 64; power++) {
                if (!isPrime[power]) {
                    BigInteger superPowerNumber = fastExponentiation(BigInteger.valueOf(base), power);

                    if (superPowerNumber.compareTo(maxValue) <= 0) {
                        superPowerNumbersSet.add(superPowerNumber);
                    } else {
                        break;
                    }
                }
            }
        }

        List<BigInteger> superPowerNumbers = new ArrayList<>(superPowerNumbersSet);
        Collections.sort(superPowerNumbers);
        for (BigInteger superPowerNumber : superPowerNumbers) {
            outputWriter.printLine(superPowerNumber);
        }
        outputWriter.flush();
    }

    private static boolean[] eratosthenesSieve() {
        boolean[] isPrime = new boolean[64];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i < 64; i++) {
            if (isPrime[i]) {
                for (int j = i + i; j < isPrime.length; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    private static BigInteger fastExponentiation(BigInteger base, long exponent) {
        if (exponent == 0) {
            return BigInteger.ONE;
        }

        BigInteger baseSquared = base.multiply(base);
        BigInteger result = fastExponentiation(baseSquared, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return base.multiply(result);
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
