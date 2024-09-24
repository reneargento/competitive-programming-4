package chapter4.section6.f.eulerian.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 16/08/24.
 */
@SuppressWarnings("unchecked")
public class TheHouseOfSantaClaus {

    public static void main(String[] args) throws IOException {
        OutputWriter outputWriter = new OutputWriter(System.out);
        computeRoutes(outputWriter);
        outputWriter.flush();
    }

    private static void computeRoutes(OutputWriter outputWriter) {
        List<Integer>[] adjacencyList = buildGraph();
        boolean[][] visited = new boolean[6][6];
        List<Integer> route = new ArrayList<>();
        route.add(1);
        computeRoutes(adjacencyList, route, visited, outputWriter, 0, 1);
    }

    private static void computeRoutes(List<Integer>[] adjacencyList, List<Integer> route, boolean[][] visited,
                                      OutputWriter outputWriter, int edgesVisited, int vertexId) {
        if (edgesVisited == 8) {
            for (int vertexIdInRoute : route) {
                outputWriter.print(vertexIdInRoute);
            }
            outputWriter.printLine();
            return;
        }

        for (int neighbor : adjacencyList[vertexId]) {
            if (!visited[vertexId][neighbor]) {
                route.add(neighbor);
                visited[vertexId][neighbor] = true;
                visited[neighbor][vertexId] = true;

                computeRoutes(adjacencyList, route, visited, outputWriter, edgesVisited + 1, neighbor);

                route.remove(route.size() - 1);
                visited[vertexId][neighbor] = false;
                visited[neighbor][vertexId] = false;
            }
        }
    }

    private static List<Integer>[] buildGraph() {
        List<Integer>[] adjacencyList = new List[6];
        for (int i = 1; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[1].add(2);
        adjacencyList[1].add(3);
        adjacencyList[1].add(5);
        adjacencyList[2].add(1);
        adjacencyList[2].add(3);
        adjacencyList[2].add(5);
        adjacencyList[3].add(1);
        adjacencyList[3].add(2);
        adjacencyList[3].add(4);
        adjacencyList[3].add(5);
        adjacencyList[4].add(3);
        adjacencyList[4].add(5);
        adjacencyList[5].add(1);
        adjacencyList[5].add(2);
        adjacencyList[5].add(3);
        adjacencyList[5].add(4);
        return adjacencyList;
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
