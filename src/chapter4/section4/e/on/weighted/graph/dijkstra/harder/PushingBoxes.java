package chapter4.section4.e.on.weighted.graph.dijkstra.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/01/24.
 */
public class PushingBoxes {

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };
    private static final char[] direction = { 'n', 'w', 'e', 's' };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();
        int mazeID = 1;

        while (rows != 0 || columns != 0) {
            Cell startCell = null;
            Cell boxCell = null;

            char[][] maze = new char[rows][columns];
            for (int row = 0; row < rows; row++) {
                String line = FastReader.getLine();
                for (int column = 0; column < line.length(); column++) {
                    char cell = line.charAt(column);
                    if (cell == 'S') {
                        startCell = new Cell(row, column);
                    } else if (cell == 'B') {
                        boxCell = new Cell(row, column);
                    }
                    maze[row][column] = cell;
                }
            }
            String shortestSequence = computeShortestSequence(maze, startCell, boxCell);
            outputWriter.printLine(String.format("Maze #%d", mazeID));
            outputWriter.printLine(shortestSequence);
            outputWriter.printLine();

            mazeID++;
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String computeShortestSequence(char[][] maze, Cell startCell, Cell boxCell) {
        int startNodeID = getNodeID(maze, startCell.row, startCell.column);
        Dijkstra dijkstra = new Dijkstra(maze, startNodeID, boxCell);
        if (dijkstra.shortestPath != null) {
            return dijkstra.shortestPath;
        } else {
            return "Impossible.";
        }
    }

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            StringBuilder moves;
            int numberOfPushes;
            Cell boxLocation;

            public Vertex(int id, StringBuilder moves, int numberOfPushes, Cell boxLocation) {
                this.id = id;
                this.moves = moves;
                this.numberOfPushes = numberOfPushes;
                this.boxLocation = boxLocation;
            }

            @Override
            public int compareTo(Vertex other) {
                if (numberOfPushes != other.numberOfPushes) {
                    return Integer.compare(numberOfPushes, other.numberOfPushes);
                }
                return Integer.compare(moves.length(), other.moves.length());
            }
        }

        private static class VisitKey {
            int vertexId;
            int boxRow;
            int boxColumn;

            public VisitKey(int vertexId, int boxRow, int boxColumn) {
                this.vertexId = vertexId;
                this.boxRow = boxRow;
                this.boxColumn = boxColumn;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                VisitKey visitKey = (VisitKey) o;
                return vertexId == visitKey.vertexId && boxRow == visitKey.boxRow && boxColumn == visitKey.boxColumn;
            }

            @Override
            public int hashCode() {
                return Objects.hash(vertexId, boxRow, boxColumn);
            }
        }

        private final PriorityQueue<Vertex> priorityQueue;
        private final Map<VisitKey, Vertex> visited;
        public String shortestPath;

        public Dijkstra(char[][] maze, int sourceID, Cell boxCell) {
            int maxNodes = maze.length * maze[0].length;
            priorityQueue = new PriorityQueue<>(maxNodes);
            visited = new HashMap<>();

            Vertex startState = new Vertex(sourceID, new StringBuilder(), 0, boxCell);
            VisitKey visitKey = new VisitKey(sourceID, boxCell.row, boxCell.column);
            visited.put(visitKey, startState);
            priorityQueue.offer(startState);

            while (!priorityQueue.isEmpty()) {
                if (shortestPath != null) {
                    break;
                }
                relax(maze, priorityQueue.poll());
            }
        }

        private void relax(char[][] maze, Vertex vertex) {
            for (int i = 0; i < neighborRows.length; i++) {
                int row = vertex.id / maze[0].length;
                int column = vertex.id % maze[0].length;
                int neighborRow = row + neighborRows[i];
                int neighborColumn = column + neighborColumns[i];

                if (isValid(maze, neighborRow, neighborColumn)
                        && maze[neighborRow][neighborColumn] != '#') {
                    int neighborID = getNodeID(maze, neighborRow, neighborColumn);
                    boolean isPushingBox = (neighborRow == vertex.boxLocation.row
                            && neighborColumn == vertex.boxLocation.column);
                    char move = direction[i];
                    Cell newBoxCell = vertex.boxLocation;
                    StringBuilder nextMoves = new StringBuilder(vertex.moves);
                    int pushAdded = 0;

                    if (isPushingBox) {
                        int rowToPush = neighborRow + neighborRows[i];
                        int columnToPush = neighborColumn + neighborColumns[i];
                        move = Character.toUpperCase(move);

                        if (isValid(maze, rowToPush, columnToPush)
                                && maze[rowToPush][columnToPush] != '#') {
                            newBoxCell = new Cell(rowToPush, columnToPush);
                            nextMoves.append(move);
                            pushAdded = 1;

                            if (maze[rowToPush][columnToPush] == 'T') {
                                shortestPath = nextMoves.toString();
                                return;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        nextMoves.append(move);
                    }

                    Vertex nextState = new Vertex(neighborID, nextMoves,
                            vertex.numberOfPushes + pushAdded, newBoxCell);
                    VisitKey visitKey = new VisitKey(neighborID, newBoxCell.row, newBoxCell.column);

                    if (!visited.containsKey(visitKey)
                            || nextState.compareTo(visited.get(visitKey)) < 0) {
                        visited.put(visitKey, nextState);
                        priorityQueue.offer(nextState);
                    }
                }
            }
        }
    }

    private static int getNodeID(char[][] maze, int row, int column) {
        return row * maze[0].length + column;
    }

    private static boolean isValid(char[][] maze, int row, int column) {
        return row >= 0 && row < maze.length && column >= 0 && column < maze[0].length;
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
