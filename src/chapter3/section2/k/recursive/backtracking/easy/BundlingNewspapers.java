package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/02/22.
 */
public class BundlingNewspapers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }

            String instructionLine = FastReader.getLine();
            List<String> newspapers = new ArrayList<>();

            String newspaper = FastReader.getLine();
            while (newspaper != null && !newspaper.isEmpty()) {
                newspapers.add(newspaper);
                newspaper = FastReader.getLine();
            }

            if (instructionLine.equals("*")) {
                printSubsetsInRange(newspapers, outputWriter, 1, newspapers.size());
            } else {
                String[] data = instructionLine.split(" ");
                int subsetSize1 = Integer.parseInt(data[0]);
                int subsetSize2 = subsetSize1;

                if (data.length == 2) {
                    subsetSize2 = Integer.parseInt(data[1]);
                }
                printSubsetsInRange(newspapers, outputWriter, subsetSize1, subsetSize2);
            }
        }
        outputWriter.flush();
    }

    private static void printSubsetsInRange(List<String> newspapers, OutputWriter outputWriter,
                                            int startRange, int endRange) {
        for (int subsetSize = startRange; subsetSize <= endRange; subsetSize++) {
            outputWriter.printLine(String.format("Size %d", subsetSize));

            int[] currentNewspapersIndexes = new int[subsetSize];
            printSubsets(newspapers, subsetSize, currentNewspapersIndexes, 0, 0, outputWriter);
            outputWriter.printLine();
        }
    }

    private static void printSubsets(List<String> newspapers, int subsetSize, int[] currentNewspapersIndexes,
                                     int index, int mask, OutputWriter outputWriter) {
        if (index == subsetSize) {
            printNewspapers(newspapers, currentNewspapersIndexes, outputWriter);
            return;
        }
        int previousNewspaperIndex = -1;
        if (index > 0) {
            previousNewspaperIndex = currentNewspapersIndexes[index - 1];
        }

        for (int i = 0; i < newspapers.size(); i++) {
            if ((mask & (1 << i)) == 0
                    && i > previousNewspaperIndex) {
                int newMask = mask | (1 << i);
                currentNewspapersIndexes[index] = i;
                printSubsets(newspapers, subsetSize, currentNewspapersIndexes, index + 1,
                        newMask, outputWriter);
            }
        }
    }

    private static void printNewspapers(List<String> currentNewspapers, int[] currentNewspapersIndexes,
                                        OutputWriter outputWriter) {
        outputWriter.print(currentNewspapers.get(currentNewspapersIndexes[0]));
        for (int i = 1; i < currentNewspapersIndexes.length; i++) {
            int newspaperIndex = currentNewspapersIndexes[i];
            outputWriter.print(", " + currentNewspapers.get(newspaperIndex));
        }
        outputWriter.printLine();
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
