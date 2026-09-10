package chapter6.section5;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/09/2026.
 */
public class SuffixArrayReconstruction {

    private static class Suffix {
        int start;
        String value;

        public Suffix(int start, String value) {
            this.start = start;
            this.value = value;
        }
    }

    private static final String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int stringLength = FastReader.nextInt();
            Suffix[] suffixes = new Suffix[FastReader.nextInt()];
            for (int i = 0; i < suffixes.length; i++) {
                suffixes[i] = new Suffix(FastReader.nextInt(), FastReader.next());
            }

            String result = reconstructSuffixArray(stringLength, suffixes);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String reconstructSuffixArray(int stringLength, Suffix[] suffixes) {
        char[] originalString = new char[stringLength];
        Arrays.fill(originalString, '?');

        for (Suffix suffix : suffixes) {
            boolean hasWildcard = suffix.value.contains("*");
            if (hasWildcard) {
                int wildcardIndex = suffix.value.indexOf("*");
                boolean result = reconstructSuffixArray(originalString, suffix, wildcardIndex);
                if (!result) {
                    return IMPOSSIBLE;
                }

                int stringIndex = originalString.length - 1;
                for (int i = suffix.value.length() - 1; i > wildcardIndex; i--, stringIndex--) {
                    char value = suffix.value.charAt(i);

                    if (originalString[stringIndex] != '?'
                            && originalString[stringIndex] != value) {
                        return IMPOSSIBLE;
                    }
                    originalString[stringIndex] = value;
                }
            } else {
                boolean result = reconstructSuffixArray(originalString, suffix, suffix.value.length());
                if (!result) {
                    return IMPOSSIBLE;
                }
            }
        }

        String completeString = String.valueOf(originalString);
        if (completeString.contains("?")) {
            return IMPOSSIBLE;
        }
        return completeString;
    }

    private static boolean reconstructSuffixArray(char[] originalString, Suffix suffix, int length) {
        int startIndex = suffix.start - 1;

        for (int i = 0; i < length; i++) {
            char value = suffix.value.charAt(i);
            if (originalString[startIndex + i] != '?'
                    && originalString[startIndex + i] != value) {
                return false;
            }
            originalString[startIndex + i] = value;
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