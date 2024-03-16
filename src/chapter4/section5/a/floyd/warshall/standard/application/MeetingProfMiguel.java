package chapter4.section5.a.floyd.warshall.standard.application;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 14/03/24.
 */
public class MeetingProfMiguel {

    private static class Result {
        int minimumEnergy;
        List<Character> cities;

        public Result(int minimumEnergy, List<Character> cities) {
            this.minimumEnergy = minimumEnergy;
            this.cities = cities;
        }
    }

    private static final int INFINITE = 10000000;
    private static final int MAX_CITIES = 50;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int streets = FastReader.nextInt();

        while (streets != 0) {
            int[][] edgeWeightedDigraphYoung = createGraph();
            int[][] edgeWeightedDigraphOld = createGraph();
            Map<Character, Integer> streetNameToID = new HashMap<>();
            char[] streetIDToName = new char[MAX_CITIES];

            for (int i = 0; i < streets; i++) {
                char ageType = FastReader.next().charAt(0);
                char directionType = FastReader.next().charAt(0);
                int streetID1 = getStreetID(streetNameToID, streetIDToName, FastReader.next().charAt(0));
                int streetID2 = getStreetID(streetNameToID, streetIDToName, FastReader.next().charAt(0));
                int energyCost = FastReader.nextInt();

                int[][] edgeWeightedGraph;
                if (ageType == 'Y') {
                    edgeWeightedGraph = edgeWeightedDigraphYoung;
                } else {
                    edgeWeightedGraph = edgeWeightedDigraphOld;
                }

                edgeWeightedGraph[streetID1][streetID2] = energyCost;
                if (directionType == 'B') {
                    edgeWeightedGraph[streetID2][streetID1] = energyCost;
                }
            }
            int shahriarCityID = getStreetID(streetNameToID, streetIDToName, FastReader.next().charAt(0));
            int miguelCityID = getStreetID(streetNameToID, streetIDToName, FastReader.next().charAt(0));

            Result result = computePlacesToMeet(edgeWeightedDigraphYoung, edgeWeightedDigraphOld, streetIDToName,
                    shahriarCityID, miguelCityID);
            if (result == null) {
                outputWriter.printLine("You will never meet.");
            } else {
                outputWriter.print(result.minimumEnergy);
                for (int i = 0; i < result.cities.size(); i++) {
                    outputWriter.print(" " + result.cities.get(i));
                }
                outputWriter.printLine();
            }
            streets = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static Result computePlacesToMeet(int[][] edgeWeightedDigraphYoung, int[][] edgeWeightedDigraphOld,
                                              char[] streetIDToName, int shahriarCityID, int miguelCityID) {
        int minimumEnergy = INFINITE;
        List<Character> citiesToMeet = new ArrayList<>();

        FloydWarshall floydWarshallYoung = new FloydWarshall(edgeWeightedDigraphYoung);
        FloydWarshall floydWarshallOld = new FloydWarshall(edgeWeightedDigraphOld);

        for (int cityID = 0; cityID < edgeWeightedDigraphYoung.length; cityID++) {
            int totalEnergy = floydWarshallYoung.distance(shahriarCityID, cityID) +
                    floydWarshallOld.distance(miguelCityID, cityID);
            if (totalEnergy < minimumEnergy) {
                minimumEnergy = totalEnergy;
                citiesToMeet = new ArrayList<>();
                citiesToMeet.add(streetIDToName[cityID]);
            } else if (totalEnergy == minimumEnergy) {
                citiesToMeet.add(streetIDToName[cityID]);
            }
        }

        if (minimumEnergy == INFINITE) {
            return null;
        }
        Collections.sort(citiesToMeet);
        return new Result(minimumEnergy, citiesToMeet);
    }

    private static int[][] createGraph() {
        int[][] edgeWeightedDigraph = new int[MAX_CITIES][MAX_CITIES];
        for (int[] values : edgeWeightedDigraph) {
            Arrays.fill(values, INFINITE);
        }
        return edgeWeightedDigraph;
    }

    private static int getStreetID(Map<Character, Integer> streetNameToID, char[] streetIDToName, char streetName) {
        if (!streetNameToID.containsKey(streetName)) {
            int streetID = streetNameToID.size();
            streetNameToID.put(streetName, streetID);
            streetIDToName[streetID] = streetName;
        }
        return streetNameToID.get(streetName);
    }

    private static class FloydWarshall {
        private final int[][] distances;        // length of shortest v->w path

        public FloydWarshall(int[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;
            distances = new int[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    int distance = edgeWeightedDigraph[vertex1][vertex2];
                    distances[vertex1][vertex2] = distance;
                }

                // In case of self-loops
                if (distances[vertex1][vertex1] >= 0) {
                    distances[vertex1][vertex1] = 0;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                        }
                    }
                }
            }
        }

        public int distance(int source, int target) {
            return distances[source][target];
        }

        public boolean hasPath(int source, int target) {
            return distances[source][target] != INFINITE;
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
