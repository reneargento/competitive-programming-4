package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/05/23.
 */
public class MazeTraversal {

    private static class State {
        int row;
        int column;
        char orientation;

        public State(int row, int column, char orientation) {
            this.row = row;
            this.column = column;
            this.orientation = orientation;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            char[][] maze = new char[FastReader.nextInt()][FastReader.nextInt()];
            for (int row = 0; row < maze.length; row++) {
                String columns = FastReader.getLine();
                maze[row] = columns.toCharArray();
            }
            State state = new State(FastReader.nextInt() - 1, FastReader.nextInt() - 1, 'N');

            String commands = FastReader.getLine();
            while (commands != null && !commands.isEmpty()) {
                String[] data = commands.split(" ");
                for (String commandSet : data) {
                    moveRobot(maze, state, commandSet);
                }
                commands = FastReader.getLine();
            }

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("%d %d %c", state.row + 1, state.column + 1, state.orientation));
        }
        outputWriter.flush();
    }

    private static void moveRobot(char[][] maze, State state, String commands) {
        for (char command : commands.toCharArray()) {
            int newRow = state.row;
            int newColumn = state.column;

            if (command == 'F') {
                switch (state.orientation) {
                    case 'N': newRow--; break;
                    case 'S': newRow++; break;
                    case 'E': newColumn++; break;
                    default: newColumn--;
                }
                if (isValid(maze, newRow, newColumn) && maze[newRow][newColumn] != '*') {
                    state.row = newRow;
                    state.column = newColumn;
                }
            } else if (command == 'R' || command == 'L') {
                state.orientation = updateOrientation(state.orientation, command);
            } else {
                break;
            }
        }
    }

    private static char updateOrientation(char currentOrientation, char command) {
        switch (currentOrientation) {
            case 'N': return command == 'R' ? 'E' : 'W';
            case 'S': return command == 'R' ? 'W' : 'E';
            case 'E': return command == 'R' ? 'S' : 'N';
            default: return command == 'R' ? 'N' : 'S';
        }
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
