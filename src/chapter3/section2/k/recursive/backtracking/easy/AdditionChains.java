package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/02/22.
 */
// Based on https://github.com/morris821028/UVa/blob/master/volume005/529%20-%20Addition%20Chains.cpp
public class AdditionChains {

    private static int sequenceLength;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int number = FastReader.nextInt();
        int[] currentSequence = new int[10001];
        int[] finalSequence = new int[10001];

        while (number != 0) {
            int[] maxValues = computeMaxValues(number);
            currentSequence[0] = 1;
            finalSequence[0] = 1;
            sequenceLength = number + 1;

            int[] count = new int[20001];
            count[1] = 1;
            count[2] = 1;
            depthFirstSearch(1, 2, maxValues, currentSequence, finalSequence, count, number);

            outputWriter.print(finalSequence[0]);
            for (int i = 1; i < sequenceLength; i++) {
                outputWriter.print(" " + finalSequence[i]);
            }
            outputWriter.printLine();
            number = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] computeMaxValues(int number) {
        int[] maxValues = new int[number * 2 + 1];

        for (int i = number - 1; i > 0; i--) {
            maxValues[i] = maxValues[i * 2] + 1;
        }
        return maxValues;
    }

    private static void depthFirstSearch(int index, int max, int[] maxValues, int[] currentSequence,
                                         int[] finalSequence, int[] count, int number) {
        int lastNumberInSequence = currentSequence[index - 1];
        if (maxValues[lastNumberInSequence] + index >= sequenceLength) {
            return;
        }

        if (lastNumberInSequence == number) {
            sequenceLength = index;
            System.arraycopy(currentSequence, 0, finalSequence, 0, sequenceLength);
            return ;
        }

        int startIndex = Math.min(number, max);
        for (int candidateNumber = startIndex; candidateNumber > lastNumberInSequence; candidateNumber--) {
            if (count[candidateNumber] == 0) {
                continue;
            }
            currentSequence[index] = candidateNumber;

            for (int j = 0; j <= index; j++) {
                int sumNumber = currentSequence[j] + candidateNumber;
                count[sumNumber]++;
            }

            depthFirstSearch(index + 1, 2 * candidateNumber, maxValues, currentSequence,
                    finalSequence, count, number);
            for (int j = 0; j <= index; j++) {
                int sumNumber = currentSequence[j] + candidateNumber;
                count[sumNumber]--;
            }
        }
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
