package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 30/11/21.
 */
public class Wordfish {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String username = FastReader.getLine();

        while (username != null) {
            String password = computePassword(username);
            outputWriter.printLine(password);
            username = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static String computePassword(String username) {
        String[] permutations = new String[21];
        permutations[10] = username;

        for (int i = 10; i > 0; i--) {
            char[] permutation = previousPermutation(permutations[i].toCharArray());
            permutations[i - 1] = new String(permutation);
        }

        for (int i = 11; i < permutations.length; i++) {
            char[] permutation = nextPermutation(permutations[i - 1].toCharArray());
            permutations[i] = new String(permutation);
        }

        int maxAbsoluteDistance = 0;
        List<String> permutationsWithMinDistance = new ArrayList<>();

        for (String permutation : permutations) {
            int distance = computeMinAbsoluteDistance(permutation);

            if (distance > maxAbsoluteDistance) {
                maxAbsoluteDistance = distance;
                permutationsWithMinDistance = new ArrayList<>();
                permutationsWithMinDistance.add(permutation);
            } else if (distance == maxAbsoluteDistance) {
                permutationsWithMinDistance.add(permutation);
            }
        }

        Collections.sort(permutationsWithMinDistance);
        return permutationsWithMinDistance.get(0) + maxAbsoluteDistance;
    }

    private static int computeMinAbsoluteDistance(String permutation) {
        int minAbsoluteDistance = Integer.MAX_VALUE;

        for (int i = 0; i < permutation.length() - 1; i++) {
            int distance = Math.abs(permutation.charAt(i) - permutation.charAt(i + 1));
            minAbsoluteDistance = Math.min(minAbsoluteDistance, distance);
        }
        return minAbsoluteDistance;
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
    // If no such k exists (there is not greater permutation), return -1
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

    private static char[] previousPermutation(char[] permutation) {
        // 1. Find the largest k, such that permutation[k] > permutation[k + 1]
        int first = getFirstIndexToSwapForPreviousPermutation(permutation);
        if (first == -1) {
            return null; // no smaller permutation
        }

        // 2. Find the last index toSwap, that permutation[k] > permutation[toSwap]
        int toSwap = permutation.length - 1;
        while (permutation[first] <= permutation[toSwap]) {
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

    // Finds the largest k, that permutation[k] > permutation[k + 1]
    // If no such k exists (there is not smaller permutation), return -1
    private static int getFirstIndexToSwapForPreviousPermutation(char[] permutation ) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] > permutation[i + 1]) {
                return i;
            }
        }
        return -1;
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
