package chapter3.section4.section1;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/05/22.
 */
public class Exercise2 {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int ballotBoxes = FastReader.nextInt();

        while (cities != -1) {
            int[] population = new int[cities];
            int maxPopulation = 0;
            for (int i = 0; i < population.length; i++) {
                population[i] = FastReader.nextInt();
                maxPopulation = Math.max(maxPopulation, population[i]);
            }
            int maximumPeoplePerBox = computeMaximumPeoplePerBox(population, ballotBoxes, maxPopulation);
            outputWriter.printLine(maximumPeoplePerBox);

            FastReader.getLine();
            cities = FastReader.nextInt();
            ballotBoxes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaximumPeoplePerBox(int[] population, int ballotBoxes, int maxPopulation) {
        int low = 1;
        int high = maxPopulation;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            boolean canReachTargetMaxPeople = canReachTargetMaxPeople(population, ballotBoxes, middle);
            if (canReachTargetMaxPeople) {
                result = middle;
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return result;
    }

    private static boolean canReachTargetMaxPeople(int[] population, int ballotBoxes, int targetMaxPeople) {
        int ballotBoxesUsed = 0;

        for (int numberOfPeople : population) {
            // Ceiling division
            ballotBoxesUsed += (numberOfPeople + (targetMaxPeople - 1)) / targetMaxPeople;
        }
        return ballotBoxesUsed <= ballotBoxes;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
