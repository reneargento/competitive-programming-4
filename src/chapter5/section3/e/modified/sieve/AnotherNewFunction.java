package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 18/07/25.
 */
public class AnotherNewFunction {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] phiDepths = computePhiDepths();

        for (int t = 0; t < tests; t++) {
            int start = FastReader.nextInt();
            int end = FastReader.nextInt();

            long sODF = computeSODF(phiDepths, start, end);
            outputWriter.printLine(sODF);
        }
        outputWriter.flush();
    }

    private static long computeSODF(int[] phiDepths, int start, int end) {
        long sODF = 0;
        for (int i = start; i <= end; i++) {
            sODF += phiDepths[i];
        }
        return sODF;
    }

    private static int[] computePhiDepths() {
        int[] eulerPhi = eratosthenesSieveEulerPhi();
        int[] phiDepths = new int[eulerPhi.length];

        for (int i = 1; i < eulerPhi.length; i++) {
            int depth = 1;
            int index = i;

            while (eulerPhi[index] != 1) {
                if (phiDepths[index] != 0) {
                    depth += phiDepths[index] - 1;
                    break;
                }

                depth++;
                index = eulerPhi[index];
            }
            phiDepths[i] = depth;
        }
        return phiDepths;
    }

    private static int[] eratosthenesSieveEulerPhi() {
        int[] eulerPhi = new int[2000001];
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
