package chapter5.section4.b.binomial.coefficients;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/12/25.
 */
public class Election {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int voters = FastReader.nextInt();
            int votesCandidate1 = FastReader.nextInt();
            int votesCandidate2 = FastReader.nextInt();
            int threshold = FastReader.nextInt();

            String result = computeOdds(voters, votesCandidate1, votesCandidate2, threshold);
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static String computeOdds(int voters, int votesCandidate1, int votesCandidate2, int threshold) {
        int votesLeft = voters - (votesCandidate1 + votesCandidate2);
        int votesNeeded = (voters / 2) + 1;

        if (votesNeeded <= votesCandidate1) {
            return "GET A CRATE OF CHAMPAGNE FROM THE BASEMENT!";
        } else if (votesNeeded - votesCandidate1 > votesLeft) {
            return "RECOUNT!";
        } else {
            long targetCombinations = cumulativeBinomialCoefficientKOrAbove(votesLeft, votesNeeded - votesCandidate1);
            double allCombinations = Math.pow(2, votesLeft);
            double probability = (targetCombinations / allCombinations) * 100;

            if (probability > threshold) {
                return "GET A CRATE OF CHAMPAGNE FROM THE BASEMENT!";
            } else {
                return "PATIENCE, EVERYONE!";
            }
        }
    }

    // Compute C(n, k) + C(n, k+1) + C(n, k+2), ... + C(n, n)
    private static long cumulativeBinomialCoefficientKOrAbove(int totalNumbers, int numbersToChoose) {
        if (numbersToChoose <= 0) {
            return 1L << totalNumbers;  // 2^n
        }
        if (numbersToChoose > totalNumbers) {
            return 0;
        }

        long sum = 0;
        long binomialCoefficient = 1;
        for (int i = 0; i < totalNumbers; i++) {
            binomialCoefficient = binomialCoefficient * (totalNumbers - i) / (i + 1);

            if (i >= numbersToChoose - 1) {
                sum += binomialCoefficient;
            }
        }
        return sum;
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
