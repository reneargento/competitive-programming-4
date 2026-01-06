package chapter5.section4.e.others.harder;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 04/01/26.
 */
// Based on https://oi.men.ci/uva-11538/
public class ChessQueen {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 || columns != 0) {
            long attackWays = computeAttackWays(rows, columns);
            outputWriter.printLine(attackWays);
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static long computeAttackWays(int rows, int columns) {
        long rowAttacks = computeRowColumnAttacks(rows, columns);
        long columnAttacks = computeRowColumnAttacks(columns, rows);
        long diagonalAttacks = computeDiagonalAttacks(rows, columns);
        return rowAttacks + columnAttacks + diagonalAttacks;
    }

    private static long computeRowColumnAttacks(long rows, long columns) {
        return rows * (columns - 1) * columns;
    }

    private static long computeDiagonalAttacks(long rows, long columns) {
        if (rows > columns) {
            long aux = rows;
            rows = columns;
            columns = aux;
        }

        long corners = (rows * (rows - 1) * (rows - 2)) / 3;
        return (computeRowColumnAttacks(columns - rows + 1, rows) + corners * 2) * 2;
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

        public void flush() {
            writer.flush();
        }
    }
}
