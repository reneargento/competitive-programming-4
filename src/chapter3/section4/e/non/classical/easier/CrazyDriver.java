package chapter3.section4.e.non.classical.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/07/22.
 */
public class CrazyDriver {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int gates = FastReader.nextInt();
        int[] tollFees = new int[gates - 1];
        int[] openingHours = new int[gates];

        for (int i = 0; i < tollFees.length; i++) {
            tollFees[i] = FastReader.nextInt();
        }
        for (int i = 0; i < openingHours.length; i++) {
            openingHours[i] = FastReader.nextInt();
        }

        long minimumTravelCost = computeMinimumTravelCost(tollFees, openingHours);
        outputWriter.printLine(minimumTravelCost);
        outputWriter.flush();
    }

    private static long computeMinimumTravelCost(int[] tollFees, int[] openingHours) {
        long minimumTravelCost = 0;
        long currentSmallestFee = tollFees[0];
        int time = 1;

        for (int i = 1; i < openingHours.length; i++) {
            currentSmallestFee = Math.min(currentSmallestFee, tollFees[i - 1]);
            minimumTravelCost += tollFees[i - 1];

            if (openingHours[i] > time) {
                int timeToGoBetweenPrevious = openingHours[i] - time;
                minimumTravelCost += (timeToGoBetweenPrevious * currentSmallestFee);
                time += timeToGoBetweenPrevious;

                if (timeToGoBetweenPrevious % 2 == 1) {
                    minimumTravelCost += currentSmallestFee;
                    time++;
                }
            }
            time++;
        }
        return minimumTravelCost;
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
