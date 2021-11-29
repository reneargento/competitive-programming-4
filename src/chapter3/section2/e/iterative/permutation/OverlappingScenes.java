package chapter3.section2.e.iterative.permutation;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 28/11/21.
 */
public class OverlappingScenes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String[] scenes = new String[FastReader.nextInt()];
            for (int i = 0; i < scenes.length; i++) {
                scenes[i] = FastReader.getLine();
            }
            int minLengthMovie = computeMinLengthMovie(scenes);
            outputWriter.printLine(String.format("Case %d: %d", t, minLengthMovie));
        }
        outputWriter.flush();
    }

    private static int computeMinLengthMovie(String[] scenes) {
        if (scenes.length == 0) {
            return 0;
        }
        int[] order = new int[scenes.length];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        int[] currentOrder = new int[order.length];
        return computeMinLengthMovie(scenes, order, currentOrder, 0, 0);
    }

    private static int computeMinLengthMovie(String[] scenes, int[] order, int[] currentOrder,
                                             int index, int mask) {
        if (mask == (1 << order.length) - 1) {
            return computeMovieLength(scenes, currentOrder);
        }

        int minLengthMovie = Integer.MAX_VALUE;

        for (int i = 0; i < order.length; i++) {
            if ((mask & (1 << i)) == 0) {
                int newMask = mask | (1 << i);
                currentOrder[index] = order[i];
                int movieLength = computeMinLengthMovie(scenes, order, currentOrder, index + 1, newMask);
                minLengthMovie = Math.min(minLengthMovie, movieLength);
            }
        }
        return minLengthMovie;
    }

    private static int computeMovieLength(String[] scenes, int[] order) {
        String firstScene = scenes[order[0]];
        StringBuilder movie = new StringBuilder(firstScene);

        for (int i = 1; i < order.length; i++) {
            int condensed = 0;
            String nextScene = scenes[order[i]];
            boolean applied = false;

            while (condensed != movie.length()) {
                String movieEnd = movie.substring(condensed);
                if (nextScene.startsWith(movieEnd)) {
                    condensed = movieEnd.length();
                    applied = true;
                    break;
                } else {
                    condensed++;
                }
            }

            if (!applied) {
                condensed = 0;
            }
            movie.append(nextScene.substring(condensed));
        }
        return movie.length();
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
