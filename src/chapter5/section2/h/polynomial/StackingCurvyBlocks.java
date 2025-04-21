package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/25.
 */
public class StackingCurvyBlocks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            double[] coefficientsBottomBlock = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                coefficientsBottomBlock[i] = Double.parseDouble(data[i]);
            }

            double[] coefficientsTopBlock = new double[data.length];
            for (int i = 0; i < coefficientsTopBlock.length; i++) {
                coefficientsTopBlock[i] = FastReader.nextDouble();
            }

            double fitQuality = computeFitQuality(coefficientsBottomBlock, coefficientsTopBlock);
            outputWriter.printLine(fitQuality);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeFitQuality(double[] coefficientsBottomBlock, double[] coefficientsTopBlock) {
        double[] coefficients = new double[coefficientsBottomBlock.length];
        for (int i = 0; i < coefficients.length; i++) {
            coefficients[i] = coefficientsTopBlock[i] - coefficientsBottomBlock[i];
        }

        // Differentiate
        double a = coefficients[3] * 3;
        double b = coefficients[2] * 2;
        double c = coefficients[1];

        // Compute roots
        double delta = Math.sqrt(b * b - 4 * a * c);
        double root1 = (-b + delta) / (2 * a);
        double root2 = (-b - delta) / (2 * a);

        // Determine the y coordinate where the 2 blocks meet
        double yMeet = Double.POSITIVE_INFINITY;
        double[] xCandidates = { 0, root1, root2, 1 };
        for (double xCandidate : xCandidates) {
            if (Double.isNaN(xCandidate) || xCandidate < 0 || xCandidate > 1) {
                continue;
            }
            double yCandidate = evaluatePolynomial(coefficients, xCandidate);
            if (yCandidate < yMeet) {
                yMeet = yCandidate;
            }
        }

        // Translate blocks vertically and compute answer
        coefficients[0] -= yMeet;
        double yResult = 0;
        for (double xCandidate : xCandidates) {
            if (Double.isNaN(xCandidate) || xCandidate < 0 || xCandidate > 1) {
                continue;
            }
            double yCandidate = evaluatePolynomial(coefficients, xCandidate);
            yResult = Math.max(yResult, yCandidate);
        }
        return yResult;
    }

    private static double evaluatePolynomial(double[] coefficients, double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
