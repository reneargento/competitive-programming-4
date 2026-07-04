package chapter6.section2.f.really.ad.hoc;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 03/07/2026.
 */
public class DNAII {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String dna = FastReader.next();
            long index = computeIndex(dna);
            outputWriter.printLine(String.format("Case %d: (%d:%d)", t, dna.length(), index));
        }
        outputWriter.flush();
    }

    private static long computeIndex(String dna) {
        long index = 0;
        for (int i = 0; i < dna.length(); i++) {
            index += computeLocalIndex(dna.charAt(i), dna.length() - i);
        }
        return index;
    }

    private static long computeLocalIndex(char firstBase, int length) {
        long smallerBases = computeSmallerBases(firstBase);
        long multiplier = (long) Math.pow(4, length - 1);
        return smallerBases * multiplier;
    }

    private static int computeSmallerBases(char base) {
        switch (base) {
            case 'A': return 0;
            case 'C': return 1;
            case 'G': return 2;
            default: return 3;
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