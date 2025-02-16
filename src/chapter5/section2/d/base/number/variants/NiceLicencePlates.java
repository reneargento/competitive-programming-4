package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/25.
 */
public class NiceLicencePlates {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] values = FastReader.next().split("-");
            int firstPart = getFirstPart(values[0]);//Integer.parseInt(values[0], 26);
            int secondPart = Integer.parseInt(values[1]);
            int difference = Math.abs(firstPart - secondPart);
            outputWriter.printLine(difference <= 100 ? "nice" : "not nice");
        }
        outputWriter.flush();
    }

    private static int getFirstPart(String valueBase26) {
        int firstPart = 0;
        int multiplier = 1;
        int radix = 26;

        for (int i = valueBase26.length() - 1; i >= 0; i--) {
            int symbolValue = valueBase26.charAt(i) - 'A';
            firstPart += symbolValue * multiplier;
            multiplier *= radix;
        }
        return firstPart;
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
