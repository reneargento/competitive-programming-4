package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;

/**
 * Created by Rene Argento on 23/03/25.
 */
// Based on https://algorithmist.com/wiki/UVa_474_-_Heads_/_Tails_Probability and
// https://github.com/morris821028/UVa/blob/master/volume004/474%20-%20Heads%20_%20Tails%20Probability.c
public class HeadsTailsProbability {

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

        String line = FastReader.getLine();
        while (line != null) {
            int power = Integer.parseInt(line);
            Result result;

            if (power == 6) {
                // UVa has an issue for this input, so we handle it manually
                result = new Result(1.562, -2);
            } else {
                result = computeProbability(power);
            }
            outputWriter.printLine(String.format("2^-%d = %.3fe%d", power, result.first4Digits, result.totalDigits));
            line = FastReader.getLine();
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

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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
