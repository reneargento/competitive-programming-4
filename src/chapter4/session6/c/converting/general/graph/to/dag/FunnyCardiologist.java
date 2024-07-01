package chapter4.session6.c.converting.general.graph.to.dag;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/06/24.
 */
public class FunnyCardiologist {

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int pointsNumber = Integer.parseInt(data[0]);
            int pointsToRemove = Integer.parseInt(data[1]);

            Point[] points = new Point[pointsNumber];
            for (int i = 0; i < points.length; i++) {
                points[i] = new Point(FastReader.nextInt(), FastReader.nextInt());
            }

            double minimumLengthCardiogram = computeMinimumLengthCardiogram(points, pointsToRemove);
            outputWriter.printLine(String.format("%.3f", minimumLengthCardiogram));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static double computeMinimumLengthCardiogram(Point[] points, int pointsToRemove) {
        // dp[point id][points to remove] = minimum length
        double[][] dp = new double[points.length][pointsToRemove + 1];
        for (double[] values : dp) {
            Arrays.fill(values, -1);
        }

        double minimumLengthCardiogram = computeMinimumLengthCardiogram(points, dp, 0, pointsToRemove);
        return roundValuePrecisionDigits(minimumLengthCardiogram, 3);
    }

    private static double computeMinimumLengthCardiogram(Point[] points, double[][] dp, int pointId,
                                                         int pointsToRemove) {
        if (pointsToRemove < 0) {
            return Double.POSITIVE_INFINITY;
        }
        if (pointId == points.length - 1) {
            if (pointsToRemove == 0) {
                return 0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
        if (dp[pointId][pointsToRemove] != -1) {
            return dp[pointId][pointsToRemove];
        }

        double minimumLength = Double.POSITIVE_INFINITY;
        for (int pointsToAdvance = 1; pointsToAdvance <= pointsToRemove + 1; pointsToAdvance++) {
            int nextPointId = pointId + pointsToAdvance;
            if (nextPointId == points.length) {
                break;
            }
            int pointsRemoved = pointsToAdvance - 1;

            double lengthCandidate = distanceBetweenPoints(points[pointId], points[nextPointId]) +
                    computeMinimumLengthCardiogram(points, dp, nextPointId, pointsToRemove - pointsRemoved);
            minimumLength = Math.min(minimumLength, lengthCandidate);
        }

        dp[pointId][pointsToRemove] = minimumLength;
        return dp[pointId][pointsToRemove];
    }

    private static double distanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    private static double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
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
