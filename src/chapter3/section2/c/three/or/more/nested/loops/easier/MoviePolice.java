package chapter3.section2.c.three.or.more.nested.loops.easier;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 07/11/21.
 */
public class MoviePolice {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String[] movieSignatures = new String[FastReader.nextInt()];
        int clipSignatures = FastReader.nextInt();

        for (int i = 0; i < movieSignatures.length; i++) {
            movieSignatures[i] = FastReader.next();
        }

        for (int i = 0; i < clipSignatures; i++) {
            String clipSignature = FastReader.next();
            int bestMatchIndex = getBestMovieMatch(movieSignatures, clipSignature);
            outputWriter.printLine(bestMatchIndex);
        }
        outputWriter.flush();
    }

    private static int getBestMovieMatch(String[] movieSignatures, String clipSignature) {
        int bestHammingDistance = Integer.MAX_VALUE;
        int bestMatch = Integer.MAX_VALUE;

        for (int i = 0; i < movieSignatures.length; i++) {
            for (int j = 0; j <= movieSignatures[i].length() - clipSignature.length(); j++) {
                int currentDistance = 0;

                for (int k = 0; k < clipSignature.length(); k++) {
                    if (clipSignature.charAt(k) != movieSignatures[i].charAt(j + k)) {
                        currentDistance++;

                        if (currentDistance > bestHammingDistance) {
                            break;
                        }
                    }
                }

                if (currentDistance < bestHammingDistance) {
                    bestHammingDistance = currentDistance;
                    bestMatch = i + 1;
                }
            }
        }
        return bestMatch;
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
