package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 17/01/24.
 */
public class TheGreatEscape {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class State implements Comparable<State> {
        int totalTime;
        Cell cell;

        public State(int totalTime, Cell cell) {
            this.totalTime = totalTime;
            this.cell = cell;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(totalTime, other.totalTime);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final int NORTH = 0;
    private static final int WEST = 1;
    private static final int EAST = 2;
    private static final int SOUTH = 3;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            char[][] map = new char[FastReader.nextInt()][FastReader.nextInt()];
            int[][] doorTimes = new int[map.length][map[0].length];
            List<Cell> rotatingDoors = new ArrayList<>();

            for (int row = 0; row < map.length; row++) {
                String columns = FastReader.getLine();
                for (int column = 0; column < columns.length(); column++) {
                    char value = columns.charAt(column);
                    if (isRotatingDoor(value)) {
                        rotatingDoors.add(new Cell(row, column));
                    }
                    map[row][column] = value;
                }
            }

            for (int i = 0; i < rotatingDoors.size(); i++) {
                int rotateTime = FastReader.nextInt();
                int row = rotatingDoors.get(i).row;
                int column = rotatingDoors.get(i).column;
                doorTimes[row][column] = rotateTime;
            }

            int timeToReachDoor = computeMinimumTime(map, doorTimes);
            if (timeToReachDoor != -1) {
                outputWriter.printLine(timeToReachDoor);
            } else {
                outputWriter.printLine("Poor Kianoosh");
            }
        }
        outputWriter.flush();
    }

    private static int computeMinimumTime(char[][] map, int[][] doorTimes) {
        int[][] bestTimes = new int[map.length][map[0].length];
        for (int[] values : bestTimes) {
            Arrays.fill(values, Integer.MAX_VALUE);
        }

        PriorityQueue<State> priorityQueue = new PriorityQueue<>();
        Cell startCell = new Cell(map.length - 1, 0);
        priorityQueue.offer(new State(0, startCell));
        bestTimes[startCell.row][startCell.column] = 0;

        while (!priorityQueue.isEmpty()) {
            State state = priorityQueue.poll();
            Cell cell = state.cell;

            if (cell.row == 0 && cell.column == map[0].length - 1) {
                return state.totalTime;
            }

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(map, neighborRow, neighborColumn)
                        && map[neighborRow][neighborColumn] != '#') {
                    if (isRotatingDoor(map[neighborRow][neighborColumn])
                            && !canEnterRotatingDoor(map[neighborRow][neighborColumn], i)) {
                        continue;
                    }

                    int nextTime = state.totalTime + 1;
                    if (isRotatingDoor(map[cell.row][cell.column])) {
                        nextTime += getRotationCost(map[cell.row][cell.column], i, doorTimes[cell.row][cell.column]);
                    }

                    if (nextTime < bestTimes[neighborRow][neighborColumn]) {
                        bestTimes[neighborRow][neighborColumn] = nextTime;
                        Cell newCell = new Cell(neighborRow, neighborColumn);
                        priorityQueue.offer(new State(nextTime, newCell));
                    }
                }
            }
        }
        return -1;
    }

    private static boolean canEnterRotatingDoor(char value, int index) {
        return (value == 'N' && index == SOUTH)
                || (value == 'W' && index == EAST)
                || (value == 'E' && index == WEST)
                || (value == 'S' && index == NORTH);
    }

    private static int getRotationCost(char value, int index, int rotateTime) {
        switch (value) {
            case 'N':
                if (index == NORTH) {
                    return 1;
                } else if (index == EAST || index == WEST) {
                    return rotateTime;
                } else {
                    return rotateTime * 2;
                }
            case 'E':
                if (index == EAST) {
                    return 1;
                } else if (index == NORTH || index == SOUTH) {
                    return rotateTime;
                } else {
                    return rotateTime * 2;
                }
            case 'W':
                if (index == WEST) {
                    return 1;
                } else if (index == NORTH || index == SOUTH) {
                    return rotateTime;
                } else {
                    return rotateTime * 2;
                }
            default:
                if (index == SOUTH) {
                    return 1;
                } else if (index == EAST || index == WEST) {
                    return rotateTime;
                } else {
                    return rotateTime * 2;
                }
        }
    }

    private static boolean isValid(char[][] map, int row, int column) {
        return row >= 0 && row < map.length && column >= 0 && column < map[0].length;
    }

    private static boolean isRotatingDoor(char value) {
        return value == 'N' || value == 'W' || value == 'E' || value == 'S';
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
