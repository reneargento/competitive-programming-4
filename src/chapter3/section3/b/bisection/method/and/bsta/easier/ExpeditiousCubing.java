package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by Rene Argento on 01/04/22.
 */
public class ExpeditiousCubing {

    private static final double EPSILON = 0.000000000001;

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        double[] scores = new double[4];

        for (int i = 0; i < scores.length; i++) {
            scores[i] = inputReader.nextDouble();
        }
        double targetScore = inputReader.nextDouble();

        double time = computeTime(scores, targetScore);
        if (time == Double.POSITIVE_INFINITY) {
            outputWriter.printLine("infinite");
        } else if (time == -1) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.printLine(String.format("%.2f", time));
        }
        outputWriter.flush();
    }

    private static double computeTime(double[] scores, double targetScore) {
        Arrays.sort(scores);
        if (isInfinite(scores, targetScore)) {
            return Double.POSITIVE_INFINITY;
        }
        if (isImpossible(scores, targetScore)) {
            return -1;
        }

        double timeRequired = Double.POSITIVE_INFINITY;
        double low = 1;
        double high = 20;

        while (low + EPSILON < high) {
            double middleTime = low + (high - low) / 2;
            double score = computeScore(scores, middleTime);

            if (score <= targetScore + EPSILON) {
                timeRequired = middleTime;
                low = middleTime;
            } else if (score > targetScore) {
                high = middleTime;
            }
        }
        return timeRequired;
    }

    private static boolean isInfinite(double[] scores, double targetScore) {
        return (scores[1] + scores[2] + scores[3]) / 3 - EPSILON <= targetScore;
    }

    private static boolean isImpossible(double[] scores, double targetScore) {
        return (scores[0] + scores[1] + scores[2]) / 3 - EPSILON > targetScore;
    }

    private static double computeScore(double[] scores, double lastTime) {
        double[] newScores = new double[5];
        System.arraycopy(scores, 0, newScores, 0, scores.length);
        newScores[4] = lastTime;
        Arrays.sort(newScores);

        double total = 0;
        total += newScores[1];
        total += newScores[2];
        total += newScores[3];
        return total / 3;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int snext() throws IOException {
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

        public int nextInt() throws IOException {
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

        public double nextDouble() throws IOException {
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

        public boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
