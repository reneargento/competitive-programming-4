package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/03/26.
 */
public class RaceTo1 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        Integer[] primeNumbers = eratosthenesSieve();

        for (int t = 1; t <= tests; t++) {
            int integer = FastReader.nextInt();
            double expectedMoves = computeExpectedMoves(primeNumbers, integer);
            outputWriter.printLine(String.format("Case %d: %.10f", t, expectedMoves));
        }
        outputWriter.flush();
    }

    private static double computeExpectedMoves(Integer[] primeNumbers, int integer) {
        // dp[current value]
        double[] dp = new double[integer + 1];
        Arrays.fill(dp, -1);
        return computeExpectedMoves(integer, primeNumbers, dp);
    }

    private static double computeExpectedMoves(int currentValue, Integer[] primeNumbers, double[] dp) {
        if (currentValue == 1) {
            return 0;
        }
        if (dp[currentValue] != -1) {
            return dp[currentValue];
        }

        double moves = 0;
        int availablePrimes = 0;
        int divisors = 0;
        for (int primeNumber : primeNumbers) {
            if (primeNumber > currentValue) {
                break;
            }
            availablePrimes++;

            if (currentValue % primeNumber == 0) {
                divisors++;
                moves += computeExpectedMoves(currentValue / primeNumber, primeNumbers, dp);
            }
        }

        dp[currentValue] = (moves + availablePrimes) / divisors;
        return dp[currentValue];
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[1000001];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
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
