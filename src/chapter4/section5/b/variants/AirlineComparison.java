package chapter4.section5.b.variants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/24.
 */
public class AirlineComparison {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
    
        for (int t = 0; t < tests; t++) {
            Map<Character, Integer> nameToIDMap = new HashMap<>();
            boolean[][] connections1 = computeConnections(nameToIDMap);
            boolean[][] connections2 = computeConnections(nameToIDMap);

            if (t > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(areEquivalent(connections1, connections2) ? "YES" : "NO");
        }
        outputWriter.flush();
    }

    private static boolean[][] computeConnections(Map<Character, Integer> nameToIDMap) throws IOException {
        boolean[][] connections = new boolean[30][30];
        for (int cityID = 0; cityID < connections.length; cityID++) {
            connections[cityID][cityID] = true;
        }

        int flightsCompany = FastReader.nextInt();
        for (int i = 0; i < flightsCompany; i++) {
            char cityName1 = FastReader.next().charAt(0);
            char cityName2 = FastReader.next().charAt(0);
            int cityID1 = getCityID(nameToIDMap, cityName1);
            int cityID2 = getCityID(nameToIDMap, cityName2);
            connections[cityID1][cityID2] = true;
        }
        return connections;
    }

    private static boolean areEquivalent(boolean[][] connections1, boolean[][] connections2) {
        floydWarshall(connections1);
        floydWarshall(connections2);

        for (int cityID1 = 0; cityID1 < connections1.length; cityID1++) {
            for (int cityID2 = 0; cityID2 < connections1.length; cityID2++) {
                if (connections1[cityID1][cityID2] != connections2[cityID1][cityID2]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int getCityID(Map<Character, Integer> nameToIDMap, Character cityName) {
        if (!nameToIDMap.containsKey(cityName)) {
            nameToIDMap.put(cityName, nameToIDMap.size());
        }
        return nameToIDMap.get(cityName);
    }

    private static void floydWarshall(boolean[][] connections) {
        int vertices = connections.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    connections[vertex2][vertex3] |= (connections[vertex2][vertex1] && connections[vertex1][vertex3]);
                }
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
            while (!tokenizer.hasMoreTokens() ) {
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
