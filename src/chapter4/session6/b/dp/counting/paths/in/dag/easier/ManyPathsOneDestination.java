package chapter4.session6.b.dp.counting.paths.in.dag.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/05/24.
 */
@SuppressWarnings("unchecked")
public class ManyPathsOneDestination {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        boolean isFirstTest = true;

        while (line != null) {
            int events = Integer.parseInt(line);
            Set<Integer> deathEvents = new HashSet<>();
            List<Integer>[] adjacencyList = new List[events];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int eventID = 0; eventID < events; eventID++) {
                int choices = FastReader.nextInt();
                for (int c = 0; c < choices; c++) {
                    int nextEventID = FastReader.nextInt();
                    adjacencyList[eventID].add(nextEventID);
                }

                if (choices == 0) {
                    deathEvents.add(eventID);
                }
            }

            int waysToLive = countWaysToLive(adjacencyList, deathEvents);

            if (isFirstTest) {
                isFirstTest = false;
            } else {
                outputWriter.printLine();
            }
            outputWriter.printLine(waysToLive);

            line = FastReader.getLine();
            if (line != null && line.isEmpty()) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int countWaysToLive(List<Integer>[] adjacencyList, Set<Integer> deathEvents) {
        int waysToLive = 0;
        int[] ways = new int[adjacencyList.length];
        ways[0] = 1;

        for (int eventID = 0; eventID < adjacencyList.length; eventID++) {
            for (int nextEventID : adjacencyList[eventID]) {
                ways[nextEventID] += ways[eventID];
            }
        }

        for (int deathEvent : deathEvents) {
            waysToLive += ways[deathEvent];
        }
        return waysToLive;
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
