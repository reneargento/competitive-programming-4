package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by Rene Argento on 06/06/21.
 */
public class BinarySearchTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int numbers = FastReader.nextInt();
        long totalDepth = 0;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();

        for (int i = 0; i < numbers; i++) {
            int value = FastReader.nextInt();
            int depth = getDepth(treeMap, value);

            treeMap.put(value, depth);
            totalDepth += depth;
            outputWriter.printLine(totalDepth);
        }
        outputWriter.flush();
    }

    private static int getDepth(TreeMap<Integer, Integer> treeMap, int value) {
        Integer ceilingKey = treeMap.ceilingKey(value);
        Integer floorKey = treeMap.floorKey(value);

        int ceilingDepth = -1;
        int floorDepth = -1;

        if (ceilingKey != null) {
            ceilingDepth = treeMap.get(ceilingKey);
        }
        if (floorKey != null) {
            floorDepth = treeMap.get(floorKey);
        }
        return Math.max(ceilingDepth, floorDepth) + 1;
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
