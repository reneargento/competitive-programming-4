package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/24.
 */
public class EdgetownsTrafficJams {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();

        while (intersections != 0) {
            int[][] originalDistances = readDistances(intersections);
            int[][] proposedDistances = readDistances(intersections);
            int factorA = FastReader.nextInt();
            int constantB = FastReader.nextInt();

            boolean satisfiesRequirements = satisfiesRequirements(originalDistances, proposedDistances, factorA,
                    constantB);
            outputWriter.printLine(satisfiesRequirements ? "Yes" : "No");
            intersections = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[][] readDistances(int intersections) throws IOException {
        int[][] distances = new int[intersections][intersections];
        for (int[] values : distances) {
            Arrays.fill(values, INFINITE);
        }
        for (int intersectionID = 0; intersectionID < distances.length; intersectionID++) {
            distances[intersectionID][intersectionID] = 0;
        }

        for (int intersectionID = 0; intersectionID < distances.length; intersectionID++) {
            String[] connections = FastReader.getLine().split(" ");
            for (int i = 1; i < connections.length; i++) {
                int otherIntersectionID = Integer.parseInt(connections[i]) - 1;
                distances[intersectionID][otherIntersectionID] = 1;
            }
        }
        return distances;
    }

    private static void floydWarshall(int[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }

    private static boolean satisfiesRequirements(int[][] originalDistances, int[][] proposedDistances, int factorA,
                                                 int constantB) {
        floydWarshall(originalDistances);
        floydWarshall(proposedDistances);

        for (int intersectionID1 = 0; intersectionID1 < proposedDistances.length; intersectionID1++) {
            for (int intersectionID2 = 0; intersectionID2 < proposedDistances.length; intersectionID2++) {
                int maxDistanceAllowed = originalDistances[intersectionID1][intersectionID2] * factorA + constantB;
                if (proposedDistances[intersectionID1][intersectionID2] > maxDistanceAllowed) {
                    return false;
                }
            }
        }
        return true;
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

        public void flush() {
            writer.flush();
        }
    }
}
