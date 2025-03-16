package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/03/25.
 */
public class SumOfProduct {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int n = FastReader.nextInt();
        int caseNumber = 1;

        while (n != 0) {
            int sop = computeSop(n);
            outputWriter.printLine(String.format("Case #%d: %d", caseNumber, sop));

            caseNumber++;
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSop(int n) {
        if (n <= 6) {
            List<List<Integer>> permutations = generateAllPermutations(n);
            return computeSopWithPermutations(permutations);
        } else {
            // Based on https://oeis.org/A007773
            // For n >= 7, a(n) = (n^3 - 16 * n + 27)/6 (n odd); (n^3 - 16 * n + 30)/6 (n even)
            int sumConstant;
            if (n % 2 == 1) {
                sumConstant = 27;
            } else {
                sumConstant = 30;
            }
            return (int) (Math.pow(n, 3) - 16 * n + sumConstant) / 6;
        }
    }

    private static int computeSopWithPermutations(List<List<Integer>> permutations) {
        Set<Integer> sop = new HashSet<>();

        for (List<Integer> permutation : permutations) {
            int sum = 0;

            for (int i = 0; i < permutation.size(); i++) {
                int next = i + 1;
                next %= permutation.size();
                sum += permutation.get(i) * permutation.get(next);
            }
            sop.add(sum);
        }
        return sop.size();
    }

    private static List<List<Integer>> generateAllPermutations(int numbers) {
        Integer[] numbersArray = new Integer[numbers];
        List<List<Integer>> permutations = new ArrayList<>();
        generateAllPermutations(numbersArray, 0, 0, permutations);
        return permutations;
    }

    private static void generateAllPermutations(Integer[] numbers, int index, int mask,
                                                List<List<Integer>> permutations) {
        int length = numbers.length;
        if (mask == (1 << length) - 1) {
            List<Integer> permutation = new ArrayList<>(Arrays.asList(numbers));
            permutations.add(permutation);
            return;
        }

        for (int i = 0; i < numbers.length; i++) {
            if ((mask & (1 << i)) == 0) {
                numbers[index] = i;
                int nextMask = mask | (1 << i);
                generateAllPermutations(numbers, index + 1, nextMask, permutations);
            }
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
