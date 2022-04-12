package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/04/22.
 */
public class Duathlon {

    private static class Contestant {
        double runningSpeed;
        double cyclingSpeed;

        public Contestant(double runningSpeed, double cyclingSpeed) {
            this.runningSpeed = runningSpeed;
            this.cyclingSpeed = cyclingSpeed;
        }
    }

    private static class Distances {
        double seconds;
        double runningDistance;
        double cyclingDistance;

        public Distances(double seconds, double runningDistance, double cyclingDistance) {
            this.seconds = seconds;
            this.runningDistance = runningDistance;
            this.cyclingDistance = cyclingDistance;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            int totalDistance = Integer.parseInt(line);
            Contestant[] contestants = new Contestant[FastReader.nextInt()];
            for (int i = 0; i < contestants.length; i++) {
                contestants[i] = new Contestant(FastReader.nextDouble(), FastReader.nextDouble());
            }

            Distances distances = cheatDistances(totalDistance, contestants);
            if (distances.seconds < 0) {
                outputWriter.printLine("The cheater cannot win.");
            } else {
                outputWriter.printLine(String.format("The cheater can win by %.0f seconds with r = %.2fkm and k = %.2fkm.",
                        distances.seconds, distances.runningDistance, distances.cyclingDistance));
            }

            line = FastReader.getLine();
            if (line != null && line.equals("")) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static Distances cheatDistances(int totalDistance, Contestant[] contestants) {
        double bestRunningDistance = 0;
        double bestCyclingDistance = 0;
        double bestTime = -1;
        double low = 0;
        double high = totalDistance;

        for (int i = 0; i < 50; i++) {
            double delta = (high - low) / 3.0;
            double middle1 = low + delta;
            double middle2 = high - delta;

            double cyclingDistance1 = totalDistance - middle1;
            double time1 = computeContestantResult(contestants, middle1, cyclingDistance1);

            double cyclingDistance2 = totalDistance - middle2;
            double time2 = computeContestantResult(contestants, middle2, cyclingDistance2);

            if (time1 >= time2) {
                high = middle2;
            } else {
                low = middle1;
            }

            if (time1 > bestTime) {
                bestTime = time1;
                bestRunningDistance = middle1;
                bestCyclingDistance = cyclingDistance1;
            }
            if (time2 > bestTime) {
                bestTime = time2;
                bestRunningDistance = middle2;
                bestCyclingDistance = cyclingDistance2;
            }
        }
        bestTime = roundValuePrecisionDigits(bestTime);
        return new Distances(bestTime, bestRunningDistance, bestCyclingDistance);
    }

    private static double computeContestantResult(Contestant[] contestants, double runningDistance, double cyclingDistance) {
        double bestTime = Double.MAX_VALUE;
        for (int i = 0; i < contestants.length - 1; i++) {
            double time = computeDuathlonTime(contestants[i], runningDistance, cyclingDistance);
            bestTime = Math.min(bestTime, time);
        }

        double contestantTime = computeDuathlonTime(contestants[contestants.length - 1], runningDistance, cyclingDistance);
        if (bestTime == Double.MAX_VALUE) {
            return contestantTime;
        }
        return bestTime - contestantTime;
    }

    private static double computeDuathlonTime(Contestant contestant, double runningDistance, double cyclingDistance) {
        double hours = runningDistance / contestant.runningSpeed + cyclingDistance / contestant.cyclingSpeed;
        return hours * 60 * 60;
    }

    private static double roundValuePrecisionDigits(double value) {
        long valueToMultiply = (long) Math.pow(10, 2);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
