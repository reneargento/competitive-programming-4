package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/12/23.
 */
@SuppressWarnings("unchecked")
public class LostInTranslation {

    private static class Edge {
        int vertex2;
        int cost;

        public Edge(int vertex2, int cost) {
            this.vertex2 = vertex2;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int languages = FastReader.nextInt();
        int translators = FastReader.nextInt();

        Map<String, Integer> languageNameToIDMap = new HashMap<>();
        List<Edge>[] adjacencyList = new List[languages + 1];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        getLanguageID(languageNameToIDMap, "English");
        for (int i = 0; i < languages; i++) {
            String languageName = FastReader.next();
            getLanguageID(languageNameToIDMap, languageName);
        }

        for (int i = 0; i < translators; i++) {
            String languageName1 = FastReader.next();
            String languageName2 = FastReader.next();
            int cost = FastReader.nextInt();

            int languageID1 = getLanguageID(languageNameToIDMap, languageName1);
            int languageID2 = getLanguageID(languageNameToIDMap, languageName2);

            adjacencyList[languageID1].add(new Edge(languageID2, cost));
            adjacencyList[languageID2].add(new Edge(languageID1, cost));
        }

        int cost = getTranslationCost(adjacencyList);
        if (cost == -1) {
            outputWriter.printLine("Impossible");
        } else {
            outputWriter.printLine(cost);
        }
        outputWriter.flush();
    }

    private static int getTranslationCost(List<Edge>[] adjacencyList) {
        int[] costs = new int[adjacencyList.length];
        int[] length = new int[adjacencyList.length];
        Arrays.fill(costs, Integer.MAX_VALUE);
        Arrays.fill(length, Integer.MAX_VALUE);

        boolean[] visited = new boolean[adjacencyList.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        costs[0] = 0;
        length[0] = 0;
        visited[0] = true;

        while (!queue.isEmpty()) {
            int languageID = queue.poll();

            for (Edge edge : adjacencyList[languageID]) {
                int neighborID = edge.vertex2;
                int pathLength = length[languageID] + 1;
                if (pathLength <= length[neighborID]) {
                    costs[neighborID] = Math.min(costs[neighborID], edge.cost);
                    length[neighborID] = pathLength;
                }

                if (!visited[neighborID]) {
                    queue.offer(neighborID);
                    visited[neighborID] = true;
                }
            }
        }

        int translationCost = 0;
        for (int cost : costs) {
            if (cost == Integer.MAX_VALUE) {
                return -1;
            }
            translationCost += cost;
        }
        return translationCost;
    }

    private static int getLanguageID(Map<String, Integer> languageNameToIDMap, String languageName) {
        if (!languageNameToIDMap.containsKey(languageName)) {
            languageNameToIDMap.put(languageName, languageNameToIDMap.size());
        }
        return languageNameToIDMap.get(languageName);
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
