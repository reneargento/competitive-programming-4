package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/04/23.
 */
@SuppressWarnings("unchecked")
public class AmandaLounges {

    private static class Color {
        int timesUsed;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int minimumLounges = 0;
        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        boolean[] hasLounge = new boolean[adjacencyList.length];
        boolean[] shouldNotHaveLounge = new boolean[adjacencyList.length];
        boolean[] isInGraph = new boolean[adjacencyList.length];

        int routes = FastReader.nextInt();
        for (int i = 0; i < routes; i++) {
            int airportID1 = FastReader.nextInt() - 1;
            int airportID2 = FastReader.nextInt() - 1;
            int lounges = FastReader.nextInt();
            if (lounges == 0) {
                shouldNotHaveLounge[airportID1] = true;
                shouldNotHaveLounge[airportID2] = true;
            } else if (lounges == 2) {
                if (!hasLounge[airportID1]) {
                    minimumLounges++;
                    hasLounge[airportID1] = true;
                }
                if (!hasLounge[airportID2]) {
                    minimumLounges++;
                    hasLounge[airportID2] = true;
                }
            } else {
                adjacencyList[airportID1].add(airportID2);
                adjacencyList[airportID2].add(airportID1);
                isInGraph[airportID1] = true;
                isInGraph[airportID2] = true;
            }
        }

        int placedLounges = countMinimumLounges(adjacencyList, hasLounge, shouldNotHaveLounge, isInGraph);
        if (placedLounges == -1) {
            outputWriter.printLine("impossible");
        } else {
            outputWriter.printLine(minimumLounges + placedLounges);
        }
        outputWriter.flush();
    }

    private static int countMinimumLounges(List<Integer>[] adjacencyList, boolean[] hasLounge,
                                           boolean[] shouldNotHaveLounge, boolean[] isInGraph) {
        if (isInvalidState(hasLounge, shouldNotHaveLounge)) {
            return -1;
        }
        int minimumLounges = 0;
        boolean[] visited = new boolean[adjacencyList.length];

        // Place lounges based on partially set airports
        for (int airportID = 0; airportID < hasLounge.length; airportID++) {
            if (isInGraph[airportID] && !visited[airportID]
                    && (hasLounge[airportID] || shouldNotHaveLounge[airportID])) {
                Color color1 = new Color();
                Color color2 = new Color();
                boolean placeLounge = hasLounge[airportID];
                boolean canPlaceLounges = placeLounges(adjacencyList, visited, hasLounge, shouldNotHaveLounge, airportID,
                        placeLounge, color1, color2);
                if (!canPlaceLounges) {
                    return -1;
                }
                minimumLounges += color1.timesUsed;
            }
        }

        // Place remaining lounges
        for (int airportID = 0; airportID < hasLounge.length; airportID++) {
            if (isInGraph[airportID] && !visited[airportID]) {
                Color color1 = new Color();
                Color color2 = new Color();
                color1.timesUsed++;
                boolean canPlaceLounges = placeLounges(adjacencyList, visited, hasLounge, shouldNotHaveLounge, airportID,
                        true, color1, color2);
                if (!canPlaceLounges) {
                    return -1;
                }
                if (color2.timesUsed == 0) {
                    minimumLounges += color1.timesUsed;
                } else {
                    minimumLounges += Math.min(color1.timesUsed, color2.timesUsed);
                }
            }
        }
        return minimumLounges;
    }

    private static boolean isInvalidState(boolean[] hasLounge, boolean[] shouldNotHaveLounge) {
        for (int airportID = 0; airportID < hasLounge.length; airportID++) {
            if (hasLounge[airportID] && shouldNotHaveLounge[airportID]) {
                return true;
            }
        }
        return false;
    }

    private static boolean placeLounges(List<Integer>[] adjacencyList, boolean[] visited, boolean[] hasLounge,
                                    boolean[] shouldNotHaveLounge, int airportID, boolean placeLounge,
                                    Color color1, Color color2) {
        visited[airportID] = true;
        if (hasLounge[airportID] && !placeLounge) {
            return false;
        }
        if (shouldNotHaveLounge[airportID] && placeLounge) {
            return false;
        }

        if (placeLounge && !hasLounge[airportID]) {
            hasLounge[airportID] = true;
        }

        for (int neighborID : adjacencyList[airportID]) {
            if (!visited[neighborID]) {
                if (placeLounge) {
                    color2.timesUsed++;
                } else if (!hasLounge[neighborID]) {
                    color1.timesUsed++;
                }

                boolean canPlaceLounges = placeLounges(adjacencyList, visited, hasLounge, shouldNotHaveLounge,
                        neighborID, !placeLounge, color1, color2);
                if (!canPlaceLounges) {
                    return false;
                }
            } else if (hasLounge[airportID] == hasLounge[neighborID]) {
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
