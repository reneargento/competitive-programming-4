package chapter6.section2.c.regular.expression;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 13/05/26.
 */
public class LindenMayorSystem {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rulesNumber = FastReader.nextInt();
        String[] rules = new String[26];
        int iterations = FastReader.nextInt();
        for (int i = 0; i < rulesNumber; i++) {
            char original = FastReader.next().charAt(0);
            FastReader.next();
            String replacement = FastReader.next();
            int index = original - 'A';
            rules[index] = replacement;
        }
        String sequence = FastReader.next();

        String finalSequence = computeFinalSequence(sequence, rules, iterations);
        outputWriter.printLine(finalSequence);
        outputWriter.flush();
    }

    private static String computeFinalSequence(String sequence, String[] rules, int iterations) {
        for (int i = 0; i < iterations; i++) {
            StringBuilder nextSequence = new StringBuilder();
            for (int s = 0; s < sequence.length(); s++) {
                char token = sequence.charAt(s);
                int index = token - 'A';
                if (rules[index] != null) {
                    nextSequence.append(rules[index]);
                } else {
                    nextSequence.append(token);
                }
            }
            sequence = nextSequence.toString();
        }
        return sequence;
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
