package chapter5.section2.e.number.systems.or.sequences;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/02/25.
 */
public class IntegerSequencesFromAdditionOfTerms {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 0; t < tests; t++) {
            int[] coefficients = new int[FastReader.nextInt() + 1];
            for (int i = 0; i < coefficients.length; i++) {
                coefficients[i] = FastReader.nextInt();
            }
            int d = FastReader.nextInt();
            int k = FastReader.nextInt();

            long kthTerm = computeKthTerm(coefficients, d, k);
            outputWriter.printLine(kthTerm);
        }
        outputWriter.flush();
    }

    private static long computeKthTerm(int[] coefficients, int d, int k) {
        long kthTerm;
        int nextTerms = d;
        int totalTermsComputed = 0;

        for (int n = 1; ; n++) {
            long currentTerm = 0;

            for (int coefficient = 0; coefficient < coefficients.length; coefficient++) {
                currentTerm += coefficients[coefficient] * fastExponentiation(n, coefficient);
            }

            totalTermsComputed += nextTerms;
            nextTerms += d;

            if (totalTermsComputed >= k) {
                kthTerm = currentTerm;
                break;
            }
        }
        return kthTerm;
    }

    private static long fastExponentiation(long base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result);
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
