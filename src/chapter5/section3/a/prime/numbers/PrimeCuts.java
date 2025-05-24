package chapter5.section3.a.prime.numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 22/05/25.
 */
public class PrimeCuts {

    private static class Result {
        int startIndex;
        int endIndex;

        public Result(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        Integer[] primeNumbers = eratosthenesSieve();

        String line = FastReader.getLine();
        while (line != null) {
            String[] data = line.split(" ");
            int n = Integer.parseInt(data[0]);
            int c = Integer.parseInt(data[1]);

            Result primeCut = computePrimeCut(primeNumbers, n, c);
            outputWriter.print(String.format("%d %d:" , n, c));
            for (int i = primeCut.startIndex; i <= primeCut.endIndex; i++) {
                outputWriter.print(" " + primeNumbers[i]);
            }
            outputWriter.printLine("\n");
            line = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static Result computePrimeCut(Integer[] primeNumbers, int n, int c) {
        int maxIndex = binarySearch(primeNumbers, n);
        int middleIndex = maxIndex / 2;

        int startIndex = middleIndex - c + 1;
        int endIndex = middleIndex + c;

        if (maxIndex % 2 == 0) {
            endIndex--;
        }
        startIndex = Math.max(startIndex, 0);
        endIndex = Math.min(endIndex, maxIndex);
        return new Result(startIndex, endIndex);
    }

    private static Integer[] eratosthenesSieve() {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[1001];
        for (int i = 1; i < isPrime.length; i++) {
            isPrime[i] = true;
        }
        primeNumbers.add(1);

        for (int i = 2; i < isPrime.length; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < isPrime.length; j += i) {
                    isPrime[j] = false;
                }
                primeNumbers.add(i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
    }

    private static int binarySearch(Integer[] values, int target) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (values[middle] > target) {
                high = middle - 1;
            } else {
                result = middle;
                low = middle + 1;
            }
        }
        return result;
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

        private static String getLine() throws IOException {
            return reader.readLine();
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
