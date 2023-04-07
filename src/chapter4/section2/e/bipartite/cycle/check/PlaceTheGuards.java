package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/04/23.
 */
@SuppressWarnings("unchecked")
public class PlaceTheGuards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            int streets = FastReader.nextInt();
            for (int e = 0; e < streets; e++) {
                int junctionID1 = FastReader.nextInt();
                int junctionID2 = FastReader.nextInt();
                adjacencyList[junctionID1].add(junctionID2);
                adjacencyList[junctionID2].add(junctionID1);
            }
            int minimumGuards = computeMinimumGuards(adjacencyList);
            outputWriter.printLine(minimumGuards);
        }
        outputWriter.flush();
    }

    private static int computeMinimumGuards(List<Integer>[] adjacencyList) {
        int[] colors = new int[adjacencyList.length];
        int minimumGuards = 0;
        int colorID = 1;

        for (int junctionID = 0; junctionID < colors.length; junctionID++) {
            if (colors[junctionID] == 0) {
                boolean possible = depthFirstSearch(adjacencyList, colors, junctionID, colorID, colorID + 1);
                if (!possible) {
                    return -1;
                }

                int color1Count = 0;
                int color2Count = 0;
                for (int ID = 0; ID < colors.length; ID++) {
                    if (colors[ID] == colorID) {
                        color1Count++;
                    } else if (colors[ID] == colorID + 1) {
                        color2Count++;
                    }
                }

                if (color2Count == 0) {
                    minimumGuards += color1Count;
                } else {
                    minimumGuards += Math.min(color1Count, color2Count);
                }
                colorID += 2;
            }
        }
        return minimumGuards;
    }

    private static boolean depthFirstSearch(List<Integer>[] adjacencyList, int[] colors, int junctionID,
                                            int colorValue, int otherColorValue) {
        boolean possible = true;
        colors[junctionID] = colorValue;
        for (int neighborID : adjacencyList[junctionID]) {
            if (colors[neighborID] == 0) {
                possible &= depthFirstSearch(adjacencyList, colors, neighborID, otherColorValue, colorValue);
            } else if (colors[neighborID] == colorValue) {
                return false;
            }
        }
        return possible;
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
