package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/04/25.
 */
public class MixedFractions {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int numerator = FastReader.nextInt();
        int denominator = FastReader.nextInt();

        while (numerator != 0 || denominator != 0) {
            int wholeNumber = numerator / denominator;
            int remaining = numerator % denominator;
            outputWriter.printLine(wholeNumber + " " + remaining + " / " + denominator);

            numerator = FastReader.nextInt();
            denominator = FastReader.nextInt();
        }
        outputWriter.flush();
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
