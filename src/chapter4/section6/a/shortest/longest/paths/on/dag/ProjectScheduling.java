package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/04/24.
 */
@SuppressWarnings("unchecked")
public class ProjectScheduling {

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int MAX_TASKS = 26;
    private static final int MAX_DAYS = 100000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            Map<Character, Integer> taskNameToIDMap = new HashMap<>();
            int[] taskDays = new int[MAX_TASKS];
            int[] inDegrees = new int[MAX_TASKS];
            List<Edge>[] adjacencyList = new List[MAX_TASKS];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            String line = FastReader.getLine();
            while (line != null && !line.isEmpty()) {
                String[] data = line.split(" ");

                int taskID = getTaskID(taskNameToIDMap, data[0].charAt(0));
                int days = Integer.parseInt(data[1]);

                if (data.length == 3) {
                    for (int i = 0; i < data[2].length(); i++) {
                        int dependencyID = getTaskID(taskNameToIDMap, data[2].charAt(i));
                        adjacencyList[dependencyID].add(new Edge(dependencyID, taskID, -days));
                    }
                    inDegrees[taskID] += data[2].length();
                }
                taskDays[taskID] = days;
                line = FastReader.getLine();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacencyList, inDegrees, taskDays);
            outputWriter.printLine(longestPath.getLongestDistance());
        }
        outputWriter.flush();
    }

    private static int getTaskID(Map<Character, Integer> taskNameToIDMap, char taskName) {
        if (!taskNameToIDMap.containsKey(taskName)) {
            taskNameToIDMap.put(taskName, taskNameToIDMap.size());
        }
        return taskNameToIDMap.get(taskName);
    }

    private static class DAGLongestPathWeighted {

        public static class TopologicalSort {

            private static int[] topologicalSort(List<Edge>[] adjacent) {
                Stack<Integer> finishTimes = getFinishTimes(adjacent);

                int[] topologicalSort = new int[finishTimes.size()];
                int arrayIndex = 0;

                while (!finishTimes.isEmpty()) {
                    topologicalSort[arrayIndex++] = finishTimes.pop();
                }
                return topologicalSort;
            }

            private static Stack<Integer> getFinishTimes(List<Edge>[] adjacent) {
                boolean[] visited = new boolean[adjacent.length];
                Stack<Integer> finishTimes = new Stack<>();

                for (int i = 0; i < adjacent.length; i++) {
                    if (!visited[i]) {
                        depthFirstSearch(i, adjacent, finishTimes, visited);
                    }
                }
                return finishTimes;
            }

            private static void depthFirstSearch(int sourceVertex, List<Edge>[] adj, Stack<Integer> finishTimes,
                                                 boolean[] visited) {
                visited[sourceVertex] = true;

                for (Edge edge : adj[sourceVertex]) {
                    int neighbor = edge.vertex2;

                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adj, finishTimes, visited);
                    }
                }
                finishTimes.push(sourceVertex);
            }
        }

        private static int[] distTo;

        public DAGLongestPathWeighted(List<Edge>[] adjacent, int[] inDegrees, int[] taskDays) {
            distTo = new int[adjacent.length];
            Arrays.fill(distTo, MAX_DAYS);
            for (int nodeID = 0; nodeID < adjacent.length; nodeID++) {
                if (inDegrees[nodeID] == 0) {
                    distTo[nodeID] = -taskDays[nodeID];
                }
            }

            int[] topological = TopologicalSort.topologicalSort(adjacent);
            for (int vertex : topological) {
                relax(adjacent, vertex);
            }
        }

        private void relax(List<Edge>[] adjacent, int vertex) {
            for (Edge edge : adjacent[vertex]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                }
            }
        }

        public static int distTo(int vertex) {
            return distTo[vertex];
        }

        public static boolean hasPathTo(int vertex) {
            return distTo[vertex] < MAX_DAYS;
        }

        public int getLongestDistance() {
            int longestDistance = 0;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (-distTo(vertex) > longestDistance) {
                    longestDistance = -distTo(vertex);
                }
            }
            return longestDistance;
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
