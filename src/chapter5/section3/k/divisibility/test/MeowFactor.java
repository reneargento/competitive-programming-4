package chapter5.section3.k.divisibility.test;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/10/25.
 */
public class MeowFactor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        long number = FastReader.nextLong();
        long meowFactor = computeMeowFactor(number);
        outputWriter.printLine(meowFactor);
        outputWriter.flush();
    }

    private static long computeMeowFactor(long number) {
        long meowFactor = 1;
        long meowCandidate = 2;

        while (true) {
            long power = 1;
            for (int i = 0; i < 9; i++) {
                power *= meowCandidate;
            }
            if (power > number || power < 0) {
                break;
            }

            if (number % power == 0) {
                meowFactor = meowCandidate;
            }
            meowCandidate++;
        }
        return meowFactor;
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
