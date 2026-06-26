package chapter6.section2.d.output.formatting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/06/2026.
 */
public class NotThatKindOfGraph {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String prices = FastReader.getLine();
            outputWriter.printLine(String.format("Case #%d:", t));
            printGraph(prices, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printGraph(String prices, OutputWriter outputWriter) {
        char[][] graph = buildGraph(prices);
        for (int row = 0; row < graph.length; row++) {
            int lastNonBlankCharIndex = getLastNonBlankCharIndex(graph, row);
            if (lastNonBlankCharIndex == -1) {
                continue;
            }
            outputWriter.print("| ");
            for (int column = 0; column <= lastNonBlankCharIndex; column++) {
                outputWriter.print(graph[row][column]);
            }
            outputWriter.printLine();
        }

        outputWriter.print("+");
        for (int row = 0; row <= prices.length() + 1; row++) {
            outputWriter.print("-");
        }
        outputWriter.printLine();
        outputWriter.printLine();
    }

    private static char[][] buildGraph(String prices) {
        int maxOffsetPositive = 0;
        int minOffsetNegative = 0;
        int offset = 0;

        for (int i = 0; i < prices.length(); i++) {
            char price =  prices.charAt(i);
            if (i > 0) {
                char previousPrice = prices.charAt(i - 1);
                if (previousPrice == 'R' && price != 'F') {
                    offset++;
                    if (offset > maxOffsetPositive) {
                        maxOffsetPositive = offset;
                    }
                } else if (price == 'F'
                        && (previousPrice == 'F' || previousPrice == 'C')) {
                    offset--;
                    if (offset < minOffsetNegative) {
                        minOffsetNegative = offset;
                    }
                }
            }
        }

        int rows = maxOffsetPositive + Math.abs(minOffsetNegative) + 1;
        char[][] graph = new char[rows][prices.length()];
        for (int row = 0; row < graph.length; row++) {
            Arrays.fill(graph[row], ' ');
        }

        int row = graph.length - 1 - Math.abs(minOffsetNegative);
        for (int column = 0; column < prices.length(); column++) {
            char price = prices.charAt(column);
            if (price == 'R') {
                graph[row][column] = '/';
                row--;
            } else if (price == 'F') {
                if (column != 0) {
                    row++;
                }
                graph[row][column] = '\\';
            } else {
                graph[row][column] = '_';
            }
        }
        return graph;
    }

    private static int getLastNonBlankCharIndex(char[][] graph, int row) {
        for (int i = graph[row].length - 1; i >= 0; i--) {
            if (graph[row][i] != ' ') {
                return i;
            }
        }
        return -1;
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