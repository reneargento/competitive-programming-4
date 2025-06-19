package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/06/25.
 */
public class FlowerGarden {

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        boolean[] isPrime = eratosthenesSieve();

        int tests = FastReader.nextInt();
        for (int t = 0; t < tests; t++) {
            Coordinate[] coordinates = new Coordinate[FastReader.nextInt()];
            int maxDistance = FastReader.nextInt();

            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinate(FastReader.nextInt(), FastReader.nextInt());
            }
            int maximumFlowers = computeMaximumFlowers(isPrime, maxDistance, coordinates);
            outputWriter.printLine(maximumFlowers);
        }
        outputWriter.flush();
    }

    private static int computeMaximumFlowers(boolean[] isPrime, int maxDistance, Coordinate[] coordinates) {
        double distanceWalked = distanceBetweenPoints(new Coordinate(0, 0), coordinates[0]);
        int flowersWatered = 0;
        int maximumFlowers = 0;
        int coordinatesIndex = 1;

        while (distanceWalked <= maxDistance) {
            flowersWatered++;

            if (isPrime[flowersWatered]) {
                maximumFlowers = flowersWatered;
            }

            if (coordinatesIndex == coordinates.length) {
                break;
            }
            distanceWalked += distanceBetweenPoints(coordinates[coordinatesIndex - 1], coordinates[coordinatesIndex]);
            coordinatesIndex++;
        }
        return maximumFlowers;
    }

    private static double distanceBetweenPoints(Coordinate point1, Coordinate point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    private static boolean[] eratosthenesSieve() {
        int maxNumber = 20001;
        boolean[] isPrime = new boolean[(int) maxNumber];

        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
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

        public void flush() {
            writer.flush();
        }
    }
}