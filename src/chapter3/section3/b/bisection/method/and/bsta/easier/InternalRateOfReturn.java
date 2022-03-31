package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/03/22.
 */
public class InternalRateOfReturn {

    private static final double EPSILON = 0.000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int positiveCashFlows = FastReader.nextInt();

        while (positiveCashFlows != 0) {
            int[] cashFlows = new int[positiveCashFlows + 1];
            for (int i = 0; i < cashFlows.length; i++) {
                cashFlows[i] = FastReader.nextInt();
            }

            double IRR = computeIRR(cashFlows);
            outputWriter.printLine(String.format("%.2f", IRR));
            positiveCashFlows = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeIRR(int[] cashFlows) {
        double low = -0.9999;
        double high = 100000;
        double result = -1;

        while (low < high + EPSILON) {
            double IRR = low + (high - low) / 2;
            double equationResult = solveEquation(cashFlows, IRR);

            if (Math.abs(equationResult) <= EPSILON) {
                result = IRR;
                break;
            } else if (equationResult < EPSILON) {
                high = IRR;
            } else {
                low = IRR;
            }
        }
        return roundValuePrecisionDigits(result);
    }

    private static double solveEquation(int[] cashFlows, double IRR) {
        double result = cashFlows[0];
        double divisor = (1 + IRR);
        double currentDivisor = divisor;

        for (int i = 1; i < cashFlows.length; i++) {
            result += cashFlows[i] / currentDivisor;
            currentDivisor *= divisor;
        }
        return result;
    }

    private static double roundValuePrecisionDigits(double value) {
        long valueToMultiply = (long) Math.pow(10, 2);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
