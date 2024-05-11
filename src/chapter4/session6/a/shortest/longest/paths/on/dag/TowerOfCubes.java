package chapter4.session6.a.shortest.longest.paths.on.dag;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 09/05/24.
 */
@SuppressWarnings("unchecked")
public class TowerOfCubes {

    private static class Cube {
        int[] colors;

        public Cube(int[] colors) {
            this.colors = colors;
        }
    }

    private static class SelectedCube {
        int cubeID;
        String topFaceSide;

        public SelectedCube(int cubeID, String topFaceSide) {
            this.cubeID = cubeID;
            this.topFaceSide = topFaceSide;
        }
    }

    private static class Edge {
        int cubeID1;
        int cubeID2;
        int cubeID1Side;
        int cubeID2Side;
        int weight;

        public Edge(int cubeID1, int cubeID2, int cubeID1Side, int cubeID2Side, int weight) {
            this.cubeID1 = cubeID1;
            this.cubeID2 = cubeID2;
            this.cubeID1Side = cubeID1Side;
            this.cubeID2Side = cubeID2Side;
            this.weight = weight;
        }
    }

    private static final String[] sides = { "front", "back", "left", "right", "top", "bottom" };
    private static final int INFINITE = -1000000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int cubesNumber = FastReader.nextInt();
        int caseID = 1;

        while (cubesNumber != 0) {
            Cube[] cubes = new Cube[cubesNumber];

            for (int cubeID = 0; cubeID < cubes.length; cubeID++) {
                int[] colors = new int[6];
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = FastReader.nextInt();
                }
                cubes[cubeID] = new Cube(colors);
            }

            List<SelectedCube> tower = buildTower(cubes);

            if (caseID > 1) {
                outputWriter.printLine();
            }
            outputWriter.printLine(String.format("Case #%d", caseID));
            outputWriter.printLine(tower.size());
            for (SelectedCube cube : tower) {
                outputWriter.printLine(cube.cubeID + " " + cube.topFaceSide);
            }

            caseID++;
            cubesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<SelectedCube> buildTower(Cube[] cubes) {
        List<Edge>[][] adjacent = buildGraph(cubes);
        DAGLongestPathWeighted longestPath = new DAGLongestPathWeighted(adjacent);
        return longestPath.getLongestPath();
    }

    private static int getOppositeSideID(int sideID) {
        if (sideID % 2 == 0) {
            return sideID + 1;
        }
        return sideID - 1;
    }

    private static List<Edge>[][] buildGraph(Cube[] cubes) {
        List<Edge>[][] adjacent = new List[cubes.length][6];
        for (int i = 0; i < adjacent.length; i++) {
            for (int sideID = 0; sideID < adjacent[0].length; sideID++) {
                adjacent[i][sideID] = new ArrayList<>();
            }
        }

        for (int cubeID1 = 0; cubeID1 < cubes.length; cubeID1++) {
            for (int cubeID2 = cubeID1 + 1; cubeID2 < cubes.length; cubeID2++) {
                for (int cube1SideID = 0; cube1SideID < 6; cube1SideID++) {
                    for (int cube2SideID = 0; cube2SideID < 6; cube2SideID++) {
                        if (cubes[cubeID1].colors[cube1SideID] == cubes[cubeID2].colors[cube2SideID]) {
                            Edge edge = new Edge(cubeID1, cubeID2, cube1SideID, cube2SideID, -1);
                            adjacent[cubeID1][cube1SideID].add(edge);
                        }
                    }
                }
            }
        }
        return adjacent;
    }

    private static class DAGLongestPathWeighted {
        private static int[][] distTo;
        private static Edge[][] edgeTo;

        public DAGLongestPathWeighted(List<Edge>[][] adjacent) {
            distTo = new int[adjacent.length][6];
            edgeTo = new Edge[adjacent.length][6];
            for (int[] values : distTo) {
                Arrays.fill(values, -1);
            }

            for (int vertexID = 0; vertexID < adjacent.length; vertexID++) {
                relax(adjacent, vertexID);
            }
        }

        private void relax(List<Edge>[][] adjacent, int vertex) {
            for (int sideID = 0; sideID < distTo[0].length; sideID++) {
                int oppositeSideID = getOppositeSideID(sideID);

                for (Edge edge : adjacent[vertex][oppositeSideID]) {
                    int neighbor = edge.cubeID2;

                    if (distTo[neighbor][edge.cubeID2Side] > distTo[vertex][sideID] + edge.weight) {
                        distTo[neighbor][edge.cubeID2Side] = distTo[vertex][sideID] + edge.weight;
                        edgeTo[neighbor][edge.cubeID2Side] = edge;
                    }
                }
            }
        }

        public List<SelectedCube> pathTo(int vertex, int sideID) {
            Stack<SelectedCube> deque = new Stack<>();
            for (Edge edge = edgeTo[vertex][sideID]; edge != null;
                 edge = edgeTo[edge.cubeID1][getOppositeSideID(edge.cubeID1Side)]) {

                String topFaceSide = sides[edge.cubeID2Side];
                deque.push(new SelectedCube(edge.cubeID2 + 1, topFaceSide));
                int cubeID1OppositeSide = getOppositeSideID(edge.cubeID1Side);

                if (edgeTo[edge.cubeID1][cubeID1OppositeSide] == null) {
                    String topFaceSideOtherCube = sides[cubeID1OppositeSide];
                    deque.push(new SelectedCube(edge.cubeID1 + 1, topFaceSideOtherCube));
                }
            }

            List<SelectedCube> selectedCubes = new ArrayList<>();
            while (!deque.isEmpty()) {
                selectedCubes.add(deque.pop());
            }
            return selectedCubes;
        }

        public List<SelectedCube> getLongestPath() {
            int furthestVertex = -1;
            int sideSelected = -1;
            double longestDistance = INFINITE;

            for (int vertex = 0; vertex < distTo.length; vertex++) {
                for (int sideID = 0; sideID < distTo[0].length; sideID++) {
                    if (-distTo[vertex][sideID] > longestDistance) {
                        longestDistance = -distTo[vertex][sideID];
                        furthestVertex = vertex;
                        sideSelected = sideID;
                    }
                }
            }
            return pathTo(furthestVertex, sideSelected);
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
