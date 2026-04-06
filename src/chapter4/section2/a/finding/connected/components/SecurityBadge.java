package chapter4.section2.a.finding.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/03/23.
 */
@SuppressWarnings("unchecked")
public class SecurityBadge {

    private static class Edge {
        int neighborRoomID;
        int badgeLow;
        int badgeHigh;

        public Edge(int neighborRoomID, int badgeLow, int badgeHigh) {
            this.neighborRoomID = neighborRoomID;
            this.badgeLow = badgeLow;
            this.badgeHigh = badgeHigh;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Edge>[] rooms = new List[inputReader.nextInt()];
        int locks = inputReader.nextInt();
        int badges = inputReader.nextInt(); // Not used
        int sourceRoom = inputReader.nextInt() - 1;
        int destinationRoom = inputReader.nextInt() - 1;
        Set<Integer> lockEndpoints = new HashSet<>();

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new ArrayList<>();
        }
        for (int i = 0; i < locks; i++) {
            int roomID1 = inputReader.nextInt() - 1;
            int roomID2 = inputReader.nextInt() - 1;
            int badgeLow = inputReader.nextInt();
            int badgeHigh = inputReader.nextInt();
            rooms[roomID1].add(new Edge(roomID2, badgeLow, badgeHigh));
            lockEndpoints.add(badgeLow);
            lockEndpoints.add(badgeHigh + 1);
        }
        List<Integer> locksList = new ArrayList<>(lockEndpoints);
        Collections.sort(locksList);

        int allowedBadges = computeAllowedBadges(rooms, locksList, sourceRoom, destinationRoom);
        outputWriter.printLine(allowedBadges);
        outputWriter.flush();
    }

    private static int computeAllowedBadges(List<Edge>[] rooms, List<Integer> locksList, int sourceRoom,
                                            int destinationRoom) {
        int allowedBadges = 0;
        int[] visited = new int[rooms.length];
        int visitID = 1;

        for (int i = 0; i < locksList.size() - 1; i++) {
            int lockEndpoint = locksList.get(i);
            depthFirstSearch(rooms, visited, visitID, lockEndpoint, destinationRoom, sourceRoom);

            if (visited[destinationRoom] == visitID) {
                allowedBadges += (locksList.get(i + 1) - lockEndpoint);
            }
            visitID++;
        }
        return allowedBadges;
    }

    private static void depthFirstSearch(List<Edge>[] rooms, int[] visited, int visitID, int lockEndpoint,
                                         int destinationRoom, int currentRoom) {
        visited[currentRoom] = visitID;
        if (currentRoom == destinationRoom) {
            return;
        }

        for (Edge edge : rooms[currentRoom]) {
            if (visited[edge.neighborRoomID] != visitID
                    && edge.badgeLow <= lockEndpoint && lockEndpoint <= edge.badgeHigh) {
                depthFirstSearch(rooms, visited, visitID, lockEndpoint, destinationRoom, edge.neighborRoomID);
            }
        }
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
