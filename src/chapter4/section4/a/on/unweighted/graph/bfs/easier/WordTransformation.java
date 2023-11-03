package chapter4.section4.a.on.unweighted.graph.bfs.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/10/23.
 */
public class WordTransformation {

    private static class State {
        String currentWord;
        int transformations;

        public State(String currentWord, int transformations) {
            this.currentWord = currentWord;
            this.transformations = transformations;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int tests = FastReader.nextInt();
        FastReader.getLine();

        for (int t = 0; t < tests; t++) {
            if (t > 0) {
                outputWriter.printLine();
            }
            List<String> dictionary = new ArrayList<>();

            String word = FastReader.getLine();
            while (!word.equals("*")) {
                dictionary.add(word);
                word = FastReader.getLine();
            }

            String pair = FastReader.getLine();
            while (pair != null && !pair.equals("")) {
                String[] words = pair.split(" ");
                int transformations = countTransformations(dictionary, words[0], words[1]);
                outputWriter.printLine(String.format("%s %s %s", words[0], words[1], transformations));
                pair = FastReader.getLine();
            }
        }
        outputWriter.flush();
    }

    private static int countTransformations(List<String> dictionary, String startWord, String endWord) {
        State state = new State(startWord, 0);
        Queue<State> queue = new LinkedList<>();
        queue.offer(state);

        Set<String> visited = new HashSet<>();
        visited.add(startWord);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.currentWord.equals(endWord)) {
                return currentState.transformations;
            }

            for (String word : dictionary) {
                if (visited.contains(word)) {
                    continue;
                }
                if (differBy1Char(currentState.currentWord, word)) {
                    visited.add(word);
                    queue.offer(new State(word, currentState.transformations + 1));
                }
            }
        }
        return -1;
    }

    private static boolean differBy1Char(String word1, String word2) {
        int differences = 0;

        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                differences++;
            }
            if (differences > 1) {
                break;
            }
        }
        return differences == 1;
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