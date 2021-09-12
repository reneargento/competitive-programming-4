package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class PrimeMatrix {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int matrixDimension = FastReader.nextInt();
        int maxNumber = FastReader.nextInt();

        if (matrixDimension != 2 && matrixDimension >= maxNumber) {
            outputWriter.printLine("impossible");
        } else {
            int[] sequence = getSequence(matrixDimension, maxNumber);
            if (sequence == null) {
                outputWriter.printLine("impossible");
            } else {
                printMatrix(sequence, outputWriter);
            }
        }
        outputWriter.flush();
    }

    private static void printMatrix(int[] sequence, OutputWriter outputWriter) {
        for (int row = 0; row < sequence.length; row++) {
            int endColumn = (row + sequence.length) % sequence.length;
            int printedNumbers = 0;

            int column = row;
            do {
                outputWriter.print(sequence[column]);
                printedNumbers++;

                if (printedNumbers != sequence.length) {
                    outputWriter.print(" ");
                }

                column++;
                if (column == sequence.length) {
                    column = 0;
                }
            } while (column != endColumn);

            outputWriter.printLine();
        }
    }

    private static int[] getSequence(int numbers, int maxNumber) {
        Set<Integer> primes = generatePrimes();
        int[] sequence = new int[numbers];
        return generateSequence(maxNumber, 1, 0, sequence, 0, primes);
    }

    private static int[] generateSequence(int maxNumber, int nextNumber, int currentSum,
                                          int[] sequence, int sequenceIndex, Set<Integer> primes) {
        if (sequenceIndex == sequence.length) {
            if (primes.contains(currentSum)) {
                return sequence;
            }
            return null;
        }

        for (int i = nextNumber; i <= maxNumber; i++) {
            sequence[sequenceIndex] = i;
            int newSum = currentSum + i;

            int[] finalSequence =
                    generateSequence(maxNumber, i + 1, newSum, sequence,
                            sequenceIndex + 1, primes);
            if (finalSequence != null) {
                return finalSequence;
            }
        }
        return null;
    }

    private static Set<Integer> generatePrimes() {
        boolean[] isComposite = new boolean[10000];
        isComposite[1] = false;

        for (long i = 2; i < isComposite.length; i++) {
            if (!isComposite[(int) i]) {
                for (long j = i * i; j < isComposite.length; j += i) {
                    isComposite[(int) j] = true;
                }
            }
        }

        Set<Integer> primes = new HashSet<>();
        for (int i = 1; i < isComposite.length; i++) {
            if (!isComposite[i]) {
                primes.add(i);
            }
        }
        return primes;
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
