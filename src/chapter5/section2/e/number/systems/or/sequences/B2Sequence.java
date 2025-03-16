package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/03/25.
 */
public class B2Sequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        int caseNumber = 1;
        while (line != null) {
            int length = Integer.parseInt(line);
            boolean isIncreasing = true;
            int[] sequence = new int[length];

            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = FastReader.nextInt();

                if (i > 0 && sequence[i] <= sequence[i - 1]) {
                    isIncreasing = false;
                }
            }

            boolean isB2Sequence = isB2Sequence(sequence, isIncreasing);
            outputWriter.print(String.format("Case #%d: ", caseNumber));
            if (isB2Sequence) {
                outputWriter.printLine("It is a B2-Sequence.\n");
            } else {
                outputWriter.printLine("It is not a B2-Sequence.\n");
            }
            caseNumber++;
            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isB2Sequence(int[] sequence, boolean isIncreasing) {
        if (!isIncreasing) {
            return false;
        }
        Set<Integer> sums = new HashSet<>();

        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] <= 0) {
                return false;
            }

            for (int j = i; j < sequence.length; j++) {
                int sum = sequence[i] + sequence[j];
                if (sums.contains(sum)) {
                    return false;
                }
                sums.add(sum);
            }
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

        public void flush() {
            writer.flush();
        }
    }
}
