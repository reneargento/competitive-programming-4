package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/24.
 */
public class ZeroFiveTwoRendezvous {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int members = FastReader.nextInt();
        int paths = FastReader.nextInt();
        int caseID = 1;

        while (members != 0) {
            String[] names = new String[members];
            for (int i = 0; i < names.length; i++) {
                names[i] = FastReader.next();
            }

            int[][] distances = new int[members][members];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }
            for (int placeID = 0; placeID < distances.length; placeID++) {
                distances[placeID][placeID] = 0;
            }

            for (int i = 0; i < paths; i++) {
                int placeID1 = FastReader.nextInt() - 1;
                int placeID2 = FastReader.nextInt() - 1;
                int distance = FastReader.nextInt();
                distances[placeID1][placeID2] = distance;
                distances[placeID2][placeID1] = distance;
            }
            String bestMember = computeBestMemberPlace(distances, names);
            outputWriter.printLine(String.format("Case #%d : %s", caseID, bestMember));

            caseID++;
            members = FastReader.nextInt();
            paths = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeBestMemberPlace(int[][] distances, String[] names) {
        int smallestDistance = INFINITE;
        int bestPlaceID = 0;

        floydWarshall(distances);

        for (int placeID1 = 0; placeID1 < distances.length; placeID1++) {
            int totalDistance = 0;

            for (int placeID2 = 0; placeID2 < distances.length; placeID2++) {
                totalDistance += distances[placeID2][placeID1];
            }

            if (totalDistance < smallestDistance) {
                smallestDistance = totalDistance;
                bestPlaceID = placeID1;
            }
        }
        return names[bestPlaceID];
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
