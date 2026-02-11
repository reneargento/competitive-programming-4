package chapter5.section5.a.probability.theory.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/02/26.
 */
public class PasswordHacking {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int passwordsNumber = FastReader.nextInt();
        List<Double> probabilities = new ArrayList<>();
        for (int i = 0; i < passwordsNumber; i++) {
            FastReader.next();
            probabilities.add(FastReader.nextDouble());
        }

        double expectedNumber = computeExpectedNumber(probabilities);
        outputWriter.printLine(expectedNumber);
        outputWriter.flush();
    }

    private static double computeExpectedNumber(List<Double> probabilities) {
        double expectedNumber = 0;
        probabilities.sort(Collections.reverseOrder());

        int attempts = 1;
        for (double probability : probabilities) {
            expectedNumber += attempts * probability;
            attempts++;
        }
        return expectedNumber;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
