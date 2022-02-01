package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/01/22.
 */
public class TheArcheryPuzzle {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] scores = new int[FastReader.nextInt()];
            int targetScore = FastReader.nextInt();

            for (int i = scores.length - 1; i >= 0; i--) {
                scores[i] = FastReader.nextInt();
            }

            List<Integer> points = computePointsRequired(scores, targetScore);
            outputWriter.print(String.format("Case %d: ", t));
            if (points != null) {
                outputWriter.print(String.format("[%d]", points.size()));
                for (int point : points) {
                    outputWriter.print(" " + point);
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine("impossible");
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> computePointsRequired(int[] scores, int targetScore) {
        for (int numberOfArrows = 1; numberOfArrows <= 300; numberOfArrows++) {
            LinkedList<Integer> points = new LinkedList<>();
            LinkedList<Integer> result = computePointsRequired(scores, targetScore, points, 0, numberOfArrows, 0);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private static LinkedList<Integer> computePointsRequired(int[] scores, int targetScore,
                                                             LinkedList<Integer> points, int currentScore,
                                                             int numberOfArrows, int arrowsUsed) {
        if (arrowsUsed > numberOfArrows) {
            return null;
        }
        if (currentScore == targetScore) {
            return points;
        }
        if (currentScore > targetScore) {
            return null;
        }

        for (int score : scores) {
            points.addLast(score);
            int newScore = currentScore + score;
            arrowsUsed++;
            LinkedList<Integer> result = computePointsRequired(scores, targetScore, points, newScore,
                    numberOfArrows, arrowsUsed);

            if (result != null) {
                return result;
            }
            points.removeLast();
            arrowsUsed--;
        }
        return null;
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
