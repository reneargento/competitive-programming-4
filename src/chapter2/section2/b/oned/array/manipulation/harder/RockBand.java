package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 01/02/21.
 */
public class RockBand {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int members = FastReader.nextInt();
        int songsNumber = FastReader.nextInt();
        int[][] songs = new int[members][songsNumber + 1];

        for (int i = 0; i < members; i++) {
            for (int s = 0; s < songsNumber; s++) {
                songs[i][s] = FastReader.nextInt();
            }
        }

        List<Integer> songsToPlay = getSongsToPlay(songs);
        Collections.sort(songsToPlay);
        printSongs(songsToPlay);
    }

    private static List<Integer> getSongsToPlay(int[][] songs) {
        Set<Integer> songsToProcess = new HashSet<>();
        Queue<Integer> songsQueue = new LinkedList<>();
        Set<Integer> songsToPlay = new HashSet<>();
        int[] memberIndexes = new int[songs.length];
        boolean[][] chosenSongsPerMember = new boolean[songs.length][songs[0].length];

        for (int i = 0; i < songs.length; i++) {
            int song = songs[i][0];
            chosenSongsPerMember[i][song] = true;

            if (!songsToProcess.contains(song)) {
                songsQueue.offer(song);
                songsToProcess.add(song);
            }
        }

        while (!songsQueue.isEmpty()) {
            int song = songsQueue.poll();

            for (int i = 0; i < songs.length; i++) {
                if (chosenSongsPerMember[i][song]) {
                    continue;
                }

                while (memberIndexes[i] != songs[i].length) {
                    int nextSong = songs[i][memberIndexes[i]];
                    memberIndexes[i]++;
                    chosenSongsPerMember[i][nextSong] = true;

                    if (nextSong == song) {
                        break;
                    }
                    if (songsToPlay.contains(nextSong) || songsToProcess.contains(nextSong)) {
                        continue;
                    }
                    songsQueue.offer(nextSong);
                    songsToProcess.add(nextSong);
                }
            }
            songsToPlay.add(song);
            songsToProcess.remove(song);
        }
        return new ArrayList<>(songsToPlay);
    }

    private static void printSongs(List<Integer> songsToPlay) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        outputWriter.printLine(songsToPlay.size());
        for (int i = 0; i < songsToPlay.size(); i++) {
            outputWriter.print(songsToPlay.get(i));

            if (i != songsToPlay.size() - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
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
            for (int i = 0; i < objects.length; i++)
            {
                if (i != 0)
                    writer.print(' ');
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
