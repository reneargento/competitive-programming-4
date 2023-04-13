package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/04/23.
 */
@SuppressWarnings("unchecked")
public class Network {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfPlaces = FastReader.nextInt();

        while (numberOfPlaces != 0) {
            List<Integer>[] adjacencyList = new List[numberOfPlaces];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            String line = FastReader.getLine();
            while (!line.equals("0")) {
                String[] data = line.split(" ");
                int placeID = Integer.parseInt(data[0]) - 1;
                for (int i = 1; i < data.length; i++) {
                    int neighborID = Integer.parseInt(data[i]) - 1;
                    adjacencyList[placeID].add(neighborID);
                    adjacencyList[neighborID].add(placeID);
                }
                line = FastReader.getLine();
            }

            ArticulationPoints.computeArticulationPoints(adjacencyList);
            outputWriter.printLine(ArticulationPoints.articulationPoints.size());
            numberOfPlaces = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class ArticulationPoints {
        private static int[] time;
        private static int[] low;
        private static int[] parent;
        private static int count;
        public static Set<Integer> articulationPoints;

        public static void computeArticulationPoints(List<Integer>[] adjacencyList) {
            time = new int[adjacencyList.length];
            low = new int[adjacencyList.length];
            parent = new int[adjacencyList.length];
            count = 0;
            articulationPoints = new HashSet<>();

            Arrays.fill(time, -1);
            Arrays.fill(low, -1);
            Arrays.fill(parent, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex);
                }
            }
        }

        private static void dfs(List<Integer>[] adjacencyList, int vertex) {
            time[vertex] = count;
            low[vertex] = count;
            count++;

            int children = 0;

            for (int neighbor : adjacencyList[vertex]) {
                if (time[neighbor] == -1) {
                    parent[neighbor] = vertex;
                    children++;

                    dfs(adjacencyList, neighbor);

                    low[vertex] = Math.min(low[vertex], low[neighbor]);

                    if (parent[vertex] == -1 && children > 1) {
                        articulationPoints.add(vertex);
                    } else if (parent[vertex] != -1 && low[neighbor] >= time[vertex]) {
                        articulationPoints.add(vertex);
                    }
                } else if (parent[vertex] != neighbor) {
                    low[vertex] = Math.min(low[vertex], time[neighbor]);
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
