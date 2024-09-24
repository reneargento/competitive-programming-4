package chapter4.section6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/08/24.
 */
@SuppressWarnings("unchecked")
public class Catenyms {

    private static class Edge implements Comparable<Edge> {
        int neighborId;
        String word;

        public Edge(int neighborId, String word) {
            this.neighborId = neighborId;
            this.word = word;
        }

        @Override
        public int compareTo(Edge other) {
            return word.compareTo(other.word);
        }
    }

    private static final String NO_PATH = "***";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int words = FastReader.nextInt();
            List<Edge>[] adjacent = new List[26];
            for (int i = 0; i < adjacent.length; i++) {
                adjacent[i] = new ArrayList<>();
            }
            int[] inDegrees = new int[26];
            int[] outDegrees = new int[26];

            for (int i = 0; i < words; i++) {
                String word = FastReader.next();
                int firstLetterId = word.charAt(0) - 'a';
                int lastLetterId = word.charAt(word.length() - 1) - 'a';

                outDegrees[firstLetterId]++;
                inDegrees[lastLetterId]++;
                adjacent[firstLetterId].add(new Edge(lastLetterId, word));
            }

            String longestCatenym = computeLongestCatenym(adjacent, inDegrees, outDegrees);
            outputWriter.printLine(longestCatenym);
        }
        outputWriter.flush();
    }

    private static String computeLongestCatenym(List<Edge>[] adjacent, int[] inDegrees, int[] outDegrees) {
        for (int vertexId = 0; vertexId < adjacent.length; vertexId++) {
            Collections.sort(adjacent[vertexId]);
        }

        int startVertexId = computeStartVertexId(adjacent, inDegrees, outDegrees);
        if (startVertexId == -1) {
            return NO_PATH;
        }

        Deque<Edge> eulerCycleStack = getDirectedEulerianPath(adjacent, startVertexId);

        if (eulerCycleStack == null || eulerCycleStack.size() < 3) {
            return NO_PATH;
        }

        Deque<Edge> reverseCycle = new ArrayDeque<>();
        while (!eulerCycleStack.isEmpty()) {
            reverseCycle.push(eulerCycleStack.pop());
        }

        List<Edge> eulerCycle = new ArrayList<>();
        while (!reverseCycle.isEmpty()) {
            eulerCycle.add(reverseCycle.pop());
        }

        StringBuilder longestCatenym = new StringBuilder();

        for (int i = 1; i < eulerCycle.size() - 1; i++) {
            String word = eulerCycle.get(i).word;
            longestCatenym.append(word).append(".");
        }
        String lastWord = eulerCycle.get(eulerCycle.size() - 1).word;
        longestCatenym.append(lastWord);
        return longestCatenym.toString();
    }

    private static int computeStartVertexId(List<Edge>[] adjacent, int[] inDegrees, int[] outDegrees) {
        int startVertexIdCandidates = 0;
        int endVertexIdCandidates = 0;
        int equalDegreeVerticesCount = 0;

        int startVertexId = -1;
        int equalVertexId = -1;

        for (int vertexId = 0; vertexId < adjacent.length; vertexId++) {
            if (inDegrees[vertexId] + 1 == outDegrees[vertexId]) {
                startVertexIdCandidates++;
                startVertexId = vertexId;
            } else if (inDegrees[vertexId] == outDegrees[vertexId] + 1) {
                endVertexIdCandidates++;
            } else if (inDegrees[vertexId] == outDegrees[vertexId]) {
                equalDegreeVerticesCount++;
                if (inDegrees[vertexId] > 0 && equalVertexId == -1) {
                    equalVertexId = vertexId;
                }
            }
        }

        if (equalDegreeVerticesCount == adjacent.length) {
            // Eulerian cycle
            return equalVertexId;
        } else if (startVertexIdCandidates == 1 && endVertexIdCandidates == 1
                && equalDegreeVerticesCount == adjacent.length - 2) {
            // Eulerian path
            return startVertexId;
        }
        return -1;
    }

    private static Deque<Edge> getDirectedEulerianPath(List<Edge>[] adjacent, int startVertexId) {
        int edges = 0;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();
        }

        // A graph with no edges is considered to have an Eulerian cycle
        if (edges == 0) {
            return new ArrayDeque<>();
        }

        Deque<Edge> dfsStack = new ArrayDeque<>();
        dfsStack.push(new Edge(startVertexId, "*"));

        Deque<Edge> eulerCycle = new ArrayDeque<>();
        int[] index = new int[adjacent.length];

        while (!dfsStack.isEmpty()) {
            Edge edge = dfsStack.peek();
            int vertex = edge.neighborId;

            if (index[vertex] < adjacent[vertex].size()) {  // still has neighbor
                int indexValue = index[vertex];
                dfsStack.push(adjacent[vertex].get(indexValue));
                index[vertex]++;
            } else {
                eulerCycle.add(edge);
                dfsStack.pop();
            }
        }

        if (eulerCycle.size() == edges + 1) {
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
