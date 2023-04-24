package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/04/23.
 */
@SuppressWarnings("unchecked")
public class Dominos {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int relations = FastReader.nextInt();
            for (int i = 0; i < relations; i++) {
                int dominoID1 = FastReader.nextInt() - 1;
                int dominoID2 = FastReader.nextInt() - 1;
                adjacencyList[dominoID1].add(dominoID2);
            }

            int dominoesToKnock = computeDominoesToKnock(adjacencyList);
            outputWriter.printLine(dominoesToKnock);
        }
        outputWriter.flush();
    }

    private static int computeDominoesToKnock(List<Integer>[] adjacencyList) {
        int dominoesToKnock = 0;
        boolean[] visited = new boolean[adjacencyList.length];

        List<Integer> finishingTimes = computeFinishingTimes(adjacencyList);

        for (int dominoID : finishingTimes) {
            if (!visited[dominoID]) {
                dominoesToKnock++;
                depthFirstSearch(adjacencyList, visited, finishingTimes, false, dominoID);
            }
        }
        return dominoesToKnock;
    }

    private static List<Integer> computeFinishingTimes(List<Integer>[] adjacencyList) {
        List<Integer> finishingTimes = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];

        for (int dominoID = 0; dominoID < adjacencyList.length; dominoID++) {
            if (!visited[dominoID]) {
                depthFirstSearch(adjacencyList, visited, finishingTimes, true, dominoID);
            }
        }
        Collections.reverse(finishingTimes);
        return finishingTimes;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, List<Integer> finishTimes,
                                         boolean computeFinishTime, int sourceVertex) {
        visited[sourceVertex] = true;

        if (adjacencyList[sourceVertex] != null) {
            for (int neighbor : adjacencyList[sourceVertex]) {
                if (!visited[neighbor]) {
                    depthFirstSearch(adjacencyList, visited, finishTimes, computeFinishTime, neighbor);
                }
            }
        }

        if (computeFinishTime) {
            finishTimes.add(sourceVertex);
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
