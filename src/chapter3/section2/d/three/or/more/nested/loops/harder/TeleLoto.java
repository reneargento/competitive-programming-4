package chapter3.section2.d.three.or.more.nested.loops.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/11/21.
 */
public class TeleLoto {

    private static class Ticket {
        Map<Integer, Cell> numbersMap;

        Ticket(Map<Integer, Cell> numbersMap) {
            this.numbersMap = numbersMap;
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            if (t > 1) {
                outputWriter.printLine();
            }

            int balls = FastReader.nextInt();
            int ticketsNumber = FastReader.nextInt();

            int[] selectedBalls = new int[balls];
            for (int i = 0; i < selectedBalls.length; i++) {
                selectedBalls[i] = FastReader.nextInt();
            }

            int[] prizes = new int[4];
            for (int i = 0; i < prizes.length; i++) {
                prizes[i] = FastReader.nextInt();
            }

            outputWriter.printLine(String.format("Case %d:", t));

            for (int i = 0; i < ticketsNumber; i++) {
                Map<Integer, Cell> numbersMap = new HashMap<>();

                for (int row = 0; row < 5; row++) {
                    for (int column = 0; column < 5; column++) {
                        int number = FastReader.nextInt();
                        numbersMap.put(number, new Cell(row, column));
                    }
                }

                Ticket ticket = new Ticket(numbersMap);
                long moneyWon = computeMoneyWon(selectedBalls, prizes, ticket);
                outputWriter.printLine(moneyWon);
            }
        }
        outputWriter.flush();
    }

    private static int computeMoneyWon(int[] selectedBalls, int[] prizes, Ticket ticket) {
        int moneyWon = 0;
        boolean[] valueAdded = new boolean[prizes.length];

        Set<Cell> cornerBalls = new HashSet<>();
        Set<Cell> midLineBalls = new HashSet<>();
        Set<Cell> diagonalBalls = new HashSet<>();
        Set<Cell> matchedBalls = new HashSet<>();

        for (int i = 0; i < selectedBalls.length; i++) {
            int selectedBall = selectedBalls[i];

            if (ticket.numbersMap.containsKey(selectedBall)) {
                Cell cell = ticket.numbersMap.get(selectedBall);

                if (isCorner(cell.row, cell.column)) {
                    cornerBalls.add(cell);
                }
                if (isMidline(cell.row)) {
                    midLineBalls.add(cell);
                }
                if (isLeftDiagonal(cell.row, cell.column) || isRightDiagonal(cell.row, cell.column)) {
                    diagonalBalls.add(cell);
                }
                matchedBalls.add(cell);
            }

            if (cornerBalls.size() == 4 && !valueAdded[0] && i < 35) {
                moneyWon += prizes[0];
                valueAdded[0] = true;
            }
            if (midLineBalls.size() == 5 && !valueAdded[1] && i < 40) {
                moneyWon += prizes[1];
                valueAdded[1] = true;
            }
            if (diagonalBalls.size() == 9 && !valueAdded[2] && i < 45) {
                moneyWon += prizes[2];
                valueAdded[2] = true;
            }
            if (matchedBalls.size() == 25 && !valueAdded[3]) {
                moneyWon += prizes[3];
                valueAdded[3] = true;
            }
        }
        return moneyWon;
    }

    private static boolean isCorner(int row, int column) {
        return (row == 0 && (column == 0 || column == 4))
                || (row == 4 && (column == 0 || column == 4));
    }

    private static boolean isMidline(int row) {
        return row == 2;
    }

    private static boolean isLeftDiagonal(int row, int column) {
        return row == column;
    }

    private static boolean isRightDiagonal(int row, int column) {
        return row + column == 4;
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
