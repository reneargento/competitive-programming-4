package chapter5.section3.b.probabilistic.prime.testing;

import java.io.*;

/**
 * Created by Rene Argento on 02/06/25.
 */
public class PrimeWords {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);

        boolean[] isPrime = eratosthenesSieve();

        String word = FastReader.getLine();
        while (word != null) {
            boolean isPrimeWord = isPrimeWord(word, isPrime);
            outputWriter.printLine(isPrimeWord ? "It is a prime word." : "It is not a prime word.");
            word = FastReader.getLine();
        }
        outputWriter.flush();
    }

    private static boolean isPrimeWord(String word, boolean[] isPrime) {
        int sum = 0;
        for (char letter : word.toCharArray()) {
            if (Character.isLowerCase(letter)) {
                sum += letter - 'a' + 1;
            } else {
                sum += letter - 'A' + 27;
            }
        }
        return isPrime[sum];
    }

    private static boolean[] eratosthenesSieve() {
        int maxNumber = 1041;
        boolean[] isPrime = new boolean[(int) maxNumber];

        for (int i = 1; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }
        return isPrime;
    }

    private static class FastReader {
        private static BufferedReader reader;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
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
