package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 19/05/22.
 */
public class ColoringSocks {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] sockColors = new int[FastReader.nextInt()];
        int capacity = FastReader.nextInt();
        int maximumDifference = FastReader.nextInt();

        for (int i = 0; i < sockColors.length; i++) {
            sockColors[i] = FastReader.nextInt();
        }
        int machinesNumber = computeMachinesNumber(sockColors, capacity, maximumDifference);
        outputWriter.printLine(machinesNumber);
        outputWriter.flush();
    }

    private static int computeMachinesNumber(int[] sockColors, int capacity, int maximumDifference) {
        int machinesNumber = 0;
        Arrays.sort(sockColors);

        for (int i = 0; i < sockColors.length; i++) {
            int j;
            for (j = i + 1; j < sockColors.length && j < i + capacity; j++) {
                if (Math.abs(sockColors[i] - sockColors[j]) > maximumDifference) {
                    break;
                }
            }
            i = j - 1;
            machinesNumber++;
        }
        return machinesNumber;
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
