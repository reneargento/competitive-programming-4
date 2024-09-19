package chapter4.session6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 24/08/24.
 */
@SuppressWarnings("unchecked")
public class JohnsTrip {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int streetId;
        boolean isUsed;

        Edge(int vertex1, int vertex2, int streetId) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.streetId = streetId;
            isUsed = false;
        }

        public int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else {
                return vertex1;
            }
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(streetId, other.streetId);
        }
    }

    private static final int MAX_JUNCTION_ID = 45;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int junctionId1 = FastReader.nextInt();
        int junctionId2 = FastReader.nextInt();

        while (junctionId1 != 0 || junctionId2 != 0) {
            LinkedList<Edge>[] adjacent = new LinkedList[MAX_JUNCTION_ID];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new LinkedList<>();
            }
            LinkedList<Integer>[][] streetIdConnect = new LinkedList[MAX_JUNCTION_ID][MAX_JUNCTION_ID];
            for (int vertexId1 = 0; vertexId1 < streetIdConnect.length; vertexId1++) {
                for (int vertexId2 = 0; vertexId2 < streetIdConnect[vertexId1].length; vertexId2++) {
                    streetIdConnect[vertexId1][vertexId2] = new LinkedList<>();
                }
            }

            int johnnyHouse = -1;

            while (junctionId1 != 0 || junctionId2 != 0) {
                int streetId = FastReader.nextInt();
                Edge edge;

                if (junctionId1 <= junctionId2) {
                    edge = new Edge(junctionId1, junctionId2, streetId);
                } else {
                    edge = new Edge(junctionId2, junctionId1, streetId);
                }
                adjacent[junctionId1].add(edge);
                adjacent[junctionId2].add(edge);
                streetIdConnect[junctionId1][junctionId2].add(streetId);
                streetIdConnect[junctionId2][junctionId1].add(streetId);

                if (johnnyHouse == -1) {
                    johnnyHouse = Math.min(junctionId1, junctionId2);
                }
                junctionId1 = FastReader.nextInt();
                junctionId2 = FastReader.nextInt();
            }

            List<Integer> johnnysTrip = computeJohnnysTrip(adjacent, streetIdConnect, johnnyHouse);
            if (johnnysTrip == null) {
                outputWriter.printLine("Round trip does not exist.\n");
            } else {
                outputWriter.print(johnnysTrip.get(0));
                for (int i = 1; i < johnnysTrip.size(); i++) {
                    outputWriter.print(" " + johnnysTrip.get(i));
                }
                outputWriter.printLine("\n");
            }

            junctionId1 = FastReader.nextInt();
            junctionId2 = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeJohnnysTrip(LinkedList<Edge>[] adjacent,
                                                    LinkedList<Integer>[][] streetIdConnect, int johnnyHouse) {
        for (LinkedList<Edge> neighbors : adjacent) {
            Collections.sort(neighbors);
        }
        for (int vertexId1 = 0; vertexId1 < streetIdConnect.length; vertexId1++) {
            for (int vertexId2 = 0; vertexId2 < streetIdConnect[vertexId1].length; vertexId2++) {
                Collections.sort(streetIdConnect[vertexId1][vertexId2], Collections.reverseOrder());
            }
        }
        return getEulerCycle(adjacent, johnnyHouse, streetIdConnect);
    }

    private static List<Integer> getEulerCycle(LinkedList<Edge>[] adjacent, int johnnyHouse,
                                               LinkedList<Integer>[][] streetIdConnect) {
        // A graph with no edges is considered to have an Eulerian cycle
        int edges = 0;
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();
        }
        edges /= 2;
        if (edges == 0) {
            return new ArrayList<>();
        }

        // Necessary condition: all vertices have even degree
        // (this test is needed or it might find an Eulerian path instead of an Eulerian cycle)
        // An Eulerian path has exactly 2 vertices with odd degrees
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex].size() % 2 != 0) {
                return null;
            }
        }

        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(johnnyHouse);

        List<Integer> eulerCycle = new ArrayList<>();
        int previousVertex = -1;

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.pop();

            while (!adjacent[vertex].isEmpty()) {
                Edge edge = adjacent[vertex].poll();
                if (edge.isUsed) {
                    continue;
                }
                edge.isUsed = true;

                dfsStack.push(vertex);
                vertex = edge.otherVertex(vertex);
            }

            if (previousVertex != -1) {
                int streetId = streetIdConnect[previousVertex][vertex].poll();
                streetIdConnect[vertex][previousVertex].poll();
                eulerCycle.add(streetId);
            }
            previousVertex = vertex;
        }

        if (eulerCycle.size() == edges) {
            Collections.reverse(eulerCycle);
            return eulerCycle;
        } else {
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
