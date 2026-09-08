package chapter6.section5;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/09/2026.
 */
public class GATTACA {

    private static class Result {
        String lrs;
        int frequency;

        public Result(String lrs, int frequency) {
            this.lrs = lrs;
            this.frequency = frequency;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String sequence = FastReader.next();
            Result result = computeLongestRepeatingSubstring(sequence);

            if (result.lrs == null) {
                outputWriter.printLine("No repetitions found!");
            } else {
                outputWriter.printLine(result.lrs + " " + result.frequency);
            }
        }
        outputWriter.flush();
    }

    private static Result computeLongestRepeatingSubstring(String sequence) {
        SuffixArrayNlgN suffixArray = new SuffixArrayNlgN(sequence + "$");
        int maxLcp = 1;
        String lrs = null;
        int frequency = 0;

        for (int i = 1; i < suffixArray.lcp.length; i++) {
            if (suffixArray.lcp[i] >= maxLcp) {
                maxLcp = suffixArray.lcp[i];
                String lrsCandidate =
                        sequence.substring(suffixArray.suffixArray[i], suffixArray.suffixArray[i] + suffixArray.lcp[i]);

                int candidateFrequency = 2;
                for (int j = i + 1; j < sequence.length() && suffixArray.lcp[j] == suffixArray.lcp[i]; j++) {
                    candidateFrequency++;
                }

                if (lrs == null
                        || lrsCandidate.length() > lrs.length()
                        || (lrsCandidate.length() == lrs.length()
                            && lrsCandidate.compareTo(lrs) < 0)) {
                    lrs = lrsCandidate;
                    frequency = candidateFrequency;
                }
            }
        }
        return new Result(lrs, frequency);
    }

    private static class SuffixArrayNlgN {
        public int[] suffixArray;
        public int[] lcp;

        private final char[] string;
        private final int stringLength;

        public SuffixArrayNlgN(String string) {
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
                count[i + k < stringLength ? ranks[i + k] : 0]++;
            }
            for (int i = 0; i < maxi; i++) {
                int aux = count[i];
                count[i] = sum;
                sum += aux;
            }
            for (int i = 0; i < stringLength; i++) {
                tempSuffixArray[count[suffixArray[i] + k < stringLength ? ranks[suffixArray[i] + k] : 0]++] = suffixArray[i];
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
                            (ranks[suffixArray[i]] == ranks[suffixArray[i - 1]] && ranks[suffixArray[i] + k] == ranks[suffixArray[i - 1] + k]) ? rank : ++rank;
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