package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/02/22.
 */
// Based on https://codeforces.com/blog/entry/54002
public class FindThePermutedString {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            char[] characters = FastReader.getLine().toCharArray();
            int index = FastReader.nextInt();
            String permutedString = getPermutedString(characters, index);
            outputWriter.printLine(permutedString);
        }
        outputWriter.flush();
    }

    private static String getPermutedString(char[] characters, int index) {
        StringBuilder permutedString = new StringBuilder();
        index--;

        for (int c = 0; c < characters.length; c++) {
            if (exceedsLength(c + 2, characters.length, index)) {
                permutedString.insert(0, characters[c]);
            } else {
                long multiplier = 1;
                for (int i = c + 2; i <= characters.length; i++) {
                    multiplier *= i;
                }
                long position = index / multiplier;
                permutedString.insert((int) position, characters[c]);
                index %= multiplier;
            }
        }
        return permutedString.toString();
    }

    private static boolean exceedsLength(int start, int end, int index) {
        long multiplier = 1;
        for (int i = start; i <= end; i++) {
            long limit = index / multiplier;
            if (i > limit) {
                return true;
            }
            multiplier *= i;
        }
        return false;
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
