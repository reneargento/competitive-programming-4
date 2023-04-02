package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/03/23.
 */
@SuppressWarnings("unchecked")
public class MontescoVsCapuleto {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int people = FastReader.nextInt();
            List<Integer>[] adjacencyList = new List[people];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int personID = 0; personID < people; personID++) {
                int enemiesNumber = FastReader.nextInt();
                for (int e = 0; e < enemiesNumber; e++) {
                    int enemyID = FastReader.nextInt() - 1;
                    if (enemyID >= people) {
                        continue;
                    }
                    adjacencyList[personID].add(enemyID);
                    adjacencyList[enemyID].add(personID);
                }
            }
            int maximumAttendees = computeMaximumAttendees(adjacencyList);
            outputWriter.printLine(maximumAttendees);
        }
        outputWriter.flush();
    }

    private static int computeMaximumAttendees(List<Integer>[] adjacencyList) {
        int maximumAttendees = 0;
        int[] color = new int[adjacencyList.length];
        int colorValue = 1;

        for (int personID = 0; personID < adjacencyList.length; personID++) {
            if (color[personID] == 0) {
                boolean isBipartite = computeBipartition(adjacencyList, color, personID, colorValue, colorValue + 1);

                if (isBipartite) {
                    int partitionSize1 = 0;
                    int partitionSize2 = 0;
                    for (int personColor : color) {
                        if (personColor == colorValue) {
                            partitionSize1++;
                        } else if (personColor == colorValue + 1) {
                            partitionSize2++;
                        }
                    }
                    maximumAttendees += Math.max(partitionSize1, partitionSize2);
                }
                colorValue += 2;
            }
        }
        return maximumAttendees;
    }

    private static boolean computeBipartition(List<Integer>[] adjacencyList, int[] color, int personID,
                                              int colorValue, int otherColorValue) {
        boolean isBipartite = true;
        color[personID] = colorValue;

        for (int friendID : adjacencyList[personID]) {
            if (color[friendID] == 0) {
                isBipartite &= computeBipartition(adjacencyList, color, friendID, otherColorValue, colorValue);
            } else if (color[friendID] == colorValue) {
                isBipartite = false;
            }
        }
        return isBipartite;
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
