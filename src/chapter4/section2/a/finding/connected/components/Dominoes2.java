package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/03/23.
 */
@SuppressWarnings("unchecked")
public class Dominoes2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dominoes = FastReader.nextInt();
            int edges = FastReader.nextInt();
            int[] knockedDominoes = new int[FastReader.nextInt()];

            List<Integer>[] adjacencyList = new List[dominoes];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < edges; i++) {
                int domino1 = FastReader.nextInt() - 1;
                int domino2 = FastReader.nextInt() - 1;
                adjacencyList[domino1].add(domino2);
            }

            for (int i = 0; i < knockedDominoes.length; i++) {
                knockedDominoes[i] = FastReader.nextInt() - 1;
            }

            int fallenDominoes = countFallenDominoes(adjacencyList, knockedDominoes);
            outputWriter.printLine(fallenDominoes);
        }
        outputWriter.flush();
    }

    private static int countFallenDominoes(List<Integer>[] adjacencyList, int[] knockedDominoes) {
        int fallenDominoes = 0;
        boolean[] visited = new boolean[adjacencyList.length];

        for (int knockedDomino : knockedDominoes) {
            fallenDominoes += depthFirstSearch(adjacencyList, visited, knockedDomino);
        }
        return fallenDominoes;
    }

    private static int depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int vertex) {
        if (visited[vertex]) {
            return 0;
        }
        visited[vertex] = true;
        int fallenDominoes = 1;

        for (int neighbor : adjacencyList[vertex]) {
            fallenDominoes += depthFirstSearch(adjacencyList, visited, neighbor);
        }
        return fallenDominoes;
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
