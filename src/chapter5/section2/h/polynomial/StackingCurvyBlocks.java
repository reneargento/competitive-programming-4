package chapter5.section2.h.polynomial;

import java.io.*;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 18/04/25.
 */
public class StackingCurvyBlocks {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = inputReader.nextLine();
        while (line != null) {
            String[] data = line.split(" ");
            double[] coefficientsBottomBlock = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                coefficientsBottomBlock[i] = Double.parseDouble(data[i]);
            }

            double[] coefficientsTopBlock = new double[data.length];
            for (int i = 0; i < coefficientsTopBlock.length; i++) {
                coefficientsTopBlock[i] = inputReader.nextDouble();
            }

            double fitQuality = computeFitQuality(coefficientsBottomBlock, coefficientsTopBlock);
            outputWriter.printLine(fitQuality);
            line = inputReader.nextLine();
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

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private double nextDouble() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                res *= 10;
                res += c - '0';
                c = snext();
            }
            if (c == '.') {
                c = snext();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = snext();
                }
            }
            return res * sgn;
        }

        private String nextLine() throws IOException {
            int c = snext();
            if (c == -1) {
                return null;
            }
            while (isSpaceChar(c))
                c = snext();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = snext();
            } while (!isEndOfLine(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
        }

        private boolean isEndOfLine(int c) {
            return c == '\n' || c == '\r' || c == -1;
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
