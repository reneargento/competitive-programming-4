package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 30/07/25.
 */
public class EnumeratingRationalNumbers {

    private static class Fraction {
        long nominator;
        long denominator;

        public Fraction(long nominator, long denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] eulerPhiPrefixSum = computeEulerPhiPrefixSum();

        long k = FastReader.nextLong();
        while (k != 0) {
            Fraction fraction = computeFraction(eulerPhiPrefixSum, k);
            outputWriter.printLine(String.format("%d/%d", fraction.nominator, fraction.denominator));
            k = FastReader.nextLong();
        }
        outputWriter.flush();
    }

    private static Fraction computeFraction(long[] eulerPhiPrefixSum, long k) {
        long nominator;
        long denominator = binarySearch(eulerPhiPrefixSum, k);

        if (denominator == 0) {
            return new Fraction(0, 1);
        } else if (denominator == 1) {
            return new Fraction(1, 1);
        } else {
            long coPrimes = k - eulerPhiPrefixSum[(int) denominator - 1];
            int coPrimesCount = 0;

            for (nominator = 0; coPrimesCount < coPrimes; nominator++) {
                if (gcd(nominator, denominator) == 1) {
                    coPrimesCount++;
                }

                if (coPrimesCount == coPrimes) {
                    break;
                }
            }
            return new Fraction(nominator, denominator);
        }
    }

    private static long[] computeEulerPhiPrefixSum() {
        int[] eulerPhi = eratosthenesSieveEulerPhi();
        long[] eulerPhiPrefixSum = new long[eulerPhi.length];
        eulerPhiPrefixSum[0] = 1;
        eulerPhiPrefixSum[1] = 2;

        for (int i = 2; i < eulerPhi.length; i++) {
            eulerPhiPrefixSum[i] += eulerPhiPrefixSum[i - 1] + eulerPhi[i];
        }
        return eulerPhiPrefixSum;
    }

    private static int[] eratosthenesSieveEulerPhi() {
        int[] eulerPhi = new int[1000000];
        for (int i = 0; i < eulerPhi.length; i++) {
            eulerPhi[i] = i;
        }

        for (int i = 2; i < eulerPhi.length; i++) {
            if (eulerPhi[i] == i) {
                for (int j = i; j < eulerPhi.length; j += i) {
                    eulerPhi[j] = (eulerPhi[j] / i) * (i - 1);
                }
            }
        }
        return eulerPhi;
    }

    private static long binarySearch(long[] values, long target) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (values[middle] < target) {
                low = middle + 1;
            } else {
                result = middle;
                high = middle - 1;
            }
        }
        return result;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
