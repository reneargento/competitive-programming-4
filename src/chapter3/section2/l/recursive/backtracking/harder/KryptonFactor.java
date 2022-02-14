package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/02/22.
 */
public class KryptonFactor {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int sequenceIndex = FastReader.nextInt();
        int maxLetter = FastReader.nextInt();
        char[] letters = new char[80];

        while (sequenceIndex != 0 || maxLetter != 0) {
            List<String> sequences = new ArrayList<>();

            for (int i = 0; i < maxLetter; i++) {
                char letter = getLetter(i);
                letters[0] = letter;
                getKthSequence(sequenceIndex, maxLetter, letters, 1, sequences);
            }
            String kthSequence = sequences.get(sequenceIndex - 1);

            int groups = 0;
            for (int i = 0; i < kthSequence.length(); i++) {
                outputWriter.print(kthSequence.charAt(i));

                if (i < kthSequence.length() - 1 && (i + 1) % 4 == 0) {
                    if (groups == 15) {
                        outputWriter.printLine();
                    } else {
                        outputWriter.print(" ");
                    }
                    groups++;
                }
            }
            outputWriter.printLine();
            outputWriter.printLine(kthSequence.length());

            sequenceIndex = FastReader.nextInt();
            maxLetter = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void getKthSequence(int sequenceIndex, int maxLetter, char[] letters, int index,
                                       List<String> sequences) {
        if (sequences.size() == sequenceIndex) {
            return;
        }

        if (isHard(letters, index)) {
            if (sequences.size() + 1 == sequenceIndex) {
                String finalSequence = buildSequence(letters, index);
                sequences.add(finalSequence);
            } else {
                sequences.add("");

                for (int i = 0; i < maxLetter; i++) {
                    char letter = getLetter(i);
                    letters[index] = letter;
                    getKthSequence(sequenceIndex, maxLetter, letters, index + 1, sequences);
                }
            }
        }
    }

    private static boolean isHard(char[] letters, int length) {
        String sequence = buildSequence(letters, length);
        return !sequence.matches("^.*(.+)(?:\\1)+.*$");
    }

    private static String buildSequence(char[] letters, int length) {
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sequence.append(letters[i]);
        }
        return sequence.toString();
    }

    private static char getLetter(int index) {
        return (char) ('A' + index);
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
