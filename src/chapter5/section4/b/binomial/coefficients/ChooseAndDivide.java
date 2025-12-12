package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Rene Argento on 08/12/25.
 */
public class ChooseAndDivide {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int p = Integer.parseInt(data[0]);
            int q = Integer.parseInt(data[1]);
            int r = Integer.parseInt(data[2]);
            int s = Integer.parseInt(data[3]);

            BigDecimal result = divideBinomialCoefficients(p, q, r, s);
            outputWriter.printLine(result);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static BigDecimal divideBinomialCoefficients(int p, int q, int r, int s) {
        BigDecimal pqBinomialCoefficient = binomialCoefficient(p, q);
        BigDecimal rsBinomialCoefficient = binomialCoefficient(r, s);
        return pqBinomialCoefficient.divide(rsBinomialCoefficient, 5, RoundingMode.HALF_UP);
    }

    private static BigDecimal binomialCoefficient(int totalNumbers, int numbersToChoose) {
        BigDecimal result = BigDecimal.ONE;

        for (int i = 0; i < numbersToChoose; i++) {
            result = result.multiply(BigDecimal.valueOf(totalNumbers - i))
                    .divide(BigDecimal.valueOf(i + 1), RoundingMode.HALF_UP);
        }
        return result;
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
