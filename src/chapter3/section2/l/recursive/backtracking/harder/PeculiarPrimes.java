package chapter3.section2.l.recursive.backtracking.harder;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 27/02/22.
 */
// Based on https://dokumen.tips/documents/sample-solutions-ctu-open-contest-2009.html
public class PeculiarPrimes {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int primesNumber = FastReader.nextInt();

        while (primesNumber != 0) {
            int[] primes = new int[primesNumber];
            for (int i = 0; i < primes.length; i++) {
                primes[i] = FastReader.nextInt();
            }
            int startRange = FastReader.nextInt();
            int endRange = FastReader.nextInt();

            List<Integer> numbers = new ArrayList<>();
            computeNumbers(primes, startRange, endRange, 0, 1, numbers);
            Collections.sort(numbers);

            if (!numbers.isEmpty()) {
                outputWriter.print(numbers.get(0));
                for (int i = 1; i < numbers.size(); i++) {
                    outputWriter.print("," + numbers.get(i));
                }
                outputWriter.printLine();
            } else {
                outputWriter.printLine("none");
            }
            primesNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static void computeNumbers(int[] primes, int start, int end, int startIndex, int number,
                                       List<Integer> numbers) {
        if (end == 0) {
            return;
        }
        if (start == 1) {
            numbers.add(number);
        }

        for (int i = startIndex; i < primes.length; i++) {
            int prime = primes[i];
            int nextStart = (start - 1) / prime + 1;
            int nextEnd = end / prime;
            int nextNumber = number * prime;
            computeNumbers(primes, nextStart, nextEnd, i, nextNumber, numbers);
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
