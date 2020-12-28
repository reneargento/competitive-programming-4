package chapter1.section6.p.time.waster.problems.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/12/20.
 */
public class ChasingAfterDonGiovanni {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                System.out.println();
            }

            int villagersRow = FastReader.nextInt();
            int villagersColumn = FastReader.nextInt();
            int leporelloRow = FastReader.nextInt();
            int leporelloColumn = FastReader.nextInt();

            int leporelloStopsNumber = FastReader.nextInt();
            List<Cell> leporelloStops = new ArrayList<>();
            for (int i = 0; i < leporelloStopsNumber; i++) {
                leporelloStops.add(new Cell(FastReader.nextInt(), FastReader.nextInt()));
            }

            int villagersStopsNumber = FastReader.nextInt();
            List<Cell> villagersStops = new ArrayList<>();
            for (int i = 0; i < villagersStopsNumber; i++) {
                villagersStops.add(new Cell(FastReader.nextInt(), FastReader.nextInt()));
            }

            boolean isSafe = isSafe(leporelloRow, leporelloColumn, villagersRow, villagersColumn, leporelloStops, villagersStops);
            System.out.println(isSafe? "Yes" : "No");
            FastReader.getLine();
        }
    }

    private static boolean isSafe(int leporelloRow, int leporelloColumn, int villagersRow , int villagersColumn,
                                  List<Cell> leporelloStops, List<Cell> villagersStops) {
        if (leporelloRow == villagersRow
                && leporelloColumn == villagersColumn &&
                !(leporelloRow == leporelloStops.get(leporelloStops.size() - 1).row
                        && leporelloColumn == leporelloStops.get(leporelloStops.size() - 1).column)) {
            return false;
        }

        int leporelloStopIndex = 0;
        int villagersStopIndex = 0;
        int leporelloTargetRow;
        int leporelloTargetColumn;
        int villagersTargetRow;
        int villagersTargetColumn;
        boolean leporelloArrived = false;

        while (leporelloStopIndex < leporelloStops.size() && villagersStopIndex < villagersStops.size()) {
            leporelloTargetRow = leporelloStops.get(leporelloStopIndex).row;
            leporelloTargetColumn = leporelloStops.get(leporelloStopIndex).column;
            villagersTargetRow = villagersStops.get(villagersStopIndex).row;
            villagersTargetColumn = villagersStops.get(villagersStopIndex).column;

            if (leporelloRow == leporelloTargetRow && leporelloColumn == leporelloTargetColumn) {
                leporelloStopIndex++;
                if (leporelloStopIndex < leporelloStops.size()) {
                    leporelloTargetRow = leporelloStops.get(leporelloStopIndex).row;
                    leporelloTargetColumn = leporelloStops.get(leporelloStopIndex).column;
                }
            }
            if (villagersRow == villagersTargetRow && villagersColumn == villagersTargetColumn) {
                villagersStopIndex++;
                if (villagersStopIndex < villagersStops.size()) {
                    villagersTargetRow = villagersStops.get(villagersStopIndex).row;
                    villagersTargetColumn = villagersStops.get(villagersStopIndex).column;
                }
            }

            Cell leporelloNextCell = getNextCell(leporelloRow, leporelloColumn, leporelloTargetRow, leporelloTargetColumn);
            leporelloRow = leporelloNextCell.row;
            leporelloColumn = leporelloNextCell.column;

            if (leporelloRow == leporelloStops.get(leporelloStops.size() - 1).row
                    && leporelloColumn == leporelloStops.get(leporelloStops.size() - 1).column
                    && leporelloStopIndex == leporelloStops.size() - 1) {
                leporelloArrived = true;
            }

            Cell villagersNextCell = getNextCell(villagersRow, villagersColumn, villagersTargetRow, villagersTargetColumn);
            villagersRow = villagersNextCell.row;
            villagersColumn = villagersNextCell.column;

            if (leporelloRow == villagersRow && leporelloColumn == villagersColumn && !leporelloArrived) {
                return false;
            }
        }
        return true;
    }

    private static Cell getNextCell(int row, int column, int targetRow, int targetColumn) {
        int nextRow;
        int nextColumn;

        if (row == targetRow) {
            nextRow = row;
            if (targetColumn > column) {
                nextColumn = column + 1;
            } else {
                nextColumn = column - 1;
            }
        } else {
            nextColumn = column;
            if (targetRow > row) {
                nextRow = row + 1;
            } else {
                nextRow = row - 1;
            }
        }
        return new Cell(nextRow, nextColumn);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
