package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/05/21.
 */
public class WhatDoesTheFoxSay {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            String[] sounds = FastReader.getLine().split(" ");
            Set<String> remainingSongs = computeRemainingSounds(sounds);

            while (true) {
                String animal = FastReader.next();
                String goes = FastReader.next();
                String sound = FastReader.next();

                if (animal.equals("what")) {
                    FastReader.next();
                    FastReader.next();
                    break;
                }
                remainingSongs.remove(sound);
            }
            findWhatTheFoxSays(sounds, remainingSongs, outputWriter);
        }
        outputWriter.flush();
    }

    private static Set<String> computeRemainingSounds(String[] sounds) {
        Set<String> remainingSongs = new HashSet<>();
        remainingSongs.addAll(Arrays.asList(sounds));
        return remainingSongs;
    }

    private static void findWhatTheFoxSays(String[] sounds, Set<String> remainingSongs,
                                           OutputWriter outputWriter) {
        List<String> soundsTheFoxSays = new ArrayList<>();

        for (String sound : sounds) {
            if (remainingSongs.contains(sound)) {
                soundsTheFoxSays.add(sound);
            }
        }

        for (int i = 0; i < soundsTheFoxSays.size(); i++) {
            outputWriter.print(soundsTheFoxSays.get(i));

            if (i != soundsTheFoxSays.size() - 1) {
                outputWriter.print(" ");
            }
        }
        outputWriter.printLine();
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
