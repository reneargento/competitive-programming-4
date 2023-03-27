package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/03/23.
 */
@SuppressWarnings("unchecked")
public class Collapse {

    private static class IslandTransaction {
        int otherIsland;
        int units;

        public IslandTransaction(int otherIsland, int units) {
            this.otherIsland = otherIsland;
            this.units = units;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int islands = FastReader.nextInt();
        int[] goodsRequired = new int[islands];
        int[] goodsReceived = new int[islands];

        List<IslandTransaction>[] adjacencyList = new List[islands];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int islandID = 0; islandID < islands; islandID++) {
            goodsRequired[islandID] = FastReader.nextInt();
            int relations = FastReader.nextInt();

            for (int i = 0; i < relations; i++) {
                int neighborIslandID = FastReader.nextInt() - 1;
                int units = FastReader.nextInt();
                adjacencyList[neighborIslandID].add(new IslandTransaction(islandID, units));
                goodsReceived[islandID] += units;
            }
        }

        int islandsSurviving = computeIslandsSurviving(adjacencyList, goodsRequired, goodsReceived);
        outputWriter.printLine(islandsSurviving);
        outputWriter.flush();
    }

    private static int computeIslandsSurviving(List<IslandTransaction>[] adjacencyList, int[] goodsRequired,
                                               int[] goodsReceived) {
        int islandsSurviving = adjacencyList.length - 1;
        boolean[] visited = new boolean[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int islandID = queue.poll();

            for (IslandTransaction transaction : adjacencyList[islandID]) {
                goodsReceived[transaction.otherIsland] -= transaction.units;

                if (goodsReceived[transaction.otherIsland] < goodsRequired[transaction.otherIsland]
                        && !visited[transaction.otherIsland]) {
                    visited[transaction.otherIsland] = true;
                    queue.offer(transaction.otherIsland);
                    islandsSurviving--;
                }
            }
        }
        return islandsSurviving;
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
