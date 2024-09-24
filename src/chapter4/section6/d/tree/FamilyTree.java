package chapter4.section6.d.tree;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 23/07/24.
 */
public class FamilyTree {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int generations = FastReader.nextInt();
            int personId1 = FastReader.nextInt();
            int personId2 = FastReader.nextInt();

            int totalNumberOfPeople = computeTotalNumberOfPeople(generations, personId1, personId2);
            outputWriter.printLine(totalNumberOfPeople);
        }
        outputWriter.flush();
    }

    private static int computeTotalNumberOfPeople(int generations, int personId1, int personId2) {
        int totalNumberCompleteTree = ((int) Math.pow(2, generations)) - 1;
        int generationPerson1 = log2(personId1);
        int generationPerson2 = log2(personId2);

        int highestGeneration = Math.max(generationPerson1, generationPerson2);
        int generationDifference = generations - highestGeneration;
        int peopleToSubtract = (int) (Math.pow(2, generationDifference)) - 2;
        return totalNumberCompleteTree - peopleToSubtract;
    }

    private static int log2(int value) {
        return (int) (Math.log(value) / Math.log(2));
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
