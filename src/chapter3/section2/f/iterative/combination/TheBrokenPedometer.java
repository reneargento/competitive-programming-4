package chapter3.section2.f.iterative.combination;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/12/21.
 */
public class TheBrokenPedometer {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int leds = FastReader.nextInt();
            int symbols = FastReader.nextInt();

            int[][] codification = new int[symbols][leds];

            for (int i = 0; i < codification.length; i++) {
                String[] codeLine = FastReader.getLine().split(" ");
                for (int c = 0; c < codeLine.length; c++) {
                    int code = Integer.parseInt(codeLine[c]);
                    codification[i][c] = code;
                }
            }

            int[] activeLeds = new int[codification[0].length];
            int minimumActiveLeds = computeMinimumActiveLeds(codification, activeLeds, 0, 0);
            outputWriter.printLine(minimumActiveLeds);
        }
        outputWriter.flush();
    }

    private static int computeMinimumActiveLeds(int[][] codification, int[] activeLeds, int index,
                                                int activeLedsCount) {
        if (index == activeLeds.length) {
            return canIdentifyAll(codification, activeLeds, activeLedsCount);
        }

        // Not active
        if (activeLeds[index] == 1) {
            activeLedsCount--;
        }
        activeLeds[index] = 0;
        int minimumActiveLeds1 = computeMinimumActiveLeds(codification, activeLeds, index + 1, activeLedsCount);

        // Active
        if (activeLeds[index] == 0) {
            activeLedsCount++;
        }
        activeLeds[index] = 1;
        int minimumActiveLeds2 = computeMinimumActiveLeds(codification, activeLeds, index + 1, activeLedsCount);
        activeLeds[index] = 0;

        return Math.min(minimumActiveLeds1, minimumActiveLeds2);
    }

    private static int canIdentifyAll(int[][] codification, int[] activeLeds, int activeLedsCount) {
        for (int i = 0; i < codification.length; i++) {
            int[] symbol1 = codification[i];

            for (int j = i + 1; j < codification.length; j++) {
                int[] symbol2 = codification[j];
                if (!canDifferentiateSymbol(symbol1, symbol2, activeLeds)) {
                    return Integer.MAX_VALUE;
                }
            }
        }
        return activeLedsCount;
    }

    private static boolean canDifferentiateSymbol(int[] symbol1, int[] symbol2, int[] activeLeds) {
        for (int i = 0; i < activeLeds.length; i++) {
            if (activeLeds[i] == 1 && symbol1[i] != symbol2[i]) {
                return true;
            }
        }
        return false;
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
