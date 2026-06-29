package chapter6.section2.e.string.comparison;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/06/2026.
 */
public class PhoneList {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] phoneNumbers = new String[FastReader.nextInt()];
            for (int i = 0; i < phoneNumbers.length; i++) {
                phoneNumbers[i] = FastReader.next();
            }
            String isConsistent = checkConsistency(phoneNumbers);
            outputWriter.printLine(isConsistent);
        }
        outputWriter.flush();
    }

    private static String checkConsistency(String[] phoneNumbers) {
        Arrays.sort(phoneNumbers);

        for (int i = 0; i < phoneNumbers.length - 1; i++) {
            if (phoneNumbers[i + 1].startsWith(phoneNumbers[i])) {
                return "NO";
            }
        }
        return "YES";
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