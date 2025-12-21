package chapter5.section4.d.others.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/12/25.
 */
public class HoneycombWalk {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int steps = FastReader.nextInt();
            long walks = countWalks(steps);
            outputWriter.printLine(walks);
        }
        outputWriter.flush();
    }

    // https://oeis.org/A002898
    // a(n) = Sum_{i=0..n} (-2)^(n-i) * binomial(n, i) * (Sum_{j=0..i} binomial(i, j)^3)
    private static long countWalks(int steps) {
        long ways = 0;

        for (int i = 0; i <= steps; i++) {
            long waysWithISteps = (long) Math.pow(-2, steps - i) * binomialCoefficient(steps, i);
            long sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += (long) Math.pow(binomialCoefficient(i, j), 3);
            }
            waysWithISteps *= sum;
            ways += waysWithISteps;
        }
        return ways;
    }

    private static long binomialCoefficient(int totalNumbers, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalNumbers - i) / (i + 1);
        }
        return result;
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
