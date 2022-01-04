package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class MagicFormula {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int a = FastReader.nextInt();
        int b = FastReader.nextInt();
        int c = FastReader.nextInt();
        int d = FastReader.nextInt();
        int limit = FastReader.nextInt();

        while (a != 0 || b != 0 || c != 0 || d != 0 || limit != 0) {
            int functionsDivisible = computeFunctionsDivisible(a, b, c, d, limit);
            outputWriter.printLine(functionsDivisible);

            a = FastReader.nextInt();
            b = FastReader.nextInt();
            c = FastReader.nextInt();
            d = FastReader.nextInt();
            limit = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeFunctionsDivisible(int a, int b, int c, int d, int limit) {
        int functionsDivisible = 0;

        for (int f = 0; f <= limit; f++) {
            int result = a * f * f + b * f + c;
            if (result % d == 0) {
                functionsDivisible++;
            }
        }
        return functionsDivisible;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
