package chapter2.section2.c.twod.array.manipulation.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 05/02/21.
 */
public class Triangles {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int rows = FastReader.nextInt();
        int caseNumber = 1;

        while (rows != 0) {
            String firstRow = FastReader.getLine();
            char[][] triangles = new char[rows][firstRow.length()];
            triangles[0] = firstRow.toCharArray();

            for (int r = 1; r < rows; r++) {
                triangles[r] = FastReader.getLine().toCharArray();
            }

            int largestArea = getLargestTriangleArea(triangles);
            System.out.printf("Triangle #%d\n", caseNumber);
            System.out.printf("The largest triangle area is %d.\n\n", largestArea);

            caseNumber++;
            rows = FastReader.nextInt();
        }
    }

    private static int getLargestTriangleArea(char[][] triangles) {
        int area = 0;

        for (int row = 0; row < triangles.length; row++) {
            for (int column = 0; column < triangles[row].length; column++) {
                if (triangles[row][column] == '-') {
                    boolean checkUp = (row % 2 == column % 2);
                    int triangleArea = checkArea(triangles, row, column, checkUp);
                    area = Math.max(area, triangleArea);
                }
            }
        }
        return area;
    }

    private static int checkArea(char[][] triangles, int row, int column, boolean checkUp) {
        int cellsToCheck = 1;
        int area = 1;

        while (true) {
            row = checkUp ? row - 1 : row + 1;
            column--;
            cellsToCheck += 2;

            boolean isPartOfTriangle = true;
            for (int i = 0; i < cellsToCheck; i++) {
                if (!isValid(triangles, row, column + i) || triangles[row][column + i] != '-') {
                    isPartOfTriangle = false;
                    break;
                }
            }

            if (!isPartOfTriangle) {
                break;
            }

            area += cellsToCheck;
        }
        return area;
    }

    private static boolean isValid(char[][] triangles, int row, int column) {
        return row >= 0 && row < triangles.length && column >= 0 && column < triangles[row].length;
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

        // Used to check EOF
        // If getLine() == null, it is a EOF
        // Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
