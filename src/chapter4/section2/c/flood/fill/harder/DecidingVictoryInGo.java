package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/03/23.
 */
public class DecidingVictoryInGo {

    private static class GameResult {
        int blackPoints;
        int whitePoints;

        public GameResult(int blackPoints, int whitePoints) {
            this.blackPoints = blackPoints;
            this.whitePoints = whitePoints;
        }
    }

    private static class Sector {
        int points;
        boolean bordersBlack;
        boolean bordersWhite;

        public Sector(int points, boolean bordersBlack, boolean bordersWhite) {
            this.points = points;
            this.bordersBlack = bordersBlack;
            this.bordersWhite = bordersWhite;
        }
    }

    private static final char WHITE = 'O';
    private static final char BLACK = 'X';
    private static final char PROCESSED = '*';
    private static final char EMPTY = '.';

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            char[][] board = new char[9][9];
            for (int row = 0; row < board.length; row++) {
                board[row] = FastReader.next().toCharArray();
            }

            GameResult gameResult = computeGameResult(board, neighborRows, neighborColumns);
            outputWriter.printLine(String.format("Black %d White %d", gameResult.blackPoints, gameResult.whitePoints));
        }
        outputWriter.flush();
    }

    private static GameResult computeGameResult(char[][] board, int[] neighborRows, int[] neighborColumns) {
        int blackPoints = countStones(board, BLACK);
        int whitePoints = countStones(board, WHITE);

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == EMPTY) {
                    Sector sector = computeSector(board, neighborRows, neighborColumns, row, column);
                    if (sector.bordersBlack && !sector.bordersWhite) {
                        blackPoints += sector.points;
                    } else if (sector.bordersWhite && !sector.bordersBlack) {
                        whitePoints += sector.points;
                    }
                }
            }
        }
        return new GameResult(blackPoints, whitePoints);
    }

    private static Sector computeSector(char[][] board, int[] neighborRows, int[] neighborColumns, int row, int column) {
        Sector sector = new Sector(1, false, false);
        board[row][column] = PROCESSED;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn)) {
                if (board[neighborRow][neighborColumn] == EMPTY) {
                    Sector nextSector = computeSector(board, neighborRows, neighborColumns, neighborRow, neighborColumn);
                    sector.points += nextSector.points;
                    sector.bordersBlack |= nextSector.bordersBlack;
                    sector.bordersWhite |= nextSector.bordersWhite;
                } else if (board[neighborRow][neighborColumn] == BLACK) {
                    sector.bordersBlack = true;
                } else if (board[neighborRow][neighborColumn] == WHITE) {
                    sector.bordersWhite = true;
                }
            }

            if (sector.bordersWhite && sector.bordersBlack) {
                break;
            }
        }
        return sector;
    }

    private static int countStones(char[][] board, char stone) {
        int stones = 0;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == stone) {
                    stones++;
                }
            }
        }
        return stones;
    }

    private static boolean isValid(char[][] board, int row, int column) {
        return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
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
