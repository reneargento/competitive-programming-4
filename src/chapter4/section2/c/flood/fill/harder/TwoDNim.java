package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 18/03/23.
 */
public class TwoDNim {

    private static class Piece implements Comparable<Piece> {
        int row;
        int column;

        public Piece(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int compareTo(Piece other) {
            if (row != other.row) {
                return Integer.compare(row, other.row);
            }
            return Integer.compare(column, other.column);
        }


        @Override
        public boolean equals(Object object) {
            Piece otherPiece = (Piece) object;
            return row == otherPiece.row && column == otherPiece.column;
        }
    }

    private static class ComponentFormat {
        List<Piece> pieces;

        public ComponentFormat() {
            pieces = new ArrayList<>();
        }

        public ComponentFormat(List<Piece> pieces) {
            this.pieces = new ArrayList<>();
            for (Piece piece : pieces) {
                this.pieces.add(new Piece(piece.row, piece.column));
            }
        }

        void adjustValues() {
            Collections.sort(pieces);
            adjustOffsets();
        }

        private void adjustOffsets() {
            if (pieces.get(0).row < 0) {
                int offset = pieces.get(0).row * -1;
                for (int i = 0; i < pieces.size(); i++) {
                    pieces.get(i).row += offset;
                }
            }

            List<Integer> columns = new ArrayList<>();
            for (Piece piece : pieces) {
                columns.add(piece.column);
            }
            Collections.sort(columns);

            if (columns.get(0) < 0) {
                int offset = columns.get(0) * -1;
                for (int i = 0; i < pieces.size(); i++) {
                    pieces.get(i).column += offset;
                }
            }
        }

        boolean isTheSame(ComponentFormat otherComponent) {
            if (pieces.size() != otherComponent.pieces.size()) {
                return false;
            }

            List<Piece> reversePieces = new ArrayList<>();
            for (Piece piece : pieces) {
                reversePieces.add(new Piece(piece.column, piece.row));
            }
            Collections.sort(reversePieces);
            return isTheSameFormat(pieces, otherComponent)
                    || isTheSameFormat(reversePieces, otherComponent);
        }

        private boolean isTheSameFormat(List<Piece> pieces, ComponentFormat otherComponent) {
            ComponentFormat componentFormat1 = new ComponentFormat(pieces);
            if (isTheSame(componentFormat1, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat2 = new ComponentFormat(pieces);
            componentFormat2.reverseValues(true);
            if (isTheSame(componentFormat2, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat3 = new ComponentFormat(pieces);
            componentFormat3.reverseValues(false);
            if (isTheSame(componentFormat3, otherComponent)) {
                return true;
            }

            ComponentFormat componentFormat4 = new ComponentFormat(pieces);
            componentFormat4.reverseValues(true);
            componentFormat4.reverseValues(false);
            return isTheSame(componentFormat4, otherComponent);
        }

        private void reverseValues(boolean reverseRows) {
            if (!reverseRows) {
                Collections.sort(pieces, new Comparator<Piece>() {
                    @Override
                    public int compare(Piece piece1, Piece piece2) {
                        return Integer.compare(piece1.column, piece2.column);
                    }
                });
            }

            List<Integer> reversedValues = new ArrayList<>();
            int currentReversedValue = 0;

            for (int i = pieces.size() - 1; i >= 0; i--) {
                reversedValues.add(currentReversedValue);

                if (i > 0) {
                    if (reverseRows) {
                        if (pieces.get(i).row != pieces.get(i - 1).row) {
                            currentReversedValue++;
                        }
                    } else {
                        if (pieces.get(i).column != pieces.get(i - 1).column) {
                            currentReversedValue++;
                        }
                    }
                }
            }

            int reverseValueIndex = 0;
            for (int i = pieces.size() - 1; i >= 0; i--) {
                if (reverseRows) {
                    pieces.get(i).row = reversedValues.get(reverseValueIndex);
                } else {
                    pieces.get(i).column = reversedValues.get(reverseValueIndex);
                }
                reverseValueIndex++;
            }
            Collections.sort(pieces);
        }

        private boolean isTheSame(ComponentFormat componentFormat1, ComponentFormat componentFormat2) {
            for (int i = 0; i < componentFormat1.pieces.size(); i++) {
                if (!componentFormat1.pieces.get(i).equals(componentFormat2.pieces.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        for (int t = 0; t < tests; t++) {
            int columns = FastReader.nextInt();
            int rows = FastReader.nextInt();
            int pieces = FastReader.nextInt();

            boolean[][] board1 = readBoard(rows, columns, pieces);
            boolean[][] board2 = readBoard(rows, columns, pieces);

            boolean isEquivalent = isEquivalent(board1, board2, neighborRows, neighborColumns);
            outputWriter.printLine(isEquivalent ? "YES" : "NO");
        }
        outputWriter.flush();
    }

    private static boolean[][] readBoard(int rows, int columns, int pieces) throws IOException {
        boolean[][] board = new boolean[rows][columns];

        for (int i = 0; i < pieces; i++) {
            int column = FastReader.nextInt();
            int row = FastReader.nextInt();
            board[row][column] = true;
        }
        return board;
    }

    private static boolean isEquivalent(boolean[][] board1, boolean[][] board2, int[] neighborRows,
                                        int[] neighborColumns) {
        List<ComponentFormat> components1 = computeComponents(board1, neighborRows, neighborColumns);
        List<ComponentFormat> components2 = computeComponents(board2, neighborRows, neighborColumns);

        if (components1.size() != components2.size()) {
            return false;
        }

        Set<Integer> componentIDsMatched = new HashSet<>();
        for (int componentID1 = 0; componentID1 < components1.size(); componentID1++) {
            boolean matched = false;

            for (int componentID2 = 0; componentID2 < components2.size(); componentID2++) {
                if (componentIDsMatched.contains(componentID2)) {
                    continue;
                }

                if (components1.get(componentID1).isTheSame(components2.get(componentID2))) {
                    matched = true;
                    componentIDsMatched.add(componentID2);
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }
        return true;
    }

    private static List<ComponentFormat> computeComponents(boolean[][] board, int[] neighborRows, int[] neighborColumns) {
        List<ComponentFormat> components = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column]) {
                    ComponentFormat componentFormat = new ComponentFormat();
                    floodFill(board, neighborRows, neighborColumns, componentFormat, row, column, row, column);
                    componentFormat.adjustValues();
                    components.add(componentFormat);
                }
            }
        }
        return components;
    }

    private static void floodFill(boolean[][] board, int[] neighborRows, int[] neighborColumns,
                                 ComponentFormat componentFormat, int originRow, int originColumn, int row,
                                 int column) {
        int rowFormatValue = row - originRow;
        int columnFormatValue = column - originColumn;
        componentFormat.pieces.add(new Piece(rowFormatValue, columnFormatValue));

        board[row][column] = false;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];

            if (isValid(board, neighborRow, neighborColumn) && board[neighborRow][neighborColumn]) {
                floodFill(board, neighborRows, neighborColumns, componentFormat, originRow, originColumn, neighborRow,
                        neighborColumn);
            }
        }
    }

    private static boolean isValid(boolean[][] board, int row, int column) {
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
