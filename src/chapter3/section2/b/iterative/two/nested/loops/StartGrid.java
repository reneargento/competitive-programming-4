package chapter3.section2.b.iterative.two.nested.loops;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 24/09/21.
 */
public class StartGrid {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String competitorsLine = FastReader.getLine();

        while (competitorsLine != null) {
            int competitors = Integer.parseInt(competitorsLine);
            int[] startPositions = new int[competitors];
            int[] endPositions = new int[competitors];

            for (int i = 0; i < startPositions.length; i++) {
                startPositions[i] = FastReader.nextInt();
            }
            for (int i = 0; i < endPositions.length; i++) {
                endPositions[i] = FastReader.nextInt();
            }

            int minimumOvertakes = computeMinimumOvertakes(startPositions, endPositions);
            outputWriter.printLine(minimumOvertakes);
            competitorsLine = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumOvertakes(int[] startPositions, int[] endPositions) {
        int minimumOvertakes = 0;
        Map<Integer, Integer> startPositionMap = createPositionsMap(startPositions);
        Map<Integer, Integer> endPositionMap = createPositionsMap(endPositions);

        for (int i = 0; i < endPositions.length; i++) {
            int competitor = endPositions[i];
            int competitorStart = startPositionMap.get(competitor);

            for (int j = 0; j < startPositions.length; j++) {
                int opponent = startPositions[j];
                int opponentEnd = endPositionMap.get(opponent);

                if (j < competitorStart && opponentEnd > i) {
                    minimumOvertakes++;
                }
            }
        }
        return minimumOvertakes;
    }

    private static Map<Integer, Integer> createPositionsMap(int[] positions) {
        Map<Integer, Integer> positionsMap = new HashMap<>();

        for (int i = 0; i < positions.length; i++) {
            positionsMap.put(positions[i], i);
        }
        return positionsMap;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
