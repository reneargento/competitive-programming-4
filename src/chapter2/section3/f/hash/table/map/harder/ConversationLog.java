package chapter2.section3.f.hash.table.map.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/05/21.
 */
public class ConversationLog {

    private static class WordFrequency implements Comparable<WordFrequency> {
        String word;
        int frequency;

        public WordFrequency(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(WordFrequency other) {
            if (frequency != other.frequency) {
                return Integer.compare(other.frequency, frequency);
            }
            return word.compareTo(other.word);
        }
    }

    private static class WordUsage {
        int frequency;
        Set<String> users;

        WordUsage() {
            users = new HashSet<>();
        }

        void usedByUser(String user) {
            frequency++;
            users.add(user);
        }

        boolean wasUsedByEveryone(int totalUsers) {
            return users.size() == totalUsers;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();

        Set<String> users = new HashSet<>();
        Map<String, WordUsage> wordUsageMap = new HashMap<>();
        int messages = FastReader.nextInt();

        for (int i = 0; i < messages; i++) {
            String[] data = FastReader.getLine().split(" ");
            String userName = data[0];

            users.add(userName);
            for (int w = 1; w < data.length; w++) {
                String word = data[w];
                if (!wordUsageMap.containsKey(word)) {
                    wordUsageMap.put(word, new WordUsage());
                }
                wordUsageMap.get(word).usedByUser(userName);
            }
        }
        printUsedWords(wordUsageMap, users.size());
    }

    private static void printUsedWords(Map<String, WordUsage> wordUsageMap, int totalUsers) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        List<WordFrequency> wordsToPrint = new ArrayList<>();
        for (String word : wordUsageMap.keySet()) {
            WordUsage wordUsage = wordUsageMap.get(word);
            if (wordUsage.wasUsedByEveryone(totalUsers)) {
                wordsToPrint.add(new WordFrequency(word, wordUsage.frequency));
            }
        }

        if (wordsToPrint.isEmpty()) {
            outputWriter.printLine("ALL CLEAR");
        } else {
            Collections.sort(wordsToPrint);
            for (WordFrequency word : wordsToPrint) {
                outputWriter.printLine(word.word);
            }
        }
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
