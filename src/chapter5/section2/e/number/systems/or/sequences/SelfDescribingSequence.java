package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 27/02/25.
 */
public class SelfDescribingSequence {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int[] fns = computeFns();
        int n = FastReader.nextInt();

        while (n != 0) {
            int fn = getFn(fns, n);
            outputWriter.printLine(fn);
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] computeFns() {
        int limit = 700000;
        int[] fn = new int[limit];
        fn[1] = 1;
        fn[2] = 2;
        fn[3] = 2;
        int fnIndex = 4;

        for (int i = 3; i < fn.length; i++) {
            for (int j = 0; j < fn[i] && fnIndex < fn.length; j++) {
                fn[fnIndex] = i;
                fnIndex++;
            }
        }
        return fn;
    }

    private static int getFn(int[] fn, int n) {
        if (n < fn.length) {
            return fn[n];
        } else {
            int fnIndex = 3;
            int currentN = 4;
            int currentNFrequency = 6;

            while (currentNFrequency + fnIndex * fn[fnIndex] < n) {
                currentNFrequency += fnIndex * fn[fnIndex];
                currentN += fn[fnIndex];
                fnIndex++;
            }
            return currentN + (n - currentNFrequency) / fnIndex;
        }
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

        public void flush() {
            writer.flush();
        }
    }
}