package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/12/24.
 */
public class StandardDeviation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();

        while (numbers != 0) {
            double standardDeviation = computeStandardDeviation(numbers);
            outputWriter.printLine(String.format("%.6f", standardDeviation));
            numbers = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    // Based on https://oeis.org/A007290
    private static double computeStandardDeviation(int numbers) {
        double sum = 2 * binomialCoefficient(numbers + 1, 3);
        return Math.sqrt(sum / (numbers - 1));
    }

    private static long binomialCoefficient(int totalLength, int numbersToChoose) {
        long result = 1;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result * (totalLength - i) / (i + 1);
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
