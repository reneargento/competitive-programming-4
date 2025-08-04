package chapter5.section3.e.modified.sieve;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 29/07/25.
 */
public class FareySequenceLength {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        int[] prefixSum = computeEulerPhiPrefixSum();

        for (int t = 0; t < tests; t++) {
            int dateSetNumber = FastReader.nextInt();
            int order = FastReader.nextInt();
            int sequenceLength = prefixSum[order] + 1;
            outputWriter.printLine(dateSetNumber + " " + sequenceLength);
        }
        outputWriter.flush();
    }

    private static int[] computeEulerPhiPrefixSum() {
        int[] eulerPhi = eratosthenesSieveEulerPhi();
        int[] prefixSum = new int[eulerPhi.length];
        prefixSum[0] = eulerPhi[0];

        for (int i = 1; i < prefixSum.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + eulerPhi[i];
        }
        return prefixSum;
    }

    private static int[] eratosthenesSieveEulerPhi() {
        int[] eulerPhi = new int[10001];
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
