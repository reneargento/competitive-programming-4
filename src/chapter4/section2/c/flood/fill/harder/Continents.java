package chapter4.section2.c.flood.fill.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/03/23.
 */
public class Continents {

    private static class ContinentInfo implements Comparable<ContinentInfo> {
        int ID;
        int regions;

        public ContinentInfo(int ID, int regions) {
            this.ID = ID;
            this.regions = regions;
        }

        @Override
        public int compareTo(ContinentInfo other) {
            return Integer.compare(other.regions, regions);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        int[] neighborRows = { -1, 0, 0, 1 };
        int[] neighborColumns = { 0, -1, 1, 0 };

        while (line != null && !line.isEmpty()) {
            String[] data = line.split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);

            char[][] map = new char[rows][columns];
            for (int row = 0; row < map.length; row++) {
                map[row] = FastReader.getLine().toCharArray();
            }
            int mijidRow = FastReader.nextInt();
            int mijidColumn = FastReader.nextInt();

            int biggestCapturableContinent = computeBiggestCapturableContinent(map, neighborRows, neighborColumns,
                    mijidRow, mijidColumn);
            outputWriter.printLine(biggestCapturableContinent);

            FastReader.getLine();
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeBiggestCapturableContinent(char[][] map, int[] neighborRows, int[] neighborColumns,
                                                         int mijidRow, int mijidColumn) {
        List<ContinentInfo> continentInfoList = new ArrayList<>();
        int[][] continentIDs = new int[map.length][map[0].length];
        int continentID = 1;
        char landKey = map[mijidRow][mijidColumn];

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                if (map[row][column] == landKey && continentIDs[row][column] == 0) {
                    int regions = computeRegionsNumber(map, continentIDs, neighborRows, neighborColumns, landKey,
                            continentID, row, column);
                    continentInfoList.add(new ContinentInfo(continentID, regions));
                    continentID++;
                }
            }
        }

        Collections.sort(continentInfoList);
        int mijidContinentID = continentIDs[mijidRow][mijidColumn];
        if (continentInfoList.size() == 1) {
            return 0;
        }
        if (continentInfoList.get(0).ID != mijidContinentID) {
            return continentInfoList.get(0).regions;
        } else {
            return continentInfoList.get(1).regions;
        }
    }

    private static int computeRegionsNumber(char[][] map, int[][] continentIDs, int[] neighborRows,
                                            int[] neighborColumns, char landKey, int continentID, int row, int column) {
        int regions = 1;
        continentIDs[row][column] = continentID;

        for (int i = 0; i < neighborRows.length; i++) {
            int neighborRow = row + neighborRows[i];
            int neighborColumn = column + neighborColumns[i];
            if (neighborColumn == -1) {
                neighborColumn = map[0].length - 1;
            } else if (neighborColumn == map[0].length) {
                neighborColumn = 0;
            }

            if (isValid(map, neighborRow)
                    && continentIDs[neighborRow][neighborColumn] == 0
                    && map[neighborRow][neighborColumn] == landKey) {
                regions += computeRegionsNumber(map, continentIDs, neighborRows, neighborColumns, landKey, continentID,
                        neighborRow, neighborColumn);
            }
        }
        return regions;
    }

    private static boolean isValid(char[][] map, int row) {
        return row >= 0 && row < map.length;
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

        public void flush() {
            writer.flush();
        }
    }
}
