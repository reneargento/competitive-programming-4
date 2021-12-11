package chapter3.section2.g.tryall.possible.answers;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 10/12/21.
 */
public class IslandsInTheDataStream {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dataSet = FastReader.nextInt();
            int[] numbers = new int[12];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = FastReader.nextInt();
            }

            int islands = countIslands(numbers);
            outputWriter.printLine(String.format("%d %d", dataSet, islands));
        }
        outputWriter.flush();
    }

    private static int countIslands(int[] numbers) {
        int islands = 0;

        for (int i = 1; i < numbers.length - 1; i++) {
            int minValue = numbers[i];

            for (int j = i; j < numbers.length - 1; j++) {
                minValue = Math.min(minValue, numbers[j]);

                if (numbers[i - 1] < minValue && numbers[j + 1] < minValue) {
                    islands++;
                }
            }
        }
        return islands;
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
