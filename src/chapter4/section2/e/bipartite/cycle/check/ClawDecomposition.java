package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/23.
 */
@SuppressWarnings("unchecked")
public class ClawDecomposition {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int vertices = FastReader.nextInt();

        while (vertices != 0) {
            List<Integer>[] adjacencyList = new List[vertices];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int vertexID1 = FastReader.nextInt();
            int vertexID2 = FastReader.nextInt();

            while (vertexID1 != 0 || vertexID2 != 0) {
                adjacencyList[vertexID1 - 1].add(vertexID2 - 1);
                adjacencyList[vertexID2 - 1].add(vertexID1 - 1);

                vertexID1 = FastReader.nextInt();
                vertexID2 = FastReader.nextInt();
            }
            boolean canDecomposeToClaws = canDecomposeToClaws(adjacencyList);
            outputWriter.printLine(canDecomposeToClaws ? "YES" : "NO");
            vertices = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static boolean canDecomposeToClaws(List<Integer>[] adjacencyList) {
        Boolean[] colors = new Boolean[adjacencyList.length];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (colors[vertexID] == null) {
                boolean isPossible = colorGraph(adjacencyList, colors, vertexID, true);
                if (!isPossible) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean colorGraph(List<Integer>[] adjacencyList, Boolean[] colors, int vertexID, boolean color) {
        boolean isPossible = true;
        colors[vertexID] = color;
        for (int neighborID : adjacencyList[vertexID]) {
            if (colors[neighborID] == null) {
                isPossible &= colorGraph(adjacencyList, colors, neighborID, !color);
            } else if (colors[neighborID] == color) {
                return false;
            }
        }
        return isPossible;
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
