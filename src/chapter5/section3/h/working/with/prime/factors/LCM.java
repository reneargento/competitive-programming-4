package chapter5.section3.h.working.with.prime.factors;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/09/25.
 */
public class LCM {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long[] lcmRanges = computeLCMRanges();

        int maxNumber = FastReader.nextInt();
        while (maxNumber != 0) {
            long lcm = lcmRanges[maxNumber];

            while (lcm % 10 == 0) {
                lcm /= 10;
            }
            long lastDigit = lcm % 10;
            outputWriter.printLine(lastDigit);
            maxNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeLCMRanges() {
        int maxValue = 1000001;
        long[] lcmRanges = new long[maxValue];
        long[] basePrimes = eratosthenesSieveBasePrimes(maxValue);

        lcmRanges[1] = 1;
        for (int i = 2; i < lcmRanges.length; i++) {
            lcmRanges[i] = (lcmRanges[i - 1] * basePrimes[i]) % 1000000000;
        }
        return lcmRanges;
    }

    private static long[] eratosthenesSieveBasePrimes(int maxValue) {
        long[] basePrimes = new long[maxValue];
        Arrays.fill(basePrimes, 1);
        boolean[] isPrime = new boolean[maxValue];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                for (long j = i; j < isPrime.length; j *= i) {
                    basePrimes[(int) j] = i;
                }
            }
        }
        return basePrimes;
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

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
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
