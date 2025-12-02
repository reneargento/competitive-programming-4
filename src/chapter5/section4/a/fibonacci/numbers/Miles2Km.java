package chapter5.section4.a.fibonacci.numbers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/11/25.
 */
public class Miles2Km {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int number = FastReader.nextInt();
        while (number != 0) {
            double lowestError = computeLowestError(number);
            outputWriter.printLine(String.format("%.2f", lowestError));
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeLowestError(int number) {
        int mod = number % 5;
        if (mod == 0) {
            return 0;
        }

        if (mod == 1 || mod == 4) {
            return 0.40;
        }
        return 0.20;
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
