package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/08/22.
 */
public class ConstructingBST {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nodes = FastReader.nextInt();
        int maxHeight = FastReader.nextInt();
        int caseNumber = 1;

        while (nodes != 0 || maxHeight != 0) {
            double minHeight = minHeight(nodes);

            outputWriter.print(String.format("Case %d: ", caseNumber));
            if (minHeight > maxHeight) {
                outputWriter.printLine("Impossible.");
            } else {
                List<Integer> insertionOrder = new ArrayList<>();
                constructBST(1, nodes, maxHeight, 1, insertionOrder);

                outputWriter.print(insertionOrder.get(0));
                for (int i = 1; i < insertionOrder.size(); i++) {
                    outputWriter.print(" " + insertionOrder.get(i));
                }
                outputWriter.printLine();
            }

            caseNumber++;
            nodes = FastReader.nextInt();
            maxHeight = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void constructBST(int min, int max, int maxHeight, int currentLevel, List<Integer> insertionOrder) {
        if (min == max) {
            insertionOrder.add(min);
            return;
        }

        for (int value = min; value <= max; value++) {
            int leftSubtreeSize = value - min;
            int rightSubtreeSize = max - value;

            double leftSubtreeMinHeight = currentLevel + minHeight(leftSubtreeSize);
            double rightSubtreeMinHeight = currentLevel + minHeight(rightSubtreeSize);

            if (leftSubtreeMinHeight <= maxHeight && rightSubtreeMinHeight <= maxHeight) {
                insertionOrder.add(value);

                if (leftSubtreeSize >= 1) {
                    constructBST(min, value - 1, maxHeight, currentLevel + 1, insertionOrder);
                }
                if (rightSubtreeSize >= 1) {
                    constructBST(value + 1, max, maxHeight, currentLevel + 1, insertionOrder);
                }
                break;
            }
        }
    }

    private static double minHeight(int nodes) {
        return Math.floor(Math.log(nodes) / Math.log(2)) + 1;
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
