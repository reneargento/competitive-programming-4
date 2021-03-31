package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/03/21.
 */
public class JoinStrings {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        int stringsNumber = FastReader.nextInt();
        String[] strings = new String[stringsNumber];

        for (int i = 0; i < strings.length; i++) {
            strings[i] = FastReader.next();
        }

        Set<Integer> childWords = new HashSet<>();
        int[] nextWords = computeNextWords(stringsNumber, childWords);
        int root = getRoot(stringsNumber, childWords);
        printWords(root, strings, nextWords);
    }

    private static int[] computeNextWords(int stringsNumber, Set<Integer> childWords) throws IOException {
        int[] nextWords = new int[stringsNumber + 1];
        int[] lastWordInChain = new int[stringsNumber + 1];

        for (int i = 0; i < stringsNumber - 1; i++) {
            int parent = FastReader.nextInt();
            int next = FastReader.nextInt();

            int realParent = parent;
            if (lastWordInChain[parent] != 0) {
                realParent = lastWordInChain[parent];
            }

            int realNextParent = next;
            if (lastWordInChain[next] != 0) {
                realNextParent = lastWordInChain[next];
            }
            lastWordInChain[parent] = realNextParent;

            nextWords[realParent] = next;
            childWords.add(next);
        }
        return nextWords;
    }

    private static int getRoot(int stringsNumber, Set<Integer> childWords) {
        for (int i = 1; i <= stringsNumber; i++) {
            if (!childWords.contains(i)) {
                return i;
            }
        }
        return -1;
    }

    private static void printWords(int root, String[] strings, int[] nextWords) {
        OutputWriter outputWriter = new OutputWriter(System.out);
        int nextWord = root;

        while (nextWord != 0) {
            outputWriter.print(strings[nextWord - 1]);
            nextWord = nextWords[nextWord];
        }
        outputWriter.printLine();
        outputWriter.flush();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
