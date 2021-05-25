package chapter2.section3.d.hash.table.set;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 26/05/21.
 */
public class DeduplicatingFiles {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int filesNumber = FastReader.nextInt();

        while (filesNumber != 0) {
            Map<Integer, List<String>> filesByHash = new HashMap<>();
            Set<String> files = new HashSet<>();
            int hashCollisions = 0;

            for (int i = 0; i < filesNumber; i++) {
                String file = FastReader.getLine();
                files.add(file);
                int hash = computeHash(file);

                if (!filesByHash.containsKey(hash)) {
                    filesByHash.put(hash, new ArrayList<>());
                }

                List<String> filesWithHash = filesByHash.get(hash);
                hashCollisions += countCollisions(filesWithHash, file);
                filesWithHash.add(file);
            }

            outputWriter.printLine(files.size() + " " + hashCollisions);
            filesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int countCollisions(List<String> filesWithHash, String file) {
        int collisions = 0;

        for (String otherFile : filesWithHash) {
            if (!otherFile.equals(file)) {
                collisions++;
            }
        }
        return collisions;
    }

    private static int computeHash(String file) {
        int hash = 0;
        for (char character : file.toCharArray()) {
            hash ^= character;
        }
        return hash;
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
