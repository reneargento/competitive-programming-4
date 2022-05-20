package chapter3.section4.a.classical;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/05/22.
 */
public class Inflation {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] gasCanisters = new int[FastReader.nextInt()];

        for (int i = 0; i < gasCanisters.length; i++) {
            gasCanisters[i] = FastReader.nextInt();
        }
        double maximumFractionFilled = computeMaximumFractionFilled(gasCanisters);
        if (maximumFractionFilled != -1) {
            outputWriter.printLine(maximumFractionFilled);
        } else {
            outputWriter.printLine("impossible");
        }
        outputWriter.flush();
    }

    private static double computeMaximumFractionFilled(int[] gasCanisters) {
        double maximumFractionFilled = 1;
        int balloonSize = 1;
        Arrays.sort(gasCanisters);

        for (int gasCanister : gasCanisters) {
            double fraction = gasCanister / (double) balloonSize;
            if (fraction > 1) {
                return -1;
            }
            maximumFractionFilled = Math.min(maximumFractionFilled, fraction);
            balloonSize++;
        }
        return maximumFractionFilled;
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
