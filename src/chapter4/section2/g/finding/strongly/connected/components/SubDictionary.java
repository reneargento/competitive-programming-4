package chapter4.section2.g.finding.strongly.connected.components;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/04/23.
 */
@SuppressWarnings("unchecked")
public class SubDictionary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int words = FastReader.nextInt();

        while (words != 0) {
            Map<String, Integer> wordNameToIDMap = new HashMap<>();
            String[] wordIDToName = new String[words];
            int[] inDegrees = new int[words];
            List<Integer>[] adjacencyList = new List[words];
            for (int i = 0; i < adjacencyList.length; i++) {
                adjacencyList[i] = new ArrayList<>();
            }

            for (int i = 0; i < words; i++) {
                String[] data = FastReader.getLine().split(" ");
                String wordName = data[0];
                int wordID = getWordID(wordName, wordNameToIDMap, wordIDToName);

                for (int j = 1; j < data.length; j++) {
                    int otherWordID = getWordID(data[j], wordNameToIDMap, wordIDToName);
                    adjacencyList[wordID].add(otherWordID);
                    inDegrees[otherWordID]++;
                }
            }

            List<String> smallestSubDictionary = computeSmallestSubDictionary(adjacencyList, wordIDToName, inDegrees);
            outputWriter.printLine(smallestSubDictionary.size());
            outputWriter.print(smallestSubDictionary.get(0));
            for (int i = 1; i < smallestSubDictionary.size(); i++) {
                outputWriter.print(" " + smallestSubDictionary.get(i));
            }
            outputWriter.printLine();
            words = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static List<String> computeSmallestSubDictionary(List<Integer>[] adjacencyList, String[] wordIDToName,
                                                             int[] inDegrees) {
        List<String> smallestSubDictionary = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] canBeComputed = new boolean[adjacencyList.length];

        for (int wordID = 0; wordID < adjacencyList.length; wordID++) {
            if (inDegrees[wordID] == 0) {
                queue.offer(wordID);
                canBeComputed[wordID] = true;
            }
        }

        while (!queue.isEmpty()) {
            int wordID = queue.poll();

            for (int neighborID : adjacencyList[wordID]) {
                inDegrees[neighborID]--;
                if (inDegrees[neighborID] == 0 && !canBeComputed[neighborID]) {
                    canBeComputed[neighborID] = true;
                    queue.offer(neighborID);
                }
            }
        }

        for (int wordID = 0; wordID < adjacencyList.length; wordID++) {
            if (!canBeComputed[wordID]) {
                smallestSubDictionary.add(wordIDToName[wordID]);
            }
        }
        Collections.sort(smallestSubDictionary);
        return smallestSubDictionary;
    }

    private static int getWordID(String wordName, Map<String, Integer> wordNameToIDMap, String[] wordIDToName) {
        if (!wordNameToIDMap.containsKey(wordName)) {
            int wordID = wordNameToIDMap.size();
            wordNameToIDMap.put(wordName, wordID);
            wordIDToName[wordID] = wordName;
        }
        return wordNameToIDMap.get(wordName);
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

        public void flush() {
            writer.flush();
        }
    }
}
