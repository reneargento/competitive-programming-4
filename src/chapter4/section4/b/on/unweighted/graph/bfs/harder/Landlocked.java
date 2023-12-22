package chapter4.section4.b.on.unweighted.graph.bfs.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 21/12/23.
 */
public class Landlocked {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class CountryData implements Comparable<CountryData> {
        char name;
        int landlockedness;

        public CountryData(char name, int landlockedness) {
            this.name = name;
            this.landlockedness = landlockedness;
        }

        @Override
        public int compareTo(CountryData other) {
            return Character.compare(name, other.name);
        }
    }

    private static final int[] neighborRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] neighborColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char[][] map = new char[FastReader.nextInt()][FastReader.nextInt()];
        Deque<Cell> deque = new ArrayDeque<>();
        int[][] distances = new int[map.length][map[0].length];

        for (int[] values : distances) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }

        for (int row = 0; row < map.length; row++) {
            String columns = FastReader.getLine();
            for (int column = 0; column < columns.length(); column++) {
                char value = columns.charAt(column);
                map[row][column] = value;
                if (value == 'W') {
                    Cell cell = new Cell(row, column);
                    deque.offerFirst(cell);
                    distances[row][column] = -1;
                }
            }
        }

        List<CountryData> countryData = computeLandlockedness(map, distances, deque);
        for (CountryData data : countryData) {
            outputWriter.printLine(String.format("%c %d", data.name, data.landlockedness));
        }
        outputWriter.flush();
    }

    private static List<CountryData> computeLandlockedness(char[][] map, int[][] distances, Deque<Cell> deque) {
        while (!deque.isEmpty()) {
            Cell cell = deque.pollFirst();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)) {
                    int nextDistance = distances[cell.row][cell.column];
                    Cell nextCell = new Cell(neighborRow, neighborColumn);
                    boolean crossesBorder = false;

                    if (map[cell.row][cell.column] != map[neighborRow][neighborColumn]) {
                        nextDistance++;
                        crossesBorder = true;
                    }

                    if (nextDistance < distances[neighborRow][neighborColumn]) {
                        distances[neighborRow][neighborColumn] = nextDistance;

                        if (crossesBorder) {
                            deque.offerLast(nextCell);
                        } else {
                            deque.offerFirst(nextCell);
                        }
                    }
                }
            }
        }

        List<CountryData> countryData = new ArrayList<>();
        Map<Character, Integer> landlockednessMap = computeLandlockednessWithDistances(map, distances);

        for (char country : landlockednessMap.keySet()) {
            int landlockedness = landlockednessMap.get(country);
            countryData.add(new CountryData(country, landlockedness));
        }
        Collections.sort(countryData);
        return countryData;
    }

    private static Map<Character, Integer> computeLandlockednessWithDistances(char[][] map, int[][] distances) {
        Map<Character, Integer> landlockednessMap = new HashMap<>();

        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                char country = map[row][column];

                if (country != 'W') {
                    if (!landlockednessMap.containsKey(country)) {
                        landlockednessMap.put(country, Integer.MAX_VALUE);
                    }
                    int currentLandlockedness = landlockednessMap.get(country);
                    if (distances[row][column] < currentLandlockedness) {
                        landlockednessMap.put(country, distances[row][column]);
                    }
                }
            }
        }
        return landlockednessMap;
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
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
