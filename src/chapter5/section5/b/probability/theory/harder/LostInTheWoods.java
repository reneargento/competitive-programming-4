package chapter5.section5.b.probability.theory.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/02/26.
 */
@SuppressWarnings("unchecked")
public class LostInTheWoods {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<Integer>[] adjacencyList = new List[FastReader.nextInt()];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        int roads = FastReader.nextInt();
        for (int m = 0; m < roads; m++) {
            int clearingId1 = FastReader.nextInt();
            int clearingId2 = FastReader.nextInt();
            adjacencyList[clearingId1].add(clearingId2);
            adjacencyList[clearingId2].add(clearingId1);
        }

        double expectedValue = computeExpectedValue(adjacencyList);
        outputWriter.printLine(expectedValue);
        outputWriter.flush();
    }

    private static double computeExpectedValue(List<Integer>[] adjacencyList) {
        double[] probabilities = new double[adjacencyList.length];
        probabilities[0] = 1.0;
        double expectedValue = 0;

        for (int length = 1; length < 10000; length++) {
            double[] probabilitiesPrevious = probabilities.clone();
            Arrays.fill(probabilities, 0);

            for (int clearingId = 0; clearingId < adjacencyList.length - 1; clearingId++) {
                if (!adjacencyList[clearingId].isEmpty()) {
                    for (int neighborId : adjacencyList[clearingId]) {
                        probabilities[neighborId] += probabilitiesPrevious[clearingId] / adjacencyList[clearingId].size();
                    }
                }
            }
            expectedValue += probabilities[adjacencyList.length - 1] * length;
        }
        return expectedValue;
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
