package chapter2.section2.i.big.integer;

import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Rene Argento on 13/03/21.
 */
public class Division {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        MathContext mathContext = new MathContext(100, RoundingMode.FLOOR);

        while (line != null) {
            String[] values = line.split(" ");
            int t = Integer.parseInt(values[0]);
            int a = Integer.parseInt(values[1]);
            int b = Integer.parseInt(values[2]);

            String formula = String.format("(%d^%d-1)/(%d^%d-1) ", t, a, t, b);
            outputWriter.print(formula);

            if (t == 1) {
                outputWriter.printLine("is not an integer with less than 100 digits.");
            } else if (a == b) {
                outputWriter.printLine("1");
            } else {
                boolean moreThan99Digits = false;
                String resultValue = null;

                if (hasMoreThan99Digits(t, a, b)) {
                    moreThan99Digits = true;
                } else {
                    BigDecimal number1 = BigDecimal.valueOf(t).pow(a).subtract(BigDecimal.ONE);
                    BigDecimal number2 = BigDecimal.valueOf(t).pow(b).subtract(BigDecimal.ONE);
                    BigDecimal result = number1.divide(number2, mathContext);

                    resultValue = result.toString();

                    if (resultValue.length() >= 100) {
                        moreThan99Digits = true;
                    }
                }

                if (!moreThan99Digits) {
                    outputWriter.printLine(resultValue);
                } else {
                    outputWriter.printLine("is not an integer with less than 100 digits.");
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean hasMoreThan99Digits(int t, int a, int b) {
        return (a - b) * (Math.log(t) / Math.log(10)) > 99;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
