package chapter6.section4.a.standard;

import java.io.*;

/**
 * Created by Rene Argento on 18/08/2026.
 */
public class RedRover {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        String route = FastReader.getLine();
        int minimumCharacters = computeMinimumCharacters(route);
        outputWriter.printLine(minimumCharacters);
        outputWriter.flush();
    }

    private static int computeMinimumCharacters(String route) {
        int minimumCharacters = route.length();

        for (int startIndex = 0; startIndex < route.length(); startIndex++) {
            int maxSize = (route.length() - startIndex) / 2;
            for (int size = 2; size <= maxSize; size++) {
                int frequency = 1;

                for (int i = startIndex + size; i + size <= route.length(); i++) {
                    if (match(route, startIndex, startIndex + size, i)){
                        frequency++;
                        i += size - 1;
                    }
                }

                if (frequency > 1) {
                    int totalCharacters = frequency * size;
                    int minimumCharactersCandidate = (route.length() - totalCharacters) + frequency + size;
                    if (minimumCharactersCandidate < minimumCharacters) {
                        minimumCharacters = minimumCharactersCandidate;
                    }
                }
            }
        }
        return minimumCharacters;
    }

    private static boolean match(String string, int startIndex1, int endIndex1, int startIndex2) {
        int length = endIndex1 - startIndex1;
        for (int i = 0; i < length; i++) {
            if (string.charAt(startIndex1 + i) != string.charAt(startIndex2 + i)) {
                return false;
            }
        }
        return true;
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

        public void flush() {
            writer.flush();
        }
    }
}