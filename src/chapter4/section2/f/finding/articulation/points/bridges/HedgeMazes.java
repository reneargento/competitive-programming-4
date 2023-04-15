package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 12/04/23.
 */
@SuppressWarnings("unchecked")
public class HedgeMazes {

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rooms = inputReader.readInt();
        int corridors = inputReader.readInt();
        int queries = inputReader.readInt();

        while (rooms != 0 || corridors != 0 || queries != 0) {
            List<Integer>[] adjacencyList = new List[rooms];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < corridors; i++) {
                int room1 = inputReader.readInt() - 1;
                int room2 = inputReader.readInt() - 1;
                adjacencyList[room1].add(room2);
                adjacencyList[room2].add(room1);
            }

            List<Integer>[] bridges = Bridges.findBridges(adjacencyList);
            int[] roomComponents = computeRoomComponents(bridges);

            for (int q = 0; q < queries; q++) {
                int startRoom = inputReader.readInt() - 1;
                int endRoom = inputReader.readInt() - 1;

                boolean isGoodChallenge = roomComponents[startRoom] == roomComponents[endRoom];
                outputWriter.printLine(isGoodChallenge ? "Y" : "N");
            }
            outputWriter.printLine("-");
            rooms = inputReader.readInt();
            corridors = inputReader.readInt();
            queries = inputReader.readInt();
        }
        outputWriter.flush();
    }

    private static int[] computeRoomComponents(List<Integer>[] adjacencyList) {
        int[] roomComponents = new int[adjacencyList.length];
        int componentID = 1;

        for (int roomID = 0; roomID < adjacencyList.length; roomID++) {
            if (roomComponents[roomID] == 0) {
                depthFirstSearch(adjacencyList, roomComponents, componentID, roomID);
                componentID++;
            }
        }
        return roomComponents;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, int[] roomComponents, int componentID,
                                         int roomID) {
        roomComponents[roomID] = componentID;

        for (int neighborRoom : adjacencyList[roomID]) {
            if (roomComponents[neighborRoom] == 0) {
                depthFirstSearch(adjacencyList, roomComponents, componentID, neighborRoom);
            }
        }
    }

    private static class Bridges {
        private static int count;
        private static int[] time;
        private static int[] low;

        private static List<Integer>[] findBridges(List<Integer>[] adjacencyList) {
            low = new int[adjacencyList.length];
            time = new int[adjacencyList.length];
            List<Integer>[] bridges = new List[adjacencyList.length];
            for (int i = 0; i < bridges.length; i++) {
                bridges[i] = new ArrayList<>();
            }

            Arrays.fill(low, -1);
            Arrays.fill(time, -1);

            for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
                if (time[vertex] == -1) {
                    dfs(adjacencyList, vertex, vertex, bridges);
                }
            }
            return bridges;
        }

        private static void dfs(List<Integer>[] adjacencyList, int currentVertex, int sourceVertex,
                                List<Integer>[] bridges) {
            time[currentVertex] = count;
            low[currentVertex] = count;
            count++;

            for (int neighbor : adjacencyList[currentVertex]) {
                if (time[neighbor] == -1) {
                    dfs(adjacencyList, neighbor, currentVertex, bridges);

                    low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                    if (low[neighbor] > time[currentVertex]) {
                        bridges[currentVertex].add(neighbor);
                        bridges[neighbor].add(currentVertex);
                    }
                } else if (neighbor != sourceVertex) {
                    low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
                }
            }
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buffer = new byte[1024];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buffer);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buffer[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }

            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }

                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
