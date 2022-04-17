package chapter3.section3.c.ternary.search.and.others;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 14/04/22.
 */
// Based on https://github.com/BrandonTang89/Kattis_CP4_Solutions/blob/main/Chapter_3/Divide_and_Conquer/kattis_goingtoseed.cpp
public class GoingToSeed {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int trees = FastReader.nextInt();

        int low = 1;
        int high = trees;

        while (high - low >= 3) {
            double middle = (high - low + 1) / 2.0;

            int range1Start = low;
            int range1End = range1Start + (int) (middle - 1);
            int range2Start = range1Start + (int) (middle / 2);
            int range2End = range2Start + (int) (middle - 1);

            outputWriter.printLine(String.format("Q %d %d %d %d", range1Start, range1End, range2Start, range2End));
            outputWriter.flush();
            int region1Result = FastReader.nextInt();
            int region2Result = FastReader.nextInt();

            if (region1Result == 1 && region2Result == 0) {
                if (range2Start - range1Start <= 1) {
                    catchTheGreatSeedling(range1Start, outputWriter);
                    return;
                }
                high = range2Start;
            } else if (region1Result == 1 && region2Result == 1) {
                if (range2Start == range1End) {
                    catchTheGreatSeedling(range2Start, outputWriter);
                    return;
                }
                low = range2Start - 1;
                high = range1End + 1;
            } else if (region1Result == 0 && region2Result == 1) {
                if (range2End - range1End <= 1) {
                    catchTheGreatSeedling(range2End, outputWriter);
                    return;
                }
                low = range1End;
                high = range2End + 1;
            } else if (region1Result == 0 && region2Result == 0) {
                if (high - range2End == 1) {
                    catchTheGreatSeedling(high, outputWriter);
                    return;
                }
                low = range2End;
                high = Math.min(high + 1, trees);
            }
        }

        // Check last 3 values
        outputWriter.printLine(String.format("Q %d %d %d %d", low, low, low + 1, low + 1));
        outputWriter.flush();
        int region1Result = FastReader.nextInt();
        int region2Result = FastReader.nextInt();
        int result;
        if (region1Result == 1 && region2Result == 0) {
            result = low;
        } else if (region1Result == 0 && region2Result == 1) {
            result = low + 1;
        } else {
            result = high;
        }
        catchTheGreatSeedling(result, outputWriter);
    }

    private static void catchTheGreatSeedling(int location, OutputWriter outputWriter) {
        outputWriter.printLine("A " + location);
        outputWriter.flush();
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
