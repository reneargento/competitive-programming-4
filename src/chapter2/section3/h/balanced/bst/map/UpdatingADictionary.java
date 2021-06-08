package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/06/21.
 */
public class UpdatingADictionary {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Map<String, String> oldDictionary = readDictionary(FastReader.getLine());
            Map<String, String> newDictionary = readDictionary(FastReader.getLine());
            printChanges(oldDictionary, newDictionary, outputWriter);
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static Map<String, String> readDictionary(String data) {
        Map<String, String> dictionary = new HashMap<>();
        if (!data.isEmpty() && data.length() >= 3) {
            String[] pairs = data.substring(1, data.length() - 1).split(",");
            for (String pair : pairs) {
                String[] pairData = pair.split(":");
                dictionary.put(pairData[0], pairData[1]);
            }
        }
        return dictionary;
    }

    private static void printChanges(Map<String, String> oldDictionary, Map<String, String> newDictionary,
                                     OutputWriter outputWriter) {
        List<String> newKeys = new ArrayList<>();
        List<String> removedKeys = new ArrayList<>();
        List<String> updatedKeys = new ArrayList<>();

        for (String key : oldDictionary.keySet()) {
            if (newDictionary.containsKey(key)) {
                String oldValue = oldDictionary.get(key);
                String newValue = newDictionary.get(key);
                if (!oldValue.equals(newValue)) {
                    updatedKeys.add(key);
                }
            } else {
                removedKeys.add(key);
            }
        }

        for (String key : newDictionary.keySet()) {
            if (!oldDictionary.containsKey(key)) {
                newKeys.add(key);
            }
        }

        if (newKeys.isEmpty() && removedKeys.isEmpty() && updatedKeys.isEmpty()) {
            outputWriter.printLine("No changes");
        } else {
            if (!newKeys.isEmpty()) {
                outputWriter.print("+");
                printKeys(newKeys, outputWriter);
            }
            if (!removedKeys.isEmpty()) {
                outputWriter.print("-");
                printKeys(removedKeys, outputWriter);
            }
            if (!updatedKeys.isEmpty()) {
                outputWriter.print("*");
                printKeys(updatedKeys, outputWriter);
            }
        }
    }

    private static void printKeys(List<String> keys, OutputWriter outputWriter) {
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            outputWriter.print(keys.get(i));

            if (i != keys.size() - 1) {
                outputWriter.print(",");
            }
        }
        outputWriter.printLine();
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
