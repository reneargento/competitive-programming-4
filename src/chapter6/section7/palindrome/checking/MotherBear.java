package chapter6.section7.palindrome.checking;

import java.io.*;

/**
 * Created by Rene Argento on 19/10/2026.
 */
public class MotherBear {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String sentence = FastReader.getLine();
        while (!sentence.equals("DONE")) {
            if (isPalindrome(sentence)) {
                outputWriter.printLine("You won't be eaten!");
            } else {
                outputWriter.printLine("Uh oh..");
            }
            sentence = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isPalindrome(String sentence) {
        String clearSentence = sentence.toLowerCase().replaceAll("[^a-z]","");
        for (int i = 0; i < clearSentence.length() / 2; i++) {
            if (clearSentence.charAt(i) != clearSentence.charAt(clearSentence.length() - 1 - i)) {
                return false;
            }
        }
        return true;
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