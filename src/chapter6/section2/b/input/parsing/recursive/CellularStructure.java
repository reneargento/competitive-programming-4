package chapter6.section2.b.input.parsing.recursive;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/05/26.
 */
public class CellularStructure {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String chain = FastReader.getLine();
            String stage = computeStage(chain);
            outputWriter.printLine(stage);
        }
        outputWriter.flush();
    }

    private static String computeStage(String chain) {
        StringBuilder chainInverse = new StringBuilder(chain);
        chain = chainInverse.reverse().toString();

        if (chain.equals("A")) {
            return "SIMPLE";
        }
        char[] cells = chain.toCharArray();
        if (checkFullyGrown(cells)) {
            return "FULLY-GROWN";
        }
        if (checkMutagenic(cells)) {
            return "MUTAGENIC";
        }
        return "MUTANT";
    }

    private static boolean checkFullyGrown(char[] cells) {
        return checkFullyGrown(cells, 0, cells.length - 1) == cells.length;
    }

    private static int checkFullyGrown(char[] cells, int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            char cell = cells[i];
            if (cell == 'B') {
                if (i == endIndex
                        || cells[i + 1] != 'A') {
                    return -1;
                }
                i++;
            } else if (cell == 'A') {
                if (i == startIndex) {
                    return -1;
                }
                if (i == endIndex) {
                    return endIndex + 1;
                }
                return checkMutagenic(cells, i, endIndex, false);
            } else {
                return -1;
            }
        }
        return -1;
    }

    private static boolean checkMutagenic(char[] cells) {
        return checkMutagenic(cells, 0, cells.length - 1, false) == cells.length;
    }

    private static int checkMutagenic(char[] cells, int startIndex, int endIndex, boolean isInnerCall) {
        if (endIndex < startIndex) {
            return -1;
        }
        if (startIndex == endIndex) {
            if (cells[startIndex] == 'A') {
                return endIndex;
            } else {
                return -1;
            }
        }

        if (isInnerCall) {
            int fullyGrownResult = checkFullyGrown(cells, startIndex, endIndex);
            if (fullyGrownResult != -1) {
                return endIndex;
            }
        }

        if (cells[startIndex] == 'A' && cells[endIndex] == 'B') {
            int mutagenicResult = checkMutagenic(cells, startIndex + 1, endIndex - 1, true);
            if (mutagenicResult == -1) {
                return -1;
            } else {
                return endIndex + 1;
            }
        } else {
            return -1;
        }
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

        public void flush() {
            writer.flush();
        }
    }
}
