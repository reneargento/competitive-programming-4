package chapter2.section3.e.hash.table.map.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/05/21.
 */
public class SQFProblems {

    private static class Category {
        String name;
        Map<String, Boolean> keywords;
        int keywordsRequired;
        int keywordsMatched;

        public Category(String name, Map<String, Boolean> keywords, int keywordsRequired) {
            this.name = name;
            this.keywords = keywords;
            this.keywordsRequired = keywordsRequired;
        }

        void tryToMatchKeyword(String keyword) {
            if (keywords.containsKey(keyword) && !keywords.get(keyword)) {
                keywords.put(keyword, true);
                keywordsMatched++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int categoriesNumber = FastReader.nextInt();
            Category[] categories = new Category[categoriesNumber];

            for (int c = 0; c < categories.length; c++) {
                String categoryName = FastReader.next();
                int keywordsNumber = FastReader.nextInt();
                int keywordsRequired = FastReader.nextInt();

                Map<String, Boolean> keywords = new HashMap<>();
                for (int i = 0; i < keywordsNumber; i++) {
                    String keyword = FastReader.next();
                    keywords.put(keyword, false);
                }
                categories[c] = new Category(categoryName, keywords, keywordsRequired);
            }

            String problemLine = FastReader.getLine();
            while (problemLine != null && !problemLine.equals("")) {
                categorize(categories, problemLine);
                problemLine = FastReader.getLine();
            }
            printResult(categories, outputWriter);
        }
        outputWriter.flush();
    }

    private static void categorize(Category[] categories, String problemLine) {
        problemLine = problemLine.replaceAll("[^a-zA-Z]", " ");
        List<String> words = getWords(problemLine);
        for (String word : words) {
            for (Category category : categories) {
                category.tryToMatchKeyword(word);
            }
        }
    }

    private static void printResult(Category[] categories, OutputWriter outputWriter) {
        StringJoiner categoriesFound = new StringJoiner(",");
        boolean isSQF = true;

        for (Category category : categories) {
            if (category.keywordsMatched >= category.keywordsRequired) {
                categoriesFound.add(category.name);
                isSQF = false;
            }
        }

        if (isSQF) {
            outputWriter.printLine("SQF Problem.");
        } else {
            outputWriter.printLine(categoriesFound.toString());
        }
    }

    private static List<String> getWords(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                if (word.length() > 0) {
                    words.add(word.toString());
                    word = new StringBuilder();
                }
            } else {
                word.append(character);
            }
        }
        if (word.length() > 0) {
            words.add(word.toString());
        }
        return words;
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
