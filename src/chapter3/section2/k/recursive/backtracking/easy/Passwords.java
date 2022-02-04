package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/02/22.
 */
public class Passwords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String[] digits = getDigits();
        String line = FastReader.getLine();

        while (line != null) {
            int words = Integer.parseInt(line);
            String[] dictionary = new String[words];
            for (int i = 0; i < dictionary.length; i++) {
                dictionary[i] = FastReader.next();
            }
            String[] rules = new String[FastReader.nextInt()];
            for (int i = 0; i < rules.length; i++) {
                rules[i] = FastReader.next();
            }
            computePasswords(dictionary, rules, outputWriter, digits);
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void computePasswords(String[] dictionary, String[] rules, OutputWriter outputWriter,
                                         String[] digits) {
        outputWriter.printLine("--");
        for (String rule : rules) {
            String[] currentPassword = new String[rule.length()];
            computePasswords(dictionary, rule, outputWriter, digits, currentPassword, 0);
        }
    }

    private static void computePasswords(String[] dictionary, String rule, OutputWriter outputWriter,
                                         String[] digits, String[] currentPassword, int index) {
        if (index == currentPassword.length) {
            for (String word : currentPassword) {
                outputWriter.print(word);
            }
            outputWriter.printLine();
            return;
        }

        char symbol = rule.charAt(index);
        if (symbol == '#') {
            for (String word : dictionary) {
                currentPassword[index] = word;
                computePasswords(dictionary, rule, outputWriter, digits, currentPassword, index + 1);
            }
        } else {
            for (String digit : digits) {
                currentPassword[index] = digit;
                computePasswords(dictionary, rule, outputWriter, digits, currentPassword, index + 1);
            }
        }
    }

    private static String[] getDigits() {
        String[] digits = new String[10];
        for (int digit = 0; digit <= 9; digit++) {
            digits[digit] = String.valueOf(digit);
        }
        return digits;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
