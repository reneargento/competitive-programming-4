package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/08/22.
 */
public class AlienDNA {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] baseSets = new String[FastReader.nextInt()];
            for (int i = 0; i < baseSets.length; i++) {
                baseSets[i] = FastReader.next();
            }

            int minimumCuts = computeMinimumCuts(baseSets);
            outputWriter.printLine(minimumCuts);
        }
        outputWriter.flush();
    }

    private static int computeMinimumCuts(String[] baseSets) {
        int minimumCuts = 0;
        Set<Character> currentBaseSet = getBases(baseSets[0]);

        for (int i = 1; i < baseSets.length; i++) {
            Set<Character> intersection = getBaseSetIntersection(currentBaseSet, baseSets[i]);
            if (intersection.isEmpty()) {
                minimumCuts++;
                currentBaseSet = getBases(baseSets[i]);
            } else {
                currentBaseSet = intersection;
            }
        }
        return minimumCuts;
    }

    private static Set<Character> getBases(String baseSet) {
        Set<Character> baseSetLetters = new HashSet<>();
        for (char letter : baseSet.toCharArray()) {
            baseSetLetters.add(letter);
        }
        return baseSetLetters;
    }

    private static Set<Character> getBaseSetIntersection(Set<Character> currentBaseSet, String baseSet) {
        Set<Character> intersection = new HashSet<>();

        for (char letter : baseSet.toCharArray()) {
            if (currentBaseSet.contains(letter)) {
                intersection.add(letter);
            }
        }
        return intersection;
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
