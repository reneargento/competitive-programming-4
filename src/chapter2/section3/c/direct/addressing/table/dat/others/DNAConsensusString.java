package chapter2.section3.c.direct.addressing.table.dat.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/05/21.
 */
public class DNAConsensusString {
    private static final int A_INDEX = 0;
    private static final int C_INDEX = 1;
    private static final int G_INDEX = 2;
    private static final int T_INDEX = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int dnaSequencesNumber = FastReader.nextInt();
            int length = FastReader.nextInt();
            String[] dnaSequences = new String[dnaSequencesNumber];

            for (int i = 0; i < dnaSequences.length; i++) {
                dnaSequences[i] = FastReader.next();
            }
            computeConsensusString(dnaSequences, outputWriter);
        }
        outputWriter.flush();
    }

    private static void computeConsensusString(String[] dnaSequences, OutputWriter outputWriter) {
        StringBuilder consensusString = new StringBuilder();
        int consensusError = 0;

        for (int i = 0; i < dnaSequences[0].length(); i++) {
            int[] nucleotideFrequency = new int[4];
            int highestFrequency = 0;

            for (String dnaSequence : dnaSequences) {
                char nucleotide = dnaSequence.charAt(i);
                int selectedIndex;
                switch (nucleotide) {
                    case 'A': selectedIndex = A_INDEX; break;
                    case 'C': selectedIndex = C_INDEX; break;
                    case 'G': selectedIndex = G_INDEX; break;
                    default: selectedIndex = T_INDEX;
                }
                nucleotideFrequency[selectedIndex]++;
                highestFrequency = Math.max(highestFrequency, nucleotideFrequency[selectedIndex]);
            }

            char nucleotideToAdd;
            if (nucleotideFrequency[A_INDEX] == highestFrequency) {
                nucleotideToAdd = 'A';
            } else if (nucleotideFrequency[C_INDEX] == highestFrequency) {
                nucleotideToAdd = 'C';
            } else if (nucleotideFrequency[G_INDEX] == highestFrequency) {
                nucleotideToAdd = 'G';
            } else {
                nucleotideToAdd = 'T';
            }

            consensusString.append(nucleotideToAdd);
            consensusError += dnaSequences.length - highestFrequency;
        }

        outputWriter.printLine(consensusString.toString());
        outputWriter.printLine(consensusError);
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
