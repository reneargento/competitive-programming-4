package chapter3.section4.b.involving.sorting.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/06/22.
 */
public class BoiledEggs {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int numberOfEggs = FastReader.nextInt();
            int maxNumberOfEggs = FastReader.nextInt();
            int maximumWeight = FastReader.nextInt();
            int[] eggWeights = new int[numberOfEggs];
            for (int i = 0; i < eggWeights.length; i++) {
                eggWeights[i] = FastReader.nextInt();
            }
            int maxPossibleNumberOfEggs = computeMaxPossibleNumberOfEggs(eggWeights, maxNumberOfEggs, maximumWeight);
            outputWriter.printLine(String.format("Case %d: %d", t, maxPossibleNumberOfEggs));
        }
        outputWriter.flush();
    }

    private static int computeMaxPossibleNumberOfEggs(int[] eggWeights, int maxNumberOfEggs, int maximumWeight) {
        int maxPossibleNumberOfEggs = 0;
        int totalWeight = 0;

        for (int eggWeight : eggWeights) {
            if (maxPossibleNumberOfEggs == maxNumberOfEggs || totalWeight + eggWeight > maximumWeight) {
                break;
            }
            totalWeight += eggWeight;
            maxPossibleNumberOfEggs++;
        }
        return maxPossibleNumberOfEggs;
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
