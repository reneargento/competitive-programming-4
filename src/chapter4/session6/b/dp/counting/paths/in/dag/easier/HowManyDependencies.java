package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/05/24.
 */
@SuppressWarnings("unchecked")
public class HowManyDependencies {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tasks = FastReader.nextInt();

        while (tasks != 0) {
            List<Integer>[] adjacencyList = new List[tasks];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int taskID = 0; taskID < tasks; taskID++) {
                int dependencies = FastReader.nextInt();
                for (int i = 0; i < dependencies; i++) {
                    int dependencyID = FastReader.nextInt() - 1;
                    adjacencyList[dependencyID].add(taskID);
                }
            }

            int taskWithMostDependencies = NumberOfPaths.computeTaskWithMostDependencies(adjacencyList);
            outputWriter.printLine(taskWithMostDependencies);
            tasks = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static class NumberOfPaths {

        public static int computeTaskWithMostDependencies(List<Integer>[] adjacencyList) {
            Set<Integer>[] dependencies = new Set[adjacencyList.length];
            for (int i = 0; i < dependencies.length; i++) {
                dependencies[i] = new HashSet<>();
                dependencies[i].add(i);
            }

            int[] inDegrees = computeInDegrees(adjacencyList);
            int[] topologicalOrder = khanTopologicalSort(adjacencyList, inDegrees);

            for (int vertexID : topologicalOrder) {
                for (int neighbor : adjacencyList[vertexID]) {
                    dependencies[neighbor].addAll(dependencies[vertexID]);
                }
            }
            return getTaskWithMostDependencies(dependencies);
        }

        private static int getTaskWithMostDependencies(Set<Integer>[] dependencies) {
            int taskWithMostDependencies = 0;
            int highestDependenciesSize = -1;

            for (int taskID = 0; taskID < dependencies.length; taskID++) {
                if (dependencies[taskID].size() > highestDependenciesSize) {
                    highestDependenciesSize = dependencies[taskID].size();
                    taskWithMostDependencies = taskID;
                }
            }
            return taskWithMostDependencies + 1;
        }

        private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < adjacencyList.length; i++) {
                for (int neighbor : adjacencyList[i]) {
                    inDegrees[neighbor]++;
                }
            }
            return inDegrees;
        }

        private static int[] khanTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
            int[] inDegreesCopy = new int[inDegrees.length];
            System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegrees.length);

            Queue<Integer> queue = new LinkedList<>();
            for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                if (inDegreesCopy[vertexID] == 0) {
                    queue.add(vertexID);
                }
            }

            int[] topologicalOrder = new int[adjacencyList.length];
            int topologicalOrderIndex = 0;

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();
                topologicalOrder[topologicalOrderIndex++] = currentVertex;

                for (int neighbor : adjacencyList[currentVertex]) {
                    inDegreesCopy[neighbor]--;

                    if (inDegreesCopy[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            return topologicalOrder;
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
