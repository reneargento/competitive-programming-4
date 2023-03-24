package chapter4.section2.d.topological.sort;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/03/23.
 */
@SuppressWarnings("unchecked")
public class RareOrder {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String string = FastReader.getLine();
        List<String> strings = new ArrayList<>();
        Set<Character> letters = new HashSet<>();

        while (!string.equals("#")) {
            strings.add(string);
            for (char letter : string.toCharArray()) {
                letters.add(letter);
            }
            string = FastReader.getLine();
        }
        String lettersOrder = computeLettersOrder(strings, letters.size());
        outputWriter.printLine(lettersOrder);
        outputWriter.flush();
    }

    private static String computeLettersOrder(List<String> strings, int graphSize) {
        StringBuilder lettersOrder = new StringBuilder();
        Map<Character, Integer> letterToIDMap = new HashMap<>();
        char[] idToLetter = new char[graphSize];
        int[] inDegree = new int[graphSize];
        List<Integer>[] adjacencyList = computeGraph(strings, graphSize, inDegree, letterToIDMap, idToLetter);

        Queue<Integer> queue = new LinkedList<>();
        for (int letterID = 0; letterID < inDegree.length; letterID++) {
            if (inDegree[letterID] == 0) {
                queue.offer(letterID);
            }
        }

        while (!queue.isEmpty()) {
            int letterID = queue.poll();
            char letter = idToLetter[letterID];
            lettersOrder.append(letter);

            for (int neighborID : adjacencyList[letterID]) {
                inDegree[neighborID]--;
                if (inDegree[neighborID] == 0) {
                    queue.offer(neighborID);
                }
            }
        }
        return lettersOrder.toString();
    }

    private static List<Integer>[] computeGraph(List<String> strings, int graphSize, int[] inDegree,
                                                Map<Character, Integer> letterToIDMap, char[] idToLetter) {
        List<Integer>[] adjacencyList = new ArrayList[graphSize];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (int i = 0; i < strings.size() - 1; i++) {
            String string1 = strings.get(i);
            String string2 = strings.get(i + 1);

            int minSize = Math.min(string1.length(), string2.length());
            for (int j = 0; j < minSize; j++) {
                if (string1.charAt(j) != string2.charAt(j)) {
                    int letterID1 = getLetterID(letterToIDMap, idToLetter, string1.charAt(j));
                    int letterID2 = getLetterID(letterToIDMap, idToLetter, string2.charAt(j));
                    adjacencyList[letterID1].add(letterID2);
                    inDegree[letterID2]++;
                    break;
                }
            }
        }
        return adjacencyList;
    }

    private static int getLetterID(Map<Character, Integer> letterToIDMap, char[] idToLetter, char letter) {
        if (!letterToIDMap.containsKey(letter)) {
            int ID = letterToIDMap.size();
            letterToIDMap.put(letter, ID);
            idToLetter[ID] = letter;
        }
        return letterToIDMap.get(letter);
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
