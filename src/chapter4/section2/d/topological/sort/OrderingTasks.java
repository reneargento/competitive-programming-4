package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/03/23.
 */
@SuppressWarnings("unchecked")
public class OrderingTasks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tasks = FastReader.nextInt();
        int relations = FastReader.nextInt();

        while (tasks != 0 || relations != 0) {
            List<Integer>[] adjacencyList = new List[tasks];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int[] inDegrees = new int[adjacencyList.length];

            for (int i = 0; i < relations; i++) {
                int taskID1 = FastReader.nextInt() - 1;
                int taskID2 = FastReader.nextInt() - 1;
                adjacencyList[taskID1].add(taskID2);
                inDegrees[taskID2]++;
            }

            List<Integer> topologicalOrder = computeTopologicalOrder(adjacencyList, inDegrees);
            outputWriter.print(topologicalOrder.get(0));
            for (int i = 1; i < topologicalOrder.size(); i++) {
                outputWriter.print(" " + topologicalOrder.get(i));
            }
            outputWriter.printLine();
            tasks = FastReader.nextInt();
            relations = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<Integer> computeTopologicalOrder(List<Integer>[] adjacencyList, int[] inDegrees) {
        List<Integer> topologicalOrder = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<>();
        for (int taskID = 0; taskID < inDegrees.length; taskID++) {
            if (inDegrees[taskID] == 0) {
                queue.offer(taskID);
            }
        }

        while (!queue.isEmpty()) {
            int taskID = queue.poll();
            topologicalOrder.add(taskID + 1);

            for (int neighborID : adjacencyList[taskID]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    queue.offer(neighborID);
                }
            }
        }
        return topologicalOrder;
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
