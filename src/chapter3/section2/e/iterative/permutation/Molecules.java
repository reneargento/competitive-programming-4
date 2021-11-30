package chapter3.section2.e.iterative.permutation;

import java.io.*;

/**
 * Created by Rene Argento on 30/11/21.
 */
public class Molecules {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String molecularChain1 = FastReader.getLine();

        while (molecularChain1.charAt(0) != 'Q') {
            String[] molecularChains = new String[4];
            molecularChains[0] = molecularChain1;

            for (int i = 1; i < molecularChains.length; i++) {
                molecularChains[i] = FastReader.getLine();
            }

            int maxArea = computeMaxArea(molecularChains);
            outputWriter.printLine(maxArea);
            molecularChain1 = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMaxArea(String[] molecularChains) {
        int[] order = new int[molecularChains.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        int[] currentOrder = new int[order.length];
        return computeArea(molecularChains, order, currentOrder, 0, 0);
    }

    private static int computeArea(String[] molecularChains, int[] order, int[] currentOrder, int index,
                                   int mask) {
        if (mask == (1 << order.length) - 1) {
            return computeArea(molecularChains, currentOrder);
        }

        int maxArea = 0;
        for (int i = 0; i < order.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentOrder[index] = order[i];
                int area = computeArea(molecularChains, order, currentOrder, index + 1, newMask);
                maxArea = Math.max(maxArea, area);
            }
        }
        return maxArea;
    }

    private static int computeArea(String[] molecularChains, int[] order) {
        String horizontalChain1 = molecularChains[order[0]];
        String horizontalChain2 = molecularChains[order[1]];
        String verticalChain1 = molecularChains[order[2]];
        String verticalChain2 = molecularChains[order[3]];

        int maxArea = 0;
        int horizontalDistance;
        int verticalDistance;
        int horizontalIndex1;
        int horizontalIndex2;
        int verticalIndex1;
        int verticalIndex2;

        for (horizontalDistance = 2; horizontalDistance <= 9; horizontalDistance++) {
            for (verticalDistance = 2; verticalDistance <= 9; verticalDistance++) {
                for (horizontalIndex1 = 1; horizontalIndex1 + horizontalDistance <= 10; horizontalIndex1++) {
                    for (verticalIndex1 = 1; verticalIndex1 + verticalDistance <= 10; verticalIndex1++) {
                        if (horizontalChain1.charAt(horizontalIndex1) != verticalChain1.charAt(verticalIndex1)) {
                            continue;
                        }

                        for (horizontalIndex2 = 1; horizontalIndex2 + horizontalDistance <= 10; horizontalIndex2++) {
                            if (horizontalChain2.charAt(horizontalIndex2) != verticalChain1.charAt(verticalIndex1 + verticalDistance)) {
                                continue;
                            }
                            for (verticalIndex2 = 1; verticalIndex2 + verticalDistance <= 10; verticalIndex2++) {
                                if (horizontalChain1.charAt(horizontalIndex1 + horizontalDistance) != verticalChain2.charAt(verticalIndex2)
                                        || horizontalChain2.charAt(horizontalIndex2 + horizontalDistance) != verticalChain2.charAt(verticalIndex2 + verticalDistance)) {
                                    continue;
                                }
                                int area = (horizontalDistance - 1) * (verticalDistance - 1);
                                maxArea = Math.max(maxArea, area);
                            }
                        }
                    }
                }
            }
        }
        return maxArea;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
