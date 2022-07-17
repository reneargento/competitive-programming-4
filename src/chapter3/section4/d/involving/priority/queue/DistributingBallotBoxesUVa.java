package chapter3.section4.d.involving.priority.queue;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/07/22.
 */
public class DistributingBallotBoxesUVa {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cities = FastReader.nextInt();
        int ballotBoxes = FastReader.nextInt();

        while (cities != -1 || ballotBoxes != -1) {
            int[] population = new int[cities];
            int maxPopulation = 0;
            for (int i = 0; i < cities; i++) {
                population[i] = FastReader.nextInt();
                maxPopulation = Math.max(maxPopulation, population[i]);
            }
            int maxPeoplePerBox = computeMaxPeoplePerBox(population, ballotBoxes, maxPopulation);
            outputWriter.printLine(maxPeoplePerBox);

            cities = FastReader.nextInt();
            ballotBoxes = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaxPeoplePerBox(int[] population, int ballotBoxes, int maxPopulation) {
        int maxPeoplePerBox = 0;
        int low = 1;
        int high = maxPopulation;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (canAssignMaxPeople(population, ballotBoxes, middle)) {
                maxPeoplePerBox = middle;
                high = middle - 1;
            }  else {
                low = middle + 1;
            }
        }
        return maxPeoplePerBox;
    }

    private static boolean canAssignMaxPeople(int[] population, int ballotBoxes, double maxPeople) {
        for (int cityPopulation : population) {
            double ballotsRequired = Math.max(cityPopulation / maxPeople, 1);
            if (ballotsRequired > ballotBoxes) {
                return false;
            }
            ballotBoxes -= ballotsRequired;
        }
        return true;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
