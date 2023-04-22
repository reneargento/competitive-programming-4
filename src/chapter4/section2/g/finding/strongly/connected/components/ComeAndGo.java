package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/04/23.
 */
@SuppressWarnings("unchecked")
public class ComeAndGo {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int intersections = FastReader.nextInt();
        int streets = FastReader.nextInt();

        while (intersections != 0 || streets != 0) {
            List<Integer>[] adjacencyList = new List[intersections];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < streets; i++) {
                int intersectionID1 = FastReader.nextInt() - 1;
                int intersectionID2 = FastReader.nextInt() - 1;
                int connectionType = FastReader.nextInt();

                adjacencyList[intersectionID1].add(intersectionID2);
                if (connectionType == 2) {
                    adjacencyList[intersectionID2].add(intersectionID1);
                }
            }

            int isStronglyConnected = isStronglyConnected(adjacencyList);
            outputWriter.printLine(isStronglyConnected);

            intersections = FastReader.nextInt();
            streets = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int isStronglyConnected(List<Integer>[] adjacencyList) {
        for (int intersectionID = 0; intersectionID < adjacencyList.length; intersectionID++) {
            boolean[] visited = new boolean[adjacencyList.length];
            int visitCount = depthFirstSearch(adjacencyList, visited, intersectionID);
            if (visitCount != adjacencyList.length) {
                return 0;
            }
        }
        return 1;
    }

    private static int depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int intersectionID) {
        int visitCount = 1;
        visited[intersectionID] = true;

        for (int neighborID : adjacencyList[intersectionID]) {
            if (!visited[neighborID]) {
                visitCount += depthFirstSearch(adjacencyList, visited, neighborID);
            }
        }
        return visitCount;
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
