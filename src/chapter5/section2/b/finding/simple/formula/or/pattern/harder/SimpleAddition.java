package chapter5.section2.b.finding.simple.formula.or.pattern.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/12/24.
 */
// Based on https://github.com/yulonglong/UVa-Solutions/blob/master/10994.cpp
public class SimpleAddition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int p = FastReader.nextInt();
        int q = FastReader.nextInt();

        while (p >= 0) {
            long result = sumDigitsFrom1ToN(q) - sumDigitsFrom1ToN(p - 1);
            outputWriter.printLine(result);
            p = FastReader.nextInt();
            q = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long sumDigitsFrom1ToN(long number) {
        if (number < 10) {
            return rangeSum(number);
        } else {
            return sumDigitsFrom1ToN(number / 10) + (number / 10 * 45L) + rangeSum(number % 10);
        }
    }

    private static long rangeSum(long number) {
        return number * (number + 1) / 2;
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
