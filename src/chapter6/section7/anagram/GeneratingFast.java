package chapter6.section7.anagram;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/10/2026.
 */
public class GeneratingFast {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String string = FastReader.next();
            printAllNextPermutations(string, outputWriter);
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void printAllNextPermutations(String string, OutputWriter outputWriter) {
        char[] permutation = string.toCharArray();
        Arrays.sort(permutation);
        outputWriter.printLine(String.valueOf(permutation));

        while ((permutation = nextPermutation(permutation)) != null ) {
            outputWriter.printLine(String.valueOf(permutation));
        }
    }

    private static char[] nextPermutation(char[] permutation) {
        // 1. Find the largest k, such that permutation[k] < permutation[k + 1]
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no greater permutation
        }

        // 2. Find the last index toSwap, that permutation[k] < permutation[toSwap]
        int toSwap = permutation.length - 1;
        while (permutation[first] >= permutation[toSwap]) {
            toSwap--;
        }

        // 3. Swap elements with indexes first and toSwap
        swap(permutation, first++, toSwap);

        // 4. Reverse sequence from k + 1 to n (inclusive)
        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    private static int getFirstIndexToSwap(char[] permutation) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] < permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    private static void swap(char[] permutation, int index1, int index2) {
        char temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
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