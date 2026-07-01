package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/07/2026.
 */
public class IRepeatMyselfIRepeatMyselfIRepeat {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.getLine();
            int shortestPatternLength = computeShortestPatternLength(string);
            outputWriter.printLine(shortestPatternLength);
        }
        outputWriter.flush();
    }

    private static int computeShortestPatternLength(String string) {
        for (int i = 1; i <= string.length(); i++) {
            String substring = string.substring(0, i);
            boolean isCycle = true;

            for (int j = i; j < string.length(); j += i) {
                if (j + i > string.length()) {
                    String partialSubstring = string.substring(j);
                    if (substring.startsWith(partialSubstring)) {
                        return i;
                    } else {
                        isCycle = false;
                        break;
                    }
                }

                String candidateSubstring = string.substring(j, j + i);
                if (!substring.equals(candidateSubstring)) {
                    isCycle = false;
                    break;
                }
            }
            if (isCycle) {
                return i;
            }
        }
        return string.length();
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