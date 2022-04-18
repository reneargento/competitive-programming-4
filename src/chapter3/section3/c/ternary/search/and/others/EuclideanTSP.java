package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/04/22.
 */
public class EuclideanTSP {

    private static final double EPSILON = 0.00000000001;

    private static class Result {
        double timeToDistributeKeys;
        double cConstant;

        public Result(double timeToDistributeKeys, double cConstant) {
            this.timeToDistributeKeys = timeToDistributeKeys;
            this.cConstant = cConstant;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int airports = FastReader.nextInt();
        double operations = FastReader.nextDouble();
        double tourLength = FastReader.nextDouble();
        double speed = FastReader.nextDouble();

        Result result = distributeKeys(airports, operations, tourLength, speed);
        outputWriter.printLine(String.format("%.10f %.10f", result.timeToDistributeKeys, result.cConstant));
        outputWriter.flush();
    }

    private static Result distributeKeys(int airports, double operations, double tourLength, double speed) {
        double low = 0;
        double high = 500;
        double bestConstant = -1;
        double bestTime = Double.MAX_VALUE;

        while (low + EPSILON <= high) {
            double middle = (high - low) / 3;
            double middle1 = low + middle;
            double middle2 = high - middle;

            double resultTime1 = computeTotalTime(airports, operations, tourLength, speed, middle1);
            double resultTime2 = computeTotalTime(airports, operations, tourLength, speed, middle2);

            if (resultTime1 < bestTime) {
                bestTime = resultTime1;
                bestConstant = middle1;
            }
            if (resultTime2 < bestTime) {
                bestTime = resultTime2;
                bestConstant = middle2;
            }

            if (resultTime1 <= resultTime2) {
                high = middle2;
            } else {
                low = middle1;
            }
        }
        return new Result(bestTime, bestConstant);
    }

    private static double computeTotalTime(int airports, double operations, double tourLength, double speed,
                                           double cConstant) {
        double power = cConstant * Math.sqrt(2);
        double dividend = airports * Math.pow(Math.log(airports) / Math.log(2), power);

        double algorithmTime = dividend / (operations * 1000000000);
        double tourTime = tourLength * (1 + 1 / cConstant) / speed;
        return algorithmTime + tourTime;
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
