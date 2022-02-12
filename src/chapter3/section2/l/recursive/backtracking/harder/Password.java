package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/02/22.
 */
public class Password {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        long[] powersOf26 = getPowersOf26();

        for (int t = 0; t < tests; t++) {
            int kthPosition = FastReader.nextInt();
            String[] grid1 = new String[6];
            String[] grid2 = new String[6];

            for (int i = 0; i < grid1.length; i++) {
                grid1[i] = FastReader.getLine();
            }
            for (int i = 0; i < grid2.length; i++) {
                grid2[i] = FastReader.getLine();
            }
            String kthPassword = getKthPassword(grid1, grid2, kthPosition, powersOf26);
            if (kthPassword != null) {
                outputWriter.printLine(kthPassword);
            } else {
                outputWriter.printLine("NO");
            }
        }
        outputWriter.flush();
    }

    private static String getKthPassword(String[] grid1, String[] grid2, int kthPosition, long[] powersOf26) {
        char[][] gridColumns1 = getGridColumns(grid1);
        char[][] gridColumns2 = getGridColumns(grid2);

        for (int i = 0; i < gridColumns1.length; i++) {
            Arrays.sort(gridColumns1[i]);
            Arrays.sort(gridColumns2[i]);
        }

        int[] columns = new int[6];
        Set<Long> grid1Passwords = new HashSet<>();
        getPasswords(gridColumns1, columns, powersOf26, grid1Passwords, 0);

        return getKthPassword(gridColumns2, columns, powersOf26, kthPosition, grid1Passwords,
                0, new HashSet<>());
    }

    private static void getPasswords(char[][] gridColumns, int[] columns, long[] powersOf26,
                                     Set<Long> passwords, int columnIndex) {
        if (columnIndex == gridColumns[0].length) {
            long passwordHash = generatePasswordHash(gridColumns, columns, powersOf26);
            passwords.add(passwordHash);
            return;
        }

        for (int i = 0; i < gridColumns[0].length; i++) {
            columns[columnIndex] = i;
            getPasswords(gridColumns, columns, powersOf26, passwords, columnIndex + 1);
        }
    }

    private static String getKthPassword(char[][] gridColumns, int[] columns, long[] powersOf26,
                                         int kthPosition, Set<Long> passwords, int columnIndex,
                                         Set<Long> generatedPasswords) {
        if (columnIndex == gridColumns[0].length) {
            long passwordHash = generatePasswordHash(gridColumns, columns, powersOf26);

            if (passwords.contains(passwordHash)) {
                if (generatedPasswords.size() == kthPosition) {
                    return String.valueOf(gridColumns[0][columns[0]]) + gridColumns[1][columns[1]]
                            + gridColumns[2][columns[2]] + gridColumns[3][columns[3]] + gridColumns[4][columns[4]];
                } else {
                    generatedPasswords.add(passwordHash);
                }
            }
            return null;
        }

        for (int i = 0; i < gridColumns[0].length; i++) {
            columns[columnIndex] = i;
            String kthPassword = getKthPassword(gridColumns, columns, powersOf26, kthPosition, passwords,
                    columnIndex + 1, generatedPasswords);
            if (kthPassword != null) {
                return kthPassword;
            }
        }
        return null;
    }

    private static long[] getPowersOf26() {
        long[] powersOf26 = new long[6];
        powersOf26[0] = 1;
        for (int i = 1; i < powersOf26.length; i++) {
            powersOf26[i] = powersOf26[i - 1] * 26;
        }
        return powersOf26;
    }

    private static long generatePasswordHash(char[][] gridColumns, int[] columns, long[] powersOf26) {
        long hash = 0;
        for (int i = 0; i < gridColumns.length; i++) {
            hash += gridColumns[i][columns[i]] * powersOf26[i];
        }
        return hash;
    }

    private static char[][] getGridColumns(String[] grid) {
        char[][] gridColumns = new char[5][6];

        for (int row = 0; row < gridColumns.length; row++) {
            for (int column = 0; column < gridColumns[0].length; column++) {
                gridColumns[row][column] = grid[column].charAt(row);
            }
        }
        return gridColumns;
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
