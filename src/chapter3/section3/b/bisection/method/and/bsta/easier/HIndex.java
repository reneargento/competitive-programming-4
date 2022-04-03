package chapter3.section3.b.bisection.method.and.bsta.easier;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/04/22.
 */
public class HIndex {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] citations = new int[FastReader.nextInt()];

        for (int i = 0; i < citations.length; i++) {
            citations[i] = FastReader.nextInt();
        }
        int hIndex = computeHIndex(citations);
        outputWriter.printLine(hIndex);
        outputWriter.flush();
    }

    private static int computeHIndex(int[] citations) {
        int low = 1;
        int high = 1000000000;
        int hIndex = 0;
        Arrays.sort(citations);

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isIndex(citations, middle)) {
                hIndex = middle;
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return hIndex;
    }

    private static boolean isIndex(int[] citations, int index) {
        int low = 0;
        int high = citations.length - 1;
        int firstValidIndex = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (citations[middle] >= index) {
                firstValidIndex = middle;
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }

        if (firstValidIndex == -1) {
            return false;
        }
        int totalPapers = citations.length - firstValidIndex;
        return totalPapers >= index;
    }

    // O(n) check
//    private static boolean isIndex(int[] citations, int index) {
//        int papersWithCitations = 0;
//        for (int citation : citations) {
//            if (citation >= index) {
//                papersWithCitations++;
//            }
//
//            if (papersWithCitations >= index) {
//                return true;
//            }
//        }
//        return false;
//    }

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
