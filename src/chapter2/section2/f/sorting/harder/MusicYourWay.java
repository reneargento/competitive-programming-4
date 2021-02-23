package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 22/02/21.
 */
public class MusicYourWay {

    private static class Song {
        String songAttributesDescription;
        String[] attributes;

        public Song(String songAttributesDescription) {
            this.songAttributesDescription = songAttributesDescription;
            attributes = songAttributesDescription.split(" ");
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String attributesDescription = FastReader.getLine();
        String[] attributes = attributesDescription.split(" ");
        Map<String, Integer> attributeMap = getAttributeMap(attributes);

        int songsNumber = FastReader.nextInt();
        Song[] songs = new Song[songsNumber];

        for (int i = 0; i < songsNumber; i++) {
            songs[i] = new Song(FastReader.getLine());
        }

        int sortingCommands = FastReader.nextInt();
        for (int i = 0; i < sortingCommands; i++) {
            String attribute = FastReader.next();
            int attributeIndex = attributeMap.get(attribute);
            Arrays.sort(songs, getComparator(attributeIndex));

            if (i > 0) {
                outputWriter.printLine();
            }
            outputWriter.printLine(attributesDescription);
            printSongs(songs, outputWriter);
        }

        outputWriter.flush();
        outputWriter.close();
    }

    private static Map<String, Integer> getAttributeMap(String[] attributes) {
        Map<String, Integer> attributeMap = new HashMap<>();
        for (int i = 0; i < attributes.length; i++) {
            attributeMap.put(attributes[i], i);
        }
        return attributeMap;
    }

    private static Comparator<Song> getComparator(int attributeIndex) {
        return new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return song1.attributes[attributeIndex].compareTo(song2.attributes[attributeIndex]);
            }
        };
    }

    private static void printSongs(Song[] songs, OutputWriter outputWriter) {
        for (Song song : songs) {
            outputWriter.printLine(song.songAttributesDescription);
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
