package chapter3.section2.j.josephus.problem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 26/01/22.
 */
public class JosephsCousin {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        List<Integer> primeNumbers = eratosthenesSieve(35000);
        int[] dp = new int[3502];

        int peopleNumber = FastReader.nextInt();

        while (peopleNumber != 0) {
            int survivor = computeSurvivor(peopleNumber, primeNumbers, dp);
            outputWriter.printLine(survivor);
            peopleNumber = FastReader.nextInt();
        }
        outputWriter.flush();
    }

    private static int computeSurvivor(int peopleNumber, List<Integer> primeNumbers, int[] dp) {
        if (dp[peopleNumber] != 0) {
            return dp[peopleNumber];
        }

        int person = 0;
        for (int i = 1; i <= peopleNumber; i++) {
            person = (person + primeNumbers.get(peopleNumber - i)) % i;
        }
        dp[peopleNumber] = person + 1;
        return dp[peopleNumber];
    }

    private static List<Integer> eratosthenesSieve(int number) {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[number + 1];

        // 1- Mark all numbers as prime
        for(int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        // 2- Remove numbers multiple of the current element
        // 3- Repeat until we finish verifying the maxNumberToCheck
        for(long i = 2; i <= number; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers;
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
