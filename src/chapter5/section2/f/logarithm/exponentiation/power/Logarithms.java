package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/03/25.
 */
public class Logarithms {

    private static class Result {
        int l;
        double x;

        public Result(int l, double x) {
            this.l = l;
            this.x = x;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int n = FastReader.nextInt();
        while (n != 0) {
            Result result = computeLnValues(n);
            outputWriter.printLine(String.format("%d %.8f", result.l, result.x));
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computeLnValues(int n) {
        double lnN = Math.log(n);
        int l = (int) Math.floor(lnN - Math.log(2) + 1);

        double x = 1 - Math.exp(lnN - l);
        return new Result(l, x);
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
