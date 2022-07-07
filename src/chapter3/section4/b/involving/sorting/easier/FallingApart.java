package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/07/22.
 */
public class FallingApart {

    private static class Solution {
        int aliceSum;
        int bobSum;

        public Solution(int aliceSum, int bobSum) {
            this.aliceSum = aliceSum;
            this.bobSum = bobSum;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] values = new int[FastReader.nextInt()];

        for (int i = 0; i < values.length; i++) {
            values[i] = FastReader.nextInt();
        }
        Solution solution = splitIntegers(values);
        outputWriter.printLine(String.format("%d %d", solution.aliceSum, solution.bobSum));
        outputWriter.flush();
    }

    private static Solution splitIntegers(int[] values) {
        int aliceSum = 0;
        int bobSum = 0;

        Arrays.sort(values);
        int integerNumber = 0;
        for (int i = values.length - 1; i >= 0; i--) {
            if (integerNumber % 2 == 0) {
                aliceSum += values[i];
            } else {
                bobSum += values[i];
            }
            integerNumber++;
        }
        return new Solution(aliceSum, bobSum);
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
