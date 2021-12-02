package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 01/12/21.
 */
public class DigTheHoles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        List<String> permutations = generatePermutations();

        for (int t = 0; t < tests; t++) {
            String[] guesses = new String[2];
            int[] correct = new int[2];
            int[] wrongPosition = new int[2];

            for (int i = 0; i < guesses.length; i++) {
                guesses[i] = FastReader.next();
                correct[i] = FastReader.nextInt();
                wrongPosition[i] = FastReader.nextInt();
            }
            boolean isPossible = isPossible(permutations, guesses, correct, wrongPosition);
            outputWriter.printLine(isPossible ? "Possible" : "Cheat");
        }
        outputWriter.flush();
    }

    private static List<String> generatePermutations() {
        char[] colors = { 'R', 'G', 'B', 'Y', 'O', 'V' };
        char[] chosenColors = new char[4];
        List<String> permutations = new ArrayList<>();
        generatePermutations(colors, chosenColors, 0, 0, permutations);
        return permutations;
    }

    private static void generatePermutations(char[] colors, char[] chosenColors, int index, int mask,
                                             List<String> permutations) {
        if (index == 4) {
            String permutation = new String(chosenColors);
            permutations.add(permutation);
            return;
        }

        for (int i = 0; i < colors.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                chosenColors[index] = colors[i];
                generatePermutations(colors, chosenColors, index + 1, newMask, permutations);
            }
        }
    }

    private static boolean isPossible(List<String> permutations, String[] guesses, int[] correct,
                                      int[] wrongPosition) {
        for (String permutation : permutations) {
            boolean possible = true;

            for (int i = 0; i < guesses.length; i++) {
                String guess = guesses[i];
                int correctGuesses = 0;
                int wrongPositionsGuesses = 0;

                for (int c = 0; c < guess.length(); c++) {
                    if (guess.charAt(c) == permutation.charAt(c)) {
                        correctGuesses++;
                    } else {
                        for (int c2 = 0; c2 < guess.length(); c2++) {
                            if (guess.charAt(c) == permutation.charAt(c2)) {
                                wrongPositionsGuesses++;
                                break;
                            }
                        }
                    }
                }
                if (correctGuesses != correct[i] || wrongPositionsGuesses != wrongPosition[i]) {
                    possible = false;
                    break;
                }
            }
            if (possible) {
                return true;
            }
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
