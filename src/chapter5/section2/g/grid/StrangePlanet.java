package chapter5.section2.g.grid;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/04/25.
 */
public class StrangePlanet {

    private static class CartesianCoordinates {
        long x;
        long y;

        public CartesianCoordinates(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int houseId1 = FastReader.nextInt();
        int houseId2 = FastReader.nextInt();

        while (houseId1 != -1 || houseId2 != -1) {
            double euclideanDistance = computeEuclideanDistance(houseId1, houseId2);
            outputWriter.printLine(String.format("%.2f", euclideanDistance));

            houseId1 = FastReader.nextInt();
            houseId2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static double computeEuclideanDistance(int houseId1, int houseId2) {
        CartesianCoordinates coordinates1 = computeCoordinates(houseId1);
        CartesianCoordinates coordinates2 = computeCoordinates(houseId2);
        return distanceBetweenPoints(coordinates1.x, coordinates1.y, coordinates2.x, coordinates2.y);
    }

    private static CartesianCoordinates computeCoordinates(int houseId) {
        int level = 0;
        long sum = 0;

        while (sum < houseId) {
            level++;
            sum += level * 4L;
        }

        long x = level * -1;
        long y = 0;
        if (houseId >= sum - level) {
            x += sum - houseId;
            y -= sum - houseId;
            return new CartesianCoordinates(x, y);
        } else {
            sum -= level;
            x = 0;
            y = level * -1;
        }

        if (houseId >= sum - level) {
            x += sum - houseId;
            y += sum - houseId;
            return new CartesianCoordinates(x, y);
        } else {
            sum -= level;
            x = level;
            y = 0;
        }

        if (houseId >= sum - level) {
            x -= sum - houseId;
            y += sum - houseId;
            return new CartesianCoordinates(x, y);
        } else {
            sum -= level;
            x = 0;
            y = level;
        }
        x -= sum - houseId;
        y -= sum - houseId;
        return new CartesianCoordinates(x, y);
    }

    public static double distanceBetweenPoints(long x1, long y1, long x2, long y2) {
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
