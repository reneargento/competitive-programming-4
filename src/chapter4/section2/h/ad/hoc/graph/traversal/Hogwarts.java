package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/05/23.
 */
public class Hogwarts {

    private static final String RESULT_IMPOSSIBLE = "Impossible";
    private static final String RESULT_YES = "Yes";
    private static final String RESULT_NO = "No";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rooms = FastReader.nextInt();
        int[][] adjacencyListSenior = computeRooms(rooms);
        int[][] adjacencyListStudent = computeRooms(rooms);

        String result = arePathsEquivalent(adjacencyListSenior, adjacencyListStudent);
        outputWriter.printLine(result);
        outputWriter.flush();
    }

    private static String arePathsEquivalent(int[][] adjacencyListSenior, int[][] adjacencyListStudent) {
        boolean[][] visited = new boolean[adjacencyListSenior.length][4];
        LinkedList<Integer> path = new LinkedList<>();
        return depthFirstSearch(adjacencyListSenior, adjacencyListStudent, visited, path, 0);
    }

    private static String depthFirstSearch(int[][] adjacencyListSenior, int[][] adjacencyListStudent,
                                           boolean[][] visited, LinkedList<Integer> path, int roomID) {
        boolean possible = false;

        if (roomID == adjacencyListSenior.length - 1) {
            if (!canStudentReach(adjacencyListStudent, path)) {
                return RESULT_NO;
            }
            possible = true;
        }

        for (int nextRoomNumber = 0; nextRoomNumber < 4; nextRoomNumber++) {
            if (!visited[roomID][nextRoomNumber]) {
                visited[roomID][nextRoomNumber] = true;
                int nextRoomID = adjacencyListSenior[roomID][nextRoomNumber];
                if (nextRoomID == -1) {
                    continue;
                }
                path.add(nextRoomNumber);

                String result = depthFirstSearch(adjacencyListSenior, adjacencyListStudent, visited, path, nextRoomID);
                if (result.equals(RESULT_NO)) {
                    return result;
                } else if (result.equals(RESULT_YES)) {
                    possible = true;
                }
                path.removeLast();
            }
        }
        return possible ? RESULT_YES : RESULT_IMPOSSIBLE;
    }

    private static boolean canStudentReach(int[][] adjacencyListStudent, LinkedList<Integer> path) {
        int currentRoomID = 0;

        for (int nextRoomNumber : path) {
            if (adjacencyListStudent[currentRoomID][nextRoomNumber] == -1) {
                return false;
            }
            currentRoomID = adjacencyListStudent[currentRoomID][nextRoomNumber];
        }
        return currentRoomID == adjacencyListStudent.length - 1;
    }

    private static int[][] computeRooms(int rooms) throws IOException {
        int[][] adjacencyList = new int[rooms][4];

        for (int roomID = 0; roomID < adjacencyList.length; roomID++) {
            for (int i = 0; i < 4; i++) {
                adjacencyList[roomID][i] = FastReader.nextInt() - 1;
            }
        }
        return adjacencyList;
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

        public void flush() {
            writer.flush();
        }
    }
}
