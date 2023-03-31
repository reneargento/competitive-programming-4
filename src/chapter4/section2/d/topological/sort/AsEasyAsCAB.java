package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/03/23.
 */
@SuppressWarnings("unchecked")
public class AsEasyAsCAB {

    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final String AMBIGUOUS = "AMBIGUOUS";

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        char lastCharacter = FastReader.next().charAt(0);
        String[] strings = new String[FastReader.nextInt()];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = FastReader.next();
        }

        String order = computeOrder(strings, lastCharacter);
        outputWriter.printLine(order);
        outputWriter.flush();
    }

    private static String computeOrder(String[] strings, char lastCharacter) {
        int lastCharacterID = getCharacterID(lastCharacter);
        int[] inDegrees = new int[lastCharacterID + 1];

        List<Integer>[] adjacencyList = buildGraph(strings, inDegrees);
        if (adjacencyList == null) {
            return IMPOSSIBLE;
        }
        return computeTopologicalOrder(adjacencyList, inDegrees);
    }

    private static List<Integer>[] buildGraph(String[] strings, int[] inDegrees) {
        List<Integer>[] adjacencyList = new ArrayList[inDegrees.length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < strings.length - 1; i++) {
            String string1 = strings[i];
            String string2 = strings[i + 1];

            // Very tricky edge case
            if (string1.startsWith(string2)) {
                return null;
            }

            int minimumSize = Math.min(string1.length(), string2.length());
            for (int c = 0; c < minimumSize; c++) {
                if (string1.charAt(c) != string2.charAt(c)) {
                    int characterID1 = getCharacterID(string1.charAt(c));
                    int characterID2 = getCharacterID(string2.charAt(c));
                    adjacencyList[characterID1].add(characterID2);
                    inDegrees[characterID2]++;
                    break;
                }
            }
        }
        return adjacencyList;
    }

    private static String computeTopologicalOrder(List<Integer>[] adjacencyList, int[] inDegrees) {
        StringBuilder order = new StringBuilder();
        boolean isAmbiguous = false;
        Queue<Integer> queue = new LinkedList<>();
        for (int characterID = 0; characterID < inDegrees.length; characterID++) {
            if (inDegrees[characterID] == 0) {
                queue.offer(characterID);
            }
        }

        if (queue.size() > 1) {
            isAmbiguous = true;
        }

        while (!queue.isEmpty()) {
            int characterID = queue.poll();
            char character = (char) ('a' + characterID);
            order.append(character);

            int neighborsAddedToQueue = 0;
            for (int neighborID : adjacencyList[characterID]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0) {
                    queue.offer(neighborID);
                    neighborsAddedToQueue++;
                }
            }

            if (neighborsAddedToQueue > 1) {
                isAmbiguous = true;
            }
        }

        if (order.length() != adjacencyList.length) {
            return IMPOSSIBLE;
        }
        if (isAmbiguous) {
            return AMBIGUOUS;
        }
        return order.toString();
    }

    private static int getCharacterID(char character) {
        return character - 'a';
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
