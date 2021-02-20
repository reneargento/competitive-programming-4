package chapter2.section2.d.twod.array.manipulation.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 12/02/21.
 */
public class RotatedSquare {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int bigSquareDimension = FastReader.nextInt();
        int smallSquareDimension = FastReader.nextInt();

        while (bigSquareDimension != 0 || smallSquareDimension != 0) {
            char[][] bigSquare = new char[bigSquareDimension][bigSquareDimension];
            char[][] smallSquare = new char[smallSquareDimension][smallSquareDimension];

            for (int row = 0; row < bigSquare.length; row++) {
                bigSquare[row] = FastReader.next().toCharArray();
            }
            for (int row = 0; row < smallSquare.length; row++) {
                smallSquare[row] = FastReader.next().toCharArray();
            }

            char[][] smallSquare90 = rotate90Degrees(smallSquare);
            char[][] smallSquare180 = rotate90Degrees(smallSquare90);
            char[][] smallSquare270 = rotate90Degrees(smallSquare180);

            int appearances = countAppearances(bigSquare, smallSquare);
            int appearances90Degrees = countAppearances(bigSquare, smallSquare90);
            int appearances180Degrees = countAppearances(bigSquare, smallSquare180);
            int appearances270Degrees = countAppearances(bigSquare, smallSquare270);

            System.out.printf("%d %d %d %d\n", appearances, appearances90Degrees, appearances180Degrees,
                    appearances270Degrees);

            bigSquareDimension = FastReader.nextInt();
            smallSquareDimension = FastReader.nextInt();
        }
    }

    private static char[][] rotate90Degrees(char[][] smallSquare) {
        char[][] rotated = new char[smallSquare.length][smallSquare[0].length];

        for (int row = 0; row < smallSquare.length; row++) {
            for (int column = 0; column < smallSquare[0].length; column++) {
                int rotatedColumn = smallSquare[0].length - 1 - row;
                rotated[column][rotatedColumn] = smallSquare[row][column];
            }
        }
        return rotated;
    }

    private static int countAppearances(char[][] bigSquare, char[][] smallSquare) {
        int appearances = 0;

        for (int row = 0; row <= bigSquare.length - smallSquare.length; row++) {
            for (int column = 0; column <= bigSquare[0].length - smallSquare[0].length; column++) {
                if (contains(bigSquare, smallSquare, row, column)) {
                    appearances++;
                }
            }
        }
        return appearances;
    }

    private static boolean contains(char[][] bigSquare, char[][] smallSquare, int row, int column) {
        for (int r = 0; r < smallSquare.length; r++) {
            for (int c = 0; c < smallSquare[0].length; c++) {
                if (bigSquare[row + r][column + c] != smallSquare[r][c]) {
                    return false;
                }
            }
        }
        return true;
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
