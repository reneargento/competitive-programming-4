package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/23.
 */
@SuppressWarnings("unchecked")
public class ThePartyPartI {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            int persons = FastReader.nextInt();
            int dancingCouples = FastReader.nextInt();

            List<Integer>[] adjacencyList = new List[persons];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < dancingCouples; i++) {
                int personID1 = FastReader.nextInt();
                int personID2 = FastReader.nextInt();
                adjacencyList[personID1].add(personID2);
                adjacencyList[personID2].add(personID1);
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            int[] giovanniNumbers = computeGiovanniNumbers(adjacencyList);
            for (int i = 1; i < giovanniNumbers.length; i++) {
                outputWriter.printLine(giovanniNumbers[i]);
            }
        }
        outputWriter.flush();
    }

    private static int[] computeGiovanniNumbers(List<Integer>[] adjacencyList) {
        int[] giovanniNumbers = new int[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int personID = queue.poll();

            for (int dancingPartner : adjacencyList[personID]) {
                if (!visited[dancingPartner]) {
                    visited[dancingPartner] = true;
                    giovanniNumbers[dancingPartner] = giovanniNumbers[personID] + 1;
                    queue.offer(dancingPartner);
                }
            }
        }
        return giovanniNumbers;
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
