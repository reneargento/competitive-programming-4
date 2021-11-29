package chapter3.section2.e.iterative.permutation;

import java.io.*;

/**
 * Created by Rene Argento on 29/11/21.
 */
public class IDCodes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String code = FastReader.getLine();
        while (!code.equals("#")) {
            char[] nextPermutation = nextPermutation(code.toCharArray());
            if (nextPermutation == null) {
                outputWriter.printLine("No Successor");
            } else {
                outputWriter.printLine(new String(nextPermutation));
            }
            code = FastReader.getLine();
        }
        outputWriter.flush();
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

    // Finds the largest k, that permutation[k] < permutation[k + 1]
    // If no such k exists (there is not greater permutation), return - 1
    private static int getFirstIndexToSwap(char[] permutation ) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] < permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    // Swaps two elements (with indexes index1 and index2) in array
    private static void swap(char[] permutation, int index1, int index2) {
        char temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
