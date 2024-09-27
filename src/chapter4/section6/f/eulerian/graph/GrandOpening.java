package chapter4.section6.f.eulerian.graph;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/08/24.
 */
@SuppressWarnings("unchecked")
public class GrandOpening {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int enclosures = FastReader.nextInt();
        FastReader.nextInt(); // Not used
        Map<String, Integer> animalToIdMap = new HashMap<>();
        int[] animalIdToEnclosureId = new int[enclosures];
        List<Integer>[] enclosuresWithAnimals = new List[enclosures];
        for (int i = 0; i < enclosuresWithAnimals.length; i++) {
            enclosuresWithAnimals[i] = new ArrayList<>();
        }

        boolean isFalseAlarm = true;
        for (int enclosureId = 0; enclosureId < enclosures; enclosureId++) {
            int correctAnimalId = getAnimalId(FastReader.next(), animalToIdMap);
            animalIdToEnclosureId[correctAnimalId] = enclosureId;

            int animalsInEnclosure = FastReader.nextInt();
            for (int a = 0; a < animalsInEnclosure; a++) {
                int animalId = getAnimalId(FastReader.next(), animalToIdMap);
                if (animalId != correctAnimalId) {
                    isFalseAlarm = false;
                    enclosuresWithAnimals[enclosureId].add(animalId);
                }
            }
        }

        if (isFalseAlarm) {
            outputWriter.printLine("FALSE ALARM");
        } else {
            boolean isPossible = isPossibleToRestore(animalIdToEnclosureId, enclosuresWithAnimals);
            outputWriter.printLine(isPossible ? "POSSIBLE" : "IMPOSSIBLE");
        }
        outputWriter.flush();
    }

    private static boolean isPossibleToRestore(int[] animalIdToEnclosureId, List<Integer>[] enclosuresWithAnimals) {
        List<Integer>[] adjacent = new List[animalIdToEnclosureId.length];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int enclosureId = 0; enclosureId < enclosuresWithAnimals.length; enclosureId++) {
            for (int animalId : enclosuresWithAnimals[enclosureId]) {
                int animalEnclosureId = animalIdToEnclosureId[animalId];
                adjacent[enclosureId].add(animalEnclosureId);
            }
        }
        return eulerPathExists(adjacent);
    }

    private static int getAnimalId(String animal, Map<String, Integer> animalToIdMap) {
        if (!animalToIdMap.containsKey(animal)) {
            animalToIdMap.put(animal, animalToIdMap.size());
        }
        return animalToIdMap.get(animal);
    }

    private static boolean eulerPathExists(List<Integer>[] adjacent) {
        int edges = 0;
        int[] inDegrees = new int[adjacent.length];
        int[] outDegrees = new int[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();

            for (int neighbor : adjacent[vertex]) {
                outDegrees[vertex]++;
                inDegrees[neighbor]++;
            }
        }

        // A graph with no edges is considered to have an Eulerian path
        if (edges == 0) {
            return true;
        }

        int startVertexCandidates = 0;
        int endVertexCandidates = 0;
        int startVertex = -1;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (inDegrees[vertex] == outDegrees[vertex]) {
                if (inDegrees[vertex] > 0 && startVertex == -1) {
                    startVertex = vertex;
                }
            } else {
                if (inDegrees[vertex] - 1 == outDegrees[vertex]) {
                    endVertexCandidates++;
                } else if (inDegrees[vertex] + 1 == outDegrees[vertex]) {
                    startVertexCandidates++;
                    startVertex = vertex;
                } else {
                    return false;
                }
            }
        }

        boolean hasEulerCycle = startVertexCandidates == 0 && endVertexCandidates == 0;
        boolean hasEulerPath = startVertexCandidates == 1 && endVertexCandidates == 1;
        if (!hasEulerCycle && !hasEulerPath) {
            return false;
        }

        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(startVertex);

        Deque<Integer> eulerPath = new ArrayDeque<>();
        int[] index = new int[adjacent.length];

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.peek();
            int indexValue = index[vertex];

            if (indexValue < adjacent[vertex].size()) {
                int neighborVertex = adjacent[vertex].get(indexValue);
                dfsStack.push(neighborVertex);
                index[vertex]++;
            } else {
                // Push vertex with no more leaving edges to the Euler path
                eulerPath.push(vertex);
                dfsStack.pop();
            }
        }

        // For each edge visited, we visited a vertex.
        // Add 1 because the first and last vertices are the same (in the case of an Euler cycle)
        // or because the vertex with one more in-degree than out-degree is visited twice (in the case of an Euler path)
        return eulerPath.size() == edges + 1;
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
