package chapter4.section6.d.tree;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/06/24.
 */
public class CockroachEscapeNetworks {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nests = FastReader.nextInt();
            int trails = FastReader.nextInt();

            int[][] distances = new int[nests + trails][nests + trails];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }
            for (int nestId = 0; nestId < distances.length; nestId++) {
                distances[nestId][nestId] = 0;
            }

            int splitNode = nests;
            for (int i = 0; i < trails; i++) {
                int nestId1 = FastReader.nextInt();
                int nestId2 = FastReader.nextInt();
                distances[nestId1][splitNode] = 1;
                distances[splitNode][nestId2] = 1;

                distances[nestId2][splitNode] = 1;
                distances[splitNode][nestId1] = 1;
                splitNode++;
            }

            int smallestEmergencyResponseTime = computeSmallestEmergencyResponseTime(distances, nests);
            outputWriter.printLine(String.format("Case #%d:", t));
            outputWriter.printLine(smallestEmergencyResponseTime);
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static int computeSmallestEmergencyResponseTime(int[][] distances, int nests) {
        int smallestDiameter = Integer.MAX_VALUE;
        floydWarshall(distances);

        for (int nestId1 = 0; nestId1 < distances.length; nestId1++) {
            int maxDiameterFromNest = 0;
            for (int nestId2 = 0; nestId2 < nests; nestId2++) {
                maxDiameterFromNest = Math.max(maxDiameterFromNest, distances[nestId1][nestId2]);
            }
            smallestDiameter = Math.min(smallestDiameter, maxDiameterFromNest);
        }
        return smallestDiameter;
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
