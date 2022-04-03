package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/04/22.
 */
public class ExpeditiousCubing {

    private static final double EPSILON = 0.000000000001;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        double[] scores = new double[4];

        for (int i = 0; i < scores.length; i++) {
            scores[i] = FastReader.nextDouble();
        }
        double targetScore = FastReader.nextDouble();

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
