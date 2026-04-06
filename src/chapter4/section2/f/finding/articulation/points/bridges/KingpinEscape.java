package chapter4.section2.f.finding.articulation.points.bridges;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 16/04/23.
 */
@SuppressWarnings("unchecked")
public class KingpinEscape {

    private static class Route {
        int locationA;
        int locationB;

        public Route(int locationA, int locationB) {
            this.locationA = locationA;
            this.locationB = locationB;
        }
    }

    public static void main(String[] args) throws IOException {
        InputReader inputReader = new InputReader(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int hideouts = inputReader.nextInt();
        int headquartersLocation = inputReader.nextInt();
        List<Integer>[] adjacencyList = new List[hideouts];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < hideouts - 1; i++) {
            int locationA = inputReader.nextInt();
            int locationB = inputReader.nextInt();
            adjacencyList[locationA].add(locationB);
            adjacencyList[locationB].add(locationA);
        }

        List<Route> escapeRoutesToAdd = computeEscapeRoutesToAdd(adjacencyList);
        outputWriter.printLine(escapeRoutesToAdd.size());
        for (Route route : escapeRoutesToAdd) {
            outputWriter.printLine(route.locationA + " " + route.locationB);
        }
        outputWriter.flush();
    }

    private static List<Route> computeEscapeRoutesToAdd(List<Integer>[] adjacencyList) {
        List<Route> escapeRoutesToAdd = new ArrayList<>();
        List<Integer> leaves = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];

        depthFirstSearch(adjacencyList, visited, leaves, 0);
        int upperBound = leaves.size() / 2;

        for (int i = 0; i < upperBound; i++) {
            int leafToConnect = leaves.size() / 2 + i;
            escapeRoutesToAdd.add(new Route(leaves.get(i), leaves.get(leafToConnect)));
        }
        if (leaves.size() % 2 == 1) {
            escapeRoutesToAdd.add(new Route(leaves.get(leaves.size() - 1), leaves.get(0)));
        }
        return escapeRoutesToAdd;
    }

    private static void depthFirstSearch(List<Integer>[] adjacencyList, boolean[] visited, List<Integer> leaves,
                                         int locationID) {
        visited[locationID] = true;
        if (adjacencyList[locationID].size() == 1) {
            leaves.add(locationID);
        }

        for (int neighborID : adjacencyList[locationID]) {
            if (!visited[neighborID]) {
                depthFirstSearch(adjacencyList, visited, leaves, neighborID);
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
