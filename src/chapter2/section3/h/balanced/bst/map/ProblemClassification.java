package chapter2.section3.h.balanced.bst.map;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 11/06/21.
 */
public class ProblemClassification {

    private static class HighestFrequencyParam {
        int highestFrequency;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int categories = FastReader.nextInt();
        Map<String, List<String>> wordToCategoriesMap = new HashMap<>();
        Set<String> allCategories = new HashSet<>();

        for (int i = 0; i < categories; i++) {
            String category = FastReader.next();
            int wordsInCategory = FastReader.nextInt();
            allCategories.add(category);

            for (int w = 0; w < wordsInCategory; w++) {
                String word = FastReader.next();
                if (!wordToCategoriesMap.containsKey(word)) {
                    wordToCategoriesMap.put(word, new ArrayList<>());
                }
                wordToCategoriesMap.get(word).add(category);
            }
        }

        HighestFrequencyParam highestFrequencyParam = new HighestFrequencyParam();
        Map<String, Integer> categoryFrequencyMap = countKeywords(wordToCategoriesMap, highestFrequencyParam);
        List<String> classifications = classifyProblem(categoryFrequencyMap, highestFrequencyParam.highestFrequency,
                allCategories);
        for (String category : classifications) {
            outputWriter.printLine(category);
        }
        outputWriter.flush();
    }

    private static Map<String, Integer> countKeywords(Map<String, List<String>> wordToCategoriesMap,
                                                      HighestFrequencyParam highestFrequencyParam) throws IOException {
        Map<String, Integer> categoryFrequencyMap = new HashMap<>();
        int highestFrequency = 0;

        String statementLine = FastReader.getLine();
        while (statementLine != null) {
            String[] words = statementLine.split(" ");
            for (String word : words) {
                if (wordToCategoriesMap.containsKey(word)) {
                    for (String category : wordToCategoriesMap.get(word)) {
                        int frequency = categoryFrequencyMap.getOrDefault(category, 0);
                        int newFrequency = frequency + 1;
                        categoryFrequencyMap.put(category, newFrequency);
                        highestFrequency = Math.max(highestFrequency, newFrequency);
                    }
                }
            }
            statementLine = FastReader.getLine();
        }
        highestFrequencyParam.highestFrequency = highestFrequency;
        return categoryFrequencyMap;
    }

    private static List<String> classifyProblem(Map<String, Integer> categoryFrequencyMap,
                                                int highestFrequency, Set<String> allCategories) {
        List<String> categories = new ArrayList<>();
        for (Map.Entry<String, Integer> categoryEntry : categoryFrequencyMap.entrySet()) {
            if (categoryEntry.getValue() == highestFrequency) {
                categories.add(categoryEntry.getKey());
            }
        }
        if (categories.isEmpty()) {
            categories = new ArrayList<>(allCategories);
        }
        Collections.sort(categories);
        return categories;
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
