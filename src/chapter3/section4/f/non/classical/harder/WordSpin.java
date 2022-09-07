package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/08/22.
 */
public class WordSpin {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String string1 = FastReader.next();
        String string2 = FastReader.next();

        int moves = countMoves(string1, string2);
        outputWriter.printLine(moves);
        outputWriter.flush();
    }

    private static int countMoves(String string1, String string2) {
        int moves = 0;
        for (int i = 0; i < string1.length(); i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                continue;
            }
            boolean isBefore = string1.charAt(i) < string2.charAt(i);
            int substringDifference = Math.abs(string1.charAt(i) - string2.charAt(i));
            moves += substringDifference;

            for (int j = i + 1; j < string1.length(); j++) {
                if ((isBefore && string1.charAt(j) < string2.charAt(j))
                        || (!isBefore && string1.charAt(j) > string2.charAt(j))) {
                    int nextSubstringDifference = Math.abs(string1.charAt(j) - string2.charAt(j));
                    moves += Math.max(0, nextSubstringDifference - substringDifference);
                    substringDifference = nextSubstringDifference;
                    i = j;
                } else {
                    break;
                }
            }
        }
        return moves;
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