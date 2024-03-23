package chapter4.section5.b.variants;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/24.
 */
public class DegreesOfSeparation {

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int people = FastReader.nextInt();
        int relationships = FastReader.nextInt();
        int networkID = 1;

        while (people != 0 || relationships != 0) {
            int[][] distances = new int[people][people];
            for (int personID = 0; personID < distances.length; personID++) {
                Arrays.fill(distances[personID], INFINITE);
                distances[personID][personID] = 0;
            }

            Map<String, Integer> nameToIDMap = new HashMap<>();
            for (int i = 0; i < relationships; i++) {
                int personID1 = getPersonID(nameToIDMap, FastReader.next());
                int personID2 = getPersonID(nameToIDMap, FastReader.next());
                distances[personID1][personID2] = 1;
                distances[personID2][personID1] = 1;
            }

            int maximumDegreeOfSeparation = computeMaximumDegreeOfSeparation(distances);
            outputWriter.print(String.format("Network %d: ", networkID));
            if (maximumDegreeOfSeparation == INFINITE) {
                outputWriter.printLine("DISCONNECTED");
            } else {
                outputWriter.printLine(maximumDegreeOfSeparation);
            }
            outputWriter.printLine();

            networkID++;
            people = FastReader.nextInt();
            relationships = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeMaximumDegreeOfSeparation(int[][] distances) {
        int maximumDegreeOfSeparation = 0;
        floydWarshall(distances);

        for (int personID1 = 0; personID1 < distances.length; personID1++) {
            for (int personID2 = 0; personID2 < distances.length; personID2++) {
                if (distances[personID1][personID2] == INFINITE) {
                    return INFINITE;
                }
                maximumDegreeOfSeparation = Math.max(maximumDegreeOfSeparation, distances[personID1][personID2]);
            }
        }
        return maximumDegreeOfSeparation;
    }

    private static int getPersonID(Map<String, Integer> nameToIDMap, String personName) {
        if (!nameToIDMap.containsKey(personName)) {
            nameToIDMap.put(personName, nameToIDMap.size());
        }
        return nameToIDMap.get(personName);
    }

    private static void floydWarshall(int[][] distances) {
        int vertices = distances.length;

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
