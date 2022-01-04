package chapter3.section2.h.mathematical.simulation.easier;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 03/01/22.
 */
public class DiceCup {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int faces1 = FastReader.nextInt();
        int faces2 = FastReader.nextInt();

        List<Integer> mostLikelySums = computeMostLikelySums(faces1, faces2);
        for (int sum : mostLikelySums) {
            outputWriter.printLine(sum);
        }
        outputWriter.flush();
    }

    private static List<Integer> computeMostLikelySums(int faces1, int faces2) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int highestFrequency = 0;
        List<Integer> mostLikelySums = null;

        for (int face1 = 1; face1 <= faces1; face1++) {
            for (int face2 = 1; face2 <= faces2; face2++) {
                int sum = face1 + face2;
                int frequency = frequencyMap.getOrDefault(sum, 0) + 1;

                if (frequency > highestFrequency) {
                    highestFrequency = frequency;
                    mostLikelySums = new ArrayList<>();
                    mostLikelySums.add(sum);
                } else if (frequency == highestFrequency) {
                    mostLikelySums.add(sum);
                }
                frequencyMap.put(sum, frequency);
            }
        }

        Collections.sort(mostLikelySums);
        return mostLikelySums;
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
