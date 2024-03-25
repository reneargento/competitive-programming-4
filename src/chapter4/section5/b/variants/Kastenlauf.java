package chapter4.section5.b.variants;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/03/24.
 */
public class Kastenlauf {

    private static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stores = FastReader.nextInt();
            Coordinates[] coordinates = new Coordinates[stores + 2];
            for (int i = 0; i < coordinates.length; i++) {
                coordinates[i] = new Coordinates(FastReader.nextInt(), FastReader.nextInt());
            }

            outputWriter.printLine(canReachBergkirchweih(coordinates) ? "happy" : "sad");
        }
        outputWriter.flush();
    }

    private static boolean canReachBergkirchweih(Coordinates[] coordinates) {
        int maxDistance = 1000;
        int[][] distances = new int[coordinates.length][coordinates.length];
        for (int[] values : distances) {
            Arrays.fill(values, INFINITE);
        }

        for (int location1 = 0; location1 < coordinates.length; location1++) {
            for (int location2 = 0; location2 < coordinates.length; location2++) {
                int distance = Math.abs(coordinates[location1].x - coordinates[location2].x) +
                        Math.abs(coordinates[location1].y - coordinates[location2].y);
                if (distance <= maxDistance) {
                    distances[location1][location2] = distance;
                    distances[location2][location1] = distance;
                }
            }
        }

        floydWarshall(distances);
        return distances[0][coordinates.length - 1] != INFINITE;
    }

    private static void floydWarshall(int[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
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
