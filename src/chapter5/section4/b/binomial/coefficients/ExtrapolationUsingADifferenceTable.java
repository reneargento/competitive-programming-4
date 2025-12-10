package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/12/25.
 */
public class ExtrapolationUsingADifferenceTable {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceSize = FastReader.nextInt();

        while (sequenceSize != 0) {
            List<Integer> sequence = new ArrayList<>();
            for (int i = 0; i < sequenceSize; i++) {
                sequence.add(FastReader.nextInt());
            }
            int extrapolationRequests = FastReader.nextInt();

            int targetTerm = sequenceSize + extrapolationRequests;
            int kthElement = performExtrapolations(sequence, extrapolationRequests);
            outputWriter.printLine(String.format("Term %d of the sequence is %d", targetTerm, kthElement));
            sequenceSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int performExtrapolations(List<Integer> sequence, int extrapolationRequests) {
        List<Integer>[] differenceTable = buildInitialDifferenceTable(sequence);

        for (int k = 0; k < extrapolationRequests; k++) {
            for (int i = differenceTable.length - 2; i >= 0; i--) {
                int referenceListLastIndex = differenceTable[i + 1].size() - 1;
                int difference = differenceTable[i + 1].get(referenceListLastIndex);

                int currentListLastIndex = differenceTable[i].size() - 1;
                int nextElement = differenceTable[i].get(currentListLastIndex) + difference;
                differenceTable[i].add(nextElement);
            }
        }
        return differenceTable[0].get(differenceTable[0].size() - 1);
    }

    private static List<Integer>[] buildInitialDifferenceTable(List<Integer> sequence) {
        @SuppressWarnings("unchecked")
        List<Integer>[] differenceTable = new List[sequence.size()];
        differenceTable[0] = sequence;

        for (int i = 1; i < differenceTable.length; i++) {
            differenceTable[i] = new ArrayList<>();
            int elementsNumber = differenceTable[i - 1].size() - 1;

            for (int j = 0; j < elementsNumber; j++) {
                differenceTable[i].add(differenceTable[i - 1].get(j + 1) - differenceTable[i - 1].get(j));
            }
        }
        return differenceTable;
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
