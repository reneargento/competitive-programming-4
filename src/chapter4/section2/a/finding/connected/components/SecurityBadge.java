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
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Edge>[] rooms = new List[FastReader.nextInt()];
        int locks = FastReader.nextInt();
        int badges = FastReader.nextInt(); // Not used
        int sourceRoom = FastReader.nextInt() - 1;
        int destinationRoom = FastReader.nextInt() - 1;
        Set<Integer> lockEndpoints = new HashSet<>();

        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new ArrayList<>();
        }
        for (int i = 0; i < locks; i++) {
            int roomID1 = FastReader.nextInt() - 1;
            int roomID2 = FastReader.nextInt() - 1;
            int badgeLow = FastReader.nextInt();
            int badgeHigh = FastReader.nextInt();
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
