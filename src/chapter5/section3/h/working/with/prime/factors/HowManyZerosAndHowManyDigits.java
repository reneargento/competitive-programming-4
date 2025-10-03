package chapter5.section3.h.working.with.prime.factors;

import java.io.*;

/**
 * Created by Rene Argento on 25/09/25.
 */
public class HowManyZerosAndHowManyDigits {

    private static final double EPSILON = .00000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        double[] digitsCount = computeFactorialDigitsDp();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int number = Integer.parseInt(data[0]);
            int base = Integer.parseInt(data[1]);

            int trailingZeroes = computeTrailingZeroes(number, base);
            int digits = computeFactorialDigits(digitsCount, number, base);
            outputWriter.printLine(trailingZeroes + " " + digits);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeTrailingZeroes(int number, int base) {
        int trailingZeroes = Integer.MAX_VALUE;

        for (int i = 2; i <= base; i++) {
            int exponent = 0;

            while (base % i == 0) {
                exponent++;
                base /= i;
            }

            if (exponent > 0) {
                int trailingZeroesCandidate = 0;

                int primeCopy = i;
                while (primeCopy <= number) {
                    trailingZeroesCandidate += number / primeCopy;
                    primeCopy *= i;
                }
                trailingZeroesCandidate /= exponent;
                trailingZeroes = Math.min(trailingZeroes, trailingZeroesCandidate);
            }
        }
        return trailingZeroes;
    }

    private static int computeFactorialDigits(double[] digitsCount, int number, int base) {
        if (number > 100000) {
            return (int) ((Math.log(Math.sqrt(2 * Math.PI * number))
                            + number * Math.log(number / Math.E)) / Math.log(base)) + 1;
        }
        return (int) Math.ceil(digitsCount[number] / Math.log(base) + EPSILON);
    }

    private static double[] computeFactorialDigitsDp() {
        double[] digitsCount = new double[1000001];
        for (int i = 1; i < digitsCount.length; i++) {
            digitsCount[i] = digitsCount[i - 1] + Math.log(i);
        }
        return digitsCount;
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
