package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/07/22.
 */
public class TextMessagingOutrage {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            FastReader.nextInt(); // Not needed
            int keys = FastReader.nextInt();
            int[] lettersFrequency = new int[FastReader.nextInt()];
            for (int i = 0; i < lettersFrequency.length; i++) {
                lettersFrequency[i] = FastReader.nextInt();
            }
            long minimalKeypadPresses = computeMinimalKeypadPresses(keys, lettersFrequency);
            outputWriter.printLine(String.format("Case #%d: %d", t, minimalKeypadPresses));
        }
        outputWriter.flush();
    }

    private static long computeMinimalKeypadPresses(int keys, int[] lettersFrequency) {
        long minimalKeypadPresses = 0;
        Arrays.sort(lettersFrequency);
        int currentKey = 0;
        int currentPosition = 1;

        for (int i = lettersFrequency.length - 1; i >= 0; i--) {
            minimalKeypadPresses += currentPosition * lettersFrequency[i];

            currentKey++;
            if (currentKey == keys) {
                currentPosition++;
                currentKey = 0;
            }
        }
        return minimalKeypadPresses;
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
