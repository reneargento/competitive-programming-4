package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/23.
 */
@SuppressWarnings("unchecked")
public class WheresMyInternet {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numberOfHouses = FastReader.nextInt();
        int networkCables = FastReader.nextInt();

        List<Integer>[] adjacencyList = new List[numberOfHouses];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < networkCables; i++) {
            int house1 = FastReader.nextInt() - 1;
            int house2 = FastReader.nextInt() - 1;
            adjacencyList[house1].add(house2);
            adjacencyList[house2].add(house1);
        }

        List<Integer> housesWithoutInternet = computeHousesWithoutInternet(adjacencyList);
        if (housesWithoutInternet.isEmpty()) {
            outputWriter.printLine("Connected");
        } else {
            for (int house : housesWithoutInternet) {
                outputWriter.printLine(house);
            }
        }
        outputWriter.flush();
    }

    private static List<Integer> computeHousesWithoutInternet(List<Integer>[] adjacencyList) {
        List<Integer> housesWithoutInternet = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        depthFirstSearch(adjacencyList, visited, 0);

        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (!visited[vertexID]) {
                housesWithoutInternet.add(vertexID + 1);
            }
        }
        return housesWithoutInternet;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, int houseID) {
        visited[houseID] = true;

        for (int neighborHouseID : adjacencyList[houseID]) {
            if (!visited[neighborHouseID]) {
                depthFirstSearch(adjacencyList, visited, neighborHouseID);
            }
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

        public void flush() {
            writer.flush();
        }
    }
}
