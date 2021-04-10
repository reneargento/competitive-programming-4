package chapter2.section2.l.list.queue.deque;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/04/21.
 */
public class TrendingTopic {

    private static class Word implements Comparable<Word> {
        String value;
        int frequency;

        public Word(String value) {
            this.value = value;
        }

        @Override
        public int compareTo(Word other) {
            if (frequency != other.frequency) {
                return Integer.compare(other.frequency, frequency);
            }
            return value.compareTo(other.value);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Word word = (Word) other;
            return value.equals(word.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();

        Map<String, Word> wordsMap = new HashMap<>();
        PriorityQueue<Word> wordsPriorityQueue = new PriorityQueue<>();
        Queue<List<String>> wordsPerDay = new LinkedList<>();

        boolean isReadingText = false;
        List<String> wordsInCurrentDay = new ArrayList<>();

        while (line != null) {
            if (!line.isEmpty()) {
                if (line.charAt(0) == '<') {
                    if (isReadingText) {
                        isReadingText = false;

                        if (wordsPerDay.size() == 7) {
                            updateFrequencies(wordsMap, wordsPriorityQueue, wordsPerDay.poll(), false);
                        }
                        wordsPerDay.offer(wordsInCurrentDay);
                        updateFrequencies(wordsMap, wordsPriorityQueue, wordsInCurrentDay, true);
                    } else {
                        if (line.charAt(2) == 'e') {
                            isReadingText = true;
                            wordsInCurrentDay = new ArrayList<>();
                        } else {
                            String[] strings = line.split(" ");
                            int query = Integer.parseInt(strings[1]);
                            query(query, wordsPriorityQueue, outputWriter);
                        }
                    }
                } else {
                    readWords(wordsInCurrentDay, line);
                }
            }
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static void readWords(List<String> wordsInCurrentDay, String line) {
        String[] words = line.split(" ");
        for (String word : words) {
            if (word.length() < 4) {
                continue;
            }
            wordsInCurrentDay.add(word);
        }
    }

    private static void updateFrequencies(Map<String, Word> wordsMap, PriorityQueue<Word> wordsPriorityQueue,
                                          List<String> words, boolean increment) {
        Set<Word> wordsToUpdate = new HashSet<>();

        for (String word : words) {
            Word wordReference;
            if (wordsMap.containsKey(word)) {
                wordReference = wordsMap.get(word);
            } else {
                wordReference = new Word(word);
            }

            if (increment) {
                wordReference.frequency++;
            } else {
                wordReference.frequency--;
            }

            wordsMap.put(word, wordReference);
            wordsToUpdate.remove(wordReference);
            wordsToUpdate.add(wordReference);
            wordsPriorityQueue.remove(wordReference);
        }

        for (Word word : wordsToUpdate) {
            wordsPriorityQueue.offer(word);
        }
    }

    private static void query(int topWords, PriorityQueue<Word> wordsPriorityQueue, OutputWriter outputWriter) {
        List<Word> wordsToPrint = new ArrayList<>();

        for (int i = 0; i < topWords; i++) {
            Word word = wordsPriorityQueue.poll();
            wordsToPrint.add(word);

            if (i == topWords - 1) {
                int minimumFrequency = word.frequency;
                while (!wordsPriorityQueue.isEmpty() && wordsPriorityQueue.peek().frequency == minimumFrequency) {
                    wordsToPrint.add(wordsPriorityQueue.poll());
                }
            }
        }

        outputWriter.printLine(String.format("<top %d>", topWords));
        for (Word word : wordsToPrint) {
            outputWriter.printLine(word.value + " " + word.frequency);
            wordsPriorityQueue.offer(word);
        }
        outputWriter.printLine("</top>");
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
