package chapter5.section2.f.logarithm.exponentiation.power;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/25.
 */
public class SchoolSpirit {

    private static class Result {
        double currentGroupScore;
        double averageGroupScore;

        public Result(double currentGroupScore, double averageGroupScore) {
            this.currentGroupScore = currentGroupScore;
            this.averageGroupScore = averageGroupScore;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] scores = new int[FastReader.nextInt()];

        for (int i = 0; i < scores.length; i++) {
            scores[i] = FastReader.nextInt();
        }

        Result result = computeGroupScores(scores);
        outputWriter.printLine(result.currentGroupScore);
        outputWriter.printLine(result.averageGroupScore);
        outputWriter.flush();
    }

    private static Result computeGroupScores(int[] scores) {
        double currentGroupScore = computeGroupScore(scores, scores.length);
        double averageGroupScore = 0;

        for (int skipIndex = 0; skipIndex < scores.length; skipIndex++) {
            averageGroupScore += computeGroupScore(scores, skipIndex);
        }
        averageGroupScore /= scores.length;
        return new Result(currentGroupScore, averageGroupScore);
    }

    private static double computeGroupScore(int[] scores, int skipIndex) {
        double averageGroupScore = 0;
        double currentTotalMultiplier = 1;
        double multiplier = 4.0 / 5.0;

        for (int i = 0; i < scores.length; i++) {
            if (i == skipIndex) {
                continue;
            }
            averageGroupScore += 1.0 / 5.0 * scores[i] * currentTotalMultiplier;
            currentTotalMultiplier *= multiplier;
        }
        return averageGroupScore;
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

        public void flush() {
            writer.flush();
        }
    }
}
