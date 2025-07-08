package chapter5.section3.c.finding.prime.factors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/06/25.
 */
public class RaidersOfTheLostSign {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        boolean[] isPrime = eratosthenesSieve();

        for (int t = 0; t < tests; t++) {
            int number = FastReader.nextInt();
            int sign = computeSign(isPrime, number);
            outputWriter.printLine(sign > 0 ? "+" : "-");
        }
        outputWriter.flush();
    }

    private static int computeSign(boolean[] isPrime, int number) {
        if (number == 2) {
            return 1;
        }

        if (isPrime[number]) {
            if ((number + 1) % 4 == 0) {
                return 1;
            } else {
                return -1;
            }
        } else {
            List<Integer> primeFactors = primeFactors(number);
            int sign = 1;
            for (Integer primeFactor : primeFactors) {
                sign *= computeSign(isPrime, primeFactor);
            }
            return sign;
        }
    }

    private static boolean[] eratosthenesSieve() {
        boolean[] isPrime = new boolean[100001];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
    }

    private static List<Integer> primeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();
        int sqrt = (int) Math.sqrt(number);

        for (int i = 2; i <= sqrt; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }
        if (number > 1) {
            primeFactors.add(number);
        }
        return primeFactors;
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
