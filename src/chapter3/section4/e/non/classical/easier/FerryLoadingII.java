package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/22.
 */
public class FerryLoadingII {

    private static class Result {
        int totalTime;
        int minimumTrips;

        public Result(int totalTime, int minimumTrips) {
            this.totalTime = totalTime;
            this.minimumTrips = minimumTrips;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int carsInFerry = FastReader.nextInt();
            int timeToCross = FastReader.nextInt();
            int[] arrivalTimes = new int[FastReader.nextInt()];

            for (int i = 0; i < arrivalTimes.length; i++) {
                arrivalTimes[i] = FastReader.nextInt();
            }
            Result result = crossCars(carsInFerry, timeToCross, arrivalTimes);
            outputWriter.printLine(result.totalTime + " " + result.minimumTrips);
        }
        outputWriter.flush();
    }

    private static Result crossCars(int carsInFerry, int timeToCross, int[] arrivalTimes) {
        int totalTime = 0;
        int minimumTrips = 0;
        int currentCars = 0;
        int initialBatchSize = arrivalTimes.length % carsInFerry;

        for (int i = 0; i < arrivalTimes.length; i++) {
            totalTime = Math.max(totalTime, arrivalTimes[i]);
            currentCars++;

            if (i == arrivalTimes.length - 1) {
                totalTime += timeToCross;
                minimumTrips++;
            } else if (currentCars == carsInFerry
                    || (minimumTrips == 0 && currentCars == initialBatchSize)) {
                totalTime = totalTime + (timeToCross * 2);
                minimumTrips++;
                currentCars = 0;
            }
        }
        return new Result(totalTime, minimumTrips);
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
