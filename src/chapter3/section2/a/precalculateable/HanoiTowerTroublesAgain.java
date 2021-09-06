package chapter3.section2.a.precalculateable;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/09/21.
 */
public class HanoiTowerTroublesAgain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] ballsPlaced = precomputeBalls();

        for (int t = 0; t < tests; t++) {
            int pegs = FastReader.nextInt();
            outputWriter.printLine(ballsPlaced[pegs]);
        }
        outputWriter.flush();
    }

    private static long[] precomputeBalls() {
        long[] ballsPlaced = new long[51];
        Set<Integer> squareNumbers = computeSquareNumbers();

        for (int pegs = 1; pegs <= 50; pegs++) {
            ballsPlaced[pegs] = placeBalls(pegs, squareNumbers);
        }
        return ballsPlaced;
    }

    private static long placeBalls(int pegs, Set<Integer> squareNumbers) {
        long ballsPlaced = 0;
        int currentBall = 1;
        int[] ballsInPegs = new int[pegs];

        while (true) {
            boolean placed = false;

            for (int i = 0; i < ballsInPegs.length; i++) {
                int sum = ballsInPegs[i] + currentBall;

                if (ballsInPegs[i] == 0 || squareNumbers.contains(sum)) {
                    ballsInPegs[i] = currentBall;
                    ballsPlaced++;
                    currentBall++;
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                break;
            }
        }
        return ballsPlaced;
    }

    private static Set<Integer> computeSquareNumbers() {
        Set<Integer> squareNumbers = new HashSet<>();
        for (int number = 1; number <= 1000; number++) {
            squareNumbers.add(number * number);
        }
        return squareNumbers;
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
