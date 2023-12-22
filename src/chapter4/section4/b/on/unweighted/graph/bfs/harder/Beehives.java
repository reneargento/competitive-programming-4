package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/12/23.
 */
@SuppressWarnings("unchecked")
public class Beehives {

    private static final int INFINITE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int trees = FastReader.nextInt();
        int paths = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[trees];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < paths; i++) {
            int tree1 = FastReader.nextInt();
            int tree2 = FastReader.nextInt();
            adjacencyList[tree1].add(tree2);
            adjacencyList[tree2].add(tree1);
        }

        int numberOfBeehives = computeNumberOfBeehives(adjacencyList);
        if (numberOfBeehives != INFINITE) {
            outputWriter.printLine(numberOfBeehives);
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static int computeNumberOfBeehives(List<Integer>[] adjacencyList) {
        int numberOfBeehives = INFINITE;

        for (int sourceTree = 0; sourceTree < adjacencyList.length; sourceTree++) {
            int shortestCycle = computeShortestCycle(adjacencyList, sourceTree);
            numberOfBeehives = Math.min(numberOfBeehives, shortestCycle);
        }
        return numberOfBeehives;
    }

    private static int computeShortestCycle(List<Integer>[] adjacencyList, int sourceTree) {
        int shortestCycleLength = INFINITE;
        Queue<Integer> queue = new LinkedList<>();
        int[] distances = new int[adjacencyList.length];
        Arrays.fill(distances, -1);

        queue.offer(sourceTree);
        distances[sourceTree] = 0;

        while (!queue.isEmpty()) {
            int tree = queue.poll();

            for (int neighborTree : adjacencyList[tree]) {
                if (distances[neighborTree] == -1) {
                    distances[neighborTree] = distances[tree] + 1;
                    queue.offer(neighborTree);
                } else if (distances[neighborTree] >= distances[tree]) {
                    int cycleLength = distances[tree] + 1 + distances[neighborTree];
                    shortestCycleLength = Math.min(shortestCycleLength, cycleLength);
                }
            }
        }
        return shortestCycleLength;
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
