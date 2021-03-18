package chapter2.section2.i.big.integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/03/21.
 */
public class LangtonsAnt {

    private enum Direction {
        NORTH, EAST, SOUTH, WEST;

        Direction turnRight() {
            switch (this) {
                case NORTH: return EAST;
                case EAST: return SOUTH;
                case SOUTH: return WEST;
                default: return NORTH;
            }
        }

        Direction turnLeft() {
            switch (this) {
                case NORTH: return WEST;
                case WEST: return SOUTH;
                case SOUTH: return EAST;
                default: return NORTH;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int worldSize = FastReader.nextInt();
        BigInteger worldConfiguration = new BigInteger(FastReader.next());
        int column = FastReader.nextInt();
        int row = FastReader.nextInt();

        while (worldSize != 0 || worldConfiguration.compareTo(BigInteger.ZERO) != 0 || column != 0 || row != 0) {
            boolean canReachEnd = canReachEnd(worldSize, worldConfiguration, column - 1, row - 1);
            System.out.println(canReachEnd ? "Yes" : "Kaputt!");

            worldSize = FastReader.nextInt();
            worldConfiguration = new BigInteger(FastReader.next());
            column = FastReader.nextInt();
            row = FastReader.nextInt();
        }
    }

    private static boolean canReachEnd(int worldSize, BigInteger worldConfiguration, int column, int row) {
        int targetRow = worldSize - 1;
        int targetColumn = worldSize - 1;
        Direction direction = Direction.NORTH;
        int totalBits = worldSize * worldSize;

        while (true) {
            if (row == targetRow && column == targetColumn) {
                return true;
            }

            int bitIndex = totalBits - ((row * worldSize) + column) - 1;
            boolean cellColor = worldConfiguration.testBit(bitIndex);
            if (cellColor) {
                direction = direction.turnRight();
            } else {
                direction = direction.turnLeft();
            }
            worldConfiguration = worldConfiguration.flipBit(bitIndex);

            switch (direction) {
                case NORTH: row++; break;
                case EAST: column++; break;
                case SOUTH: row--; break;
                default: column--; break;
            }

            if (!isValid(worldSize, row, column)) {
                return false;
            }
        }
    }

    private static boolean isValid(int worldSize, int row, int column) {
        return row >= 0 && row < worldSize && column >= 0 && column < worldSize;
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
}
