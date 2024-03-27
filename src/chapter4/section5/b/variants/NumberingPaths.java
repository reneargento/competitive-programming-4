package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/03/24.
 */
public class NumberingPaths {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cityID = 0;
        int streets = FastReader.nextInt();

        try {
            while (true) {
                int[][] distances = new int[30][30];
                int[][] distancesCycles = new int[30][30];
                for (int intersectionID1 = 0; intersectionID1 < distances.length; intersectionID1++) {
                    Arrays.fill(distances[intersectionID1], INFINITE);
                    distances[intersectionID1][intersectionID1] = 0;

                    Arrays.fill(distancesCycles[intersectionID1], INFINITE);
                    distancesCycles[intersectionID1][intersectionID1] = INFINITE;
                }
                int highestIntersection = 0;

                for (int i = 0; i < streets; i++) {
                    int intersectionID1 = FastReader.nextInt();
                    int intersectionID2 = FastReader.nextInt();
                    distances[intersectionID1][intersectionID2] = 1;
                    distancesCycles[intersectionID1][intersectionID2] = 1;
                    highestIntersection = Math.max(highestIntersection, intersectionID1);
                    highestIntersection = Math.max(highestIntersection, intersectionID2);
                }

                long[][] routesCount = countRoutes(distances, distancesCycles, highestIntersection + 1);
                outputWriter.printLine(String.format("matrix for city %d", cityID));
                for (int row = 0; row < routesCount.length; row++) {
                    for (int column = 0; column < routesCount[0].length; column++) {
                        outputWriter.print(routesCount[row][column]);
                        if (column != routesCount[0].length - 1) {
                            outputWriter.print(" ");
                        }
                    }
                    outputWriter.printLine();
                }
                cityID++;
                streets = FastReader.nextInt();
            }
        } catch (Exception e) { }

        outputWriter.flush();
    }

    private static long[][] countRoutes(int[][] distances, int[][] distancesCycles, int highestIntersection) {
        long[][] routesCount = new long[highestIntersection][highestIntersection];

        for (int vertex1 = 0; vertex1 < highestIntersection; vertex1++) {
            for (int vertex2 = 0; vertex2 < highestIntersection; vertex2++) {
                if (distances[vertex1][vertex2] == 1) {
                    routesCount[vertex1][vertex2] = 1;
                }
            }
        }

        floydWarshall(distancesCycles);
        floydWarshall(distances);

        for (int vertex1 = 0; vertex1 < highestIntersection; vertex1++) {
            for (int vertex2 = 0; vertex2 < highestIntersection; vertex2++) {
                if (distancesCycles[vertex1][vertex1] < INFINITE && distances[vertex1][vertex2] < INFINITE) {
                    routesCount[vertex1][vertex2] = -1;
                }
            }
        }

        for (int vertex1 = 0; vertex1 < highestIntersection; vertex1++) {
            for (int vertex2 = 0; vertex2 < highestIntersection; vertex2++) {
                for (int vertex3 = 0; vertex3 < highestIntersection; vertex3++) {
                    if (distances[vertex2][vertex1] + distances[vertex1][vertex3] < INFINITE) {
                        if (routesCount[vertex2][vertex2] == -1
                                || routesCount[vertex2][vertex1] == -1
                                || routesCount[vertex1][vertex3] == -1) {
                            routesCount[vertex2][vertex3] = -1;
                            continue;
                        }
                        routesCount[vertex2][vertex3] += routesCount[vertex2][vertex1] * routesCount[vertex1][vertex3];
                    }
                }
            }
        }
        return routesCount;
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
