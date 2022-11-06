package chapter3.section5.a.max.oned.twod.range.sum;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 06/11/22.
 */
public class AliceInTheDigitalWorld {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int datasets = FastReader.nextInt();

        for (int t = 0; t < datasets; t++) {
            int[] sequence = new int[FastReader.nextInt()];
            int minimumValue = FastReader.nextInt();

            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();
            }
            int maximumWeight = computeMaximumWeight(sequence, minimumValue);
            outputWriter.printLine(maximumWeight);
        }
        outputWriter.flush();
    }

    private static int computeMaximumWeight(int[] sequence, int minimumValue) {
        int maximumWeight = 0;
        int currentWeight = 0;
        int startIndex = 0;
        int minimumValuesUsed = 0;

        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] > minimumValue) {
                currentWeight += sequence[i];
                if (minimumValuesUsed == 1) {
                    maximumWeight = Math.max(maximumWeight, currentWeight);
                }
            } else if (sequence[i] < minimumValue) {
                startIndex = i + 1;
                currentWeight = 0;
                minimumValuesUsed = 0;
            } else {
                minimumValuesUsed++;

                if (minimumValuesUsed > 1) {
                    while (sequence[startIndex] != minimumValue) {
                        currentWeight -= sequence[startIndex];
                        startIndex++;
                    }
                    currentWeight -= minimumValue;
                    startIndex++;
                    minimumValuesUsed--;
                }
                currentWeight += sequence[i];
                maximumWeight = Math.max(maximumWeight, currentWeight);
            }
        }
        return maximumWeight;
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
