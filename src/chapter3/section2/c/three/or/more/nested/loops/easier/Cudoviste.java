package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 09/11/21.
 */
public class Cudoviste {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        String[][] parkingLot = new String[rows][columns];

        for (int r = 0; r < rows; r++) {
            parkingLot[r] = FastReader.next().split("");
        }

        int[] crashedCars = countCrashedCars(parkingLot);
        for (int crashedCarsCount : crashedCars) {
            outputWriter.printLine(crashedCarsCount);
        }
        outputWriter.flush();
    }

    private static int[] countCrashedCars(String[][] parkingLot) {
        int[] crashedCars = new int[5];
        int[] neighborRows = { 0, 0, 1, 1 };
        int[] neighborColumns = { 0, 1, 0, 1 };

        for (int row = 0; row < parkingLot.length; row++) {
            for (int column = 0; column < parkingLot[0].length; column++) {
                if (parkingLot[row][column].equals("#")) {
                    continue;
                }

                boolean canPark = true;
                int crashes = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (isValid(parkingLot, neighborRow, neighborColumn)
                            && !parkingLot[neighborRow][neighborColumn].equals("#")) {
                        if (parkingLot[neighborRow][neighborColumn].equals("X")) {
                            crashes++;
                        }
                    } else {
                        canPark = false;
                        break;
                    }
                }
                if (canPark) {
                    crashedCars[crashes]++;
                }
            }
        }
        return crashedCars;
    }

    private static boolean isValid(String[][] parkingLot, int row, int column) {
        return row >= 0 && row < parkingLot.length && column >= 0 && column < parkingLot[row].length;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
