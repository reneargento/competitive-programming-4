package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/07/21.
 */
public class MyDearNeighbours {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int places = FastReader.nextInt();
            int[] neighbors = new int[places];
            int minNumberOfNeighbors = Integer.MAX_VALUE;

            for (int i = 0; i < places; i++) {
                String[] neighborsDescription = FastReader.getLine().split(" ");
                neighbors[i] = neighborsDescription.length;
                minNumberOfNeighbors = Math.min(minNumberOfNeighbors, neighbors[i]);
            }

            List<Integer> bestPlaces = new ArrayList<>();
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] == minNumberOfNeighbors) {
                    bestPlaces.add(i + 1);
                }
            }

            for (int i = 0; i < bestPlaces.size(); i++) {
                outputWriter.print(bestPlaces.get(i));

                if (i != bestPlaces.size() - 1) {
                    outputWriter.print(" ");
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
