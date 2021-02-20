package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 08/02/21.
 */
public class FunHouse {

    private enum Direction {
        LEFT, UP, RIGHT, DOWN
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int width = FastReader.nextInt();
        int length = FastReader.nextInt();
        int houseId = 1;

        while (width != 0 || length != 0) {
            char[][] house = new char[length][width];
            int doorRow = -1;
            int doorColumn = -1;

            for (int row = 0; row < length; row++) {
                house[row] = FastReader.getLine().toCharArray();

                if (doorRow == -1) {
                    for (int column = 0; column < house[row].length; column++) {
                        if (house[row][column] == '*') {
                            doorRow = row;
                            doorColumn = column;
                            break;
                        }
                    }
                }
            }

            placeExit(house, doorRow, doorColumn);

            System.out.printf("HOUSE %d\n", houseId);
            printHouse(house);

            houseId++;
            width = FastReader.nextInt();
            length = FastReader.nextInt();
        }
    }

    private static void placeExit(char[][] house, int row, int column) {
        Direction direction;

        if (row == 0) {
            direction = Direction.DOWN;
        } else if (row == house.length - 1) {
            direction = Direction.UP;
        } else if (column == 0) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }

        boolean placedDoor = false;

        while (!placedDoor) {
            switch (direction) {
                case UP: row--; break;
                case DOWN: row++; break;
                case LEFT: column--; break;
                case RIGHT: column++; break;
            }

            if (house[row][column] == 'x') {
                house[row][column] = '&';
                placedDoor = true;
            } else if (house[row][column] == '/') {
                switch (direction) {
                    case UP: direction = Direction.RIGHT; break;
                    case DOWN: direction = Direction.LEFT; break;
                    case LEFT: direction = Direction.DOWN; break;
                    case RIGHT: direction = Direction.UP; break;
                }
            } else if (house[row][column] == '\\') {
                switch (direction) {
                    case UP: direction = Direction.LEFT; break;
                    case DOWN: direction = Direction.RIGHT; break;
                    case LEFT: direction = Direction.UP; break;
                    case RIGHT: direction = Direction.DOWN; break;
                }
            }
        }
    }

    private static void printHouse(char[][] house) {
        for (int row = 0; row < house.length; row++) {
            for (int column = 0; column < house[0].length; column++) {
                System.out.print(house[row][column]);
            }
            System.out.println();
        }
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
