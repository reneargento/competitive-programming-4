package chapter6.section2.a.cipher.encode.encrypt.decode.decrypt.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/04/26.
 */
public class CompressionII {

    private static class BurrowsWheelerTransform {
        String[] strings;
        String transformedString;
        int indexOfString1;

        BurrowsWheelerTransform(String string) {
            generateStrings(string);
        }

        private void generateStrings(String string) {
            int length = string.length();
            strings = new String[length];
            String string1 = null;

            for (int i = 0; i < length; i++) {
                strings[i] = string;
                if (i == 1) {
                    string1 = string;
                }
                string = string.substring(1) + string.charAt(0);
            }
            Arrays.sort(strings);
            indexOfString1 = Arrays.binarySearch(strings, string1);
            getTransformedString();
        }

        private void getTransformedString() {
            StringBuilder transform = new StringBuilder();
            int length = strings[0].length();

            for (String string : strings) {
                transform.append(string.charAt(length - 1));
            }
            transformedString = transform.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.getLine();
            int length = FastReader.nextInt();
            String string = readString(length);

            BurrowsWheelerTransform burrowsWheelerTransform = new BurrowsWheelerTransform(string);
            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(burrowsWheelerTransform.indexOfString1);
            printString(outputWriter, burrowsWheelerTransform.transformedString);
        }
        outputWriter.flush();
    }

    private static String readString(int length) throws IOException {
        StringBuilder string = new StringBuilder();
        while (string.length() < length) {
            string.append(FastReader.getLine());
        }
        return string.toString();
    }

    private static void printString(OutputWriter outputWriter, String string) {
        int printedCharacters = 0;
        while (printedCharacters < string.length()) {
            for (int i = 0; i < 50 && printedCharacters < string.length(); i++) {
                outputWriter.print(string.charAt(printedCharacters));
                printedCharacters++;
            }
            outputWriter.printLine();
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
