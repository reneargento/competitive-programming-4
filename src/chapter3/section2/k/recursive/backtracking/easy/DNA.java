package chapter3.section2.k.recursive.backtracking.easy;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/02/22.
 */
public class DNA {

    private static final char[] elements = { 'A', 'C', 'T', 'G' };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            FastReader.nextInt(); // unused
            int targetDistance = FastReader.nextInt();
            char[] dna = FastReader.next().toCharArray();

            Set<String> mutations = new HashSet<>();
            computeMutations(dna, targetDistance, mutations, 0, 0);

            List<String> mutationsList = new ArrayList<>(mutations);
            Collections.sort(mutationsList);

            outputWriter.printLine(mutationsList.size());
            for (String mutation : mutationsList) {
                outputWriter.printLine(mutation);
            }
        }
        outputWriter.flush();
    }

    private static void computeMutations(char[] dna, int targetDistance, Set<String> mutations,
                                         int distance, int mask) {
        if (distance == targetDistance) {
            mutations.add(new String(dna));
            return;
        }

        for (int i = 0; i < dna.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                char currentElement = dna[i];

                for (char element : elements) {
                    dna[i] = element;
                    computeMutations(dna, targetDistance, mutations, distance + 1, newMask);
                }
                dna[i] = currentElement;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
