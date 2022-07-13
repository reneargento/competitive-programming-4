package chapter3.section4.c.involving.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/07/22.
 */
public class BirdsOnAWire {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int wireLength = FastReader.nextInt();
        int distance = FastReader.nextInt();
        long[] birdLocations = new long[FastReader.nextInt()];

        for (int i = 0; i < birdLocations.length; i++) {
            birdLocations[i] = FastReader.nextInt();
        }
        int maxBirdsToPlace = computeMaxBirdsToPlace(wireLength, distance, birdLocations);
        outputWriter.printLine(maxBirdsToPlace);
        outputWriter.flush();
    }

    private static int computeMaxBirdsToPlace(int wireLength, int distance, long[] birdLocations) {
        int maxBirdsToPlace = 0;
        Arrays.sort(birdLocations);

        long currentLocation = 6;
        int startIndex = 0;
        if (birdLocations.length > 0) {
            if (birdLocations[0] < 6 + distance) {
                currentLocation = birdLocations[0];
                startIndex = 1;
            } else {
                maxBirdsToPlace++;
            }
        }

        for (int i = startIndex; i < birdLocations.length; i++) {
            long distancesAvailable = (birdLocations[i] - currentLocation) / distance;
            distancesAvailable--;

            if (distancesAvailable > 0) {
                maxBirdsToPlace += distancesAvailable;
            }
            currentLocation = birdLocations[i];
        }
        int finalLocation = wireLength - 6;
        maxBirdsToPlace += (finalLocation - currentLocation) / distance;

        if (birdLocations.length == 0 && maxBirdsToPlace > 0) {
            maxBirdsToPlace++;
        }
        return maxBirdsToPlace;
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
