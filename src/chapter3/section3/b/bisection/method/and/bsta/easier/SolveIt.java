package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;

/**
 * Created by Rene Argento on 26/03/22.
 */
public class SolveIt {

    private static final double EPSILON = 0.00000000000001;

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
            int t = Integer.parseInt(data[4]);
            int u = Integer.parseInt(data[5]);

            double x = solveEquation(p, q, r, s, t, u);
            if (x != Double.POSITIVE_INFINITY) {
                double roundedNumber = roundValuePrecisionDigits(x, 4);
                outputWriter.printLine(String.format("%.4f", roundedNumber));
            } else {
                outputWriter.printLine("No solution");
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double solveEquation(int p, int q, int r, int s, int t, int u) {
        double low = 0;
        double high = 1;

        while (low < high) {
            double x = low + (high - low) / 2;
            double solution = solveEquationForX(p, q, r, s, t, u, x);

            if (Math.abs(solution) <= EPSILON) {
                return x;
            } else if (solution < 0) {
                high = x;
            } else {
                low = x;
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    private static double solveEquationForX(int p, int q, int r, int s, int t, int u, double x) {
        return p * Math.pow(Math.E, -x) + q * Math.sin(x) + r * Math.cos(x) + s * Math.tan(x) + t * x * x + u;
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
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
