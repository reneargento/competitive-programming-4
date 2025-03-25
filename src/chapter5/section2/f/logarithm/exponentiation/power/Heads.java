package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/25.
 */
public class Heads {

    private static class Result {
        double first4Digits;
        int totalDigits;

        public Result(double first4Digits, int totalDigits) {
            this.first4Digits = first4Digits;
            this.totalDigits = totalDigits;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            int power = FastReader.nextInt();
            Result result = computeProbability(power);
            outputWriter.printLine(String.format("2^-%d = %.3fE%d", power, result.first4Digits, result.totalDigits));
        }
        outputWriter.flush();
    }

    private static Result computeProbability(int power) {
        int totalDigits = computeNumberOfDigitsOfNPowerX(2, -power) - 1;
        double first4Digits = Math.pow(10, Math.log10(2) * (-power) - totalDigits);
        return new Result(first4Digits, totalDigits);
    }

    private static int computeNumberOfDigitsOfNPowerX(int n, int x) {
        return (int) (Math.floor(1 + x * Math.log10(n)));
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
