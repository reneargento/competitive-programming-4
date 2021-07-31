package chapter2.section4.b.union.find.disjoint.sets;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 30/07/21.
 */
public class MartensTheorem {

    private static class Word implements Comparable<Word> {
        String word;

        public Word(String word) {
            this.word = word;
        }

        @Override
        public int compareTo(Word other) {
            return Integer.compare(word.length(), other.word.length());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int statements = FastReader.nextInt();

        UnionFind unionFind = new UnionFind(statements * 2);
        Map<Integer, List<Integer>> notRelations = new HashMap<>();
        Map<String, Integer> wordToIdMap = new HashMap<>();
        List<Word> allWords = new ArrayList<>();

        for (int i = 0; i < statements; i++) {
            String word1 = FastReader.next();
            String relation = FastReader.next();
            String word2 = FastReader.next();

            int wordId1 = getWordId(word1, wordToIdMap, allWords);
            int wordId2 = getWordId(word2, wordToIdMap, allWords);

            if (relation.equals("is")) {
                unionFind.union(wordId1, wordId2);
            } else {
                if (!notRelations.containsKey(wordId1)) {
                    notRelations.put(wordId1, new ArrayList<>());
                }
                notRelations.get(wordId1).add(wordId2);
            }
        }
        connectWordsByMartensTheorem(unionFind, allWords, wordToIdMap);

        boolean isEverythingCorrect = checkStatements(unionFind, notRelations);
        outputWriter.printLine(isEverythingCorrect ? "yes" : "wait what?");
        outputWriter.flush();
    }

    private static void connectWordsByMartensTheorem(UnionFind unionFind, List<Word> allWords,
                                                     Map<String, Integer> wordToIdMap) {
        Collections.sort(allWords);
        Map<String, List<String>> suffixToWordsMap = new HashMap<>();

        for (Word word : allWords) {
            String key = getSuffix(suffixToWordsMap, word.word);

            if (!suffixToWordsMap.containsKey(key)) {
                List<String> words = new ArrayList<>();
                words.add(word.word);
                suffixToWordsMap.put(key, words);
            } else {
                String wordInSuffixMap = suffixToWordsMap.get(key).get(0);
                int wordId1 = getWordId(word.word, wordToIdMap, allWords);
                int wordId2 = getWordId(wordInSuffixMap, wordToIdMap, allWords);
                unionFind.union(wordId1, wordId2);
            }
        }
    }

    private static String getSuffix(Map<String, List<String>> suffixToWordsMap, String word) {
        int endIndex = word.length();

        for (int length = 1; true; length++) {
            String suffix = word.substring(endIndex - length, endIndex);
            if (suffixToWordsMap.containsKey(suffix)
                    || word.length() == length
                    || length == 3) {
                return suffix;
            }
        }
    }

    private static boolean checkStatements(UnionFind unionFind, Map<Integer, List<Integer>> notRelations) {
        for (int wordId : notRelations.keySet()) {
            List<Integer> impossibleWords = notRelations.get(wordId);
            for (int impossibleWord : impossibleWords) {
                if (unionFind.connected(wordId, impossibleWord)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int getWordId(String word, Map<String, Integer> wordToIdMap, List<Word> allWords) {
        if (!wordToIdMap.containsKey(word)) {
            wordToIdMap.put(word, wordToIdMap.size());
            allWords.add(new Word(word));
        }
        return wordToIdMap.get(word);
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private final int[] sizes;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            sizes = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
                sizes[i] = 1;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(inverse Ackermann function)
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
                sizes[leader1] += sizes[leader2];
            } else {
                leaders[leader1] = leader2;
                sizes[leader2] += sizes[leader1];
                ranks[leader2]++;
            }
            components--;
        }

        public int size(int set) {
            int leader = find(set);
            return sizes[leader];
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
