package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/01/26.
 */
public class TheColoredCubes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int colors = FastReader.nextInt();

        while (colors != 0) {
            long possibleCubes = computePossibleCubes(colors);
            outputWriter.printLine(possibleCubes);
            colors = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computePossibleCubes(int colors) {
        return (longPow(colors, 6) + 3 * longPow(colors, 4)
                + 12 * longPow(colors, 3) + 8 * longPow(colors, 2)) / 24;
    }

    private static long longPow(int base, int pow) {
        long result = 1;
        for (int i = 1; i <= pow; i++) {
            result *= base;
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
