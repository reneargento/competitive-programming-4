package chapter2.section2.b.oned.array.manipulation.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 31/01/21.
 */
public class PhysicalMusic {

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int chartSize = FastReader.nextInt();
            int[] positions = new int[chartSize];

            for (int i = 0; i < positions.length; i++) {
                positions[i] = FastReader.nextInt();
            }

            List<Integer> singles = getCertainlyAvailableSingles(positions);
            outputWriter.printLine(singles.size());
            Collections.sort(singles);
            for (int single : singles) {
                outputWriter.printLine(single);
            }
        }
        outputWriter.flush();
        outputWriter.close();
    }

    private static List<Integer> getCertainlyAvailableSingles(int[] positions) {
        int minPosition = Integer.MAX_VALUE;
        List<Integer> singles = new ArrayList<>();

        for (int i = positions.length - 1; i >= 0; i--) {
            if (positions[i] > minPosition) {
                singles.add(positions[i]);
            }
            minPosition = Math.min(minPosition, positions[i]);
        }
        return singles;
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
