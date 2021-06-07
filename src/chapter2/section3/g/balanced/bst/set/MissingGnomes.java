package chapter2.section3.g.balanced.bst.set;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 06/06/21.
 */
public class MissingGnomes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int totalGnomes = FastReader.nextInt();
        int remainingGnomesNumber = FastReader.nextInt();
        int[] remainingGnomes = new int[remainingGnomesNumber];
        Set<Integer> remainingGnomesSet = new HashSet<>();

        for (int i = 0; i < remainingGnomes.length; i++) {
            int remainingGnome = FastReader.nextInt();
            remainingGnomes[i] = remainingGnome;
            remainingGnomesSet.add(remainingGnome);
        }

        TreeSet<Integer> gnomesRemoved = getGnomesRemoved(totalGnomes, remainingGnomesSet);

        for (int remainingGnome : remainingGnomes) {
            while (!gnomesRemoved.isEmpty() && gnomesRemoved.first() < remainingGnome) {
                outputWriter.printLine(gnomesRemoved.pollFirst());
            }
            outputWriter.printLine(remainingGnome);
        }

        while (!gnomesRemoved.isEmpty()) {
            outputWriter.printLine(gnomesRemoved.pollFirst());
        }
        outputWriter.flush();
    }

    private static TreeSet<Integer> getGnomesRemoved(int totalGnomes, Set<Integer> remainingGnomesSet) {
        TreeSet<Integer> gnomes = new TreeSet<>();
        for (int i = 1; i <= totalGnomes; i++) {
            if (!remainingGnomesSet.contains(i)) {
                gnomes.add(i);
            }
        }
        return gnomes;
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
