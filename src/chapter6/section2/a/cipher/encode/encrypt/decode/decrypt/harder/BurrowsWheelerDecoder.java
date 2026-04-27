package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class BurrowsWheelerDecoder {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String lastColumn = FastReader.getLine();
        int inputRow = FastReader.nextInt();

        int testCase = 1;
        while (inputRow != 0) {
            if (testCase > 1) {
                outputWriter.printLine();
            }
            String decodedMessage = decode(lastColumn, inputRow - 1);
            outputWriter.printLine(decodedMessage);

            testCase++;
            lastColumn = FastReader.getLine();
            inputRow = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    @SuppressWarnings("unchecked")
    private static String decode(String lastColumn, int inputRow) {
        char[] sortedCharacters = lastColumn.toCharArray();
        Arrays.sort(sortedCharacters);
        int[] rowShift = new int[lastColumn.length()];

        Deque<Integer>[] positionsInLastColumn = new Deque[128];
        for (int i = 0; i < positionsInLastColumn.length; i++) {
            positionsInLastColumn[i] = new ArrayDeque<>();
        }
        for (int i = 0; i < lastColumn.length(); i++) {
            char character = lastColumn.charAt(i);
            positionsInLastColumn[character].add(i);
        }

        for (int i = 0; i < sortedCharacters.length; i++) {
            char character = sortedCharacters[i];
            rowShift[i] = positionsInLastColumn[character].pop();
        }

        char[] decodedMessage = new char[sortedCharacters.length];
        for (int i = 0; i < rowShift.length; i++) {
            inputRow = rowShift[inputRow];
            decodedMessage[i] = lastColumn.charAt(inputRow);
        }
        return new String(decodedMessage);
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
