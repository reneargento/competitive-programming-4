package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/05/22.
 */
public class DragonOfLoowater {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int headsNumber = FastReader.nextInt();
        int knightsNumber = FastReader.nextInt();

        while (headsNumber != 0 || knightsNumber != 0) {
            int[] headDiameters = new int[headsNumber];
            int[] knightHeights = new int[knightsNumber];

            for (int i = 0; i < headDiameters.length; i++) {
                headDiameters[i] = FastReader.nextInt();
            }
            for (int i = 0; i < knightHeights.length; i++) {
                knightHeights[i] = FastReader.nextInt();
            }

            long minimumGoldToSlayDragon = computeMinimumGoldToSlayDragon(headDiameters, knightHeights);
            if (minimumGoldToSlayDragon == -1) {
                outputWriter.printLine("Loowater is doomed!");
            } else {
                outputWriter.printLine(minimumGoldToSlayDragon);
            }
            headsNumber = FastReader.nextInt();
            knightsNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeMinimumGoldToSlayDragon(int[] headDiameters, int[] knightHeights) {
        long minimumGoldToSlayDragon = 0;
        Arrays.sort(headDiameters);
        Arrays.sort(knightHeights);

        int knightIndex = 0;
        for (int headDiameter : headDiameters) {
            while (knightIndex < knightHeights.length && knightHeights[knightIndex] < headDiameter) {
                knightIndex++;
            }
            if (knightIndex == knightHeights.length) {
                return -1;
            }
            minimumGoldToSlayDragon += knightHeights[knightIndex];
            knightIndex++;
        }
        return minimumGoldToSlayDragon;
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
