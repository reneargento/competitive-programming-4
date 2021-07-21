package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/07/21.
 */
@SuppressWarnings("unchecked")
public class ChoppingWood {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int listLength = FastReader.nextInt();
        int[] connectingVertices = new int[listLength];

        for (int i = 0; i < listLength; i++) {
            connectingVertices[i] = FastReader.nextInt();
        }

        int maxVertex = listLength + 1;
        if (connectingVertices[listLength - 1] != maxVertex) {
            outputWriter.printLine("Error");
        } else {
            List<Integer>[] lastAppearances = computeLastAppearances(connectingVertices);
            computeLeaves(lastAppearances, outputWriter);
        }
        outputWriter.flush();
    }

    private static List<Integer>[] computeLastAppearances(int[] connectingVertices) {
        Set<Integer> computedVertex = new HashSet<>();
        List<Integer>[] lastAppearances = new List[connectingVertices.length + 1];

        for (int i = connectingVertices.length - 1; i >= 0; i--) {
            lastAppearances[i + 1] = new ArrayList<>();

            int vertex = connectingVertices[i];
            if (!computedVertex.contains(vertex)) {
                lastAppearances[i + 1].add(vertex);
                computedVertex.add(vertex);
            }
        }

        lastAppearances[0] = new ArrayList<>();
        for (int vertex = 1; vertex <= connectingVertices.length + 1; vertex++) {
            if (!computedVertex.contains(vertex)) {
                lastAppearances[0].add(vertex);
            }
        }
        return lastAppearances;
    }

    private static void computeLeaves(List<Integer>[] lastAppearances, OutputWriter outputWriter) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        for (int step = 0; step < lastAppearances.length - 1; step++) {
            for (int vertex : lastAppearances[step]) {
                priorityQueue.offer(vertex);
            }
            int leaf = priorityQueue.poll();
            outputWriter.printLine(leaf);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
