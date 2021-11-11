package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/11/21.
 */
// Based on https://www.mathsisfun.com/puzzles/outwitting-the-weighing-machine-solution.html
public class OutwittingTheWeighingMachine {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] weights = new int[10];

            for (int i = 0; i < weights.length; i++) {
                weights[i] = FastReader.nextInt();
            }

            int[] girlWeights = getWeights(weights);
            outputWriter.printLine(String.format("Case %d: %d %d %d %d %d", t, girlWeights[0],
                    girlWeights[1], girlWeights[2], girlWeights[3], girlWeights[4]));
        }
        outputWriter.flush();
    }

    private static int[] getWeights(int[] weights) {
        int sum = 0;
        for (int weight : weights) {
            sum += weight;
        }
        Arrays.sort(weights);

        // Each girl is weighted 4 times
        int average = sum / 4;
        // c = (a + b + c + d + e) - (a + b) - (d + e)
        int thirdWeight = average - weights[0] - weights[9];

        int firstWeight = weights[1] - thirdWeight;
        int secondWeight = weights[0] - firstWeight;
        int fifthWeight = weights[8] - thirdWeight;
        int fourthWeight = weights[9] - fifthWeight;
        return new int[] { firstWeight, secondWeight, thirdWeight, fourthWeight, fifthWeight };
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
