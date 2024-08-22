package chapter4.session6.e.bipartite.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 11/08/24.
 */
@SuppressWarnings("unchecked")
public class PianoLessons {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int students = FastReader.nextInt();
        int slotsAvailable = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[students + slotsAvailable];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int studentId = 0; studentId < students; studentId++) {
            int timeSlots = FastReader.nextInt();

            for (int t = 0; t < timeSlots; t++) {
                int slot = FastReader.nextInt() - 1;
                int slotVertexId = students + slot;
                adjacencyList[studentId].add(slotVertexId);
            }
        }

        int maxStudents = computeMaximumCardinality(adjacencyList, students);
        outputWriter.printLine(maxStudents);
        outputWriter.flush();
    }

    private static int computeMaximumCardinality(List<Integer>[] adjacencyList, int leftPartitionVertexMaxId) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID = 0; vertexID < leftPartitionVertexMaxId; vertexID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
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
