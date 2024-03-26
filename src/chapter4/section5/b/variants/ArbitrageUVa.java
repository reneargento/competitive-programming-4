package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/03/24.
 */
public class ArbitrageUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int dimensions = Integer.parseInt(line);
            double[][][] conversionRates = new double[dimensions][dimensions][dimensions];

            for (int countryID1 = 0; countryID1 < conversionRates.length; countryID1++) {
                for (int countryID2 = 0; countryID2 < conversionRates.length; countryID2++) {
                    double conversionRate;
                    if (countryID1 == countryID2) {
                        conversionRate = 1;
                    } else {
                        conversionRate = FastReader.nextDouble();
                    }
                    conversionRates[countryID1][countryID2][0] = conversionRate;
                }
            }

            FloydWarshall floydWarshall = new FloydWarshall(conversionRates);
            List<Integer> sequence = floydWarshall.getShortestCycle();
            if (sequence == null) {
                outputWriter.printLine("no arbitrage sequence exists");
            } else {
                outputWriter.print((sequence.get(0) + 1));
                for (int i = 1; i < sequence.size(); i++) {
                    outputWriter.print(String.format(" %d", sequence.get(i) + 1));
                }
                outputWriter.printLine();
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static class FloydWarshall {
        private final double[][][] distances;     // length of shortest v->w path
        private final int[][][] path;             // last vertex on shortest v->w path with k steps

        public FloydWarshall(double[][][] distances) {
            this.distances = distances;
            int vertices = distances.length;
            path = new int[vertices][vertices][vertices];

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    path[vertex1][vertex2][0] = vertex1;
                }
            }

            for (int step = 1; step < vertices; step++) {
                for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                    for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                        for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                            if (distances[vertex2][vertex3][step] <
                                    distances[vertex2][vertex1][step - 1] * distances[vertex1][vertex3][0]) {
                                distances[vertex2][vertex3][step] =
                                        distances[vertex2][vertex1][step - 1] * distances[vertex1][vertex3][0];
                                path[vertex2][vertex3][step] = vertex1; // depth
                            }
                        }
                    }
                }
            }
        }

        public List<Integer> getShortestCycle() {
            int vertices = distances.length;
            List<Integer> shortestCycle = new ArrayList<>();
            Deque<Integer> pathStack = new ArrayDeque<>();

            for (int step = 1; step < vertices; step++) {
                for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                    if (distances[vertex1][vertex1][step] > 1.01) {
                        int previousVertex = path[vertex1][vertex1][step];
                        pathStack.push(previousVertex);

                        for (int previousStep = step - 1; previousStep >= 0; previousStep--) {
                            previousVertex = pathStack.peek();
                            pathStack.push(path[vertex1][previousVertex][previousStep]);
                        }
                        int lastVertex = pathStack.peek();

                        while (!pathStack.isEmpty()) {
                            shortestCycle.add(pathStack.pop());
                        }
                        shortestCycle.add(lastVertex);
                        return shortestCycle;
                    }
                }
            }
            return null;
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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
