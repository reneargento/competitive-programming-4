package chapter6.section5;

import java.io.*;

/**
 * Created by Rene Argento on 12/09/2026.
 */
public class BurrowsWheeler {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String message = FastReader.getLine();
        while (message != null) {
            String encodedMessage = applyBurrowsWheeler(message);
            outputWriter.printLine(encodedMessage);
            message = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String applyBurrowsWheeler(String message) {
        StringBuilder encodedMessage = new StringBuilder();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(message);

        for (int suffixIndex : circularSuffixArray.suffixArray) {
            int rightColumnIndex = suffixIndex - 1;
            if (rightColumnIndex < 0) {
                rightColumnIndex = circularSuffixArray.string.length - 1;
            }
            encodedMessage.append(circularSuffixArray.string[rightColumnIndex]);
        }
        return encodedMessage.toString();
    }

    private static class CircularSuffixArray {
        public int[] suffixArray;
        public int[] lcp;

        private final char[] string;
        private final int stringLength;

        public CircularSuffixArray(String string) {
            this.string = string.toCharArray();
            stringLength = this.string.length;
            constructSuffixArray();
            computeLcp();
        }

        private void countingSort(int[] ranks, int k) {
            int sum = 0;
            int[] tempSuffixArray = new int[stringLength];
            int maxi = Math.max(300, stringLength);
            int[] count = new int[maxi];
            for (int i = 0; i < stringLength; i++) {
                count[ranks[(i + k) % stringLength]]++;
            }
            for (int i = 0; i < maxi; i++) {
                int aux = count[i];
                count[i] = sum;
                sum += aux;
            }
            for (int i = 0; i < stringLength; i++) {
                tempSuffixArray[count[ranks[(suffixArray[i] + k) % stringLength]]++] = suffixArray[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = tempSuffixArray[i];
            }
        }

        private void constructSuffixArray() {
            suffixArray = new int[stringLength];
            int[] ranks = new int[stringLength];
            int[] tempRanks = new int[stringLength];

            for (int i = 0; i < stringLength; i++) {
                ranks[i] = string[i];
            }
            for (int i = 0; i < stringLength; i++) {
                suffixArray[i] = i;
            }
            for (int k = 1; k < stringLength; k <<= 1) {
                countingSort(ranks, k);
                countingSort(ranks, 0);

                int rank = 0;
                for (int i = 1; i < stringLength; i++) {
                    tempRanks[suffixArray[i]] =
                            (ranks[suffixArray[i]] == ranks[suffixArray[i - 1]]
                                    && ranks[(suffixArray[i] + k) % stringLength]
                                    == ranks[(suffixArray[i - 1] + k) % stringLength]) ? rank : ++rank;
                }
                for (int i = 0; i < stringLength; i++) {
                    ranks[i] = tempRanks[i];
                }
            }
        }

        private void computeLcp() {
            int length = 0;
            lcp = new int[stringLength];
            int[] plcp = new int[stringLength];
            int[] phi = new int[stringLength];
            phi[suffixArray[0]] = -1;
            for (int i = 1; i < stringLength; i++) {
                phi[suffixArray[i]] = suffixArray[i - 1];
            }
            for (int i = 0; i < stringLength; i++) {
                if (phi[i] == -1) {
                    plcp[i] = 0;
                    continue;
                }
                while (i + length < string.length
                        && phi[i] + length < string.length
                        && string[i + length] == string[phi[i] + length]) {
                    length++;
                }
                plcp[i] = length;
                length = Math.max(length - 1, 0);
            }
            for (int i = 1; i < stringLength; i++) {
                lcp[i] = plcp[suffixArray[i]];
            }
        }
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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

        public void flush() {
            writer.flush();
        }
    }
}