package chapter3.section4.f.non.classical.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 07/08/22.
 */
public class AndysShoes {

    private static class ShoePair {
        int left;
        int right;

        public ShoePair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            ShoePair[] pairs = new ShoePair[FastReader.nextInt()];
            Map<Integer, List<Integer>> shoeToPairLocation = new HashMap<>();
            Set<Integer> colors = new HashSet<>();

            for (int i = 0; i < pairs.length; i++) {
                pairs[i] = new ShoePair(FastReader.nextInt(), FastReader.nextInt());

                if (!shoeToPairLocation.containsKey(pairs[i].left)) {
                    shoeToPairLocation.put(pairs[i].left, new ArrayList<>());
                }
                if (!shoeToPairLocation.containsKey(pairs[i].right)) {
                    shoeToPairLocation.put(pairs[i].right, new ArrayList<>());
                }

                shoeToPairLocation.get(pairs[i].left).add(i);
                shoeToPairLocation.get(pairs[i].right).add(i);
                colors.add(pairs[i].left);
                colors.add(pairs[i].right);
            }
            int minimumSwaps = computeMinimumSwaps(pairs, shoeToPairLocation, colors);
            outputWriter.printLine(minimumSwaps);
        }
        outputWriter.flush();
    }

    private static int computeMinimumSwaps(ShoePair[] pairs, Map<Integer, List<Integer>> shoeToPairLocation,
                                           Set<Integer> colors) {
        int minimumSwaps = 0;

        for (int color : colors) {
            List<Integer> locations = shoeToPairLocation.get(color);
            if (locations.get(0).equals(locations.get(1))) {
                continue;
            }
            ShoePair pair1 = pairs[locations.get(0)];
            ShoePair pair2 = pairs[locations.get(1)];

            int otherShoe2 = (pair2.left == color) ? pair2.right : pair2.left;

            if (pair1.left == color) {
                pair1.left = otherShoe2;
            } else {
                pair1.right = otherShoe2;
            }
            List<Integer> otherShoe2Locations = shoeToPairLocation.get(otherShoe2);
            otherShoe2Locations.remove(locations.get(1));
            otherShoe2Locations.add(locations.get(0));

            minimumSwaps++;
        }
        return minimumSwaps;
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
