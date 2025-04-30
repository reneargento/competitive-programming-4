package chapter5.section2.i.fraction;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/04/25.
 */
public class InPuzzlelandIV {

    private static class Result {
        int number;
        int fractionNominator;
        int fractionDenominator;

        public Result(int number, int fractionNominator, int fractionDenominator) {
            this.number = number;
            this.fractionNominator = fractionNominator;
            this.fractionDenominator = fractionDenominator;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int hour1 = FastReader.nextInt();
            int timeHour1 = FastReader.nextInt();
            int hour2 = FastReader.nextInt();

            Result result = computeTime(hour1, timeHour1, hour2);
            outputWriter.print(String.format("Case %d: ", t));
            if (result.number != -1) {
                outputWriter.print(result.number);
                if (result.fractionNominator != 0) {
                    outputWriter.print(" ");
                }
            }
            if (result.fractionNominator != 0) {
                if (result.fractionDenominator == 1) {
                    outputWriter.print(result.fractionNominator);
                } else {
                    outputWriter.print(result.fractionNominator + "/" + result.fractionDenominator);
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Result computeTime(int hour1, int timeHour1, int hour2) {
        int nominator = timeHour1 * (hour2 - 1);
        int denominator = hour1 - 1;
        int number = -1;

        if (nominator > denominator) {
            number = nominator / denominator;
            nominator -= number * denominator;
        }

        int gcd = gcd(nominator, denominator);
        nominator /= gcd;
        denominator /= gcd;
        return new Result(number, nominator, denominator);
    }

    private static int gcd(int number1, int number2) {
        while (number2 > 0) {
            int temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
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
