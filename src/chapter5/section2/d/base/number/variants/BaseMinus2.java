package chapter5.section2.d.base.number.variants;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/25.
 */
public class BaseMinus2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int integer = FastReader.nextInt();
            String negabinary = convertToNegabinary(integer);
            outputWriter.printLine(String.format("Case #%d: %s", t, negabinary));
        }
        outputWriter.flush();
    }

    private static String convertToNegabinary(int integer) {
        if (integer == 0) {
            return "0";
        }

        StringBuilder negabinary = new StringBuilder();
        while (integer != 0) {
            boolean add1 = (integer % 2 == -1);
            int mod = Math.abs(integer % -2);
            negabinary.append(mod);

            integer = integer / -2;
            if (add1) {
                integer++;
            }
        }
        return negabinary.reverse().toString();
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
