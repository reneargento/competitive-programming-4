package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/11/21.
 */
public class AntsShoppingMall {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();

            String[][] antWorld = new String[rows][columns];
            for (int r = 0; r < rows; r++) {
                antWorld[r] = FastReader.next().split("");
            }

            int moves = computeMoves(antWorld);
            outputWriter.printLine(String.format("Case %d: %d", t, moves));
        }
        outputWriter.flush();
    }

    private static int computeMoves(String[][] antWorld) {
        boolean possible = false;
        int bestMoves = Integer.MAX_VALUE;

        for (int column = 0; column < antWorld[0].length; column++) {
            int moves = computeMovesInColumn(antWorld, column);
            if (moves != -1) {
                possible = true;
                bestMoves = Math.min(bestMoves, moves);
            }
        }

        if (possible) {
            return bestMoves;
        }
        return -1;
    }

    private static int computeMovesInColumn(String[][] antWorld, int column) {
        int moves = 0;

        for (int row = 0; row < antWorld.length; row++) {
            if (antWorld[row][column].equals("0")) {
                continue;
            }

            int movesLeft = 1;
            boolean isPossibleLeft = false;

            for (int leftColumn = column - 1; leftColumn >= 0; leftColumn--) {
                if (antWorld[row][leftColumn].equals("1")) {
                    movesLeft++;
                } else {
                    isPossibleLeft = true;
                    break;
                }
            }

            int movesRight = 1;
            boolean isPossibleRight = false;

            for (int rightColumn = column + 1; rightColumn < antWorld[row].length; rightColumn++) {
                if (antWorld[row][rightColumn].equals("1")) {
                    movesRight++;
                } else {
                    isPossibleRight = true;
                    break;
                }
            }

            if (isPossibleLeft && isPossibleRight) {
                moves += Math.min(movesLeft, movesRight);
            } else if (isPossibleLeft) {
                moves += movesLeft;
            } else if (isPossibleRight) {
                moves += movesRight;
            } else {
                return -1;
            }
        }
        return moves;
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
