package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 21/07/25.
 */
public class GCDExtremeII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int n = FastReader.nextInt();
        long[] gCDSums = computeGCDSums();

        while (n != 0) {
            outputWriter.printLine(gCDSums[n]);
            n = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int[] eratosthenesSieveEulerPhi() {
        int[] eulerPhi = new int[4000001];
        for (int i = 0; i < eulerPhi.length; i++) {
            eulerPhi[i] = i;
        }

        for (int i = 2; i < eulerPhi.length; i++) {
            if (eulerPhi[i] == i) {
                for (int j = i; j < eulerPhi.length; j += i) {
                    eulerPhi[j] = (eulerPhi[j] / i) * (i - 1);
                }
            }
        }
        return eulerPhi;
    }

    private static long[] computeGCDSums() {
        int[] eulerPhi = eratosthenesSieveEulerPhi();
        long[] gCDSums = new long[eulerPhi.length];

        for (int i = 1; i < eulerPhi.length; i++) {
            int multiple = 2;
            for (int j = i * 2; j < eulerPhi.length; j += i) {
                gCDSums[j] += i * eulerPhi[multiple];
                multiple++;
            }
        }

        for (int i = 1; i < eulerPhi.length; i++) {
            gCDSums[i] += gCDSums[i - 1];
        }
        return gCDSums;
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
