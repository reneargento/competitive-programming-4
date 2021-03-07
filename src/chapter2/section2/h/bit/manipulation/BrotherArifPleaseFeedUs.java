package chapter2.section2.h.bit.manipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/03/21.
 */
public class BrotherArifPleaseFeedUs {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int problemSetters = FastReader.nextInt();
        int caseNumber = 1;

        while (rows != 0 || columns != 0 || problemSetters != 0) {
            Set<Integer> blockedRows = new HashSet<>();
            Set<Integer> blockedColumns = new HashSet<>();
            blockedRows.add(-1);
            blockedRows.add(rows);
            blockedColumns.add(-1);
            blockedColumns.add(columns);

            for (int i = 0; i < problemSetters; i++) {
                blockedRows.add(FastReader.nextInt());
                blockedColumns.add(FastReader.nextInt());
            }

            int brotherArifRow = FastReader.nextInt();
            int brotherArifColumn = FastReader.nextInt();

            System.out.printf("Case %d: ", caseNumber);
            if (canBrotherArifEscape(brotherArifRow, brotherArifColumn, blockedRows, blockedColumns)) {
                System.out.println("Escaped again! More 2D grid problems!");
            } else {
                System.out.println("Party time! Let's find a restaurant!");
            }

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
            problemSetters = FastReader.nextInt();
            caseNumber++;
        }
    }

    private static boolean canBrotherArifEscape(int brotherArifRow, int brotherArifColumn, Set<Integer> blockedRows,
                                                Set<Integer> blockedColumns) {
        int[] neighborRows = {-1, 0, 0, 1, 0};
        int[] neighborColumns = {0, -1, 1, 0, 0};

        for (int i = 0; i < neighborRows.length; i++) {
            int row = brotherArifRow + neighborRows[i];
            int column = brotherArifColumn + neighborColumns[i];

            if (!blockedRows.contains(row) && !blockedColumns.contains(column)) {
                return true;
            }
        }
        return false;
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
