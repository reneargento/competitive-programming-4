package chapter4.section2.h.ad.hoc.graph.traversal;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 20/05/23.
 */
public class Hogwarts {

    private static final String RESULT_IMPOSSIBLE = "Impossible";
    private static final String RESULT_YES = "Yes";
    private static final String RESULT_NO = "No";

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rooms = inputReader.nextInt();
        int[][] adjacencyListSenior = computeRooms(inputReader, rooms);
        int[][] adjacencyListStudent = computeRooms(inputReader, rooms);

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

    private static int[][] computeRooms(InputReader inputReader, int rooms) throws IOException {
        int[][] adjacencyList = new int[rooms][4];

        for (int roomID = 0; roomID < adjacencyList.length; roomID++) {
            for (int i = 0; i < 4; i++) {
                adjacencyList[roomID][i] = inputReader.nextInt() - 1;
            }
        }
        return adjacencyList;
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[8192];
        private int curChar, snumChars;

        private InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int snext() throws IOException {
            if (snumChars == -1)
                throw new InputMismatchException();
            if (curChar >= snumChars) {
                curChar = 0;
                snumChars = stream.read(buf);
                if (snumChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = snext();
            while (isSpaceChar(c)) {
                c = snext();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = snext();
            }
            int res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = snext();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == -1;
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
