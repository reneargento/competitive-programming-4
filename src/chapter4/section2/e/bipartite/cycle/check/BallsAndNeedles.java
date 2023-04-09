package chapter4.section2.e.bipartite.cycle.check;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 06/04/23.
 */
@SuppressWarnings("unchecked")
public class BallsAndNeedles {

    private static class Coordinates3D {
        int x;
        int y;
        int z;

        public Coordinates3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Coordinates3D that = (Coordinates3D) other;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private static class Coordinates2D {
        int x;
        int y;

        public Coordinates2D(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Coordinates2D that = (Coordinates2D) other;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class Result {
        boolean hasTrueClosedChains;
        boolean hasFloorClosedChains;

        public Result(boolean hasTrueClosedChains, boolean hasFloorClosedChains) {
            this.hasTrueClosedChains = hasTrueClosedChains;
            this.hasFloorClosedChains = hasFloorClosedChains;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int knittingNeedles = FastReader.nextInt();
        Set<Integer>[] adjacencySet3D = new HashSet[knittingNeedles * 2];
        Set<Integer>[] adjacencySet2D = new HashSet[knittingNeedles * 2];
        for (int i = 0; i < adjacencySet3D.length; i++) {
            adjacencySet3D[i] = new HashSet<>();
            adjacencySet2D[i] = new HashSet<>();
        }

        Map<Coordinates3D, Integer> coordinate3DToIDMap = new HashMap<>();
        Map<Coordinates2D, Integer> coordinate2DToIDMap = new HashMap<>();
        for (int i = 0; i < knittingNeedles; i++) {
            int[] coordinate3DIDs = new int[2];
            int[] coordinate2DIDs = new int[2];

            for (int coordinate = 0; coordinate < 2; coordinate++) {
                int x = FastReader.nextInt();
                int y = FastReader.nextInt();
                int z = FastReader.nextInt();
                Coordinates3D coordinates3D = new Coordinates3D(x, y, z);
                if (!coordinate3DToIDMap.containsKey(coordinates3D)) {
                    coordinate3DToIDMap.put(coordinates3D, coordinate3DToIDMap.size());
                }
                coordinate3DIDs[coordinate] = coordinate3DToIDMap.get(coordinates3D);

                Coordinates2D coordinates2D = new Coordinates2D(x, y);
                if (!coordinate2DToIDMap.containsKey(coordinates2D)) {
                    coordinate2DToIDMap.put(coordinates2D, coordinate2DToIDMap.size());
                }
                coordinate2DIDs[coordinate] = coordinate2DToIDMap.get(coordinates2D);
            }

            if (coordinate3DIDs[0] != coordinate3DIDs[1]
                    && !adjacencySet3D[coordinate3DIDs[0]].contains(coordinate3DIDs[1])) {
                adjacencySet3D[coordinate3DIDs[0]].add(coordinate3DIDs[1]);
                adjacencySet3D[coordinate3DIDs[1]].add(coordinate3DIDs[0]);
            }
            if (coordinate2DIDs[0] != coordinate2DIDs[1]
                    && !adjacencySet2D[coordinate2DIDs[0]].contains(coordinate2DIDs[1])) {
                adjacencySet2D[coordinate2DIDs[0]].add(coordinate2DIDs[1]);
                adjacencySet2D[coordinate2DIDs[1]].add(coordinate2DIDs[0]);
            }
        }

        Result result = checkChains(adjacencySet3D, adjacencySet2D);
        if (result.hasTrueClosedChains) {
            outputWriter.printLine("True closed chains");
        } else {
            outputWriter.printLine("No true closed chains");
        }
        if (result.hasFloorClosedChains) {
            outputWriter.printLine("Floor closed chains");
        } else {
            outputWriter.printLine("No floor closed chains");
        }
        outputWriter.flush();
    }

    private static Result checkChains(Set<Integer>[] adjacencySet3D, Set<Integer>[] adjacencySet2D) {
        HasCycleGraph hasCycleGraph3D = new HasCycleGraph(adjacencySet3D);
        HasCycleGraph hasCycleGraph2D = new HasCycleGraph(adjacencySet2D);
        return new Result(hasCycleGraph3D.hasCycle(), hasCycleGraph2D.hasCycle());
    }

    private static class HasCycleGraph {
        private final boolean[] visited;
        private boolean hasCycle;

        public HasCycleGraph(Set<Integer>[] adjacent) {
            visited = new boolean[adjacent.length];

            for (int source = 0; source < adjacent.length; source++) {
                if (!visited[source]) {
                    dfs(adjacent, source, source);
                }
            }
        }

        private void dfs(Set<Integer>[] adjacent, int vertex, int origin) {
            visited[vertex] = true;

            if (adjacent[vertex] != null) {
                for (int neighbor : adjacent[vertex]) {
                    if (!visited[neighbor]) {
                        dfs(adjacent, neighbor, vertex);
                    } else if (neighbor != origin) {
                        hasCycle = true;
                    }
                }
            }
        }

        public boolean hasCycle() {
            return hasCycle;
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
