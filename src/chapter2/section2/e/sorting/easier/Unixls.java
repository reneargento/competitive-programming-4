package chapter2.section2.e.sorting.easier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/02/21.
 */
public class Unixls {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        String namesLine = FastReader.getLine();

        while (namesLine != null) {
            int namesCount = Integer.parseInt(namesLine);
            String[] fileNames = new String[namesCount];
            int longestLength = 0;

            for (int i = 0; i < fileNames.length; i++) {
                fileNames[i] = FastReader.next();
                longestLength = Math.max(longestLength, fileNames[i].length());
            }

            Arrays.sort(fileNames);
            int columns = getNumberOfColumns(longestLength);
            printFileNames(fileNames, columns, longestLength);

            namesLine = FastReader.getLine();
        }
    }

    private static int getNumberOfColumns(int longestLength) {
        int columns = 0;
        int length = longestLength;

        while (length <= 60) {
            columns++;
            length += longestLength + 2;
        }

        return columns;
    }

    private static void printFileNames(String[] fileNames, int columns, int columnSize) {
        int rows = (int) Math.ceil(fileNames.length / (double) columns);

        System.out.println("------------------------------------------------------------");
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int fileIndex = (rows * column) + row;
                if (fileIndex >= fileNames.length) {
                    break;
                }

                System.out.printf("%-" + columnSize + "s", fileNames[fileIndex]);
                if (column != columns - 1) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
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

        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }
}
