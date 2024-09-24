package chapter4.section6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 13/05/24.
 */
public class SavingTheUniverse {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String[] searchEngines = new String[FastReader.nextInt()];
            Map<String, Deque<Integer>> indexesMap = new HashMap<>();
            for (int i = 0; i < searchEngines.length; i++) {
                String searchEngine = FastReader.getLine();
                searchEngines[i] = searchEngine;
                indexesMap.put(searchEngine, new ArrayDeque<>());
            }

            int queries = FastReader.nextInt();
            for (int queryIndex = 0; queryIndex < queries; queryIndex++) {
                String query = FastReader.getLine();
                indexesMap.get(query).push(queryIndex);
            }

            int switches = computeSwitches(searchEngines, indexesMap);
            outputWriter.printLine(String.format("Case #%d: %d", t, switches));
        }
        outputWriter.flush();
    }

    private static int computeSwitches(String[] searchEngines, Map<String, Deque<Integer>> indexesMap) {
        for (String searchEngine : searchEngines) {
            if (indexesMap.get(searchEngine).isEmpty()) {
                return 0;
            }
        }
        int switches = 0;
        int maxIndex;

        do {
            maxIndex = getMaxIndex(indexesMap);
            switches++;
        } while (updateMap(indexesMap, maxIndex));

        return switches;
    }

    private static int getMaxIndex(Map<String, Deque<Integer>> indexesMap) {
        int maxIndex = 0;
        for (String searchEngine : indexesMap.keySet()) {
            if (!indexesMap.get(searchEngine).isEmpty()) {
                maxIndex = Math.max(maxIndex, indexesMap.get(searchEngine).peekLast());
            }
        }
        return maxIndex;
    }

    private static boolean updateMap(Map<String, Deque<Integer>> indexesMap, int currentIndex) {
        for (String searchEngine : indexesMap.keySet()) {
            Deque<Integer> indexes = indexesMap.get(searchEngine);

            while (!indexes.isEmpty() && indexes.peekLast() < currentIndex) {
                indexes.pollLast();
            }
            if (indexes.isEmpty()) {
                return false;
            }
        }
        return true;
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
