package chapter2.section2.f.sorting.harder;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class ZipfsSong {

    private static class Song implements Comparable<Song> {
        String name;
        long position;
        long quality;

        public Song(String name, long position, long timesPlayed) {
            this.name = name;
            this.position = position;
            quality = timesPlayed * (position + 1);
        }

        @Override
        public int compareTo(Song other) {
            if (quality != other.quality) {
                return Long.compare(other.quality, quality);
            }
            return Long.compare(position, other.position);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int songsNumber = FastReader.nextInt();
        int songsToChoose = FastReader.nextInt();

        Song[] songs = new Song[songsNumber];

        for (int i = 0; i < songs.length; i++) {
            long timesPlayed = FastReader.nextLong();
            String songName = FastReader.next();
            songs[i] = new Song(songName, i, timesPlayed);
        }

        Arrays.sort(songs);

        for (int i = 0; i < songsToChoose; i++) {
            outputWriter.printLine(songs[i].name);
        }
        outputWriter.flush();
        outputWriter.close();
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

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
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
