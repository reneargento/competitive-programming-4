package chapter2.section4.a.graph.data.structures.problems;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/07/21.
 */
@SuppressWarnings("unchecked")
public class EasyProblemFromRujiaLiu {


    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        while (line != null) {
            String[] data = line.split(" ");
            int arraySize = Integer.parseInt(data[0]);
            int queries = Integer.parseInt(data[1]);

            List<Integer>[] valuePositions = new ArrayList[1000000];
            for (int i = 1; i <= arraySize; i++) {
                int value = FastReader.nextInt();
                if (valuePositions[value] == null) {
                    valuePositions[value] = new ArrayList<>();
                }
                valuePositions[value].add(i);
            }

            for (int q = 0; q < queries; q++) {
                int queryPosition = FastReader.nextInt() - 1;
                int value = FastReader.nextInt();

                if (valuePositions[value] == null || valuePositions[value].size() <= queryPosition) {
                    outputWriter.printLine(0);
                } else {
                    outputWriter.printLine(valuePositions[value].get(queryPosition));
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
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