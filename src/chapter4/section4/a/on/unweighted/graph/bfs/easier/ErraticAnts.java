package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/12/23.
 */
@SuppressWarnings("unchecked")
public class ErraticAnts {

    private static class State {
        int cellID;
        int steps;

        public State(int cellID, int steps) {
            this.cellID = cellID;
            this.steps = steps;
        }
    }

    private static final int OFFSET = 60;
    private static final int MAX_DIMENSION = 121;
    private static final int MAX_ID = MAX_DIMENSION * MAX_DIMENSION;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int paths = FastReader.nextInt();

        for (int p = 0; p < paths; p++) {
            FastReader.getLine();

            List<Integer>[] adjacencyList = new List[MAX_ID];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            int steps = FastReader.nextInt();
            int row = OFFSET;
            int column = OFFSET;
            int cellID = getCellID(row, column);

            for (int s = 0; s < steps; s++) {
                char direction = FastReader.next().charAt(0);
                switch (direction) {
                    case 'N': row--; break;
                    case 'S': row++; break;
                    case 'E': column++; break;
                    default: column--;
                }

                int nextCellID = getCellID(row, column);
                adjacencyList[cellID].add(nextCellID);
                adjacencyList[nextCellID].add(cellID);
                cellID = nextCellID;
            }

            int shortestPathSteps = computeShortestPathSteps(adjacencyList, row, column);
            outputWriter.printLine(shortestPathSteps);
        }
        outputWriter.flush();
    }

    private static int computeShortestPathSteps(List<Integer>[] adjacencyList, int foodRow, int foodColumn) {
        boolean[] visited = new boolean[adjacencyList.length];
        Queue<State> queue = new LinkedList<>();

        int startCellID = getCellID(OFFSET, OFFSET);
        State startState = new State(startCellID, 0);
        int foodCellID = getCellID(foodRow, foodColumn);

        queue.offer(startState);
        visited[startCellID] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            if (state.cellID == foodCellID) {
                return state.steps;
            }

            for (int neighborID : adjacencyList[state.cellID]) {
                if (!visited[neighborID]) {
                    visited[neighborID] = true;
                    queue.offer(new State(neighborID, state.steps + 1));
                }
            }
        }
        return -1;
    }

    private static int getCellID(int row, int column) {
        return row * MAX_DIMENSION + column;
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
