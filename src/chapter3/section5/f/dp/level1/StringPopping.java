package chapter3.section5.f.dp.level1;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/01/23.
 */
public class StringPopping {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.next();
            Map<String, Integer> dp = new HashMap<>();
            int minimumLength = computeMinimumLength(string, dp);
            outputWriter.printLine(minimumLength == 0 ? 1 : 0);
        }
        outputWriter.flush();
    }

    private static int computeMinimumLength(String string, Map<String, Integer> dp) {
        if (string.isEmpty()) {
            return 0;
        }
        if (dp.containsKey(string)) {
            return dp.get(string);
        }

        int minimumLength = Integer.MAX_VALUE;
        for (int i = 0; i < string.length(); i++) {
            int endGroupIndex = i;
            while (endGroupIndex < string.length() - 1 && string.charAt(endGroupIndex + 1) == string.charAt(i)) {
                endGroupIndex++;
            }

            if (i != endGroupIndex) {
                String nextString = string.substring(0, i) + string.substring(endGroupIndex + 1);
                int length = computeMinimumLength(nextString, dp);
                minimumLength = Math.min(minimumLength, length);
                i = endGroupIndex;
            }
        }
        dp.put(string, minimumLength);
        return minimumLength;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}

