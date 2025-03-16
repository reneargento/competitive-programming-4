package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 15/03/25.
 */
public class LookAndSaySequences {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int firstTerm = FastReader.nextInt();
        int targetTerm = FastReader.nextInt();
        int digit = FastReader.nextInt();

        while (firstTerm != 0 || digit != 0 || targetTerm != 0) {
            char targetTermDigit = getTargetTermDigit(String.valueOf(firstTerm), targetTerm, digit - 1);
            outputWriter.printLine(targetTermDigit);

            firstTerm = FastReader.nextInt();
            targetTerm = FastReader.nextInt();
            digit = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static char getTargetTermDigit(String firstTerm, int targetTerm, int digit) {
        String currentTerm = firstTerm;
        int offset = 10;

        for (int i = 1; i < targetTerm; i++) {
            StringBuilder nextTerm = new StringBuilder();

            for (int c = 0; c < currentTerm.length() && c <= digit + offset; c++) {
                char currentDigit = currentTerm.charAt(c);
                int count = 1;

                for (int nextC = c + 1; nextC < currentTerm.length() && currentDigit == currentTerm.charAt(nextC); nextC++) {
                    count++;
                    c++;
                }
                nextTerm.append(count).append(currentDigit);
            }
            currentTerm = nextTerm.toString();
        }
        return currentTerm.charAt(digit);
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
