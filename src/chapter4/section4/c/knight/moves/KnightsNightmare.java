package chapter4.section4.c.knight.moves;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/23.
 */
public class KnightsNightmare {

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class State {
        Cell cell;
        int minutes;
        boolean monsterAwake;

        public State(Cell cell, int minutes, boolean monsterAwake) {
            this.cell = cell;
            this.minutes = minutes;
            this.monsterAwake = monsterAwake;
        }
    }

    private static final int[] neighborRows = { -2, -2, 1, -1, 1, -1, 2, 2 };
    private static final int[] neighborColumns = { -1, 1, -2, -2, 2, 2, -1, 1 };
    private static final int MONSTER_SLEEPING = 0;
    private static final int MONSTER_AWAKE = 1;
    private static final int INFINITE = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String setNumber = FastReader.getLine();
        while (setNumber != null) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            Cell[] leaderPositions = new Cell[4];
            for (int i = 0; i < leaderPositions.length; i++) {
                leaderPositions[i] = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);
            }
            Cell monsterPosition = new Cell(FastReader.nextInt() - 1, FastReader.nextInt() - 1);

            int minimumMeetTime = computeMinimumMeetTime(rows, columns, leaderPositions, monsterPosition);
            outputWriter.printLine(setNumber);
            if (minimumMeetTime != INFINITE) {
                outputWriter.printLine(String.format("Minimum time required is %d minutes.", minimumMeetTime));
            } else {
                outputWriter.printLine("Meeting is impossible.");
            }
            setNumber = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int computeMinimumMeetTime(int rows, int columns, Cell[] leaderPositions, Cell monsterPosition) {
        int[][][][] timePerLocation = new int[leaderPositions.length][rows][columns][2];
        for (int[][][] timePerRowColumn : timePerLocation) {
            for (int[][] timePerRow : timePerRowColumn) {
                for (int[] time : timePerRow) {
                    Arrays.fill(time, INFINITE);
                }
            }
        }

        for (int leaderID = 0; leaderID < leaderPositions.length; leaderID++) {
            bfs(rows, columns, timePerLocation, leaderPositions[leaderID], leaderID, monsterPosition);
        }

        int[] leaderWithMonsterAwake = { -1, 0, 1, 2, 3 };
        int minimumTime = INFINITE;

        for (int leaderThatWakesMonster : leaderWithMonsterAwake) {
            for (int row = 0; row < timePerLocation[0].length; row++) {
                for (int column = 0; column < timePerLocation[0][0].length; column++) {
                    int leaders = 0;
                    int totalTime = 0;
                    for (int leaderID = 0; leaderID < timePerLocation.length; leaderID++) {
                        int monsterState = MONSTER_SLEEPING;
                        if (leaderID == leaderThatWakesMonster) {
                            monsterState = MONSTER_AWAKE;
                        }

                        if (timePerLocation[leaderID][row][column][monsterState] != INFINITE) {
                            leaders++;
                            totalTime += timePerLocation[leaderID][row][column][monsterState];
                        }
                    }
                    if (leaders == 4) {
                        minimumTime = Math.min(minimumTime, totalTime);
                    }
                }
            }
        }
        return minimumTime;
    }

    private static void bfs(int rows, int columns, int[][][][] timePerLocation, Cell startCell, int leaderID,
                            Cell monsterPosition) {
        boolean[][][] visited = new boolean[rows][columns][2];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startCell, 0, false));
        timePerLocation[leaderID][startCell.row][startCell.column][MONSTER_SLEEPING] = 0;
        visited[startCell.row][startCell.column][MONSTER_SLEEPING] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            Cell cell = state.cell;

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];
                Cell nextCell = new Cell(neighborRow, neighborColumn);
                int nextTime = state.minutes + 1;

                if (isValid(visited, neighborRow, neighborColumn)) {
                    if (state.monsterAwake || nextCell.equals(monsterPosition)) {
                        if (!visited[neighborRow][neighborColumn][MONSTER_AWAKE]
                                && !visited[neighborRow][neighborColumn][MONSTER_SLEEPING]) {
                            visited[neighborRow][neighborColumn][MONSTER_AWAKE] = true;
                            timePerLocation[leaderID][neighborRow][neighborColumn][MONSTER_AWAKE] = nextTime;
                            queue.offer(new State(nextCell, nextTime, true));
                        }
                    } else {
                        if (!visited[neighborRow][neighborColumn][MONSTER_SLEEPING]) {
                            visited[neighborRow][neighborColumn][MONSTER_SLEEPING] = true;
                            timePerLocation[leaderID][neighborRow][neighborColumn][MONSTER_SLEEPING] = nextTime;
                            queue.offer(new State(nextCell, nextTime, false));
                        }
                    }
                }
            }
        }
    }

    private static boolean isValid(boolean[][][] visited, int row, int column) {
        return row >= 0 && row < visited.length && column >= 0 && column < visited[0].length;
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
