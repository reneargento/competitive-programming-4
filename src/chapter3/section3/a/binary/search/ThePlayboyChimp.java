package chapter3.section3.a.binary.search;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 17/03/22.
 */
public class ThePlayboyChimp {

    private static class SelectedChimps {
        int height1;
        int height2;

        public SelectedChimps(int height1, int height2) {
            this.height1 = height1;
            this.height2 = height2;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] ladyChimpHeights = new int[FastReader.nextInt()];

        for (int i = 0; i < ladyChimpHeights.length; i++) {
            ladyChimpHeights[i] = FastReader.nextInt();
        }

        int queries = FastReader.nextInt();
        for (int q = 0; q < queries; q++) {
            int queryHeight = FastReader.nextInt();
            SelectedChimps selectedChimps = selectedChimps(ladyChimpHeights, queryHeight);

            if (selectedChimps.height1 != -1) {
                outputWriter.print(selectedChimps.height1);
            } else {
                outputWriter.print("X");
            }

            if (selectedChimps.height2 != -1) {
                outputWriter.print(" " + selectedChimps.height2);
            } else {
                outputWriter.print(" X");
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static SelectedChimps selectedChimps(int[] ladyChimpHeights, int queryHeight) {
        int chimpHeight1Index = binarySearch(ladyChimpHeights, queryHeight, false, 0, ladyChimpHeights.length - 1);
        int chimpHeight2Index = binarySearch(ladyChimpHeights, queryHeight, true, 0, ladyChimpHeights.length - 1);

        int chimpHeight1 = -1;
        if (chimpHeight1Index != -1) {
            chimpHeight1 = ladyChimpHeights[chimpHeight1Index];
        }
        int chimpHeight2 = -1;
        if (chimpHeight2Index != -1) {
            chimpHeight2 = ladyChimpHeights[chimpHeight2Index];
        }
        return new SelectedChimps(chimpHeight1, chimpHeight2);
    }

    private static int binarySearch(int[] values, int target, boolean isLowerBound, int low, int high) {
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] <= target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, true, low, middle - 1);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            } else {
                if (values[middle] >= target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    int candidate = binarySearch(values, target, false, middle + 1, high);
                    if (candidate != -1) {
                        result = candidate;
                    }
                    break;
                }
            }
        }
        return result;
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
