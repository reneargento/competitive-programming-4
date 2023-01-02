package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/01/23.
 */
public class GrowingTrees {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int level = FastReader.nextInt();
        long[] leaves = computeLeavesPerLevel();

        while (level != 0) {
            outputWriter.printLine(leaves[level - 1]);
            level = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long[] computeLeavesPerLevel() {
        long[] leaves = new long[86];
        leaves[0] = 1;
        leaves[1] = 1;

        for (int i = 2; i < leaves.length; i++) {
            leaves[i] = leaves[i - 1] + leaves[i - 2];
        }
        return leaves;
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

        public void flush() {
            writer.flush();
        }
    }
}
