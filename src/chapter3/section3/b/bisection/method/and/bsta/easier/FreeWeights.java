package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 23/03/22.
 */
public class FreeWeights {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int pairs = FastReader.nextInt();

        int[] row1 = new int[pairs];
        int[] row2 = new int[pairs];
        int maxWeight = 0;
        int maxWeightInSeparateRows = 0;
        Set<Integer> weightsInRow1 = new HashSet<>();
        Set<Integer> weightsOnSeparateRows = new HashSet<>();

        for (int i = 0; i < row1.length; i++) {
            int weight = FastReader.nextInt();
            row1[i] = weight;
            weightsInRow1.add(weight);
            maxWeight = Math.max(maxWeight, weight);
        }

        for (int i = 0; i < row2.length; i++) {
            int weight = FastReader.nextInt();
            row2[i] = weight;
            if (weightsInRow1.contains(weight)) {
                maxWeightInSeparateRows = Math.max(maxWeightInSeparateRows, weight);
                weightsOnSeparateRows.add(weight);
            }
            maxWeight = Math.max(maxWeight, weight);
        }

        int heaviestWeightToMove = getHeaviestWeightToMove(row1, row2, weightsOnSeparateRows,
                maxWeightInSeparateRows, maxWeight);
        outputWriter.printLine(heaviestWeightToMove);
        outputWriter.flush();
    }

    private static int getHeaviestWeightToMove(int[] row1, int[] row2, Set<Integer> weightsOnSeparateRows,
                                               int maxWeightInSeparateRows, int maxWeight) {
        int minimumWeightFound = maxWeight;
        int lowWeight = 0;
        int highWeight = maxWeight;

        while (lowWeight <= highWeight) {
            int weight = lowWeight + (highWeight - lowWeight) / 2;

            boolean areAllPairsTogetherRow1 = areAllPairsTogether(row1, weight, weightsOnSeparateRows);
            boolean areAllPairsTogetherRow2 = areAllPairsTogether(row2, weight, weightsOnSeparateRows);
            if (areAllPairsTogetherRow1 && areAllPairsTogetherRow2) {
                minimumWeightFound = weight;
                highWeight = weight - 1;
            } else {
                lowWeight = weight + 1;
            }
        }
        return Math.max(minimumWeightFound, maxWeightInSeparateRows);
    }

    private static boolean areAllPairsTogether(int[] row, int weightLimit, Set<Integer> weightsOnSeparateRows) {
        int currentWeight = -1;

        for (int weight : row) {
            if (weightsOnSeparateRows.contains(weight)) {
                continue;
            }
            if (weight > weightLimit) {
                if (currentWeight == -1) {
                    currentWeight = weight;
                } else if (currentWeight == weight) {
                    currentWeight = -1;
                } else {
                    return false;
                }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
