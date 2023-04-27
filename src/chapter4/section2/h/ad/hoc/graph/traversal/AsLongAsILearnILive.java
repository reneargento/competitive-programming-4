package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/04/23.
 */
@SuppressWarnings("unchecked")
public class AsLongAsILearnILive {

    private static class Result {
        int maximumLearningUnits;
        int endNodeID;

        public Result(int maximumLearningUnits, int endNodeID) {
            this.maximumLearningUnits = maximumLearningUnits;
            this.endNodeID = endNodeID;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nodes = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[nodes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int edges = FastReader.nextInt();

            int[] learningUnits = new int[nodes];
            for (int i = 0; i < learningUnits.length; i++) {
                learningUnits[i] = FastReader.nextInt();
            }
            for (int i = 0; i < edges; i++) {
                int nodeID1 = FastReader.nextInt();
                int nodeID2 = FastReader.nextInt();
                adjacencyList[nodeID1].add(nodeID2);
            }

            Result result = computeMaximumLearningUnits(adjacencyList, learningUnits);
            outputWriter.printLine(String.format("Case %d: %d %d", t, result.maximumLearningUnits, result.endNodeID));
        }
        outputWriter.flush();
    }

    private static Result computeMaximumLearningUnits(List<Integer>[] adjacencyList, int[] learningUnits) {
        int maximumLearningUnits = 0;
        int endNodeID = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int nodeID = queue.poll();
            maximumLearningUnits += learningUnits[nodeID];

            if (adjacencyList[nodeID].isEmpty()) {
                endNodeID = nodeID;
                break;
            }

            int maxLearningFromNeighbors = 0;
            int bestNeighborID = 0;
            for (int neighborID : adjacencyList[nodeID]) {
                if (learningUnits[neighborID] > maxLearningFromNeighbors) {
                    maxLearningFromNeighbors = learningUnits[neighborID];
                    bestNeighborID = neighborID;
                }
            }

            queue.offer(bestNeighborID);
        }
        return new Result(maximumLearningUnits, endNodeID);
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
