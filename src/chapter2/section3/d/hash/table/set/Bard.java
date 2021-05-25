package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/05/21.
 */
@SuppressWarnings("unchecked")
public class Bard {

    public static void main(String[] args) throws IOException {
        FastReader.init();

        int villagers = FastReader.nextInt();
        int evenings = FastReader.nextInt();
        int totalSongs = 0;
        Set<Integer>[] songsListened = createSongsListenedArray(villagers);

        for (int e = 0; e < evenings; e++) {
            int villagersNumber = FastReader.nextInt();
            List<Integer> villagersOnEvening = new ArrayList<>();
            boolean isBardPresent = false;

            for (int i = 0; i < villagersNumber; i++) {
                int villager = FastReader.nextInt();
                if (villager == 1) {
                    isBardPresent = true;
                    totalSongs++;
                } else {
                    villagersOnEvening.add(villager);
                }
            }
            processEvening(songsListened, villagersOnEvening, isBardPresent, e);
        }
        printVillagers(songsListened, totalSongs);
    }

    private static void processEvening(Set<Integer>[] songsListened, List<Integer> villagersOnEvening,
                                       boolean isBardPresent, int songId) {
        if (isBardPresent) {
            for (int villager : villagersOnEvening) {
                songsListened[villager].add(songId);
            }
        } else {
            Set<Integer> allSongs = new HashSet<>();
            for (int villager : villagersOnEvening) {
                allSongs.addAll(songsListened[villager]);
            }

            for (int villager : villagersOnEvening) {
                songsListened[villager].addAll(allSongs);
            }
        }
    }

    private static void printVillagers(Set<Integer>[] songsListened, int totalSongs) {
        OutputWriter outputWriter = new OutputWriter(System.out);

        outputWriter.printLine("1");
        for (int i = 2; i < songsListened.length; i++) {
            Set<Integer> villagerSongs = songsListened[i];

            if (villagerSongs.size() == totalSongs) {
                outputWriter.printLine(i);
            }
        }
        outputWriter.flush();
    }

    private static Set<Integer>[] createSongsListenedArray(int villagers) {
        Set<Integer>[] songsListened = new HashSet[villagers + 1];
        for (int i = 0; i < songsListened.length; i++) {
            songsListened[i] = new HashSet<>();
        }
        return songsListened;
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
