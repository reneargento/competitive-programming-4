package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/08/22.
 */
public class CinemaCola {

    private static class Location {
        boolean leftSupportTaken;
        boolean rightSupportTaken;
        boolean isReserved;
    }

    private static class SeatId {
        int row;
        int column;

        public SeatId(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (rows != 0 && columns != 0) {
            Location[][] theater = new Location[rows][columns];
            for (int row = 0; row < theater.length; row++) {
                for (int column = 0; column < theater[row].length; column++) {
                    theater[row][column] = new Location();
                }
            }

            int personsBefore = FastReader.nextInt();
            for (int i = 0; i < personsBefore; i++) {
                String location = FastReader.next();
                SeatId seatId = getSeatId(location);
                boolean isLeftTaken = FastReader.next().equals("-");

                if (isLeftTaken) {
                    theater[seatId.row][seatId.column].leftSupportTaken = true;
                    if (seatId.column > 0) {
                        theater[seatId.row][seatId.column - 1].rightSupportTaken = true;
                    }
                } else {
                    theater[seatId.row][seatId.column].rightSupportTaken = true;
                    if (seatId.column < theater[0].length - 1) {
                        theater[seatId.row][seatId.column + 1].leftSupportTaken = true;
                    }
                }
            }

            String[] personsInTheGroupLocations = new String[FastReader.nextInt()];
            for (int i = 0; i < personsInTheGroupLocations.length; i++) {
                personsInTheGroupLocations[i] = FastReader.next();
            }
            boolean isPossibleToUseSupports = checkTheaterSupports(theater, personsInTheGroupLocations);
            outputWriter.printLine(isPossibleToUseSupports ? "YES" : "NO");

            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static SeatId getSeatId(String location) {
        int row = (location.charAt(0) - 'A');
        int column = Integer.parseInt(location.substring(1)) - 1;
        return new SeatId(row, column);
    }

    private static boolean checkTheaterSupports(Location[][] theater, String[] personsInTheGroupLocations) {
        for (String location : personsInTheGroupLocations) {
            SeatId seatId = getSeatId(location);
            theater[seatId.row][seatId.column].isReserved = true;
        }

        for (Location[] locations : theater) {
            for (int column = 0; column < locations.length; column++) {
                if (locations[column].isReserved) {
                    if (!locations[column].leftSupportTaken) {
                        continue;
                    } else if (!locations[column].rightSupportTaken) {
                        if (column < theater[0].length - 1) {
                            locations[column + 1].leftSupportTaken = true;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
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
