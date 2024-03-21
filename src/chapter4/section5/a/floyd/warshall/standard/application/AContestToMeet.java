package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/24.
 */
public class AContestToMeet {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int intersections = Integer.parseInt(data[0]);
            int[][] distances = new int[intersections][intersections];
            for (int[] values : distances) {
                Arrays.fill(values, INFINITE);
            }
            for (int intersectionID = 0; intersectionID < distances.length; intersectionID++) {
                distances[intersectionID][intersectionID] = 0;
            }

            int streets = Integer.parseInt(data[1]);

            for (int s = 0; s < streets; s++) {
                int intersectionID1 = FastReader.nextInt();
                int intersectionID2 = FastReader.nextInt();
                int length = FastReader.nextInt();
                distances[intersectionID1][intersectionID2] = length;
                distances[intersectionID2][intersectionID1] = length;
            }

            double[] speeds = new double[3];
            for (int i = 0; i < speeds.length; i++) {
                speeds[i] = FastReader.nextInt();
            }
            int minimumMinutes = computeMinimumMinutes(distances, speeds);
            outputWriter.printLine(minimumMinutes);

            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMinutes(int[][] distances, double[] speeds) {
        double minimumMinutesRequired = 0;
        Arrays.sort(speeds);
        floydWarshall(distances);

        for (int intersectionID = 0; intersectionID < distances.length; intersectionID++) {
            Arrays.sort(distances[intersectionID]);
            double minimumMinutesWithIntersectionID = distances[intersectionID][distances.length - 1] / speeds[0];
            minimumMinutesRequired = Math.max(minimumMinutesRequired, minimumMinutesWithIntersectionID);
        }
        return (int) Math.ceil(minimumMinutesRequired);
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
