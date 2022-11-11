package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 10/11/22.
 */
public class TheTowerOfBabylon {

    private static class Block implements Comparable<Block> {
        int dimension1;
        int dimension2;
        int height;

        public Block(int dimension1, int dimension2, int height) {
            this.dimension1 = dimension1;
            this.dimension2 = dimension2;
            this.height = height;
        }

        @Override
        public int compareTo(Block other) {
            if (dimension1 != other.dimension1) {
                return Integer.compare(dimension1, other.dimension1);
            }
            if (dimension2 != other.dimension2) {
                return Integer.compare(dimension2, other.dimension2);
            }
            return Integer.compare(height, other.height);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int blocksNumber = FastReader.nextInt();
        int caseNumber = 1;

        while (blocksNumber != 0) {
            Block[] blocks = new Block[blocksNumber * 6];
            for (int i = 0; i < blocksNumber; i++) {
                int dimension1 = FastReader.nextInt();
                int dimension2 = FastReader.nextInt();
                int dimension3 = FastReader.nextInt();
                addBlocks(blocks, dimension1, dimension2, dimension3, i * 6);
            }
            long maxHeight = longestIncreasingSubsequenceMaxHeight(blocks);
            outputWriter.printLine(String.format("Case %d: maximum height = %d", caseNumber, maxHeight));

            caseNumber++;
            blocksNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void addBlocks(Block[] blocks, int dimension1, int dimension2, int dimension3, int index) {
        blocks[index] = new Block(dimension1, dimension2, dimension3);
        blocks[index + 1] = new Block(dimension1, dimension3, dimension2);
        blocks[index + 2] = new Block(dimension2, dimension1, dimension3);
        blocks[index + 3] = new Block(dimension2, dimension3, dimension1);
        blocks[index + 4] = new Block(dimension3, dimension1, dimension2);
        blocks[index + 5] = new Block(dimension3, dimension2, dimension1);
    }

    private static long longestIncreasingSubsequenceMaxHeight(Block[] blocks) {
        long maxHeight = 0;
        Arrays.sort(blocks);

        long[] heights = new long[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            heights[i] = blocks[i].height;
        }

        for (int i = 0; i < blocks.length; i++) {
            for (int j = i + 1; j < blocks.length; j++) {
                if (blocks[j].dimension1 > blocks[i].dimension1 && blocks[j].dimension2 > blocks[i].dimension2
                        && heights[i] + blocks[j].height > heights[j]) {
                    heights[j] = heights[i] + blocks[j].height;
                    maxHeight = Math.max(maxHeight, heights[j]);
                }
            }
        }
        return maxHeight;
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
