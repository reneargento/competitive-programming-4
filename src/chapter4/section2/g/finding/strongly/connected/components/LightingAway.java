package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/04/23.
 */
@SuppressWarnings("unchecked")
public class LightingAway {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int caseID = 1;

        for (int t = 0; t < tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int connections = FastReader.nextInt();

            for (int i = 0; i < connections; i++) {
                int lightID1 = FastReader.nextInt() - 1;
                int lightID2 = FastReader.nextInt() - 1;
                adjacencyList[lightID1].add(lightID2);
            }

            LightComponents lightComponents = new LightComponents(adjacencyList);
            int minimumManualLights = lightComponents.components;
            outputWriter.printLine(String.format("Case %d: %d", caseID, minimumManualLights));
            caseID++;
        }
        outputWriter.flush();
    }

    private static class LightComponents {
        private int components;

        public LightComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];

            List<Integer> finishingTimes = computeFinishingTimes(adjacent);
            Collections.reverse(finishingTimes);

            for (int vertex : finishingTimes) {
                if (!visited[vertex]) {
                    depthFirstSearch(vertex, adjacent, null, visited, false);
                    components++;
                }
            }
        }

        private List<Integer> computeFinishingTimes(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            List<Integer> finishTimes = new Stack<>();

            for (int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    depthFirstSearch(i, adjacent, finishTimes, visited, true);
                }
            }
            return finishTimes;
        }

        private void depthFirstSearch(int sourceVertex, List<Integer>[] adjacent, List<Integer> finishTimes,
                                      boolean[] visited, boolean getFinishTimes) {
            visited[sourceVertex] = true;

            if (adjacent[sourceVertex] != null) {
                for (int neighbor : adjacent[sourceVertex]) {
                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adjacent, finishTimes, visited, getFinishTimes);
                    }
                }
            }

            if (getFinishTimes) {
                finishTimes.add(sourceVertex);
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
