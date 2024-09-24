package chapter4.section6.e.bipartite.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/08/24.
 */
@SuppressWarnings("unchecked")
public class AbsurdistanRoadsIII {

    private static class Assignment {
        int cityId1;
        int cityId2;

        public Assignment(int cityId1, int cityId2) {
            this.cityId1 = cityId1;
            this.cityId2 = cityId2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[cities];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int[] degree = new int[adjacencyList.length];

        for (int i = 0; i < cities; i++) {
            int cityId1 = FastReader.nextInt() - 1;
            int cityId2 = FastReader.nextInt() - 1;

            adjacencyList[cityId1].add(cityId2);
            adjacencyList[cityId2].add(cityId1);
            degree[cityId1]++;
            degree[cityId2]++;
        }

        List<Assignment> assignments = computeRoadAssignments(adjacencyList, degree);
        for (Assignment assignment : assignments) {
            outputWriter.printLine(assignment.cityId1 + " " + assignment.cityId2);
        }
        outputWriter.flush();
    }

    private static List<Assignment> computeRoadAssignments(List<Integer>[] adjacencyList, int[] degree) {
        List<Assignment> assignments = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];

        processLeaves(adjacencyList, degree, visited, assignments);

        for (int cityId = 0; cityId < adjacencyList.length; cityId++) {
            if (!visited[cityId]) {
                processCycles(adjacencyList, visited, cityId, assignments);
            }
        }
        return assignments;
    }

    private static void processLeaves(List<Integer>[] adjacencyList, int[] degree, boolean[] visited,
                                     List<Assignment> assignments) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int cityId = 0; cityId < degree.length; cityId++) {
            if (degree[cityId] == 1) {
                stack.push(cityId);
            }
        }

        while (!stack.isEmpty()) {
            int cityId = stack.pop();
            visited[cityId] = true;

            for (int neighborId : adjacencyList[cityId]) {
                if (!visited[neighborId]) {
                    degree[neighborId]--;
                    if (degree[neighborId] == 1) {
                        stack.push(neighborId);
                    }
                    assignments.add(new Assignment(cityId + 1, neighborId + 1));
                }
            }
        }
    }

    private static void processCycles(List<Integer>[] adjacencyList, boolean[] visited, int startCityId,
                                     List<Assignment> assignments) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(startCityId);

        while (!stack.isEmpty()) {
            int cityId = stack.pop();

            for (int neighborId : adjacencyList[cityId]) {
                if (!visited[neighborId]) {
                    visited[cityId] = true;
                    stack.push(neighborId);
                    assignments.add(new Assignment(cityId + 1, neighborId + 1));
                    break;
                }
            }
        }

        // Re-attach cycle
        for (int neighborId : adjacencyList[startCityId]) {
            if (!visited[neighborId]) {
                assignments.add(new Assignment(neighborId + 1, startCityId + 1));
                break;
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
