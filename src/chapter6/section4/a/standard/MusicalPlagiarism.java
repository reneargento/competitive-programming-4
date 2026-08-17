package chapter6.section4.a.standard;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 16/08/2026.
 */
public class MusicalPlagiarism {

    private static final String[] NOTES = {
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "Cb",
            "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "Cb"
    };

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int songSize = FastReader.nextInt();
        int snippetSize = FastReader.nextInt();

        while (songSize != 0 || snippetSize != 0) {
            String[] song = new String[songSize];
            for (int i = 0; i < song.length; i++) {
                song[i] = FastReader.next();
            }
            String[] snippet = new String[snippetSize];
            for (int i = 0; i < snippet.length; i++) {
                snippet[i] = FastReader.next();
            }

            String result = investigate(song, snippet);
            outputWriter.printLine(result);
            songSize = FastReader.nextInt();
            snippetSize = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static String investigate(String[] song, String[] snippet) {
        String lyric1 = buildLyric(song);
        String lyric2 = buildLyric(snippet);

        KnuthMorrisPratt kmp = new KnuthMorrisPratt(lyric2);
        if (kmp.search(lyric1) != lyric1.length()) {
            return "S";
        }
        return "N";
    }

    private static String buildLyric(String[] notes) {
        StringBuilder lyric = new StringBuilder();
        for (int i = 1; i < notes.length; i++) {
            int distance = getDistance(notes[i - 1], notes[i]);
            lyric.append("[").append(distance).append("]");
        }
        return lyric.toString();
    }

    private static int getDistance(String note1, String note2) {
        String mainNote1 = getMainNote(note1);
        String mainNote2 = getMainNote(note2);

        int index1 = -1;
        int index2 = -1;
        int index3 = -1;

        for (int i = 0; i < NOTES.length; i++) {
            if (NOTES[i].equals(mainNote1)) {
                if (index1 == -1) {
                    index1 = i;
                } else {
                    index3 = i;
                    break;
                }
            }
            if (NOTES[i].equals(mainNote2)) {
                if (index1 != -1) {
                    index2 = i;
                }
            }
        }

        int distance1 = index2 - index1;
        int distance2 = index3 - index2;
        return Math.min(distance1, distance2);
    }

    private static String getMainNote(String note) {
        if (note.equals("C") || note.equals("B#")) {
            return "C";
        } else if (note.equals("Db") || note.equals("C#")) {
            return "Db";
        } else if (note.equals("D")) {
            return "D";
        } else if (note.equals("Eb") || note.equals("D#")) {
            return "Eb";
        } else if (note.equals("E") || note.equals("Fb")) {
            return "E";
        } else if (note.equals("F") || note.equals("E#")) {
            return "F";
        } else if (note.equals("Gb") || note.equals("F#")) {
            return "Gb";
        } else if (note.equals("G")) {
            return "G";
        } else if (note.equals("Ab") || note.equals("G#")) {
            return "Ab";
        } else if (note.equals("A")) {
            return "A";
        } else if (note.equals("Bb") || note.equals("A#")) {
            return "Bb";
        } else {
            return "Cb";
        }
    }

    private static class KnuthMorrisPratt {
        private final String pattern;
        private final int[] next;

        public KnuthMorrisPratt(String pattern) {
            this.pattern = pattern;
            int patternLength = pattern.length();
            next = new int[patternLength];

            int j = -1;
            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                if (patternIndex == 0) {
                    next[patternIndex] = -1;
                } else if (pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    next[patternIndex] = j;
                } else {
                    next[patternIndex] = next[j];
                }

                while (j >= 0 && pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    j = next[j];
                }
                j++;
            }
        }

        public int search(String text) {
            int textIndex;
            int patternIndex;
            int textLength = text.length();
            int patternLength = pattern.length();

            for (textIndex = 0, patternIndex = 0; textIndex < textLength && patternIndex < patternLength; textIndex++) {
                while (patternIndex >= 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                    patternIndex = next[patternIndex];
                }
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                return textIndex - patternLength; // found
            } else {
                return textLength;                // not found
            }
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

        public void flush() {
            writer.flush();
        }
    }
}