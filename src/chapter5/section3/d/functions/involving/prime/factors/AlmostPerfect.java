package chapter5.section3.d.functions.involving.prime.factors;

import java.io.*;

/**
 * Created by Rene Argento on 10/07/25.
 */
public class AlmostPerfect {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int number = Integer.parseInt(line);
            long factorsSum = sumFactors(number);
            long difference = Math.abs(factorsSum - number);

            outputWriter.print(number + " ");
            if (difference == 0) {
                outputWriter.printLine("perfect");
            } else if (difference <= 2) {
                outputWriter.printLine("almost perfect");
            } else {
                outputWriter.printLine("not perfect");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static long sumFactors(long number) {
        long factorsSum = 0;
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factorsSum += i;

                if (i != number / i && i != 1) {
                    factorsSum += number / i;
                }
            }
        }
        return factorsSum;
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
