package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 28/07/21.
 */
@SuppressWarnings("unchecked")
public class WakingUpBrain {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String line = FastReader.getLine();
        while (line != null) {
            int areas = Integer.parseInt(line);
            int connections = FastReader.nextInt();
            String initialAwakenAreas = FastReader.next();
            Map<Character, Integer> characterToIdMap = new HashMap<>();

            Set<Integer> awakeAreas = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                char character = initialAwakenAreas.charAt(i);
                int id = getId(characterToIdMap, character);
                awakeAreas.add(id);
            }

            List<Integer>[] adjacencyList = new List[areas];
            for (int i = 0; i < areas; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < connections; i++) {
                String connection = FastReader.next();
                int area1 = getId(characterToIdMap, connection.charAt(0));
                int area2 = getId(characterToIdMap, connection.charAt(1));
                adjacencyList[area1].add(area2);
                adjacencyList[area2].add(area1);
            }

            int years = countYearsToAwakeBrain(areas, adjacencyList, awakeAreas);
            if (years == -1) {
                outputWriter.printLine("THIS BRAIN NEVER WAKES UP");
            } else {
                outputWriter.printLine(String.format("WAKE UP IN, %d, YEARS", years));
            }

            line = FastReader.getLine();
            if (line != null && line.isEmpty()) {
                line = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int getId(Map<Character, Integer> characterToIdMap, char character) {
        if (!characterToIdMap.containsKey(character)) {
            characterToIdMap.put(character, characterToIdMap.size());
        }
        return characterToIdMap.get(character);
    }

    private static int countYearsToAwakeBrain(int areas, List<Integer>[] adjacencyList, Set<Integer> awakeAreas) {
        int years = 0;
        Set<Integer> newAwakenAreas;

        while (true) {
            newAwakenAreas = new HashSet<>();

            for (int area = 0; area < areas; area++) {
                if (awakeAreas.contains(area)) {
                    continue;
                }

                int connectionsAwaken = 0;
                for (int neighbor : adjacencyList[area]) {
                    if (awakeAreas.contains(neighbor)) {
                        connectionsAwaken++;
                    }
                }

                if (connectionsAwaken >= 3) {
                    newAwakenAreas.add(area);
                }
            }

            if (!newAwakenAreas.isEmpty()) {
                years++;
                awakeAreas.addAll(newAwakenAreas);
            } else {
                break;
            }
        }

        if (awakeAreas.size() == areas) {
            return years;
        } else {
            return -1;
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
