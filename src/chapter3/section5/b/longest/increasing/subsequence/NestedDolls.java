package chapter3.section5.b.longest.increasing.subsequence;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/11/22.
 */
public class NestedDolls {

    public static class Doll implements Comparable<Doll> {
        int width;
        int height;

        public Doll(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int compareTo(Doll otherDoll) {
            if (this.width != otherDoll.width) {
                return Integer.compare(otherDoll.width, this.width);
            } else {
                return Integer.compare(this.height, otherDoll.height);
            }
        }

        public int compareHeight(Doll otherDoll) {
            return Integer.compare(this.height, otherDoll.height);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            Doll[] dolls = new Doll[FastReader.nextInt()];
            for (int i = 0; i < dolls.length; i++) {
                dolls[i] = new Doll(FastReader.nextInt(), FastReader.nextInt());
            }

            int minimumNumberOfDolls = countMinimumNumberOfDolls(dolls);
            outputWriter.printLine(minimumNumberOfDolls);
        }
        outputWriter.flush();
    }

    public static int countMinimumNumberOfDolls(Doll[] dolls) {
        if (dolls.length == 0) {
            return 0;
        }
        Arrays.sort(dolls);

        int dollsLength = dolls.length;
        int[] endIndexes = new int[dollsLength];

        int length = 1;

        for (int i = 1; i < dollsLength; i++) {
            // Case 1 - smallest end element
            if (dolls[i].compareHeight(dolls[endIndexes[0]]) < 0) {
                endIndexes[0] = i;
            } else if (dolls[endIndexes[length - 1]].compareHeight(dolls[i]) <= 0) {
                // Case 2 - highest end element - extends longest increasing subsequence
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = nextElementIndex(dolls, endIndexes, 0, length - 1, dolls[i]);
                endIndexes[indexToReplace] = i;
            }
        }
        return length;
    }

    private static int nextElementIndex(Doll[] dolls, int[] endIndexes, int low, int high, Doll doll) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (dolls[endIndexes[middle]].compareHeight(doll) > 0) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
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
