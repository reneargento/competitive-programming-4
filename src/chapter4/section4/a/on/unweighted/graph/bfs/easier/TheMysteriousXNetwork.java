package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/12/23.
 */
@SuppressWarnings("unchecked")
public class TheMysteriousXNetwork {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            int camarades = FastReader.nextInt();

            List<Integer>[] adjacencyList = new List[camarades];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < camarades; i++) {
                int camaradeID = FastReader.nextInt();
                int acquaintances = FastReader.nextInt();

                for (int a = 0; a < acquaintances; a++) {
                    int acquaintanceID = FastReader.nextInt();
                    adjacencyList[camaradeID].add(acquaintanceID);
                    adjacencyList[acquaintanceID].add(camaradeID);
                }
            }

            int sourceCamaradeID = FastReader.nextInt();
            int targetCamaradeID = FastReader.nextInt();
            int minimumIntermediates = countMinimumIntermediates(adjacencyList, sourceCamaradeID, targetCamaradeID);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("%d %d %d", sourceCamaradeID, targetCamaradeID, minimumIntermediates));
        }
        outputWriter.flush();
    }

    private static int countMinimumIntermediates(List<Integer>[] adjacencyList, int sourceCamaradeID,
                                                 int targetCamaradeID) {
        boolean[] visited = new boolean[adjacencyList.length];
        int[] intermediatesNumber = new int[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(sourceCamaradeID);
        visited[sourceCamaradeID] = true;

        while (!queue.isEmpty()) {
            int camaradeID = queue.poll();
            if (camaradeID == targetCamaradeID) {
                break;
            }

            for (int acquaintanceID : adjacencyList[camaradeID]) {
                if (!visited[acquaintanceID]) {
                    intermediatesNumber[acquaintanceID] = intermediatesNumber[camaradeID] + 1;
                    visited[acquaintanceID] = true;
                    queue.offer(acquaintanceID);
                }
            }
        }
        return intermediatesNumber[targetCamaradeID] - 1;
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
