package chapter5.section3.f.gcd.and.or.lcm;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/08/25.
 */
public class GCDLCM {

    private static class Result {
        int number1;
        int number2;

        public Result(int number1, int number2) {
            this.number1 = number1;
            this.number2 = number2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int gcd = FastReader.nextInt();
            int lcm = FastReader.nextInt();

            Result pair = computePair(gcd, lcm);
            if (pair == null) {
                outputWriter.printLine("-1");
            } else {
                outputWriter.printLine(pair.number1 + " " + pair.number2);
            }
        }
        outputWriter.flush();
    }

    private static Result computePair(int gcd, int lcm) {
        if (lcm % gcd != 0) {
            return null;
        }
        return new Result(gcd, lcm);
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
