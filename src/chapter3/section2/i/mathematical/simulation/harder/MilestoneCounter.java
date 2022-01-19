package chapter3.section2.i.mathematical.simulation.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/01/22.
 */
public class MilestoneCounter {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        long[] seenStones = new long[FastReader.nextInt()];
        long[] totalStones = new long[FastReader.nextInt()];

        for (int i = 0; i < seenStones.length; i++) {
            seenStones[i] = FastReader.nextLong();
        }
        for (int i = 0; i < totalStones.length; i++) {
            totalStones[i] = FastReader.nextLong();
        }

        List<Long> possibleDistances = computePossibleDistances(seenStones, totalStones);
        outputWriter.printLine(possibleDistances.size());
        for (int i = 0; i < possibleDistances.size(); i++) {
            outputWriter.print(possibleDistances.get(i));

            if (i != possibleDistances.size() - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
        outputWriter.flush();
    }

    private static List<Long> computePossibleDistances(long[] seenStones, long[] totalStones) {
        Set<Long> possibleDistances = new HashSet<>();
        double[] ratios = computeRatios(seenStones);

        for (int i = 0; i < totalStones.length - ratios.length; i++) {
            if (isPossibleSpeed(totalStones, i, i + 1, ratios)) {
                long distance = totalStones[i + 1] - totalStones[i];
                possibleDistances.add(distance);
            }
        }
        List<Long> possibleDistancesList = new ArrayList<>(possibleDistances);
        Collections.sort(possibleDistancesList);
        return possibleDistancesList;
    }

    private static boolean isPossibleSpeed(long[] totalStones, int start, int next, double[] ratios) {
        double difference = totalStones[next] - totalStones[start];

        for (int i = 1; i < ratios.length; i++) {
            double targetNextDifference = difference * ratios[i];

            long nextDifference = totalStones[next + 1] - totalStones[next];
            if (nextDifference != targetNextDifference) {
                return false;
            }
            next++;
            difference = nextDifference;
        }
        return true;
    }

    private static double[] computeRatios(long[] seenStones) {
        double[] ratios = new double[seenStones.length - 1];
        double difference = 1;

        for (int i = 1; i < seenStones.length; i++) {
            if (i == 1) {
                difference = seenStones[i] - seenStones[i - 1];
                ratios[0] = difference;
            } else {
                long nextDifference = seenStones[i] - seenStones[i - 1];
                ratios[i - 1] = nextDifference / difference;
                difference = nextDifference;
            }
        }
        return ratios;
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
