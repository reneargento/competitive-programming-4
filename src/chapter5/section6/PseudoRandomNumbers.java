package chapter5.section6;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 20/03/26.
 */
public class PseudoRandomNumbers {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        int Z = FastReader.nextInt();
        int I = FastReader.nextInt();
        int M = FastReader.nextInt();
        int L = FastReader.nextInt();
        int caseNumber = 1;

        while (Z != 0 || I != 0 || M != 0 || L != 0) {
            int sequenceLength = computeSequenceLength(Z, I, M, L);
            outputWriter.printLine(String.format("Case %d: %d", caseNumber, sequenceLength));

            caseNumber++;
            Z = FastReader.nextInt();
            I = FastReader.nextInt();
            M = FastReader.nextInt();
            L = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSequenceLength(int Z, int I, int M, int number0) {
        int tortoise = function(Z, I, M, number0);
        int hare = function(Z, I, M, function(Z, I, M, number0));

        while (tortoise != hare) {
            tortoise = function(Z, I, M, tortoise);
            hare = function(Z, I, M, function(Z, I, M, hare));
        }

        hare = number0;
        while (tortoise != hare) {
            tortoise = function(Z, I, M, tortoise);
            hare = function(Z, I, M, hare);
        }

        int lambda = 1;
        hare = function(Z, I, M, tortoise);

        while (tortoise != hare) {
            hare = function(Z, I, M, hare);
            lambda++;
        }
        return lambda;
    }

    private static int function(int Z, int I, int M, int number) {
        return (Z * number + I) % M;
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
