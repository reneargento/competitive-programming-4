package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/02/21.
 */
public class Brothers {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int heirs = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int battles = FastReader.nextInt();

        while (heirs != 0 || rows != 0 || columns != 0 || battles != 0) {
            int[][] land = new int[rows][columns];

            for (int row = 0; row < land.length; row++) {
                for (int column = 0; column < land[0].length; column++) {
                    land[row][column] = FastReader.nextInt();
                }
            }

            land = doBattles(land, battles, heirs);
            printLand(land);

            heirs = FastReader.nextInt();
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            battles = FastReader.nextInt();
        }
    }

    private static int[][] doBattles(int[][] land, int battles, int heirs) {
        int[] neighborRows = {-1, 0, 0, 1};
        int[] neighborColumns = {0, -1, 1, 0};

        for (int b = 0; b < battles; b++) {
            int[][] afterBattleLand = new int[land.length][land[0].length];
            for (int row = 0; row < land.length; row++) {
                System.arraycopy(land[row], 0, afterBattleLand[row], 0, land[row].length);
            }

            for (int row = 0; row < land.length; row++) {
                for (int column = 0; column < land[0].length; column++) {
                    int heirId = land[row][column];

                    for (int i = 0; i < neighborRows.length; i++) {
                        int neighborRow = row + neighborRows[i];
                        int neighborColumn = column + neighborColumns[i];

                        if (isValid(land, neighborRow, neighborColumn)) {
                            int enemy = heirId != (heirs - 1) ? heirId + 1 : 0;

                            if (land[neighborRow][neighborColumn] == enemy) {
                                afterBattleLand[neighborRow][neighborColumn] = heirId;
                            }
                        }
                    }
                }
            }
            land = afterBattleLand;
        }
        return land;
    }

    private static void printLand(int[][] land) {
        for (int row = 0; row < land.length; row++) {
            for (int column = 0; column < land[0].length; column++) {
                System.out.print(land[row][column]);

                if (column != land[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static boolean isValid(int[][] land, int row, int column) {
        return row >= 0 && row < land.length && column >= 0 && column < land[0].length;
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
    }
}
