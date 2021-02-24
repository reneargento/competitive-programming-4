package chapter2.section2.f.sorting.harder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 23/02/21.
 */
public class SearchingQuickly {

    private static class Title implements Comparable<Title> {
        String[] words;
        int keywordIndex;
        int titleIndex;

        public Title(String[] words, int keywordIndex, int titleIndex) {
            this.words = words;
            this.keywordIndex = keywordIndex;
            this.titleIndex = titleIndex;
        }

        @Override
        public int compareTo(Title other) {
            String keyword1 = words[keywordIndex].toLowerCase();
            String keyword2 = other.words[other.keywordIndex].toLowerCase();

            if (!keyword1.equals(keyword2)) {
                return keyword1.compareTo(keyword2);
            }
            if (titleIndex != other.titleIndex) {
                return titleIndex - other.titleIndex;
            }
            return keywordIndex - other.keywordIndex;
        }

        @Override
        public String toString() {
            StringJoiner fullTitle = new StringJoiner(" ");
            for (int i = 0; i < words.length; i++) {
                String word;
                if (i == keywordIndex) {
                    word = words[i].toUpperCase();
                } else {
                    word = words[i].toLowerCase();
                }
                fullTitle.add(word);
            }
            return fullTitle.toString();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        Set<String> wordsToIgnore = new HashSet<>();
        String wordToIgnore = FastReader.getLine();

        while (!wordToIgnore.equals("::")) {
            wordsToIgnore.add(wordToIgnore);
            wordToIgnore = FastReader.getLine();
        }

        List<Title> titles = new ArrayList<>();
        int titleIndex = 0;
        String title = FastReader.getLine();

        while (title != null) {
            parseAndAddTitles(title, titles, wordsToIgnore, titleIndex);
            title = FastReader.getLine();
            titleIndex++;
        }

        Collections.sort(titles);
        for (Title currentTitle : titles) {
            System.out.println(currentTitle);
        }
    }

    private static void parseAndAddTitles(String fullTitle, List<Title> titles, Set<String> wordsToIgnore,
                                          int titleIndex) {
        String[] words = fullTitle.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (!wordsToIgnore.contains(words[i].toLowerCase())) {
                titles.add(new Title(words, i, titleIndex));
            }
        }
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
}
