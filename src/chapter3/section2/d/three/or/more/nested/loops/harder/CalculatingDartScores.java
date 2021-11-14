package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/11/21.
 */
public class CalculatingDartScores {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int targetScore = FastReader.nextInt();

        Set<Integer> singlePoints = computePoints(1);
        Set<Integer> doublePoints = computePoints(2);
        Set<Integer> triplePoints = computePoints(3);

        List<Integer> points = new ArrayList<>();
        points.addAll(singlePoints);
        points.addAll(doublePoints);
        points.addAll(triplePoints);

        List<Integer> darts = computeDarts(targetScore, points);

        if (darts == null) {
            outputWriter.printLine("impossible");
        } else {
            for (Integer dart : darts) {
                if (triplePoints.contains(dart)) {
                    outputWriter.printLine("triple " + dart / 3);
                } else if (doublePoints.contains(dart)) {
                    outputWriter.printLine("double " + dart / 2);
                } else {
                    outputWriter.printLine("single " + dart);
                }
            }
        }
        outputWriter.flush();
    }

    private static Set<Integer> computePoints(int multiplier) {
        Set<Integer> points = new HashSet<>();

        for (int i = 0; i <= 20; i++) {
            points.add(i * multiplier);
        }
        return points;
    }

    private static List<Integer> computeDarts(int targetScore, List<Integer> points) {
        for (int points1 : points) {
            for (int points2 : points) {
                for (int points3 : points) {
                    int sum = points1 + points2 + points3;

                    if (sum == targetScore) {
                        List<Integer> darts = new ArrayList<>();
                        if (points1 != 0) {
                            darts.add(points1);
                        }
                        if (points2 != 0) {
                            darts.add(points2);
                        }
                        if (points3 != 0) {
                            darts.add(points3);
                        }
                        return darts;
                    }
                }
            }
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
