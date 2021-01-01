package chapter1.section6.p.time.waster.problems.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/12/20.
 */
@SuppressWarnings("unchecked")
public class LumberCraft {

    private static final double EPSILON = .000000001;

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object otherCell) {
            if (this == otherCell) return true;
            if (otherCell == null || getClass() != otherCell.getClass()) return false;
            Cell cell = (Cell) otherCell;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static class Player implements Comparable<Player> {
        Cell lumberMill;
        char name;
        double wood;
        int id;

        public Player(Cell lumberMill, char name, int id) {
            this.lumberMill = lumberMill;
            this.name = name;
            this.id = id;
        }

        @Override
        public int compareTo(Player otherPlayer) {
            return name - otherPlayer.name;
        }

        @Override
        public String toString() {
            return String.format("%s %.6f", name, wood);
        }
    }

    private static class Tree implements Comparable<Tree> {
        Cell location;
        double distance;

        public Tree(Cell location, double distance) {
            this.location = location;
            this.distance = distance;
        }

        @Override
        public int compareTo(Tree otherTree) {
            if (Math.abs(distance - otherTree.distance) > EPSILON) {
                return Double.compare(distance, otherTree.distance);
            }
            if (location.column != otherTree.location.column) {
                return otherTree.location.column - location.column;
            }
            return otherTree.location.row - location.row;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int turns = FastReader.nextInt();
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        while (turns != 0 || rows != 0 || columns != 0) {
            List<Player> players = new ArrayList<>();
            List<Cell> treeLocations = new ArrayList<>();
            char[][] map = new char[rows][columns];

            for (int row = 0; row < rows; row++) {
                String rowData = FastReader.getLine();
                for (int column = 0; column < columns; column++) {
                    map[row][column] = rowData.charAt(column);
                    if (Character.isLetter(map[row][column])) {
                        players.add(new Player(new Cell(row, column), map[row][column], players.size()));
                    } else if (map[row][column] == '!') {
                        treeLocations.add(new Cell(row, column));
                    }
                }
            }
            Collections.sort(players);
            PriorityQueue<Tree>[] treesPerPlayer = new PriorityQueue[players.size()];
            addTreesToPriorityQueues(players, treesPerPlayer, treeLocations);

            for (int t = 0; t < turns; t++) {
                Map<Cell, List<Player>> treesSelected = new HashMap<>();

                for (Player player : players) {
                    boolean selected = false;

                    while (!treesPerPlayer[player.id].isEmpty() && !selected) {
                        Tree tree = treesPerPlayer[player.id].poll();
                        Cell treeLocation = tree.location;
                        if (map[treeLocation.row][treeLocation.column] != '!') {
                            continue;
                        } else {
                            selected = true;
                        }

                        if (!treesSelected.containsKey(treeLocation)) {
                            treesSelected.put(treeLocation, new ArrayList<>());
                        }
                        treesSelected.get(treeLocation).add(player);
                    }
                }

                for (Cell tree : treesSelected.keySet()) {
                    List<Player> playerList = treesSelected.get(tree);
                    for (Player player : playerList) {
                        player.wood += 1.0 / playerList.size();
                    }
                    map[tree.row][tree.column] = '.';
                }
            }

            printMap(map, outputWriter);
            for (Player player : players) {
                outputWriter.printLine(player);
            }

            turns = FastReader.nextInt();
            rows = FastReader.nextInt();
            columns = FastReader.nextInt();
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static void addTreesToPriorityQueues(List<Player> players, PriorityQueue<Tree>[] treesPerPlayer,
                                                 List<Cell> treeLocations) {
        for (Player player : players) {
            treesPerPlayer[player.id] = new PriorityQueue<>();

            for (Cell treeLocation : treeLocations) {
                double distance = distance(player.lumberMill, treeLocation.row, treeLocation.column);
                treesPerPlayer[player.id].offer(new Tree(treeLocation, distance));
            }
        }
    }

    private static void printMap(char[][] map, OutputWriter outputWriter) {
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[0].length; column++) {
                outputWriter.print(map[row][column]);
            }
            outputWriter.printLine();
        }
    }

    private static double distance(Cell cell, int row, int column) {
        int rowDifference = cell.row - row;
        int columnDifference = cell.column - column;
        return Math.sqrt((rowDifference * rowDifference) + (columnDifference * columnDifference));
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
