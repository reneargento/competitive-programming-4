package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/03/23.
 */
@SuppressWarnings("unchecked")
public class PickUpSticksKattis {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        int[] inDegrees = new int[adjacencyList.length];
        int connections = FastReader.nextInt();
        for (int i = 0; i < connections; i++) {
            int stickID1 = FastReader.nextInt() - 1;
            int stickID2 = FastReader.nextInt() - 1;
            adjacencyList[stickID1].add(stickID2);
            inDegrees[stickID2]++;
        }

        List<Integer> order = computeOrder(adjacencyList, inDegrees);
        if (order.size() == adjacencyList.length) {
            for (int stickID : order) {
                outputWriter.printLine(stickID);
            }
        } else {
            outputWriter.printLine("IMPOSSIBLE");
        }
        outputWriter.flush();
    }

    private static List<Integer> computeOrder(List<Integer>[] adjacencyList, int[] inDegrees) {
        List<Integer> order = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();
        for (int stickID = 0; stickID < adjacencyList.length; stickID++) {
            if (inDegrees[stickID] == 0) {
                queue.offer(stickID);
            }
        }

        while (!queue.isEmpty()) {
            int stickID = queue.poll();
            order.add(stickID + 1);

            for (int neighborID : adjacencyList[stickID]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    queue.offer(neighborID);
                }
            }
        }
        return order;
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
