package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/04/22.
 */
public class TrickOrTreat {

    private static final double EPSILON = 0.000001;

    private static class House {
        double x;
        double y;

        public House(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Result {
        double bestXCoordinate;
        double minimumTime;

        public Result(double bestXCoordinate, double minimumTime) {
            this.bestXCoordinate = bestXCoordinate;
            this.minimumTime = minimumTime;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int housesNumber = FastReader.nextInt();
        while (housesNumber != 0) {
            House[] houses = new House[housesNumber];
            for (int i = 0; i < houses.length; i++) {
                houses[i] = new House(FastReader.nextDouble(), FastReader.nextDouble());
            }

            Result result = getBestMeetingPoint(houses);
            outputWriter.printLine(String.format("%.12f %.12f", result.bestXCoordinate, result.minimumTime));
            housesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result getBestMeetingPoint(House[] houses) {
        double low = -200000;
        double high = 200000;
        double bestXCoordinate = 0;
        double minimumTime = Double.POSITIVE_INFINITY;

        while (low + EPSILON < high) {
            double thirdLength = (high - low) / 3;
            double section1 = low + thirdLength;
            double section2 = high - thirdLength;

            double maxDistance1 = getMaxDistance(houses, section1);
            double maxDistance2 = getMaxDistance(houses, section2);

            if (maxDistance1 <= maxDistance2) {
                high = section2;
                if (maxDistance1 < minimumTime) {
                    minimumTime = maxDistance1;
                    bestXCoordinate = section1;
                }
            } else {
                low = section1;
                if (maxDistance2 < minimumTime) {
                    minimumTime = maxDistance2;
                    bestXCoordinate = section2;
                }
            }
        }
        return new Result(bestXCoordinate, minimumTime);
    }

    private static double getMaxDistance(House[] houses, double xCoordinate) {
        double maxDistance = 0;
        for (House house : houses) {
            double distance = distance(house.x, house.y, xCoordinate, 0);
            maxDistance = Math.max(maxDistance, distance);
        }
        return maxDistance;
    }

    private static double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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
