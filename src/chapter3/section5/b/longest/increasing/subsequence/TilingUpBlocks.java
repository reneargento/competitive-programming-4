package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/11/22.
 */
public class TilingUpBlocks {

    private static class Block implements Comparable<Block> {
        int leftValue;
        int middleValue;

        public Block(int leftValue, int middleValue) {
            this.leftValue = leftValue;
            this.middleValue = middleValue;
        }

        @Override
        public int compareTo(Block otherBlock) {
            if (this.leftValue != otherBlock.leftValue) {
                return Integer.compare(this.leftValue, otherBlock.leftValue);
            } else {
                return Integer.compare(this.middleValue, otherBlock.middleValue);
            }
        }

        public boolean isHigherOrEqual(Block otherBlock) {
            return leftValue >= otherBlock.leftValue && middleValue >= otherBlock.middleValue;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int blocksNumber = FastReader.nextInt();

        while (blocksNumber != 0) {
            Block[] blocks = new Block[blocksNumber];
            for (int i = 0; i < blocks.length; i++) {
                blocks[i] = new Block(FastReader.nextInt(), FastReader.nextInt());
            }

            int tallestBlock = longestIncreasingSubsequence(blocks);
            outputWriter.printLine(tallestBlock);
            blocksNumber = FastReader.nextInt();
        }
        outputWriter.printLine("*");
        outputWriter.flush();
    }

    private static int longestIncreasingSubsequence(Block[] blocks) {
        int[] lis = new int[blocks.length];
        int lisLength = 0;

        Arrays.fill(lis, 1);
        Arrays.sort(blocks);

        for (int i = 0; i < blocks.length; i++) {
            for (int j = i + 1; j < blocks.length; j++) {
                if (blocks[j].isHigherOrEqual(blocks[i]) && lis[i] + 1 > lis[j]) {
                    lis[j] = lis[i] + 1;
                    lisLength = Math.max(lisLength, lis[j]);
                }
            }
        }
        return lisLength;
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
