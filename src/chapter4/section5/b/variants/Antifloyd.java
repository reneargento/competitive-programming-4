package chapter4.section5.b.variants;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/03/24.
 */
public class Antifloyd {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int computers = FastReader.nextInt();
            int[][] latency = new int[computers][computers];
            for (int computerID = 0; computerID < latency.length; computerID++) {
                Arrays.fill(latency[computerID], INFINITE);
            }

            for (int computerID1 = 1; computerID1 < latency.length; computerID1++) {
                for (int computerID2 = 0; computerID2 < computerID1; computerID2++) {
                    int latencyValue = FastReader.nextInt();
                    latency[computerID2][computerID1] = latencyValue;
                    latency[computerID1][computerID2] = latencyValue;
                }
            }

            boolean[][] notUniqueCables = computeCablesData(latency);
            outputWriter.printLine(String.format("Case #%d:", t));
            if (notUniqueCables == null) {
                outputWriter.printLine("Need better measurements.");
            } else {
                int totalCables = countCables(notUniqueCables);
                outputWriter.printLine(totalCables);

                for (int computerID1 = 0; computerID1 < latency.length; computerID1++) {
                    for (int computerID2 = computerID1 + 1; computerID2 < latency.length; computerID2++) {
                        if (!notUniqueCables[computerID1][computerID2]) {
                            outputWriter.printLine(String.format("%d %d %d", computerID1 + 1, computerID2 + 1,
                                    latency[computerID1][computerID2]));
                        }
                    }
                }
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static boolean[][] computeCablesData(int[][] latency) {
        boolean[][] notUniqueCables = new boolean[latency.length][latency.length];

        for (int computerID1 = 0; computerID1 < latency.length; computerID1++) {
            for (int computerID2 = 0; computerID2 < latency.length; computerID2++) {
                for (int computerID3 = 0; computerID3 < latency.length; computerID3++) {
                    if (latency[computerID2][computerID3] == INFINITE
                            || computerID1 == computerID2 || computerID2 == computerID3 || computerID1 == computerID3) {
                        continue;
                    }
                    int alternativePath = latency[computerID2][computerID1] + latency[computerID1][computerID3];

                    if (alternativePath < latency[computerID2][computerID3]) {
                        return null;
                    }
                    if (alternativePath == latency[computerID2][computerID3]) {
                        notUniqueCables[computerID2][computerID3] = true;
                    }
                }
            }
        }
        return notUniqueCables;
    }

    private static int countCables(boolean[][] notUniqueCables) {
        int cables = 0;
        for (int computerID1 = 0; computerID1 < notUniqueCables.length; computerID1++) {
            for (int computerID2 = 0; computerID2 < notUniqueCables.length; computerID2++) {
                if (computerID1 < computerID2 && !notUniqueCables[computerID1][computerID2]) {
                    cables++;
                }
            }
        }
        return cables;
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
