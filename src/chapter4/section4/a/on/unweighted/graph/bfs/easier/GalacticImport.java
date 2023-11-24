package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
@SuppressWarnings("unchecked")
public class GalacticImport {

    private static class State {
        int planetID;
        int distanceFromEarth;

        public State(int planetID, int distanceFromEarth) {
            this.planetID = planetID;
            this.distanceFromEarth = distanceFromEarth;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int numberOfPlanets = Integer.parseInt(line);
            List<Integer>[] adjacencyList = new List[numberOfPlanets + 1];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            Map<Character, Integer> nameToIDMap = new HashMap<>();
            Map<Integer, Character> idToNameMap = new HashMap<>();
            getPlanetIDFromName(nameToIDMap, idToNameMap, '*');
            double[] exportValues = new double[27];

            for (int i = 0; i < adjacencyList.length - 1; i++) {
                char planetName = FastReader.next().charAt(0);
                int planetID = getPlanetIDFromName(nameToIDMap, idToNameMap, planetName);
                exportValues[planetID] = FastReader.nextDouble();

                String shippingLines = FastReader.next();
                connectPlanets(adjacencyList, planetID, nameToIDMap, idToNameMap, shippingLines);
            }

            char mostValuablePlanet = computeMostValuablePlanet(adjacencyList, idToNameMap, exportValues);
            outputWriter.printLine(String.format("Import from %c", mostValuablePlanet));
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static int getPlanetIDFromName(Map<Character, Integer> nameToIDMap, Map<Integer, Character> idToNameMap,
                                           char planetName) {
        if (nameToIDMap.containsKey(planetName)) {
            return nameToIDMap.get(planetName);
        }

        int planetID = nameToIDMap.size();
        nameToIDMap.put(planetName, planetID);
        idToNameMap.put(planetID, planetName);
        return planetID;
    }

    private static void connectPlanets(List<Integer>[] adjacencyList, int planetID, Map<Character, Integer> nameToIDMap,
                                       Map<Integer, Character> idToNameMap, String shippingLines) {
        for (int i = 0; i < shippingLines.length(); i++) {
            char planetName = shippingLines.charAt(i);
            int neighborPlanetID = getPlanetIDFromName(nameToIDMap, idToNameMap, planetName);
            adjacencyList[planetID].add(neighborPlanetID);
            adjacencyList[neighborPlanetID].add(planetID);
        }
    }

    private static char computeMostValuablePlanet(List<Integer>[] adjacencyList, Map<Integer, Character> idToNameMap,
                                                  double[] exportValues) {
        double[] finalExportValues = new double[adjacencyList.length];
        boolean[] visited = new boolean[27];

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(0, -1));
        visited[0] = true;

        while (!queue.isEmpty()) {
            State state = queue.poll();
            for (int neighborID : adjacencyList[state.planetID]) {
                if (visited[neighborID]) {
                    continue;
                }
                int distanceFromEarth = state.distanceFromEarth + 1;
                if (distanceFromEarth > 0) {
                    finalExportValues[neighborID] = exportValues[neighborID] * Math.pow(1 - 0.05, distanceFromEarth);
                } else {
                    finalExportValues[neighborID] = exportValues[neighborID];
                }

                visited[neighborID] = true;
                queue.offer(new State(neighborID, distanceFromEarth));
            }
        }
        return computeMostValuablePlanet(idToNameMap, finalExportValues);
    }

    private static char computeMostValuablePlanet(Map<Integer, Character> idToNameMap, double[] finalExportValues) {
        int mostValuablePlanetID = -1;
        double highestExportValue = -1;

        for (int planetID = 1; planetID < finalExportValues.length; planetID++) {
            if (finalExportValues[planetID] > highestExportValue
                    || (finalExportValues[planetID] == highestExportValue
                    && idToNameMap.get(planetID) < idToNameMap.get(mostValuablePlanetID))) {
                highestExportValue = finalExportValues[planetID];
                mostValuablePlanetID = planetID;
            }
        }
        return idToNameMap.get(mostValuablePlanetID);
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

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
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