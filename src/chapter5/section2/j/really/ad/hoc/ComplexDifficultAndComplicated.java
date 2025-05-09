package chapter5.section2.j.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/05/25.
 */
public class ComplexDifficultAndComplicated {

    private static final String TOO_COMPLICATED_RESULT = "TOO COMPLICATED";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int a = FastReader.nextInt();
            int b = FastReader.nextInt();

            String powerToRaise = computePowerToRaise(a, b);
            outputWriter.printLine(powerToRaise);
        }
        outputWriter.flush();
    }

    private static String computePowerToRaise(int a, int b) {
        if (b == 0) {
            return "1";
        }
        if (a == 0) {
            return "2";
        }
        double c = a * a - b * b;
        if (c == 0) {
            double ab = 2 * a * b;
            double x = c * c - ab * ab;
            if (x <= (1 << 30)) {
                return "4";
            } else {
                return TOO_COMPLICATED_RESULT;
            }
        } else {
            return TOO_COMPLICATED_RESULT;
        }
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
