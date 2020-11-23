package chapter1.section6.c.games.other.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 17/10/20.
 */
public class ConnectTheDots {

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
        List<List<Character>> image = new ArrayList<>();

        Map<Character, Cell> symbolsMap = new HashMap<>();
        int rowNumber = 0;
        int imageId = 0;

        String line = FastReader.getLine();

        while (line != null) {
            if (line.isEmpty()) {
                connectDots(image, symbolsMap);
                printImage(image, imageId);

                image = new ArrayList<>();
                symbolsMap = new HashMap<>();
                rowNumber = 0;
                imageId++;
                line = FastReader.getLine();
                continue;
            }

            List<Character> row = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char value = line.charAt(i);
                if (value != '.') {
                    symbolsMap.put(value, new Cell(rowNumber, i));
                }
                row.add(value);
            }
            image.add(row);
            rowNumber++;
            line = FastReader.getLine();
        }

        connectDots(image, symbolsMap);
        printImage(image, imageId);
    }

    private static void connectDots(List<List<Character>> image, Map<Character, Cell> symbolsMap) {
        int symbolIndex = 0;

        while (true) {
            char symbol = getSymbol(symbolIndex);
            if (!symbolsMap.containsKey(symbol)) {
                break;
            }
            Cell position1 = symbolsMap.get(symbol);

            symbolIndex++;
            char nextSymbol = getSymbol(symbolIndex);
            if (!symbolsMap.containsKey(nextSymbol)) {
                break;
            }
            Cell position2 = symbolsMap.get(nextSymbol);

            connect(image, position1, position2);
        }
    }

    private static void connect(List<List<Character>> image, Cell cell1, Cell cell2) {
        boolean isVertical = cell1.column == cell2.column;

        if (isVertical) {
            int minRow = Math.min(cell1.row, cell2.row);
            int maxRow = Math.max(cell1.row, cell2.row);
            int column = cell1.column;

            for (int r = minRow + 1; r < maxRow; r++) {
                char value = image.get(r).get(column);

                if (value == '.') {
                    image.get(r).set(column, '|');
                } else  if (value == '-') {
                    image.get(r).set(column, '+');
                }
            }
        } else {
            int minColumn = Math.min(cell1.column, cell2.column);
            int maxColumn = Math.max(cell1.column, cell2.column);
            int row = cell1.row;

            for (int c = minColumn + 1; c < maxColumn; c++) {
                char value = image.get(row).get(c);

                if (value == '.') {
                    image.get(row).set(c, '-');
                } else  if (value == '|') {
                    image.get(row).set(c, '+');
                }
            }
        }
    }

    private static char getSymbol(int index) {
        if (index <= 9) {
            return (char) ('0' + index);
        }

        index -= 10;
        if (index <= 25) {
            return (char) ('a' + index);
        }
        index -= 26;
        return (char) ('A' + index);
    }

    private static void printImage(List<List<Character>> image, int imageId) {
        if (imageId > 0) {
            System.out.println();
        }

        for (int r = 0; r < image.size(); r++) {
            for (int c = 0; c < image.get(0).size(); c++) {
                System.out.print(image.get(r).get(c));
            }
            System.out.println();
        }
    }

    public static class FastReader {

        private static BufferedReader reader;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

}
